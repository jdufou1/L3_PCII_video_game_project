package pcii_project;

import pcii_project.controls.Controls;
import pcii_project.models.Model;
import pcii_project.view.CarModel;

public class Main {
	
	public static void main(String[] args) {
		Model model = new Model();
		
		/* Test model */
		CarModel carview = new CarModel(model);
		new Controls(carview,model);
		carview.show();
		
		//Controls controleur = new Controls(model,view);

	}

}
