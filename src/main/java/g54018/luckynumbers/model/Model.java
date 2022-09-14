/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.luckynumbers.model;

import java.util.List;

/**
 * Interface for the Game model.
 *
 * @author MCD {@literal <mcodutti@he2b.be>}
 */
public interface Model {

    /**
     * Initialize a game.
     * <ul>
     * <li>An empty board is created for each player</li>
     * <li>Player number 0 starts the game</li>
     * <li> Initialize a random Tile for the diagonal of each player </li>
     * <li>State becomes PICK_TILE</li>
     * </ul>
     *
     * @param playerCount the number of players
     * @throws IllegalArgumentException if the number of players is not between 2 and 4 (both included).
     * @throws IllegalStateException if called when state is not NOT_STARTED or GAME_OVER.
     */
    void start(int playerCount);

    /**
     * Give the size of the boards. We suppose that all boards are squares and of the same size. So this is both number of lines and number of columns. With the official rules, this should be 4 but this must not be assumed and this methode must be used instead of hardcoding that value elsewhere in the code.
     *
     * @return the size of the board.
     */
    int getBoardSize();

    /**
     * Choose a random available Tile in the down deck 
     * And change the face up of the tile to true
     * Return the selected Tile
     * State become to PLACE_OR_DROP_TILE
     * 
     * @throws IllegalStateException if called when state is not PICK_TILE
     * @return the selected random picked tile in the down deck
     */
    Tile pickFaceDownTile();
    
    /**
     * Check if the state is PICK_TILE if not it will throw a IllegalStateException
     * And if the Tile in paramater is face up and is correctly in the deck
     * It will return the selected Tile
     * Else it will throw an illegal argument excpetion
     * 
     * @param tile
     * @throws IllegalStateException if called when state is not PICK_TILE
     * @throws IllegalArgumentException if the Tile in paramater is not face up and the value is not in the deck
     * @throws IllegalArgumentException if the tile is not inside the visible deck
     * @return the selected tile in the deck
     */
    Tile pickFaceUpTile(Tile tile);
    
    /**
     * Replace the pickedTile in the deck and 
     * Set the state to TURN_END
     * @throws IllegalStateException if called when state is not PLACE_OR_DROP_TILE
     */
    void dropTile();
    
    /**
     * Return the number of down Tile in the deck
     * @return number of hidden Tile in the deck down
     */
    int faceDownTileCount();
    
    /**
     * Return the number of none hide Tile in the deck
     * @return number of none hidden Tile in the deck
     */
    int faceUpTileCount();
    
    /**
     * Return an unmodifiable List of face up Tile which are visible in the deck
     * @return a list of visible Tile
     */
    List<Tile> getAllfaceUpTiles();
    
    /**
     * Put a tile at the given position. Put the previously picked tile of the current player at the given position on its board. 
     * If the case is not empty it will replace the Tile in the game into the deck
     * State becomes TURN_END OR GAME_OVER.
     *
     * @param pos where to put the tile.
     * @throws IllegalArgumentException if the tile can't be put on that position (position outside of the board or position not allowed by the rules)
     * @throws IllegalStateException if called when state is not PLACE_TILE OR PLACE_OR_DROP_TILE
     */
    void putTile(Position pos);

    /**
     * Change current player. The next player becomes the current one. The order is : 0, 1, 2, 3 and again 0, 1, ... 
     * State becomes PICK_TILE
     *
     * @throws IllegalStateException if called when state is not TURN_END
     */
    void nextPlayer();

    /**
     * Give the total number of players in this game.
     *
     * @return the total number of players in this game.
     * @throws IllegalStateException if state is NOT_STARTED
     */
    int getPlayerCount();

    /**
     * Get the current state of the game.
     *
     * @return the current state of the game.
     */
    State getState();

    /**
     * Give the number of the current player. Players are numeroted from 0 to (count-1).
     *
     * @return the number of the current player.
     * @throws IllegalStateException if state is NOT_STARTED or GAME_OVER
     */
    int getCurrentPlayerNumber();

    /**
     * Get the current picked tile. Get the tile picked by the current player.
     *
     * @return the current picked tile.
     * @throws IllegalStateException if state is not PLACE_TILE
     */
    Tile getPickedTile();

    /**
     * Check if a position is inside the board of the current player or not.
     *
     * @param pos a position
     * @return true if the given position is inside the board.
     */
    boolean isInside(Position pos);

    /**
     * Check if a tile can be put at the given position. Check if the current player is allowed to put its previously picked tile at the given position on the board of the current player.
     *
     * @param pos the position to check
     * @return true if the picked tile can be put at that position.
     * @throws IllegalArgumentException if the position is outside the board.
     * @throws IllegalStateException if state is not PLACE_TILE.
     */
    boolean canTileBePut(Position pos);

    /**
     * Give a tile at a given position of the board of a given player.
     *
     * @param playerNumber the player number
     * @param pos a position on the board
     * @return the tile at that position for that player or <code>null</code> if there is no tile there.
     * @throws IllegalArgumentException if the position is outside the board or if the playerNUmber is ouside of range.
     * @throws IllegalStateException if game state is NOT_STARTED
     */
    Tile getTile(int playerNumber, Position pos);

    /**
     * Give the only player if the boards of the current player is full
     * Else it return a list of all player which have a minimum of null Tile in their boards
     *
     * @return a list of the winner for the game
     * @throws IllegalStateException if game state is not GAME_OVER
     */
    List<Integer> getWinners();

}
