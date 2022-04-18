package hardware;

public class Bottle {
	private int fill;
    
	public Bottle() {
		fill = 100;
	}
	
	public void fillBottle() {
		fill = 100;
	}
	public void emptyBottle() {
		fill = 0;
	}
	
	public int dispensed(int vol) {
		fill -= vol;
		return fill;
	}
	public int howFull() {
		return fill;
	}
    public void printFill() {
    	System.out.println("Bottle fill is: " + fill);
    }
        
}
