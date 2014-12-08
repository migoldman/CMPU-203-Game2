/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmpu.pkg203.game2;

/**
 *
 * @author michaelgoldman
 */
public class CMPU203Game2 {
    //implements world
        //create a pause world and a fight world
            //pause world has
                //options (maybe)
                //restart
            //fight world
                //user 
                //enemy
                //boss
                //upgrade
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FightWorld game = new FightWorld();
        game.bigBang(500, 500, 1);
        
    }
    
}
