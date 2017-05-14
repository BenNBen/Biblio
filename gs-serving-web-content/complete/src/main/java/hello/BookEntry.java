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

public class BookEntry extends Entry{
	private String title;
	private String authorFN;
	private String authorLN;
	private String volume;
	private String edition;
	private String publisher;
	private String city;
	private String year;
	
	public BookEntry(String title, String authorFN, String authorLN, String volume, String edition, String publisher, String city, String year){
		this.title = title;
		this.authorFN = authorFN;
		this.authorLN = authorLN;
		this.volume = volume;
		this.publisher = publisher;
		this.edition = edition;
		this.year = year;
		this.city = city;
		this.citation = "";
	}
	
	@Override Entry copy(){
		Entry e = new BookEntry(this.title, this.authorFN, this.authorLN, this.volume, this.edition, this.publisher, this.city, this.year);
		e.cite();
		return e;
	}
	
	@Override void setName(){
		this.lastName = this.authorLN;
	}
	
	@Override void setTitle(){
		this.title = this.title;
	}
	
	@Override void organize(){
		if(this.volume.equals("") && this.edition.equals("")){
			this.citation = this.authorLN + ", " + this.authorFN + ". " + this.title + ". " + this.city + ": " + this.publisher + ", " + this.year + ". Print.";
		} else if(this.volume.equals("")){
			this.citation = this.authorLN + ", " + this.authorFN + ". " + this.title + ". " + this.edition + " ed. " + this.city + ": " + this.publisher + ", " + this.year + ". Print.";
		} else if(this.edition.equals("")){
			this.citation = this.authorLN + ", " + this.authorFN + ". " + this.title + ". Vol. " + this.volume + ". " + this.city + ": " + this.publisher + ", " + this.year + ". Print.";
		} else {
		this.citation = this.authorLN + ", " + this.authorFN + ". " + this.title + ". " + this.edition + " ed. Vol. " + this.volume + ". " + this.city + ": " + this.publisher + ", " + this.year + ". Print.";
		}
	}
}
