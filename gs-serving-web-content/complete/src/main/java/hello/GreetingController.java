package hello;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Controller
public class GreetingController {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private List<Entry> entries;
    @RequestMapping("/biblio")
    public String greeting(Model model) {
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
    
    @RequestMapping("/deleteEntry")
    public String deleteEntry(@RequestParam(value="authorLastName", required=true) String a, @RequestParam(value="title", required=true) String t, @RequestParam(value="type", required=true) String type, Model model) {
	for(Entry entry : this.entries){
	    if(entry.title.compareTo(t) == 0 && entry.lastName.compareTo(a) == 0){
		switch(type) {
                case "lecture": if(entry instanceof LectureEntry){
			jdbcTemplate.update("delete from lectures where presentation = ? and speakerLastName = ?", a, t);
		    }
                case "website": if(entry instanceof WebsiteEntry){
			jdbcTemplate.update("delete from websites where article = ? and authorLastName = ?", a, t);
		    }
                case "book": if(entry instanceof BookEntry){
			jdbcTemplate.update("delete from books where book = ? and authorLastName = ?", a, t);
		    }
                case "film": if(entry instanceof FilmEntry){
			jdbcTemplate.update("delete from films where film = ? and directorLastName = ?", a, t);
		    }
                case "journal": if(entry instanceof JournalEntry){
			jdbcTemplate.update("delete from journals where article = ? and authorLastName = ?", a, t);
		    }
    		}
	    }
	}
	return "redirect:biblio"; // back to the biblio view
    }
    @RequestMapping("/addWebsite")
    public String addWebsite(@RequestParam(value="article", required=true) String article,
			     @RequestParam(value="authorFN", required=true) String authorFN,
			     @RequestParam(value="authorLN", required=true) String authorLN,
			     @RequestParam(value="website", required=true) String website,
			     @RequestParam(value="publisher", required=false) String publisher,
			     @RequestParam(value="url", required=true) String url,
			     @RequestParam(value="publishDate", required=false) String publishDate,
			     @RequestParam(value="accessDate", required=true) String accessDate,
			     Model model) {
	if(publishDate.equals("")){
	    publishDate = "N/A";
	}
	if(publisher.equals("")){
	    publisher = "N/A";
	}
	if(detectDuplicate(article, authorLN, "website") == false){
	    jdbcTemplate.update("insert into websites values (?, ?, ?, ?, ?, ?, ?, ?)", article, authorFN, authorLN, website, publisher, url, publishDate, accessDate);
	}
	return "redirect:biblio"; // back to the biblio view
    }
    
    @RequestMapping("/addBook")
    public String addBook(@RequestParam(value="title", required=true) String title,
			  @RequestParam(value="authorFN", required=true) String authorFN,
			  @RequestParam(value="authorLN", required=true) String authorLN,
			  @RequestParam(value="volume", required=false) String volume,
			  @RequestParam(value="edition", required=false) String edition,
			  @RequestParam(value="publisher", required=true) String publisher,
			  @RequestParam(value="city", required=true) String city,
			  @RequestParam(value="year", required=true) String year,
			  Model model) {
	if(detectDuplicate(title, authorLN, "book") == false){
	    jdbcTemplate.update("insert into books values (?, ?, ?, ?, ?, ?, ?, ?)", title, authorFN, authorLN, volume, publisher, edition, year, city);
	}
	return "redirect:biblio"; // back to the biblio view
    }
    
    @RequestMapping("/addJournal")
    public String addJournal(@RequestParam(value="article", required=true) String article,
			     @RequestParam(value="authorFN", required=true) String authorFN,
			     @RequestParam(value="authorLN", required=true) String authorLN,
			     @RequestParam(value="journal", required=true) String journal,
			     @RequestParam(value="volume", required=true) String volume,
			     @RequestParam(value="issue", required=false) String issue,
			     @RequestParam(value="year", required=false) String year,
			     @RequestParam(value="pageStart", required=true) String pageStart,
			     @RequestParam(value="pageEnd", required=true) String pageEnd,
			     @RequestParam(value="database", required=true) String dBase,
			     @RequestParam(value="accessDate", required=true) String accessDate,
			     Model model) {
	if(volume.equals("")){
	    volume = "N/A";
	}
	if(issue.equals("")){
	    issue = "N/A";
	}
	if(detectDuplicate(article, authorLN, "journal") == false){
	    jdbcTemplate.update("insert into journals values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", article, authorFN, authorLN, volume, journal, issue, year, pageStart, pageEnd, dBase, accessDate);
	}
	return "redirect:biblio"; // back to the biblio view
    }
    
    @RequestMapping("/addFilm")
    public String addFilm(@RequestParam(value="title", required=true) String title,
			  @RequestParam(value="directorFN", required=true) String directorFN,
			  @RequestParam(value="directorLN", required=true) String directorLN,
			  @RequestParam(value="actors", required=true) String actors,
			  @RequestParam(value="studio", required=true) String studio,
			  @RequestParam(value="medium", required=true) String medium,
			  @RequestParam(value="year", required=true) String year,
			  Model model) {
	if(detectDuplicate(title, directorLN, "film") == false){
	    jdbcTemplate.update("insert into films values (?, ?, ?, ?, ?, ?, ?)", title, directorFN, directorLN, actors, studio, medium, year);
	}
	return "redirect:biblio"; // back to the biblio view
    }
    
    @RequestMapping("/addLecture")
    public String addLecture(@RequestParam(value="presentation", required=true) String presentation,
			     @RequestParam(value="speakerFN", required=true) String speakerFN,
			     @RequestParam(value="speakerLN", required=true) String speakerLN,
			     @RequestParam(value="type", required=true) String type,
			     @RequestParam(value="event", required=true) String event,
			     @RequestParam(value="city", required=true) String city,
			     @RequestParam(value="location", required=true) String location,
			     @RequestParam(value="date", required=true) String date,
			     Model model) {
	if(detectDuplicate(presentation, speakerLN, "lecture") == false){
	    jdbcTemplate.update("insert into lectures values (?, ?, ?, ?, ?, ?, ?, ?)", presentation, speakerFN, speakerLN, type, event, city, location, date);
	}
	return "redirect:biblio"; // back to the biblio view
    }
    
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
    
    @RequestMapping("/searchEntry")
    public String search(@RequestParam(value="authorLastName", required=true) String a, @RequestParam(value="title", required=true) String t, @RequestParam(value="type", required=true) String type, Model model) {
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
	    return "redirect:biblio"; // back to the biblio view
	}
	return "ERROR";
    }  
}
