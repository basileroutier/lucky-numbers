/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.luckynumbers.view;

import g54018.luckynumbers.model.Position;

/**
 *
 * @author basile <54018@etu.he2b.be>
 */
public interface View {
    
    /**
     * Display information for the user like :
     * game name, author, version, a small summary, ...
     * Welcome message with dynamic sentence and a small summary who can be change
     */
    void displayWelcome();
    
    /**
     * Display all the interface of the game it design the
     * <ul>
     * <li>The current player who play</li>
     * <li>His own Board with all the tiles that put inside it</li>
     * <li>His board with a simple view, dynamic and reusable of the number of the size of the board</li>
     * <li>The select tile</li>
     * </ul>
     */
    void displayGame();
    
    /**
     * Display the current player number who finish in first the game
     */
    void displayWinner();
    
     /**
     * If the choice is 0 it will put the Tile into a given position selected by the user
     * Else it will drop the Tile into the available deck
     * 
     */
    void putTileOrDrop();
    
    /**
     * Ask the number of all the player who can play in the game, this number is including between 2 and 4
     * Verify if the number of player count is valid so different of null, chain or decimal.
     * And check if the player enter the good number of player. If this number is not between 2 and 4. It will reask the number
     * Such as the number of player is valid
     * @return the number of player in the game who will play
     */
    int askPlayerCount();
    
    /**
     * Ask the Position for a select Tile
     * Will choose the number of the row and the column 
     * to put it's select tile inside his board
     * If the position is not inside the board it will display an error and re ask to the player a position
     * It will check if the selected Tile can be put inside the board with the player position.
     * Robust Reading method
     * Such as the position is good
     * @return a valid position
     */
    Position askPosition();
    
    /**
     * Display the error with a specific format
     * The format is underline the error message
     * @param message String: message error that give in parameter to be display
     */
    void displayError(String message);
}
