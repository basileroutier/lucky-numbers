/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.luckynumbers.model;

/**
 * Create a position to indicate a position in the plates of the game
 * Their is two information in the position
 * The row and the column
 *
 * @author basile <54018@etu.he2b.be>
 */
public class Position {

    private int row;
    private int column;

    /**
     * Initialize the row and column to create a position
     *
     * @param row int: row position in the plates
     * @param column int: column position in the plates
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Return only the row position in the game
     *
     * @return   row position for the position
     */
    public int getRow() {
        return row;
    }

    /**
     * Return only the column position in the game
     *
     * @return column position for the position
     */
    public int getColumn() {
        return column;
    }

}
