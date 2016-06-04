package com.adventorium.lab8.music;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Андрей on 04.06.2016.
 */
@XmlRootElement(name = "Album")
@XmlAccessorType(XmlAccessType.FIELD)
public class Album implements MusicStuff, Serializable {
    //@XmlAnyAttribute
    private String name;
    private Collection<String> genres;
    private Collection<Song> songs;

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Album other = (Album) obj;
        if (!name.equals(other.name))
            return false;
        if (!genres.equals(other.genres))
            return false;
        if (!songs.equals(other.songs))
            return false;
        return true;
    }

    public Album(String name) {
        this.name = name;
        genres = new HashSet<>();
        songs = new HashSet<>();
    }

    public Album(){

    }

    public void addGenre(String genre) {
        this.genres.add(genre);
    }

    public void addGenre(Collection<String> genre) {
        this.genres.addAll(genre);
    }

    public void addAuthor(Author author) {
        author.addAlbum(this);
    }

    public void addAuthor(Collection<Author> authors) {
        for (Author author : authors) {
            author.addAlbum(this);
        }
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }

    public void addSong(Collection<Song> songs) {
        this.songs.addAll(songs);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Collection<Song> getSongs() {
        return songs;
    }

    public void setSongs(Collection<Song> songs) {
        this.songs = songs;
    }

    public Collection<String> getGenres() {
        return genres;
    }
}