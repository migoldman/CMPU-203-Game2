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
public class FightWorld extends World {

    static final int SCREENWIDTH = 1000;
    static final int SCREENHEIGHT = 1000;
    static final int SIZE = 50;
    boolean firstWaveHuh = true;
    boolean gameOver;
    Random random = new Random();

    Upgrade upgrade;
    Fire fire;
    User user;
    LinkedList<Minions> enemies;
    BigBoss boss;
    int level;
    //grid size is 20 by 20
    
    FightWorld() {
        user = new User(new Posn(randomInt(0,5),randomInt(0,20)),Rotation.UP,false,3);
        enemies = new LinkedList<Minions>();
        for(int i = 0; i <level+1; i++) {
            enemies.add(new Minions(new Posn(randomInt(10,19), randomInt(0,20))));
        }
        boss = new BigBoss(new Posn(20,10),true, level);
        level = 1;
    }

    FightWorld(User user, LinkedList<Minions> enemies, BigBoss boss, int level) {
        this.user = user;
        this.enemies = enemies;
        this.boss = boss;
        this.level = level;
    }
    
    public int randomInt(int min, int max) {
        return random.nextInt((max-min)+1)+min;
    }
    
    public FightWorld makeWorld() {
        return makeWorld(1);
    }
    
    public FightWorld makeWorld(int level) {
        User tempU = new User(new Posn(randomInt(0,5),randomInt(0,20)),Rotation.UP,false,3);
        LinkedList<Minions> mob = new LinkedList<>();
        BigBoss tempB = new BigBoss(new Posn(20,10),true, level+2);
        for(int i = 0; i < level+1; i++) {
            mob.add(new Minions(new Posn(randomInt(10,19),randomInt(0,20))));
        }
        return new FightWorld(tempU,mob,tempB,level);
    }
    
    public FightWorld spawnMinions() {
        LinkedList<Minions> mob = new LinkedList<>();
        for(int i = 0; i < level+1; i++) {
            mob.add(new Minions(new Posn(randomInt(10, 19), randomInt(0,20))));
        }
        return new FightWorld(this.user,mob,boss,level);
    }
    
    //TODO
            //graphics
        //Make the upgrade item
        //

    //#JustImagethings
    public WorldImage background() {
        return new RectangleImage(new Posn(SCREENWIDTH / 2, SCREENHEIGHT / 2),
                SCREENWIDTH, SCREENHEIGHT, new Black());
    }
    
    
    public WorldImage drawUser() {
        return new CircleImage(user.pos, SIZE, new Green());
    }
    
    public WorldImage drawUser(Posn place) {
        return new CircleImage(place, SIZE, new Green());
    }
    
    public WorldImage drawFire() {
        switch(fire.rotation) {
            case UP:
                return new RectangleImage(new Posn(user.pos.x, user.pos.y+1), SIZE, SIZE, new Red());
            case LEFT:
                return new RectangleImage(new Posn(user.pos.x-1, user.pos.y), SIZE, SIZE, new Red());
            case DOWN:
                return new RectangleImage(new Posn(user.pos.x, user.pos.y-1), SIZE, SIZE, new Red());
            default:
                return new RectangleImage(new Posn(user.pos.x, user.pos.y+1), SIZE, SIZE, new Red());
        }
    }

    public WorldImage drawMinion(Minions Minion) {
        return new RectangleImage(new Posn(Minion.pos.x, Minion.pos.y), SIZE, SIZE, new White());
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
    
    public WorldImage drawBoss() {
        return new CircleImage(boss.pos, SIZE, new Red());
    }
    
    public WorldImage drawBoss(Posn place) {
        return new CircleImage(place ,SIZE, new Red());
    }
    
    //Hvae the user, fire, boss, minions, then background
    public WorldImage makeImage() {
        return new OverlayImages(background(),
                new OverlayImages(drawMinions(enemies,enemies.size()),
                        new OverlayImages(drawBoss(),
                            new OverlayImages(drawFire(),drawUser()))));
    }
    //onkeyevent
        //if key = p, return pauseworld
    public FightWorld onTick() {
        LinkedList<Minions> tempM = enemies;
        System.out.println(user.toString());
        System.out.println("game over is " + gameOver);
        if(user.HP <= 0) {
            gameOver = true;
        }
        for(int i = 0; i < tempM.size(); i++) {
            if(tempM.get(i).onUserHuh()) {
                tempM.remove(i);
                return new FightWorld(user.loseHP(), tempM, boss.move(), level);
            }
            else if(tempM.get(i).onFireHuh()) {
                tempM.remove(i);
                return new FightWorld(user, tempM, boss.move(), level);
            }
            else {
                tempM.get(i).move();
                return new FightWorld(user, tempM, boss.move(), level);
            }
        }
        if(boss.onUserHuh()) {
            return new FightWorld(user.loseHP(), enemies, boss.teleport(), level);
        }
        else if(boss.onFireHuh()) {
            if(boss.HP -1 <= 0) {
                return makeWorld(level+1);
            }
            else {
                return new FightWorld(user, enemies, boss.loseHP(), level);
            }
        }
        else if(firstWaveHuh && tempM.size()==0) {
            firstWaveHuh = false;
            this.spawnMinions();
            boss.invinc = false;
            return new FightWorld(user, enemies, boss, level);
        }
        return new FightWorld(user, enemies, boss.move(),level);
    }    
    
    //Game Over is false
    
    public WorldEnd worldEnds() {
        if(gameOver) {
            System.out.println("You died! You got to level " + level);
            return new WorldEnd(true, new TextImage(new Posn(SCREENWIDTH /2, SCREENHEIGHT/2), ("GAME OVER"), 20, new White()));
        }
        else {
            return new WorldEnd(false, this.makeImage());
        }
    }

    
}
