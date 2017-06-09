#include<bits/stdc++.h>
using namespace std;

int nChromosomes;
int iterations;
int queens;
float mutationRate;

vector<vector<int> > population;
vector<int> fitness;

void generateInitialPopulation(){
    vector<int> chromosome(queens);
    int i,k;
    
    for(k=0;k<nChromosomes;k++){
        for(i=0;i<queens;i++){
            chromosome[i]=i;
        }

        //shuffle randomly
        for(i=0;i<queens;i++){
            int j=rand()%queens;
            swap(chromosome[i],chromosome[j]);
        }

        for(i=0;i<queens;i++){
            population[k][i]=chromosome[i];
        }
    }
}

bool checkForClash(int i, int j, int p, int q){
    //two queens in the same row
    if(j==q)
        return true;
   
    //backward diagonal clash
    if((i-j)==(p-q))
        return true;
    
    //forward diagonal clash
    if((i+j)==(p+q))
        return true;

    return false;
}

int cost(vector<int> &a){
    int cost=0;
    int i,j,p,q;

    for(i=0;i<queens;i++)
    {    
        j=a[i]; 
        
        p=i+1;
        while(p<queens){
            q=a[p];

            if(checkForClash(i,j,p,q)){
                cost++;
            }

            p++;
        }
    }
    
    return cost;
}

void findFitness(){
    int k;

    for(k=0;k<nChromosomes;k++){

        //calculate fitness/cost for kth chromosome that has already been generated
        fitness[k]=cost(population[k]);
    }
}

//generate a random float value between 0 and 1
float randomFloat() {
    float min=0.000;
    float max=1.000;
    float random = ((float) rand()) / (float) RAND_MAX;

    float range = max - min;  
    return (random*range) + min;
}

struct compare{ 
    bool operator() (pair<int, int> &l, pair<int, int> &r) const { 
        return l.second > r.second;
    }
};

void sortPopulation(){
    int i,j,k;
    
    //min priority queue
    priority_queue<pair<int, int>, vector<pair<int, int> >, compare > pq;

    for(i=0;i<nChromosomes;i++){
        pq.push(make_pair(i,fitness[i]));
    }

    pair<int, int> temp;
    vector<vector<int> > newPopulation=population;

    for(k=0;k<nChromosomes;k++){
        temp=pq.top();     
        pq.pop();
        
        j=temp.first;
        population[k]=newPopulation[j];
    }

    sort(fitness.begin(), fitness.end());
}

vector<int> crossOver(vector<int> &p1, vector<int> &p2){
    int i,j;

    cout<<endl<<"Children chosen for crossover "<<endl;
    for(i=0;i<queens;i++){
        cout<<p1[i]<<"  ";
    }
    cout<<endl;
    
    for(i=0;i<queens;i++){
        cout<<p2[i]<<"  ";
    }
    cout<<endl<<endl;

    vector<int> child(queens,-1);
    vector<bool> added(queens,true);
    vector<int> possible;

    for(i=0;i<queens;i++){
        if(p1[i]==p2[i]){
            child[i]=p1[i];
            added[child[i]]=false;    
        }
    }

    for(i=0;i<queens;i++){
        if(added[i]){
            possible.push_back(i);
        }
    }

    for(i=0;i<queens;i++){
        if(child[i]==-1){
            //pick a random element from possible
            j=rand()%(possible.size());
            child[i]=possible[j];
            possible.erase(possible.begin()+j);
        }
    }

    cout<<"Child after breeding two chromosomes "<<endl;
    for(i=0;i<queens;i++){
        cout<<child[i]<<"  ";
    }

    cout<<endl;

    return child;
}

void mutation(vector<int> &child){
    int i=rand()%queens;
    int j=rand()%queens;

    int temp=child[i];
    child[i]=child[j];
    child[j]=temp;
}

int main(){
    srand(time(0));
    int i,j,k;
    
    cout<<"Input initial population  ";
    cin>>nChromosomes;

    cout<<"Input no. of iterations  ";
    cin>>iterations;

    cout<<"Input no. of queens ";
    cin>>queens;

    cout<<"Input mutation rate  ";
    cin>>mutationRate;

    population.resize(nChromosomes, vector<int>(queens));
    fitness.resize(nChromosomes);
    
    vector<int> firstParent, secondParent;
    vector<int> child;
    int c1,c; 

    generateInitialPopulation();
    findFitness();
    sortPopulation();

    cout<<"Initial Population Generated"<<endl;

    j=1;
    firstParent=population[0];
    c1=fitness[0];

    for(i=1;i<=iterations;i++){
        cout<<endl<<"Iteration "<<i<<endl;
        c=cost(firstParent);

        if(c==0){
            cout<<endl<<"Solution found"<<endl<<endl;
            for(k=0;k<queens;k++){
                cout<<firstParent[k]<<"  ";
            }

            cout<<endl;
            
            return 0;
        }
        
        if(randomFloat() < mutationRate){
            mutation(firstParent);
            
            cout<<endl<<"child after mutation"<<endl<<endl;
            for(k=0;k<queens;k++){
                cout<<firstParent[k]<<"  ";
            }
            cout<<endl;
        }
       
        secondParent=population[j];
        j=(j+1)%nChromosomes;
        
        firstParent=crossOver(firstParent, secondParent);
    }

    cout<<"No solution found"<<endl;

    return 0;
}

