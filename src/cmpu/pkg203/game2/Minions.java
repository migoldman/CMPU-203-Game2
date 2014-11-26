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
    User user;
    Fire fire;
    //position of minion
    int xm = this.pos.x;
    int ym = this.pos.y;
    //position of user
    int xu = user.pos.x;
    int yu = user.pos.y;
    
    public Minions(Posn pos) {
        this.pos = pos;
    }
    
    public Minions move() {
        //if left of user, move right
        if(xm < xu) {
            return new Minions(new Posn(xm+1,ym));
        }
        //if right of user, move left
        else if(xm > xu) {
            return new Minions(new Posn(xm-1, ym));
        }
        //if above under user, move down
        else if(ym < yu) {
            return new Minions(new Posn(xm, ym+1));
        }
        //else move up
        else {
            return new Minions(new Posn(xm, ym-1));
        }
    }
    
    
    //if the minion is in the same x and y of the fire 
        //(including the width or height), return true
    
    public boolean onFireHuh() {
        Rotation fireR = fire.rotation;
        switch(fireR){
            case UP:
                //fire height takes into account upgrade, so it is fine
                if((ym > yu && ym < yu + fire.height())
                        && xm == xu) {
                    return true;
                }
            case DOWN:
                if((ym < yu && ym < yu - fire.height())
                        && xm == xu) {
                    return true;
                }
            case RIGHT:
                if((xm > xu && xm < xu + fire.width()
                        && ym == yu)) {
                    return true;
                }
            case LEFT:
                if((xm < xu && xm < xu - fire.width()
                        && ym == yu)) {
                    return true;
                }
            default:
                return false;
        }
    }
        
    public boolean onUserHuh() {   
        if(xm == xu && ym == yu) {
            return true;
        }
        else {
            return false;
        }
    }
}
