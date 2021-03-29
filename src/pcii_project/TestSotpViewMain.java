package pcii_project;

import pcii_project.models.Model;
import pcii_project.view.StopView;

public class TestSotpViewMain {
	public static void main(String[] args) {
		Model model = new Model();
		StopView frame = new StopView(model);
		frame.setVisible(true);

	}

}
