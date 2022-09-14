/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.luckynumbers.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author basile
 */
public class TileTest {
    
    private Tile tile;
    
    public TileTest() {
    }
    @BeforeEach     // Exécutée avant chaque test
    public void setUp() {
        tile = new Tile(2);
    }
    
    @Test
    public void tile_not_face_up(){
        assertFalse(tile.isFaceUp());
    }
    
    @Test
    public void change_Face_Up_Tile() {
        tile.flipFaceUp();
        assertTrue(tile.isFaceUp());
    }
    
    @Test
    public void change_Face_Up_Tile_when_already_face() {
        tile.flipFaceUp();
        tile.flipFaceUp();
        assertTrue(tile.isFaceUp());
    }
    
    @Test
    public void tile_not_equal_between_us(){
        Tile tile2 = new Tile(0);
        assertFalse(tile.equals(tile2));
    }
    
    @Test
    public void tile_equal_between_us(){
        Tile tile2 = new Tile(2);
        assertTrue(tile.equals(tile2));
    }
    
    @Test
    public void tile_both_same_flip_face_up(){
        Tile tile1 = new Tile(2);
        tile.flipFaceUp();
        assertFalse(tile1.isFaceUp());
        assertTrue(tile.isFaceUp());
    }
    
}
