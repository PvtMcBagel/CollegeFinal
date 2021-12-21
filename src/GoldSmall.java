import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class GoldSmall extends GameObject {
	
	private Handler handler;
	private BufferedImage jewel_image;

	public GoldSmall(int x, int y, ID id, SpriteSheet Sprites, boolean crateused) {
		super(x, y, id, Sprites, false);
		
		jewel_image = Sprites.grabImage(12, 2, 32, 32);
		
	}

	public void tick() {
	}
	
	public void render(Graphics g) {
		g.drawImage(jewel_image, x, y, null);
	}
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

}
