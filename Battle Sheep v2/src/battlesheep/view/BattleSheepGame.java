package battlesheep.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import battlesheep.view.scene.PlayGameScene;
import battlesheep.view.scene.SetupBoardScene;
import battlesheep.view.scene.SetupPlayerScene;
import jGame.JGame;
import jGame.model.game.GameSceneManager;

public class BattleSheepGame {

	private JGame game;
	
	public BattleSheepGame(String title, int width, int height, int fps){
		GameSceneManager tempSceneMan = new GameSceneManager();
		// add scenes
		tempSceneMan.addScene(new SetupBoardScene("SETUP_BOARD"));
		tempSceneMan.addScene(new SetupPlayerScene("SETUP_PLAYER"));
		tempSceneMan.addScene(new PlayGameScene("PLAY_GAME"));
		
		
		game = new JGame(title, width, height, fps, tempSceneMan);
	}
	
	public void start(){
		JFrame window = new JFrame(game.getTitle());
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());
		window.setSize(new Dimension(game.getWidth(), game.getHeight()));
        JPanel container = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setVgap(0);
        layout.setHgap(0);
        container.setLayout(layout);
        container.add(game);
        
        window.add(container);
		window.pack();
        window.setVisible(true);
	}
	
}
