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
    boolean firing;
    int HP;

    User(Posn pos, Rotation rotation, boolean firing, int HP) {
        this.pos = pos;
        this.rotation = rotation;
        this.firing = firing;
        this.HP = HP;
    }

    public Posn setPosn(int x, int y) {
        return new Posn(x, y);
    }

    public User onKeyEvent(String ke) {
        int x = pos.x;
        int y = pos.y;

        switch (ke) {
            //Move keys
            case ("left"):
                if (x > 0) {
                    return new User(setPosn(x - 1, y), rotation, firing, HP);
                }
            case ("right"):
                if (x < 20) {
                    return new User(setPosn(x + 1, y), rotation, firing, HP);
                }
            case ("up"):
                if (y > 0) {
                    return new User(setPosn(x, y - 1), rotation, firing, HP);
                }
            case ("down"):
                if (y < 20) {
                    return new User(setPosn(x, y + 1), rotation, firing, HP);
                }

            //Rotation keys
            case ("W"):
                return new User(pos, Rotation.UP, firing, HP);
            case ("A"):
                return new User(pos, Rotation.LEFT, firing, HP);
            case ("S"):
                return new User(pos, Rotation.DOWN, firing, HP);
            case ("D"):
                return new User(pos, Rotation.RIGHT, firing, HP);
                //What is the actual variable name for the spacebar key?
            case ("T"):
                if (firing == true) {
                    return new User(pos, rotation, false, HP);
                } else {
                    return new User(pos, rotation, true, HP);
                }
            default:
                return this;
        }
    }
    
    public User loseHP() {
        return new User(pos, rotation, firing, HP-1);
    }
    
    public boolean isDeadHuh() {
        if(HP <= 0) {
            return true;
        }
        return false;
    }
}
