package org.Group7_FinalProject.Runner;

import java.util.Timer;
import java.util.TimerTask;

public class Difficulty extends TimerTask {
    public void run() {
       int diff = Sprite.changeDifficulty();
       System.out.println("Difficulty increased to " + diff + "!"); 
    }

    /* public static void main(String[] args) {
    	Timer timer = new Timer();
    	timer.schedule(new Difficulty(), 0, 50000);
    } */
}