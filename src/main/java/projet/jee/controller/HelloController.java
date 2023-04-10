package projet.jee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @RequestMapping(value="/")
    public String redirect(){
        return "redirect:/a";
    }

    @GetMapping("/hello-world")
    public String helloWord() {
        return "projet";
    }

}