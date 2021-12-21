import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends Canvas implements Runnable {
	
	//I don't know what this does it just generated it to get rid of a warning.
	private static final long serialVersionUID = 1L;
	
	private boolean isRunning = false;
	private Thread thread;
	private Handler handler;
	private Camera camera;
	private SpriteSheet Sprites;
	private SpriteSheet Sprites2;

	private BufferedImage level = null;
	private BufferedImage background = null;
	private BufferedImage sprite_sheet = null;
	private BufferedImage floor = null;
	private BufferedImage sprite_sheet2 = null;
	
	public int ammo = 5;
	public int hp = 100;
	public int score = 0;
	public int timer = 0;
	public boolean choice = false;
	public int choicedec = 1;
	public String name;
	public int result;
	
	
	public Game() {
		new Window(1280, 720, "Dungeon Crawler Prototype", this);
		start();
		
		handler = new Handler();
		camera = new Camera(0, 0);
		this.addKeyListener(new KeyInput(handler));
		
		
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("GameLevel.png");
		background = loader.loadImage("background.png");
		sprite_sheet = loader.loadImage("spritesheet.png");
		//sprite_sheet2 = loader.loadImage("spritesheet2.gif");
		
		Sprites = new SpriteSheet(sprite_sheet);
		//Sprites2 = new SpriteSheet(sprite_sheet2);
		
		floor = Sprites.grabImage(9, 7, 32, 32);
		
		this.addMouseListener(new MouseInput(handler, camera, this, Sprites));
		loadLevel(level);
	}
	//start and stop the thread
	private void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				//updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
				//updates = 0;
			}
		}
		stop();
	}
	
	public void tick() {
		if(choice == false) {
			JFrame frame = new JFrame("Dungeon Crawler Prototype");
			name = JOptionPane.showInputDialog(frame, "What's your name?");
			choice = true;
		}
		if(hp == 0) {
			WriteCsv.saveRecord(name, score, "highscore.txt");
			hp=100;
			stop();
			loadLevel(level);
			
		}
		for(int i = 0; i < handler.object.size(); i++) {
			if(handler.object.get(i).getId() == ID.Player) {
				camera.tick(handler.object.get(i));
			}
		}
		
		handler.tick();
		if(hp <= 0) {
			hp = 0;
		}
		
		if(ammo==0) {
			timer++;
			if(timer== 10) {
				ammo++;
			}
			
			
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		//--------------------------------------------------------------
	
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 1280, 720);
		g.drawImage(background, 0, 0, null);
		
		g2d.translate(-camera.getX(), -camera.getY());
		
		for(int xx = 0; xx < (32*39); xx+=32){
			for(int yy = 0; yy < (32*29); yy+=32) {
				g.drawImage(floor, xx, yy, null);
			}
		}
		handler.render(g);
		
		g2d.translate(camera.getX(), camera.getY());
		
		g.setColor(Color.GRAY);
		g.fillRect(5, 5, 200, 32);
		g.setColor(Color.GREEN);
		g.fillRect(5, 5, hp*2, 32);
		g.setColor(Color.BLACK);
		g.drawRect(5, 5, 200, 32);
		
		g.setColor(Color.WHITE);
		g.drawString("Ammo =" + ammo, 5, 50);
		g.setColor(Color.WHITE);
		g.drawString("Score =" + score, 5, 60);
		//--------------------------------------------------------------
		g.dispose();
		bs.show();
		}
	//this loads the level .png file.
	private void loadLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		
		for(int xx = 0; xx < w; xx++) {
			for(int yy = 0; yy < h; yy++) {
				int pixel = image.getRGB(xx,yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(green == 0 && blue == 0 && red == 255)
					handler.addObject(new Block(xx*32, yy*32, ID.Block, Sprites));
				
				if(green == 50 && blue == 50 && red == 255)
					handler.addObject(new BlockSolid(xx*32, yy*32, ID.Block, Sprites));
				
				if(green == 200 && blue == 100 && red == 255)
					handler.addObject(new BlockTall2(xx*32, yy*32, ID.Block, Sprites));
				
				if(green == 100 && blue == 200 && red == 255)
					handler.addObject(new BlockTall(xx*32, yy*32, ID.Block, Sprites));
				
				if(green == 150 && blue == 150 && red == 255)
					handler.addObject(new Block2(xx*32, yy*32, ID.Block, Sprites));
				
				if(green == 0 && blue == 255 && red == 0)
					handler.addObject(new Gaben(xx*32, yy*32, ID.Player, handler, this, Sprites));
				
				if(green == 255 && blue == 0 && red == 0)
					handler.addObject(new Enemy(xx*32, yy*32, ID.Enemy, handler, Sprites));
				
				if(green == 200 && blue == 0 && red == 0)
					handler.addObject(new enemy2(xx*32, yy*32, ID.Enemy, handler, Sprites));
				
				if(green == 255 && blue == 255 && red == 0)
					handler.addObject(new Crate(xx*32, yy*32, ID.Crate, Sprites, false));
				
				if(green == 0 && blue == 0 && red == 125)
					handler.addObject(new Torch(xx*32, yy*32, ID.HazardLite, Sprites, false));
				
				if(green == 200 && blue == 200 && red == 200)
					handler.addObject(new Well(xx*32, yy*32, ID.Block, Sprites, false));
				
				if(green == 137 && blue == 137 && red == 0)
					handler.addObject(new Jewels(xx*32, yy*32, ID.ScoreLarge, Sprites, false));
				
				if(green == 0 && blue == 137 && red == 137)
					handler.addObject(new GoldLarge(xx*32, yy*32, ID.ScoreMedium, Sprites, false));
				
				if(green == 137 && blue == 0 && red == 137)
					handler.addObject(new GoldSmall(xx*32, yy*32, ID.ScoreSmall, Sprites, false));
			}
			
		}
		
	}
	
	
	public static void main(String args[]) {
		new Game();
	}

}
