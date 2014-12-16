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
import java.util.Random;

public class Util {
    Random random = new Random();
    
    public int randomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}
