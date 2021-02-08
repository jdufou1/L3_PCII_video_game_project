package pcii_project;

import pcii_project.view.Window;
import pcii_project.controls.Controls;
import pcii_project.models.Model;
import pcii_project.view.Display;

public class Main {

	
	private Model model;
	private Display view;
	private Controls controls;
	
	public Main() {
		model = new Model();
		view = new Display ();
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
