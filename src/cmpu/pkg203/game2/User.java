/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmpu.pkg203.game2;

import javalib.worldimages.*;
/**
 *
 * @author michaelgoldman
 */

public class User {
    Posn pos;
    Rotation rotation;
    int HP;
         
    
    User(Posn pos, Rotation rotation, int HP) {
        this.pos = pos;
        this.rotation = rotation;
        this.HP = HP;
    }
}
