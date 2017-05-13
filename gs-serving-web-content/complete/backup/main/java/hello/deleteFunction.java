@RequestMapping("/deleteEntry")
public String addEntry(@RequestParam(value="authorLastName", required=true) String a, @RequestParam(value="title", required=true) String t, Model model) {
    for(Entry entry : this.entries){
    	if(entry.title.compareTo(t) == 0 && entry.lastName.compareTo(a) == 0){
    		if(entry instanceof WebsiteEntry){
    			jdbcTemplate.update("delete from websites where article = ? and authorLastName = ?", a, t);
    		}
    		if(entry instanceof BookEntry){
    			jdbcTemplate.update("delete from books where book = ? and authorLastName = ?", a, t);
    		}
    		if(entry instanceof JournalEntry){
    			jdbcTemplate.update("delete from journals where article = ? and authorLastName = ?", a, t);
    		}
    		if(entry instanceof FilmEntry){
    			jdbcTemplate.update("delete from films where film = ? and directorLastName = ?", a, t);
    		}
    		if(entry instanceof LectureEntry){
    			jdbcTemplate.update("delete from lectures where presentation = ? and speakerLastName = ?", a, t);
    		}
    	}
    }
    return "redirect:biblio"; // back to the biblio view
}
