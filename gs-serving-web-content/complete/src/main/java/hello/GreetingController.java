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
    
    private List<Entry> entries;
    @RequestMapping("/biblio")
    public String greeting(Model model) {
	List<Entry> films = this.jdbcTemplate.query("select film, directorFirstName, directorLastName, actors, studio, medium, year from films",
						    new RowMapper<Entry>() {
							public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
							    Entry entry = new FilmEntry(rs.getString("film"),
											rs.getString("directorFirstName"),
											rs.getString("directorLastName"),
											rs.getString("actors"),
											rs.getString("studio"),
											rs.getString("medium"),
											rs.getString("year")
											);
							    entry.cite();
							    return entry;
							}
						    });
	List<Entry> books = this.jdbcTemplate.query("select book, authorFirstName, authorLastName, volume, edition, publisher, city, year from books",
						    new RowMapper<Entry>() {
							public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
							    Entry entry = new BookEntry(rs.getString("book"),
											rs.getString("authorFirstName"),
											rs.getString("authorLastName"),
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
	List<Entry> journals = this.jdbcTemplate.query("select article, authorFirstName, authorLastName, journal, volume, issue, year, startPage, endPage, database, accessDate from journals",
						       new RowMapper<Entry>() {
							   public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
							       Entry entry = new JournalEntry(rs.getString("article"),
											      rs.getString("authorFirstName"),
											      rs.getString("authorLastName"),
											      rs.getString("volume"),
											      rs.getString("journal"),
											      rs.getString("issue"),
											      rs.getString("year"),
											      rs.getString("startDate"),
											      rs.getString("endDate"),
											      rs.getString("dBase"),
											      rs.getString("accessDate")
											      );
							       entry.cite();
							       return entry;
							   }
						       });
	List<Entry> lectures = this.jdbcTemplate.query("select presentation, speakerFirstName, speakerLastName, type, event, city, location, date from lectures",
						       new RowMapper<Entry>() {
							   public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
							       Entry entry = new LectureEntry(rs.getString("presentation"),
											      rs.getString("speakerFirstName"),
											      rs.getString("speakerLastName"),
											      rs.getString("type"),
											      rs.getString("event"),
											      rs.getString("city"),
											      rs.getString("location"),
											      rs.getString("date")
											      );
							       entry.cite();
							       return entry;
							   }
						       });
	List<Entry> websites = this.jdbcTemplate.query("select article, authorFirstName, authorLastName, website, publisher, url, publishDate, accessDate from websites",
						       new RowMapper<Entry>() {
							   public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
							       Entry entry = new WebsiteEntry(rs.getString("article"),
											      rs.getString("authorFirstName"),
											      rs.getString("authorLastName"),
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
	//@Override public int compareTo(Entry compareEntry){
	//   return this.citation.compareTo(compareEntry.citation);
	//};
	//Comparator<Entry> comp = (Entry a, Entry b) -> {
	//  return a.citation.compareTo(b.citation);
	//};
	//Collections.sort(entries, comp);
	model.addAttribute("entries", entries);
	this.entries = entries;
	return "biblio";
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
	jdbcTemplate.update("insert into websites values (?, ?, ?, ?, ?, ?, ?, ?)", a, aFN, aLN, w, p, u, pD, aD);
	return "redirect:biblio"; // back to the biblio view
    }
    
    @RequestMapping("/addBook")
    public String addBook(@RequestParam(value="title", required=true) String t,
			  @RequestParam(value="authorFN", required=true) String aFN,
			  @RequestParam(value="authorLN", required=true) int aLN,
			  @RequestParam(value="volume", required=false) String v,
			  @RequestParam(value="edition", required=false) String e,
			  @RequestParam(value="publisher", required=true) String p,
			  @RequestParam(value="city", required=true) String c,
			  @RequestParam(value="year", required=true) String y,
			  Model model) {
	jdbcTemplate.update("insert into books values (?, ?, ?, ?, ?, ?, ?, ?)", t, aFN, aLN, v, e, p, c, y);
	return "redirect:biblio"; // back to the biblio view
    }
    
    @RequestMapping("/addJournal")
    public String addJournal(@RequestParam(value="article", required=true) String a,
			     @RequestParam(value="authorFN", required=true) String aFN,
			     @RequestParam(value="authorLN", required=true) int aLN,
			     @RequestParam(value="journal", required=true) String j,
			     @RequestParam(value="volume", required=true) String v,
			     @RequestParam(value="issue", required=false) String i,
			     @RequestParam(value="year", required=false) String y,
			     @RequestParam(value="pageStart", required=true) String pS,
			     @RequestParam(value="pageEnd", required=true) String pE,
			     @RequestParam(value="dBase", required=true) String d,
			     @RequestParam(value="accessDate", required=true) String aD,
			     Model model) {
	if(v.equals("")){
	    v = "N/A";
	}
	if(i.equals("")){
	    i = "N/A";
	}
	jdbcTemplate.update("insert into journals values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", a, aFN, aLN, j, v, i, y, pS, pE, d, aD);
	return "redirect:biblio"; // back to the biblio view
    }
    
    @RequestMapping("/addFilm")
    public String addFilm(@RequestParam(value="title", required=true) String t,
			  @RequestParam(value="directorFN", required=true) String dFN,
			  @RequestParam(value="directLN", required=true) int dLN,
			  @RequestParam(value="actors", required=true) String a,
			  @RequestParam(value="studio", required=true) String s,
			  @RequestParam(value="medium", required=true) String m,
			  @RequestParam(value="year", required=true) String y,
			  Model model) {
	jdbcTemplate.update("insert into films values (?, ?, ?, ?, ?, ?, ?)", t, dFN, dLN, a, s, m, y);
	return "redirect:biblio"; // back to the biblio view
    }
    
    @RequestMapping("/addLecture")
    public String addLecture(@RequestParam(value="presentation", required=true) String p,
			     @RequestParam(value="speakerFN", required=true) String sFN,
			     @RequestParam(value="speakerLN", required=true) int sLN,
			     @RequestParam(value="type", required=true) String t,
			     @RequestParam(value="event", required=true) String e,
			     @RequestParam(value="city", required=true) String c,
			     @RequestParam(value="location", required=true) String l,
			     @RequestParam(value="date", required=true) String d,
			     Model model) {
	jdbcTemplate.update("insert into lectures values (?, ?, ?, ?, ?, ?, ?, ?)", p, sFN, sLN, t, e, c, l, d);
	return "redirect:biblio"; // back to the biblio view
    }
    
    @RequestMapping("/deleteEntry")
    public String addEntry(@RequestParam(value="authorLastName", required=true) String a, @RequestParam(value="title", required=true) String t, Model model) {
	for(Entry entry : this.entries){
	    if(entry.title.compareTo(t) == 0 && entry.lastName.compareTo(a) == 0){
    		if(entry instanceof WebsiteEntry){
		    jdbcTemplate.update("delete from websites where article = ? and authorLastName = ?", a, t);
    		}
    		if(entry instanceof BookEntry){
		    jdbcTemplate.update("delete from books where book = ? and authorLastName = ?", a, t);
    		}
    		if(entry instanceof JournalEntry){
		    jdbcTemplate.update("delete from journals where article = ? and authorLastName = ?", a, t);
    		}
    		if(entry instanceof FilmEntry){
		    jdbcTemplate.update("delete from films where film = ? and directorLastName = ?", a, t);
    		}
    		if(entry instanceof LectureEntry){
		    jdbcTemplate.update("delete from lectures where presentation = ? and speakerLastName = ?", a, t);
    		}
	    }
	}
	return "redirect:biblio"; // back to the biblio view
    }
}
