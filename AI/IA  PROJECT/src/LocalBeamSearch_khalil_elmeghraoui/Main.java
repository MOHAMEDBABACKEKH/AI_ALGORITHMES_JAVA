package LocalBeamSearch_khalil_elmeghraoui;
import java.util.Arrays;

/**
 * Created by khalil el maghraoui 05/13/2021.
 */
public class Main  {

    public static void main(String[] args) {

        LocalBeamSearch localBeamSearch = new LocalBeamSearch();
        int[] res =  localBeamSearch.solve(8,100,2);
        System.out.println("les cases occupee par les reines ");
        System.out.println(Arrays.toString(res));
        System.out.println("");
        printBoard(res);

    }
    public static void printBoard(int[] res) {
        String[][] board = new String[8][8];
        // Display the board.

        for(int i = 0; i< 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = ". ";
            }
            System.out.println();
        }
        for(int j = 0; j < 8; j++) {
            board[res[j]][j] = "Q";
        }
        for(int i = 0; i< 8; i++) {
            for(int j = 0; j < 8; j++) {
                System.out.print(board[i][j] );
            }
            System.out.println(" \n");

        }

    }

}
