import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Crate extends GameObject {
	
	private Handler handler;
	static boolean crateused;
	private BufferedImage crate_image;

	public Crate(int x, int y, ID id, SpriteSheet Sprites, boolean crateused) {
		super(x, y, id, Sprites, false);
		this.objectstatus = crateused;
		
		crate_image = Sprites.grabImage(10, 10, 32, 32);
		
	}

	public void tick() {

	}
	
	public void render(Graphics g) {
		g.drawImage(crate_image, x, y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

}
