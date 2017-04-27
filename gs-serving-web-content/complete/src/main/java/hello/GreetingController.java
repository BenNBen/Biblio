package hello;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Controller
public class GreetingController {

    @RequestMapping("/biblio")
    public String greeting(Model model) {
        List<Entry> entries = this.jdbcTemplate.query(
        "select author, title, year, journal, and media (movie, book, game) from entries",
        new RowMapper<Entry>() {
            public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
                Entry entry = new Entry(rs.getString("author"),
                                        rs.getString("title"),
                                        rs.getInt("year"),
                                        rs.getString("journal"),
					rs.getString("media")
                                        );
                return entry;
            }
        });
        model.addAttribute("entries", entries);
        return "biblio";
    }

    @RequestMapping("/addEntry")
    public String addEntry(@RequestParam(value="author", required=true) String a,
			   @RequestParam(value="title", required=true) String t,
			   @RequestParam(value="year", required=true) int y,
			   @RequestParam(value="journal", required=true) String j,
			   @RequestParam(value="media", required=true) String m,
            Model model) {
        jdbcTemplate.update("insert into entries values (?, ?, ?, ?)", a, t, y, j,m);
        return "redirect:biblio"; // back to the biblio view
    }

    @Autowired
	JdbcTemplate jdbcTemplate;
    
    private static class Entry {
        final public String author;
        final public String title;
        final public int year;
        final public String journal;
	final public String media;

        Entry(String a, String t, int y, String j, String m) {
            author = a;
            title = t;
            year = y;
            journal = j;
	    media = m;
        }
    }
}
