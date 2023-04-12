package projet.jee.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import projet.jee.entity.Activity;
import projet.jee.entity.User;
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

    //page accueil + recherche programmes
    @RequestMapping(value={"", "/"})
    public String main(Model model, HttpSession httpSession){
        List<User> appUserList = userService.fetchUserList();
        List<Activity> activityList = activityService.fetchActivityList();

        User me = null;
        Object o = httpSession.getAttribute("user_id");
        System.out.println(httpSession.getId());
        System.out.println("pass1");
        if (o != null){
            System.out.println("pass2");
            Optional<User> oMe = userService.fetchUserById((long)o);
            if (oMe.isPresent()){
                me = oMe.get();
                System.out.println("pass3");
            }
        }

        model.addAttribute("me", me);

        model.addAttribute("users", appUserList);
        model.addAttribute("activities", activityList);
        return "accueil"; // TODO
    }

    @RequestMapping(value={"/inscription"})
    @ResponseBody
    public String inscription() { return "inscription"; }

    @RequestMapping(value={"/deconnexion","/disconnect"})
    @ResponseBody
    public void deconnexion(
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        session.invalidate();
        System.out.println("disconnect : "+request.getQueryString());
        response.sendRedirect("/");
    }

    @RequestMapping(value={"/connexion"})
    @ResponseBody
    public String connexion() { return "connexion"; }
    //info profil + programmes suivis
    @RequestMapping(value={"/profil"})
    public String profil() { return "profil"; }

    @RequestMapping(value={"/activite/{id}"})
    public String activite() { return "activite"; }

}