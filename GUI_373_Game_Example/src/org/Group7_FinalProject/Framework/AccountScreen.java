package org.Group7_FinalProject.Framework;

import org.Group7_FinalProject.Utilities.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

/*
 * The AccountScreen class contains all fields and methods related to an AccountScreen
 */
public final class AccountScreen extends GameScreen {
	
	//Fields for an AccountScreen
	private JButton menubtn;
	private JButton selectacctbtn;
	private JButton createacctbtn;
	private JTextField accttxtbox;
	private ScrollableTable accttable;
	private JLabel curracctlabel;
	
	//Default no-arg constructor
	public AccountScreen() {
		this(new Window());
	}

	//Constructor that requires one argument
	public AccountScreen(Window w) {
		
		super(w, new ImageIcon("src/resources/background_account.jpeg"));
		
		//Create a JLabel to display the current account at all times
		curracctlabel = new JLabel("<html>Currently Logged In As:<br>&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp " + window.getGame().getCurrAccount().getName() + "</html>");
		curracctlabel.setFont(new Font("Arial", Font.BOLD, 18));
		curracctlabel.setBounds(176,330,300,50); 
		curracctlabel.setForeground(Color.CYAN);
		add(curracctlabel);
		
		//Create a menu button
		menubtn = new JButton("Menu");
		menubtn.setFont(new Font("Arial", Font.BOLD, 26));
		menubtn.setBounds(456,505,80,50); 
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
				//Check to make sure the user is logged into an account, otherwise they can't play the game
				if (window.getGame().getCurrAccount() == null) {
					JOptionPane.showMessageDialog(window, "Please select or create an account!", "Account Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//If the user has entered a new account name in the textbox but not saved it to create a new account, display a warning
				else if (!accttxtbox.getText().equals("")) {
					//The user can continue without saving the changes, or return to the account screen
					int result = JOptionPane.showConfirmDialog(window, "The account name you entered has not been saved. Continue to menu anyway?", "Return to Menu", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.NO_OPTION) {
						return;
					}
					//If the user does decide to continue, empty the textbox
					accttxtbox.setText(null);
				}
				//All checks have been passed, go to Menu screen
				window.setCurrentScreen(window.getGameScreenList().get("Menu Screen"));
			}
		});
		add(menubtn);
		
		//Create a textbox for the user to enter a new account name in
		accttxtbox = new JTextField("", 20);
		accttxtbox.setFont(new Font("Arial", Font.BOLD, 20));
		accttxtbox.setBounds(400,245,200,35); 
		accttxtbox.setBorder(new LineBorder(Color.blue));
		accttxtbox.setForeground(Color.blue);
		accttxtbox.setBackground(Color.white);
		//Limit account names to 20 characters max
		accttxtbox.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	            if (accttxtbox.getText().length() >= 20 )
	                e.consume();
	        }
	    });
		add(accttxtbox);
				
		//Create a create account button
		String text = "<html>" + "Create New" + "<br>" + "&nbsp Account"
				 + "</html>";
		createacctbtn = new JButton(text);
		createacctbtn.setFont(new Font("Arial", Font.BOLD, 18));
		createacctbtn.setBounds(427,295,150,70); 
		//createacctbtn.setContentAreaFilled(true);
		createacctbtn.setBorderPainted(true);
		createacctbtn.setBorder(new LineBorder(Color.white, 2));
		//createacctbtn.setOpaque(true);
		//createacctbtn.setBackground(Color.white);
		createacctbtn.setForeground(Color.white);
		createacctbtn.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mousePressed(MouseEvent e) {
		    	//createacctbtn.setBorderPainted(true);
		    	createacctbtn.setBorder(new LineBorder(Color.black, 2));
		    }
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	//createacctbtn.setBorderPainted(false);
		    	createacctbtn.setBorder(new LineBorder(Color.white, 2));
		    }
		});
		createacctbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//When the user clicks the createacctbtn, a new account with the name entered in the textbox should be created
				//But first, do some input verification. The name should not contain any non-alpha numeric characters
				Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(accttxtbox.getText());
				boolean b = m.find();
				if (b) {
					JOptionPane.showMessageDialog(window, "The account name you entered is invalid. Account names cannot contain any special characters or spaces.", "Account Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//Next, check to make sure that the textbox is not empty
				if (accttxtbox.getText().equals("")) {
					JOptionPane.showMessageDialog(window, "The account name you entered is invalid. Account names cannot be empty.", "Account Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//Finally, check to make sure the account name has not already been taken
				for (Account a : window.getGame().getGameAccounts()) {
					if (a.getName().equals(accttxtbox.getText())) {
						JOptionPane.showMessageDialog(window, "The account name you entered has already been taken. Please enter a unique account name.", "Account Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				//Input verification has been passed. Add the new account to the game and empty the textbox
				window.getGame().getGameAccounts().add(new Account(accttxtbox.getText()));
				accttxtbox.setText("");
				//Set the current account to the new account and update the JLabel to reflect the change
				window.getGame().setCurrAccount(window.getGame().getGameAccounts().get(window.getGame().getGameAccounts().size() - 1));
				curracctlabel.setText("<html>Currently Logged In As:<br> &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp " + window.getGame().getCurrAccount().getName() + "</html>");
				//Add the new account to the table as well, and set the current selection to the new account
				String[] row = { window.getGame().getCurrAccount().getName() };
				accttable.addRow(row);
				accttable.setSelectedRow(window.getGame().getGameAccounts().size() - 1);
				//Display a message welcoming the new account to the game
				JOptionPane.showMessageDialog(window, "Welcome to Cave Runner, " + window.getGame().getCurrAccount().getName() + "!", "Welcome", JOptionPane.PLAIN_MESSAGE);
			}
		});
		add(createacctbtn);
		
		//Create a table to display the game accounts
		String[][] accounts = new String[window.getGame().getGameAccounts().size()][1];
		for (int i = 0; i < accounts.length; i++) {
			accounts[i][0] = window.getGame().getGameAccounts().get(i).getName();
		}
		String[] head = {"Accounts"};
		this.accttable = new ScrollableTable(head, accounts, 200, 200);
		accttable.setFont(new Font("Arial", Font.BOLD, 18));
		accttable.setBounds(625,245,200,200); 
		accttable.setBorder(new LineBorder(Color.black));
		accttable.setForeground(Color.black);
		accttable.setBackground(Color.orange);
		accttable.setOpaque(true);
		accttable.setBackground(Color.orange);
		add(accttable);
		
		//Create a select account button
		text = "<html>" + "&nbsp Select" + "<br>" + "Account"
				 + "</html>";
		selectacctbtn = new JButton(text);
		selectacctbtn.setFont(new Font("Arial", Font.BOLD, 18));
		selectacctbtn.setBounds(665,460,120,70); 
		selectacctbtn.setContentAreaFilled(false);
		//selectacctbtn.setOpaque(true);
		selectacctbtn.setBorderPainted(true);
		selectacctbtn.setBorder(new LineBorder(Color.white, 2));
		//selectacctbtn.setBackground(Color.white);
		selectacctbtn.setForeground(Color.white);
		selectacctbtn.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mousePressed(MouseEvent e) {
		    	//selectacctbtn.setBorderPainted(true);
		    	selectacctbtn.setBorder(new LineBorder(Color.black, 2));
		    }
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	//selectacctbtn.setBorderPainted(false);
		    	selectacctbtn.setBorder(new LineBorder(Color.white, 2));
		    }
		});
		selectacctbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//When the user clicks the selectacctbtn, the current account should be set to the account currently selected in the table
				//But first, check to make sure that the account selected in the table is not already the current account
				if (window.getGame().getCurrAccount().equals(window.getGame().getGameAccounts().get(accttable.getSelectedRow()))) {
					JOptionPane.showMessageDialog(window, "You're already logged in, " + window.getGame().getCurrAccount().getName() + "!", "Oops", JOptionPane.PLAIN_MESSAGE);
				}
				else {
					//Login the selected account
					window.getGame().setCurrAccount(window.getGame().getGameAccounts().get(accttable.getSelectedRow()));
					//Update the JLabel to reflect the change
					curracctlabel.setText("<html>Currently Logged In As:<br> &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp " + window.getGame().getCurrAccount().getName() + "</html>");
					//Display a message welcoming the account back to the game
					JOptionPane.showMessageDialog(window, "Welcome back to Cave Runner, " + window.getGame().getCurrAccount().getName() + "!", "Welcome", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		add(selectacctbtn);
				
		//Necessary for styling to apply properly
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
	 * @return the selectacctbtn
	 */
	public JButton getSelectacctbtn() {
		return selectacctbtn;
	}

	/**
	 * @param selectacctbtn the selectacctbtn to set
	 */
	public void setSelectacctbtn(JButton selectacctbtn) {
		this.selectacctbtn = selectacctbtn;
	}

	/**
	 * @return the createacctbtn
	 */
	public JButton getCreateacctbtn() {
		return createacctbtn;
	}

	/**
	 * @param createacctbtn the createacctbtn to set
	 */
	public void setCreateacctbtn(JButton createacctbtn) {
		this.createacctbtn = createacctbtn;
	}

	/**
	 * @return the accttxtbox
	 */
	public JTextField getAccttxtbox() {
		return accttxtbox;
	}

	/**
	 * @param accttxtbox the accttxtbox to set
	 */
	public void setAccttxtbox(JTextField accttxtbox) {
		this.accttxtbox = accttxtbox;
	}

	/**
	 * @return the accttable
	 */
	public ScrollableTable getAccttable() {
		return accttable;
	}

	/**
	 * @param accttable the accttable to set
	 */
	public void setAccttable(ScrollableTable accttable) {
		this.accttable = accttable;
	}

	/**
	 * @return the curracctlabel
	 */
	public JLabel getCurracctlabel() {
		return curracctlabel;
	}

	/**
	 * @param curracctlabel the curracctlabel to set
	 */
	public void setCurracctlabel(JLabel curracctlabel) {
		this.curracctlabel = curracctlabel;
	}
	
}
