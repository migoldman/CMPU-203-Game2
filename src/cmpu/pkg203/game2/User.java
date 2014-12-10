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
    Fire fire;
    int HP;

    User(Posn pos, Rotation rotation, boolean firing, Fire fire, int HP) {
        this.pos = pos;
        this.rotation = rotation;
        this.firing = firing;
        this.fire = fire;
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
                    return new User(setPosn(x - 1, y), rotation, firing, fire, HP);
                }
            case ("right"):
                if (x < 20) {
                    return new User(setPosn(x + 1, y), rotation, firing, fire, HP);
                }
            case ("up"):
                if (y > 0) {
                    return new User(setPosn(x, y - 1), rotation, firing, fire, HP);
                }
            case ("down"):
                if (y < 20) {
                    return new User(setPosn(x, y + 1), rotation, firing, fire, HP);
                }
            case ("w"):
                return new User(pos, Rotation.UP, firing, fire, HP);
            case ("a"):
                return new User(pos, Rotation.LEFT, firing, fire, HP);
            case ("s"):
                return new User(pos, Rotation.DOWN, firing, fire, HP);
            case ("d"):
                return new User(pos, Rotation.RIGHT, firing, fire, HP);
                //What is the actual variable name for the spacebar key?
            case (" "):
                if (firing == true) {
                    return new User(pos, rotation, false, new Fire(), HP);
                } else {
                    return new User(pos, rotation, true, fire.attack(this), HP);
                }
            default:
                return this;
        }
    }
    
    public User loseHP() {
        return new User(pos, rotation, firing, fire, HP-1);
    }
    
    public boolean isDeadHuh() {
        if(HP <= 0) {
            return true;
        }
        return false;
    }
    
    public String toString() {
        return "X: " + pos.x + " Y: " + pos.y + 
                " Rotation: " + rotation + " firing: " + firing +
                " HP: " + HP + " isDead: " + isDeadHuh();
    }
}
