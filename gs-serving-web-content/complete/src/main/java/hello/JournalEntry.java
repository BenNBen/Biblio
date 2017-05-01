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
	
	@Override void organize(){
		this.lastName = this.authorLastName;
		this.title = this.articleTitle;
		this.citation = this.authorLastName + ", " + this.authorFirstName + ". \"" + this.articleTitle + "\". " + this.journalTitle + ". " + this.volume + "." + this.issue + " (" + this.publishingYear + "): " + this.startPage + "-" + this.endPage + ". " + this.database + ". Web. " + this.accessDate + ".";
	}
}
