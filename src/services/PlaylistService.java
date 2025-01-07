package services;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import dao.PlaylistDAO;
import models.Playlist;
import models.Song;

public class PlaylistService {
    private PlaylistDAO playlistDAO = new PlaylistDAO();
    private SongService songService = new SongService();
    private Scanner scanner = new Scanner(System.in);

    public void createPlaylist() {
        System.out.print("Enter Playlist Name: ");
        String name = scanner.nextLine();
        Playlist playlist = new Playlist(name);

        List<Song> songs = new ArrayList<>();
        String addMore;
        do {
            songService.viewSongs();
            System.out.print("Enter the title of the song to add to playlist: ");
            String title = scanner.nextLine();
            Song song = songService.getSongByTitle(title);
            if (song != null) {
                songs.add(song);
            } else {
                System.out.println("Song not found!");
            }
            System.out.print("Add another song? (yes/no): ");
            addMore = scanner.nextLine();
        } while (addMore.equalsIgnoreCase("yes"));

        playlist.setSongs(songs);
        playlistDAO.insertPlaylist(playlist);
        System.out.println("Playlist created successfully!");
    }

    public void viewPlaylists() {
        List<Playlist> playlists = playlistDAO.getAllPlaylists();
        System.out.println("\n--- List of Playlists ---");
        for (Playlist playlist : playlists) {
            System.out.println("Playlist: " + playlist.getName());
            System.out.println("Songs:");
            for (Song song : playlist.getSongs()) {
                System.out.println("  - " + song.getTitle() + " by " + song.getArtist());
            }
        }
    }
}
