package hello;
import java.util.*;

public class Citation{
    
    String author;
    String title;
    String year;
    String journal;
    String citation;

    //    Vector<String> references;
    //references refList = new references();
    
    
    public String getAuthor(){
	return author;
    }

    public String getTitle(){
	return title;
    }

    public String getYear(){
	return year;
    }

    public String getJournal(){
	return journal;
    }

    public void setCitation(String author, String title, String year, String journal){
	String temp;
	temp = author+", "+title+", "+year+", "+journal;
	this.citation = temp;
	//	refList.addElement(temp);
    }

    
    
    
}
