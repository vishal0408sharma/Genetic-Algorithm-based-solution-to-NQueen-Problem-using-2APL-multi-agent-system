

class ChessBoard implements Comparable<ChessBoard> {
	int[] board;
	int fitness;
	int queens;
	
	public ChessBoard(int[] b, int n){
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
	public int compareTo(ChessBoard r) {
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
