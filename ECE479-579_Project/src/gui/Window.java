package gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import javax.swing.*;
import software.System;

/*
 * The Window class contains all fields and methods related to a Window
 * The Window is the JFrame that contains all graphics related to the ThirstAI
 */
public class Window extends JFrame {
	private JPanel mainPanel;
   	private CardLayout cl;
   	
  //Default no-arg constructor
   	public Window() {
   		this(new System(), 700, 500);
   	}
   	
  //Constructor that requires three arguments
   	public Window(System s, Integer width, Integer height) {
	  
   	}
}
