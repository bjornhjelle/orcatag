package no.orcatag.rest.controllers;

import no.orcatag.rest.models.Greeting;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by bjorn on 21/09/17.
 */
@Controller
public class GreetingsController {

    @GetMapping("/greeting")
    public String greeting(Model model) {
        System.out.println("her");
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting) {
        return "result";
    }


}
