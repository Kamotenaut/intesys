package window.game_engine.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import window.game_engine.Input.InputType;
import window.game_engine.controller.MainEngine;


public class SwingGameEngine extends JPanel implements Runnable, KeyListener, MouseListener{

	private static final long	serialVersionUID	= 1L;
		
		// Thread
		private Thread thread;
		
		// Timer
		private long deltaTime;
		private long variableYieldTime, lastTime;
		
		// Renderer
		private BufferedImage bufferedImage;
		private Graphics2D rendIn;
		
		// window variables
		private int width, height, fps;
		private boolean isRunning;
		
		// Engine
		private MainEngine engine;
		
		public SwingGameEngine(int width, int height, int fps){
			super();
			setWindowWidth(width);
			setWindowHeight(height);
			setFps(fps);
			setBufferedImage(BufferedImage.TYPE_INT_ARGB);
			setGraphics2D();
			setRunning(true);
			setPreferredSize(new Dimension(width, height));
		}
		
		public void addNotify(){
			super.addNotify();
			if(thread == null){
				thread = new Thread(this);
				thread.start();
			}
			addKeyListener(this);
			addMouseListener(this);
			setFocusable(true);
			requestFocus();
		}
		
		private void init(){
			engine = new MainEngine(getWindowWidth(), getWindowHeight());
			
			lastTime = System.nanoTime();
		}
		
		public void run(){
			init();
			do{
				input(); //Get Input from the user
				update(); // Game status
				render(); // Character Matrix
				fps(); // FPS

			}while(isRunning());
			System.exit(0);
		}


		private void input() {
			
		}
		
		private void update() {
			engine.update( deltaTime / 1000000000.0f );
		}
		
		private void render() {
			rendIn.setColor(Color.WHITE);
			rendIn.fillRect(0, 0, getWindowWidth(), getWindowHeight());
			
			engine.render(rendIn);
			
			Graphics rendOut = this.getGraphics();
			rendOut.drawImage(getBufferedImage(), 0, 0, null);
			rendOut.dispose();
		}
		
		private void fps(){
			deltaTime = System.nanoTime() - lastTime;
			if (getFps() <= 0){
				System.err.println("Invalid FPS!");
				setRunning(false);
				return;
			}
			
			long sleepTime = 1000000000 / getFps(); 
			long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000 * 1000));
			long overSleep = 0; 
		          
			try {
		    	while (true) {
		        	long t = System.nanoTime() - lastTime;
		                  
		        	if (t < sleepTime - yieldTime) {
		                    Thread.sleep(1);
		        	}else if (t < sleepTime) {
		                    Thread.yield();
		        	}else {
		            	overSleep = t - sleepTime;
		            	break; 
		         	}
		    	}
			} catch (InterruptedException e) {
		    	 e.printStackTrace();
			}finally{
		    	lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);
		    	if (overSleep > variableYieldTime) {
		        	variableYieldTime = Math.min(variableYieldTime + 200*1000, sleepTime);
		        } else if (overSleep < variableYieldTime - 200*1000) {
		        	variableYieldTime = Math.max(variableYieldTime - 2*1000, 0);
		        }
		    }
		}
		
		public int getFps(){ return fps; }
		public int getWindowWidth(){ return width; }
		public int getWindowHeight(){ return height; }
		public boolean isRunning(){ return isRunning; }
		
		public void setFps(int fps){ this.fps = fps;}
		public void setWindowWidth(int width){this.width = width;}
		public void setWindowHeight(int height){this.height = height;}
		public void setRunning(boolean isRunning){this.isRunning = isRunning;}
		
		public long getDeltaTime() {
			return deltaTime;
		}

		public void setDeltaTime(long deltaTime) {
			this.deltaTime = deltaTime;
		}

		public BufferedImage getBufferedImage() {
			return bufferedImage;
		}

		public void setBufferedImage(int TYPE) {
			bufferedImage = null;
			bufferedImage = new BufferedImage(getWindowWidth(), getWindowHeight(), TYPE);
		}

		public Graphics2D getRendIn() {
			return rendIn;
		}

		public void setGraphics2D() {
			if(getBufferedImage() != null)
				rendIn = (Graphics2D) getBufferedImage().getGraphics();
			else
				System.err.println("Cannot initialize Graphics 2D!");
		}

		// Input
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			setFocusable(true);
			requestFocus();
			if(engine != null)
				engine.input(InputType.MOUSE_CLICKED, arg0.getButton(), arg0.getX(), arg0.getY());
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			setFocusable(true);
			requestFocus();
			if(engine != null)
				engine.input(InputType.MOUSE_ENTERED, arg0.getButton(), arg0.getX(), arg0.getY());
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			if(engine != null)
				engine.input(InputType.MOUSE_EXITED, arg0.getButton(), arg0.getX(), arg0.getY());
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			if(engine != null)
				engine.input(InputType.MOUSE_PRESSED, arg0.getButton(), arg0.getX(), arg0.getY());
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			if(engine != null)
				engine.input(InputType.MOUSE_RELEASED, arg0.getButton(), arg0.getX(), arg0.getY());
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			if(engine != null)
				engine.input(InputType.KEY_PRESS, arg0.getKeyCode());
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			if(engine != null)
				engine.input(InputType.KEY_RELEASE, arg0.getKeyCode());
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			if(engine != null)
				engine.input(InputType.KEY_TYPED, arg0.getKeyCode());
		}
		

}
