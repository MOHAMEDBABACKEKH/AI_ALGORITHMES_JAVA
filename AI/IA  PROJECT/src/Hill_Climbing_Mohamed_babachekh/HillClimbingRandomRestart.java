package Hill_Climbing_Mohamed_babachekh;



import java.util.Scanner;

import java.util.Random;


public class HillClimbingRandomRestart {
    private static int n ;
    private static int stepsClimbedAfterLastRestart = 0;
    private static int stepsClimbed =0;
    private static int heuristic = 0;
    private static int randomRestarts = 0;

  //Méthode pour créer un nouveau tableau aléatoire
    public static NQueen[] generateBoard() {
        NQueen[] startBoard = new NQueen[n];
        Random rndm = new Random();
        for(int i=0; i<n; i++){
            startBoard[i] = new NQueen(rndm.nextInt(n), i);
        }
        return startBoard;
    }

    //Méthode pour imprimer l'état actuel
    private static void printState (NQueen[] state) {
    	//Création d'une planche temporaire à partir de la planche actuelle
        int[][] tempBoard = new int[n][n];
        for (int i=0; i<n; i++) {
        	//Obtenir les positions de la Reine à partir du tableau actuel et mettre ces positions à 1 dans le tableau temporaire.
            tempBoard[state[i].getRow()][state[i].getColumn()]=1;
        }
        System.out.println();
        for (int i=0; i<n; i++) {
            for (int j= 0; j < n; j++) {
                System.out.print(tempBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

 // Méthode pour trouver les Heuristiques d'un état
    public static int findHeuristic (NQueen[] state) {
        int heuristic = 0;
        for (int i = 0; i< state.length; i++) {
            for (int j=i+1; j<state.length; j++ ) {
                if (state[i].ifConflict(state[j])) {
                    heuristic++;
                }
            }
        }
        return heuristic;
    }

 // Méthode pour obtenir la planche suivante avec une heuristique plus basse
    public static NQueen[] nextBoard (NQueen[] presentBoard) {
        NQueen[] nextBoard = new NQueen[n];
        NQueen[] tmpBoard = new NQueen[n];
        int presentHeuristic = findHeuristic(presentBoard);
        int bestHeuristic = presentHeuristic;
        int tempH;

        for (int i=0; i<n; i++) {
        	// Copier le tableau actuel comme meilleur tableau et tableau temporaire
            nextBoard[i] = new NQueen(presentBoard[i].getRow(), presentBoard[i].getColumn());
            tmpBoard[i] = nextBoard[i];
        }
        //  Itérer chaque colonne
        for (int i=0; i<n; i++) {
            if (i>0)
                tmpBoard[i-1] = new NQueen (presentBoard[i-1].getRow(), presentBoard[i-1].getColumn());
            tmpBoard[i] = new NQueen (0, tmpBoard[i].getColumn());
            //   Itérer chaque ligne
            for (int j=0; j<n; j++) {
            	//Récupérer l'heuristique
                tempH = findHeuristic(tmpBoard);
              //Vérifier si l'etat temporaire est meilleure que la meilleure table.
                if (tempH < bestHeuristic) {
                    bestHeuristic = tempH;
                 // Copier l'etat temp comme meilleure table
                    for (int k=0; k<n; k++) {
                        nextBoard[k] = new NQueen(tmpBoard[k].getRow(), tmpBoard[k].getColumn());
                    }
                }
                //Déplacer la reine
                if (tmpBoard[i].getRow()!=n-1)
                    tmpBoard[i].move();
            }
        }
        //Vérifier si le bord actuel et le meilleur bord trouvé ont la même heuristique.
        //Ensuite, générer aléatoirement une nouvelle planche et l'assigner à la meilleure planche.
        if (bestHeuristic == presentHeuristic) {
            randomRestarts++;
            stepsClimbedAfterLastRestart = 0;
            nextBoard = generateBoard();
            heuristic = findHeuristic(nextBoard);
        } else
            heuristic = bestHeuristic;
        stepsClimbed++;
        stepsClimbedAfterLastRestart++;
        return nextBoard;
    }

    public static void main(String[] args) {
        int presentHeuristic;
        int i=0;
        Scanner s=new Scanner(System.in);
        while (true){
            System.out.println("Entrez le nombre de reines :");
            n = s.nextInt();
            if ( n == 2 || n ==3) {
                System.out.println("No Solution possible pour "+n+" reines. Please entrer un autre number");
            }
            else
                break;
        }
        System.out.println("La solution pour "+n+" les reines en utilisant l'escalade de collines avec un redémarrage aléatoire:");
      //Création du tableau initial
        NQueen[] presentBoard = generateBoard();
        presentHeuristic = findHeuristic(presentBoard);
     // tester si la solution est la bonne, c'est-à-dire si l'etat actuelle est l'etat de la solution.
        while (presentHeuristic != 0) {
        	// Obtenir le tableau suivant
        	System.out.println(i++);
            printState(presentBoard);
            presentBoard = nextBoard(presentBoard);
            presentHeuristic  = heuristic;
        }
      //Imprimer la solution
        printState(presentBoard);
        System.out.println("\nNombre total de marches escaladées: " + stepsClimbed);
        System.out.println("Nombre de redémarrages aléatoires: " + randomRestarts);
        System.out.println("Marches franchies après le dernier redémarrage: " + stepsClimbedAfterLastRestart);
    }
}