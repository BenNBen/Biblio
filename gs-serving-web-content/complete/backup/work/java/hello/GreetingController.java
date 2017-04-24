package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.*;

@Controller
public class GreetingController{

    // private Vector<Citation> references;
    //references refList = new references();

    
    
    /*  @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    */
    @RequestMapping("/citation")
    public String citationForm(Model model){
	model.addAttribute("citation", new Citation());
	return "citation";
    }

    @RequestMapping("/citation")
    public String citationSubmit(@ModelAttribute Citation citation){
	return "result";
    }
    

}
