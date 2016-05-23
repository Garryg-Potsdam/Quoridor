package gameboard;

public class Test{
    public static void main(String[] args){
	Board b = new Board();
	b.placePawn(1, new Coordinate(4, 0));
//	PawnMove best = b.bestMove(1);
	int[][] distances = b.fillDistances(new Coordinate(4, 0));
	for(int i = 0; i < 9; i++){
	    for(int j = 0; j < 9; j++)
		System.out.print(distances[j][i] + " ");
	    System.out.println();
	}
//	System.out.println(best.destination.column + " " + best.destination.row);
    }
}