package Algorithm_genetic_Zakaria_Elmahalli;



import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class Genetic {
	
	private int MAX_LENGTH;                 // taille de table par defaut je vais utiliser n = 8
	private int START_SIZE;				    // nombre de population
	private int MAX_EPOCHS;                 // nombre de test
	private double MATING_PROBABILITY;      // probabilité de chromosomes
	private double MUTATION_RATE;           // Mutation 
	private int MIN_SELECT;                 // min de chromosome 
	private int MAX_SELECT;                 // max de chromosome
	private int OFFSPRING_PER_GENERATION;   // nombre de generaration
	private int MINIMUM_SHUFFLES;           
	private int MAXIMUM_SHUFFLES;

	private int nextMutation;              
	private ArrayList<Chromosome> population;
	private ArrayList<Chromosome> solutions;
	private Random rand;
	private int childCount;
	private int mutations;
	private int epoch;
	private int populationSize;

	
	public Genetic(int n) {
		MAX_LENGTH = 8;
		START_SIZE = 40;
		MAX_EPOCHS = 1000;
		MATING_PROBABILITY = 1;
		MUTATION_RATE = 0.001;
		MIN_SELECT = 10; 
		MAX_SELECT = 20;
		OFFSPRING_PER_GENERATION = 20;
		MINIMUM_SHUFFLES = 8; 
		MAXIMUM_SHUFFLES = 20;  
		epoch = 0;
		populationSize = 0;
	}

	//commancer 
	public boolean algorithm() {
		population = new ArrayList<Chromosome>();
		solutions = new ArrayList<Chromosome>();
		rand = new Random();
		nextMutation = 0;
		childCount = 0;                 
		mutations = 0;
		epoch = 0;
		populationSize = 0;

		boolean done = false;
		Chromosome thisChromo = null;
		nextMutation = getRandomNumber(0, (int)Math.round(1.0 / MUTATION_RATE));

		initialize();

		while(!done) {
			populationSize = population.size();

			for(int i = 0; i < populationSize; i++) {
				thisChromo = population.get(i);
				if((thisChromo.getConflicts() == 0)) {			
					done = true;
				}
			}

			if(epoch == MAX_EPOCHS) {							
				done = true;
			}

			getFitness();

			rouletteSelection();

			mating();

			prepNextEpoch();

			epoch++;
			
		}

		if(epoch >= MAX_EPOCHS) {
			System.out.println("No solution found");
			done = false;
		} else {
			populationSize = population.size();					
			for(int i = 0; i < populationSize; i++) {
				thisChromo = population.get(i);
				if(thisChromo.getConflicts() == 0) {
					solutions.add(thisChromo);
					printSolution(thisChromo);
				}
			}
		}
		System.out.println("nouvelle generation");

		
		System.out.println("nombre de mutations: " + mutations + " de " + childCount ); 
		
		return done;
	}

	
	public void mating() {
		int getRand = 0;
        int parentA = 0;
        int parentB = 0;
        int newIndex1 = 0;
        int newIndex2 = 0;
        Chromosome newChromo1 = null;
        Chromosome newChromo2 = null;

        for(int i = 0; i < OFFSPRING_PER_GENERATION; i++) {
            parentA = chooseParent();
            getRand = getRandomNumber(0, 100);
            if(getRand <= MATING_PROBABILITY * 100) {
                parentB = chooseParent(parentA);
                newChromo1 = new Chromosome(MAX_LENGTH);
                newChromo2 = new Chromosome(MAX_LENGTH);
                population.add(newChromo1);
                newIndex1 = population.indexOf(newChromo1);
                population.add(newChromo2);
                newIndex2 = population.indexOf(newChromo2);
                
                partiallyMappedCrossover(parentA, parentB, newIndex1, newIndex2);

                if(childCount - 1 == nextMutation) {
                    exchangeMutation(newIndex1, 1);
                } else if (childCount == nextMutation) {
                    exchangeMutation(newIndex2, 1);
                }

                population.get(newIndex1).computeConflicts();
                population.get(newIndex2).computeConflicts();

                childCount += 2;

                if(childCount % (int)Math.round(1.0 / MUTATION_RATE) == 0) {
                    nextMutation = childCount + getRandomNumber(0, (int)Math.round(1.0 / MUTATION_RATE));
                    
                }
            }
        } // i
	}

	/* croissement de deux chromosome parents.
	 *
	 * @param: parent A
	 * @param: parent B
	 * @param: child A
	 * @param: child B
	 */
	public void partiallyMappedCrossover(int chromA, int chromB, int child1, int child2) {
        int j = 0;
        int item1 = 0;
        int item2 = 0;
        int pos1 = 0;
        int pos2 = 0;
        Chromosome thisChromo = population.get(chromA);
        Chromosome thatChromo = population.get(chromB);
        Chromosome newChromo1 = population.get(child1);
        Chromosome newChromo2 = population.get(child2);
        int crossPoint1 = getRandomNumber(0, MAX_LENGTH - 1);
        int crossPoint2 = getExclusiveRandomNumber(MAX_LENGTH - 1, crossPoint1);
        
        if(crossPoint2 < crossPoint1) {
            j = crossPoint1;
            crossPoint1 = crossPoint2;
            crossPoint2 = j;
        }

        for(int i = 0; i < MAX_LENGTH; i++) {
            newChromo1.setGene(i, thisChromo.getGene(i));
            newChromo2.setGene(i, thatChromo.getGene(i));
        }

        for(int i = crossPoint1; i <= crossPoint2; i++) {
            
            item1 = thisChromo.getGene(i);
            item2 = thatChromo.getGene(i);

            
            for(j = 0; j < MAX_LENGTH; j++) {
                if(newChromo1.getGene(j) == item1) {
                    pos1 = j;
                } else if (newChromo1.getGene(j) == item2) {
                    pos2 = j;
                }
            } 

            if(item1 != item2) {
                newChromo1.setGene(pos1, item2);
                newChromo1.setGene(pos2, item1);
            }

            for(j = 0; j < MAX_LENGTH; j++) {
                if(newChromo2.getGene(j) == item2) {
                    pos1 = j;
                } else if(newChromo2.getGene(j) == item1) {
                    pos2 = j;
                }
            } // j

            if(item1 != item2) {
                newChromo2.setGene(pos1, item1);
                newChromo2.setGene(pos2, item2);
            }

        } // i
	}

	//selection aleatoire
	public int chooseParent() {
        int parent = 0;
        Chromosome thisChromo = null;
        boolean done = false;

        while(!done) {
            parent = getRandomNumber(0, population.size() - 1);
            thisChromo = population.get(parent);
            if(thisChromo.isSelected() == true) {
                done = true;
            }
        }

        return parent;    	
    }    
    
    
    public int chooseParent(int parentA) {
        int parent = 0;
        Chromosome thisChromo = null;
        boolean done = false;

        while(!done) {
            parent = getRandomNumber(0, population.size() - 1);
            if(parent != parentA){
                thisChromo = population.get(parent);
                if(thisChromo.isSelected() == true){
                    done = true;
                }
            }
        }

        return parent;    	
    } 

	
	public void rouletteSelection() {
   	 	int j = 0;
        int populationSize = population.size();
        int maximumToSelect = getRandomNumber(MIN_SELECT, MAX_SELECT);
        double genTotal = 0.0;
        double selTotal = 0.0;
        double rouletteSpin = 0.0;
        Chromosome thisChromo = null;
        Chromosome thatChromo = null;
        boolean done = false;
        
        for(int i = 0; i < populationSize; i++) {												//get total fitness
            thisChromo = population.get(i);
            genTotal += thisChromo.getFitness();
        }
        
        genTotal *= 0.01;															

        for(int i = 0; i < populationSize; i++) {
            thisChromo = population.get(i);
            thisChromo.setSelectionProbability(thisChromo.getFitness() / genTotal);		//set selection probability. the more fit the better selection probability
        }
        
        for(int i = 0; i < maximumToSelect; i++) {										//selects parents
            rouletteSpin = getRandomNumber(0, 99);
            j = 0;
            selTotal = 0;
            done = false;
            while(!done) {
                thisChromo = population.get(j);
                selTotal += thisChromo.getSelectionProbability();
                if(selTotal >= rouletteSpin) {
					 if(j == 0) {
					    thatChromo = population.get(j);
					 } else if(j >= populationSize - 1) {
					     thatChromo = population.get(populationSize - 1);
					 } else {
					     thatChromo = population.get(j-1);
					 }
					thatChromo.setSelected(true);
					done = true;
                } else {
                    j++;
                }
            }
        }
	}

	
	public void getFitness() {
		int populationSize = population.size();
		Chromosome thisChromo = null;
		double bestScore = 0;
		double worstScore = 0;

		worstScore = Collections.max(population).getConflicts();

		bestScore = worstScore - Collections.min(population).getConflicts();

		for(int i = 0; i < populationSize; i++) {
			thisChromo = population.get(i);
			thisChromo.setFitness((worstScore - thisChromo.getConflicts()) * 100.0 / bestScore);
		}   
	}

	
	public void prepNextEpoch() {
		int populationSize = 0;
		Chromosome thisChromo = null;

		populationSize = population.size();
		for(int i = 0; i < populationSize; i++) {
			thisChromo = population.get(i);
			thisChromo.setSelected(false);
		}   
	}

	
	public void printSolution(Chromosome solution) {
		String board[][] = new String[MAX_LENGTH][MAX_LENGTH];

		for(int x = 0; x < MAX_LENGTH; x++) {
			for(int y = 0; y < MAX_LENGTH; y++) {
			board[x][y] = "";
			}
		}

		for(int x = 0; x < MAX_LENGTH; x++) {
			board[x][solution.getGene(x)] = "Q";
		}

		System.out.println("Table:");
		for(int y = 0; y < MAX_LENGTH; y++) {
			for(int x = 0; x < MAX_LENGTH; x++) {
				if(board[x][y] == "Q") {
					System.out.print("Q ");
				} else {
					System.out.print(". ");
				}
			}
			System.out.print("\n");
		}
	}

	public void initialize() {
		int shuffles = 0;
		Chromosome newChromo = null;
		int chromoIndex = 0;

		for(int i = 0; i < START_SIZE; i++)  {
			newChromo = new Chromosome(MAX_LENGTH);
			population.add(newChromo);
			chromoIndex = population.indexOf(newChromo);

			shuffles = getRandomNumber(MINIMUM_SHUFFLES, MAXIMUM_SHUFFLES);
			exchangeMutation(chromoIndex, shuffles);
			population.get(chromoIndex).computeConflicts();
		}
	}

	
	public void exchangeMutation(int index, int exchanges) {
		int tempData = 0;
		int gene1 = 0;
		int gene2 = 0;
		Chromosome thisChromo = null;
		thisChromo = population.get(index);

		for(int i = 0; i < exchanges; i++) {
			gene1 = getRandomNumber(0, MAX_LENGTH - 1);
			gene2 = getExclusiveRandomNumber(MAX_LENGTH - 1, gene1);

			tempData = thisChromo.getGene(gene1);
			thisChromo.setGene(gene1, thisChromo.getGene(gene2));
			thisChromo.setGene(gene2, tempData);
		}
		mutations++;
	}

	public int getExclusiveRandomNumber(int high, int except) {
		boolean done = false;
		int getRand = 0;

		while(!done) {
			getRand = rand.nextInt(high);
			if(getRand != except){
				done = true;
			}
		}
		return getRand;  
	}

	
	public int getRandomNumber(int low, int high) {
   		return (int)Math.round((high - low) * rand.nextDouble() + low);
	}
   
	
	public ArrayList<Chromosome> getSolutions() {
		return solutions;
	}
	
	
	public int getEpoch() {
		return epoch;
	}
	
	
	public int getPopSize() {
		return population.size();
	}
	
	/* gets the start size
	 *
	 * @return: start size
	 */ 
	public int getStartSize() {
		return START_SIZE;
	}
	
	
	public double getMatingProb() {
		return MATING_PROBABILITY;
	}
	
	
	public double getMutationRate() {
		return MUTATION_RATE;
	}
	
	
	public int getMinSelect() {
		return MIN_SELECT;
	}
	
	
	public double getMaxSelect() {
		return MAX_SELECT;
	}
	
	
	public double getOffspring() {
		return OFFSPRING_PER_GENERATION;
	}
	
	
	public int getMaxEpoch() {
		return MAX_EPOCHS;
	}

	
	public int getShuffleMin() {
		return MINIMUM_SHUFFLES;
	}

	
	public int getShuffleMax() {
		return MAXIMUM_SHUFFLES;
	}

	
	public void setMutation(double newMutation) {
		this.MUTATION_RATE = newMutation;
	}

		
	public void setEpoch(int newMaxEpoch) {
		this.MAX_EPOCHS = newMaxEpoch;
	}

}