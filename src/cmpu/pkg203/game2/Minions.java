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
public class Minions {

    Posn pos;
    int width, height;

    public Minions(Posn pos) {
        this.pos = pos;
    }

    public Minions move(User user) {
        //position of minion
        int xm = this.pos.x;
        int ym = this.pos.y;
        //position of user
        int xu = user.pos.x;
        int yu = user.pos.y;
        //if left of user, move right
        if (xm < xu) {
            return new Minions(new Posn(xm + 1, ym));
        } //if right of user, move left
        else if (xm > xu) {
            return new Minions(new Posn(xm - 1, ym));
        } //if above under user, move down
        else if (ym < yu) {
            return new Minions(new Posn(xm, ym + 1));
        } //else move up
        else {
            return new Minions(new Posn(xm, ym - 1));
        }
    }

    //if the minion is in the same x and y of the fire 
    //(including the width or height), return true
    public boolean onFireHuh(User user) {
        //position of minion
        int xm = this.pos.x;
        int ym = this.pos.y;
        //position of user
        int xu = user.pos.x;
        int yu = user.pos.y;

        Rotation fireR = user.fire.rotation;
        if (user.firing) {
            switch (fireR) {
                case UP:
                    //fire height takes into account upgrade, so it is fine
                    if ((ym == yu - 1)
                            && xm == xu) {
                        return true;
                    }
                case DOWN:
                    if ((ym == yu + 1)
                            && xm == xu) {
                        return true;
                    }
                case RIGHT:
                    if ((xm == xu + 1
                            && ym == yu)) {
                        return true;
                    }
                case LEFT:
                    if ((xm == xu - 1
                            && ym == yu)) {
                        return true;
                    }
            }
        }
        return false;
    }

    public boolean onUserHuh(User user) {
        //position of minion
        int xm = this.pos.x;
        int ym = this.pos.y;
        //position of user
        int xu = user.pos.x;
        int yu = user.pos.y;
        if (xm == xu && ym == yu) {
            return true;
        } else {
            return false;
        }
    }

}
