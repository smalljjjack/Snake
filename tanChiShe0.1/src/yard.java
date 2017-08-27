import java.awt.*;
import java.awt.event.*;


public class yard extends Frame {
	private boolean GAMEOVER = false;
	
	public static final int ROWS = 50;
	public static final int CLOS = 50;
	public static final int BLOCKW = 30;
	private boolean pause = false;
	
	private  sThread SThread =new sThread();
	
	private int score = 0;
	//set the start snake
	snake s = new snake(this);
	
	egg e = new egg();
	
	Image offScreenImage = null;
	
	public yard(String str) {
		super(str);
	}

	public static void main (String args[]){
		new yard("tanchishe").launch();
	}
	
	public void launch(){
		this.setBounds(300, 0, CLOS * BLOCKW, ROWS * BLOCKW);
		this.setVisible(true);
		this.setFont(new Font("Jack", 20, 50));
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.addKeyListener(new sKeyMonitor());
			new Thread(SThread).start();
	}
	
	public void paint(Graphics g){
		
		Color c = g.getColor();
		g.setColor(Color.gray);
		g.fillRect(0, 0, CLOS * BLOCKW, ROWS * BLOCKW);
		g.setColor(Color.darkGray);
		//draw rows
		for(int i = 1; i < ROWS; i++){
			g.drawLine(0, BLOCKW * i, CLOS * BLOCKW, BLOCKW * i);
		}
		//draw clos
		for(int i = 1; i < CLOS; i++){
			g.drawLine(BLOCKW * i, 0, BLOCKW * i, ROWS * BLOCKW);
		}
		g.setColor(Color.YELLOW);
		g.drawString("Score = "+score, 100, 100);
		
		g.setColor(Color.RED);
		if(GAMEOVER == true) {
			g.setFont(new Font("jack", 10, 80 ));
			g.drawString("Game Over", CLOS * BLOCKW/2, ROWS * BLOCKW/2);
			SThread.gameOver();
		}
		
		g.setColor(c);
		s.eat(e);
		s.draw(g);
		e.draw(g);
		
		g.setColor(c);
	}
	
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(CLOS * BLOCKW, ROWS * BLOCKW);
		}
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0,  null);
	}
	

	private class sThread implements Runnable{
		
		private boolean running = true;
		private boolean pause = false;
		public void run() {
			while(running){
				if (pause == true) {
					continue;
				}else{
					repaint();
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void gameOver(){
			running = false;
		}

		public void pause() {
			this.pause = true;
		}
		
		public void reStart() {
			this.pause = false;
			s = new snake(yard.this);
			GAMEOVER = false;
		}
	}	
		
	private class sKeyMonitor extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_F2){
				SThread.reStart();
			}
			s.keyPressed(e);
		}
 	}

	public void stop() {
		System.out.println("Your score is = "+ score);
		GAMEOVER = true;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int i) {
		score = i;
	}

}
