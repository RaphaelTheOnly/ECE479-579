package people;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.UserInterface;

public class Technician implements ActionListener {
	private UserInterface ui;
	public boolean leakDetected;
	
	public Technician() {
		this(new UserInterface());
	}
	
	public Technician(UserInterface ui) {
		this.ui = ui;
		leakDetected = false;
		//System.out.println("technician was created");
	}

	@Override
	  public void actionPerformed(ActionEvent e) {
			if (leakDetected && ui.getNumFullBott() == 0) {
				System.out.println("leak detected and no more full bottles");
				fixLeak();
				ui.restartAISystem();
			}
			else if (leakDetected) {
				System.out.println("leak detected");
				fixLeak();
			}
			else {
				ui.restartAISystem();
			}
	  }
	
	public void fixLeak() {
		ui.leakFixed();
		leakDetected = false;
	}

}
