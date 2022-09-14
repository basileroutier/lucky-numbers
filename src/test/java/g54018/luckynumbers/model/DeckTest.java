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
public class DeckTest {
    
    private Deck deck;
    
    public DeckTest() {
    }

    @BeforeEach     // Exécutée avant chaque test
    public void setUp() {
        deck = new Deck(2);
    }
    
    @Test
    public void number_tile_list_in_deck(){
        assertEquals(40, deck.getAllFaceDown().size());
    }
    
    @Test
    public void number_tile_count_in_deck(){
        assertEquals(40, deck.faceDownCount());
    }
    
    @Test
    public void number_tile_list_in_deck_face_up(){
        assertEquals(0, deck.getAllFaceUp().size());
    }
    
    @Test
    public void number_tile_count_in_deck_face_up(){
        assertEquals(0, deck.faceUpCount());
    }
    
    @Test
    public void number_tile_count_when_pick_tile(){
        deck.pickFaceDown();
        assertEquals(39, deck.faceDownCount());
    }
    
    @Test
    public void number_tile_count_when_pick_tile_and_back_tile(){
        Tile tile = deck.pickFaceDown();
        deck.putBack(tile);
        assertEquals(39, deck.faceDownCount());
        assertEquals(1, deck.faceUpCount());
    }
    
    @Test
    public void number_tile_list_when_pick_tile_and_back_tile(){
        Tile tile = deck.pickFaceDown();
        deck.putBack(tile);
        assertEquals(39, deck.getAllFaceDown().size());
        assertEquals(1, deck.getAllFaceUp().size());
    }
    
    @Test
    public void pick_tile_value(){
        Tile pickTile = deck.pickFaceDown();
        deck.putBack(pickTile);
        assertEquals(pickTile, deck.getAllFaceUp().get(0));
    }
    
    @Test
    public void pick_tile_value_when_tile_is_face_up(){
        Tile pickTile = deck.pickFaceDown();
        assertTrue(pickTile.isFaceUp());
    }
    
    @Test
    public void get_tile_face_down(){
        assertEquals(1, deck.getAllFaceDown().get(0).getValue());
    }
    
    @Test
    public void pick_tile_when_is_face_up_and_tile_is_equals(){
        Tile pickTile = deck.pickFaceDown();
        deck.putBack(pickTile);
        assertTrue(deck.hasFaceUp(pickTile)); 
    }
    
    @Test
    public void pick_tile_when_is_face_up_and_tile_is_not_equals(){
        Tile pickTile = deck.pickFaceDown();
        deck.putBack(pickTile);
        assertFalse(deck.hasFaceUp(new Tile(30))); 
    }
    
    @Test
    public void pick_tile_when_is_not_face_up_and_tile_is_not_equals(){
        assertFalse(deck.hasFaceUp(new Tile(20))); 
    }
    
    @Test
    public void putBack_value_when_tile_is_face_up_(){
        Tile pickTile = deck.pickFaceDown();
        deck.putBack(pickTile);
        assertTrue(pickTile.isFaceUp());
    }
    
    @Test
    public void pick_tile_value_when_tile_is_not_face_up(){
        assertFalse(deck.getAllFaceDown().get(0).isFaceUp());
    }
    
    @Test
    public void put_back_all_tile_both_equals(){
        int tot = deck.faceDownCount();
        for(var i=0;i<tot;i++){
            Tile tile = deck.pickFaceDown();
            deck.putBack(tile);
        }
        assertEquals(0, deck.faceDownCount());
        assertEquals(40, deck.faceUpCount());
    }
    
    @Test
    public void pick_face_up_count_when_not_equals(){
        Tile pickTile = deck.pickFaceDown();
        deck.putBack(pickTile);
        assertEquals(1, deck.faceUpCount());
    }
    
    @Test
    public void pick_face_up_count_when_is_ok(){
        Tile pickTile = deck.pickFaceDown();
        deck.putBack(pickTile);
        deck.pickFaceUp(pickTile);
        assertEquals(0, deck.faceUpCount());
    }
    
    @Test
    public void double_pick_face_up_count_when_is_ok(){
        Tile pickTile = deck.pickFaceDown();
        deck.putBack(pickTile);
        Tile pickTile2 = deck.pickFaceDown();
        deck.putBack(pickTile2);
        deck.pickFaceUp(pickTile);
        assertEquals(1, deck.faceUpCount());
    }
    
    @Test
    public void double_pick_face_up_list_when_is_ok(){
        Tile pickTile = deck.pickFaceDown();
        deck.putBack(pickTile);
        Tile pickTile2 = deck.pickFaceDown();
        deck.putBack(pickTile2);
        deck.pickFaceUp(pickTile);
        assertEquals(pickTile2, deck.getAllFaceUp().get(0));
    }
    
    @Test
    public void double_pick_face_up_list_when_is_not_ok(){
        Tile pickTile = deck.pickFaceDown();
        deck.putBack(pickTile);
        Tile pickTile2 = deck.pickFaceDown();
        deck.putBack(pickTile2);
        deck.pickFaceUp(pickTile);
        deck.pickFaceUp(pickTile2);
        assertEquals(0, deck.faceUpCount());
        assertEquals(38, deck.faceDownCount());
    }
}

