package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @RequestMapping("/biblio")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "biblio";
    }

    @RequestMapping("/addEntry")
    public String addEntry(@RequestParam(value="title", required=true) String t, Model model) {
        model.addAttribute("title", t);
        System.out.println("adding new entry with title "+t);
        return "biblio"; // back to the biblio view
    }

}
