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


public class LectureEntry extends Entry{
	private String presentationTitle;
	private String speakerFirstName;
	private String speakerLastName;
	private String genre;
	private String eventTitle;
	private String city;
	private String location;
	private String jDate;
	
	public LectureEntry(String presentation, String speakerFN, String speakerLN, String type, String event, String city, String location, String date){
		this.presentationTitle = presentation;
		this.speakerFirstName = speakerFN;
		this.speakerLastName = speakerLN;
		this.genre = type;
		this.eventTitle = event;
		this.city = city;
		this.location = location;
		this.jDate = date;
		this.citation = "";
	}
	
	@Override void organize(){
	    this.lastName = this.speakerLastName;
	    this.title = this.presentationTitle;
	    this.citation = this.speakerFirstName + ", " + this.speakerLastName + ". \"" + this.presentationTitle + "\". " + this.eventTitle + ". " + this.location + ", " + this.city + ". " + this.jDate + ". " + this.genre + ".";
	}
}
