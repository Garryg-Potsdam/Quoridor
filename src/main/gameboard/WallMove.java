package gameboard;

public class WallMove extends Move {

    public final Wall wall;

    public WallMove(Wall wall) {
        this.wall = wall;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof WallMove) {
            WallMove otherW = (WallMove)other;
            return this.wall.equals(otherW.wall);
        } else {
            return false;
        }
    }
}
