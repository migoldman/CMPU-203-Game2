/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmpu.pkg203.game2;

import javalib.funworld.*;
import javalib.colors.*;
import java.util.*;
import javalib.worldimages.*;


/**
 *
 * @author michaelgoldman
 */
public class FightWorld extends World {

    static final int SCREENWIDTH = 600;
    static final int SCREENHEIGHT = 600;
    static final int SIZE = 10;
    boolean firstWaveHuh = true;
    boolean gameOver;
    Random random = new Random();

    User user;
    LinkedList<Minions> enemies;
    BigBoss boss;
    int level;
    //grid size is 20 by 20

    FightWorld() {
        user = new User(new Posn(randomInt(0, 5), randomInt(0, 20)), Rotation.UP, false, new Fire(), 3);
        enemies = new LinkedList<Minions>();
        for (int i = 0; i < level + 1; i++) {
            enemies.add(new Minions(new Posn(randomInt(10, 19), randomInt(0, 20))));
        }
        boss = new BigBoss(new Posn(20, 10), true, level);
        level = 1;
    }

    FightWorld(User user, LinkedList<Minions> enemies, BigBoss boss, int level) {
        this.user = user;
        this.enemies = enemies;
        this.boss = boss;
        this.level = level;
    }

    public int randomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public FightWorld makeWorld() {
        return makeWorld(1);
    }

    public FightWorld makeWorld(int level) {
        User tempU = new User(new Posn(randomInt(0, 5), randomInt(0, 20)), Rotation.UP, false, new Fire(), 3);
        LinkedList<Minions> mob = new LinkedList<>();
        BigBoss tempB = new BigBoss(new Posn(20, 10), true, level + 2);
        for (int i = 0; i < level + 1; i++) {
            mob.add(new Minions(new Posn(randomInt(10, 19), randomInt(0, 20))));
        }
        return new FightWorld(tempU, mob, tempB, level);
    }

    public FightWorld spawnMinions() {
        LinkedList<Minions> mob = new LinkedList<>();
        for (int i = 0; i < level + 1; i++) {
            mob.add(new Minions(new Posn(randomInt(10, 19), randomInt(0, 20))));
        }
        return new FightWorld(this.user, mob, boss, level);
    }

    //#JustImagethings
    public WorldImage background() {
        return new RectangleImage(new Posn(SCREENWIDTH / 2, SCREENHEIGHT / 2),
                SCREENWIDTH, SCREENHEIGHT, new Black());
    }

    public WorldImage drawUser() {
        return new CircleImage(new Posn(user.pos.x*SIZE, user.pos.y*SIZE), SIZE, new Green());
    }

    public WorldImage drawFire() {
        if(user.firing) {
            switch (user.fire.rotation) {
                case UP:
                    return new RectangleImage(new Posn(user.pos.x*SIZE, (user.pos.y - 1)*SIZE), SIZE, SIZE, new Red());
                case LEFT:
                    return new RectangleImage(new Posn((user.pos.x - 1)*SIZE, user.pos.y*SIZE), SIZE, SIZE, new Red());
                case DOWN:
                    return new RectangleImage(new Posn(user.pos.x*SIZE, (user.pos.y + 1)*SIZE), SIZE, SIZE, new Red());
                default:
                    return new RectangleImage(new Posn((user.pos.x +1)*SIZE, user.pos.y*SIZE), SIZE, SIZE, new Red());
            }
        }
        else {
            return new RectangleImage(new Posn(user.pos.x*SIZE, user.pos.y*SIZE), 0, 0, new White());
        }
    }

    public WorldImage drawMinion(Minions Minion) {
        return new RectangleImage(new Posn(Minion.pos.x*SIZE, Minion.pos.y*SIZE), SIZE, SIZE, new White());
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
        return new CircleImage(new Posn(boss.pos.x*SIZE, boss.pos.y*SIZE), SIZE, new Red());
    }

    //Hvae the user, fire, boss, minions, then background
    public WorldImage makeImage() {
        return new OverlayImages(background(),
                new OverlayImages(drawMinions(enemies, enemies.size()-1),
                        new OverlayImages(drawBoss(),
                                new OverlayImages(drawFire(), drawUser()))));
    }
    //onkeyevent
    //if key = p, return pauseworld

    public World onKeyEvent(String ke) {
        if (ke.equals("a") || ke.equals("w") || ke.equals("s") || ke.equals("d")
                || ke.equals("up") || ke.equals("down") || ke.equals("left") 
                || ke.equals("right") || ke.equals(" ")) {
            
            return new FightWorld(user.onKeyEvent(ke), enemies, boss, level);
        } else if (ke.equals("p")) {
            return new PauseWorld(user, enemies, boss, level);
        }
        System.out.println("nothing pressed");
        return new FightWorld(user, enemies, boss, level);
    }

    public World onTick() {
        int counter = 0;
        LinkedList<Minions> startM = enemies;
        LinkedList<Minions> nextM = new LinkedList();
        FightWorld nextW = new FightWorld(user, nextM, boss, level);
        
        System.out.println(user.toString());
        
        //Game over function
        if (user.HP <= 0) {
            gameOver = true;
        }
        System.out.println("game over is " + gameOver);

        //goes through the list to check if minion is on fire or on user
        while(counter < startM.size()) {
            
            Minions baddie = startM.get(counter).move(user);
            System.out.println("Baddie is at " + baddie.pos.x + " and " + baddie.pos.y);
            if (baddie.onUserHuh(user)) {
                nextW = new FightWorld(user.loseHP(), nextM, boss.move(user), level);
                counter++;
            } 
            
            else if (startM.get(counter).onFireHuh(user)) {
                nextW = new FightWorld(user, nextM, boss.move(user), level);
                counter++;
            } 
            
            else {
                nextM.add(baddie);
                nextW = new FightWorld(user, nextM, boss.move(user), level);
                counter++;
            }
        }
        
        //checks boss on fire and on user
            //if on user, teleport
        if (boss.onUserHuh(user)) {
            nextW =  new FightWorld(user.loseHP(), startM, boss.teleport(), level);
            
            //if on fire, check if dead
        } else if (boss.onFireHuh(user)) {
            if (boss.HP - 1 <= 0) {
                return makeWorld(level + 1);
                
                //else lose hp
            } else {
                nextW = new FightWorld(user, startM, boss.loseHP(), level);
            }
            
            //If first wave is all dead, spawn minions
        } else if (firstWaveHuh && startM.size() == 0) {
            firstWaveHuh = false;
            this.spawnMinions();
            boss.invinc = false;
            nextW = new FightWorld(user, startM, boss, level);
        }
        System.out.println("Defaulting");
        for(int i = 0; i < startM.size()-1; i ++) {
            nextM.add(startM.get(i).move(user));
        }
        return nextW.onKeyEvent("");
    }

    //Game Over is false
    public WorldEnd worldEnds() {
        if (gameOver) {
            System.out.println("You died! You got to level " + level);
            return new WorldEnd(true, new TextImage(new Posn(SCREENWIDTH / 2, SCREENHEIGHT / 2), ("GAME OVER"), 20, new White()));
        } else {
            return new WorldEnd(false, this.makeImage());
        }
    }

}
