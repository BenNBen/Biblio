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

public abstract class Entry{
    public String citation;
    public String lastName;
    public String title;
    abstract void organize();
    abstract void setName();
    abstract void setTitle();
    abstract Entry copy();
    public Entry cloneEntry(){
	return copy();
    }
    public void cite(){
	setName();
	setTitle();
	organize();
    }
}
