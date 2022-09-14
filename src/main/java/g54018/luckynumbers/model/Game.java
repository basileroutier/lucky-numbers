/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.luckynumbers.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Start the game with different player minmum 2 to 4 maximum. 
 * Initialize a diagonal random Tile for each player
 * Initilize different deck visible and none visible
 * Make different action to advance in a board of a player
 *
 * @author basile <54018@etu.he2b.be>
 */
public class Game implements Model {

    private int playerCount;
    private int currentPlayerNumber;
    private State state;
    private Board[] boards;
    private Tile pickedTile;
    private Deck deck;
    
    
    /**
     * Initialize beginning state of the game
     */
    public Game() {
        state = State.NOT_STARTED;
    }

    /**
     * Initialize all the board of player in the game
     */
    private void initialElementBoards() {
        boards = new Board[playerCount];
        for (var i = 0; i < playerCount; i++) {
            boards[i] = new Board();
        }
    }
    
    /**
     * Put into the board of each player
     * A Tile in diagonal which are sorted
     */
    private void initialDiagonalElementBoards(){
        for(var i=0;i<playerCount;i++){
            List<Tile> currentTile = initialTileArraySortedDiagElement();
            for(var j=0;j<getBoardSize();j++){
                boards[i].put(currentTile.get(j), new Position(j,j));
            }
        }
    }
    
    /**
     * Initialize a array of Tile
     * And pick a Tile which are face down and put it into the array for the boards of the player
     * @return the none sorted array of Tile
     */
    private List initialTileArrayNotSortedDiagElement(){
        int sizeBoard = getBoardSize();
        List<Tile> tileDiag = new ArrayList<>();
            for(var j=0;j<sizeBoard;j++){
                Tile currentTile = deck.pickFaceDown();
                currentTile.flipFaceUp();
                tileDiag.add(currentTile);
            }
        return tileDiag;
    }
    
    /**
     * Sorted all the Tile in the array
     * And return the sorted array
     * @return the sorted Tile array
     */
    private List initialTileArraySortedDiagElement(){
        List tileDiag = initialTileArrayNotSortedDiagElement();
        Collections.sort(tileDiag, new Comparator<Tile>() {
            @Override
            public int compare(Tile o1, Tile o2) {
                return o1.getValue() - o2.getValue();
            }
        }
        );
        return tileDiag;
    }
    
    /**
     * Initialize all the needed attribute for the start of the game
     * Like the state of the current State to PICK TILE
     */
    private void initialStartGame(int playerCount){
        currentPlayerNumber = 0;
        this.playerCount = playerCount;
        deck = new Deck(playerCount);
        initialElementBoards();
        initialDiagonalElementBoards();
        state = State.PICK_TILE;
    }

    /**
     * Start the game by changing the state of Game_Over or Not_Started to pick_line 
     * And Initialize a random Tile for the diagonal of each player
     * Set the player count in the game and current player who play the turn Initliaze the Board of all player 
     * In other state, it will IllegalStateException
     *
     * @param playerCount int: number of player in the game
     * @throws IllegalArgumentException if the number of players is not between 2 and 4 (both included).
     * @throws IllegalStateException if called when state is not NOT_STARTED nor
     */
    @Override
    public void start(int playerCount) {
        switch (state) {
            case NOT_STARTED:
            case GAME_OVER:
                initialStartGame(playerCount);
                break;
            case PICK_TILE:
            case PLACE_OR_DROP_TILE:
            case PLACE_TILE:
            case TURN_END:
                throw new IllegalStateException("The game couldn't be started at this state of the game");
            default:
                throw new AssertionError(state.name());
        }
        if (playerCount < 2 || playerCount > 4) {
            throw new IllegalArgumentException("The number of player is not valid");
        }
    }

    /**
     * Check if the current state is PICK_TILE
     * Change the pickedTile to a random available picked Tile in deck Down
     * And change the attribut of the Tile to visible
     * Change the state to PLACE_OR_DROP_TILE
     * Return this selected Tile
     * 
     * @throws IllegalStateException if called when state is not PICK_TILE
     * @return 
     */
    private Tile pickTileInDeckDown(){
        if(state != State.PICK_TILE){
            throw new IllegalStateException("It is not possible to pick a face down tile for the moment");
        }
        state = State.PLACE_OR_DROP_TILE;
        pickedTile = deck.pickFaceDown();
        pickedTile.flipFaceUp();
        return pickedTile;
    }
    
    @Override
    public Tile pickFaceDownTile() {
        return pickTileInDeckDown();
    }

    /**
     * Check if the Tile in paramater is visible
     * And if we are in the deck
     * Change the pickedTile to the selected Tile
     * Change the State to PLACE_TILE
     * And return the parameterTile if all the condition is valid
     * If not it throw an Illegal Argument Exception
     *
     * @param tile Tile: selected Tile to be used in the game
     * @throws IllegalArgumentException if the Tile is not face up
     * @return the selected Tile if the Tile is face up else it will throw an Exception
     */
    private Tile pickTileInDeck(Tile tile){
        if(!deck.hasFaceUp(tile)){
            throw new IllegalArgumentException("The selected tile does not match if a finded tile in the visible deck");
        }
        pickedTile = tile;
        state = state.PLACE_TILE;
        return pickedTile;
    }
    
    @Override
    public Tile pickFaceUpTile(Tile tile) {
        if(state != State.PICK_TILE){
            throw new IllegalStateException("It is not possible to pick a face up tile for the moment");
        }
        return pickTileInDeck(tile);
    }
    
    /**
     * If a Tile is replace by another, it stock the Tile which are replace in the none visible deck to be reusable.
     * If utilisation of pick face up, it remove the picked tile from the deck visible
     * 
     * @param pos 
     */
    private void changeCurrentDeck(Position pos){
        if(state == State.PLACE_TILE){
            deck.pickFaceUp(pickedTile);
        }
        if(boards[currentPlayerNumber].getTile(pos)!=null){
            deck.putBackTileInFaceDown(boards[currentPlayerNumber].getTile(pos));
        }
    }
    
    /**
     * Verify if the tile can be put inside the current player board
     * If it is possible, it will put the Tile
     * Check if it continue or end the game
     * @throws IllegalArgumentException if the position is not inside the board or is already taken.
     * @param pos Position: position that be helpfull to find a tile in a game
     */
    private void putTilePosVerif(Position pos){
        if (canTileBePut(pos)) {
            changeCurrentDeck(pos);
            boards[currentPlayerNumber].put(pickedTile, pos);
            changeStateIfContinueOrEnd();
        } else {
            throw new IllegalArgumentException("The position give is not inside the board or the position in the board is already taken.");
        }
    }

    /**
     * Check if the board is full
     * If it is, il will change the state to GAME_OVER
     * Else it will pass to the TURN_END
     */
    private void changeStateIfContinueOrEnd(){
        boolean isEndFull = boards[currentPlayerNumber].isFull();
        boolean isEndTileFaceDown = faceDownTileCount()==0;
        if(isEndFull || isEndTileFaceDown){
            state = State.GAME_OVER;
        }else{
            state = State.TURN_END;
        }
    }
    
    /**
     * Check if the player board is full and modify the state to game over, 
     * if not it check if the Tile can be put into the board and modify, it will check after the input if the current have finish or not
     * if it is true 
     * If the case is not empty it will replace the Tile in the game into the deck
     * State change to TURN_END or GAME_OVER
     *
     * @throws IllegalArgumentException if the tile can't be put on that position (position outside of the board or position not allowed by the rules)
     * @throws IllegalStateException if called when state is not PLACE_TILE OR PLACE_OR_DROP_TILE
     * @param pos Position : position that be helpfull to find a tile in a game
     */
    @Override
    public void putTile(Position pos) {
        if(state != State.PLACE_TILE && state != State.PLACE_OR_DROP_TILE){
            throw new IllegalStateException("It is not your turn, please wait the end");
        }
        putTilePosVerif(pos);
    }
    
    @Override
    public void dropTile() {
        if(state!= State.PLACE_OR_DROP_TILE){
            throw new IllegalStateException("The Tile couldn't be put to the deck");
        }
        deck.putBack(pickedTile);
        state = State.TURN_END;
    }

    /**
     * Change the current player number of the game
     * Change the current state to PICK_TILE
     */
    private void changeCurrentPlayer(){
        currentPlayerNumber = (currentPlayerNumber + 1) % playerCount;
        state = State.PICK_TILE;
    }
    
    /**
     * Change the player, set the current player to 0 if the number is equal to number of player in the game like 0,1,2,3,... 0,1,... and again 
     * Modify the State to Pick Tile to play
     *
     * @throws IllegalStateException if called when state is not TURN_END
     */
    @Override
    public void nextPlayer() {
        if(state != State.TURN_END){
            throw new IllegalStateException("It is not possible to finish the current turn");
        }
        boolean isNoMoreFaceDownTile = deck.faceDownCount()==0;
        if(isNoMoreFaceDownTile){
            state = State.GAME_OVER;
        }else{
            changeCurrentPlayer();
        }
        
    }

    /**
     * Give the size of the boards. We suppose that all boards are squares and of the same size. 
     * So this is both number of lines and number of columns. 
     * With the official rules, this should be 4 but this must not be assumed and this methode must be used instead of hardcoding that value elsewhere in the code.
     *
     * @return the size of the board.
     */
    @Override
    public int getBoardSize() {
        return boards[currentPlayerNumber].getSize();
    }

    /**
     * Get the current state of the game.
     *
     * @return the current state of the game.
     */
    @Override
    public State getState() {
        return state;
    }

    /**
     * Give the total number of players in this game.
     *
     * @return the total number of players in this game.
     * @throws IllegalStateException if state is NOT_STARTED
     */
    @Override
    public int getPlayerCount() {
        if (state == State.NOT_STARTED) {
            throw new IllegalStateException("It is not possible to get the total of player in the game in the state of the game");
        }
        return playerCount;
    }

    /**
     * Give the number of the current player. Players are numeroted from 0 to (count).
     *
     * @return the number of the current player.
     * @throws IllegalStateException if state is NOT_STARTED or GAME_OVER
     */
    @Override
    public int getCurrentPlayerNumber() {
        switch (state) {
            case NOT_STARTED:
            case GAME_OVER:
                throw new IllegalStateException("It is not possible to get the current player at this state of the game");
            case PICK_TILE:
            case PLACE_OR_DROP_TILE:
            case PLACE_TILE:
            case TURN_END:
                return currentPlayerNumber;
            default:
                throw new AssertionError(state.name() + " is not valid");
        }
    }

    /**
     * Get the current picked tile. Get the tile picked by the current player.
     *
     * @return the current picked tile.
     * @throws IllegalStateException if state is not PLACE_TILE
     */
    @Override
    public Tile getPickedTile() {
        if (state != State.PLACE_TILE && state != State.PLACE_OR_DROP_TILE) {
            throw new IllegalStateException("It is not possible to get the Tile of the player at this moment");
        }
        return pickedTile;
    }

    /**
     * Check if a position is inside the board of the current player or not.
     *
     * @param pos a position
     * @return true if the given position is inside the board.
     */
    @Override
    public boolean isInside(Position pos) {
        return boards[currentPlayerNumber].isInside(pos);
    }

    /**
     * Check if a tile can be put at the given position. Check if the current player is allowed to put its previously picked tile at the given position on the board of the current player.
     *
     * @param pos the position to check
     * @return true if the picked tile can be put at that position.
     * @throws IllegalArgumentException if the position is outside the board.
     * @throws IllegalStateException if state is not PLACE_TILE.
     */
    @Override
    public boolean canTileBePut(Position pos) {
        if (state != State.PLACE_TILE && state != State.PLACE_OR_DROP_TILE) {
            throw new IllegalStateException("It is not possible to verify the position in the actual state");
        }
        if (!isInside(pos)) {
            throw new IllegalArgumentException("The given position is not inside the board");
        }
        return boards[currentPlayerNumber].canBePut(pickedTile, pos);
    }

    /**
     * Give a tile at a given position of the board of a given player.
     *
     * @param playerNumber the player number
     * @param pos a position on the board
     * @return the tile at that position for that player or <code>null</code> if there is no tile there.
     * @throws IllegalArgumentException if the position is outside the board or if the playerNUmber is ouside of range.
     * @throws IllegalStateException if game state is NOT_STARTED
     */
    @Override
    public Tile getTile(int playerNumber, Position pos) {
        if (state == State.NOT_STARTED) {
            throw new IllegalStateException("It is not possible to put a Tile when the game is not started");
        } else if (!isInside(pos) || (playerNumber < 0 || playerNumber > playerCount - 1)) {
            throw new IllegalArgumentException("Position is not inside the board or the player don't exist");
        }
        return boards[playerNumber].getTile(pos);
    }

    /**
     * Store all the null Tile into the board of each player into a Array of Integer
     * If the Tile is null for all the position in the board it will add 1 to the number of null
     * Return the array of null Tile in all board
     * 
     * @return an array of integer who contain the number of null in board player
     */
    private int[] getNumberNullTilePlayer(){
        int playerCount = this.playerCount;
        int[] nbNullPlayer = new int[playerCount];
        for(var i=0;i<playerCount;i++){
            int currentNbNull =0;
            for(var j=0;j<getBoardSize();j++){
                for(var k=0;k<getBoardSize();k++){
                    if(boards[i].getTile(new Position(j,k)) == null){
                        currentNbNull++;
                    }
                }
            }
            nbNullPlayer[i] = currentNbNull;
        }
        return nbNullPlayer;
    }
    
    /**
     * Check for each null number of player 
     * If the max is higher than null number of player
     * If it is, it set the max to the new number of null
     * Return the max of minimum null of player
     * 
     * @return the minimum of null in a board
     */
    private int getMaxFromNumberNullTilePlayer(){
        int[] nbNullPlayer = getNumberNullTilePlayer();
        int minOfNull =nbNullPlayer[0];
        
        for(var numberOfNull: nbNullPlayer){
            if(numberOfNull<minOfNull){
                minOfNull = numberOfNull;
            }
        }
        return minOfNull;
    }
    
    /**
     * Check for each player if the minimum of null finded is equal to the minimum of null
     * If it is, it add one to the number of player which have the number of minimum number of null
     * Return the number of player which have the minimum of null Tile
     * 
     * @return the number of player which have the minimum of null Tile
     */
    private int numberOfMaxTileNull(){
        int[] nbNullPlayer = getNumberNullTilePlayer();
        int minOfNull = getMaxFromNumberNullTilePlayer();
        int nbOfMax=0;
        for(var playerNumberNullTile: nbNullPlayer){
            if(playerNumberNullTile==minOfNull){
                nbOfMax++;
            }
        }
        return nbOfMax;
    } 
    
    /**
     * Check for each player if the minimum of null finded is equal to the minimum of null
     * If it is, it add the index of player who will win the game with the less of null Tile in their board
     * Return the list of winner
     * 
     * @return an Integer List which contains all winner of the game
     */
    private List<Integer> indexNumberTilePlayer(){
        int[] nbNullPlayer = getNumberNullTilePlayer();
        int nbOfMax = numberOfMaxTileNull();
        int max = getMaxFromNumberNullTilePlayer();
        List<Integer> playerWinner = new ArrayList<Integer>();
        for(var i=0;i<nbNullPlayer.length;i++){
            if(nbNullPlayer[i]==max){
                playerWinner.add(i);
            }
        }
        return playerWinner;
    }
    
    /**
     * Give the only player if the boards of the current player is full
     * Else it return a list of all player which have a minimum of null Tile in their boards
     *
     * @return a list of the winner for the game
     * @throws IllegalStateException if game state is not GAME_OVER
     */
    @Override
    public List<Integer> getWinners() {
        if (state != State.GAME_OVER){
            throw new IllegalStateException("It is not possible to get the Winner without the end of the game");
        }
        
        if(boards[currentPlayerNumber].isFull()){
            List<Integer> playerWinner = new ArrayList<Integer>();
            playerWinner.add(currentPlayerNumber);
            playerWinner.add(-1);
            return playerWinner;
        }else{
            return indexNumberTilePlayer();
        }
    }

    @Override
    public int faceDownTileCount() {
        if(state == State.NOT_STARTED && state == State.GAME_OVER){
            throw new IllegalStateException("It is not possible to get the number of face down Tile in the game if it is not started or game over");
        }
        return deck.faceDownCount();
    }

    @Override
    public int faceUpTileCount() {
        if(state == State.NOT_STARTED && state == State.GAME_OVER){
            throw new IllegalStateException("It is not possible to get the number of face up Tile in the game if it is not started or game over");
        }
        return deck.faceUpCount();
    }

    @Override
    public List<Tile> getAllfaceUpTiles() {
        if(state == State.NOT_STARTED && state == State.GAME_OVER){
            throw new IllegalStateException("It is not possible to get the list of face up Tile in the game if it is not started or game over");
        }
        List<Tile> allFaceUpTilesUnmodifiable = Collections.unmodifiableList(deck.getAllFaceUp());
        return allFaceUpTilesUnmodifiable;
    }
}
