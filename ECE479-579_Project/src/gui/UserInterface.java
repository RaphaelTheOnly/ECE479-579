package gui;

import java.awt.EventQueue;
import java.util.Timer;
import javax.swing.JFrame;

import software.System;

public class UserInterface extends JFrame {

    public UserInterface() {
        
        initUI();
    }
    
    private void initUI() {

        add(new System());

        setTitle("ThirstAI");
        setSize(1400, 850);
        
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
        	UserInterface ex = new UserInterface();
            ex.setVisible(true);
        });
        
        Timer timer = new Timer();
    	//timer.schedule(new Difficulty(), 0, 5000);
		
    }
}