package pechemoulesfrites.Modele.Personnage.IA;

import java.util.Random;
import pechemoulesfrites.Modele.Enum.Enum_IA;
import pechemoulesfrites.Modele.Personnage.Personnage;

public class Fabrique_IA {
    
    public static IA cree(Enum_IA ia, Personnage perso){
        IA res = null;
        Random rand = new Random();
        switch(ia){
            case IA_leatoire : {
                res = new IA_leatoire(perso, rand.nextDouble()*5, rand.nextDouble()*5, rand.nextDouble()*5, rand.nextDouble()*5);
                break;
            }
            case IA_ffamee : {
                res = new IA_ffamee(perso);
                break;
            }
            case IA_pprentie : {
                res = new IA_pprentie(perso);
                break;
            }
            default : {
                res = new IA_pprentie(perso);
                break;
            }
        }
        return res;
    }
}
