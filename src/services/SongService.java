// src/services/SongService.java
package services;

import javazoom.jl.player.Player;
import java.io.FileInputStream;
import java.util.List;
import models.Song;
import dao.SongDAO;

public class SongService {
    private SongDAO songDAO;
    private Player player;
    private Thread playbackThread;

    public SongService() {
        songDAO = new SongDAO();
    }

    public void addSong(Song song) {
        songDAO.insertSong(song);
    }

    public Song getSongByTitle(String title) {
        return songDAO.getSongByTitle(title);
    }

    public List<Song> getAllSongs() {
        return songDAO.getAllSongs();
    }

    public void viewSongs() {
        List<Song> songs = getAllSongs();
        if (songs.isEmpty()) {
            System.out.println("No songs available.");
        } else {
            System.out.printf("%-20s %-20s %-15s\n", "Title", "Artist", "Genre");
            System.out.println("----------------------------------------------------");
            for (Song song : songs) {
                System.out.printf("%-20s %-20s %-15s\n", song.getTitle(), song.getArtist(), song.getGenre());
            }
        }
    }

    public void playSong(String title) {
        Song song = getSongByTitle(title);
        if (song != null) {
            playSongFile(song.getFilePath());
            System.out.println("Now playing: " + song.getTitle() + " by " + song.getArtist());
        } else {
            System.out.println("Song not found.");
        }
    }

    public void playSongFile(String filePath) {
        stopSongFile();  // Stop any currently playing song before starting a new one
        playbackThread = new Thread(() -> {
            try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
                player = new Player(fileInputStream);
                System.out.println("Playing song...");
                player.play();
            } catch (Exception e) {
                System.out.println("Could not play the song.");
                e.printStackTrace();
            }
        });
        playbackThread.start();
    }

    public void stopSongFile() {
        if (player != null) {
            player.close();  // Stop the player
            if (playbackThread != null) {
                playbackThread.interrupt();
            }
            System.out.println("Song stopped.");
        }
    }
}
