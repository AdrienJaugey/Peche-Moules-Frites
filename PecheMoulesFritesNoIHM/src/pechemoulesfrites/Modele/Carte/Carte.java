package pechemoulesfrites.Modele.Carte;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;
import pechemoulesfrites.Gestionnaire;
import pechemoulesfrites.Modele.Enum.Enum_Action;
import static pechemoulesfrites.Modele.Enum.Enum_Action.*;
import static pechemoulesfrites.Modele.Enum.Enum_Case.*;
import static pechemoulesfrites.Modele.Enum.Enum_Objet.*;
import pechemoulesfrites.Modele.Objet.Frites;
import pechemoulesfrites.Modele.Objet.Moules;
import pechemoulesfrites.Modele.Objet.Objet;
import pechemoulesfrites.Modele.Personnage.IA.IA;
import pechemoulesfrites.Modele.Personnage.Personnage;

/**
 *
 * @author ajaug
 */
public class Carte {
    private final Case[][] cases;

    /**
     * Constructeur de Carte 
     * @param largeur la largeur de la carte sous forme de int
     * @param hauteur la hauteur de la carte sous forme de int
     */
    public Carte(int largeur, int hauteur){
        this.cases = new Case[largeur][hauteur];
    }
    
    /**
     * Permet de déplacer un personnage si ce dernier peut le faire
     * @param lePersonnage le Personnage à déplacer
     * @param action l'action à réaliser de type Enum_Action
     */
    public void actionPerso(Personnage lePersonnage, Enum_Action action){
        boolean bouge = true;
        Enum_Action actionFinale = action;
        boolean check = false;
        //On récupère les coordonnées du personnage
        int x = lePersonnage.getColonne(),y = lePersonnage.getLigne();
        int xCheck = x, yCheck = y;
        
        //Suivant l'action à réaliser, on modifie la coordonnée nécessaire
        switch(action){
            case AllerHaut : {
                y--;  
                break;
            }
            case AllerBas : {
                y++;
                break;
            }
            case AllerGauche : {
                x--;
                break;
            }
            case AllerDroite : {
                x++;
                break;
            }
            //pour sauter un mur il faut effectuer un déplacement de 2 case
            case SauterHaut : {
                y -= 2;
                yCheck--;
                check = true;
                break;
            }
            case SauterBas : {
                y += 2;
                yCheck++;
                check = true;
                break;
            }
            case SauterGauche : {
                x -= 2;
                xCheck--;
                check = true;
                break;
            }
            case SauterDroite : {
                x += 2;
                xCheck++;
                check = true;
                break;
            }
            case Ramasser : {
                bouge = false;
                Objet objet = lePersonnage.getCase().getObjet(); // On récupère l'objet sur lequel se trouve le personnage 
                if(objet != null){
                    if((objet.getType() == Frites && !lePersonnage.hasFrites())||(objet.getType() == Moules)){ //  
                        lePersonnage.gerer(objet);              //On appelle la fonction gerer() pour augmenter le score du Personnage si c'est une moules, ou rendre le Personnage capable de sauter un mûr
                        lePersonnage.getCase().removeObjet();  //On retire l'objet de la case 
                        Gestionnaire.get().removeObjet(objet);   //On informe le Gestionnaire que l'objet ramassé n'existe pas 
                    } else {
                        actionFinale = Attendre;
                    }
                } else {
                    actionFinale = Attendre;
                }
                break;
            }
            default : {
                actionFinale = Attendre;
                bouge = false;
            }
        }
        
        //Si le personnage effectue un déplacement
        if (bouge) {
            //On vérifie si ce dernier n'est pas en dehors de la carte
            if (checkColonne(x) && checkLigne(y)){
                //On vérifie si la case visée n'est pas un Mur
                if (getCase(x,y).getType() != Mur) {
                    //On met à jour les cases concernées et le personnage
                    if(check){
                        if(checkColonne(xCheck) && checkLigne(yCheck) && lePersonnage.hasFrites()){
                            if(getCase(xCheck, yCheck).getType() == Mur){
                                movePerso(lePersonnage, getCase(x,y));
                                lePersonnage.saute();
                            }
                        } else {
                            actionFinale = Attendre;
                            System.err.println("Saut impossible");
                        }
                    } else {
                        movePerso(lePersonnage,getCase(x,y));
                    }
                } else {
                    actionFinale = Attendre;
                    System.err.println("Mur présent\n");
                }
            } else {
                actionFinale = Attendre;
                System.err.println("Sortie de la carte\n");
            }
        }
        lePersonnage.setLastAction(actionFinale);
    }
    /**
     * Permet de gerer le déplacement d'un personnage
     * @param lePersonnage le Personnage qu'on veut déplacer, de type Personnage
     * @param laCase la case sur laquelle on veut que le personnage se déplace, de type Case
     */
    private void movePerso(Personnage lePersonnage, Case laCase){
        lePersonnage.getCase().removePerso(lePersonnage); //On supprime le Personnage de la case où il se trouve acutellement 
        laCase.addPerso(lePersonnage); // On ajoute le Personnage sur la case entrée en paramètre de la Fonction
        lePersonnage.setCase(laCase); // Le Personnage prend connaissance de la nouvelle case où il se situe 
    }
    /**
     * Permet de savoir si la coordonnée rentrée en paramètre ne dépasse pas la taille de la carte
     * @param colonne coordonnée à tester de type int
     * @return le résultat d'un test sous la forme d'un booléen
     */
    public boolean checkColonne(int colonne){
        return (colonne >= 0) && (colonne < getLargeur());
    }
    /**
     * Permet de savoir si la coordonnée rentrée en paramètre ne dépasse pas la taille de la carte
     * @param ligne coordonnée à tester, de type int
     * @return le résultat d'un test sous la forme d'un booléen
     */
    public boolean checkLigne(int ligne){
        return (ligne >= 0) && (ligne < getHauteur());
    }
    
    /**
     * Permet d'obtenir la largeur de la carte
     * @return un entier, la largeur de la case 
     */
    public int getLargeur() {
        return cases.length;
    }
    
    /**
     * Permet d'obtenir la hauteur de la Carte
     * @return la hauteur de la Carte sous forme d'int
     */
    public int getHauteur() {
        return cases[0].length;
    }
    
    /**
     * Permet d'obtenir une Case selon ses coordonnées
     * @param colonne la colonne de la Case
     * @param ligne la ligne de la Case
     * @return la Case si elle existe, sinon null
     */
    public Case getCase(int colonne, int ligne){
        Case res = null;
        //On teste si les coordonnées sont correctes
        if (colonne >= 0 && colonne < getLargeur() && ligne >= 0 && ligne < getHauteur()){
            return cases[colonne][ligne];
        }
        return res;
    }
    
    /**
     * Permet de créer une carte dans le cas où un document texte générerait une carte non conforme
     * @param IAs les IA à donner
     */
    public void creerCarteDefaut(IA[] IAs){
        try {
            lireCarte(true, IAs);
        } catch (Exception ex) {
            System.err.println("Fichier de carte par défaut manquant !\nArrêt du programme");
        }
    }
    
    /**
     * Permet à partir d'un document .txt de générer une carte
     * @param IAs les IA à donner
     */
    public void creerCartePredef(IA[] IAs){
        try {
            lireCarte(false, IAs);
        } catch (Exception ex) { //Erreur envoyée s'il y a une erreur dans le fichier texte
            System.err.println(ex.getMessage());
            System.err.println("Utilisation de la carte par défaut");
            Carte defaut = new Carte(24,20); //création de la carte par défaut
            defaut.creerCarteDefaut(IAs);
        }        
    }
    
    /**
     * Permet de lire la carte contenue dans fichier et de donner les IA voulues
     * @param fichier le chemin vers le fichier voulu
     * @param IAs les IA à donner
     * @throws Exception le fichier est manquant ou incorrect
     */
    private void lireCarte(boolean defaut,IA[] IAs) throws Exception{
        String s;
        int ia = 0;
        Scanner scan;
        if(defaut)
            scan = new Scanner(this.getClass().getResourceAsStream("../../Ressources/defaut.txt")); //On instancie un scanner avec le fichier texte contenant la carte
        else 
            scan = new Scanner(new File("carte.txt"));
        Pattern autre = Pattern.compile("[PF*#]");//On créé des patterns pour la détection des charactères du fichier texte
        Pattern moule = Pattern.compile("M");
        for(int ligne = 0;ligne<getHauteur();ligne++){ //On parcours toutes les ligne du fichier
            for(int colonne = 0;colonne < getLargeur(); colonne++){ //On parcours toutes les colonnes du fichier                    
                if(scan.hasNext(moule)){ //On teste si c'est quelque chose qui correspond au pattern moule
                    scan.next(moule);  //Le scanner passe au charactere suivant du fichier                 
                    int val = scan.nextInt(); //Le scanner revoit les chiffres qui suivent le charactere M dans le fichier
                    cases[colonne][ligne] = new Case(colonne,ligne,Sol); //Création d'une case sol aux bonnes coordonnées
                    ajouterMoules(val,colonne,ligne); //ajout du nombre de moules sur la case
                } else if(scan.hasNext(autre)){ //Sinon on teste si ce qui suit correspond au pattern autre
                    s = scan.next(autre); //Le scanner passe au charactere suivant du fichier et le stock dans la variable s
                    char c = s.charAt(0); //Stockage du premier membre de la chaine de charactere s dans char c
                    if(c == 'P'){ //On teste si c'est un personnage
                        cases[colonne][ligne] = new Case(colonne,ligne,Sol); //Création d'une case sol aux bonnes coordonnées
                        if(ia < 4){
                            ajouterPersonnage(IAs[ia%4],colonne,ligne);//ajout d'un personnage avec son IA à la case
                            ia++; //changement de la valeur de l'IA pour le prochain personnage
                        } else {
                            System.err.println("Nombre de personnages limité à 4");
                        }
                    }
                    if(c == 'F'){ //On teste si c'est une frite
                        cases[colonne][ligne] = new Case(colonne,ligne,Sol); //Création d'une case sol aux bonnes coordonnées
                        ajouterFrites(colonne,ligne); //ajout d'une frite à la case
                    }
                    if(c == '#'){ //On teste si c'est un mur
                        cases[colonne][ligne] = new Case(colonne,ligne,Mur); //Création d'une case mur aux bonnes coordonnées
                    }
                    if(c == '*'){ //On teste si c'est une case sol sans rien
                        cases[colonne][ligne] = new Case(colonne,ligne,Sol); //Création d'une case sol aux bonnes coordonnées
                    }
                } else {
                    throw new Exception("Erreur de création de carte depuis le fichier texte");  //détection d'une erreur dans le fichier texte
                }
            }
        }
        scan.close();  //arrêt du scanner
        Gestionnaire.addCarte(this); //ajout de la carte au gestionnaire
    }
    
    /**
     * Permet d'ajouter/créer une moule avec une valuer et des coordonées entrées dans les paramètres
     * @param valeur définie la valeur de points que rapporte la Moules
     * @param x correspond à l'absice de la case ou sera placer la moule
     * @param y correspond à l'ordonée de la case ou sera placer la moule
     */
    public void ajouterMoules(int valeur, int x, int y){
        Case laCase = cases[x][y];                   // On recupère la case aux coordonnées x et y
        Objet moule = new Moules(valeur, laCase);   //On instancie une nouvelle Moules
        Gestionnaire.get().addObjet(moule);        //On ajoute ce nouvel objet à la liste des objets du Gestionnaire
        laCase.setObjet(moule);                   //La case sait qu'elle a contient une Moules
    }
    /**
     * Permet d'ajouter/créer une frite à des coordonées entrées dans les paramètres 
     * @param x correspond à l'abcisse de la case ou sera placer la frite
     * @param y correspond à l'ordonée de la case ou sera placer la frite
     */
    public void ajouterFrites(int x, int y){
        Case laCase = cases[x][y];              // On recupère la case aux coordonnées x et y
        Objet frite = new Frites(laCase);      // On instancie une nouvelle Frites
        Gestionnaire.get().addObjet(frite);   // On ajoute ce nouvel objet à la liste des objets du Gestionnaire
        laCase.setObjet(frite);              //La case sait qu'elle contient une Frites
    }
    /**
     * Permet d'ajouter/créer une frite à des coordonées entrées dans les paramètres 
     * @param ia correspond à l'IA que l'on souhaite attribuer au personnage qui va être ajouter
     * @param x correspond à l'abcisse de la case ou sera placer le nouveau personnage
     * @param y correspond à l'ordonnée de la case ou sera placer le nouveau personnage
     */
    public void ajouterPersonnage(IA ia, int x, int y){
        Case laCase = cases[x][y];                      // on recupère la case aux coordonnées x et y
        Personnage perso = new Personnage(ia, laCase); //On instancie un nouveau Personnage  
        Gestionnaire.get().addPersonnage(perso);      //On ajoute ce nouvel objet à la liste des personnages du Gestionnaire
        laCase.addPerso(perso);                      //La case sait qu'elle a un personnage sur elle 
    }
    
    
    @Override
    public String toString() {
        String res = "";
        for(int y = 0;y<getHauteur();y++){
            for(int x = 0;x < getLargeur(); x++){
                res += cases[x][y].toString() + " ";
            }
            res += "\n";
        }
        return res;
    }
    
    
}
