package pcii_project;

import pcii_project.controls.Controls;
import pcii_project.models.Model;
import pcii_project.view.Display;

public class Main {
	
	public static void main(String[] args) {
		Model model = new Model();
		Display view = new Display(model);
		Controls controleur = new Controls(model,view);
		
	}

}
