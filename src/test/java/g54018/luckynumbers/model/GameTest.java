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
 * @author MCD <mcodutti@he2b.be> x Basile <54018@etu.he2b.be>
 */
public class GameTest {

    private Game game;

    public GameTest() {
    }

    @BeforeEach     // Exécutée avant chaque test
    public void setUp() {
        game = new Game();
    }

    /*
    =========================
   *        START TEST      *
    =========================
     */
    
    /**
     * Not possible to make a full play without choosing
     */
//    @Test
//    public void start_when_game_over_ok() {
//        fullPlay();
//        
//        game.start(2);
//    }

    /* 
    *Play a game till the end 
    *ONLY POSSIBLE WITH NOT RANDOM
    *
     */
//    private void fullPlay() {
//        game.start(2);
//        for (int turn = 1; turn < game.getBoardSize() * game.getBoardSize(); turn++) {
//            for (int player = 0; player < game.getPlayerCount(); player++) {
//                game.pickFaceDownTile();
//                game.dropTile();
//                game.nextPlayer();
//            }
//        }
//        game.pickFaceUpTile(new Tile(16));
//        System.out.println(game.getTile(game.getCurrentPlayerNumber(), new Position(2,2)).getValue());
//        System.out.println(game.getTile(game.getCurrentPlayerNumber(), new Position(3,3)).getValue());
//        game.putTile(new Position(2,3));
//        game.nextPlayer();
//        game.pickFaceDownTile();
//        game.dropTile();
//        game.nextPlayer();
//        System.out.println(game.getState());
//    }
    
    /**
     * Work only if currentTile is between pos22 and pos33
     */
    private void fullPlayer_finish_tile_and_only_one_winner(){
        game.start(2);
        Tile tile;
        Tile tileBetweenPos = null;
        int tilePos22  = game.getTile(1, new Position(2,2)).getValue();
        int tilePos33  = game.getTile(1, new Position(3,3)).getValue();
        for(int tileFaceDown=0;tileFaceDown<31;tileFaceDown++){
            tile = game.pickFaceDownTile();
            if(tile.getValue() > tilePos22 && tile.getValue() <tilePos33){
                tileBetweenPos = tile;
            }
            game.dropTile();
            game.nextPlayer();
        }
        if(tileBetweenPos!=null){
            game.pickFaceUpTile(tileBetweenPos);
            int currentPlayer = game.getCurrentPlayerNumber();
            game.putTile(new Position(2,3));
            game.nextPlayer();
            game.pickFaceDownTile();
            game.dropTile();
            game.nextPlayer();
            var listeWinner = game.getWinners();
            assertEquals(currentPlayer, listeWinner.get(0));
            assertEquals(1, listeWinner.size());
            assertEquals(State.GAME_OVER,game.getState());
        }
            
    }
    
    private void fullPlayer_finish_tile_only(){
        game.start(2);
        for(int tileFaceDown=0;tileFaceDown<31;tileFaceDown++){
            game.pickFaceDownTile();
            game.dropTile();
            game.nextPlayer();
        }
            game.pickFaceDownTile();
            game.dropTile();
            game.nextPlayer();
            assertEquals(2, game.getWinners().size());
            assertEquals(State.GAME_OVER,game.getState());
    }

    @Test
    public void not_started_state(){
        assertEquals(game.getState(), State.NOT_STARTED);
    }
    
    @Test
    public void start_game_when_tile_in_diag_one_player(){
        game.start(2);
        for(var i=0;i<4;i++){
            assertNotEquals(null,game.getTile(0, new Position(i,i)));
        }
    }
    
    @Test
    public void start_game_when_tile_in_diag_is_face_up(){
        game.start(2);
        for(var i=0;i<4;i++){
            assertTrue(game.getTile(0, new Position(i,i)).isFaceUp());
        }
    }
    
    
    @Test
    public void start_game_when_count_face_down(){
        game.start(2);
        assertEquals(32, game.faceDownTileCount());
    }
    
    @Test
    public void start_when_get_player_count_exception(){
        assertThrows(IllegalStateException.class,()-> game.getPlayerCount());
    }
    
    @Test
    public void start_when_get_current_player_exception(){
        assertThrows(IllegalStateException.class,()-> game.getCurrentPlayerNumber());
    }
    
    
    @Test
    public void start_when_player_count_isGoodMax() {
        game.start(4);
    }

    @Test
    public void start_when_player_count_isGoodMin() {
        game.start(2);
    }

    @Test
    public void start_when_player_count_is_LowerOfMin() {
        assertThrows(IllegalArgumentException.class, () -> game.start(1));
    }

    @Test
    public void start_when_player_count_is_upperOfMax() {
        assertThrows(IllegalArgumentException.class, () -> game.start(5));
    }

    @Test
    public void start_when_notState_and_LowerOfMin() {
        game.start(2);
        assertThrows(IllegalStateException.class, () -> game.start(1));
    }

    @Test
    public void start_get_nextState() {
        game.start(2);
        assertEquals(State.PICK_TILE, game.getState());
    }

    @Test
    public void not_started_Pick_face_down_tile() {
        assertThrows(IllegalStateException.class, () -> game.pickFaceDownTile());
    }
    
    @Test
    public void not_started_Pick_face_up_tile() {
        assertThrows(IllegalStateException.class, () -> game.pickFaceUpTile(new Tile(4)));
    }

    @Test
    public void start_when_put_Tile_in_Board() {
        game.start(2);
        assertThrows(IllegalStateException.class, () -> game.putTile(new Position(0, 0)));
    }

    @Test
    public void start_get_number_player_in_Game() {
        game.start(2);
        assertEquals(2, game.getPlayerCount());
    }

    @Test
    public void start_get_current_player() {
        game.start(2);
        assertEquals(0, game.getCurrentPlayerNumber());
    }
    
    @Test
    public void start_get_board_size(){
        game.start(2);
        assertEquals(4, game.getBoardSize());
    }
    
    @Test
    public void start_get_tile_success_not_null(){
        game.start(2);
        assertNotEquals(null, game.getTile(0, new Position(0,0)));
    }
    
    @Test
    public void start_get_tile_success_null(){
        game.start(2);
        assertEquals(null, game.getTile(0, new Position(0,1)));
    }
    
    @Test
    public void start_get_tile_exception_player_number(){
        game.start(2);
        assertThrows(IllegalArgumentException.class, ()-> game.getTile(5, new Position(0,0)));
    }
    
    @Test
    public void start_get_tile_exception_position(){
        game.start(2);
        assertThrows(IllegalArgumentException.class, ()-> game.getTile(0, new Position(0,5)));
    }

    /*
    ======================================
   *      PICK TILE DOWN AND UP TEST     *
    ======================================
     */
    @Test
    public void pick_Face_Down_Tile_when_game_Started() {
        game.start(2);
        game.pickFaceDownTile();
    }
    
    @Test
    public void pick_Face_Up_Tile_when_game_Started() {
        game.start(2);
        Tile tile = game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceUpTile(tile);
        game.putTile(new Position(0,0));
    }

    @Test
    public void pick_Face_Down_Tile_when_game_is_process() {
        game.start(2);
        Tile tile = game.pickFaceDownTile();
        assertEquals(tile, game.getPickedTile());
    }

    @Test
    public void pick_Face_Down_Tile_state_exception() {
        game.start(2);
        game.pickFaceDownTile();
        assertThrows(IllegalStateException.class, () -> game.pickFaceDownTile());
    }

    @Test
    public void pick_Face_Down_Tile_current_state() {
        game.start(2);
        game.pickFaceDownTile();
        assertEquals(State.PLACE_OR_DROP_TILE, game.getState());
    }
    
    @Test
    public void pick_Face_Up_Tile_state_exception() {
        game.start(2);
        assertThrows(IllegalArgumentException.class, () -> game.pickFaceUpTile(new Tile(2)));
    }
    
    @Test
    public void pick_Face_Down_Up_Tile_state_exception() {
        game.start(2);
        game.pickFaceDownTile();
        assertThrows(IllegalStateException.class, () -> game.pickFaceUpTile(new Tile(2)));
    }

    @Test
    public void pick_Face_Up_Tile_current_state() {
        game.start(2);
        Tile tile = game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceUpTile(tile);
        assertEquals(State.PLACE_TILE, game.getState());
    }

    @Test
    public void pick_Face_Down_Tile_get_current_player() {
        game.start(2);
        game.pickFaceDownTile();
        assertEquals(0, game.getCurrentPlayerNumber());
    }

    @Test
    public void pick_Face_Down_Tile_when_tile_be_put_ok() {
        game.start(2);
        game.pickFaceDownTile();
        assertTrue(game.canTileBePut(new Position(0, 0)));
    }
    
    @Test
    public void pick_Face_Up_Tile_when_tile_be_put_ok() {
        game.start(2);
        Tile tile = game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceUpTile(tile);
        assertTrue(game.canTileBePut(new Position(0, 0)));
    }
    
    
    @Test
    public void pick_Face_Down_Tile_be_put_is_not_inside_lower() {
        game.start(2);
        game.pickFaceDownTile();
        assertThrows(IllegalArgumentException.class, () -> game.canTileBePut(new Position(-1, 0)));
    }

    @Test
    public void pick_Face_Down_Tile_when_tile_be_put_is_not_inside_upper() {
        game.start(2);
        game.pickFaceDownTile();
        assertThrows(IllegalArgumentException.class, () -> game.canTileBePut(new Position(0, 4)));
    }

    @Test
    public void pick_Face_Down_Tile_when_tile_be_put_is_not_inside_lowerUpper() {
        game.start(2);
        game.pickFaceDownTile();
        assertThrows(IllegalArgumentException.class, () -> game.canTileBePut(new Position(-1, 4)));
    }
    
    @Test
    public void pick_Face_Up_Tile_be_put_is_not_inside_lower() {
        game.start(2);
        Tile tile = game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceUpTile(tile);
        assertThrows(IllegalArgumentException.class, () -> game.canTileBePut(new Position(-1, 0)));
    }

    @Test
    public void pick_Face_Up_Tile_when_tile_be_put_is_not_inside_upper() {
        game.start(2);
        Tile tile = game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceUpTile(tile);
        assertThrows(IllegalArgumentException.class, () -> game.canTileBePut(new Position(0, 4)));
    }

    @Test
    public void pick_Face_Up_Tile_when_tile_be_put_is_not_inside_lowerUpper() {
        game.start(2);
        Tile tile = game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceUpTile(tile);
        assertThrows(IllegalArgumentException.class, () -> game.canTileBePut(new Position(-1, 4)));
    }

    /*
    =========================
   *      PUT TILE TEST     *
    =========================
     */
    @Test
    public void putTile_Tile_Down_when_game_continue_ok() {
        game.start(2);
        game.pickFaceDownTile();
        game.putTile(new Position(0, 0));
    }
    
    @Test
    public void putTile_Tile_Up_when_game_continue_ok() {
        game.start(2);
        Tile tile = game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceUpTile(tile);
        game.putTile(new Position(0, 0));
    }

    @Test
    public void putTile_when_game_is_process() {
        game.start(2);
        Tile tile = game.pickFaceDownTile();
        game.putTile(new Position(0, 0));
        assertEquals(tile, game.getTile(game.getCurrentPlayerNumber(), new Position(0, 0)));
    }
    
    @Test
    public void putTile_Tile_Up_when_game_is_process() {
        game.start(2);
        Tile tile = game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        Tile tile2 = game.pickFaceUpTile(tile);
        game.putTile(new Position(0, 0));
        assertEquals(tile, game.getTile(game.getCurrentPlayerNumber(), new Position(0, 0)));
    }

    @Test
    public void putTile_Tile_Down_state_exception() {
        game.start(2);
        game.pickFaceDownTile();
        game.putTile(new Position(0, 0));
        assertThrows(IllegalStateException.class, () -> game.putTile(new Position(0, 0)));
    }
    
    @Test
    public void putTile_Tile_Up_state_exception() {
        game.start(2);
        Tile tile = game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        Tile tile2 = game.pickFaceUpTile(tile);
        game.putTile(new Position(0, 0));
        assertThrows(IllegalStateException.class, () -> game.putTile(new Position(0, 0)));
    }

    @Test
    public void putTile_Tile_Down_current_state() {
        game.start(2);
        game.pickFaceDownTile();
        game.putTile(new Position(0, 0));
        assertEquals(State.TURN_END, game.getState());
    }
    
     @Test
    public void putTile_Tile_Up_current_state() {
        game.start(2);
        Tile tile = game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceUpTile(tile);
        game.putTile(new Position(0, 0));
        assertEquals(State.TURN_END, game.getState());
    }

    @Test
    public void putTile_inside_Upper() {
        game.start(2);
        game.pickFaceDownTile();
        assertThrows(IllegalArgumentException.class, () -> game.putTile(new Position(0, 4)));
    }
    
    @Test
    public void putTile_Tile_Down_isInside_ok() {
        game.start(2);
        game.pickFaceDownTile();
        assertTrue(game.isInside(new Position(0,0)));
    }
    
    @Test
    public void putTile_Tile_Up_isInside_ok() {
        game.start(2);
        Tile tile = game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceUpTile(tile);
        assertTrue(game.isInside(new Position(0,0)));
    }
    
    @Test
    public void putTile_isInside_not_ok() {
        game.start(2);
        game.pickFaceDownTile();
        assertFalse(game.isInside(new Position(-1,0)));
    }

    @Test
    public void putTile_inside_Lower() {
        game.start(2);
        Tile tile = game.pickFaceDownTile();
        assertThrows(IllegalArgumentException.class, () -> game.putTile(new Position(-1, 0)));
    }

    @Test
    public void putTile_inside_Both() {
        game.start(2);
        game.pickFaceDownTile();
        assertThrows(IllegalArgumentException.class, () -> game.putTile(new Position(-3, 5)));
    }
    
    @Test
    public void putTile_when_a_tile_is_not_empty_and_replace_size(){
        game.start(2);
        game.pickFaceDownTile();
        game.getTile(game.getCurrentPlayerNumber(), new Position(2,2));
        game.putTile(new Position (2,2));
        assertEquals(1, game.getAllfaceUpTiles().size());
    }
    
    @Test
    public void putTile_when_a_tile_is_not_empty_and_replace_equal(){
        game.start(2);
        Tile tile = game.pickFaceDownTile();
        Tile tile2 = game.getTile(game.getCurrentPlayerNumber(), new Position(2,2));
        game.putTile(new Position (2,2));
        assertEquals(tile, game.getTile(game.getCurrentPlayerNumber(), new Position(2,2)));
        assertEquals(tile2, game.getAllfaceUpTiles().get(0));
    }

    /*
    =========================
   *     NEXT PLAYER TEST    *
    =========================
     */
    
    @Test
    public void nextPlayer_when_game_continue_ok() {
        game.start(2);
        game.pickFaceDownTile();
        game.putTile(new Position(0, 0));
        game.nextPlayer();
    }
    
     @Test
    public void nextPlayer_when_Tile_up_game_continue_ok() {
        game.start(2);
        Tile tile = game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        game.pickFaceUpTile(tile);
        game.putTile(new Position(0,0));
    }
    
    @Test
    public void nextPlayer_state_exception() {
        game.start(2);
        game.pickFaceDownTile();
        game.putTile(new Position(0, 0));
        game.nextPlayer();
        assertThrows(IllegalStateException.class, () -> game.nextPlayer());
    }
    
    @Test
    public void nextPlayer_Tile_Up_state_exception() {
        game.start(2);
        game.pickFaceDownTile();
        game.dropTile();
        game.nextPlayer();
        assertThrows(IllegalStateException.class, () -> game.nextPlayer());
    }
    
    @Test
    public void nextPlayer_current_state() {
        game.start(2);
        game.pickFaceDownTile();
        game.putTile(new Position(0, 0));
        game.nextPlayer();
        assertEquals(State.PICK_TILE, game.getState());
    }
    
    @Test
    public void putTile_when_current_player_different() {
        game.start(2);
        game.pickFaceDownTile();
        game.putTile(new Position(0, 0));
        game.nextPlayer();
        assertEquals(1, game.getCurrentPlayerNumber());
    }
    
    
    /*
    =========================
   *      GAME OVER TEST     *
    =========================
     */
    
    @Test
    public void end_is_not_winner_exception(){
        game.start(2);
        game.pickFaceDownTile();
        game.putTile(new Position(0,0));
        assertThrows(IllegalStateException.class, ()-> game.getWinners());
    }
    
    @Test
    public void end_is_winner_state(){
        fullPlayer_finish_tile_and_only_one_winner();
        assertEquals(State.GAME_OVER, game.getState());
    }
    
    @Test
    public void end_with_one_winner_no_more_face_down(){
        fullPlayer_finish_tile_and_only_one_winner();
    }
    
    @Test
    public void end_with_double_winner_when_game_finish_tile_face_down(){
        fullPlayer_finish_tile_only();
    }
}
