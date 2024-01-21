package fr.uge.jee.springmvc.rectangle;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Controller
public class RectangleController {
    @GetMapping("/rectangle")
    public String rectangleForm(Rectangle rectangle){
        return "rectangle";
    }

/*    @PostMapping("/rectangle")
    public String processForm(@ModelAttribute("rectangle") Rectangle param, Model model){
        model.addAttribute("area", param.area());
        return "rectangle-result";
    }*/

    @PostMapping("/rectangle")
    public String processForm(@Valid @ModelAttribute("rectangle") Rectangle rectangle,
                              BindingResult result,
                              Model model){
        if (result.hasErrors()){
            model.addAttribute("error", "one of the value is not a number or negative");
            return "rectangle";
        }
        model.addAttribute("area", rectangle.area());
        return "rectangle-result";
    }
}
