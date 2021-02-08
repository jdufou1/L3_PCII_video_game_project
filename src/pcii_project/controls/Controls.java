package pcii_project.controls;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import pcii_project.models.Model;
import pcii_project.view.Display;
import pcii_project.view.Window;



public class Controls  implements KeyListener {

	/**
	 * @param args
	 */
	
	private Display view;
	private Model model;


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

}