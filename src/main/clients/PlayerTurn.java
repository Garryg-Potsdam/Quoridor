package clients;

public class PlayerTurn {

    private static final int TWOPLAYERS = 2;
    private static final int FOURPLAYERS = 4;

    private Integer[] players;

    // this is an index into the players array
    private int turn = -1;

    // the number of players left
    private int playerCount;
    
    /**
     * @param players - player numbers in order; must not contain duplicates
     */
    public PlayerTurn(Integer... players) {
        this.players = players;
        playerCount = players.length;
    }

    /**
     * @return - next player to move in the list
     */
    public int next() {
        if (playerCount() == 0) {
            throw new IllegalStateException("No players left");
        }

        do {
            turn = (turn + 1) % players.length;
        } while (players[turn] == null);

        return players[turn];
    }

    /**
     * @param pNumber - the player that got booted 
     * Boots the passed in player from the turn list
     */
    public void bootPlayer(Integer pNumber) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == pNumber) {
                players[i] = null;
                playerCount--;
                break;
            }
        }
    }

    /**
     * Returns the number of players left.
     */
    public int playerCount() {
        return playerCount;
    }
}
