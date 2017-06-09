package BacktrackingGUI;
import java.awt.*;
import javax.swing.*;

public class NQueenGUI extends JPanel {
    private int[] board;
    private int size;
    private int width=600;
    JFrame frame;
    private int status=1;
    //1 - searching
    //2 - solution found
    //3 - search over
    
    //constructor
    public NQueenGUI() {
    	String s = (String)JOptionPane.showInputDialog(null,"Hello everyone, Please input no. of queens:","N-Queens puzzle",JOptionPane.PLAIN_MESSAGE,null,null,"8");
		size = Integer.parseInt(s);
		
    	frame = new JFrame();
        
        frame.setSize(width, width);
        frame.getContentPane().add(this);
        frame.setLocationRelativeTo(null);
        
        frame.setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    	board = new int[size];
    	for(int i=0;i<size;i++){
    		board[i]=-1;
    	}
    }

    public void paint(Graphics g){
    	g.setColor(Color.BLACK);
    	g.fillRect(50, 10, 200, 30);
    	g.fillRect(265, 10, 170, 30);
    	g.fillRect(445, 10, 100, 30);
    	
    	g.setColor(Color.red);    
    	
    	if(this.status==1){
    		g.drawString("Searching for solutions...", 70, 30);
    	} else if(this.status==2){
    		g.drawString("Solution found!", 275, 30);
    	} else {
    		g.drawString("Search Over!!!", 455, 30);
    	}
        
    	
    	int block_size=((width-100)/size);
    	g.setColor(Color.BLACK);
    	g.fillRect(48, 48, 4+size*block_size, 4+size*block_size);
    	
    	for(int i=0; i<size; i++){
    		for(int j=0; j<size; j++){
    			if((i+j)%2==0){
    				//g.clearRect(50+(i*block_size),50+(j*block_size), block_size, block_size);
    				g.setColor(Color.WHITE);
    				g.fillRect(50+(i*block_size),50+(j*block_size), block_size, block_size);
    			} else {
    				g.setColor(Color.BLACK);
    				g.fillRect(50+(i*block_size),50+(j*block_size), block_size, block_size);
    			}
    		}
    	}
    	
    	//now, print the queens according to the board
    	for(int i=0; i<size; i++){
    		if(board[i]!=-1){
    	    	g.setColor(Color.ORANGE);
    	    	
    	    	int x=(50+(block_size*i));
    	    	int y=(50+(block_size*board[i]));
    	    	
    			int center_x = (x+(block_size/2));
    			int center_y = (y+(block_size/2));
    	    	g.fillOval(center_x, center_y, block_size/4, block_size/4);
    		}
    	}
    }
    
    public boolean checkForSafety(int r, int c) {
        for (int i = 0; i < r; i++) {
            if (board[i] == c || (i - r) == (board[i] - c) ||(i + board[i]) == (r + c)) 
            {
            	//collision detected
                return false;
            }
        }
        
        //safe
        return true;
    }

    public void printQueens() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i] == j) {
                    System.out.print("Q  ");
                } else {
                    System.out.print(".  ");
                }
            }
            System.out.println();
        }
        System.out.println("\n\n");
    }

    public void delay(int seconds){
    	try {
    	    Thread.sleep(seconds*1000);
    	} catch(InterruptedException ex) {
    	    Thread.currentThread().interrupt();
    	}
    }
    
    public void placeNQueenGUIs(int r) {
    	this.repaint();
    	this.delay(1);
    	
    	if(r==size) {
    		//got the solution
    		this.status=2;
    		this.repaint();
    		this.delay(1);
    		this.status=1;
    		
    		System.out.println("Solution Found");
    		this.delay(1);
    		printQueens();
    	}
        for (int c = 0; c < size; c++) {
            if (checkForSafety(r, c)) {
            	board[r] = c;
                placeNQueenGUIs(r + 1);
            }
        }
    }

    public static void main(String args[]) {
    	NQueenGUI Q = new NQueenGUI();
        Q.placeNQueenGUIs(0);  
        
        System.out.println("Search Over");
        Q.status=3;
        Q.repaint();
    }
}