public abstract class Entry{
	public String citation;
	public String lastName;
	public String title;
	abstract void organize();
	public void cite(){
		organize();
	}
}
