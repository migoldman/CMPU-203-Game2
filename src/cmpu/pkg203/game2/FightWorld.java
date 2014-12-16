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
    boolean gameOver;
    Random random = new Random();

    User user;
    LinkedList<Minions> enemies;
    BigBoss boss;
    int level;
    //grid size is 20 by 20

    FightWorld() {
        user = new User(new Posn(2,10), Rotation.UP, false, new Fire(), 3);
        enemies = new LinkedList<Minions>();
        for (int i = 0; i < level + 2; i++) {
            enemies.add(new Minions(new Posn(randomInt(10, 19), randomInt(0,20))));
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
        User tempU = new User(new Posn(2, 10), Rotation.UP, false, new Fire(), 3);
        LinkedList<Minions> mob = spawnMinions(user, boss, level);
        BigBoss tempB = new BigBoss(new Posn(20, 10), true, level + 2);
        return new FightWorld(tempU, mob, tempB, level);
    }
    
    public LinkedList<Minions> spawnMinions(User user, BigBoss boss, int level) {
        LinkedList<Minions> temp = new LinkedList();
        int x;
        int y;
        
        for(int i = 0; i < level+2; i++) {
            x = randomInt(10, 20);
            y = randomInt(0, 20);
            if((x == user.pos.x && y == user.pos.y)
                    || (x == boss.pos.x && y == boss.pos.y)) {
                 spawnMinions(user, boss, level);
            }
            temp.add(new Minions(new Posn(x,y)));
        }
        return temp;
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
        Iterator<Minions> evil = enemies.listIterator(0);
        LinkedList<Minions> nextM = new LinkedList();
        
        BigBoss bigbaddie = boss;
        BigBoss bigmove = boss.move(user);
        
        FightWorld nextW = new FightWorld(user, enemies, bigmove, level);
        
        System.out.println(user.toString());
        System.out.println("boss invicible? " + bigbaddie.invinc + " boss HP " + bigbaddie.HP);
        
        //Game over function
        if (user.isDeadHuh()) {
            gameOver = true;
        }

        //goes through the list to check if minion is on fire or on user
        while(evil.hasNext()) {
            Minions baddie = evil.next();
            System.out.println("Baddie is at " + baddie.pos.x + " and " + baddie.pos.y);
            if (baddie.onUserHuh(user)) {
                System.out.println("Baddie exploded!");
                nextW = new FightWorld(user.loseHP(), nextM, bigmove, level);
            }
            
            else if (baddie.onFireHuh(user)) {
                System.out.println("Baddie on fire!");
                nextW = new FightWorld(user, nextM, bigmove, level);
            } 
            
            else {
                nextM.add(baddie.move(user));
                bigbaddie = bigbaddie.move(user);
                nextW = new FightWorld(user, nextM, bigmove, level);
            }
        }
        
        //checks boss on fire and on user
            //if on user, teleport
        if (boss.onUserHuh(user)) {
            System.out.print("teleporting!");
            bigbaddie = boss.teleport();
            nextW =  new FightWorld(user.loseHP(), enemies, bigbaddie, level);
            
            //if on fire, check if dead
        } else if (boss.onFireHuh(user)) {
            if (boss.HP - 1 <= 0) {
                return makeWorld(level + 1);
                
                //else lose hp
            } else {
                System.out.println("OUCH said bb");
                bigbaddie = boss.loseHP();
                nextW = new FightWorld(user, enemies, bigbaddie.teleport(), level);
            }
            
            //If first wave is all dead, spawn minions
        } else if (enemies.size() == 0) {
            System.out.println("Spawning baddies");
            bigbaddie.invinc = false;
            bigmove.invinc = false;
            nextW = new FightWorld(user, spawnMinions(user,bigmove,level), bigmove, level);
        }
        
        System.out.println("Defaulting");
        System.out.println("size: " + enemies.size());
        while(evil.hasNext()) {
            nextM.add(evil.next().move(user));
        }
        return nextW.onKeyEvent("");
    }

    //Game Over is false
    public WorldEnd worldEnds() {
        if (user.isDeadHuh()) {
            System.out.println("You died! You got to level " + level);
            return new WorldEnd(true, new TextImage(new Posn(SCREENWIDTH / 2, SCREENHEIGHT / 2), ("GAME OVER! You got to level " + level), 20, new Black()));
        } else {
            return new WorldEnd(false, this.makeImage());
        }
    }

}
