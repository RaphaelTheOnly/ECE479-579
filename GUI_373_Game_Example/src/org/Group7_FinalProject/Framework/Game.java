package org.Group7_FinalProject.Framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.Group7_FinalProject.Utilities.Account;
import org.Group7_FinalProject.Utilities.Highscore;

/*
 * The Game class contains all fields and methods related to a Game
 * Primary game flow control is implemented here
 */
public final class Game {
	
	//Fields for a Game
	private Window gameWindow;
	private ArrayList<Account> gameAccounts;
	private Account currAccount;

	//Default no-arg constructor
	public Game() {
		
		//Load in accounts from txt file
		this.gameAccounts = new ArrayList<Account>();
		this.loadAccounts();
		
		//Open the game with the guest account logged in
		this.currAccount = this.gameAccounts.get(0);
		
		//Create a new game window associated with this Game
		this.gameWindow = new Window(this, 1000, 720);
		
		//Start the game on the Menu Screen
		this.gameWindow.setCurrentScreen(gameWindow.getGameScreenList().get("Menu Screen"));
		
	}

	//Method that implements primary game flow control
	public void runGame() {
		
		//Infinite while loop runs until the program termination signal is sent
		while(true) {
			
			//Display the appropriate screen and call the appropriate update function based on currentScreen
			if (gameWindow.getCurrentScreen() == gameWindow.getGameScreenList().get("Menu Screen")) {
				gameWindow.getCl().show(gameWindow.getMainPanel(), "Menu Screen");
				this.updateMenuScreen();
			}
			else if (gameWindow.getCurrentScreen() == gameWindow.getGameScreenList().get("Account Screen")) {
				gameWindow.getCl().show(gameWindow.getMainPanel(), "Account Screen");
				this.updateAccountScreen();
			}
			else if (gameWindow.getCurrentScreen() == gameWindow.getGameScreenList().get("Highscore Screen")) {
				gameWindow.getCl().show(gameWindow.getMainPanel(), "Highscore Screen");
				this.updateHighscoreScreen();
			}
			else if (gameWindow.getCurrentScreen() == gameWindow.getGameScreenList().get("Running Screen")) {
				gameWindow.getCl().show(gameWindow.getMainPanel(), "Running Screen");
				this.updateRunningScreen();
			}
			//In case the program falls off the rails, send the termination signal
			else {
				this.terminate();
			}
			
		}
		
	}
	
	private void updateMenuScreen() {
		//TO DO: Fill out menu screen tasks
	}
	
	private void updateAccountScreen() {
		//TO DO: Fill out account screen tasks
	}
	
	private void updateHighscoreScreen() {
		//TO DO: Fill out highscore screen tasks
	}
	
	private void updateRunningScreen() {
		
		//Start the runner
		((RunningScreen)gameWindow.getGameScreenList().get("Running Screen")).startRunning();
    	
    	//Continue running until the player dies
    	while(((RunningScreen)gameWindow.getGameScreenList().get("Running Screen")).isRunnerDead() == false);
    	
    	//When the player is dead, check to see if the player quit from the pause screen or if they died in the game
    	if((((RunningScreen)gameWindow.getGameScreenList().get("Running Screen")).isRunnerPaused() == true)) {
    		//Game was paused, go back to menu screen without recording highscore
    		gameWindow.setCurrentScreen(gameWindow.getGameScreenList().get("Menu Screen"));
    	}
    	else {
    		//Runner died normally
			//If the current account has set a new highscore, record it
	    	Integer depth = ((RunningScreen)gameWindow.getGameScreenList().get("Running Screen")).getRunner().getDepth();
			boolean newHighscore = false;
			for (Highscore h : gameWindow.getGame().getCurrAccount().getHighscores()) {
				if (depth > h.getValue()) {
					gameWindow.getGame().getCurrAccount().getHighscores().remove(9);
					gameWindow.getGame().getCurrAccount().getHighscores().add(new Highscore(depth, gameWindow.getGame().getCurrAccount().getName()));
					Collections.sort(gameWindow.getGame().getCurrAccount().getHighscores(), Account.scoreComparator);
					Collections.reverse(gameWindow.getGame().getCurrAccount().getHighscores());
					newHighscore = true;
					break;
				}
			}
			//Give the user the option to return to the Menu Screen or play again
			int result;
			if (newHighscore == false)
				result = JOptionPane.showConfirmDialog(gameWindow, "Ouch! You died at a depth of " + depth.toString() + "ft. Do you wish to play again?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			else
				result = JOptionPane.showConfirmDialog(gameWindow, "Ouch! You died at a depth of " + depth.toString() + "ft.\n Congratulations, this is a new top 10 score for your account!\n Do you wish to play again?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				gameWindow.setCurrentScreen(gameWindow.getGameScreenList().get("Running Screen"));
			}
			else {
				gameWindow.setCurrentScreen(gameWindow.getGameScreenList().get("Menu Screen"));
			}
    	}
		
	}
	
	//Method to retreive the scores of the current account
	public Object[][] getPersonalScores() {
		
		Object scores[][] = new Object[10][1];
		
		Collections.sort(currAccount.getHighscores(), Account.scoreComparator);
		Collections.reverse(currAccount.getHighscores());
		
		for (int i = 0; i < 10; i++) {
			scores[i][0] = currAccount.getHighscores().get(i).getValue();
		}
		
		return scores;
		
	}
	
	//Method to retreive the top ten scores of all time
	public Object[][] getAllTimeHighscores() {
		
		Object topTenScores[][] = new Object[10][2];
		ArrayList<Highscore> allScores = new ArrayList<Highscore>();
		
		for (Account a : gameAccounts) {
			for (Highscore h : a.getHighscores()) {
				allScores.add(h);
			}
		}

		Collections.sort(allScores, Account.scoreComparator);
		Collections.reverse(allScores);

		for (int i = 0; i < 10; i++) {
			topTenScores[i][1] = allScores.get(i).getValue();
			topTenScores[i][0] = allScores.get(i).getAccountName();
		}
		
		return topTenScores;
		
	}
	
	//Method that loads in accounts from a txt file
	private void loadAccounts() {
		
		try {
			File accData = new File("src/resources/account_data.txt");
			Scanner scanner = new Scanner(accData);
			while (scanner.hasNextLine()) {
				ArrayList<Highscore> accScores= new ArrayList<Highscore>();
				String name = scanner.nextLine();
				while(scanner.hasNextInt()) {
					Integer score = scanner.nextInt();
					accScores.add(new Highscore(score, name));
				}
				String garbage = scanner.nextLine();
				Account acc = new Account(name, accScores);
				gameAccounts.add(acc);
			}
			scanner.close();
		} catch(FileNotFoundException e) {
			System.out.println("An error occurred in loading the account data");
			e.printStackTrace();
		}
		
	}
	
	//Method that saves accounts to a txt file
	private void saveAccounts() {
		
		try {
			File accData = new File("src/resources/account_data.txt");
			PrintWriter writer = new PrintWriter(accData);
			for(Account acc : gameAccounts) {
				writer.println(acc.getName());
				for(Highscore h : acc.getHighscores()) {
					writer.print(h.getValue().toString() + " ");
				}
				writer.println();
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occured in saving the account data");
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Method that sends the termination signal to the program
	 * All possible ways of closing the program should run through here
	 */
	public void terminate() {
		this.saveAccounts();
		System.exit(0);
	}

	/**
	 * @return the gameWindow
	 */
	public Window getGameWindow() {
		return gameWindow;
	}

	/**
	 * @param gameWindow the gameWindow to set
	 */
	public void setGameWindow(Window gameWindow) {
		this.gameWindow = gameWindow;
	}

	/**
	 * @return the gameAccounts
	 */
	public ArrayList<Account> getGameAccounts() {
		return gameAccounts;
	}

	/**
	 * @param gameAccounts the gameAccounts to set
	 */
	public void setGameAccounts(ArrayList<Account> gameAccounts) {
		this.gameAccounts = gameAccounts;
	}

	/**
	 * @return the currAccount
	 */
	public Account getCurrAccount() {
		return currAccount;
	}

	/**
	 * @param currAccount the currAccount to set
	 */
	public void setCurrAccount(Account currAccount) {
		this.currAccount = currAccount;
	}

}
