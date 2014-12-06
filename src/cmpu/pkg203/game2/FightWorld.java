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
    
    public int randomInt(int min, int max) {
        return random.nextInt((max-min)+1)+min;
    }
    
    public FightWorld makeWorld(int level) {
        User tempU = new User(new Posn(randomInt(0,5),randomInt(0,20)),Rotation.UP,false,3);
        LinkedList<Minions> mob = new LinkedList<>();
        for(int i = 0; i < level; i++) {
            mob.add(new Minions(new Posn(randomInt(10,19),randomInt(0,20))));
        }
    }
    
    //TODO
        //Make a spawner
            //graphics
        //Make the upgrade item
        //

    //#JustImagethings
    public WorldImage background() {
        return new RectangleImage(new Posn(SCREENWIDTH / 2, SCREENHEIGHT / 2),
                SCREENWIDTH, SCREENHEIGHT, new Black());
    }

    public WorldImage drawUser(int x, int y) {
        return new CircleImage(new Posn(x, y), SIZE, new Green());
    }
    
    public WorldImage drawUser(Posn place) {
        return new CircleImage(place, SIZE, new Green());
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
    
    public WorldImage drawWorld() {
        return new OverlayImages(drawMinions(enemies, enemies.size()-1),
            new OverlayImages(drawUser(user.pos),background()));
    }
    
    public FightWorld onTick() {
        User tempU = user;
        LinkedList<Minions> tempM = enemies;
        System.out.println(user.toString());
        System.out.println("game over is " + gameOver);
        if(user.HP <= 0) {
            gameOver = true;
        }
        for(int i = 0; i < tempM.size(); i++) {
            if(tempM.get(i).onUserHuh()) {
                user.loseHP();
                tempM.remove(i);
            }
            else if(tempM.get(i).onFireHuh()) {
                tempM.remove(i);
            }
        }
        if(boss.onUserHuh()) {
            user.loseHP();
            boss.teleport();
        }
        
    }    
    
    //Game Over is false
    
    public WorldEnd worldEnds() {
        if(gameOver) {
            System.out.println("You died! You got to level " + level);
            return new WorldEnd(true, new TextImage(new Posn(SCREENWIDTH /2, SCREENHEIGHT/2), ("GAME OVER"), 20, new White()));
        }
        else {
            return new WorldEnd(false, this.drawWorld());
        }
    }
}
