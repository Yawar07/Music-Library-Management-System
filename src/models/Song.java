package models;

public class Song {
    private int id;
    private String title;
    private String artist;
    private String genre;
    private String filepath;

    public Song(String title, String artist, String genre ,String filepath) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getFilePath() { return filepath; }
    public void setFilePath(String filepath) { this.filepath = filepath; }
}
