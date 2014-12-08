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
public class Upgrade {
    Posn pos;
    User user;
    int xu = user.pos.x;
    int yu = user.pos.y;
    
    Upgrade(Posn pos) {
        this.pos = pos;
    }
    
    //unsure if i want this now :/
    
    public boolean pickedUpHuh() {   
        if(pos.x == xu && pos.y == yu) {
            return true;
        }
        else {
            return false;
        }
    }
}
