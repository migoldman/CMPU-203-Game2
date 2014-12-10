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
    
    Fire() {}
    
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
    
    //Unsure if this is correct position for method. If the user is firing, 
    //would I do something (in fightworld) like 
        
    public Fire attack(User user) {        
        switch(user.rotation ) {
            case UP: 
                return new Fire(x,y-1,Rotation.UP,this.upgrade,this.color);
            case DOWN:
                return new Fire(x,y+1,Rotation.DOWN,this.upgrade,this.color);
            case LEFT:
                return new Fire(x-1,y,Rotation.LEFT,this.upgrade,this.color);
            default:
                return new Fire(x+1,y,Rotation.RIGHT,this.upgrade,this.color);
        }
    }
    
    //width of the flame based on rotation (including upgrade)
    public int width() {
        return 1;
    }
    
    //height of the falme based on rotation (including upgrade)
    public int height() {
        return 1;
    }
   
}




