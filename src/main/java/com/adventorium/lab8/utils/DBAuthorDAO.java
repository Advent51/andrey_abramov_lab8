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
    Statement statement;

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
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT ID, NAME FROM AUTHOR WHERE ID=" + id + ";");
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
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT ID, NAME, GENRE FROM ALBUM WHERE AUTHOR_ID=" + authorID + ";");
            while (rs.next()) {
                albumID = rs.getInt("id");
                String name = rs.getString("name");
                String genre = rs.getString("genre");
                album = new Album(name);
                //album.addAuthor(author);
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
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT ID, NAME, DURATION FROM SONG WHERE ALBUM_ID=" + albumID + ";");
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
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT ID, NAME FROM AUTHOR;");
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
        if(author.getAlbums()==null){
            return -1;
        }
        ++nextAuthorID;
        String sql = "INSERT INTO AUTHOR (ID, NAME) VALUES (" + nextAuthorID + ", '" + author.getName() + "');";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
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
        if(album.getSongs()==null){
            return -1;
        }
        ++nextAlbumID;
        String sql = "INSERT INTO ALBUM VALUES (" +
                nextAlbumID + ", '" + album.getName() + "', '" +
                album.getGenres() + "', " + authorID + ");";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
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
        ++nextSongID;
        String sql = "INSERT INTO SONG VALUES (" + nextSongID + ", '" + song.getName() + "', " +
                (int) song.getDuration() + ", " + albumID + ");";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
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
        String sql = "DELETE FROM AUTHOR WHERE ID=" + id + ";";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
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
        String sql = "UPDATE AUTHOR SET " +
                "NAME=" + author.getName() +
                " WHERE AUTHOR.ID=" + id + ";";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            log.info("Author updated");
            statement.close();
            log.info("Statement closed");
        } catch (SQLException e) {
            log.error("Author not updated or connection closing aborted");
            e.printStackTrace();
        }
    }

    public void createTables() {
        String sql = "CREATE TABLE AUTHOR " +
                "(ID       INT      PRIMARY KEY NOT NULL," +
                "NAME      CHAR(500) NOT NULL);" +
                "CREATE TABLE ALBUM" +
                "(ID        INT     PRIMARY KEY NOT NULL," +
                "NAME       CHAR(500) NOT NULL," +
                "GENRE      CHAR(500)," +
                "AUTHOR_ID  INT     REFERENCES AUTHOR(ID) ON DELETE CASCADE ON UPDATE CASCADE);" +
                "CREATE TABLE SONG" +
                "(ID        INT     PRIMARY KEY NOT NULL," +
                "NAME       CHAR(500) NOT NULL," +
                "DURATION   INT," +
                "ALBUM_ID   INT     REFERENCES ALBUM(ID) ON DELETE CASCADE ON UPDATE CASCADE);";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            log.info("Tables created");
            statement.close();
            log.info("Statement closed");
        } catch (SQLException e) {
            log.error("Tables not created or connection closing aborted");
            e.printStackTrace();
        }
    }

    public void deleteTables() {
        String sql = "DROP TABLE AUTHOR, ALBUM, SONG;";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            log.info("Tables deleted");
            statement.close();
            log.info("Statement closed");
        } catch (SQLException e) {
            log.error("Tables not created or connection closing aborted");
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
            //statement = connection.createStatement();
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
