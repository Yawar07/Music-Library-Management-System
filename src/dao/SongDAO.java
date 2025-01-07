package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Song;

public class SongDAO {
    private DatabaseConnection dbConnection = new DatabaseConnection();

    public void insertSong(Song song) {
        try (Connection connection = dbConnection.getConnection()) {
            String query = "INSERT INTO songs (title, artist, genre, filepath) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, song.getTitle());
            statement.setString(2, song.getArtist());
            statement.setString(3, song.getGenre());
            statement.setString(4, song.getFilePath());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection()) {
            String query = "SELECT * FROM songs";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Song song = new Song(resultSet.getString("title"),
                                     resultSet.getString("artist"),
                                     resultSet.getString("genre"),
                                     resultSet.getString("filepath"));
                song.setId(resultSet.getInt("id"));
                song.setFilePath(resultSet.getString("filePath"));
                songs.add(song);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public Song getSongByTitle(String title) {
        Song song = null;
        try (Connection connection = dbConnection.getConnection()) {
            String query = "SELECT * FROM songs WHERE title = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                song = new Song(resultSet.getString("title"),
                                resultSet.getString("artist"),
                                resultSet.getString("genre"),
                                 resultSet.getString("filepath"));
                song.setId(resultSet.getInt("id"));
                song.setFilePath(resultSet.getString("filePath"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return song;
    }
}
