package pcii_project.view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


public class Display extends JPanel{
	
	private Window window;
	
	public static final int HAUT_VOITURE = 40;
	public static final int LARGE_VOITURE = 20;
	public static final int Y_VOITURE = 300;
	
	/*variable*/
	public int x_voiture = window.LARGE_FENETRE/2;
	
	/*constructeur*/
    public Display()  {

    	
    	
    	window = new Window();
        window.carRoute.add(this);
        
    	this.setPreferredSize(new Dimension(600,450));
    }
	
	
	
	public void paint(Graphics g) {
    	super.paint(g);
    	
    	//voiture
    	g.fillRect(x_voiture+LARGE_VOITURE/2, Y_VOITURE+HAUT_VOITURE/2, HAUT_VOITURE, LARGE_VOITURE);
    	g.fillRect(x_voiture+HAUT_VOITURE/2, Y_VOITURE+LARGE_VOITURE/2, LARGE_VOITURE, HAUT_VOITURE );
    	
    	//parcours
    	g.drawLine(300,0, 300, 450);
    	
    }

}
