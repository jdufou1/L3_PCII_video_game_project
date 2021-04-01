package pcii_project;

import pcii_project.controls.Controls;
import pcii_project.models.Model;
import pcii_project.view.MainView;

public class Main {
	
	public static void main(String[] args) {
		Model model = new Model();

		MainView carview = new MainView(model);
		new Controls(carview,model);
		carview.show();

	}

}
