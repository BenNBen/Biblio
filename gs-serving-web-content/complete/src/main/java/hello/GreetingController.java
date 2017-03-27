package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;

@Controller
public class GreetingController implements ErrorController{

    private static final String PATH = "/error";
    
    refList Citations = new refList();
    
    public class Citation{
	String title;
	String year;
	String author;
	String journal;

	public Citation(String title, String year, String author, String journal){
	    title = title;
	    year = year;
	    author = author;
	    journal = journal;
	}
    }

    public class refList{
	private Vector<Citation> references;
    }
    
    
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping("/bibliography")
    public String bibliography(){
	return "BIBLIOGRAPHY";
    }
    
    //public Citation bibliography(){
    //String title, year, author, journal;
	//title = document.getElementById('Ti').value;
	//year = document.getElementById('Yr').value;
	//author = document.getElementById('Au').value;
	//journal = document.getElementById('Jo').value;
	//Citation cite = new Citation(title, year, author, journal);
	//return cite;
    //}

}
