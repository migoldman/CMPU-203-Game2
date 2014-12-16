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
public class PauseWorld extends FightWorld {
        
    PauseWorld(User user, LinkedList<Minions> enemies, BigBoss boss, int level) {
        super();
        this.user = user;
        this.enemies = enemies;
        this.boss = boss;
        this.level = level;
    }

    public World onKeyEvent(String ke) {
        if("p".equals(ke)) {
            return new FightWorld(user, enemies, boss, level);
        }
        else if("r".equals(ke)) {
            return makeWorld();
        }
        return this;
    }
    
    public WorldImage makeImage() {
        return new OverlayImages(new TextImage(new Posn(SCREENWIDTH/2, SCREENHEIGHT/2), "PAUSED", 30, new Black()), 
                new RectangleImage(new Posn(SCREENWIDTH / 2, SCREENHEIGHT / 2),
                SCREENWIDTH, SCREENHEIGHT, new Yellow()));    
    }
    
    public WorldEnd worldEnds() {
        return null;
    }
    
    public World onTick() {
        return this;
    }
}
