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
	
	@Override void organize(){
		this.lastName = this.speakerLastName;
		this.title = this.presentationTitle;
		this.citation = this.speakerFirstName + ", " + this.speakerLastName + ". \"" + this.presentationTitle + "\". " + this.eventTitle + ". " + this.location + ", " + this.city + ". " + this.date + ". " + this.type + ".";
	}
}
