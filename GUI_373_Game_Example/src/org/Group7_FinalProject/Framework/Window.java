package org.Group7_FinalProject.Framework;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import javax.swing.*;

/*
 * The Window class contains all fields and methods related to a Window
 * The Window is the JFrame that contains all graphics related to the Game
 */
public class Window extends JFrame {

	//Fields for a Window
	private Game game;
	private JPanel mainPanel;
   	private CardLayout cl;
   	private HashMap<String, GameScreen> gameScreenList;
   	private GameScreen currentScreen;
   	
   	//Default no-arg constructor
   	public Window() {
   		this(new Game(), 700, 500);
   	}

   	//Constructor that requires three arguments
   	public Window(Game g, Integer width, Integer height) {
	   
		//Every window belongs to a Game
		this.game = g;
		//Initialize the list of GameScreens
		this.gameScreenList = new HashMap<String, GameScreen>();
		
		//Initialize the window
		this.setTitle("Cave Runner");
		this.setSize(width, height);
		  
		//Initialize the game screens
		this.mainPanel = new JPanel();
		gameScreenList.put("Menu Screen", new MenuScreen(this));
		gameScreenList.put("Account Screen", new AccountScreen(this));
		gameScreenList.put("Highscore Screen", new HighscoreScreen(this));
		gameScreenList.put("Running Screen", new RunningScreen(this));
		
		//Create the CardLayout, which will allow switching between gamescreens
		this.cl = new CardLayout();
		this.mainPanel.setLayout(cl);
		  
		//Add the game screens to the CardLayout
		this.mainPanel.add(gameScreenList.get("Menu Screen"), "Menu Screen");
		this.mainPanel.add(gameScreenList.get("Account Screen"), "Account Screen");
		this.mainPanel.add(gameScreenList.get("Highscore Screen"), "Highscore Screen");
		this.mainPanel.add(gameScreenList.get("Running Screen"), "Running Screen");
		
		//Setup and show the JFrame
		this.add(mainPanel);
		//Make sure of a clean exit if the JFrame is closed
		this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                game.terminate();
            }
        });
		
		this.setVisible(true);
		
   	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * @return the mainPanel
	 */
	public JPanel getMainPanel() {
		return mainPanel;
	}

	/**
	 * @param mainPanel the mainPanel to set
	 */
	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	/**
	 * @return the cl
	 */
	public CardLayout getCl() {
		return cl;
	}

	/**
	 * @param cl the cl to set
	 */
	public void setCl(CardLayout cl) {
		this.cl = cl;
	}

	/**
	 * @return the gameScreenList
	 */
	public HashMap<String, GameScreen> getGameScreenList() {
		return gameScreenList;
	}

	/**
	 * @param gameScreenList the gameScreenList to set
	 */
	public void setGameScreenList(HashMap<String, GameScreen> gameScreenList) {
		this.gameScreenList = gameScreenList;
	}

	/**
	 * @return the currentScreen
	 */
	public GameScreen getCurrentScreen() {
		return currentScreen;
	}

	/**
	 * @param currentScreen the currentScreen to set
	 */
	public void setCurrentScreen(GameScreen currentScreen) {
		this.currentScreen = currentScreen;
	}


}