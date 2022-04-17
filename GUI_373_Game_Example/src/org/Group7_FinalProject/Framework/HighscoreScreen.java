package org.Group7_FinalProject.Framework;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import org.Group7_FinalProject.Utilities.ScrollableTable;

/*
 * The HighscoreScreen class includes all fields and methods related to a HighscoreScreen
 */
public final class HighscoreScreen extends GameScreen {

	//Fields for a HighscoreScreen
	private JButton menubtn;
	private JButton togglebtn;
	private ScrollableTable highscoreTable;
	
	//Default no-arg constructor
	public HighscoreScreen() {
		this(new Window());
	}
	
	//Constructor that requires one argument
	public HighscoreScreen(Window w) {
		
		super(w, new ImageIcon("src/resources/background_highscore.jpeg"));
		
		//Create a menu button
		menubtn = new JButton("Menu");
		menubtn.setFont(new Font("Arial", Font.BOLD, 20));
		menubtn.setBounds(464,435,80,50); 
		menubtn.setContentAreaFilled(false);
		menubtn.setBorderPainted(false);
		menubtn.setBorder(new LineBorder(Color.white));
		menubtn.setForeground(Color.white);
		menubtn.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mousePressed(MouseEvent e) {
		    	menubtn.setBorderPainted(true);
		    }
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	menubtn.setBorderPainted(false);
		    }
		});
		menubtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setCurrentScreen(w.getGameScreenList().get("Menu Screen"));
			}
		});
		add(menubtn);
		
		//Displays Alltime scores first
		String title[] = {"Account", "Score"};
		//load in highscores into a double array & create Jtable
		highscoreTable = new ScrollableTable(title, window.getGame().getAllTimeHighscores());
		highscoreTable.setFont(new Font("Arial", Font.BOLD, 26));
		highscoreTable.setBounds(585,240,300,180); 
		highscoreTable.setBorder(new LineBorder(Color.white));
		highscoreTable.setForeground(Color.white);
		add(highscoreTable);
		
		//Create a toggle button to switch between personal and all-time highscores display
		togglebtn = new JButton("Personal Highscores");
		togglebtn.setFont(new Font("Arial", Font.BOLD, 24));
		togglebtn.setBounds(185,265,260,80); 
		togglebtn.setContentAreaFilled(false);
		togglebtn.setBorderPainted(true);
		togglebtn.setBorder(new LineBorder(Color.white, 2));
		togglebtn.setForeground(Color.white);
		togglebtn.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mousePressed(MouseEvent e) {
		    	//togglebtn.setBorderPainted(true);
		    	togglebtn.setBorder(new LineBorder(Color.black, 2));
		    }
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	//togglebtn.setBorderPainted(false);
		    	togglebtn.setBorder(new LineBorder(Color.white, 2));
		    }
		});
		togglebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (togglebtn.getText().equals("All-time Highscores")) {
					togglebtn.setText("Personal Highscores");
					//Show all-time scores
					String title[] = {"Account", "Score"};
					highscoreTable.refreshData(title, window.getGame().getAllTimeHighscores());
				}
				else {
					togglebtn.setText("All-time Highscores");
					highscoreTable.emptyData();
					//show personal scores
					String title[] = {"Highscores for " + window.getGame().getCurrAccount().getName()};
					highscoreTable.refreshData(title, window.getGame().getPersonalScores());
				}
			}
		});
		add(togglebtn);
		setLayout(null);
		
	}

	/**
	 * @return the menubtn
	 */
	public JButton getMenubtn() {
		return menubtn;
	}

	/**
	 * @param menubtn the menubtn to set
	 */
	public void setMenubtn(JButton menubtn) {
		this.menubtn = menubtn;
	}

	/**
	 * @return the togglebtn
	 */
	public JButton getTogglebtn() {
		return togglebtn;
	}

	/**
	 * @param togglebtn the togglebtn to set
	 */
	public void setTogglebtn(JButton togglebtn) {
		this.togglebtn = togglebtn;
	}

	/**
	 * @return the highscoreTable
	 */
	public ScrollableTable getHighscoreTable() {
		return highscoreTable;
	}

	/**
	 * @param highscoreTable the highscoreTable to set
	 */
	public void setHighscoreTable(ScrollableTable highscoreTable) {
		this.highscoreTable = highscoreTable;
	}
	
}
