package com.adventorium.lab8.music;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Андрей on 04.06.2016.
 */
@XmlRootElement(name = "Author")
@XmlAccessorType (XmlAccessType.FIELD)
public class Author implements MusicStuff, Serializable {
    //@XmlAnyAttribute
    private String name;
    //@XmlElement(required = true)
    private Collection<Album> albums;

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
        Author other = (Author) obj;
        if (!name.equals(other.name))
            return false;
        if (!albums.equals(other.albums))
            return false;
        return true;
    }

    public Author(String name) {
        this.name = name;
        albums = new HashSet<>();
    }

    public Author(){

    }

    public void addAlbum(Album album) {
        this.albums.add(album);
    }

    public void addAlbum(Collection<Album> albums) {
        this.albums.addAll(albums);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Collection<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Collection<Album> albums) {
        this.albums = albums;
    }
}