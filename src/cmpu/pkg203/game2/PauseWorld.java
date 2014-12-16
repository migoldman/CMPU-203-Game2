/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmpu.pkg203.game2;

import static cmpu.pkg203.game2.FightWorld.SCREENWIDTH;
import java.util.LinkedList;
import javalib.colors.*;
import javalib.funworld.World;
import javalib.worldimages.*;

/**
 *
 * @author michaelgoldman
 */
public class PauseWorld extends World {
    
    User user;
    LinkedList<Minions> enemies;
    BigBoss boss;
    int level;
    int SCREENWIDTH = 600;
    int SCREENHEIGHT = 600;
    int SIZE = 30;
        
    PauseWorld(User user, LinkedList<Minions> enemies, BigBoss boss, int level) {
        this.user = user;
        this.enemies = enemies;
        this.boss = boss;
        this.level = level;
    }

    public World onKeyEvent(String ke) {
        if(ke.equals("p")) {
            return new FightWorld(user, enemies, boss, level);
        }
        
        else if(ke.equals("r")) {
            return new FightWorld();
        }
        
        else if(ke.equals("h")) {
            System.out.println("HP increased to " + (user.HP+1));
            user.HP++;
        }
        
        else if(ke.equals("up")) {            
            boss.HP++;
            level++;
            enemies = new LinkedList();
            System.out.println("Level increased to " + level);
            System.out.println("Boss HP is " + boss.HP + " and " + (level+2) + 
                    " minions are waiting");

        }
        
        else if(ke.equals("down")) {
            if(level == 1) {
                System.out.println("Can't go lower than level 1");
            }
            else {
                boss.HP--;
                enemies = new LinkedList();
                level--;
                System.out.println("Level decreased to " + level);
                System.out.println("Boss HP is " + boss.HP + " and " + (level +2) + 
                        " minions are waiting");
                
            }
        }
        
        else if(ke.equals(" ")) {
            if(enemies.size() == 0) {
                System.out.println("No enemies to remove!");
            }
            else {
                System.out.println("360-noscoped a minion! Now only " + (enemies.size()-1) + " left");
                enemies.remove(0);
            }
        }
        else if(ke.equals("`")) {
            user.HP=0;
            return new FightWorld(user, enemies, boss, level);
        }
        return this;
    }
    
    public WorldImage makeImage() {
        return new OverlayImages(
                new RectangleImage(new Posn(SCREENWIDTH / 2, SCREENHEIGHT / 2),
                    SCREENWIDTH, SCREENHEIGHT, new Yellow()), 
                new OverlayImages(
                    new TextImage(new Posn(SCREENWIDTH / 2, SCREENHEIGHT-SIZE*2), 
                        ("HP: " + user.HP + "  X: " + user.pos.x + "  Y: " + user.pos.y + "  Max minions: " + (level+2) + "  Boss HP: " + boss.HP + "  Level: " + level),
                        12, new Black()), 
                    new TextImage(new Posn(SCREENWIDTH /2, SCREENHEIGHT/2), 
                            "PAUSED", 
                            32, new Black())));
    }
    
    public World onTick() {
        return this;
    }
}
