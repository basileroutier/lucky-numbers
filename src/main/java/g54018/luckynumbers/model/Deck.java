/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.luckynumbers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the Tiles who are visible in the center of the table and 
 * The none visible who are not yeat used from all the player
 * 
 * @author basile <54018@etu.he2b.be>
 */
public class Deck {
    private List<Tile> faceUpTiles; 
    private List<Tile> faceDownTiles;
    
    /**
     * Initiliaze with the visible List to no used yeat Tile and
     * The none visible List of tiles with all the tiles of all player which are not visible
     * 
     * @param playerCount int: number of player in the game to know how many Tiles did we need
     */
    public Deck(int playerCount) {
        initializeFaceTiles(playerCount);
        initializeFaceDownTiles(playerCount);
    }
    
    /**
     * Initialize the none visible and visible list of Tiles
     * @param playerCount int: number of player in the game to know how many Tiles did we need
     */
    private void initializeFaceTiles(int playerCount){
        faceDownTiles = new ArrayList<>();
        faceUpTiles = new ArrayList<>();
    }
    
    /**
     * Initialize the none visible list of Tiles
     * With a value 1 to 20 in as many copies as the number of players
     * @param playerCount int: number of player in the game to know how many Tiles did we need
     */
    private void initializeFaceDownTiles(int playerCount){
        int maxNumber=20;
        int currentIndex=0;
        for(var i=0;i<playerCount;i++){
            for(var j=0;j<maxNumber;j++, currentIndex++){
                int number= (j+1);
                faceDownTiles.add(new Tile(number));
            }
        }
    }
    
    /**
     * Give the number of face down Tile who are not null and not face up in the face down tiles
     * @return the number of value in a Array of Tiles face down
     */
    private int tileFaceNumberDown(){
        int nbTileFaceDown = 0;
        for (var tileFace : faceDownTiles) {
            if (tileFace != null && !tileFace.isFaceUp()) {
                nbTileFaceDown++;
            }
        }
        return nbTileFaceDown;
    }
    
    /**
     * Give the number of face up Tile who are not null and face up in the face up tiles
     * @return the number of value in a Array of Tiles face up
     */
    private int tileFaceNumberUp(){
        int nbTileFaceDown = 0;
        for (var tileFace : faceUpTiles) {
            if (tileFace != null && tileFace.isFaceUp()) {
                nbTileFaceDown++;
            }
        }
        return nbTileFaceDown;
    }
    
    /**
     * Return the number of none visible value of the face down tiles list
     * @return number of none visible value in the game
     */
    public int faceDownCount(){
        return tileFaceNumberDown();
    }
    
    /**
     * Return the number of visible Tiles of the face up tiles list
     * @return number of visible Tiles in the game
     */
    public int faceUpCount(){
        return tileFaceNumberUp();
    }
    
    /**
     * Generate a random number between 0 to the length of List of face down Tiles
     * It will return the random number
     * 
     * @return the random number valid
     */
    private int indexFaceDownRandom(){
        int max = (faceDownTiles.size())-1;
        int randomNumber = 0 + (int)(Math.random() * ((max - 0) + 1));
        return randomNumber;
    }
     
    /**
     * Change the attribut of the select Tiles to visible,
     * Get the selected Tile to return It
     * And remove from the list the selected Tiles
     * @param indexTileFaceDown int: random number of a selected Tile
     * @return the Tile of the random number value
     */
    private Tile changeFaceDownTile(int indexTileFaceDown){
        faceDownTiles.get(indexTileFaceDown).flipFaceUp();
        Tile pickTileFaceDown = faceDownTiles.get(indexTileFaceDown);
        faceDownTiles.remove(indexTileFaceDown);
        return pickTileFaceDown;
    }
    
    /**
     * A tile is randomly selected
     * And the tile is turned face down to remove it
     * Return the Tile which have been selected with the randomly number
     * 
     * @return a valid Tile from the none visible list
     */
    public Tile pickFaceDown(){
        int indexTileFaceDown = indexFaceDownRandom();
        return changeFaceDownTile(indexTileFaceDown);
    }
    
    /**
     * Return the list of face down Tiles
     * 
     * @return a list of none visible Tiles
     */
    protected List<Tile> getAllFaceDown(){
        return faceDownTiles;
    }
    
    /**
     * Return the list of face up Tiles
     * 
     * @return a list of visible Tiles
     */
    public List<Tile> getAllFaceUp(){
        return faceUpTiles;
    }
    
    /**
     * Check if the Tile in paramter exist in the list of visible Tiles and check if the Tile is visible
     * It return true if the condition is valid
     * Return false if we did not finded any Tile
     * 
     * @param tile Tile: a selected tile to be check
     * @return true if the Tile exist and is visible, else return false
     */
    public boolean hasFaceUp(Tile tile){
        for(var pickFaceUp : faceUpTiles){
            if(pickFaceUp !=null && pickFaceUp.isFaceUp() && tile.getValue() == pickFaceUp.getValue()){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get the index of the Tile in paramter and
     * Remove it from the visible deck
     * 
     * @param tile Tile: a selected tile to be remove
     */
    public void pickFaceUp(Tile tile){
        int index = faceUpTiles.indexOf(tile);
        faceUpTiles.remove(index);
    }
    
    /**
     * Change the tile to visible even if it is already true
     * Replace the selected Tile to the visible deck
     * @param tile Tile: tile which be placed into the array
     */
    public void putBack(Tile tile){
        tile.flipFaceUp();
        faceUpTiles.add(tile);
    }
    
    /**
     * Change the tile to none visible even it is already false
     * Replace the selected Tile into the non visible deck
     * @param tile Tile : tile which be place into the none visible deck
     */
    public void putBackTileInFaceDown(Tile tile){
        tile.flipFaceDown();
        faceDownTiles.add(tile);
    }
}
