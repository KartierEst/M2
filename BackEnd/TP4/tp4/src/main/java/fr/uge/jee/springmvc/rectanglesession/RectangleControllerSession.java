package fr.uge.jee.springmvc.rectanglesession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@SessionAttributes("history")
public class RectangleControllerSession {
    @ModelAttribute("history")
    public History history(){
        return new History();
    }

    @GetMapping("/rectangle-session")
    public String rectangleForm(Rectangle rectangle,
                                Model model,
                                @ModelAttribute("history") History history){
        model.addAttribute("rectangle", new Rectangle());
        return "rectangle-session";
    }

    @PostMapping("/rectangle-session")
    public String processForm(@Valid @ModelAttribute("rectangle") Rectangle rectangle,
                              BindingResult result,
                              @ModelAttribute("history") History history,
                              Model model){
        if (result.hasErrors()){
            model.addAttribute("error", "one of the value is not a number or negative");
            return "rectangle-session";

        }
        history.add("width : " + rectangle.getWidth() + " height : " + rectangle.getHeight() + " area : " + rectangle.area());
        return "rectangle-session";
    }
}
