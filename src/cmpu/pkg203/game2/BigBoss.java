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
public class BigBoss {
    
    User user;
    Posn pos;
    Fire fire;
    boolean invinc;
    int HP;
    
    int xb = this.pos.x;
    int yb = this.pos.y;
    int xu = user.pos.x;
    int yu = user.pos.y;
    
    BigBoss(Posn pos, boolean invinc, int HP) {
        this.pos = pos;
        this.invinc = invinc;
        this.HP = HP;
    }
    
    public BigBoss teleport() {
        return new BigBoss(new Posn(20,10),this.invinc,this.HP);
    }
        
    public BigBoss move() {
        //if left of user, move right
        if(xb < xu) {
            return new BigBoss(new Posn(xb+1,yb), false, HP);
        }
        //if right of user, move left
        else if(xb > xu) {
            return new BigBoss(new Posn(xb-1, yb), false, HP);
        }
        //if above under user, move down
        else if(yb < yu) {
            return new BigBoss(new Posn(xb, yb+1), false, HP);
        }
        //else move up
        else {
            return new BigBoss(new Posn(xb, yb-1), false, HP );
        }
    }
    
    public boolean onFireHuh() {
        Rotation fireR = fire.rotation;
        switch(fireR){
            case UP:
                //fire height takes into account upgrade, so it is fine
                if((yb > yu && yb < yu + fire.height())
                        && xb == xu) {
                    return true;
                }
            case DOWN:
                if((yb < yu && yb < yu - fire.height())
                        && xb == xu) {
                    return true;
                }
            case RIGHT:
                if((xb > xu && xb < xu + fire.width()
                        && yb == yu)) {
                    return true;
                }
            case LEFT:
                if((xb < xu && xb < xu - fire.width()
                        && yb == yu)) {
                    return true;
                }
            default:
                return false;
        }
    }
    
    public boolean onUserHuh() {   
        if(xb == xu && yb == yu) {
            return true;
        }
        else {
            return false;
        }
    }
}
