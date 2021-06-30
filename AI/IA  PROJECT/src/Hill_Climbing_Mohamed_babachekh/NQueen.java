package Hill_Climbing_Mohamed_babachekh;

//Class pour N-queens Problem
public class NQueen {
  private int row;
  private int column;

  public NQueen(int row, int column) {
      this.row = row;
      this.column = column;
  }

  public void move () {
      row++;
  }

  public boolean ifConflict(NQueen q){
  	// Vérifier les lignes et les colonnes
      if(row == q.getRow() || column == q.getColumn())
          return true;
   // Vérifier les diagonales
      else if(Math.abs(column-q.getColumn()) == Math.abs(row-q.getRow()))
          return true;
      return false;
  }
  public int getRow() {
      return row;
  }

  public int getColumn() {
      return column;
  }
}