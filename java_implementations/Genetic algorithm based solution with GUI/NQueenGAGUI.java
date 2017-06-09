package GAGUI;
import java.awt.Color;
import java.awt.Graphics;
import java.util.*;
import javax.swing.*;

public class NQueenGAGUI {
	private int population;
	private int iterations;
	private int queens;
	private double mutationRate;
	
	Random rnd = new Random();
	ChessBoard[] boards;
	
	GAGUI gui;
	
	public NQueenGAGUI(int pop, int iter, int n, double mR){
		population=pop;
		iterations=iter;
		queens=n;
		mutationRate=mR;
		
		boards=new ChessBoard[pop];
		gui=new GAGUI(n);
	}
	
	public static void main(String[] args){
		int population;
		int iterations;
		int queens;
		double mutationRate;
		
		//input values from console
		Scanner keyboard=new Scanner(System.in);
		System.out.print("Input the size of initial population??  ");
		
		population=keyboard.nextInt();
		
		System.out.print("No. of iterations??  ");
		iterations=keyboard.nextInt();
		
		System.out.print("No. of queens??  ");
		queens=keyboard.nextInt();
		
		System.out.print("Mutation Rate??  ");
		mutationRate=keyboard.nextDouble();
		
		NQueenGAGUI driver = new NQueenGAGUI(population, iterations, queens, mutationRate);
		System.out.println("Generating population");
		driver.gui.message="Generating Population";
		
		driver.gui.repaint();
		driver.delay(4);
		
		ChessBoard solution = driver.generatePopulation();
		System.out.println("Initial population generated");
		driver.gui.message="Initial population generated";
		driver.gui.repaint();
		driver.delay(4);
		
		for(int i=0;i<driver.iterations && solution==null;i++){
			//search for the solution
			System.out.print("Executing iteration " + (i+1));
			driver.gui.message="Executing iteration "+(i+1);
			solution = driver.evolve();
		}
		
		if(solution!=null){
			System.out.println("Solution Found");
			
			driver.gui.message1="Solution Found";
			driver.gui.board1=solution.board;
			driver.gui.display1=true;
			driver.gui.repaint();
			
			for(int i=0;i<queens;i++){
				System.out.print(solution.board[i]+" ");
			}
			System.out.println("");
			
			System.out.print("\n");
		} else {
			System.out.print("No solution found\n");
		}
		
		keyboard.close();
	}
	
	public void delay(int seconds){
    	try {
    	    Thread.sleep(seconds*500);
    	} catch(InterruptedException ex) {
    	    Thread.currentThread().interrupt();
    	}
    }
	
	public void mutate(ChessBoard chromosome){
		int i=rnd.nextInt(queens);
		int j=rnd.nextInt(queens);
		
		int temp=chromosome.board[i];
		chromosome.board[i]=chromosome.board[j];
		chromosome.board[j]=temp;
	}
	
	public ChessBoard evolve(){
		for(int i=0; i<population/2; i++){
			ChessBoard child = new ChessBoard(breed(boards[i], boards[i+1]), queens);
			boards[i]=child;
		}
		
		for(int i = 1; i < population; i++){
			//this will generate a value between 0 and 1(inclusive)
			double randomValue = rnd.nextDouble();
			
			if(randomValue < mutationRate){
				this.gui.message1="Mutating";
				this.gui.board1=boards[i].board;
				this.gui.display1=true;
				
				System.out.println("Mutating ");
				for(int j=0;j<queens;j++){
					System.out.print(boards[i].board[j]+" ");
				}
				System.out.println("\nAfter mutation, we get");
				
				mutate(boards[i]);
				
				this.gui.board2=boards[i].board;
				this.gui.display2=true;
				this.gui.message2="Mutated to";
				this.gui.repaint();
				this.delay(6);
				
				this.gui.display1=false;
				this.gui.display2=false;
				this.gui.message1="";
				this.gui.message2="";
				
				for(int j=0;j<queens;j++){
					System.out.print(boards[i].board[j]+" ");
				}
				System.out.println("\n\n");
			}
		}
		
		Arrays.sort(boards);
		
		if(boards[0].fitness==0){
			return boards[0];
		}
		
		return null;
	}
	
	public ChessBoard generatePopulation(){
		for(int i=0;i<population;i++){
			int[] tempBoard = new int[queens];
			
			//generate a random chessboard
			for(int j=0;j<queens;j++){
				tempBoard[j]=j;
			}
			
			//shuffle
			for(int j=0;j<queens;j++){
				int k=rnd.nextInt(queens);
				
				int tmp=tempBoard[j];
				tempBoard[j]=tempBoard[k];
				tempBoard[k]=tmp;
			}
			
			boards[i]=new ChessBoard(tempBoard, queens);
		}
		
		Arrays.sort(boards);
		
		if(boards[0].fitness==0){
			return boards[0];
		}
		
		return null;
	}
	
	public int[] breed(ChessBoard chromosome1, ChessBoard chromosome2){
		int[] child = new int[queens];
		boolean[] added = new boolean[queens];
		ArrayList<Integer> possible=new ArrayList<Integer>();
		
		this.gui.board1=chromosome1.board;
		this.gui.board2=chromosome2.board;
		this.gui.display1=true;
		this.gui.display2=true;
		this.gui.message1="Breeding these chromosomes";
		this.gui.repaint();
		this.delay(6);
		
		this.gui.message1="";
		this.gui.display1=false;
		this.gui.display2=false;
		
		System.out.println("Children chosen for Crossover are ");
		for(int i=0;i<queens;i++){
			System.out.print(chromosome1.board[i]+" ");
		}
		
		System.out.println("");
		for(int i=0;i<queens;i++){
			System.out.print(chromosome2.board[i]+" ");
		}
		
		System.out.println("\n\n");
		
		for(int i=0;i<queens;i++){
			child[i]=-1;
			added[i]=true;
		}
		
		for(int i=0;i<queens;i++){
	        if(chromosome1.board[i]==chromosome2.board[i]){
	            child[i]=chromosome1.board[i];
	            added[child[i]]=false;    
	        }
	    }
		
	    for(int i=0;i<queens;i++){
	        if(added[i]==true){
	            possible.add(i);
	        }
	    }

	    for(int i=0;i<queens;i++){
	        if(child[i]==-1){
	            //pick a random element from possible
	            int j=rnd.nextInt(possible.size());
	            child[i]=possible.get(j);
	            possible.remove(j);
	        }
	    }
	    
	    this.gui.board1=child;
		this.gui.display1=true;
		this.gui.message1="Evolved child obtained after breeding";
		this.gui.repaint();
		this.delay(6);
		
		this.gui.message1="";
		this.gui.display1=false;
	    
	    System.out.println("Evolved child obtained after breeding ");
		for(int i=0;i<queens;i++){
			System.out.print(child[i]+" ");
		}
		
		System.out.println("\n\n");
		
		return child;
	}
}

