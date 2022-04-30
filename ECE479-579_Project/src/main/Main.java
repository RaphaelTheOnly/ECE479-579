package main;

import java.awt.EventQueue;
import java.util.Random;

import software.AISystem;

public class Main {
	public static void main(String[] args) {
		
        EventQueue.invokeLater(() -> {
        	AISystem ex = new AISystem();
            ex.setVisible(true);
        });
    }
}
