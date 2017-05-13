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
	private String bookTitle;
	private String authorFirstName;
	private String authorLastName;
	private String volume;
	private String edition;
	private String publisher;
	private String publisherCity;
	private String publishingYear;
	
	public BookEntry(String book, String authorFN, String authorLN, String volume, String edition, String publisher, String pCity, String pYear){
		this.bookTitle = book;
		this.authorFirstName = authorFN;
		this.authorLastName = authorLN;
		this.volume = volume;
		this.publisher = publisher;
		this.edition = edition;
		this.publishingYear = pYear;
		this.publisherCity = pCity;
		this.citation = "";
	}
	
	@Override Entry copy(){
		Entry e = new BookEntry(this.bookTitle, this.authorFirstName, this.authorLastName, this.volume, this.edition, this.publisher, this.publisherCity, this.publishingYear);
		e.cite();
		return e;
	}
	
	@Override void setName(){
		this.lastName = this.authorLastName;
	}
	
	@Override void setTitle(){
		this.title = this.bookTitle;
	}
	
	@Override void organize(){
		if(this.volume.equals("") && this.edition.equals("")){
			this.citation = this.authorLastName + ", " + this.authorFirstName + ". " + this.bookTitle + ". " + this.publisherCity + ": " + this.publisher + ", " + this.publishingYear + ". Print.";
		} else if(this.volume.equals("")){
			this.citation = this.authorLastName + ", " + this.authorFirstName + ". " + this.bookTitle + ". " + this.edition + " ed. " + this.publisherCity + ": " + this.publisher + ", " + this.publishingYear + ". Print.";
		} else if(this.edition.equals("")){
			this.citation = this.authorLastName + ", " + this.authorFirstName + ". " + this.bookTitle + ". Vol. " + this.volume + ". " + this.publisherCity + ": " + this.publisher + ", " + this.publishingYear + ". Print.";
		} else {
		this.citation = this.authorLastName + ", " + this.authorFirstName + ". " + this.bookTitle + ". " + this.edition + " ed. Vol. " + this.volume + ". " + this.publisherCity + ": " + this.publisher + ", " + this.publishingYear + ". Print.";
		}
	}
}
