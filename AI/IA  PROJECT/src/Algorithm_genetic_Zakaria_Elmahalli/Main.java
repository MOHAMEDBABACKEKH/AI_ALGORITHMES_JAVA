package Algorithm_genetic_Zakaria_Elmahalli;


public class Main {
	Writer logWriter;
	Genetic ga;
	int MAX_RUN;
	int MAX_LENGTH;
	long[] runtimes;

	/* initialisation de TesterGA class
	 *
	 */
	public Main() {
		logWriter = new Writer();
		MAX_RUN = 50;
		runtimes = new long[MAX_RUN];
	}

	
	public void test(int maxLength, double mutationRate, int maxEpoch) {
		MAX_LENGTH = maxLength;
		ga = new Genetic(MAX_LENGTH);										
		ga.setMutation(mutationRate);
		ga.setEpoch(maxEpoch);
		long testStart = System.nanoTime();
		String filepath = "GA-N"+MAX_LENGTH+"-"+mutationRate+"-"+maxEpoch+".txt";
		long startTime = 0;
        long endTime = 0;
        long totalTime = 0;
        int fail = 0;
        int success = 0;
        
		logParameters();
        
        for(int i = 0; i < MAX_RUN; ) {												
        	startTime = System.nanoTime();
        	if(ga.algorithm()) {
        		endTime = System.nanoTime();
        		totalTime = endTime - startTime;
        		
        		System.out.println("----------");
        		System.out.println("commancer "+(i+1));
        
            	
            	
            	runtimes[i] = totalTime;
            	i++;
            	success++;
            	
            	
            	
            	for(Chromosome c: ga.getSolutions()) {								
					logWriter.add(c);
					logWriter.add("");
    			}
        	} else {																
        		fail++;
        		System.out.println("Fail!");
        	}
        	
        	if(fail >= 100) {
        		System.out.println("Cannot find solution with these params");
        		break;
        	}
        	startTime = 0;															
        	endTime = 0;
        	totalTime = 0;
        }
	
        System.out.println("Number of Success: " +success);
        System.out.println("Number of failures: "+fail);
        logWriter.add("Runtime summary");
        logWriter.add("");
        
		for(int x = 0; x < runtimes.length; x++){									
			logWriter.add(Long.toString(runtimes[x]));
		}
		
		long testEnd = System.nanoTime();
		logWriter.add(Long.toString(testStart));
		logWriter.add(Long.toString(testEnd));
		logWriter.add(Long.toString(testEnd - testStart));
		
      
       	logWriter.writeFile(filepath);
       //	printRuntimes();
	}

	public void logParameters() {
        logWriter.add("Genetic Algorithm");
        logWriter.add("Parameters");
        logWriter.add((String)("MAX_LENGTH/N: "+MAX_LENGTH));
        logWriter.add((String)("STARTING_POPULATION: "+ga.getStartSize()));
        logWriter.add((String)("MAX_EPOCHS: "+ga.getMaxEpoch()));
        logWriter.add((String)("MATING_PROBABILITY: "+ga.getMatingProb()));
        logWriter.add((String)("MUTATION_RATE: "+ga.getMutationRate()));
        logWriter.add((String)("MIN_SELECTED_PARENTS: "+ga.getMinSelect()));
        logWriter.add((String)("MAX_SELECTED_PARENTS: "+ga.getMaxSelect()));
        logWriter.add((String)("OFFSPRING_PER_GENERATION: "+ga.getOffspring()));
        logWriter.add((String)("MINIMUM_SHUFFLES: "+ga.getShuffleMin()));
        logWriter.add((String)("MAXIMUM_SHUFFLES: "+ga.getShuffleMax()));
        logWriter.add("");
	}

	

	public static void main(String args[]) {
		Main tester = new Main();

		tester.test(4, 0.001, 1000);
/*		tester.test(8, 0.001, 1000);
		tester.test(12, 0.001, 1000);
		tester.test(16, 0.001, 1000);
		tester.test(20, 0.001, 1000);
		
		tester.test(20, 0.001, 1000);
		tester.test(20, 0.005, 1000);
		tester.test(20, 0.01, 1000);
		tester.test(20, 0.05, 1000);
		tester.test(20, 0.1, 1000);
		
		tester.test(16, 0.001, 5000);
		tester.test(16, 0.005, 5000);
		tester.test(16, 0.01, 5000);
		tester.test(16, 0.05, 5000);
		tester.test(16, 0.1, 5000);
		
		tester.test(20, 0.001, 5000);
		tester.test(20, 0.005, 5000);
		tester.test(20, 0.01, 5000);
		tester.test(20, 0.05, 5000);
		tester.test(20, 0.1, 5000);
		
 		tester.test(16, 0.001, 1000);
		tester.test(16, 0.005, 1000);
		tester.test(16, 0.01, 1000);
		tester.test(16, 0.05, 1000);
		tester.test(16, 0.1, 1000);
			
		tester.test(16, 0.001, 5000);
		tester.test(16, 0.005, 5000);
		tester.test(16, 0.01, 5000);
		tester.test(16, 0.05, 5000);
		tester.test(16, 0.1, 5000);

		tester.test(16, 0.001, 10000);
		tester.test(16, 0.005, 10000);
		tester.test(16, 0.01, 10000);
		tester.test(16, 0.05, 10000);
		tester.test(16, 0.1, 10000);

		tester.test(20, 0.001, 1000);
		tester.test(20, 0.005, 1000);
		tester.test(20, 0.01, 1000);
		tester.test(20, 0.05, 1000);
		tester.test(20, 0.1, 1000);
		
		tester.test(20, 0.001, 5000);
		tester.test(20, 0.005, 5000);
		tester.test(20, 0.01, 5000);
		tester.test(20, 0.05, 5000);
		tester.test(20, 0.1, 5000);

		tester.test(20, 0.001, 10000);
		tester.test(20, 0.005, 10000);
		tester.test(20, 0.01, 10000);
		tester.test(20, 0.05, 10000);
		tester.test(20, 0.1, 10000);	
*/
		
	}
}
