package com.adventorium.lab8.music;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Андрей on 04.06.2016.
 */
@XmlRootElement(name = "Song")
@XmlAccessorType(XmlAccessType.FIELD)
public class Song implements MusicStuff, Serializable {
    //@XmlAnyAttribute
    private String name;
    private long duration;

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
        Song other = (Song) obj;
        if (!name.equals(other.name))
            return false;
        if (duration != other.duration)
            return false;
        return true;
    }

    public Song(String name) {
        this.name = name;
    }

    public Song(String name, long duration) {
        this.name = name;
        this.duration = duration;
    }

    public Song(){

    }

    public void addAlbum(Album album) {
        album.addSong(this);
    }

    public void addAlbum(Collection<Album> albums) {
        for (Album album : albums) {
            album.addSong(this);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void addDuration(int duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return this.duration;
    }
}