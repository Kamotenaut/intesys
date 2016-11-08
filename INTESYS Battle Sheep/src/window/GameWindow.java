package window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import window.game_engine.view.SwingGameEngine;


public class GameWindow {

	private JPanel animatedPanel;
	private String titleBar;
	
	public GameWindow(String titleBar, int w, int h, int fps) {
		animatedPanel = new SwingGameEngine(w, h, fps);
		setTitleBar(titleBar);
	}
	
	public void start(){
		
		JFrame window = new JFrame(titleBar);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		window.setContentPane(animatedPanel);
		window.pack();
		window.setVisible(true);
		*/
        //this.setSize(100, 75);
        window.setLayout(new BorderLayout());
        JPanel container = new JPanel();
        window.setSize(new Dimension(animatedPanel.getWidth(),
        		animatedPanel.getHeight()));
        FlowLayout layout = new FlowLayout();
        layout.setVgap(0);
        layout.setHgap(0);
        container.setLayout(layout);
        container.add(animatedPanel);

        window.add(container);
        
		window.pack();
        window.setVisible(true);
        //animatedPanel.setFocusable(true);
        //animatedPanel.setRequestFocusEnabled(true);
        //animatedPanel.requestFocus();

	}

	public String getTitleBar() {
		return titleBar;
	}

	public void setTitleBar(String titleBar) {
		this.titleBar = titleBar;
	}
}
