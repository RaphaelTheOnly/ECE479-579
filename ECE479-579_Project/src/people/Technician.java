package people;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.UserInterface;
import software.AISystem;


public class Technician implements ActionListener {
	private AISystem system;
	private UserInterface ui;

	
	public Technician() {
		this(new UserInterface(), new AISystem());
	}

	public Technician(UserInterface ui, AISystem sys) {
		system = sys;
		this.ui = ui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		system.initSystem();
		ui.repaint();
	}
}
