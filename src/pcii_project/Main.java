package pcii_project;

import pcii_project.view.*;
import pcii_project.controls.Controls;
import pcii_project.models.Model;

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
