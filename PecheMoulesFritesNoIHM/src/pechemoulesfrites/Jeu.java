package pechemoulesfrites;

public class Jeu {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tournoi Ranking = new Tournoi();
        Ranking.Generateur(4);
        Ranking.faireTournoi();
        //Ranking.Generateur(8);
        //Ranking.faireMatch();
        
        
        /*IA IA1 = new IA_leatoire(Math.random()*5, Math.random()*5, Math.random()*5, 0);
        System.out.println(IA1);
        IA IA2 = new IA_leatoire(Math.random()*5, Math.random()*5, Math.random()*5, 0);
        System.out.println(IA2);
        IA IA3 = new IA_leatoire(Math.random()*5, Math.random()*5, Math.random()*5, 0);
        System.out.println(IA3);
        IA IA4 = new IA_leatoire(Math.random()*5, Math.random()*5, Math.random()*5, 0);
        System.out.println(IA4);
        
        Match leMatch = new Match(IA1, IA2, IA3, IA4);
        IA[] res = leMatch.lancerMatch();

        for(IA ia : res){
            String nom;
            if(ia == IA1) nom = "IA1";
            else if(ia == IA2) nom = "IA2";
            else if(ia == IA3) nom = "IA3";
            else nom = "IA4";
            System.out.println(nom);
            System.out.println(ia.getIAScore());
        }*/
        
        
    }
}
