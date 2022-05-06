package software;

import java.util.Arrays;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder; 

class TSP extends JPanel implements ActionListener {  
    private AISystem system;
    private Image image; // For background image
    // GUI elements for value-getting TSP window
    private JLabel sToALabel;
    private JLabel sToBLabel;
    private JLabel sToCLabel;
    private JLabel sToDLabel;
    private JLabel sToELabel;
    private JLabel aToBLabel;
    private JLabel aToCLabel;
    private JLabel aToDLabel;
    private JLabel aToELabel;
    private JLabel bToCLabel;
    private JLabel bToDLabel;
    private JLabel bsToELabel;
    private JLabel cToDLabel;
    private JLabel cToELabel;
    private JLabel dToELabel;
    private JTextField sToATextField;
    private JTextField sToBTextField;
    private JTextField sToCTextField;
    private JTextField sToDTextField;
    private JTextField sToETextField;
    private JTextField aToBTextField;
    private JTextField aToCTextField;
    private JTextField aToDTextField;
    private JTextField aToETextField;
    private JTextField bToCTextField;
    private JTextField bToDTextField;
    private JTextField bToETextField;
    private JTextField cToDTextField;
    private JTextField cToETextField;
    private JTextField dToETextField;
    private JButton confirmInitialTSPBtn;
    // GUI elements for graph window
    private JLabel intoLabel;
    private JLabel nodeSLabel;
    private JLabel nodeALabel;
    private JLabel nodeBLabel;
    private JLabel nodeCLabel;
    private JLabel nodeDLabel;
    private JLabel nodeELabel;
    private JLabel sToAEdgeLabel;
    private JLabel sToBEdgeLabel;
    private JLabel sToCEdgeLabel;
    private JLabel sToDEdgeLabel;
    private JLabel sToEEdgeLabel;
    private JLabel aToBEdgeLabel;
    private JLabel aToCEdgeLabel;
    private JLabel aToDEdgeLabel;
    private JLabel aToEEdgeLabel;
    private JLabel bToCEdgeLabel;
    private JLabel bToDEdgeLabel;
    private JLabel bToEEdgeLabel;
    private JLabel cToDEdgeLabel;
    private JLabel cToEEdgeLabel;
    private JLabel dToEEdgeLabel;
    private JLabel shortestPathLabel;
    private JButton okayBtn;
    
    // Default Constructor
    public TSP() {
    	this(new AISystem());
    }
    
    // Parameterized Constructor
    public TSP(AISystem sys) {
    	inputUI();
    	system = sys;
    }
    
	static int findHamiltonianCycle(int[][] distance, boolean[] visited, int curr, int places, int count, int cost, int hamiltonianCycle){  
        if (count == places && distance[curr][0] > 0){  
            hamiltonianCycle = Math.min(hamiltonianCycle, cost + distance[curr][0]);  
            return hamiltonianCycle;  
        }  
    
        //Backtracking 
        for (int i = 0; i < 6; i++){  
            if (visited[i] == false && distance[curr][i] > 0){   
                visited[i] = true;
                hamiltonianCycle = findHamiltonianCycle(distance, visited, i, places, count + 1, cost + distance[curr][i], hamiltonianCycle);  
                //Reset the visited cities 
                visited[i] = false;  
            }  
        }  
        return hamiltonianCycle;  
    }  
    
	static ArrayList<Integer> AStarSearch(int[][] distance, int[] visited, int currNode, int totalDist, int[][] average, ArrayList<Integer> sequence) {
    	int min = 999;
		int nearestNeighbor = 0;
		if(nearestNeighbor == currNode) {
			nearestNeighbor += 1;
		}
		//How many and what are the nodes visited.
		int count = 0;
    	for(int i = 0; i < 6; i++) {
    		count += visited[i];
    	}
		
    	//Find the average distance of the current path + the path to be expanded
    	for(int i = 0; i < 6; i++) {
    		if(i != currNode) {
    			average[currNode][i] = (totalDist + distance[currNode][i])/count;
    		}
    	}
    	
    	//Find the node to be expanded, which promising the smallest average
    	for(int i = 0; i < 6; i++) {
    		if(average[currNode][i] < min && average[currNode][i] != 99 && visited[i] == 0) {
    			min = average[currNode][i];
    		}
    	}
    	//Find of the index of that average-min-granting node in the average[][]
    	for(int i = 0; i < 6; i++) {
    		if(min == average[currNode][i] && visited[i] == 0) {
    			nearestNeighbor = i;
    		}
    	}
    	totalDist += distance[currNode][nearestNeighbor];
 
    	currNode = nearestNeighbor;
    	sequence.add(nearestNeighbor);
    	visited[nearestNeighbor] = 1;
    	//If all places have been visited, return the sequence of indices of places
    	//And the total distance appended to the end, it's an int so it's fine
    	count = 0;
    	for(int i = 0; i < 6; i++) {
    		count += visited[i];
    	}
    	// If all 5 places visited, the search is done.
    	if(count == 6) {
    		sequence.add(totalDist);
    		return sequence;
    	}
    	//Or if the next minimal node still produce a path average greater than that of some previous point
    	// Trace back to the point until there's a node who can expand to another node with average less than the aforementioned.
    	else{ //Recursively go through all places
    		return sequence = AStarSearch(distance, visited, currNode, totalDist, average, sequence);
    	}
    	
    }
    
	// Generate all elements for the first window, the user input interface
    public void inputUI() {
    	setFocusable(true);
        setBackground(Color.lightGray);
        //setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        
        // S to A label
        sToALabel = new JLabel("<html><font size=7> Welcome to the Traveling Salesman Initialization of the ThirstAI 2000 system! </font><br><br>" +
        "<font size=5>Please go ahead and enter the distances between the houses.</font><br><br><font size=5>From S to A: </font></html>");
        sToALabel.setBounds(50,25,790,180); // Position of this GUI element via (x, y, width, height)
		add(sToALabel);
		sToATextField = new JTextField("", 5);
		sToATextField.setFont(new Font("Arial", Font.BOLD, 20));
		sToATextField.setBounds(165,180,30,25); 
		sToATextField.setBorder(new LineBorder(Color.blue));
		sToATextField.setForeground(Color.black);
		sToATextField.setBackground(Color.white);
  		add(sToATextField);
		
		sToBLabel = new JLabel("<html><font size=5>S to B: </font></html>");
		sToBLabel.setBounds(210,180,150,20); // Position of this GUI element via (x, y, width, height)
		add(sToBLabel);
		sToBTextField = new JTextField("", 5);
		sToBTextField.setFont(new Font("Arial", Font.BOLD, 20));
		sToBTextField.setBounds(275,180,30,25); 
		sToBTextField.setBorder(new LineBorder(Color.blue));
		sToBTextField.setForeground(Color.black);
		sToBTextField.setBackground(Color.white);
  		add(sToBTextField);
		
	    sToCLabel = new JLabel("<html><font size=5>S to C: </font></html>");
	    sToCLabel.setBounds(320,180,150,20); // Position of this GUI element via (x, y, width, height)
		add(sToCLabel);
		sToCTextField = new JTextField("", 5);
		sToCTextField.setFont(new Font("Arial", Font.BOLD, 20));
		sToCTextField.setBounds(385,180,30,25); 
		sToCTextField.setBorder(new LineBorder(Color.blue));
		sToCTextField.setForeground(Color.black);
		sToCTextField.setBackground(Color.white);
  		add(sToCTextField);
  		
	    sToDLabel = new JLabel("<html><font size=5>S to D: </font></html>");
	    sToDLabel.setBounds(430,180,150,20); // Position of this GUI element via (x, y, width, height)
		add(sToDLabel);
		sToDTextField = new JTextField("", 5);
		sToDTextField.setFont(new Font("Arial", Font.BOLD, 20));
		sToDTextField.setBounds(495,180,30,25); 
		sToDTextField.setBorder(new LineBorder(Color.blue));
		sToDTextField.setForeground(Color.black);
		sToDTextField.setBackground(Color.white);
  		add(sToDTextField);
	    
	    sToELabel = new JLabel("<html><font size=5>S to E: </font></html>");
	    sToELabel.setBounds(540,180,150,20); // Position of this GUI element via (x, y, width, height)
		add(sToELabel);
		sToETextField = new JTextField("", 5);
		sToETextField.setFont(new Font("Arial", Font.BOLD, 20));
		sToETextField.setBounds(605,180,30,25); 
		sToETextField.setBorder(new LineBorder(Color.blue));
		sToETextField.setForeground(Color.black);
		sToETextField.setBackground(Color.white);
  		add(sToETextField);
	    
	    aToBLabel = new JLabel("<html><font size=5>A to B: </font></html>");
	    aToBLabel.setBounds(100,220,150,20); // Position of this GUI element via (x, y, width, height)
		add(aToBLabel);
		aToBTextField = new JTextField("", 5);
	    aToBTextField.setFont(new Font("Arial", Font.BOLD, 20));
		aToBTextField.setBounds(165,220,30,25); 
		aToBTextField.setBorder(new LineBorder(Color.blue));
		aToBTextField.setForeground(Color.black);
		aToBTextField.setBackground(Color.white);
  		add(aToBTextField);
	    
	    aToCLabel = new JLabel("<html><font size=5>A to C: </font></html>");
	    aToCLabel.setBounds(210,220,150,20); // Position of this GUI element via (x, y, width, height)
		add(aToCLabel);
		aToCTextField = new JTextField("", 5);
		aToCTextField.setFont(new Font("Arial", Font.BOLD, 20));
		aToCTextField.setBounds(275,220,30,25); 
		aToCTextField.setBorder(new LineBorder(Color.blue));
		aToCTextField.setForeground(Color.black);
		aToCTextField.setBackground(Color.white);
  		add(aToCTextField);
	    
	    aToDLabel = new JLabel("<html><font size=5>A to D: </font></html>");
	    aToDLabel.setBounds(320,220,150,20); // Position of this GUI element via (x, y, width, height)
		add(aToDLabel);
		aToDTextField = new JTextField("", 5);
		aToDTextField.setFont(new Font("Arial", Font.BOLD, 20));
		aToDTextField.setBounds(385,220,30,25); 
		aToDTextField.setBorder(new LineBorder(Color.blue));
		aToDTextField.setForeground(Color.black);
		aToDTextField.setBackground(Color.white);
  		add(aToDTextField);
	    
	    aToELabel = new JLabel("<html><font size=5>A to E: </font></html>");
	    aToELabel.setBounds(430,220,150,20); // Position of this GUI element via (x, y, width, height)
		add(aToELabel);
		aToETextField = new JTextField("", 5);
		aToETextField.setFont(new Font("Arial", Font.BOLD, 20));
		aToETextField.setBounds(495,220,30,25); 
		aToETextField.setBorder(new LineBorder(Color.blue));
		aToETextField.setForeground(Color.black);
		aToETextField.setBackground(Color.white);
  		add(aToETextField);
	    
	    bToCLabel = new JLabel("<html><font size=5>B to C: </font></html>");
	    bToCLabel.setBounds(100,260,150,20); // Position of this GUI element via (x, y, width, height)
		add(bToCLabel);
		bToCTextField = new JTextField("", 5);
		bToCTextField.setFont(new Font("Arial", Font.BOLD, 20));
	    bToCTextField.setBounds(165,260,30,25); 
		bToCTextField.setBorder(new LineBorder(Color.blue));
		bToCTextField.setForeground(Color.black);
		bToCTextField.setBackground(Color.white);
  		add(bToCTextField);
	    
	    bToDLabel = new JLabel("<html><font size=5>B to D: </font></html>");
	    bToDLabel.setBounds(210,260,150,20); // Position of this GUI element via (x, y, width, height)
		add(bToDLabel);
		bToDTextField = new JTextField("", 5);
		bToDTextField.setFont(new Font("Arial", Font.BOLD, 20));
		bToDTextField.setBounds(275,260,30,25); 
		bToDTextField.setBorder(new LineBorder(Color.blue));
		bToDTextField.setForeground(Color.black);
		bToDTextField.setBackground(Color.white);
  		add(bToDTextField);
	    
	    bsToELabel = new JLabel("<html><font size=5>B to E: </font></html>");
	    bsToELabel.setBounds(320,260,150,20); // Position of this GUI element via (x, y, width, height)
		add(bsToELabel);
		bToETextField = new JTextField("", 5);
		bToETextField.setFont(new Font("Arial", Font.BOLD, 20));
		bToETextField.setBounds(385,260,30,25); 
		bToETextField.setBorder(new LineBorder(Color.blue));
		bToETextField.setForeground(Color.black);
		bToETextField.setBackground(Color.white);
  		add(bToETextField);
	    
	    cToDLabel = new JLabel("<html><font size=5>C to D: </font></html>");
	    cToDLabel.setBounds(100,300,150,20); // Position of this GUI element via (x, y, width, height)
		add(cToDLabel);
		cToDTextField = new JTextField("", 5);
		cToDTextField.setFont(new Font("Arial", Font.BOLD, 20));
		cToDTextField.setBounds(165,300,30,25); 
	    cToDTextField.setBorder(new LineBorder(Color.blue));
		cToDTextField.setForeground(Color.black);
		cToDTextField.setBackground(Color.white);
  		add(cToDTextField);
	    
	    cToELabel = new JLabel("<html><font size=5>C to E: </font></html>");
	    cToELabel.setBounds(210,300,150,20); // Position of this GUI element via (x, y, width, height)
		add(cToELabel);
		cToETextField = new JTextField("", 5);
		cToETextField.setFont(new Font("Arial", Font.BOLD, 20));
		cToETextField.setBounds(275,300,30,25); 
		cToETextField.setBorder(new LineBorder(Color.blue));
		cToETextField.setForeground(Color.black);
		cToETextField.setBackground(Color.white);
  		add(cToETextField);
	    
	    dToELabel = new JLabel("<html><font size=5>D to E: </font></html>");
	    dToELabel.setBounds(100,340,150,20); // Position of this GUI element via (x, y, width, height)
		add(dToELabel);
		dToETextField = new JTextField("", 5);
		dToETextField.setFont(new Font("Arial", Font.BOLD, 20));
		dToETextField.setBounds(165,340,30,25); 
		dToETextField.setBorder(new LineBorder(Color.blue));
	    dToETextField.setForeground(Color.black);
		dToETextField.setBackground(Color.white);
  		add(dToETextField);
        
  		// Button to empty the dispenser bottle
        confirmInitialTSPBtn = new JButton("Calculate");
        confirmInitialTSPBtn.setBounds(341,381,100,32); 
        confirmInitialTSPBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				prepareTSPValues();
			}
		});
        this.add(confirmInitialTSPBtn);
		
        this.setLayout(null);
        //switchToGraphWindow(); //Was used to bypass the value-enter window and work on the graph window
    }
    
	// Process the entered user input values and call the TSP search algorithm
    private void prepareTSPValues() {
    	System.out.println("Values: " + sToATextField.getText());
    	//Get distances between each pair of cities
    	//Treat the company, the dispather's departing point, as a node as customer places
    	int distance[][] = new int[6][6];
        for(int[] rows : distance) {
        	Arrays.fill(rows, 0);
        }
        distance[0][1] = Integer.parseInt(sToATextField.getText());
        distance[1][0] = distance[0][1];
        distance[0][2] = Integer.parseInt(sToBTextField.getText());
        distance[2][0] = distance[0][2];
        distance[0][3] = Integer.parseInt(sToCTextField.getText());
        distance[3][0] = distance[0][3];
        distance[0][4] = Integer.parseInt(sToDTextField.getText());
        distance[4][0] = distance[0][4];
        distance[0][5] = Integer.parseInt(sToETextField.getText());
        distance[5][0] = distance[0][5];
        distance[1][2] = Integer.parseInt(aToBTextField.getText());
        distance[2][1] = distance[1][2];
        distance[1][3] = Integer.parseInt(aToCTextField.getText());
        distance[3][1] = distance[1][3];
        distance[1][4] = Integer.parseInt(aToDTextField.getText());
        distance[4][1] = distance[1][4];
        distance[1][5] = Integer.parseInt(aToETextField.getText());
        distance[5][1] = distance[1][5];
        distance[2][3] = Integer.parseInt(bToCTextField.getText());
        distance[3][2] = distance[2][3];
        distance[2][4] = Integer.parseInt(bToDTextField.getText());
        distance[4][2] = distance[2][4];
        distance[2][5] = Integer.parseInt(bToETextField.getText());
        distance[5][2] = distance[2][5];
        distance[3][4] = Integer.parseInt(cToDTextField.getText());
        distance[4][3] = distance[3][4];
        distance[3][5] = Integer.parseInt(cToETextField.getText());
        distance[5][3] = distance[3][5];
        distance[4][5] = Integer.parseInt(dToETextField.getText());
        distance[5][4] = distance[4][5];
        
        int average[][] = new int[6][6];
        for(int[] rows : average) {
        	Arrays.fill(rows, 999);
        }
        char places[] = {'S', 'A', 'B', 'C', 'D', 'E'};
        for(int i = 0; i < 6; i++){  
            for(int j = i + 1; j < 6; j++){ 
                System.out.print(distance[i][j] + ", ");  
            }  
        }  
       
        //Visited places, ordered from 0 to 5
        int visited[] = new int[6];
        Arrays.fill(visited, 0);
        visited[0] = 1;
          
        //Set up the A* search with the nearest neighbor approach
        ArrayList<Integer> sequence = new ArrayList<Integer>(6);
        sequence = AStarSearch(distance, visited, 0, 0, average, sequence); // TODO Error in here!!!!!!
    
        // print the minimum weighted Hamiltonian Cycle
        for(int i : sequence) {
        	if(i < 7) {
        		System.out.println(places[i]);
        	}
        }
        System.out.println(sequence.get(5)); 
        
        // When done, pull up the graph window to show the result
        switchToGraphWindow(sequence, distance);
    }
    
    // Turn invisible all GUI elements from the first window
    private void makeTSPEntryWindowInvis() {
    	sToALabel.setVisible(false);
        sToBLabel.setVisible(false);;
        sToCLabel.setVisible(false);;
        sToDLabel.setVisible(false);;
        sToELabel.setVisible(false);;
        aToBLabel.setVisible(false);;
        aToCLabel.setVisible(false);;
        aToDLabel.setVisible(false);;
        aToELabel.setVisible(false);;
        bToCLabel.setVisible(false);;
        bToDLabel.setVisible(false);;
        bsToELabel.setVisible(false);;
        cToDLabel.setVisible(false);;
        cToELabel.setVisible(false);;
        dToELabel.setVisible(false);;
        sToATextField.setVisible(false);;
        sToBTextField.setVisible(false);;
        sToCTextField.setVisible(false);;
        sToDTextField.setVisible(false);;
        sToETextField.setVisible(false);;
        aToBTextField.setVisible(false);;
        aToCTextField.setVisible(false);;
        aToDTextField.setVisible(false);;
        aToETextField.setVisible(false);;
        bToCTextField.setVisible(false);;
        bToDTextField.setVisible(false);;
        bToETextField.setVisible(false);;
        cToDTextField.setVisible(false);;
        cToETextField.setVisible(false);;
        dToETextField.setVisible(false);;
        confirmInitialTSPBtn.setVisible(false);;
    }
    
    // Create all GUI elements for second window
 	public void switchToGraphWindow(ArrayList<Integer> sequence, int[][] distance) {
 		System.out.println("in graph window");
 		char places[] = {'S', 'A', 'B', 'C', 'D', 'E'};
 		// print the minimum weighted Hamiltonian Cycle
 		StringBuilder sbComma = new StringBuilder();
        for(int i : sequence) {
        	if(i < 7) {
        		sbComma.append(places[i]+", ");
        	}
        }
        sbComma.delete(13,15);
        String bestPathStr = sbComma.toString();
        char[] bestPath = bestPathStr.toCharArray();
 		makeTSPEntryWindowInvis();
 		this.setBackground(Color.white);
        ImageIcon ii = new ImageIcon("src/resources/TSPbackground.JPG");
        image = ii.getImage();
        intoLabel = new JLabel("Traveling Salesman Result as Graph Representation");
        intoLabel.setBounds(63,5,700,30); // Position of this GUI element via (x, y, width, height)
        intoLabel.setFont(new Font("Arial", Font.BOLD, 26));
        add(intoLabel);
        
        nodeSLabel = new JLabel("S");
        nodeSLabel.setBounds(163,173,150,20); // Position of this GUI element via (x, y, width, height)
        nodeSLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(nodeSLabel);
        
        nodeALabel = new JLabel(Character.toString(bestPath[0]));
        nodeALabel.setBounds(277,303,150,20); // Position of this GUI element via (x, y, width, height)
        nodeALabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(nodeALabel);
        
        nodeBLabel = new JLabel(Character.toString(bestPath[3]));
        nodeBLabel.setBounds(476,361,150,20); // Position of this GUI element via (x, y, width, height)
        nodeBLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(nodeBLabel);
        
        nodeCLabel = new JLabel(Character.toString(bestPath[6]));
        nodeCLabel.setBounds(607,232,150,20); // Position of this GUI element via (x, y, width, height)
        nodeCLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(nodeCLabel);
        
        nodeDLabel = new JLabel(Character.toString(bestPath[9]));
        nodeDLabel.setBounds(478,107,150,20); // Position of this GUI element via (x, y, width, height)
        nodeDLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(nodeDLabel);
        
		nodeELabel = new JLabel(Character.toString(bestPath[12]));
        nodeELabel.setBounds(303,80,20,20); // Position of this GUI element via (x, y, width, height)
        nodeELabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(nodeELabel);
        
        sToAEdgeLabel = new JLabel(Integer.toString(distance[0][1]));
        sToAEdgeLabel.setBounds(160,260,150,20); // Position of this GUI element via (x, y, width, height)
        sToAEdgeLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(sToAEdgeLabel);
        
		sToBEdgeLabel = new JLabel(Integer.toString(distance[0][2]));
        sToBEdgeLabel.setBounds(406,300,150,20); // Position of this GUI element via (x, y, width, height)
        sToBEdgeLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(sToBEdgeLabel);
        
		sToCEdgeLabel = new JLabel(Integer.toString(distance[0][3]));
        sToCEdgeLabel.setBounds(470,190,150,20); // Position of this GUI element via (x, y, width, height)
        sToCEdgeLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(sToCEdgeLabel);
        
		sToDEdgeLabel = new JLabel(Integer.toString(distance[0][4]));
        sToDEdgeLabel.setBounds(410,98,150,20); // Position of this GUI element via (x, y, width, height)
        sToDEdgeLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(sToDEdgeLabel);
        
		sToEEdgeLabel = new JLabel(Integer.toString(distance[0][5]));
        sToEEdgeLabel.setBounds(235,98,150,20); // Position of this GUI element via (x, y, width, height)
        sToEEdgeLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(sToEEdgeLabel);
        
		aToBEdgeLabel = new JLabel(Integer.toString(distance[1][2]));
        aToBEdgeLabel.setBounds(380,320,150,20); // Position of this GUI element via (x, y, width, height)
        aToBEdgeLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(aToBEdgeLabel);
        
		aToCEdgeLabel = new JLabel(Integer.toString(distance[1][3]));
        aToCEdgeLabel.setBounds(490,220,150,20); // Position of this GUI element via (x, y, width, height)
        aToCEdgeLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(aToCEdgeLabel);
        
		aToDEdgeLabel = new JLabel(Integer.toString(distance[1][4]));
        aToDEdgeLabel.setBounds(388,150,150,20); // Position of this GUI element via (x, y, width, height)
        aToDEdgeLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(aToDEdgeLabel);
        
		aToEEdgeLabel = new JLabel(Integer.toString(distance[1][5]));
        aToEEdgeLabel.setBounds(260,207,150,20); // Position of this GUI element via (x, y, width, height)
        aToEEdgeLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(aToEEdgeLabel);
        
		bToCEdgeLabel = new JLabel(Integer.toString(distance[2][3]));
        bToCEdgeLabel.setBounds(580,300,150,20); // Position of this GUI element via (x, y, width, height)
        bToCEdgeLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(bToCEdgeLabel);
        
		bToDEdgeLabel = new JLabel(Integer.toString(distance[2][4]));
        bToDEdgeLabel.setBounds(520,140,150,20); // Position of this GUI element via (x, y, width, height)
        bToDEdgeLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(bToDEdgeLabel);
        
		bToEEdgeLabel = new JLabel(Integer.toString(distance[2][5]));
        bToEEdgeLabel.setBounds(415,213,150,20); // Position of this GUI element via (x, y, width, height)
        bToEEdgeLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(bToEEdgeLabel);
        
		cToDEdgeLabel = new JLabel(Integer.toString(distance[3][4]));
        cToDEdgeLabel.setBounds(580,120,150,20); // Position of this GUI element via (x, y, width, height)
        cToDEdgeLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(cToDEdgeLabel);
        
		cToEEdgeLabel = new JLabel(Integer.toString(distance[3][5]));
        cToEEdgeLabel.setBounds(550,170,150,20); // Position of this GUI element via (x, y, width, height)
        cToEEdgeLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(cToEEdgeLabel);
        
		dToEEdgeLabel = new JLabel(Integer.toString(distance[4][5]));
        dToEEdgeLabel.setBounds(400,45,150,20); // Position of this GUI element via (x, y, width, height)
        dToEEdgeLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(dToEEdgeLabel);
		
		int result = sequence.get(5) + distance[0][5];
        shortestPathLabel = new JLabel("<html>Shortest path: S, " + bestPathStr + ", S<br>Total distance: " + result +"</html>");
        shortestPathLabel.setBounds(40,360,350,50); // Position of this GUI element via (x, y, width, height)
        shortestPathLabel.setFont(new Font("Arial", Font.BOLD, 22));
		add(shortestPathLabel);
        
		okayBtn = new JButton("Okay");
        okayBtn.setBounds(344,406,100,32); 
        okayBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				system.createMainUI(sequence);
			}
		});
        add(okayBtn);
        
        repaint();
     } 
 	
    // This function gets called by "repaint()" and is very important for keeping the GUI updated
 	@Override 
 	public void paintComponent(Graphics g) { 
 		super.paintComponent(g);
 		g.drawImage(image, 0, 0, this); // Background gets drawn (again)
 		
 		Toolkit.getDefaultToolkit().sync();
 	}
 	
 	// This function gets triggered by the UI's timer
 	@Override
 	public void actionPerformed(ActionEvent e) {
        repaint();
 	}
}  
