package projet.jee.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projet.jee.entity.Activity;
import projet.jee.entity.Subscription;
import projet.jee.entity.User;
import projet.jee.error.AlreadyExistException;
import projet.jee.error.NotFoundException;
import projet.jee.others.Constants;
import projet.jee.service.ActivityService;
import projet.jee.service.SubscriptionService;
import projet.jee.service.UserService;

import java.util.List;
import java.util.Optional;

/**
 * Controller pour les pages web
 */
@Controller
public class HelloController {
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private Api api;
    @Autowired
    private PasswordEncoder passwordEncoder;


    // Accueil

    @RequestMapping(value={"", "/"})
    public String accueil (
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        HttpSession httpSession = request.getSession(false);

        List<User> userList = userService.findAll();
        List<Activity> activityList = activityService.findAll();

        User me = null;
        if (httpSession != null) {
            Object o = httpSession.getAttribute("userId");
            if (o != null) {
                Optional<User> oMe = userService.findById((long) o);
                if (oMe.isPresent()) {
                    me = oMe.get();
                }
            }
        }

        model.addAttribute("me", me);
        model.addAttribute("users", userList);
        model.addAttribute("activities", activityList);

        List<Activity> mostsPopular = activityService.findAll();
        mostsPopular.sort((lhs, rhs) -> lhs.getNotationAverage() == null && rhs.getNotationAverage() == null ? 0 :
                (lhs.getNotationAverage() == null && rhs.getNotationAverage() != null) ? 1 :
                (lhs.getNotationAverage() != null && rhs.getNotationAverage() == null) ? -1 :
                        rhs.getNotationAverage().compareTo(lhs.getNotationAverage()));

        if (mostsPopular.size() >= 4) {
            model.addAttribute("popularActivity1", mostsPopular.get(0));
            model.addAttribute("popularActivity2", mostsPopular.get(1));
            model.addAttribute("popularActivity3", mostsPopular.get(2));
            model.addAttribute("popularActivity4", mostsPopular.get(3));
        }

        Constants.applyModel(model, request, userService);
        Constants.setLastPage("/", response);
        return "accueil";
    }


    // Inscription + Connexion + Déconnexion

    @GetMapping(value="/inscription")
    public String inscription(
            Model model,
            HttpServletRequest request
    ) {
        Constants.applyModel(model, request, userService);
        return "inscription";
    }

    @PostMapping("/inscription")
    public String inscription(
            @ModelAttribute User user,
            HttpServletRequest request
    ){
        try {
            api.saveUser(user);
        } catch (AlreadyExistException e) {
            return "redirect:/inscription?error=Nom d'utilisateur deja pris";
        }
        return "redirect:/connexion";
    }

    @RequestMapping(value="/deconnexion")
    public String deconnexion (
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        return "redirect:"+Constants.getLastPage("/", request);
    }

    @GetMapping(value={"/connexion"})
    public String connexion(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        model.addAttribute("error", request.getParameter("error"));
        Constants.applyModel(model, request, userService);
        return "connexion";
    }

    @PostMapping(value={"/connexion"})
    public String connexion(
            HttpServletRequest request
    ) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null){
            return "redirect:/connexion?error=Merci d'indiquer vos identifiants";
        } else {
            User user = userService.findByUsername(username);
            if (user == null){
                return "redirect:/connexion?error=Nom d'utilisateur ou mot de passe incorrect";
            } else {
                if(passwordEncoder.matches(password, user.getPassword())){
                    request.getSession(true)
                            .setAttribute("userId",user.getUserId());
                } else {
                    return "redirect:/connexion?error=Nom d'utilisateur ou mot de passe incorrect";
                }
            }
        }
        return "redirect:"+Constants.getLastPage("/", request); // Connexion réussie
    }


    // Profil

    @RequestMapping(value={"/profil"})
    public String profil(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception{
        Object o = request.getSession(false).getAttribute("userId");
        User u = api.fetchUserById((long)o);
        model.addAttribute("me",u);
        List<Subscription> subsList = api.findSubscriptionByUser(u.getUserId());
        model.addAttribute("subs",subsList);

        Constants.applyModel(model, request, userService);
        model.addAttribute("error", request.getParameter("error"));
        Constants.setLastPage("/profil", response);
        return "profil";
    }

    @PostMapping(value={"/profil/edit"})
    public String profilEdit(HttpServletRequest request) throws Exception{
        Object o = request.getSession(false).getAttribute("userId");
        User u = api.fetchUserById((long)o);
        u.setUsername(request.getParameter("username"));
        u.setPassword(request.getParameter("password"));
        try {
            api.updateUser(u);
        } catch (AlreadyExistException ignored) {
            return "redirect:/profil?error=Nom d'utilisateur deja pris";
        }
        return "redirect:"+Constants.getLastPage("/profil", request);
    }


    // Activités

    @GetMapping(value="/activites")
    public String activites(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        String s = request.getParameter("query");

        if (s != null){
            List<Activity> byName = activityService.findByNameLike(s);
            model.addAttribute("query", s);
            model.addAttribute("listActivities", byName);
            model.addAttribute("isNoResult", (byName.size()==0));
        } else {
            List<Activity> all = activityService.findAll();
            model.addAttribute("listActivities", all);
            model.addAttribute("isNoResult", (all.size()==0));
        }

        Constants.applyModel(model, request, userService);
        Constants.setLastPage("/activites", response);
        return "activites";
    }

    @RequestMapping(value={"/activites/{id}"})
    public String activite(
            @PathVariable("id") Long id,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception{
        Optional<Activity> oa = activityService.findById(id);
        if (oa.isPresent()){ // Activité existante
            Activity a = oa.get();
            model.addAttribute("activity",a);

            HttpSession ses = request.getSession(false);
            if (ses != null) {
                Long uId = (Long)ses.getAttribute("userId");
                User u = api.fetchUserById(uId);
                model.addAttribute("me",u);

                Subscription s = api.findSubscriptionByUserAndActivity(u.getUserId(), a.getActivityId());
                if (s != null) {
                    model.addAttribute("isSubscribe", true);
                    model.addAttribute("note", s.getNote());
                } else {
                    model.addAttribute("isSubscribe", null);
                }
            }
            Constants.applyModel(model, request, userService);
            Constants.setLastPage("/activites/"+id, response);
            return "activites-id";
        } else { // Activité inexistante
            return "redirect:/404";
        }
    }

    @RequestMapping(value = "/activites/{id}/note/{note}")
    public String userNoteActivity(
            @PathVariable(value = "id") Long activityId,
            @PathVariable int note,
            HttpServletRequest request
    ) {
        Optional<Activity> oa = activityService.findById(activityId);
        if (oa.isPresent()){ // Activité existante
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("userId") != null) {
                // Connecté
                if (note<6 && note>0) {
                    long userId = (long) session.getAttribute("userId");
                    Subscription s = api.findSubscriptionByUserAndActivity(userId, activityId);
                    s.setNote(note);
                    subscriptionService.save(s);
                }
            }
            return "redirect:"+Constants.getLastPage("/activites/"+activityId, request);
        } else { // Activité inexistante
            return "redirect:/404";
        }
    }

    @RequestMapping("/activites/{activityId}/subscribe")
    public String subscribe(
            @PathVariable Long activityId,
            HttpServletRequest request
    ) {
        Object o = request.getSession(false).getAttribute("userId");
        if (o != null){
            Optional<User> ou = userService.findById((long)o);
            if (ou.isPresent()){
                User u = ou.get();
                try {
                    api.subscribeUserToActivity(u.getUserId(),activityId);
                } catch (NotFoundException ignored) {}
            }
        }
        return "redirect:"+Constants.getLastPage("/activites/"+activityId, request);
    }

    @RequestMapping("/activites/{activityId}/unsubscribe")
    public String unsubscribe(
            @PathVariable Long activityId,
            HttpServletRequest request
    ) {
        Object o = request.getSession(false).getAttribute("userId");
        if (o != null){
            Optional<User> ou = userService.findById((long)o);
            if (ou.isPresent()){
                User u = ou.get();
                api.unsubscribeUserToActivity(u.getUserId(),activityId);
            }
        }
        return "redirect:"+Constants.getLastPage("/activites/"+activityId, request);
    }


    // Admin

    @RequestMapping(value = {"/admin"})
    public String adminPanel(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        Object o = request.getSession(false).getAttribute("userId");
        Optional<User> ou = userService.findById((long)o);
        if (ou.isPresent()){
            User u = ou.get();
            model.addAttribute("me",u);
            List<User> userList = userService.findAll();
            model.addAttribute("users",userList);
            Constants.applyModel(model, request, userService);
            Constants.setLastPage("/admin", response);
            return "admin";
        }
        return "redirect:"+Constants.getLastPage("/", request);
    }

    @PostMapping("/admin/activites/add")
    public String addActivity(HttpServletRequest request){
        Activity activity = new Activity();
        Long id = activityService.save(activity).getActivityId();
        return "redirect:/admin/activites/"+id+"/edit";
    }

    @GetMapping("/admin/activites/{id}/edit")
    public String updateActivity(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable Long id
    ) {
        Optional<Activity> oa = activityService.findById(id);
        if (oa.isPresent()){ // Activité existante
            Activity a = oa.get();
            model.addAttribute("activity",a);

            HttpSession ses = request.getSession(false);
            if (ses != null) {
                Long uId = (Long)ses.getAttribute("userId");
                try {
                    User u = api.fetchUserById(uId);
                    if (u.isAdmin()) {
                        model.addAttribute("me", u);
                        model.addAttribute("isEditing", true);
                        Constants.applyModel(model, request, userService);
                        Constants.setLastPage("/admin/activites/"+id+"/edit", response);
                        return "activites-id";
                    }
                } catch (NotFoundException ignored) {}
            }
            return "redirect:"+Constants.getLastPage("/404", request);
        }
        return "redirect:/404";
    }

    @PostMapping("/admin/activites/{id}/edit")
    public String confirmEditActivity(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        Activity activity = new Activity();
        activity.setName(request.getParameter("name"));
        activity.setTel(request.getParameter("tel"));
        activity.setAddress(request.getParameter("address"));
        activity.setMail(request.getParameter("mail"));
        activity.setVille(request.getParameter("ville"));
        activity.setDescription(request.getParameter("description"));
        activity.setActivityId(id);
        activityService.update(activity);
        return "redirect:/activites/"+id;
    }

    @RequestMapping("/admin/activites/{id}/delete")
    public String deleteActivity(
            HttpServletRequest request,
            @PathVariable Long id
    ) {
        api.deleteActivityById(id);
        return "redirect:/";
    }

    @PostMapping("/admin/users/{id}/promote")
    public String promoteUser(@PathVariable Long id, HttpServletRequest request) throws Exception{
        User u = api.fetchUserById(id);
        u.setAdmin(true);
        userService.save(u);
        return "redirect:"+Constants.getLastPage("/admin", request);
    }

    @PostMapping("/admin/users/{id}/destitute")
    public String destituteUser(@PathVariable Long id, HttpServletRequest request) throws Exception{
        User u = api.fetchUserById(id);
        u.setAdmin(false);
        userService.save(u);
        return "redirect:"+Constants.getLastPage("/admin", request);
    }

    @RequestMapping("/admin/users/{id}/delete")
    public String deleteUser(
            HttpServletRequest request,
            @PathVariable Long id
    ) {
        api.deleteUserById(id);
        return "redirect:"+Constants.getLastPage("/admin", request);
    }


    // 404

    @RequestMapping("/404")
    public String error404(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        Constants.applyModel(model, request, userService);
        Constants.setLastPage("/404", response);
        model.addAttribute("walle", true);
        return "404";
    }

    @RequestMapping("/*")
    public String redirect404(
            HttpServletRequest request
    ){
        return "redirect:"+Constants.getLastPage("/404", request);
    }

}