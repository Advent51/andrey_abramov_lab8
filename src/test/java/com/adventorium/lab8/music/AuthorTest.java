package com.adventorium.lab8.music;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Андрей on 04.06.2016.
 */
public class AuthorTest {
    @Test
    public void equals() throws Exception {
        Author au1 = new Author("Author1");
        Author au2 = new Author("Author2");
        Assert.assertEquals(false, au1.equals(au2));
    }

    @Ignore
    @Test
    public void getAlbums() throws Exception {

    }

}