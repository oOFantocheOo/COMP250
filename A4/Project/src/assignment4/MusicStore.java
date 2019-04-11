//260832483 Yuyao Zhang

package assignment4;

import javax.xml.soap.SAAJMetaFactory;
import java.util.ArrayList;

public class MusicStore {
    //ADD YOUR CODE BELOW HERE
    MyHashTable<String,Song> SongTitle;
    MyHashTable<String,ArrayList<Song>> SongArtist;
    MyHashTable<Integer, ArrayList<Song>> SongYear;
    //ADD YOUR CODE ABOVE HERE
    
    
    public MusicStore(ArrayList<Song> songs) {
        //ADD YOUR CODE BELOW  HERE
        SongTitle=new MyHashTable<>(songs.size());
        SongArtist=new MyHashTable<>(songs.size());
        SongYear=new MyHashTable<>(songs.size());
        for (Song s :songs)
            addSong(s);

        //ADD YOUR CODE ABOVE HERE
    }
    
    
    /**
     * Add Song s to this MusicStore
     */
    public void addSong(Song s) {
        // ADD CODE BELOW HERE
        SongTitle.put(s.getTitle(),s);
        if (SongArtist.get(s.getArtist())==null)
            SongArtist.put(s.getArtist(), new ArrayList<>());
        if (SongYear.get(s.getYear())==null)
            SongYear.put(s.getYear(), new ArrayList<>());
        SongArtist.get(s.getArtist()).add(s);
        SongYear.get(s.getYear()).add(s);
        // ADD CODE ABOVE HERE
    }
    
    /**
     * Search this MusicStore for Song by title and return any one song 
     * by that title 
     */
    public Song searchByTitle(String title) {
        //ADD CODE BELOW HERE
        return SongTitle.get(title);
        //ADD CODE ABOVE HERE
    }
    
    /**
     * Search this MusicStore for song by `artist' and return an 
     * ArrayList of all such Songs.
     */
    public ArrayList<Song> searchByArtist(String artist) {
        //ADD CODE BELOW HERE
        return SongArtist.get(artist);
        //ADD CODE ABOVE HERE
    }
    
    /**
     * Search this MusicSotre for all songs from a `year'
     *  and return an ArrayList of all such  Songs  
     */
    public ArrayList<Song> searchByYear(Integer year) {
        //ADD CODE BELOW HERE
        return SongYear.get(year);
        //ADD CODE ABOVE HERE
        
    }
}
