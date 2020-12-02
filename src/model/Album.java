/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


/**
 *
 * @author angulo.jorge
 */
public class Album {
    private int albumId;
    private String title = "";
    private int artistId;
    private Artist artist = new Artist();

    public Album() {}
        
    public Album(int albumId, String title, int artistId) {
        this.albumId = albumId;
        this.title = title;
        this.artistId = artistId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }
    
    public Artist getArtist() {
        return artist;
    }
    
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Album{" + "AlbumId=" + albumId + ", Title=" + title + ", ArtistId=" + artistId + '}';
    }
    
}
