package org.Group7_FinalProject.Utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * The Account class includes all fields and methods related to an Account
 */
public class Account {
	
	//Fields for an Account
	private String name;
	private ArrayList<Highscore> highscores;
	
	//Comparator used for sorting an account's highscores
	static public Comparator<Highscore> scoreComparator;
	static {
		scoreComparator = new Comparator<Highscore>() {
			public int compare(Highscore hs1, Highscore hs2) {
				return Integer.compare(hs1.getValue(), hs2.getValue());
			}
		};
	}

	//Default no-arg constructor
	public Account() {
		this("unknown");
	}
	
	//Constructor that accepts one argument
	public Account(String name) {
		this(name, new ArrayList<Highscore>(Collections.nCopies(10, new Highscore(0, name))));
	}
	
	//Constructor that accepts two arguments
	public Account(String name, ArrayList<Highscore> highscores) {
		
		this.name = name;
		this.highscores = highscores;
		Collections.sort(this.highscores, scoreComparator);
		Collections.reverse(this.highscores);
		
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the highscores
	 */
	public ArrayList<Highscore> getHighscores() {
		return highscores;
	}

	/**
	 * @param highscores the highscores to set
	 */
	public void setHighscores(ArrayList<Highscore> highscores) {
		this.highscores = highscores;
	}
	
	//Method to check if two accounts are equal by comparing names
	public Boolean equals(Account acct) {
		if (this.name == acct.getName()) {
			return true;
		}
		else {
			return false;
		}
	}

}