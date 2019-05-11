package pechemoulesfrites;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import pechemoulesfrites.Modele.Personnage.IA.IA;
import pechemoulesfrites.Modele.Personnage.IA.IA_leatoire;


public class Tournoi {
    //private HashMap<Integer, IA> Participant;
    private ArrayList<IA> Participant;
    private int Generation; //Complètement inutile pour la suite 
    public Tournoi(){
        this.Participant = new ArrayList();
        this.Generation = 1;
    }
    
    /**
     * Permet de générer des IA aléatoire et de les ajouter à la liste répertoriant les participant du tournois
     * @param taille précise le nombre d'IA que l'on veut créer et ajouter
     */    
    public void Generateur(int taille){    
        for(int i = 0; i < taille; i++){
            IA ia = new IA_leatoire(Math.random()*5, Math.random()*5, Math.random()*5, Math.random()*5);
            ia.setID("GEN"+this.Generation+"_"+"IA"+i); //A chaque appel de la fonction Generateur on passe à la génération suivant *inutile pour la suite 
            Participant.add(ia); //Juste pour les test
            System.out.println(ia); // Juste pour les test
        }
        this.Generation ++;
    }
    
    /**
     * Permet de lancer le tournoi
     */
    public void faireTournoi(){        
        int a=0;    //Ici on initialise les 4 "emplacements" pour les IA afin de générer aléatoirement des match
        int b=0;
        int c=0;
        int d=0;
        String code=""; // Variable qui va contenir la combinaison d'IA donc d'un match
        for(IA i : Participant){ //Si on lance plusieur tournois il faut remettre le score des IA gardée à 0 sinon les nouvelles IA sont désavantagée
            i.setIAScore(0);
        }
        ArrayList MatchDone = new ArrayList<String>(); //Liste qui va stocker tous les matchs réalisé et permettre de ne pas les refaire
        for(int i=0;(i<=NbMatch()-1);i++){                      //Remplacer 2 par NbMatch() pour faire autant de match possible afin que toutes les IA se rencontre au moins une fois
            Random rnd = new Random();                  //A partir d'ici on génère aléatoirement des entiers correspondant a un indice d'une ia dans la liste des participants
            a = rnd.nextInt(Participant.size());        //a est le premier donc il choisit qui il veut
            b = rnd.nextInt(Participant.size());        //
            while(b==a){                                //
                   b = rnd.nextInt(Participant.size()); //Pour b,c,d il faut faire en sorte que chaucun soit différent de ses prédécesseurs
            }                                           //
            c = rnd.nextInt(Participant.size());        //  
            while(c==a||c==b){                          //
                   c = rnd.nextInt(Participant.size());
            }       
            d = rnd.nextInt(Participant.size());
            while(d==a||d==b||d==c){
                  d = rnd.nextInt(Participant.size());
            }
            code = (""+a+""+b+""+c+""+d);              //Une fois qu'on a une combinaison sans doublon, on affecte cette valeur dans code
            System.out.println(code); //Pour test
            
             
            
            if(MatchDone.contains(code)){              //Si le match a déjà été réalisé alors on regénère une nouvelle combinaison
                a = rnd.nextInt(Participant.size());
                b = rnd.nextInt(Participant.size());
                while(b==a){
                       b = rnd.nextInt(Participant.size());
                }
                c = rnd.nextInt(Participant.size());
                while(c==a||c==b){
                       c = rnd.nextInt(Participant.size());
                }
                d = rnd.nextInt(Participant.size());
                while(d==a||d==b||d==c){
                      d = rnd.nextInt(Participant.size());
                }
                
                code = (""+a+""+b+""+c+""+d);
                System.out.println(code);
                Match party = new Match(Participant.get(a),Participant.get(b),Participant.get(c),Participant.get(d)); //On crée un match avec les IA de la liste correspondant aux indices de la liste
                party.lancerMatch();
               
                
            }else{ //Si le match n'existe pas dans la liste alors on crée le match et on le lance
                
                Match party = new Match(Participant.get(a),Participant.get(b),Participant.get(c),Participant.get(d));
                MatchDone.add(code);
                party.lancerMatch();                
                
            }   
        }
        for(IA ai : Participant){                                   //POur les tests on affiche toutes les IA du tournois et leurs score total
            System.out.println(ai.getID()+" Score "+ai.getIAScore());
        }
        System.out.println("--------------------------Trie de la liste--------------------------------");//Juste de l'affichage pour les tests
        SortIAList();
        for(IA ai : Participant){
            System.out.println(ai.getID()+" Score "+ai.getIAScore());
        }
        System.out.println("FIN");
        
        
        
    }
    /**
     * Permet de trier la liste des participants en fonction de leur score total et ne garde que la moitié de la liste "Les meilleurs"
     */
    private void SortIAList(){
        boolean tab_en_ordre=false;
        int taille = Participant.size();
        while(!tab_en_ordre){
            tab_en_ordre = true;
            for(int i=0 ; i < taille-1 ; i++)
            {
                if(Participant.get(i).getIAScore() > Participant.get(i+1).getIAScore())
                {
                    Collections.swap(Participant, i,i+1);
                    tab_en_ordre = false;
                }
            }
            taille--;
        }
        Collections.reverse(Participant); // La liste est triée dans l'ordre croissant mais on veut un ordre décroissant
        for(int e=((this.Participant.size())/4); e<this.Participant.size(); e++) this.Participant.remove(e); // Après plusieurs test pour obtenir la moitié de la liste il faut diviser par 4 et pas par 2 pour des raisons que l'on a pas compris
    }
    
    /**
     * Permet de calculer le nombre de match possible afin que chaque IA puisse se rencontrer 1 fois dans un match 
     * @return le nombre de match afin que chaque IA puisse se rencontrer 1 fois dans un match 
     */
    private int NbMatch(){
        int res =0;
        res = (int) Math.pow(4,Participant.size());
        return res;
    }

}
