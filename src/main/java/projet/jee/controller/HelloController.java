package projet.jee.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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