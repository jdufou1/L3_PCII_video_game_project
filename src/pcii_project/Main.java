package pcii_project;

import pcii_project.controls.Controls;
import pcii_project.models.Model;
import pcii_project.view.MainView;


/*
 * Point d'entree
 * Jeu developpee par Jeremy DUFOURMANTELLE et Yuan XU
 * Derniere mis a jour : 06/04/21
 * */
public class Main {
	/*
	 * Main 
	 * Lancer le jeu
	 * */
	public static void main(String[] args) {
		/* Creation du modele du jeu*/
		Model model = new Model(); 
		/* Creation de la vue du jeu*/
		MainView view = new MainView(model); 
		/* Ajout de controleurs clavier au jeu */
		new Controls(view,model);
		/* Affichage de l'interface graphique */
		view.show();
	}
}
