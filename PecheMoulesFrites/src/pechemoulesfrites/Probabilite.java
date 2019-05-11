package pechemoulesfrites;

import java.util.Random;

/**
 *
 * @author md155007
 */
public class Probabilite {

    public static boolean chance(int chance){
        Random r = new Random();
        return (r.nextInt(101) < chance);
    }
    public static int aleatoire(){
        Random r = new Random();
        return r.nextInt(101);
    }
    
}
