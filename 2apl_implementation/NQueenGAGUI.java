
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.*;
import javax.swing.*;

import apapl.Environment;
import apapl.ExternalActionFailedException;
import apapl.data.APLFunction;
import apapl.data.APLIdent;
import apapl.data.APLList;
import apapl.data.APLNum;
import apapl.data.Term;

public class NQueenGAGUI extends Environment {
	private int population;
	private int iterations;
	private int queens;
	private int counter;
	private double mutationRate;
	
	private HashMap<String, Integer> agents = new HashMap<String, Integer>();	
	private HashMap<Integer, String> agents2 = new HashMap<Integer, String>();
	
	Random rnd = new Random();
	ChessBoard[] boards;
	ChessBoard solution;
	GAGUI gui;
	
	public NQueenGAGUI(){
		int pop;
		int iter;
		int n;
		double mR;
		
		String s = (String)JOptionPane.showInputDialog(null,"Input the size of initial population","N-Queens puzzle",JOptionPane.PLAIN_MESSAGE,null,null,"200");
		pop = Integer.parseInt(s);
		
		s = (String)JOptionPane.showInputDialog(null,"No. of iterations","N-Queens puzzle",JOptionPane.PLAIN_MESSAGE,null,null,"200");
		iter = Integer.parseInt(s);
		
		s = (String)JOptionPane.showInputDialog(null,"No. of queens","N-Queens puzzle",JOptionPane.PLAIN_MESSAGE,null,null,"8");
		n = Integer.parseInt(s);
		
		s = (String)JOptionPane.showInputDialog(null,"Mutation Rate ","N-Queens puzzle",JOptionPane.PLAIN_MESSAGE,null,null,"0.2");
		mR = Double.parseDouble(s);
		
		population=pop;
		iterations=iter;
		queens=n;
		mutationRate=mR;
		counter=1;
		
		boards=new ChessBoard[pop];
		gui=new GAGUI(n);
		
		System.out.println("Generating population");
		this.gui.message="Generating Population";
		
		this.gui.repaint();
		this.delay(4);
		
		solution = this.generatePopulation();
		System.out.println("Initial population generated");
		this.gui.message="Initial population generated";
		this.gui.repaint();
		this.delay(4);
		
		if(solution!=null){
			System.out.println("Solution Found");
			
			this.gui.message1="Solution Found";
			this.gui.board1=solution.board;
			this.gui.display1=true;
			this.gui.repaint();
			
			for(int i=0;i<this.queens;i++){
				System.out.print(solution.board[i]+" ");
			}
			System.out.println("");
			
			System.out.print("\n");
		} else {
			System.out.print("No solution found\n");
		}
	}
	/*
	public void execute() {
		if(solution==null && counter<=iterations){
			//search for the solution
			System.out.print("Executing iteration " + (counter));
			this.gui.message="Executing iteration "+(counter);
			solution = this.evolve();
			
			counter++;
		}
	}
	*/
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
	
	public void mutate(ChessBoard chromosome){
		int i=rnd.nextInt(queens);
		int j=rnd.nextInt(queens);
		
		int temp=chromosome.board[i];
		chromosome.board[i]=chromosome.board[j];
		chromosome.board[j]=temp;
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

	public static void main(String[] args){
	}
	
	public void delay(int seconds){
    	try {
    	    Thread.sleep(seconds*500);
    	} catch(InterruptedException ex) {
    	    Thread.currentThread().interrupt();
    	}
    }
	
	protected void addAgent(String agName) {
		System.out.println("env> agent " + agName + " has entered in the environment.");
		
		agents.put(agName,agents.size()); 	
		agents2.put(agents.size()-1, agName);
	}
	
	public Term perceive(String agName) throws ExternalActionFailedException {
		System.out.println("Invoking percieve");
		Integer agent = agents.get(agName);								
		LinkedList<Term> terms = new LinkedList<Term>();
	
		if(agent!=null){
			for(int q = 0; q < this.queens; q++){
				//store the configuration of the chessboard
				terms.add(new APLNum(this.boards[0].board[q]));
			}
		}
		
		return new APLList(terms);				
	}
	
	public Term getFitness(String agName) throws ExternalActionFailedException {
		int fitness=boards[0].fitness;
		
		return new APLNum(fitness);
	}
	
	public Term run(String agName) throws ExternalActionFailedException {
		if(solution==null && counter<=iterations){
			//search for the solution
			System.out.print("Executing iteration " + (counter));
			this.gui.message="Executing iteration "+(counter);
			solution = this.evolve();
			
			counter++;
		}
		
		return new APLNum(0);
	}
	
	public Term iterationsRemaining(String agName) throws ExternalActionFailedException {
		if(counter<=iterations)
			return new APLNum(1);
		
		return new APLNum(0);
	}
}

