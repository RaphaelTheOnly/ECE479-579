package org.Group7_FinalProject.Framework;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;

/*
 * The MenuScreen class includes all fields and methods related to a MenuScreen
 */
public final class MenuScreen extends GameScreen {
	
	//Fields for a MenuScreen
	private JButton runningbtn;
	private JButton accountbtn;
	private JButton highscorebtn;
	private JButton exitbtn;
	
	//Default no-arg constructor
	public MenuScreen() {
		this(new Window());
	}

	//Constructor that requires one argument
	public MenuScreen(Window w) {
		
		super(w, new ImageIcon("src/resources/background_menu.jpeg"));
		
		//Create a button to go to the running screen
		String text = "<html>" + "Start" + "<br>" + "Game"
				 + "</html>";
		runningbtn = new JButton(text);
		runningbtn.setFont(new Font("Arial", Font.BOLD, 18));
		runningbtn.setBounds(162,396,100,50); 
		runningbtn.setContentAreaFilled(false);
		runningbtn.setBorderPainted(false);
		runningbtn.setBorder(new LineBorder(Color.black));
		runningbtn.setForeground(Color.black);
		runningbtn.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mousePressed(MouseEvent e) {
		    	runningbtn.setBorderPainted(true);
		    }
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	runningbtn.setBorderPainted(false);
		    }
		});
		runningbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setCurrentScreen(w.getGameScreenList().get("Running Screen"));
			}
		});
		this.add(runningbtn);
				
		//Create a button to go to the account screen
		text = "<html>" + "Change" + "<br>" + "Account"
				 + "</html>";
		accountbtn = new JButton(text);
		accountbtn.setFont(new Font("Arial", Font.BOLD, 18));
		accountbtn.setBounds(357,396,100,50); 
		accountbtn.setContentAreaFilled(false);
		accountbtn.setBorderPainted(false);
		accountbtn.setBorder(new LineBorder(Color.black));
		//accountbtn.setBorderPainted(false);
		//accountbtn.setBackground(Color.orange);
		accountbtn.setForeground(Color.black);
		accountbtn.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mousePressed(MouseEvent e) {
		    	accountbtn.setBorderPainted(true);
		    }
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	accountbtn.setBorderPainted(false);
		    }
		});
		accountbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setCurrentScreen(w.getGameScreenList().get("Account Screen"));
			}
		});
		add(accountbtn);
		
		//Create a button to go to the highscore screen
		text = "<html>" + "&nbsp &nbsp &nbsp View" + "<br>" + "Highscores"
				 + "</html>";
		highscorebtn = new JButton(text);
		highscorebtn.setFont(new Font("Arial", Font.BOLD, 18));
		highscorebtn.setBounds(556,396,100,50); 
		highscorebtn.setContentAreaFilled(false);
		highscorebtn.setBorderPainted(false);
		highscorebtn.setBorder(new LineBorder(Color.black));
		highscorebtn.setForeground(Color.black);
		highscorebtn.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mousePressed(MouseEvent e) {
		    	highscorebtn.setBorderPainted(true);
		    }
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	highscorebtn.setBorderPainted(false);
		    }
		});
		highscorebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setCurrentScreen(w.getGameScreenList().get("Highscore Screen"));
			}
		});
		add(highscorebtn);
		
		//Create a button to exit the game
		exitbtn = new JButton("Exit");
		exitbtn.setFont(new Font("Arial", Font.BOLD, 20));
		exitbtn.setBounds(745,430,100,50); 
		//exitbtn.setOpaque(true);
		exitbtn.setContentAreaFilled(false);
		exitbtn.setBorderPainted(false);
		exitbtn.setBorder(new LineBorder(Color.black));
		exitbtn.setForeground(Color.black);
		exitbtn.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mousePressed(MouseEvent e) {
		    	exitbtn.setBorderPainted(true);
		    }
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	exitbtn.setBorderPainted(false);
		    }
		});
		exitbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Confirm the user's choice of action with a yes/no popup
				int result = JOptionPane.showConfirmDialog(window, "Are you sure you want to exit the game?", "Exit Cave Runner", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					window.getGame().terminate();
				}
			}
		});
		this.add(exitbtn);
		
		this.setLayout(null);
		
	}

	/**
	 * @return the runningbtn
	 */
	public JButton getRunningbtn() {
		return runningbtn;
	}

	/**
	 * @param runningbtn the runningbtn to set
	 */
	public void setRunningbtn(JButton runningbtn) {
		this.runningbtn = runningbtn;
	}

	/**
	 * @return the accountbtn
	 */
	public JButton getAccountbtn() {
		return accountbtn;
	}

	/**
	 * @param accountbtn the accountbtn to set
	 */
	public void setAccountbtn(JButton accountbtn) {
		this.accountbtn = accountbtn;
	}

	/**
	 * @return the highscorebtn
	 */
	public JButton getHighscorebtn() {
		return highscorebtn;
	}

	/**
	 * @param highscorebtn the highscorebtn to set
	 */
	public void setHighscorebtn(JButton highscorebtn) {
		this.highscorebtn = highscorebtn;
	}

	/**
	 * @return the exitbtn
	 */
	public JButton getExitbtn() {
		return exitbtn;
	}

	/**
	 * @param exitbtn the exitbtn to set
	 */
	public void setExitbtn(JButton exitbtn) {
		this.exitbtn = exitbtn;
	}

}
