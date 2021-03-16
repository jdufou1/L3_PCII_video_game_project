package pcii_project;

import pcii_project.controls.Controls;
import pcii_project.models.Model;
import pcii_project.models.TestModel;

public class TestModelMain {

	public static void main(String[] args) {
		Model model = new Model();
		
		/* Test model */
		TestModel vue = new TestModel(model);
		new Controls(vue,model);
		vue.show();
		
		//Controls controleur = new Controls(model,view);

	}
}
