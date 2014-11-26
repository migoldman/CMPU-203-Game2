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
    
    public Posn setPosn(int x, int y) {
        return new Posn(x,y);
    }
    
    public User onKeyEvent(String ke) {
        int x = pos.x;
        int y = pos.y;
        
        switch(ke) {
            case ("left"):
                if(x > 0) {
                    return new User(setPosn(x-1,y), rotation, HP);
                }
            case ("right"):
                if(x < 20) {
                    return new User(setPosn(x+1, y), rotation, HP);
                }
            case ("up"):
                if(y > 0) {
                    return new User(setPosn(x, y-1), rotation, HP);
                }
            case ("down"):
                if(y<20) {
                    return new User(setPosn(x,y+1), rotation, HP);
                }
            case ("W"):
                return new User(pos, Rotation.UP, HP);
            case ("A"):
                return new User(pos, Rotation.LEFT, HP);
            case ("S"):
                return new User(pos, Rotation.DOWN, HP);
            case ("D"):
                return new User(pos, Rotation.RIGHT, HP);
            default:
                return this;
        }
        
    }
}
