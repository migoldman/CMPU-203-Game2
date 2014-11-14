/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmpu.pkg203.game2;

import java.util.LinkedList;

/**
 *
 * @author michaelgoldman
 */
public class FightWorld extends PauseWorld {
    User user;
    LinkedList<Minions> enemies;
    BigBoss boss;
    int level;
    
    FightWorld(User user, LinkedList<Minions> enemies, BigBoss boss, int level) {
        this.user = user;
        this.enemies = enemies;
        this.boss = boss;
        this.level = level;
    }
}
