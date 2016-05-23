package gameboard;

public class Wall {

    public static enum Direction {
        HORIZONTAL, VERTICAL;
    };

    public final Coordinate position;
    public final Direction direction;

    public Wall(Coordinate position, Direction direction) {
        this.position = position;
        this.direction = direction;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Wall) {
            Wall otherW = (Wall)other;
            return this.position.equals(otherW.position) && this.direction == otherW.direction;
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "[(" + position.column + ", " + position.row + "), " + direction + "]";
    }
}
