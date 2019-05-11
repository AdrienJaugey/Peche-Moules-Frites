package pechemoulesfrites;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import pechemoulesfrites.Modele.Carte.Carte;
import static pechemoulesfrites.Modele.Enum.Enum_Action.Attendre;
import pechemoulesfrites.Modele.Personnage.IA.IA;
import pechemoulesfrites.Modele.Personnage.Personnage;

public class Partie {
    private static int compteur = 1;
    
    public Partie(){ }
    
    public IA[] lancerPartie(IA IA1,IA IA2,IA IA3,IA IA4){
        Gestionnaire.get().reset();
        IA[] tab = {IA1,IA2,IA3,IA4};
        creerCarte(tab);
        System.out.println("Lancement Partie "+ compteur++);
        //Déroulement de la partie
        do{
            for(Personnage p : Gestionnaire.get().getListePersonnage()){
                p.getIa().action();
            }
        }while(!finPartie());
        
        
        //Récupération des scores
        ArrayList<Personnage> liste = Gestionnaire.get().getListePersonnage();
        
        //Récupération du premier
        Personnage premier = liste.get(0);
        for(Personnage p : liste){
            if(p.getScore() > premier.getScore()) premier = p;          
        }
        liste.remove(premier);
        
        //Récupération du deuxième
        Personnage deuxieme = liste.get(0);
        for(Personnage p : liste){
            if(p.getScore()> deuxieme.getScore()) deuxieme = p;
        }
        liste.remove(deuxieme);
        
        //Récupération du troisième
        Personnage troisieme = liste.get(0);
        if(liste.get(1).getScore() > troisieme.getScore()) troisieme = liste.get(1);
        liste.remove(troisieme);
        
        //Récupération du dernier
        Personnage dernier = liste.get(0);
        
        IA[] res = {premier.getIa(), deuxieme.getIa(), troisieme.getIa(), dernier.getIa()};
        return res;
    }
    
    /**
     * Permet de tester si la partie est terminée ou non
     * @return 
     */
    private boolean finPartie(){
        boolean res = Gestionnaire.get().getListePersonnage().get(0).getLastAction() == Attendre;
        for(Personnage p : Gestionnaire.get().getListePersonnage()){
            res = res && (p.getLastAction() == Attendre);
        }
        return res;
    }
    
    private void creerCarte(IA[] tab){
        Carte test;
        try {
            Scanner scan = new Scanner(new File("carte.txt")); 
            int nbColonne=0;
            int nbLigne=0;
            while (scan.hasNext()) {
                if(!scan.hasNextInt()){
                        nbColonne=nbColonne+1;
                }
                    scan.next();
            }
            scan.close();
            Scanner scan2 = new Scanner(new File("carte.txt"));
            while (scan2.hasNextLine()) {
                nbLigne++;
                scan2.nextLine();
            }
            scan.close();
            nbColonne = nbColonne/nbLigne;
            test = new Carte(nbColonne,nbLigne);
            test.creerCartePredef(tab);
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Utilisation de la carte par défaut");
            test = new Carte(10,15);
            test.creerCarteDefaut(tab);
        }
    }
}
