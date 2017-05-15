package hello;
/*
  Authors: Ben Barriage & Anne Marie Bogar
  Purpose: Design an HTML/Java/MYSQL based Bibliography program supporting multiple types of media (book, film, journal, lecture, website)
  Bugs: Bess beetle, pill bug, addBook only working once for some reason
  Date: 5/10/17
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Controller
@RequestMapping("/")
public class GreetingController {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private List<Entry> entries;
    @GetMapping("/biblio")
    public String greeting(Model model) {
	//establishing new entries of each type
	List<Entry> films = this.jdbcTemplate.query(
						    "select title, directorFN, directorLN, actors, studio, medium, year from films",
						    new RowMapper<Entry>() {
							public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
							    Entry entry = new FilmEntry(rs.getString("title"),
											rs.getString("directorFN"),
											rs.getString("directorLN"),
											rs.getString("actors"),
											rs.getString("studio"),
											rs.getString("medium"),
											rs.getString("year")
											);
							    entry.cite();
							    return entry;
							}
						    });
	List<Entry> books = this.jdbcTemplate.query(
						    "select title, authorFN, authorLN, volume, publisher, edition, year, city from books",
						    new RowMapper<Entry>() {
							public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
							    Entry entry = new BookEntry(rs.getString("title"),
											rs.getString("authorFN"),
											rs.getString("authorLN"),
											rs.getString("volume"),
											rs.getString("edition"),
											rs.getString("publisher"),
											rs.getString("city"),
											rs.getString("year")
											);
							    entry.cite();
							    return entry;
							}
						    });
	List<Entry> journals = this.jdbcTemplate.query(
						       "select article, authorFN, authorLN, volume, journal, issue, year, pageStart, pageEnd, dBase, accessDate from journals",
						       new RowMapper<Entry>() {
							   public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
							       Entry entry = new JournalEntry(rs.getString("article"),
											      rs.getString("authorFN"),
											      rs.getString("authorLN"),
											      rs.getString("volume"),
											      rs.getString("journal"),
											      rs.getString("issue"),
											      rs.getString("year"),
											      rs.getString("pageStart"),
											      rs.getString("pageEnd"),
											      rs.getString("dBase"),
											      rs.getString("accessDate")
											      );
							       entry.cite();
							       return entry;
							   }
						       });
	List<Entry> lectures = this.jdbcTemplate.query(
						       "select presentation, speakerFN, speakerLN, type, event, city, location, speechDate from lectures",
						       new RowMapper<Entry>() {
							   public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
							       Entry entry = new LectureEntry(rs.getString("presentation"),
											      rs.getString("speakerFN"),
											      rs.getString("speakerLN"),
											      rs.getString("type"),
											      rs.getString("event"),
											      rs.getString("city"),
											      rs.getString("location"),
											      rs.getString("speechDate")
											      );
							       entry.cite();
							       return entry;
							   }
						       });
	List<Entry> websites = this.jdbcTemplate.query(
						       "select article, authorFN, authorLN, website, publisher, url, publishDate, accessDate from websites",
						       new RowMapper<Entry>() {
							   public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
							       Entry entry = new WebsiteEntry(rs.getString("article"),
											      rs.getString("authorFN"),
											      rs.getString("authorLN"),
											      rs.getString("website"),
											      rs.getString("publisher"),
											      rs.getString("url"),
											      rs.getString("publishDate"),
											      rs.getString("accessDate")
											      );
							       entry.cite();
							       return entry;
							   }
						       });
	//display all list entries of each media type
	List<Entry> entries = new ArrayList<Entry>(films);
	entries.addAll(books);
	entries.addAll(journals);
	entries.addAll(lectures);
	entries.addAll(websites);
	boolean flag = true;
	Entry temp;
	while(flag){
	    flag = false;
	    for(int j=0; j < entries.size() - 1; j++){
		if(entries.get(j).citation.compareTo(entries.get(j+1).citation) > 0){
		    temp = entries.get(j).cloneEntry();
		    entries.set(j, entries.get(j+1).cloneEntry());
		    entries.set(j+1, temp.cloneEntry());
		    flag = true;
		}
	    }
	}
	model.addAttribute("entries", entries);
	this.entries = entries;
	return "biblio";
    }

    @GetMapping("/deleteEntry")
    public String switchEntry(Model model){
	return "/deleteEntry";
    }

    //used to delete an entry from the table using the last name of the author and the title of the work, update once done
    @RequestMapping("/deleteEntry")
    public String deleteEntry(@RequestParam(value="authorLN", required=true) String authorLN, @RequestParam(value="title",required=true) String title, @RequestParam(value="type", required=true) String type, Model model) {
	for(Entry entry : this.entries){
	    if(entry.title.compareTo(title) == 0 && entry.lastName.compareTo(authorLN) == 0){
		switch(type) {
                case "lecture": if(entry instanceof LectureEntry){
			jdbcTemplate.update("delete from lectures where presentation = ? and speakerLN = ?", title, authorLN);
		    }
                case "website": if(entry instanceof WebsiteEntry){
			jdbcTemplate.update("delete from websites where article = ? and authorLN = ?", title, authorLN);
		    }
                case "book": if(entry instanceof BookEntry){
			jdbcTemplate.update("delete from books where title = ? and authorLN = ?", title, authorLN);
		    }
                case "film": if(entry instanceof FilmEntry){
			jdbcTemplate.update("delete from films where title = ? and directorLN = ?", title, authorLN);
		    }
                case "journal": if(entry instanceof JournalEntry){
			jdbcTemplate.update("delete from journals where article = ? and authorLN = ?", title, authorLN);
		    }
    		}
	    }
	}
	return "redirect:/biblio"; // back to the biblio view
    }


    //used to switch to alternate page to add information into sql
    @GetMapping("/addWebsite")
    public String switchWebsite(Model model){
	return "addWebsite";
    }

    //the actual page/function call to add information to the MySQL table
    @PostMapping("/addWebsite")
    public String addWebsite(@RequestParam(value="article", required=true) String article,
			     @RequestParam(value="authorFN", required=true) String authorFN,
			     @RequestParam(value="authorLN", required=true) String authorLN,
			     @RequestParam(value="website", required=true) String website,
			     @RequestParam(value="publisher", required=false) String publisher,
			     @RequestParam(value="url", required=true) String url,
			     @RequestParam(value="publishDate", required=false) String publishDate,
			     @RequestParam(value="accessDate", required=true) String accessDate,
			     Model model) {
	//this is because not every website will have a publishDate, nor publisher
	if(publishDate.equals("")){
	    publishDate = "N/A";
	}
	if(publisher.equals("")){
	    publisher = "N/A";
	}

	//used to check if that item already exists, if so the item is not added
	if(detectDuplicate(article, authorLN, "website") == false){
	    jdbcTemplate.update("insert into websites values (?, ?, ?, ?, ?, ?, ?, ?)", article, authorFN, authorLN, website, publisher, url, publishDate, accessDate);
	    }
	return "redirect:/biblio"; // back to the biblio view
	
    }

    //function used to switch view to that of adding a book
    @GetMapping("/addBook")
    public String switchBook(Model model){
	return "addBook";
    }

    //actual function used to submit book information for a new database entry
    @PostMapping("/addBook")
    public String addBook(@RequestParam(value="title", required=true) String title,
			  @RequestParam(value="authorFN", required=true) String authorFN,
			  @RequestParam(value="authorLN", required=true) String authorLN,
			  @RequestParam(value="volume", required=true) String volume,
			  @RequestParam(value="publisher", required=true) String publisher,
			  @RequestParam(value="edition", required=true) String edition,
			  @RequestParam(value="year", required=true) String year,
			  @RequestParam(value="city", required=true) String city,
			  Model model) {
	//checks if the entered information matches that of a current entry
	if(detectDuplicate(title, authorLN, "book") == false){
	    jdbcTemplate.update("insert into books values (?, ?, ?, ?, ?, ?, ?, ?)", title, authorFN, authorLN, volume, publisher, edition, year, city);
	}
	return "redirect:/biblio"; // back to the biblio view
    }

    //function used to switch view to that of adding journal information
    @GetMapping("/addJournal")
    public String switchJournal(Model model){
	return "addJournal";
    }

    //function used to add journal information into the database from the webpage
    @PostMapping("/addJournal")
    public String addJournal(@RequestParam(value="article", required=true) String article,
			     @RequestParam(value="authorFN", required=true) String authorFN,
			     @RequestParam(value="authorLN", required=true) String authorLN,
			     @RequestParam(value="journal", required=true) String journal,
			     @RequestParam(value="volume", required=false) String volume,
			     @RequestParam(value="issue", required=false) String issue,
			     @RequestParam(value="year", required=true) String year,
			     @RequestParam(value="pageStart", required=true) String pageStart,
			     @RequestParam(value="pageEnd", required=true) String pageEnd,
			     @RequestParam(value="database", required=true) String dBase,
			     @RequestParam(value="accessDate", required=true) String accessDate,
			     Model model) {

	//not all journals will have a volume or issue, so they are not required
	if(volume.equals("")){
	    volume = "N/A";
	}
	if(issue.equals("")){
	    issue = "N/A";
	}
	//checks if entered information matches that of a current database entry
	if(detectDuplicate(article, authorLN, "journal") == false){
	    jdbcTemplate.update("insert into journals values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", article, authorFN, authorLN, journal, volume, issue, year, pageStart, pageEnd, dBase, accessDate);
	}
	
	return "redirect:/biblio"; // back to the biblio view
    }

    //switches view to that of adding a film
    @GetMapping("/addFilm")
    public String switchFilm(Model model) {
	return "addFilm"; // back to the biblio view
    }

    //adds entered film data into the MySQL database
    @PostMapping("/addFilm")
    public String addFilm(
			  @RequestParam(value="title", required=true) String title,
			  @RequestParam(value="directorFN", required=true) String directorFN,
			  @RequestParam(value="directorLN", required=true) String directorLN,
			  @RequestParam(value="actors", required=true) String actors,
			  @RequestParam(value="studio", required=true) String studio,
			  @RequestParam(value="medium", required=true) String medium,
			  @RequestParam(value="year", required=true) String year,
			  Model model){
	//checks if entered film data already belongs to the MySQL database, only adds entry if it does not currently exist
	if(detectDuplicate(title, directorLN, "film") == false){
	    jdbcTemplate.update("insert into films values (?, ?, ?, ?, ?, ?, ?)", title, directorFN, directorLN, actors, studio, medium, year);
	}
	return "redirect:/biblio";
    }
    

    //function used to switch view to that of adding a lecture citation
    @GetMapping("/addLecture")
    public String switchLecture(Model model){
	return "addLecture";
    }

    //function used to enter lecture information into the MySQL database
    @PostMapping("/addLecture")
    public String addLecture(@RequestParam(value="presentation", required=true) String presentation,
			     @RequestParam(value="speakerFN", required=true) String speakerFN,
			     @RequestParam(value="speakerLN",required=true) String speakerLN,
			     @RequestParam(value="type", required=true) String type,
			     @RequestParam(value="event", required=true) String event,
			     @RequestParam(value="city", required=true) String city,
			     @RequestParam(value="location", required=true) String location,
			     @RequestParam(value="date", required=true) String date,
			     Model model) {
	//checks if the entered information already exists in the database based off of presentation and speaker Last name; if so, the data is not added
	if(detectDuplicate(presentation, speakerLN, "lecture") == false){
	    jdbcTemplate.update("insert into lectures values (?, ?, ?, ?, ?, ?, ?, ?)", presentation, speakerFN, speakerLN, type, event, city, location, date);
	}    
	return "redirect:/biblio";
    }

    //function used to check whether a proposed entry matches that of an entry currently in the database. used to keep duplicate information from being added into the database
    public boolean detectDuplicate(String title, String lastName, String type){
	for(Entry entry : this.entries){
	    if(entry.title.compareTo(title) == 0 && entry.lastName.compareTo(lastName) == 0){
		switch(type) {
                case "lecture": if(entry instanceof LectureEntry){
			return true;
		    }
                case "website": if(entry instanceof WebsiteEntry){
			return true;
		    }
                case "book": if(entry instanceof BookEntry){
			return true;
		    }
                case "film": if(entry instanceof FilmEntry){
			return true;
		    }
                case "journal": if(entry instanceof JournalEntry){
			return true;
		    }
		}
	    }
	}
	return false;
    }

    //search entry function used to check whether an entry currently exists in the database based off of the entered last name and work, as well as type of media
    @RequestMapping("/searchEntry")
    public String switchSearch(Model model){
	return "searchEntry";
    }
    
    @PostMapping("/searchEntry")
    public String search(@RequestParam(value="lastName", required=true) String a, @RequestParam(value="work", required=true) String t, @RequestParam(value="type", required=true) String type, Model model) {
	for(Entry entry : this.entries){
	    if(entry.title.compareTo(t) == 0 && entry.lastName.compareTo(a) == 0){
    		switch(type) {
                case "lecture": if(entry instanceof LectureEntry){
			model.addAttribute("searchedEntry", entry);
		    }
                case "website": if(entry instanceof WebsiteEntry){
			model.addAttribute("searchedEntry", entry);
		    }
                case "book": if(entry instanceof BookEntry){
			model.addAttribute("searchedEntry", entry);
		    }
                case "film": if(entry instanceof FilmEntry){
			model.addAttribute("searchedEntry", entry);
		    }
                case "journal": if(entry instanceof JournalEntry){
			model.addAttribute("searchedEntry", entry);
		    }
    		}
	    }
	    return "redirect:/biblio"; // back to the biblio view
	}
	return "redirect:/searchEntry";
    }  
}
