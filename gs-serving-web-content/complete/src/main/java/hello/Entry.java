package hello;

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
