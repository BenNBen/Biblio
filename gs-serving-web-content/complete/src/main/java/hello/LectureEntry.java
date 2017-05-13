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

public class LectureEntry extends Entry{
	private String presentationTitle;
	private String speakerFirstName;
	private String speakerLastName;
	private String type;
	private String eventTitle;
	private String city;
	private String location;
	private String date;
	
	public LectureEntry(String presentation, String speakerFN, String speakerLN, String type, String event, String city, String location, String date){
		this.presentationTitle = presentation;
		this.speakerFirstName = speakerFN;
		this.speakerLastName = speakerLN;
		this.type = type;
		this.eventTitle = event;
		this.city = city;
		this.location = location;
		this.date = date;
		this.citation = "";
	}
	
	@Override Entry copy(){
		Entry e = new LectureEntry(this.presentationTitle, this.speakerFirstName, this.speakerLastName, this.type, this.eventTitle, this.city, this.location, this.date);
		e.cite();
		return e;
	}
	
	@Override void setName(){
		this.lastName = this.speakerLastName;
	}
	
	@Override void setTitle(){
		this.title = this.presentationTitle;
	}
	
	@Override void organize(){
		this.citation = this.speakerFirstName + ", " + this.speakerLastName + ". \"" + this.presentationTitle + "\". " + this.eventTitle + ". " + this.location + ", " + this.city + ". " + this.date + ". " + this.type + ".";
	}
}
