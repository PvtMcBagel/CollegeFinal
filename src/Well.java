import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Well extends GameObject {
	
	private Handler handler;
	private BufferedImage well_image;

	public Well(int x, int y, ID id, SpriteSheet Sprites, boolean crateused) {
		super(x, y, id, Sprites, false);
		this.objectstatus = crateused;
		
		well_image = Sprites.grabImage(11, 9, 32, 32);
		
	}

	public void tick() {
	}
	
	public void render(Graphics g) {
		g.drawImage(well_image, x, y, null);
	}
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

}
