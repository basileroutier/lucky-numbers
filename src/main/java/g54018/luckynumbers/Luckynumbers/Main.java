/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.luckynumbers.Luckynumbers;

import g54018.luckynumbers.controller.Controller;
import g54018.luckynumbers.model.Game;
import g54018.luckynumbers.model.Model;
import g54018.luckynumbers.view.MyView;
import g54018.luckynumbers.view.View;

/**
 * Create and launch the game for the user
 * 
 * @author basile <54018@etu.he2b.be>
 */
public class Main {
    public static void main(String[] args) {
        Model model = new Game();
        View view = new MyView(model);
        Controller controller = new Controller(model, view);
        controller.play();
    }
}
