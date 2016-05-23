package gameboard;

public class Coordinate {

    public int row;
    public int column;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Coordinate) {
            Coordinate otherC = (Coordinate)other;
            return this.row == otherC.row && this.column == otherC.column;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        // compute the z-value by interleaving the
        // binary representations of row and column
        int pow2 = 1;
        int shift = 0;
        int hash = 0;
        // only the first 16 bits of row and column will fit
        for (int i = 0; i < 16; i++) {
            hash += (row & pow2) << shift;
            shift += 1;
            hash += (column & pow2) << shift;
            pow2 *= 2;
        }
        return hash;
    }

    /**
     * Returns the coordinate immediately above this one.
     */
    public Coordinate up() {
        return new Coordinate(row - 1, column);
    }

    /**
     * Returns the coordinate immediately to the right of this one.
     */
    public Coordinate right() {
        return new Coordinate(row, column + 1);
    }

    /**
     * Returns the coordinate immediately below this one.
     */
    public Coordinate down() {
        return new Coordinate(row + 1, column);
    }

    /**
     * Returns the coordinate immediately to the left of this one.
     */
    public Coordinate left() {
        return new Coordinate(row, column - 1);
    }
}
