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

public class JournalEntry extends Entry{
	private String articleTitle;
	private String authorFirstName;
	private String authorLastName;
	private String journalTitle;
	private String volume;
	private String issue;
	private String publishingYear;
	private String startPage;
	private String endPage;
	private String database;
	private String accessDate;
	
	public JournalEntry(String article, String authorFN, String authorLN, String volume, String journal, String issue, String year, String start, String end, String database, String date){
		this.articleTitle = article;
		this.authorFirstName = authorFN;
		this.authorLastName = authorLN;
		this.volume = volume;
		this.journalTitle = journal;
		this.issue = issue;
		this.publishingYear = year;
		this.startPage = start;
		this.endPage = end;
		this.database = database;
		this.accessDate = date;
		this.citation = "";
	}
	
	@Override Entry copy(){
		Entry e = new JournalEntry(this.articleTitle, this.authorFirstName, this.authorLastName, this.volume, this.journalTitle, this.issue, this.publishingYear, this.startPage, this.endPage, this.database, this.accessDate);
		e.cite();
		return e;
	}
	
	@Override void setName(){
		this.lastName = this.authorLastName;
	}
	
	@Override void setTitle(){
		this.title = this.articleTitle;
	}
	
	@Override void organize(){
		this.citation = this.authorLastName + ", " + this.authorFirstName + ". \"" + this.articleTitle + "\". " + this.journalTitle + ". " + this.volume + "." + this.issue + " (" + this.publishingYear + "): " + this.startPage + "-" + this.endPage + ". " + this.database + ". Web. " + this.accessDate + ".";
	}
}
