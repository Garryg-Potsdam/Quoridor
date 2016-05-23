package gameboard;

/* Joe Dobriko & Warren Squires
 * The purpose of this class is to test if the board object is created as well as to test if 
 * all the coordinates are accurate and that the edges are correct.
 */
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import java.util.*;

public class BoardTest{

	private Board b = new Board();
	private int x, y;
	private boolean test;

	@Before
	public void setUp(){
	//Board b = new Board();
	    int x = 0, y = 0;
	    boolean test = false;
	}

	@Test
	public void testPlayers(){
	    Map<Integer, Coordinate> map = b.getPawnPositions();
	    b.placePawn(1, new Coordinate(6, 0));
	    b.placePawn(2, new Coordinate(4, 0));
	    b.placePawn(3, new Coordinate(4, 8));
	    b.placePawn(4, new Coordinate(8, 4));
            b.setWalls(5);
	    assertEquals(false, test);
	    }

	@Test
	public void testEdge(){ //tests a random node edge
	    x = 4;
	    y = 4;
	    test = b.getDownEdge(4, 4);
	    assertEquals(true, test);
	    }

	@Test
	public void testGoodWall(){ //ensures a wall was placed
	    
	    x = 2;
	    y = 4;
	    b.placePawn(1, new Coordinate(0, 0));
            b.setWalls(10);
	    b.placeWall(1, new Wall(new Coordinate(4, 2), Wall.Direction.VERTICAL));
            
	    y++;
	    test = b.getRightEdge(x, y);
	    assertEquals(false, test);
	    }

	@Test
	public void testBadWall(){ //tries placing an invalid wall
            b.setWalls(10);
	    test = b.placeWall(1, new Wall(new Coordinate(0, 15), Wall.Direction.HORIZONTAL));
	    assertEquals(false, test);
	    }
	    
	@Test
	public void testWallNotCross(){
	    b.placePawn(1, new Coordinate(0, 4));
	    b.setWalls(10);
	    b.placeWall(1, new Wall(new Coordinate(0, 1), Wall.Direction.VERTICAL));
	    b.placeWall(1, new Wall(new Coordinate(2, 1), Wall.Direction.VERTICAL));
	    assertEquals(true, b.placeWall(1, new Wall(new Coordinate(1, 1), Wall.Direction.HORIZONTAL)));
	}
	
	@Test
	public void testWallCross(){
	    b.placePawn(1, new Coordinate(0, 4));
	    b.setWalls(10);
	    b.placeWall(1, new Wall(new Coordinate(0, 1), Wall.Direction.VERTICAL));
	    assertEquals(false, b.placeWall(1, new Wall(new Coordinate(0, 1), Wall.Direction.HORIZONTAL)));
	}
    
	@Test
	public void testValidMove(){
	    b.placePawn(1, new Coordinate(0, 6));
	    x = 5;
	    y = 0;
	    test = b.movePawn(1, new Coordinate(y, x));
	    assertEquals(true, test);
	    }
	    
	@Test
	public void testJump(){
	    b.placePawn(2, new Coordinate(0, 5));
	    b.placePawn(1, new Coordinate(0, 6));
	    int col = 4;
	    int row = 0;	    
	    assertEquals(true, b.movePawn(1, new Coordinate(row, col)));
	}
	    
	@Test
	public void testPawnBlock(){
	    b.placePawn(2, new Coordinate(0, 5));
	    b.placePawn(1, new Coordinate(0, 6));
	    x = 5;
	    y = 0;
	    test = b.movePawn(1, new Coordinate(y, x));
	    assertEquals(false, test);
	    }
	    
	@Test
	public void testWallBlock(){
	    b.placePawn(1, new Coordinate(0, 6));
            b.setWalls(10);
	    x = 5;
	    y = 0;
	    b.placeWall(1, new Wall(new Coordinate(0, 5), Wall.Direction.VERTICAL));
	    test = b.movePawn(1, new Coordinate(y, x));
	    assertEquals(false, test);
	}
	
	@Test
	public void testInvalidJump(){
	    b.placePawn(2, new Coordinate(0, 5));
	    b.placePawn(1, new Coordinate(0, 6));
            b.setWalls(10);
	    x = 4;
	    y = 0;
	    b.placeWall(1, new Wall(new Coordinate(0, 4), Wall.Direction.VERTICAL));
	    test = b.movePawn(1, new Coordinate(y, x));
	    assertEquals(false, test);
	    }

    @Test
    public void testJumpInfiniteRecursion() {
        b.placePawn(1, new Coordinate(0, 0));
        b.placePawn(2, new Coordinate(0, 1));
        b.placePawn(3, new Coordinate(1, 1));
        b.placePawn(4, new Coordinate(1, 0));
        b.setWalls(5);
        /*
         * The top-left corner of the board looks like this:
         *     0   1
         *   +-------
         * 0 | 1   2
         *   |
         * 1 | 4   3
         */
        assertTrue(b.movePawn(1, new Coordinate(2, 0)));
    }
	
	@Test
	public void testInvalidMove(){
	    x = 100;
	    y = 0;
	    test = b.movePawn(1, new Coordinate(x, y));
	    assertEquals(false, test);
	    }
	    
	@Test
	public void testRemovePawn(){
	    b.placePawn(1, new Coordinate(0, 6));
	    b.removePawn(1);
	    assertEquals(false, b.getBoard()[6][0].occupied);
	}
	
	@Test
	public void testSimpleDistance(){
	    int distance = 0;
	    Coordinate source = new Coordinate(4, 4);
	    int[][] distances = b.fillDistances(source);
	    assertEquals(true, distances[4][5] == 1);
	}
	
	@Test
	public void testFarDistance(){
	    Coordinate source = new Coordinate(4, 4);
	    int[][] distances = b.fillDistances(source);
	    assertEquals(true, distances[6][8] == 6);
	}
	
	@Test
	public void testDistanceWithWall(){
	    Coordinate source = new Coordinate(4, 4);
	    b.placePawn(1, new Coordinate(0, 0));
            b.setWalls(10);
	    b.placeWall(1, new Wall(new Coordinate(4, 4), Wall.Direction.VERTICAL));
	    int[][] distances = b.fillDistances(source);
	    assertEquals(true, distances[5][4] == 3);
	}
	
	@Test
	public void testDistanceWithManyWalls(){
	    Coordinate source = new Coordinate(4, 4);
	    b.placePawn(1, new Coordinate(0, 0));
            b.setWalls(10);
	    b.placeWall(1, new Wall(new Coordinate(4, 4), Wall.Direction.VERTICAL));
	    b.placeWall(1, new Wall(new Coordinate(3, 4), Wall.Direction.HORIZONTAL));
	    b.placeWall(1, new Wall(new Coordinate(4, 2), Wall.Direction.VERTICAL));
	    int[][] distances = b.fillDistances(source);
	    assertEquals(5, distances[5][4]);
	}
	
	@Test
	public void testBlockedGoal(){
	    b.placePawn(1, new Coordinate(0, 0));
            b.setWalls(10);
	    b.placeWall(1, new Wall(new Coordinate(0, 1), Wall.Direction.VERTICAL));
	    assertEquals(false, b.placeWall(1, new Wall(new Coordinate(1, 0), Wall.Direction.HORIZONTAL)));
	}
	
	@Test
	public void testShortestGoalDistance(){
            b = new Board();
	    b.placePawn(1, new Coordinate(0, 4));
            b.placePawn(2, new Coordinate(6, 4));
	    int closestPlayer = b.getClosestPlayer();            
	    assertEquals(2, closestPlayer);
	}
	
	@Test
	public void testBestMove(){
	    b.placePawn(1, new Coordinate(0, 4));
	    PawnMove best = b.bestMove(1);
	    assertEquals(1, best.destination.row);
            assertEquals(4, best.destination.column);
	}
	
}
