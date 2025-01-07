package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Playlist;
import models.Song;

public class PlaylistDAO {
    private DatabaseConnection dbConnection = new DatabaseConnection();

    public void insertPlaylist(Playlist playlist) {
        try (Connection connection = dbConnection.getConnection()) {
            
            String query = "INSERT INTO playlists (name) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, playlist.getName());
            statement.executeUpdate();

            
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int playlistId = generatedKeys.getInt(1);

                
                String insertSongQuery = "INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?, ?)";
                PreparedStatement insertSongStatement = connection.prepareStatement(insertSongQuery);
                for (Song song : playlist.getSongs()) {
                    insertSongStatement.setInt(1, playlistId);
                    insertSongStatement.setInt(2, song.getId());
                    insertSongStatement.addBatch();
                }
                insertSongStatement.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Playlist> getAllPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection()) {
            String query = "SELECT * FROM playlists";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Playlist playlist = new Playlist(resultSet.getString("name"));
                playlist.setId(resultSet.getInt("id"));
                playlist.setSongs(getSongsForPlaylist(playlist.getId()));
                playlists.add(playlist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlists;
    }

    private List<Song> getSongsForPlaylist(int playlistId) {
        List<Song> songs = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection()) {
            String query = "SELECT s.id, s.title, s.artist, s.genre, s.filePath FROM songs s " +
                           "JOIN playlist_songs ps ON s.id = ps.song_id WHERE ps.playlist_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Song song = new Song(resultSet.getString("title"),
                                     resultSet.getString("artist"),
                                     resultSet.getString("genre"),
                                     resultSet.getString("filepath") );
                song.setId(resultSet.getInt("id"));
                song.setFilePath(resultSet.getString("filePath"));
                songs.add(song);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }
}
