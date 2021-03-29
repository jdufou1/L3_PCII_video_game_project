package pcii_project.view;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pcii_project.controls.Controls;
import pcii_project.models.Model;

public class StopView extends JFrame {
	//private JPanel contentPane;



	/**
	 * Create the frame.
	 */
	public StopView() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Login");
		setBounds(100,100,550,400);
		FlowLayout f1=new FlowLayout(FlowLayout.LEFT);
		setLayout(f1);
		
		/*content panel
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);*/

		//image gameover
		ImageIcon imgGamoverForChange = new ImageIcon("img/gameover.png");
		ImageIcon imgGamover=change(imgGamoverForChange,0.15);//diminuer l'image
		JLabel jlGamover=new JLabel(imgGamover);
		jlGamover.setLocation(50,50);
		add(jlGamover);
		
		//Label score
		JLabel jlScore=new JLabel("Your score is : ");
		add(jlScore);
		jlScore.setLocation(300,300);
		
		//button quit
		JButton btnQuit = new JButton("OK");
		btnQuit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		btnQuit.setBounds(47, 74, 112, 67);

		add(btnQuit);

	

	}
	
	public ImageIcon change(ImageIcon image,double i){//  i 为放缩的倍数
		int width=(int) (image.getIconWidth()*i);
		int height=(int) (image.getIconHeight()*i);
		Image img=image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);//第三个值可以去查api是图片转化的方式
		ImageIcon image2=new ImageIcon(img);
	 
		return image2;
	}
}