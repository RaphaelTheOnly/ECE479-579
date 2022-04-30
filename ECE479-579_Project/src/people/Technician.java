package people;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.UserInterface;

// ActionListener needed for the Technician's timer (launched in UI class)
public class Technician implements ActionListener {
	private UserInterface ui;
	public boolean leakDetected;
	
	// Default constructor
	public Technician() {
		this(new UserInterface());
	}
	
	// Parameterized constructor
	public Technician(UserInterface ui) {
		this.ui = ui;
		leakDetected = false;
	}

	// Necessary function for the timer
	@Override
	public void actionPerformed(ActionEvent e) {
		// If there's a leak and they need bottles, do both
		if (leakDetected && ui.getNumEmptyBott() == 0) {
			System.out.println("leak detected and no more full bottles");
			fixLeak();
			deliverBottles();
		}
		// If there's only a leak, fix the leak
		else if (leakDetected) {
			System.out.println("leak detected");
			fixLeak();
		}
		// Otherwise, this was a call to deliver bottles
		else {
			deliverBottles();
		}
	}
	
	// Fct that bundles what needs to be done to fix the leak
	public void fixLeak() {
		ui.leakFixed();
		leakDetected = false;
	}
	
	// Fct that bundles what needs to be done to deliver the bottles
	public void deliverBottles() {
		ui.bottlesDelivered();
	}
}
