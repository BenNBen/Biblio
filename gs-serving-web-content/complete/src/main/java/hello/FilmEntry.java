public class FilmEntry extends Entry{
	private String filmTitle;
	private String directorFirstName;
	private String directorLastName;
	private String mainPerformers;
	private String studio;
	private String medium;
	private String releaseYear;
	
	public FilmEntry(String film, String directorFN, String directorLN, String actors, String studio, String medium, String year){
		this.filmTitle = film;
		this.directorFirstName = directorFN;
		this.directorLastName = directorLN;
		this.mainPerformers = actors;
		this.studio = studio;
		this.medium = medium;
		this.releaseYear = year;
		this.citation = "";
	}
	
	@Override void organize(){
		this.lastName = this.directorLastName;
		this.title = this.filmTitle;
		this.citation = this.filmTitle + ". Dir. " + this.directorFirstName + ", " + this.directorLastName + ". Perf. " + this.mainPerformers + ". " + this.studio + ", " + this.releaseYear + ". " + this.medium + ".";
	}
}
