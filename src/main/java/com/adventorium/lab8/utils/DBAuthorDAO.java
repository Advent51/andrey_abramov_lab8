package com.adventorium.lab8.utils;

import com.adventorium.lab8.music.Album;
import com.adventorium.lab8.music.Author;
import com.adventorium.lab8.music.Song;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

/**
 * Created by Андрей on 10.06.2016.
 */
public class DBAuthorDAO implements AuthorDAO {

    private static final Logger log = Logger.getLogger(DBAuthorDAO.class);

    private int nextAuthorID;
    private int nextAlbumID;
    private int nextSongID;
    private int nextGenreID;

    Connection connection;
    PreparedStatement statement;

    public DBAuthorDAO() {
        nextAuthorID = 0;
        nextAlbumID = 0;
        nextSongID = 0;
        nextGenreID = 0;
    }

    @Override
    public Author getAuthor(int id) {
        Author author = null;
        try {
            String request = "SELECT ID, NAME FROM AUTHOR WHERE ID=?";
            statement = connection.prepareStatement(request);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int authorID = rs.getInt("id");
                String name = rs.getString("name");
                author = new Author(name);
            }
            rs.close();
            statement.close();
            connection.close();
            getAlbum(id, author);
        } catch (SQLException e) {
            log.error("Author not added or connection closing aborted");
            e.printStackTrace();
        }
        return author;
    }

    public Author getAlbum(int authorID, Author author) {
        Album album = null;
        int albumID;
        try {
            String request = "SELECT ID, NAME, GENRE FROM ALBUM WHERE AUTHOR_ID=?";
            statement = connection.prepareStatement(request);
            statement.setInt(1, authorID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                albumID = rs.getInt("id");
                String name = rs.getString("name");
                String genre = rs.getString("genre");
                album = new Album(name);
                album.addGenre(genre);
                getSong(albumID, album);
                author.addAlbum(album);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            log.error("Album not get or connection closing aborted");
            e.printStackTrace();
        }
        return author;
    }

    public void getSong(int albumID, Album album) {
        Song song = null;
        int songID;
        try {
            String request = "SELECT ID, NAME, DURATION FROM SONG WHERE ALBUM_ID=?";
            statement = connection.prepareStatement(request);
            statement.setInt(1, albumID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                songID = rs.getInt("id");
                String name = rs.getString("name");
                int duration = rs.getInt("duration");
                song = new Song(name);
                song.addDuration(duration);
                album.addSong(song);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            log.error("Song not get or connection closing aborted");
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Author> getAuthors() {
        Collection<Author> retrievedAuthors = new ArrayList<>();
        try {
            String request = "SELECT ID, NAME FROM AUTHOR;";
            statement = connection.prepareStatement(request);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int authorID = rs.getInt("id");
                String name = rs.getString("name");
                Author author = new Author(name);
                getAlbum(authorID, author);
                retrievedAuthors.add(author);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            log.error("Author not added or connection closing aborted");
            e.printStackTrace();
        }
        log.info("Read authors");
        return retrievedAuthors;
    }

    @Override
    public int addAuthor(Author author) {
        if (author.getAlbums() == null) {
            return -1;
        }
        try {
            ++nextAuthorID;
            String sql = "INSERT INTO AUTHOR VALUES (?,?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, nextAuthorID);
            statement.setString(2, author.getName());
            statement.executeUpdate();
            log.info("Author added");
            statement.close();
            log.info("Statement closed");
            author.getAlbums().forEach(album -> addAlbum(album, nextAuthorID));
        } catch (SQLException e) {
            log.error("Author not added or connection closing aborted");
            e.printStackTrace();
        }
        return nextAuthorID;
    }

    public int addAlbum(Album album, int authorID) {
        if (album.getSongs() == null) {
            return -1;
        }
        try {
            ++nextAlbumID;
            String sql = "INSERT INTO ALBUM VALUES (?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, nextAlbumID);
            statement.setString(2, album.getName());
            statement.setString(3, album.getGenres().toString());
            statement.setInt(4, authorID);
            statement.executeUpdate();
            log.info("Album added");
            statement.close();
            log.info("Statement closed");
            album.getSongs().forEach(song -> addSong(song, nextAlbumID));
        } catch (SQLException e) {
            log.error("Album not added or connection closing aborted");
            e.printStackTrace();
        }
        return nextAlbumID;
    }

    public int addSong(Song song, int albumID) {
        try {
            ++nextSongID;
            String sql = "INSERT INTO SONG VALUES (?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, nextSongID);
            statement.setString(2, song.getName());
            statement.setInt(3, (int) song.getDuration());
            statement.setInt(4, albumID);
            statement.executeUpdate();
            log.info("Song added");
            statement.close();
            log.info("Statement closed");
        } catch (SQLException e) {
            log.error("Song not added or connection closing aborted");
            e.printStackTrace();
        }
        return nextSongID;
    }

    @Override
    public boolean deleteAuthor(int id) {
        boolean deleted = false;
        try {
            String sql = "DELETE FROM AUTHOR WHERE ID=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            log.info("Author deleted");
            statement.close();
            log.info("Statement closed");
            deleted = true;
        } catch (SQLException e) {
            log.error("Author not deleted or connection closing aborted");
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public void updateAuthor(int id, Author author) {
        try {
            String sql = "UPDATE AUTHOR SET NAME=? WHERE AUTHOR.ID=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, author.getName());
            statement.setInt(2, id);
            statement.executeUpdate();
            log.info("Author updated");
            statement.close();
            log.info("Statement closed");
        } catch (SQLException e) {
            log.error("Author not updated or connection closing aborted");
            e.printStackTrace();
        }
    }

    public void openConnection() {
        String url = "jdbc:postgresql://localhost:5432/LabPostgre";
        Properties properties = new Properties();
        properties.setProperty("user", "postgres");
        properties.setProperty("password", "123");
        //properties.setProperty("ssl", "false");

        try {
            connection = DriverManager.getConnection(url, properties);
            log.info("DB connected successfully");
        } catch (SQLException e) {
            log.error("DB is not connected");
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            connection.close();
            log.info("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
            log.info("Connection closing abortion");
        }
    }
}
