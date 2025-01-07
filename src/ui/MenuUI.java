// src/ui/MenuUI.java
package ui;

import java.util.Scanner;
import java.util.List;

import models.Song;
import services.SongService;
import services.PlaylistService;
import utils.ASCIIArt;

public class MenuUI {
    private SongService songService = new SongService();
    private PlaylistService playlistService = new PlaylistService();
    private Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        while (true) {
            ASCIIArt.display("Music Library 🎶");
            System.out.println("\n--- Music Library Management System ---");
            System.out.println("1. Add Song");
            System.out.println("2. View Songs");
            System.out.println("3. Play Song by Title");
            System.out.println("4. Play Song from Library");
            System.out.println("5. Stop Song");
            System.out.println("6. Create Playlist");
            System.out.println("7. View Playlists");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1: // Add Song
                    ASCIIArt.displaySection("Add Song");
                    System.out.print("Enter Song Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Artist: ");
                    String artist = scanner.nextLine();
                    System.out.print("Enter Genre: ");
                    String genre = scanner.nextLine();
                    System.out.print("Enter File Path: ");
                    String filepath = scanner.nextLine();

                    Song newSong = new Song(title, artist, genre, filepath);
                    songService.addSong(newSong);
                    System.out.println("Song added successfully!");
                    break;

                case 2: // View Songs
                    songService.viewSongs();
                    break;

                case 3: // Play Song by Title (existing option)
                    ASCIIArt.displaySection("Play Song by Title");
                    System.out.print("Enter Song Title: ");
                    String songTitle = scanner.nextLine();
                    songService.playSong(songTitle);
                    break;

                case 4: // Play Song from Library (new option)
                    ASCIIArt.displaySection("Play Song from Library");
                    List<Song> songs = songService.getAllSongs();
                    
                    if (songs.isEmpty()) {
                        System.out.println("No songs available.");
                    } else {
                        System.out.println("\nAvailable Songs:");
                        for (Song song : songs) {
                            System.out.printf("- %s by %s\n", song.getTitle(), song.getArtist());
                        }

                        System.out.print("Enter the title of the song to play: ");
                        String selectedTitle = scanner.nextLine();
                        songService.playSong(selectedTitle);
                    }
                    break;

                case 5: // Stop Song
                    songService.stopSongFile();
                    break;

                case 6: // Create Playlist
                    playlistService.createPlaylist();
                    break;

                case 7: // View Playlists
                    playlistService.viewPlaylists();
                    break;

                case 8: // Exit
                    System.out.println("Exiting... Thank you for using Music Library!");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
