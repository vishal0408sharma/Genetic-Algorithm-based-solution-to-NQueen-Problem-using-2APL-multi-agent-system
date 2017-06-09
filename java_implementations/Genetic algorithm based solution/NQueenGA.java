package GA;
import java.util.*;

public class NQueenGA {
	private int population;
	private int iterations;
	private int queens;
	private double mutationRate;
	
	Random rnd = new Random();
	Chessboard[] boards;
	
	public NQueenGA(int pop, int iter, int n, double mR){
		population=pop;
		iterations=iter;
		queens=n;
		mutationRate=mR;
		
		boards=new Chessboard[pop];
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
		
		NQueenGA driver = new NQueenGA(population, iterations, queens, mutationRate);
		Chessboard solution = driver.generatePopulation();
		
		for(int i=0;i<driver.iterations && solution==null;i++){
			//search for the solution
			solution = driver.evolve();
		}
		
		if(solution!=null){
			System.out.println("Solution Found");
			for(int i=0;i<queens;i++){
				System.out.print(solution.board[i]+" ");
			}
			
			System.out.print("\n");
		} else {
			System.out.print("No solution found\n");
		}
		
		keyboard.close();
	}
	
	public void mutate(Chessboard chromosome){
		int i=rnd.nextInt(queens);
		int j=rnd.nextInt(queens);
		
		int temp=chromosome.board[i];
		chromosome.board[i]=chromosome.board[j];
		chromosome.board[j]=temp;
	}
	
	public Chessboard evolve(){
		for(int i=0; i<population/2; i++){
			Chessboard child = new Chessboard(breed(boards[i], boards[i+1]), queens);
			boards[i]=child;
		}
		
		for(int i = 1; i < population; i++){
			//this will generate a value between 0 and 1(inclusive)
			double randomValue = rnd.nextDouble();
			
			if(randomValue < mutationRate){
				mutate(boards[i]);
			}
		}
		
		Arrays.sort(boards);
		
		if(boards[0].fitness==0){
			return boards[0];
		}
		
		return null;
	}
	
	public Chessboard generatePopulation(){
		for(int i=0;i<population;i++){
			int[] tempBoard = new int[queens];
			
			//generate a random Chessboard
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
			
			boards[i]=new Chessboard(tempBoard, queens);
		}
		
		Arrays.sort(boards);
		
		if(boards[0].fitness==0){
			return boards[0];
		}
		
		return null;
	}
	
	public int[] breed(Chessboard chromosome1, Chessboard chromosome2){
		int[] child = new int[queens];
		boolean[] added = new boolean[queens];
		ArrayList<Integer> possible=new ArrayList<Integer>();
		
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
		
		return child;
	}
}

class Chessboard implements Comparable<Chessboard> {
	int[] board;
	int fitness;
	int queens;
	
	public Chessboard(int[] b, int n){
		board=b;
		queens=n;
		fitness=calculateFitness();
	}
	
	public int calculateFitness(){
		int fitness=0;
		for(int i=1;i<queens;i++){
			for(int j=0;j<i;j++){
				fitness=(fitness+collision(i,board[i],j,board[j]));
			}
		}
		
		return fitness;
	}
	
	public int collision(int i1, int j1, int i2, int j2){
		int result=0;
		if(j1==j2)
			return 1;
		
		if((i1-j1)==(i2-j2)){
			return 1;
		}
		
		if((i1+j1)==(i2+j2)){
			return 1;
		}
		
		return result;
	}
	
	@Override
	public int compareTo(Chessboard r) {
		if(this.fitness > r.fitness)
			return 1;
		else if(this.fitness < r.fitness)
			return -1;
		else
			return 0;
	}
	
	public int[] getBoard(){
		return board;
	}
	
	public int getFitness(){
		return fitness;
	}
}
