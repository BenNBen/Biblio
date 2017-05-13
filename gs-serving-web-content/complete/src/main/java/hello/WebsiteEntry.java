package hello;
public class WebsiteEntry extends Entry{
	private String articleTitle;
	private String authorFirstName;
	private String authorLastName;
	private String websiteTitle;
	private String publisher;
	private String url;
	private String publishingDate;
	private String accessDate;
	
	public WebsiteEntry(String article, String authorFN, String authorLN, String website, String publisher, String url, String pDate, String aDate){
		this.articleTitle = article;
		this.authorFirstName = authorFN;
		this.authorLastName = authorLN;
		this.websiteTitle = website;
		this.publisher = publisher;
		this.url = url;
		this.publishingDate = pDate;
		this.accessDate = aDate;
		this.citation = "";
	}
	
	@Override Entry copy(){
		Entry e = new WebsiteEntry(this.articleTitle, this.authorFirstName, this.authorLastName, this.websiteTitle, this.publisher, this.url, this.publishingDate, this.accessDate);
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
		this.citation = this.authorLastName + ", " + this.authorFirstName + ". \"" + this.articleTitle + ".\" " + this.websiteTitle + ". " + this.publisher + ", " + this.publishingDate + ". Web. " + this.accessDate + ". " + this.url + ".";
	}
}
