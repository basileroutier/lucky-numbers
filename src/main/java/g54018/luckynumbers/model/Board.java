/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.luckynumbers.model;

/**
 * Create a Board that initialize the tile in the game, it is a 4*4 size of Tile per default
 * Make multiple check to verify the tile value can be put into the board
 *
 * @author basile <54018@etu.he2b.be>
 */
public class Board {

    private Tile[][] tiles;

    /**
     * Initialize the board with a fix size 4*4 Tile array
     */
    public Board() {
        this.tiles = new Tile[4][4];
    }

    /**
     * Return the size of the Tile array
     *
     * @return return the size of the array
     */
    public int getSize() {
        return this.tiles.length;
    }

    /**
     * Check if the position put in parameter is inside the Board. 
     * If column and row is inside it, it return true else false.
     *
     * @param pos Position : position that contain row and column
     * @return true if Position is inside the Board, and false if not
     */
    public boolean isInside(Position pos) {
        int lastValidIndexBoard = getSize() - 1;
        return ((pos.getColumn() >= 0 && pos.getColumn() <= lastValidIndexBoard) && (pos.getRow() >= 0 && pos.getRow() <= lastValidIndexBoard));
    }

    /**
     * Return a Tile from the Board without check if it is in Board Title contain null or a method to return the integer of a Tile
     *
     * @param pos Position : position that contain row and column
     * @return return a Tile
     */
    public Tile getTile(Position pos) {
        return this.tiles[pos.getRow()][pos.getColumn()];
    }

    /**
     * Check with a Integer Tile and Position. 
     * Return false if the Tile is above or below the row Tiles on the board, 
     * it will not check for null Tiles cause it can be put
     *
     * @param tile Tile : Integer Tile that gonna be check
     * @param pos Position: position that contain row and column
     * @return return true if all the row is valid else return false
     */
    private boolean canBePutRow(Tile tile, Position pos) {
        int row = pos.getRow();
        int col = pos.getColumn();
        int lengthBoard = getSize();
        int currentTileValue = tile.getValue();
        Tile[][] boardTiles = this.tiles;

        for (var i = 0; i < lengthBoard; i++) {
            if (boardTiles[i][col] != null) {
                if (((currentTileValue <= boardTiles[i][col].getValue() && i < row) 
                         || (currentTileValue >= boardTiles[i][col].getValue() && i > row))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check with a Integer Tile and Position. 
     * Return false if the Tile is above or below the column Tiles on the board,
     * it will not check for null Tiles
     *
     * @param tile Tile : Integer Tile that gonna be check
     * @param pos Position: position that contain row and column
     * @return return true if all the column is valid else return false
     */
    private boolean canBePutCol(Tile tile, Position pos) {
        int row = pos.getRow();
        int col = pos.getColumn();
        int lengthBoard = getSize();
        int currentTileValue = tile.getValue();
        Tile[][] boardTiles = this.tiles;

        for (var j = 0; j < lengthBoard; j++) {
            if (boardTiles[row][j] != null) {
                if (((currentTileValue <= boardTiles[row][j].getValue() && col > j ) 
                        || (currentTileValue >= boardTiles[row][j].getValue() && col < j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Return true if the canBePutRow and canBePutCol method is true 
     * It return false if the value is possible to put at the selected position
     * Check the ascending order for canBePutRow and canBePutCol
     *
     * @param tile Tile : Integer Tile that gonna be check
     * @param pos Position: position that contain row and column
     * @return return true if both method is true else return false
     */
    public boolean canBePut(Tile tile, Position pos) {
        return (canBePutRow(tile, pos) && canBePutCol(tile, pos));
    }

    /**
     * Put a Tile into a Position in the BoardTile 
     * Without checking if the Position is in the Board or can be put
     *
     * @param tile Tile : Integer Tile that gonna be put inside the board
     * @param pos Position : position that contain row and column to place the Tile in the Board
     */
    public void put(Tile tile, Position pos) {
        int row = pos.getRow();
        int col = pos.getColumn();
        this.tiles[row][col] = tile;
    }

    /**
     * Check if the Board is ended, 
     * Return false if a Tile contain null 
     * Return true if we find no null inside it
     *
     * @return true if the board is full of Integer else return false
     */
    public boolean isFull() {
        int lengthBoard = getSize();
        for (var i = 0; i < lengthBoard; i++) {
            for (var j = 0; j < lengthBoard; j++) {
                if (tiles[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Return the Tile of the current board
     * @return array tile of the board 
     */
    public String toString() {
        return "Board{" + "tile=" + tiles + '}';
    }
    
}
