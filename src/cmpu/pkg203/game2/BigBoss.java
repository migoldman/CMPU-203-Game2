/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmpu.pkg203.game2;

import java.util.LinkedList;
import java.util.Random;
import javalib.worldimages.Posn;

/**
 *
 * @author michaelgoldman
 */
public class BigBoss {

    Posn pos;
    boolean invinc;
    int HP;
    Random random = new Random();

    BigBoss(Posn pos, boolean invinc, int HP) {
        this.pos = pos;
        this.invinc = invinc;
        this.HP = HP;
    }

    public BigBoss loseHP() {
        return new BigBoss(pos, false, HP - 1);
    }

    public BigBoss teleport(User user, LinkedList<Minions> enemies) {
        int x = random.nextInt((20 - 0) + 1) + 0;
        int y = random.nextInt((20 - 0) + 1) + 0;
        for(int i = 0; i < enemies.size(); i++) {
            if((x == user.pos.x && y == user.pos.y) 
                    || (x == enemies.get(i).pos.x && y == enemies.get(i).pos.y)) {
                return teleport(user, enemies);
            }
        }
        return new BigBoss(new Posn(x,y), invinc, HP);
    }

    public BigBoss move(User user) {
        int xb = this.pos.x;
        int yb = this.pos.y;
        int xu = user.pos.x;
        int yu = user.pos.y;
        if (!invinc) {
            //if left of user, move right
            if (yb < yu) {
                return new BigBoss(new Posn(xb, yb + 1), false, HP);
            } //else move up
            else if (yb > yu) {
                return new BigBoss(new Posn(xb, yb - 1), false, HP);
            }
            if (xb < xu) {
                return new BigBoss(new Posn(xb + 1, yb), false, HP);
            } //if right of user, move left
            else {
                return new BigBoss(new Posn(xb - 1, yb), false, HP);
            } //if above under user, move down
        } else {
            return new BigBoss(new Posn(20, 10), invinc, HP);
        }
    }

    public boolean onFireHuh(User user) {
        int xb = this.pos.x;
        int yb = this.pos.y;
        int xu = user.pos.x;
        int yu = user.pos.y;
        if (user.firing) {
            if(!this.invinc) {
                Rotation fireR = user.fire.rotation;
                switch (fireR) {
                    case UP:
                        //fire height takes into account upgrade, so it is fine
                        if ((yb == yu - 1)
                                && xb == xu) {
                            return true;
                        }
                    case DOWN:
                        if ((yb == yu + 1 )
                                && xb == xu) {
                            return true;
                        }
                    case RIGHT:
                        if ((xb == xu + 1
                                && yb == yu)) {
                            return true;
                        }
                    case LEFT:
                        if ((xb == xu - 1
                                && yb == yu)) {
                            return true;
                        }
                }

            }
        }
        return false;
    }

    public boolean onUserHuh(User user) {
        int xb = this.pos.x;
        int yb = this.pos.y;
        int xu = user.pos.x;
        int yu = user.pos.y;
        if (xb == xu && yb == yu) {
            return true;
        } else {
            return false;
        }
    }
}
