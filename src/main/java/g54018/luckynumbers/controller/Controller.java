/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.luckynumbers.controller;

import g54018.luckynumbers.model.Model;
import g54018.luckynumbers.model.Position;
import g54018.luckynumbers.view.View;

/**
 * Controller of the game, it iniliaze the all game.
 * Initiliaze all the state of the game and make a loop to continue everytime
 * 
 * @author basile <54018@etu.he2b.be>
 */
public class Controller {
    private View view;
    private Model model;

    /**
     * Create a controller that can initialize the all game for player
     * And make all the view for the home page and the board game.
     * 
     * @param view View: view for the display of the game
     * @param model Model: model for the all game that will be initialize
     */
    public Controller(Model model, View view) {
        this.view = view;
        this.model = model;
    }
    
    /**
     * Start the game by requesting
     * Number of player in the game and start the game for all the player
     */
    private void startGame(){
        view.displayWelcome();
        int playerCount = view.askPlayerCount();
        model.start(playerCount);
    }
    
    /**
     * Pick a Tile in the game
     * Display the current player board with the selected Tile
     */
    private void pickTileAndDisplay(){
        view.displayGame();
    }
    
    /**
     * ASk to the player if he want to put or drop the tile inside the deck
     * And go to next player if he choose the second option.
     */
    private void putOrDropTile(){
        view.putTileOrDrop();
    }
    
    /**
     * Ask the position to wich the selected Tile will be put
     * Put the Tile inside the board if it is valid
     */
    private void askPotisionAndPut(){
        Position pos = view.askPosition();
        model.putTile(pos);
    } 
    
    /**
     * Change the current turn to the next player
     * If the game is finish it will go to the GAME_OVER state
     */
    private void nextPlayerTurn(){
        model.nextPlayer();
    }
    
    /**
     * Display the winner of the game
     * Create a new Game by starting a new one
     * And ask the player count number
     */
    private void gameOverFinish(){
        view.displayWinner();
    }
    
    /**
     * Create all the current view and detail of the game
     * Display a welcome message with all stuff
     * For each State it will make a different action the action of the game is
     * Start - pickTile - place_or_drop_tile - dropTile or askPosition - nextPlayer and gameOver 
     * It will loop all the switch such as the user force the closure of the programm
     */
    public void play(){
        boolean isFinish=false;
        while(!isFinish){
            switch(model.getState()){
                case NOT_STARTED:
                    startGame();
                    break;
                case PICK_TILE:
                    pickTileAndDisplay();
                    break;
                case PLACE_OR_DROP_TILE:
                    putOrDropTile();
                    break;
                case PLACE_TILE:
                    askPotisionAndPut();
                    break;
                case TURN_END:
                    nextPlayerTurn();
                    break;
                case GAME_OVER:
                    isFinish = true;
                    gameOverFinish();
                    break;
                default:
                    throw new AssertionError(model.getState().name());
            }
        }
    }
}
