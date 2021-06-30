package Hill_Climbing_Mohamed_babachekh;



import java.util.Scanner;

import java.util.Random;


public class HillClimbingRandomRestart {
    private static int n ;
    private static int stepsClimbedAfterLastRestart = 0;
    private static int stepsClimbed =0;
    private static int heuristic = 0;
    private static int randomRestarts = 0;

  //M�thode pour cr�er un nouveau tableau al�atoire
    public static NQueen[] generateBoard() {
        NQueen[] startBoard = new NQueen[n];
        Random rndm = new Random();
        for(int i=0; i<n; i++){
            startBoard[i] = new NQueen(rndm.nextInt(n), i);
        }
        return startBoard;
    }

    //M�thode pour imprimer l'�tat actuel
    private static void printState (NQueen[] state) {
    	//Cr�ation d'une planche temporaire � partir de la planche actuelle
        int[][] tempBoard = new int[n][n];
        for (int i=0; i<n; i++) {
        	//Obtenir les positions de la Reine � partir du tableau actuel et mettre ces positions � 1 dans le tableau temporaire.
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

 // M�thode pour trouver les Heuristiques d'un �tat
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

 // M�thode pour obtenir la planche suivante avec une heuristique plus basse
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
        //  It�rer chaque colonne
        for (int i=0; i<n; i++) {
            if (i>0)
                tmpBoard[i-1] = new NQueen (presentBoard[i-1].getRow(), presentBoard[i-1].getColumn());
            tmpBoard[i] = new NQueen (0, tmpBoard[i].getColumn());
            //   It�rer chaque ligne
            for (int j=0; j<n; j++) {
            	//R�cup�rer l'heuristique
                tempH = findHeuristic(tmpBoard);
              //V�rifier si l'etat temporaire est meilleure que la meilleure table.
                if (tempH < bestHeuristic) {
                    bestHeuristic = tempH;
                 // Copier l'etat temp comme meilleure table
                    for (int k=0; k<n; k++) {
                        nextBoard[k] = new NQueen(tmpBoard[k].getRow(), tmpBoard[k].getColumn());
                    }
                }
                //D�placer la reine
                if (tmpBoard[i].getRow()!=n-1)
                    tmpBoard[i].move();
            }
        }
        //V�rifier si le bord actuel et le meilleur bord trouv� ont la m�me heuristique.
        //Ensuite, g�n�rer al�atoirement une nouvelle planche et l'assigner � la meilleure planche.
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
        System.out.println("La solution pour "+n+" les reines en utilisant l'escalade de collines avec un red�marrage al�atoire:");
      //Cr�ation du tableau initial
        NQueen[] presentBoard = generateBoard();
        presentHeuristic = findHeuristic(presentBoard);
     // tester si la solution est la bonne, c'est-�-dire si l'etat actuelle est l'etat de la solution.
        while (presentHeuristic != 0) {
        	// Obtenir le tableau suivant
        	System.out.println(i++);
            printState(presentBoard);
            presentBoard = nextBoard(presentBoard);
            presentHeuristic  = heuristic;
        }
      //Imprimer la solution
        printState(presentBoard);
        System.out.println("\nNombre total de marches escalad�es: " + stepsClimbed);
        System.out.println("Nombre de red�marrages al�atoires: " + randomRestarts);
        System.out.println("Marches franchies apr�s le dernier red�marrage: " + stepsClimbedAfterLastRestart);
    }
}