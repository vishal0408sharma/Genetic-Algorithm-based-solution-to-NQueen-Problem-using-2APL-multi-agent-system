package GAGUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

class GAGUI extends JPanel {
	public int[] board1;
	public int[] board2;
	
	public boolean display1;
	public boolean display2;
    
	private int size;
    
    private int height=700;
    private int width=1200;
    String message, message1, message2;
    JFrame frame;
    
    //constructor
    public GAGUI(int n){
    	size=n;
    	frame = new JFrame();
        
        frame.setSize(width, height+50);
        frame.getContentPane().add(this);
        frame.setLocationRelativeTo(null);
        
        frame.setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    	board1 = new int[size];
    	board2 = new int[size];
    	
    	display1=false;
    	display2=false;
    	message="";
    	message1="";
    	message2="";
    }

    public void paint(Graphics g){
    	g.setColor(Color.WHITE);
    	g.fillRect(0, 0, 1200, 700);
    	
    	g.setColor(Color.BLACK);
    	g.fillRect(50, 10, 1100, 30);
    	
    	g.setColor(Color.red);    
    	g.drawString(message, 70, 30);
    	
    	int block_size=((height-180)/size);
    	int x2=(1148-size*block_size);
    	
    	//draw board1
    	if(display1==true){
    		g.setColor(Color.BLACK);
        	g.fillRect(50, 60, 2+size*block_size, 50);
        	
        	g.setColor(Color.red);    
        	g.drawString(message1, 70, 80);
        	
        	g.setColor(Color.BLACK);
        	g.fillRect(48, 128, 4+size*block_size, 4+size*block_size);
        	
        	for(int i=0; i<size; i++){
        		for(int j=0; j<size; j++){
        			if((i+j)%2==0){
        				g.setColor(Color.WHITE);
        				g.fillRect(50+(i*block_size),130+(j*block_size), block_size, block_size);
        			} else {
        				g.setColor(Color.BLACK);
        				g.fillRect(50+(i*block_size),130+(j*block_size), block_size, block_size);
        			}
        		}
        	}	
        	
        	//now, print the queens according to the board1
        	for(int i=0; i<size; i++){
        		if(board1[i]!=-1){
        	    	g.setColor(Color.ORANGE);
        	    	
        	    	int x=(50+(block_size*i));
        	    	int y=(130+(block_size*board1[i]));
        	    	
        			int center_x = (x+(block_size/2));
        			int center_y = (y+(block_size/2));
        	    	g.fillOval(center_x, center_y, block_size/4, block_size/4);
        		}
        	}
    	}
    	
    	//draw board2
    	if(display2==true){
    		g.setColor(Color.BLACK);
        	g.fillRect(x2, 60, 2+size*block_size, 50);
        	
        	g.setColor(Color.red);    
        	g.drawString(message2, x2+20, 80);
        	
        	g.setColor(Color.BLACK);
        	g.fillRect(x2-2, 128, 4+size*block_size, 4+size*block_size);
        	
        	for(int i=0; i<size; i++){
        		for(int j=0; j<size; j++){
        			if((i+j)%2==0){
        				g.setColor(Color.WHITE);
        				g.fillRect(x2+(i*block_size),130+(j*block_size), block_size, block_size);
        			} else {
        				g.setColor(Color.BLACK);
        				g.fillRect(x2+(i*block_size),130+(j*block_size), block_size, block_size);
        			}
        		}
        	}
        	
        	//now, print the queens according to the board2
        	for(int i=0; i<size; i++){
        		if(board2[i]!=-1){
        	    	g.setColor(Color.ORANGE);
        	    	
        	    	int x=(x2+(block_size*i));
        	    	int y=(130+(block_size*board2[i]));
        	    	
        			int center_x = (x+(block_size/2));
        			int center_y = (y+(block_size/2));
        	    	g.fillOval(center_x, center_y, block_size/4, block_size/4);
        		}
        	}
        }	
    }
}