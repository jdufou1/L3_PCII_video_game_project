package pcii_project;

import pcii_project.controls.Controls;
import pcii_project.models.Model;
import pcii_project.models.TestModel;
/*
 * Point d'entree du Test
 * Jeu developpee par Jeremy DUFOURMANTELLE et Yuan XU
 * Derniere mis a jour : 06/04/21
 * */
public class TestModelMain {
	/*
	 * Main 
	 * Lancer la version developpement du jeu 
	 * Aucune sprite n'est affichee et les elements graphiques sont representees
	 * */
	public static void main(String[] args) {
		/* Creation du modele du jeu*/
		Model model = new Model(); 
		/* Creation de la vue de test du jeu*/
		TestModel view = new TestModel(model);
		/* Ajout de controleurs clavier au jeu */
		new Controls(view,model);
		/* Affichage de l'interface graphique */
		view.show();
	}
}
