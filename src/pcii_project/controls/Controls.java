package pcii_project.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import pcii_project.models.Acceleration;
import pcii_project.models.Model;
import pcii_project.models.Road;
import pcii_project.models.TestModel;
import pcii_project.models.data.DataGame;
import pcii_project.view.CarModel;
import pcii_project.view.ContinueView;
import pcii_project.view.StopView;


/*
 * Controle : 
 * 
 * SPACE : Mise en pause du model
 * 
 * */


public class Controls  implements KeyListener {

	
	/* constantes */
	
	/* parametre par defaut : */
	
	
	/* attributs */
	
	//private CarModel view;
	private Model model;
	private ThreadControls threadControls;
	
	
	
	
	
	public Controls(CarModel view, Model model) {
		
		view.getWindows().addKeyListener(this);

		view.getWindows().addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){  
	          System.exit(0);  
			}
		});	
		
		this.threadControls = new ThreadControls(model);
		this.model = model;
		//this.view = view;
		
		new Thread(threadControls).start();
	}
	
	/* test */
	public Controls(TestModel view, Model model) {
		
		view.getWindows().addKeyListener(this);

		view.getWindows().addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){  
	          System.exit(0);  
			}
		});	
		
		this.threadControls = new ThreadControls(model);
		this.model = model;
		//this.view = view;
		
		new Thread(threadControls).start();
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		int keyCode = evt.getKeyCode();
	    try {
	    	/* MISE EN PAUSE DU MODEL */
	    	if(keyCode == KeyEvent.VK_SPACE) {
	    		threadControls.set_space();
	    		ContinueView frame = new ContinueView(model);
				frame.setVisible(true);
	    	}	
	    	if(keyCode == KeyEvent.VK_RIGHT) {
	    		threadControls.set_right(true);
	    	}
	    	if(keyCode == KeyEvent.VK_LEFT) {
	    		threadControls.set_left(true);
	    	}
	    	if(keyCode == KeyEvent.VK_UP) {
	    		threadControls.set_up(true);
	    	}
	    	if(keyCode == KeyEvent.VK_A) {
	    	}
	    	if(keyCode == KeyEvent.VK_2) {
	    		//model.getRoad().getAcceleration().lvl2_activation();
	    	}
	    }
	    catch(Exception error) {
	    	System.out.println(error);
	    }
		
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		int keyCode = evt.getKeyCode();
	    try {
	    	if(keyCode == KeyEvent.VK_RIGHT) {
	    		threadControls.set_right(false);
	    	}
	    	if(keyCode == KeyEvent.VK_LEFT) {
	    		threadControls.set_left(false);
	    	}
	    	if(keyCode == KeyEvent.VK_UP) {
	    		threadControls.set_up(false);
	    	}    	
	    }
	    catch(Exception error) {
	    	System.out.println(error);
	    
	    }
		
	}
	
	

}

class ThreadControls extends Thread{
	
	
	private Model model;
	
	private boolean up = false;
	
	private boolean right = false;
	
	private boolean left = false;
	
	private boolean space = false;
	
	private boolean pause = false;
	
	public ThreadControls(Model model) {
		this.model = model;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep(10);
				if(!pause) {
					
					if(right) {
			    		model.getCars().move_right();
			    	}
			    	if(left) {
			    		model.getCars().move_left();
			    	}
			    	if(up) {
			    		model.getRoad().move();
			    		model.getRoad().getAcceleration().start_acceleration();
			    	}
			    	if(!up) {
			    		model.getRoad().getAcceleration().end_acceleration();
			    	}
				}
		    	if(space) {
		    		if(pause) model.continue_progress();
		    		else model.stop_progress();
		    		
		    		pause = !pause;
		    		space = !space;
		    	}
				
				
			}
		}
		catch(Exception e) {	
			e.printStackTrace();
		}
	}
	
	public void set_up(boolean up) {
		this.up = up;
	}
	
	public void set_right(boolean right) {
		this.right = right;
	}
	
	public void set_left(boolean left) {
		this.left = left;
	}
	
	public void set_space() {
		this.space =! space;
	}
	
	
}
