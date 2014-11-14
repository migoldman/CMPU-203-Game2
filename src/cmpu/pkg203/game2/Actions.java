/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmpu.pkg203.game2;

import javalib.worldimages.Posn;

/**
 *
 * @author michaelgoldman
 */
public interface Actions {
    Posn move(int x, int y);
    Fire attack();
    
}
