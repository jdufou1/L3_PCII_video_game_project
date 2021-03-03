package pcii_project;

import pcii_project.controls.Controls;
import pcii_project.models.Model;
import pcii_project.models.TestModel;
import pcii_project.view.Display;

public class Main {
	
	public static void main(String[] args) {
		Model model = new Model();
		
		/* Test model */
		TestModel testview = new TestModel(model);
		Controls controls = new Controls(testview,model);
		testview.show();
		
		//Controls controleur = new Controls(model,view);

	}

}
