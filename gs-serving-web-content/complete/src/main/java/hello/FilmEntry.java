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

public class FilmEntry extends Entry{
	private String filmTitle;
	private String directorFirstName;
	private String directorLastName;
	private String mainPerformers;
	private String studio;
	private String medium;
	private String releaseYear;
	
    public FilmEntry(String film, String directorFN, String directorLN, String actors, String studio, String medium, String year){
	this.filmTitle = film;
	this.directorFirstName = directorFN;
	this.directorLastName = directorLN;
	this.mainPerformers = actors;
	this.studio = studio;
	this.medium = medium;
	this.releaseYear = year;
	this.citation = "";
    }
	
	@Override Entry copy(){
		Entry e = new FilmEntry(this.filmTitle, this.directorFirstName, this.directorLastName, this.mainPerformers, this.studio, this.medium, this.releaseYear);
		e.cite();
		return e;
	}
	
	@Override void setName(){
		this.lastName = this.directorLastName;
	}
	
	@Override void setTitle(){
		this.title = this.filmTitle;
	}
	
	@Override void organize(){
		this.citation = this.filmTitle + ". Dir. " + this.directorFirstName + ", " + this.directorLastName + ". Perf. " + this.mainPerformers + ". " + this.studio + ", " + this.releaseYear + ". " + this.medium + ".";
	}
}
