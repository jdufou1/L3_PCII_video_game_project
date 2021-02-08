package pcii_project.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import pcii_project.models.Horizon;
import pcii_project.models.Model;


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
    JSplitPane jSplitPane1 =new JSplitPane();//Fen�tre divis�e
    
    
    
    /*constructeur*/
    public Window()
    {
      	this.setTitle("jeu du voiture");
    	this.setBounds(100, 100, Model.WIDTH_MAX, Model.HEIGHT_MAX);
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init()
    {
        jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);//Diviser de haut en bas
        jSplitPane.setDividerSize(0);//D�finir la largeur de la ligne de s�paration
        
        jSplitPane.setLeftComponent(horizen);//ajouter panel horizen
        jSplitPane.setRightComponent(carRoute);//ajouter panel carRoute

        jSplitPane.setDividerLocation(Horizon.HEIGHT_HORIZON);//D�finir la position de la ligne de s�paration
        setContentPane(jSplitPane);

        /*border color
        horizen.setBorder(BorderFactory.createLineBorder(Color.blue));
        carRoute.setBorder(BorderFactory.createLineBorder(Color.green));
        */
        horizen.setBackground(new java.awt.Color(135,206,235));
        
    }
    
}
