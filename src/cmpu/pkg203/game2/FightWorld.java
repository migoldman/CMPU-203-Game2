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
    static final int SIZE = 25;
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
            x = randomInt(0, 20);
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
        return new OverlayImages(
                new RectangleImage(new Posn(SCREENWIDTH / 2, SCREENHEIGHT / 2),
                    SCREENWIDTH, SCREENHEIGHT, new Black()), 
        new TextImage(new Posn(SCREENWIDTH / 2, SCREENHEIGHT-SIZE*2), 
                    ("HP: " + user.HP + "  X: " + user.pos.x + "  Y: " + user.pos.y + "  Rotation: " + user.rotation + "  Minions alive: " + enemies.size() + "  Boss HP: " + boss.HP + "  Level: " + level),
                    12, new Yellow()));
    }

    public WorldImage drawUser() {
        return new DiskImage(new Posn(user.pos.x*SIZE, user.pos.y*SIZE), SIZE, new Green());
    }

    public WorldImage drawFire() {
        if(user.firing) {
            switch (user.fire.rotation) {
                case UP:
                    return new RectangleImage(new Posn(user.pos.x*SIZE, (user.pos.y - 1)*SIZE), SIZE, SIZE, new Blue());
                case LEFT:
                    return new RectangleImage(new Posn((user.pos.x - 1)*SIZE, user.pos.y*SIZE), SIZE, SIZE, new Blue());
                case DOWN:
                    return new RectangleImage(new Posn(user.pos.x*SIZE, (user.pos.y + 1)*SIZE), SIZE, SIZE, new Blue());
                default:
                    return new RectangleImage(new Posn((user.pos.x +1)*SIZE, user.pos.y*SIZE), SIZE, SIZE, new Blue());
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
        if(boss.invinc) {
            return new DiskImage(new Posn(boss.pos.x*SIZE, boss.pos.y*SIZE), SIZE, new Yellow());
        }
        return new DiskImage(new Posn(boss.pos.x*SIZE, boss.pos.y*SIZE), SIZE, new Red());
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
        return new FightWorld(user, enemies, boss, level);
    }

    public World onTick() {
        Iterator<Minions> evil = enemies.listIterator(0);
        LinkedList<Minions> nextM = new LinkedList();
        
        BigBoss bigbaddie = boss;
        BigBoss bigmove = boss.move(user);
        
        User nextU = user;
        
        FightWorld nextW = new FightWorld(user, enemies, bigmove, level);
                
        //Game over function
        if (user.isDeadHuh()) {
            gameOver = true;
        }

        //goes through the list to check if minion is on fire or on user
        while(evil.hasNext()) {
            Minions baddie = evil.next();
            if (baddie.onUserHuh(nextU)) {
                System.out.println("Baddie exploded!");
                nextU = nextU.loseHP();
                nextW = new FightWorld(nextU, nextM, bigmove, level);
            }
            
            else if (baddie.onFireHuh(nextU)) {
                System.out.println("Baddie on fire!");
                nextW = new FightWorld(nextU, nextM, bigmove, level);
            } 
            
            else {
                nextM.add(baddie.move(nextU));
                bigbaddie = bigbaddie.move(nextU);
                nextW = new FightWorld(nextU, nextM, bigmove, level);
            }
        }
        
        //checks boss on fire and on user
            //if on user, teleport
        if (boss.onUserHuh(nextU)) {
            System.out.print("Take that! TATICAL RETREAT");
            bigbaddie = boss.teleport();
            nextW =  new FightWorld(nextU.loseHP(), nextM, bigbaddie, level);
            
            //if on fire, check if dead
        } else if (boss.onFireHuh(nextU)) {
            if(boss.invinc) {
                System.out.println("YOUR ATTACK DOES NOTHING TO ME!");
            }
            else {
                System.out.println("OUCH said bb");

                if (boss.HP - 1 <= 0) {
                    System.out.println("LEVEL " + (level+1) + "!");
                    return makeWorld(level + 1);

                    //else lose hp
                } else {
                    bigbaddie = boss.loseHP();
                    nextW = new FightWorld(nextU, nextM, bigbaddie.teleport(), level);
                }
            }
            
            //If first wave is all dead, spawn minions
        } else if (enemies.size() == 0) {
            System.out.println("Spawning baddies");
            bigbaddie.invinc = false;
            bigmove.invinc = false;
            nextW = new FightWorld(user, spawnMinions(nextU,bigmove,level), bigmove, level);
        }
        
        while(evil.hasNext()) {
            nextM.add(evil.next().move(nextU));
        }
        return nextW.onKeyEvent("");
    }

    //Game Over is false
    public WorldEnd worldEnds() {
        if (user.isDeadHuh()) {
            System.out.println("You died! You got to level " + level);
            return new WorldEnd(true, new TextImage(new Posn(SCREENWIDTH / 2, SCREENHEIGHT / 2), 
                    ("GAME OVER! You got to level " + level), 20, new Black()));
        } else {
            return new WorldEnd(false, this.makeImage());
        }
    }

}
