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
	
	@Override void organize(){
		this.lastName = this.authorLastName;
		this.title = this.articleTitle;
		this.citation = this.authorLastName + ", " + this.authorFirstName + ". \"" + this.articleTitle + ".\" " + this.websiteTitle + ". " + this.publisher + ", " + this.publishingDate + ". Web. " + this.accessDate + ". " + this.url + ".";
	}
}
