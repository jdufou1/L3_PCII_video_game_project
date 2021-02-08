package pcii_project.view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import pcii_project.models.Cars;
import pcii_project.models.Horizon;
import pcii_project.models.Model;


public class Display extends JPanel{
	
	private Window window;
	
	public static final int HAUT_VOITURE = 40;
	public static final int LARGE_VOITURE = 40;
	public static final int Y_VOITURE = 300;
	
	/*variable*/
	public int x_voiture = window.LARGE_FENETRE/2;
	
	private Cars car;
	
	
	
	/*constructeur*/
    public Display(Cars c)  {
	
    	window = new Window();
        window.carRoute.add(this);
        
        this.car = c;
        
    	this.setPreferredSize(new Dimension(Model.WIDTH_MAX,Model.HEIGHT_MAX-Horizon.HEIGHT_HORIZON));
    	this.setBackground(java.awt.Color.green);
    	
    }
	
	
	
	public void paint(Graphics g) {
    	super.paint(g);
    	
    	//voiture
    	g.fillRect(car.getPlacement_x(), Y_VOITURE+HAUT_VOITURE/2, HAUT_VOITURE, LARGE_VOITURE);
    	//g.fillRect(x_voiture+HAUT_VOITURE/2, Y_VOITURE+LARGE_VOITURE/2, LARGE_VOITURE, HAUT_VOITURE );
    	
    	//parcours
    	g.drawLine(400,0, 400, 450);
    	
    }

}
