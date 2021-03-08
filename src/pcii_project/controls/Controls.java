package pcii_project.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import pcii_project.models.Model;
import pcii_project.models.TestModel;
import pcii_project.view.CarModel;


/*
 * Controle : 
 * 
 * SPACE : Mise en pause du model
 * 
 * 
 * 
 * 
 * 
 * 
 * */


public class Controls  implements KeyListener {

	
	/* constantes */
	
	/* parametre par defaut : */
	
	public static final boolean PAUSE = false;
	
	
	
	/* attributs */
	
	private CarModel view;
	private Model model;
	
	
	private boolean pause;
	
	/* constructors */
	public Controls(CarModel view, Model model) {
		
		view.getWindows().addKeyListener(this);

		view.getWindows().addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){  
	          System.exit(0);  
			}
		});	
		
		
		this.model = model;
		this.view = view;
		
		pause = PAUSE;
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		int keyCode = evt.getKeyCode();
	    //char keyChar = evt.getKeyChar();
	    
	    try {
	    	/* MISE EN PAUSE DU MODEL */
	    	if(keyCode == KeyEvent.VK_SPACE) {
	    		if(pause) model.continue_progress();
	    		else model.stop_progress();
	    		
	    		pause =! pause;
	    	}
	    	
	    	if(keyCode == KeyEvent.VK_RIGHT) {
	    		model.getCars().move_right();
	    	}
	    	if(keyCode == KeyEvent.VK_LEFT) {
	    		model.getCars().move_left();
	    	}
	    	if(keyCode == KeyEvent.VK_UP) {
	    		model.getRoad().move();
	    	}
	    	
	    	
	    }
	    catch(Exception error) {
	    	System.out.println(error);
	    }
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}

/*
 * 
	
	


	public Controls(Model model,Display view) {
		this.model = model;
		this.view = view;
		view.getWindow().addKeyListener(this);

		view.getWindow().addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){  
	          System.exit(0);  
			}
		});	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			System.out.println("RIGHT");
			model.getCars().move_right();
			view.repaint();
		}
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			System.out.println("LEFT");
			model.getCars().move_left();
			view.repaint();
		}	
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
 * 
 * 
 */