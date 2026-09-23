package lab13;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


import lab11.ArtistQueue;

//Class is for use with one single artist object




public class Artist implements Comparable<Artist>{
	protected String id;
	protected int popularity;
	protected String genres;
	protected double followers;
	protected String name;
	protected static Artist [] artistList;
	
            
    // Creates artist given full line of details  
	public Artist (String temp) {
		//System.out.println(temp);
		String [] one = temp.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)",5);
		if (one[1].trim() == "") { 
        	one[1]="0";
        }
		this.id = one[0];
		//this.followers = Double.parseDouble(one[1]);
		this.followers = Double.parseDouble(one[1]);
		//this.followers = Double.parseDouble(one[1].replaceAll(",", "").trim());
		this.genres = one[2];
		this.name = one[3];
		this.popularity = Integer.parseInt(one[4]);
		
		
	}
	// Creates artist with details entered separately
	public Artist (String newId,double newFollowers ,String newGenre, String newName , int newPopularity) {
		this.id = newId;
		this.popularity = newPopularity;
		this.genres  = newGenre;
		this.followers = newFollowers;
		this.name = newName;
	}
	
	
	
	
	
	public String getId() {
		return id;
	}
	
	public String getGenre() {
		return genres;
	}


	public void setId(String id) {
		this.id = id;
	}


	public int getPopularity() {
		return popularity;
	}


	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}


	public double getFollowers() {
		return followers;
	}


	public void setFollowers(double followers) {
		this.followers = followers;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	// Converts a double to a integer
	public static int convert (double count) {
		return (int) count;
	}
	
	
	
	public String toString() {
		return "artist [id=" + id + ", popularity=" + popularity + ", followers=" + followers +  ", genre=" + genres + ", name=" + name + "]";
	}
	
	
	public int compareTo(Artist a) {
		int compare = this.getId().compareTo(a.getId());
		
		if (compare == 0) {
			return this.getName().compareTo(a.getName());
		}else {
			return this.getId().compareTo(a.getId());
		}
		
	}
	public boolean equals  (Artist a) {
		if (this.getId().compareTo(a.getId()) == 0 && this.getName().compareTo(a.getName()) == 0) {
			return true;
		}else {
			return false;
		}
	}
	
}