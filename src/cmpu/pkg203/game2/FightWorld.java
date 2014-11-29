/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmpu.pkg203.game2;

import javalib.funworld.*;
import javalib.colors.*;
import java.util.Random;
import java.util.LinkedList;
import javalib.worldimages.*;

/**
 *
 * @author michaelgoldman
 */
public class FightWorld extends PauseWorld {

    static final int SCREENWIDTH = 1000;
    static final int SCREENHEIGHT = 1000;
    static final int SIZE = 50;
    boolean gameOver;
    Random random = new Random();

    User user;
    LinkedList<Minions> enemies;
    BigBoss boss;
    int level;
    //grid size is 20 by 20

    FightWorld(User user, LinkedList<Minions> enemies, BigBoss boss, int level) {
        this.user = user;
        this.enemies = enemies;
        this.boss = boss;
        this.level = level;
    }
    
    //TODO
        //Make a spawner
            //graphics
        //Make the upgrade item
        //
        
    
    public FightWorld takeDamage() {
        FightWorld tempFW = this;
        LinkedList<Minions> tempM = enemies;
        User tempU = user;
        for(int i =0; i < enemies.size(); i++) {
            if(enemies.get(i).onUserHuh()) {
                tempM.remove(i);
                tempU.loseHP();
                if(tempU.isDeadHuh()) {
                    gameOver = true;
                }
                tempFW = new FightWorld(tempU, tempM, boss, level);
            }
        }
        return tempFW;
    }

    //#JustImagethings
    public WorldImage background() {
        return new RectangleImage(new Posn(SCREENWIDTH / 2, SCREENHEIGHT / 2),
                SCREENWIDTH, SCREENHEIGHT, new Black());
    }

    public WorldImage drawUser(int x, int y) {
        return new CircleImage(new Posn(x, y), SIZE, new Green());
    }

    public WorldImage drawMinion(Minions Minion) {
        return new RectangleImage(new Posn(Minion.xm, Minion.ym), SIZE, SIZE, new White());
    }

    public WorldImage drawMinions(LinkedList<Minions> Minions, int counter) {
        if (Minions.isEmpty()) {
            return background();
        } else if (counter == -1) {
            return background();
        } else {
            return new OverlayImages(drawMinions(Minions, counter - 1),
                    drawMinion(Minions.get(counter)));
        }
    }
    
    public WorldEnd gameOver() {
        if(gameOver) {
            return null;
        }
        return null;
    }

}
