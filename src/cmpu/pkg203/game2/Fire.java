/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmpu.pkg203.game2;

/**
 *
 * @author michaelgoldman
 */
import javalib.colors.Red;

enum Rotation {
        UP, DOWN, LEFT, RIGHT;
    }

enum Colors {
    Black, Blue, Green, Red, White, Yellow;
}

public class Fire {
    int x, y;
    Rotation rotation;
    boolean upgrade;
    Colors color;
    
    Fire(int x, int y, Rotation rotation, boolean upgrade, Colors color) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.color = color;
    }
    
    Fire(int x, int y, Rotation rotation, boolean upgrade) {
        this.x = x;
        this.y = y;
        this.rotation = rotation; 
        this.upgrade = upgrade;
        this.color = Colors.Red;
    }
    
    Fire(int x, int y, Rotation rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation; 
        this.upgrade = false;
        this.color = Colors.Red;
    }
    
    //Might want to make it so you are always attacking, unsure atm
    public Fire attack() {
        Fire attack;            
        if(rotation == Rotation.UP) {
            attack = new Fire(x,y-1,Rotation.UP,this.upgrade,this.color);
        }
        else if(rotation == Rotation.DOWN) {
            attack = new Fire(x,y+1,Rotation.DOWN,this.upgrade,this.color);
        }
        else if(rotation == Rotation.LEFT) {
            attack = new Fire(x-1,y,Rotation.LEFT,this.upgrade,this.color);
        }
        else {
            attack = new Fire(x+1,y,Rotation.RIGHT,this.upgrade,this.color);
        }
        return attack;
    }
    
    public int width() {
        if(upgrade) {
            if(rotation == Rotation.UP || rotation == Rotation.DOWN) {
                return 1;
            }
            else {
                return 3;
            }
        }
        else {
            return 1;
        }
    }
    
    public int height() {
        if(upgrade) {
            if(rotation == Rotation.UP || rotation == Rotation.DOWN) {
                return 3;
            }
            else {
                return 1;
            }
        }
        else {
            return 1;
        }
    }
}




