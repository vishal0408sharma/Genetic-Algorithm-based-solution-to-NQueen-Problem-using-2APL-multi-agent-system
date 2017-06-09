package Backtracking;
import java.util.Scanner;

public class NQueen {
    int[] board;
    int size;
    Scanner keyboard;
    
    //constructor
    public NQueen() {
    	//input size of the chessboard
    	keyboard=new Scanner(System.in);
		System.out.print("Input the size of chessboard ");
		int n=keyboard.nextInt();
		
    	this.size=n;
    	board = new int[size];
    	
    	keyboard.close();
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

    public void placeNqueens(int r) {
    	if(r==size) {
    		//got the solution
    		printQueens();
    	}
        for (int c = 0; c < size; c++) {
            if (checkForSafety(r, c)) {
            	board[r] = c;
                placeNqueens(r + 1);
            }
        }
    }

    public static void main(String args[]) {
    	NQueen Q = new NQueen();
        Q.placeNqueens(0);  
        
        System.out.println("Solution found...Search Over");
    }
}