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
import projet.jee.others.Constants;
import projet.jee.service.ActivityService;
import projet.jee.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class HelloController {
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private Api api;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //page accueil + recherche programmes
    @RequestMapping(value={"", "/"})
    public String accueil (
            Model model,
            HttpServletRequest request
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
        return "accueil"; // TODO
    }

    @GetMapping(value={"/inscription"})
    public String inscription(
            Model model,
            HttpServletRequest request
    ) {

        String lastPage = request.getHeader("Referer");

        if (lastPage != null && Constants.fromMySite(lastPage)){
            model.addAttribute("sendFromParam", lastPage);
        } else {
            model.addAttribute("sendFromParam", "/");
        }

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
            return "redirect:/inscription"; // TODO Gestion erreur
        }
        String lastPage = request.getParameter("lastPage");

        String lastPageGet = request.getParameter("lastPage");
        if (lastPageGet != null){
            lastPage = lastPageGet;
        }

        if (lastPage != null){
            return "redirect:/connexion?lastPage="+lastPage;
        }
        return "redirect:/connexion";

    }


    @RequestMapping(value="/deconnexion")
    public String deconnexion (
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String lastPage = request.getHeader("Referer");
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        if (lastPage != null){
            return "redirect:"+lastPage;
        } else {
            return "redirect:/";
        }
    }

    @GetMapping(value={"/connexion"})
    public String connexion(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String lastPage = request.getHeader("Referer");

        String lastPageGet = request.getParameter("lastPage");
        if (lastPageGet != null){
            lastPage = lastPageGet;
        }

        if (lastPage != null && Constants.fromMySite(lastPage)){
            model.addAttribute("sendFromParam", lastPage);
        } else {
            model.addAttribute("sendFromParam", "/");
        }
        return "connexion";
    }

    @PostMapping(value={"/connexion"})
    public String connexion(
            HttpServletRequest request
    ) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null){
            return "redirect:/connexion"; // TODO Error
        } else {
            User user = userService.findByUsername(username);
            if (user == null){
                return "redirect:/connexion"; // TODO Error
            } else {
                if(passwordEncoder.matches(password, user.getPassword())){
                    request.getSession(true)
                            .setAttribute("userId",user.getUserId());
                } else {
                    return "redirect:/connexion"; // TODO Error
                }
            }
        }

        String lastPage = request.getParameter("lastPage");
        if (lastPage != null){
            return "redirect:"+lastPage;
        }
        return "/";
    }

    //info profil + programmes suivis
    @RequestMapping(value={"/profile"})
    public String profil(Model model, HttpServletRequest request) throws Exception{
        Object o = request.getSession(false).getAttribute("userId");
        User u = api.fetchUserById((long)o);
        model.addAttribute("me",u);
        List<Subscription> subsList = api.findSubscriptionByUser(u.getUserId());
        model.addAttribute("subs",subsList);
        return "profile";
    }

    @RequestMapping(value={"/activities/{id}"})
    public String activite(@PathVariable("id") Long id, Model model,HttpServletRequest request) throws Exception{
        Optional<Activity> oa = activityService.findById(id);
        if (oa.isPresent()){ // Activité existante
            Activity a = oa.get();
            model.addAttribute("activity",a);

            Object o = request.getSession(false).getAttribute("userId");
            if (o != null) {
                User u = api.fetchUserById((long) o);
                model.addAttribute("me",u);

                Subscription s = api.findSubscriptionByUserAndActivity(u.getUserId(), a.getActivityId());
                if (s != null) {
                    model.addAttribute("isSubscribe", true);
//                    model.addAttribute("subscriptionId", s.getSubscriptionId()); // TODO
                } else {
                    model.addAttribute("isSubscribe", false);
                }
            }
            return "activite";
        } else { // Activité inexistante
            return "redirect:/";
        }
    }

    @GetMapping("/search")
    public String search(HttpServletRequest request,Model model) throws Exception{
        List<Activity> searchList = activityService.findByNameLike(request.getParameter("inputname"));
        Object o = request.getSession(false).getAttribute("userId");
        User u = api.fetchUserById((long)o);
        model.addAttribute("me",u);
        model.addAttribute("search",searchList);
        return "accueil";
    }

    @PostMapping("/activities/{activityId}/subscribe")
    public String subscribe(
            @PathVariable Long activityId,
            HttpServletRequest request
    ) throws  Exception{
        Object o = request.getSession(false).getAttribute("userId");
        if (o != null){
            Optional<User> ou = userService.findById((long)o);
            if (ou.isPresent()){
                User u = ou.get();
                api.subscribeUserToActivity(u.getUserId(),activityId);
            }
        }

        return "redirect:/";
    }

}