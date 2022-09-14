/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.luckynumbers.model;

/**
 * Create a tile of number between 1 and 20 and 
 * The visibility of the Tile to false
 * 
 * @author basile <54018@etu.he2b.be>
 */
public class Tile {

    private int value;
    private boolean faceUp;
    
    /**
     * Initiliaze a Tile value and a visibility to false that can be put in a Game. 
     * Their is two type of value
     * An integer and a null
     *
     * @param value int : value of the tile
     */
    public Tile(int value) {
        this.value = value;
        this.faceUp = false;
    }

    /**
     * Return the value of a tile in the game
     *
     * @return the value of a tile
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Return if a Tile is already face up in the game or if is not reveal
     * @return true if the Tile is face Up and false if not yeat
     */
    public boolean isFaceUp(){
        return faceUp;
    }

    /**
     * Change the faceUp of the tile to true
     * It's not possible to change the face up to false when it's true except if we use flipFaceDown
     */
    protected void flipFaceUp() {
        this.faceUp = true;
    }
    
    /**
     * Change the faceUp of the tile to false
     * It is not possible to change the face up to true except if we use flipFaceUp
     */
    protected void flipFaceDown(){
        this.faceUp = false;
    }

    /**
     * Cast an Object of Tile
     * Compare the hash code, it they are different, it will return false
     * Else true
     * @param obj Object Tile: Object
     * @return true if the condition of hash is valid else false
     */
    @Override
    public boolean equals(Object obj) {
        final Tile other =  (Tile) obj;
        if (this.value != other.value) {
            return false;
        }
        return true;
    }
    
    
}
