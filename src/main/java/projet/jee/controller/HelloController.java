package projet.jee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import projet.jee.entity.Activity;
import projet.jee.entity.User;
import projet.jee.service.ActivityService;
import projet.jee.service.UserService;

import java.util.List;

@Controller
public class HelloController {
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;

    //page accueil + recherche programmes
    @RequestMapping(value={"", "/"})
    public String main(Model model){
        List<User> appUserList = userService.fetchUserList();
        List<Activity> activityList = activityService.fetchActivityList();
        model.addAttribute("users", appUserList);
        model.addAttribute("activities", activityList);
        return "accueil"; // TODO
    }

    @RequestMapping(value={"/inscription"})
    @ResponseBody
    public String inscription() { return "inscription"; }

    @RequestMapping(value={"/connexion"})
    @ResponseBody
    public String connexion() { return "connexion"; }
    //info profil + programmes suivis
    @RequestMapping(value={"/profil"})
    public String profil() { return "profil"; }

    @RequestMapping(value={"/activite/{id}"})
    public String activite() { return "activite"; }

}