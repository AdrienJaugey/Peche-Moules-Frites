package pechemoulesfrites;

import java.util.ArrayList;
import java.util.HashMap;
import pechemoulesfrites.Modele.Personnage.IA.IA;


public class Match {
    
    private final HashMap<IA, Integer> result;
    private final IA IA1;
    private final IA IA2;
    private final IA IA3;
    private final IA IA4;
    
    private static int compteur = 1;
    
    public Match(IA IA1, IA IA2, IA IA3, IA IA4){
        result = new HashMap<>();
        this.IA1 = IA1;
        this.IA2 = IA2;
        this.IA3 = IA3;
        this.IA4 = IA4;
        result.put(this.IA1, 0);
        result.put(this.IA2, 0);
        result.put(this.IA3, 0);
        result.put(this.IA4, 0);
    }
    
    public IA[] lancerMatch(){
        System.out.println("Lancement Match " + compteur++);
        lancerPartie(IA1,IA2,IA3,IA4);
        lancerPartie(IA1,IA3,IA2,IA4);
        lancerPartie(IA1,IA3,IA4,IA2);
        lancerPartie(IA1,IA2,IA4,IA3);
        lancerPartie(IA1,IA4,IA3,IA2);
        lancerPartie(IA1,IA4,IA2,IA3);
        
        lancerPartie(IA2,IA1,IA3,IA4);
        lancerPartie(IA2,IA3,IA1,IA4);
        lancerPartie(IA2,IA3,IA4,IA1);        
        lancerPartie(IA2,IA1,IA4,IA3);        
        lancerPartie(IA2,IA4,IA3,IA1);
        lancerPartie(IA2,IA4,IA1,IA3);
        
        lancerPartie(IA3,IA1,IA2,IA4);
        lancerPartie(IA3,IA1,IA4,IA2);
        lancerPartie(IA3,IA2,IA1,IA4);
        lancerPartie(IA3,IA2,IA4,IA1);
        lancerPartie(IA3,IA4,IA2,IA1);
        lancerPartie(IA3,IA4,IA1,IA2);
        
        lancerPartie(IA4,IA2,IA1,IA3);
        lancerPartie(IA4,IA2,IA3,IA1);
        lancerPartie(IA4,IA1,IA3,IA2);
        lancerPartie(IA4,IA1,IA2,IA3);
        lancerPartie(IA4,IA3,IA2,IA1);
        lancerPartie(IA4,IA3,IA1,IA2);

        return getResult();
    }
    
    private IA[] getResult(){
        ArrayList<IA> score = new ArrayList<>();
        score.add(IA1);
        score.add(IA2);
        score.add(IA3);
        score.add(IA4);
        score.sort((a, b) -> result.get(b).compareTo(result.get(a)));
        IA[] res = {score.get(0), score.get(1), score.get(2), score.get(3)};
        return res;
    }
    
     private void lancerPartie(IA IA1, IA IA2, IA IA3, IA IA4){
        Partie laPartie = new Partie();
        IA[] resultat = laPartie.lancerPartie(IA1, IA2, IA3, IA4);
        addScore(resultat[0],2);
        resultat[0].setIAScore( (resultat[0].getIAScore())+2);
        addScore(resultat[1],1);
        resultat[1].setIAScore( (resultat[1].getIAScore())+1);
        addScore(resultat[2],-1);
        resultat[2].setIAScore( (resultat[2].getIAScore())-1);
        addScore(resultat[3],-2);
        resultat[3].setIAScore( (resultat[3].getIAScore())-2);
        
    }
    
    
    private void addScore(IA ia, int ajout){
        int score = result.get(ia) + ajout;
        result.replace(ia, score);
    }
}
