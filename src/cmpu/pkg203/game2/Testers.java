/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmpu.pkg203.game2;

import tester.*;
/**
 *
 * @author michaelgoldman
 */
import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.colors.*;

import java.io.*;
import java.util.*;

public class Testers {

    LinkedList<Minions> MT = new LinkedList();
    User user = new User(new Posn(5, 5), Rotation.UP, false, new Fire(), 3);
    Fire fire = new Fire();
    BigBoss bbInvinc = new BigBoss(new Posn(20, 10), true, 3);
    BigBoss bbNonvinc = new BigBoss(new Posn(20, 10), false, 3);
    int level1 = 1;
    FightWorld fwEI = new FightWorld(user, MT, bbInvinc, level1);
    LinkedList<Minions> enemies = fwEI.spawnMinions(user, bbInvinc, level1);
    FightWorld fwNN = new FightWorld(user, enemies, bbInvinc, level1);
    PauseWorld pw = new PauseWorld(user, enemies, bbInvinc, level1);

    public boolean testUserMoveNonFire(Tester t) {
        //5,5, up, not firing
        User user = new User(new Posn(5, 5), Rotation.UP, false, new Fire(), 3);
        User up = new User(new Posn(5, 0), Rotation.UP, false, new Fire(), 3);
        User down = new User(new Posn(5, 20), Rotation.UP, false, new Fire(), 3);
        User left = new User(new Posn(0, 5), Rotation.UP, false, new Fire(), 3);
        User right = new User(new Posn(20, 5), Rotation.UP, false, new Fire(), 3);
        

        return t.checkExpect(user.onKeyEvent("up"),
                new User(new Posn(5, 4), Rotation.UP, false, new Fire(), 3))
                && t.checkExpect(user.onKeyEvent("down"),
                        new User(new Posn(5, 6), Rotation.UP, false, new Fire(), 3))
                && t.checkExpect(user.onKeyEvent("left"),
                        new User(new Posn(4, 5), Rotation.UP, false, new Fire(), 3))
                && t.checkExpect(user.onKeyEvent("right"),
                        new User(new Posn(6, 5), Rotation.UP, false, new Fire(), 3))
                && t.checkExpect(user.onKeyEvent(" "),
                        new User(new Posn(5, 5), Rotation.UP, true, fire.attack(user), 3))
                && t.checkExpect(user.onKeyEvent("w"),
                        new User(new Posn(5, 5), Rotation.UP, false, new Fire(), 3))
                && t.checkExpect(user.onKeyEvent("a"),
                        new User(new Posn(5, 5), Rotation.LEFT, false, new Fire(), 3))
                && t.checkExpect(user.onKeyEvent("s"),
                        new User(new Posn(5, 5), Rotation.DOWN, false, new Fire(), 3))
                && t.checkExpect(user.onKeyEvent("d"),
                        new User(new Posn(5, 5), Rotation.RIGHT, false, new Fire(), 3))
                && t.checkExpect(up.onKeyEvent("up"),
                        up)
                && t.checkExpect(down.onKeyEvent("down"),
                        down)
                && t.checkExpect(left.onKeyEvent("left"),
                        left)
                && t.checkExpect(right.onKeyEvent("right"),
                        right);
    }

    public boolean testUserMoveFire(Tester t) {
        User user = new User(new Posn(5, 5), Rotation.UP, true, fire.attack(this.user), 3);
        User up = new User(new Posn(5, 0), Rotation.UP,true, fire.attack(this.user), 3);
        User down = new User(new Posn(5, 20), Rotation.UP, true, fire.attack(this.user), 3);
        User left = new User(new Posn(0, 5), Rotation.UP, true, fire.attack(this.user), 3);
        User right = new User(new Posn(20, 5), Rotation.UP, true, fire.attack(this.user), 3);

        return t.checkExpect(user.onKeyEvent("up"),
                new User(new Posn(5, 4), Rotation.UP, true, fire.attack(user), 3))
                && t.checkExpect(user.onKeyEvent("down"),
                        new User(new Posn(5, 6), Rotation.UP, true, fire.attack(user), 3))
                && t.checkExpect(user.onKeyEvent("left"),
                        new User(new Posn(4, 5), Rotation.UP, true, fire.attack(user), 3))
                && t.checkExpect(user.onKeyEvent("right"),
                        new User(new Posn(6, 5), Rotation.UP, true, fire.attack(user), 3))
                && t.checkExpect(user.onKeyEvent(" "),
                        new User(new Posn(5,5), Rotation.UP, false, new Fire(), 3))
                && t.checkExpect(user.onKeyEvent("w"),
                        new User(new Posn(5,5), Rotation.UP, true, fire.attack(user),3))
                && t.checkExpect(user.onKeyEvent("a"),
                        new User(new Posn(5,5), Rotation.UP, true, fire.attack(user),3))
                && t.checkExpect(user.onKeyEvent("s"),
                        new User(new Posn(5,5), Rotation.UP, true, fire.attack(user),3))
                && t.checkExpect(user.onKeyEvent("d"),
                        new User(new Posn(5,5), Rotation.UP, true, fire.attack(user),3))
                && t.checkExpect(user.onKeyEvent("p"),
                        new User(new Posn(5,5), Rotation.UP, true, fire.attack(user),3))
                && t.checkExpect(up.onKeyEvent("up"),
                        up)
                && t.checkExpect(down.onKeyEvent("down"),
                        down)
                && t.checkExpect(left.onKeyEvent("left"),
                        left)
                && t.checkExpect(right.onKeyEvent("right"),
                        right);
    }
    
    public boolean testUserDeadAlive(Tester t) {
        User user = new User(new Posn(5, 5), Rotation.UP, false, new Fire(), 3);
        return t.checkExpect(user.isDeadHuh(),
                false);
    }
    
    public boolean testUserDeadDead(Tester t) {
        User user = new User(new Posn(5, 5), Rotation.UP, false, new Fire(), 0);
        return t.checkExpect(user.isDeadHuh(),
                true);
    }
    
    public boolean testUserSetPos(Tester t) {
        User user = new User(new Posn(5, 5), Rotation.UP, false, new Fire(), 3);
        return t.checkExpect(user.setPosn(10, 2),
                user.pos = new Posn(10,2));
    }
    
    public boolean testUserPAIN(Tester t) {
        User user = new User(new Posn(5, 5), Rotation.UP, false, new Fire(), 3);
        return t.checkExpect(user.loseHP(),
                new User(new Posn(5, 5), Rotation.UP, false, new Fire(), 2));
    }
    
    public boolean testMinionOnUser(Tester t) {
        User user = new User(new Posn(5, 5), Rotation.UP, false, new Fire(), 3);
        Minions baddie = new Minions(new Posn(5,5), true);
        Minions baddie2 = new Minions(new Posn(9,9), true);
        MT.add(baddie);
        LinkedList<Minions> baddieList = MT;
        FightWorld fw = new FightWorld(user,baddieList,bbInvinc,level1);
        
        return t.checkExpect(fw.onTick(),
                new FightWorld(user.loseHP(),new LinkedList<Minions>(), bbInvinc,level1))
                && t.checkExpect(baddie2.onUserHuh(user),
                        false);
    }
    
    public boolean testPause(Tester t) {
        return t.checkExpect(fwNN.onKeyEvent("p"),
                new PauseWorld(user, enemies, bbInvinc, level1));
    }
    
    public boolean testFight(Tester t) {
        Minions addme = new Minions(new Posn(6,6), true);
        Minions addme2 = new Minions(new Posn(8,8), false);
        LinkedList<Minions> temp = new LinkedList();
        temp.add(addme);
        temp.add(addme2);
        LinkedList<Minions> minus1 = temp;
        minus1.remove(0);
        
        return t.checkExpect(pw.onKeyEvent("h"),
                    new PauseWorld(new User(new Posn(5, 5), Rotation.UP, false, new Fire(), 4), enemies, bbInvinc,level1))
                &&  t.checkExpect(pw.onKeyEvent("`"),
                        new FightWorld(new User(new Posn(5, 5), Rotation.UP, false, new Fire(), 0), enemies, bbInvinc, level1))
                &&  t.checkExpect(pw.onKeyEvent("p"),
                        new FightWorld(user, enemies, bbInvinc, level1))
                &&  t.checkExpect(pw.onKeyEvent("down"),
                        pw)
                && t.checkExpect(pw.onKeyEvent("up"),
                        new PauseWorld(user, new LinkedList<Minions>(), bbInvinc, (level1+1)))
                
                //causing errors, not sure why it is returning an empty list, the function states it removes one enemy
                && t.checkExpect(pw.onKeyEvent(" "),
                        new PauseWorld(user, minus1, bbInvinc, level1));
        
        
    }
    
    public boolean testBossMove(Tester t) {
        User user = new User(new Posn(5, 5), Rotation.UP, false, new Fire(), 3);
        return t.checkExpect(bbNonvinc.move(user),
                new BigBoss( new Posn(20,9), false, 3));
        
    }
    
    public boolean testBosstele(Tester t) {
        return t.checkExpect(bbNonvinc.teleport(user, enemies).pos == bbNonvinc.pos,
                false);
    }
    
    public boolean testBossFire(Tester t) {
        User user = new User(new Posn(20, 9), Rotation.DOWN, true, fire.attack(this.user), 3);
        
        return t.checkExpect(bbNonvinc.onFireHuh(user),
                true)
            &&  t.checkExpect(bbInvinc.onFireHuh(user),
                false);
    }
    
    public boolean testMinionMove(Tester t) {
        User user = new User(new Posn(5, 5), Rotation.UP, false, new Fire(), 3);
        Minions baddie = new Minions(new Posn(6,6), true);
        
        return t.checkExpect(baddie.move(user).pos.x == 7,
                        false)
                && t.checkExpect(baddie.move(user).pos.y == 7,
                        false);
    }
    
    

    
    //Unsure why
    /*
    
    ERROR ON THIS AND I AM NOT SURE WHY? SAYS I AM LOSING HP, BUT I THINK IT IS BECAUSE 
    THE FIRE.ATTACK(USER) IS REFERENCING A USER THAT ISN'T ATTACKING, UNCLEAR THOUGH
    
    public boolean testMinionOnFire(Tester t) {
        User user = new User(new Posn(5, 5), Rotation.UP, true, fire.attack(this.user), 3);
        Minions baddie = new Minions(new Posn(5,4), true);
        MT.add(baddie);
        LinkedList<Minions> baddieList = MT;
        FightWorld fw = new FightWorld(user,baddieList,bbInvinc,level1);
        
        return t.checkExpect(fw.onTick(),
                new FightWorld(user,new LinkedList<Minions>(), bbInvinc,level1));
    }
    
    */
    
    //So random
    

//    //Can't test respawn since it is random locations
////    public boolean testMinionRespawn(Tester t) {
////        LinkedList<Minions> temp = fwNN.spawnMinions(user, bbInvinc, level1);
////        return t.checkExpect(fwEI,
////                new FightWorld(user,temp,bbNonvinc,level1));
////    }

//    public static void main(String[] args) {
//        Tester.runReport(new Testers(), false, false);
//    }
}
