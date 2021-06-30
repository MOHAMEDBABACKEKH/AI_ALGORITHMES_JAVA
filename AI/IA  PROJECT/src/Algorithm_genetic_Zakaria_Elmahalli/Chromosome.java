package Algorithm_genetic_Zakaria_Elmahalli;

public class Chromosome implements Comparable<Chromosome>{
	private int MAX_LENGTH; 					//taille
	private int[] gene; 						//localisation de chaque queen
	private double fitness;						//fitness
	private int conflicts; 						//numero d'enemies
	private boolean selected; 					//les selections
	private double selectionProbability; 		//la probablilité de selection
	
	//initialisation de Chromosome
	public Chromosome(int n) {
		MAX_LENGTH = n;
		gene = new int[MAX_LENGTH];
		fitness = 0.0;
		conflicts = 0;
		selected = false;
		selectionProbability = 0.0;
		
		initChromosome();
	}

	//comparaison des chromosomes
	public int compareTo(Chromosome c) {
		return this.conflicts - c.getConflicts();
	}

	//calculer le conflit
	public void computeConflicts() { 
		String board[][] = new String[MAX_LENGTH][MAX_LENGTH]; //initialisation de la table
		int x = 0; 
        int y = 0; 
        int tempx = 0; 
        int tempy = 0;
        
        int dx[] = new int[] {-1, 1, -1, 1,-1, 1, -1, 1}; //cherhcer dans le diagonal
        int dy[] = new int[] {-1, 1, 1, -1,-1, 1, -1, 1}; 
        
        boolean done = false; //stop la recherche
        int conflicts = 0; //nombre de conflit retourner
        
        clearBoard(board); //suprimer les reines
        plotQueens(board); // ajouter les reines
 

        for(int i = 0; i < MAX_LENGTH; i++) {
            x = i;
            y = gene[i];

            //tester le diagonal
            for(int j = 0; j < 8; j++) { 
                tempx = x;
                tempy = y; 
                done = false;
                
                while(!done) {
                    tempx += dx[j];
                    tempy += dy[j];
                    
                    if((tempx < 0 || tempx >= MAX_LENGTH) || (tempy < 0 || tempy >= MAX_LENGTH)) { //if exceeds board
                        done = true;
                    } else {
                        if(board[tempx][tempy].equals("Q")) {
                            conflicts++;
                        }
                    }
                }
            }
        }

        this.conflicts = conflicts; //set conflicts of this chromosome
        
	}

	
	public void plotQueens(String[][] board) {
        for(int i = 0; i < MAX_LENGTH; i++) {
            board[i][gene[i]] = "Q";
        }
	}

	
	public void clearBoard(String[][] board) {
		for (int i = 0; i < MAX_LENGTH; i++) {
			for (int j = 0; j < MAX_LENGTH; j++) {
				board[i][j] = "";
			}
		}
	}

	//initilaiser les reines dans la table
	public void initChromosome() {
		for(int i = 0; i < MAX_LENGTH; i++) {
			gene[i] = i;
		}
	}
	
	
	public int getGene(int index) {
		return gene[index];
	}

	
	public void setGene(int index, int position) {
		this.gene[index] = position;
	}
	
	
	public double getFitness() {
		return fitness;
	}
	
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	
	public int getConflicts() {
		return conflicts;
	}
	
	
	public void setConflicts(int conflicts) {
		this.conflicts = conflicts;
	}
	
	
	public boolean isSelected() {
		return selected;
	}
	
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
	public double getSelectionProbability() {
		return selectionProbability;
	}
	
	
	public void setSelectionProbability(double selectionProbability) {
		this.selectionProbability = selectionProbability;
	}
	
	 
	public int getMaxLength() {
	   return MAX_LENGTH;
	}
}