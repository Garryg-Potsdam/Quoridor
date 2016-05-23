package gameboard;

public class PawnMove extends Move {

    public final Coordinate destination;

    public PawnMove(Coordinate dest) {
        this.destination = dest;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof PawnMove) {
            PawnMove otherP = (PawnMove)other;
            return this.destination.equals(otherP.destination);
        } else {
            return false;
        }
    }
}
