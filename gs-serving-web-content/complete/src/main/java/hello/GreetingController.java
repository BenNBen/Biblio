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
    public String addWebsite(@RequestParam(value="article", required=true) String a,
			     @RequestParam(value="authorFN", required=true) String aFN,
			     @RequestParam(value="authorLN", required=true) String aLN,
			     @RequestParam(value="website", required=true) String w,
			     @RequestParam(value="publisher", required=false) String p,
			     @RequestParam(value="url", required=true) String u,
			     @RequestParam(value="publishDate", required=false) String pD,
			     @RequestParam(value="accessDate", required=true) String aD,
			     Model model) {
	if(pD.equals("")){
	    pD = "N/A";
	}
	if(p.equals("")){
	    p = "N/A";
	}
	if(detectDuplicate(a, aLN, "website") == false){
	    jdbcTemplate.update("insert into websites values (?, ?, ?, ?, ?, ?, ?, ?)", a, aFN, aLN, w, p, u, pD, aD);
	}
	return "redirect:biblio"; // back to the biblio view
    }
    
    @RequestMapping("/addBook")
    public String addBook(@RequestParam(value="title", required=true) String t,
			  @RequestParam(value="authorFN", required=true) String aFN,
			  @RequestParam(value="authorLN", required=true) String aLN,
			  @RequestParam(value="volume", required=false) String v,
			  @RequestParam(value="edition", required=false) String e,
			  @RequestParam(value="publisher", required=true) String p,
			  @RequestParam(value="city", required=true) String c,
			  @RequestParam(value="year", required=true) String y,
			  Model model) {
	if(detectDuplicate(t, aLN, "book") == false){
	    jdbcTemplate.update("insert into books values (?, ?, ?, ?, ?, ?, ?, ?)", t, aFN, aLN, v, p, e, y, c);
	}
	return "redirect:biblio"; // back to the biblio view
    }
    
    @RequestMapping("/addJournal")
    public String addJournal(@RequestParam(value="article", required=true) String a,
			     @RequestParam(value="authorFN", required=true) String aFN,
			     @RequestParam(value="authorLN", required=true) String aLN,
			     @RequestParam(value="journal", required=true) String j,
			     @RequestParam(value="volume", required=true) String v,
			     @RequestParam(value="issue", required=false) String i,
			     @RequestParam(value="year", required=false) String y,
			     @RequestParam(value="pageStart", required=true) String pS,
			     @RequestParam(value="pageEnd", required=true) String pE,
			     @RequestParam(value="database", required=true) String d,
			     @RequestParam(value="accessDate", required=true) String aD,
			     Model model) {
	if(v.equals("")){
	    v = "N/A";
	}
	if(i.equals("")){
	    i = "N/A";
	}
	if(detectDuplicate(a, aLN, "journal") == false){
	    jdbcTemplate.update("insert into journals values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", a, aFN, aLN, v, j, i, y, pS, pE, d, aD);
	}
	return "redirect:biblio"; // back to the biblio view
    }
    
    @RequestMapping("/addFilm")
    public String addFilm(@RequestParam(value="title", required=true) String t,
			  @RequestParam(value="directorFN", required=true) String dFN,
			  @RequestParam(value="directLN", required=true) String dLN,
			  @RequestParam(value="actors", required=true) String a,
			  @RequestParam(value="studio", required=true) String s,
			  @RequestParam(value="medium", required=true) String m,
			  @RequestParam(value="year", required=true) String y,
			  Model model) {
	if(detectDuplicate(t, dLN, "film") == false){
	    jdbcTemplate.update("insert into films values (?, ?, ?, ?, ?, ?, ?)", t, dFN, dLN, a, s, m, y);
	}
	return "redirect:biblio"; // back to the biblio view
    }
    
    @RequestMapping("/addLecture")
    public String addLecture(@RequestParam(value="presentation", required=true) String p,
			     @RequestParam(value="speakerFN", required=true) String sFN,
			     @RequestParam(value="speakerLN", required=true) String sLN,
			     @RequestParam(value="type", required=true) String t,
			     @RequestParam(value="event", required=true) String e,
			     @RequestParam(value="city", required=true) String c,
			     @RequestParam(value="location", required=true) String l,
			     @RequestParam(value="date", required=true) String d,
			     Model model) {
	if(detectDuplicate(p, sLN, "lecture") == false){
	    jdbcTemplate.update("insert into lectures values (?, ?, ?, ?, ?, ?, ?, ?)", p, sFN, sLN, t, e, c, l, d);
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
