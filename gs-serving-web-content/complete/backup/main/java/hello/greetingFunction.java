private List<Entry> entries;
@RequestMapping("/biblio")
public String greeting(Model model) {
    List<Entry> films = this.jdbcTemplate.query(
    "select film, directorFirstName, directorLastName, actors, studio, medium, year from films",
    new RowMapper<Entry>() {
        public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
            Entry entry = new FilmEntry(rs.getString("film"),
                                    rs.getString("directorFirstName"),
                                    rs.getString("directorLastName"),
                                    rs.getString("actors"),
                                    rs.getString("studio"),
                                    rs.getString("medium"),
                                    rs.getString("year")
                                    );
            entry.cite();
            return entry;
        }
    });
    List<Entry> books = this.jdbcTemplate.query(
    "select book, authorFirstName, authorLastName, volume, edition, publisher, city, year from books",
    new RowMapper<Entry>() {
        public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
            Entry entry = new BookEntry(rs.getString("book"),
                                    rs.getString("authorFirstName"),
                                    rs.getString("authorLastName"),
                                    rs.getString("volume"),
                                    rs.getString("edition"),
                                    rs.getString("publisher"),
                                    rs.getString("city"),
                                    rs.getString("year")
                                    );
            entry.cite();
            return entry;
        }
    });
    List<Entry> journals = this.jdbcTemplate.query(
    "select article, authorFirstName, authorLastName, journal, volume, issue, year, startPage, endPage, database, accessDate from journals",
    new RowMapper<Entry>() {
        public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
            Entry entry = new JournalEntry(rs.getString("article"),
                                    rs.getString("authorFirstName"),
                                    rs.getString("authorLastName"),
                                    rs.getString("volume"),
                                    rs.getString("journal"),
                                    rs.getString("issue"),
                                    rs.getString("year"),
                                    rs.getString("startDate"),
                                    rs.getString("endDate"),
                                    rs.getString("database"),
                                    rs.getString("accessDate")
                                    );
            entry.cite();
            return entry;
        }
    });
    List<Entry> lectures = this.jdbcTemplate.query(
    "select presentation, speakerFirstName, speakerLastName, type, event, city, location, date from lectures",
    new RowMapper<Entry>() {
        public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
            Entry entry = new LectureEntry(rs.getString("presentation"),
                                        rs.getString("speakerFirstName"),
                                        rs.getString("speakerLastName"),
                                        rs.getString("type"),
                                        rs.getString("event"),
                                        rs.getString("city"),
                                        rs.getString("location"),
                                        rs.getString("date")
                                        );
            entry.cite();
            return entry;
        }
    });
    List<Entry> websites = this.jdbcTemplate.query(
    "select article, authorFirstName, authorLastName, website, publisher, url, publishDate, accessDate from websites",
    new RowMapper<Entry>() {
        public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
            Entry entry = new WebsiteEntry(rs.getString("article"),
                                        rs.getString("authorFirstName"),
                                        rs.getString("authorLastName"),
                                        rs.getString("website"),
                                        rs.getString("publisher"),
                                        rs.getString("url"),
                                        rs.getString("publishDate"),
                                        rs.getString("accessDate")
                                        );
            entry.cite();
            return entry;
        }
    });
    List<Entry> entries = new ArrayList<Entry>(films);
    entries.addAll(books);
    entries.addAll(journals);
    entries.addAll(lectures);
    entries.addAll(websites);
    Comparator<Entry> comp = (Entry a, Entry b) -> {
        return a.citation.compareTo(b.citation);
    };
    Collections.sort(entries, comp);
    model.addAttribute("entries", entries);
    this.entries = entries;
    return "biblio";
}
