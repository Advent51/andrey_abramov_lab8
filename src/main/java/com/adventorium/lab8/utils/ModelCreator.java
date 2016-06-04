package com.adventorium.lab8.utils;

import com.adventorium.lab8.music.Album;
import com.adventorium.lab8.music.Author;
import com.adventorium.lab8.music.Song;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Андрей on 04.06.2016.
 */
public class ModelCreator {

    private static final Logger log = Logger.getLogger(ModelCreator.class);

    private ArrayList<Author> authors;
    private ArrayList<Album> albums;
    private ArrayList<Song> songs;
    private ArrayList<String> genres;

    private int numberOfAuthors;
    private int numberOfAlbums;
    private int numberOfSongs;

    public ModelCreator(int numberOfAuthors, int numberOfAlbums, int numberOfSongs, AuthorDAO authorDAO) {
        this.numberOfAuthors = numberOfAuthors;
        this.numberOfAlbums = numberOfAlbums;
        this.numberOfSongs = numberOfSongs;
        authors = new ArrayList<>();
        albums = new ArrayList<>();
        songs = new ArrayList<>();
        genres = new ArrayList<>();
        createModel(authors, albums, songs, genres);
        authors.forEach(authorDAO::addAuthor);
        log.info("Model created");
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    private void createModel(ArrayList<Author> authors, ArrayList<Album> albums,
                                 ArrayList<Song> songs, ArrayList<String> genres) {

        Random random = new Random();

        genres.add("pop");
        genres.add("grindcore");
        genres.add("metal");
        genres.add("sludge");

        for (int i = 0; i < numberOfAuthors; i++) {
            authors.add(new Author("Author " + i));
        }

        for (int i = 0; i < numberOfAlbums; i++) {
            albums.add(new Album("Album " + i));
        }

        for (int i = 0; i < numberOfSongs; i++) {
            songs.add(new Song("Song " + i, random.nextInt(1000)));
        }

        for (Album album : albums) {
            album.addAuthor(authors.get(random.nextInt(numberOfAuthors)));
            int genreNums = random.nextInt(3) + 1;
            for (int i = 0; i < genreNums; i++) {
                album.addGenre(genres.get(random.nextInt(4)));
            }
        }

        for (Song song : songs) {
            song.addAlbum(albums.get(random.nextInt(numberOfAlbums)));
        }
    }
}