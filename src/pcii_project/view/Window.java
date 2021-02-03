package pcii_project.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;


public class Window extends JFrame{
	/*constante*/
	public static final int HAUT_FENETRE = 400;
	public static final int LARGE_FENETRE = 600;
	public static final int HAUT_VOITURE = 40;
	public static final int LARGE_VOITURE = 20;
	public static final int Y_VOITURE = 300;
	
	
	/*variable*/
	public int x_voiture = LARGE_FENETRE/2;
	JSplitPane jSplitPane =new JSplitPane();
    JPanel horizen = new JPanel();
    JPanel carRoute =new JPanel();
    JSplitPane jSplitPane1 =new JSplitPane();//Fenêtre divisée
    
    
    
    /*constructeur*/
    public Window()
    {
    	this.setTitle("jeu du voiture");
    	this.setBounds(100, 100, 600, 600);
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init()
    {
        jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);//Diviser de haut en bas
        jSplitPane.setDividerSize(0);//Définir la largeur de la ligne de séparation
        
        jSplitPane.setLeftComponent(horizen);//ajouter panel horizen
        jSplitPane.setRightComponent(carRoute);//ajouter panel carRoute

        jSplitPane.setDividerLocation(150);//Définir la position de la ligne de séparation
        setContentPane(jSplitPane);

        /*border color
        horizen.setBorder(BorderFactory.createLineBorder(Color.blue));
        carRoute.setBorder(BorderFactory.createLineBorder(Color.green));
        */
        horizen.setBackground(new java.awt.Color(135,206,235));
        
    }
    
}
