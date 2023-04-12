package projet.jee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        List<User> userList = userService.fetchUserList();
        List<Activity> activityList = activityService.fetchActivityList();
        model.addAttribute("users",userList);
        model.addAttribute("activities", activityList);
        return "accueil"; // TODO
    }

    @RequestMapping(value={"/inscription"})
    public String inscription() { return "inscription"; }

    @PostMapping("/inscription/confirm")
    public String inscr(@ModelAttribute User user){
        userService.saveUser(user);
        return "redirect:/connexion";
    }

    @RequestMapping(value={"/connexion"})
    public String connexion() { return "connexion"; }
    //info profil + programmes suivis
    @RequestMapping(value={"/users/{id}"})
    public String profil() { return "profil"; }

    @RequestMapping(value={"/activities/{id}"})
    public String activite(@PathVariable("id") Long id, Model model) {
        Activity a = activityService.fetchActivityById(id).get();
        model.addAttribute("activity",a);
        return "activite";
    }

}