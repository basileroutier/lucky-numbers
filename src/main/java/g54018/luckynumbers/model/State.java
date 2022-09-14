/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.luckynumbers.model;

/**
 * State of the game State can change to know where are we in the game
 * Their is different state to know where we are in current game.
 * Their is 
 * <ul>
 * <li>NOT_STARTED when game is not initialize</li>
 * <li>PICK_TILE when the player need to pick a Tile</li>
 * <li>PLACE_OR_DROP_TILE when the player choose a Tile which are not visible and place or drop the Tile</li>
 * <li>PLACE_TILE when the player gonna put the Tile into the game</li>
 * <li>TURN_END when the turn is end</li>
 * <li>GAME_OVER when the game is finish</li>
 * </ul>
 * @author basile <54018@etu.he2b.be>
 */
public enum State {
    NOT_STARTED,
    PICK_TILE,
    PLACE_OR_DROP_TILE,
    PLACE_TILE,
    TURN_END,
    GAME_OVER;
}
