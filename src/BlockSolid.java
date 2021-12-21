import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class BlockSolid extends GameObject {
	
	private BufferedImage block_image;
	public BlockSolid(int x, int y, ID id, SpriteSheet Sprites) {
		super(x, y, id, Sprites, false);
		
		block_image = Sprites.grabImage(9, 8, 32, 32);
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		g.drawImage(block_image, x, y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

}
