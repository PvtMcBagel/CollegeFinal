import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Torch extends GameObject {
	
	private Handler handler;
	private BufferedImage[] torch_image = new BufferedImage[3];
	Animation torch_anim;

	public Torch(int x, int y, ID id, SpriteSheet Sprites, boolean crateused) {
		super(x, y, id, Sprites, false);
		this.objectstatus = crateused;
		
		torch_image[0] = Sprites.grabImage(14, 13, 32, 32);
		torch_image[1] = Sprites.grabImage(15, 13, 32, 32);
		torch_image[2] = Sprites.grabImage(15, 13, 32, 32);
		
		torch_anim = new Animation(3, torch_image[0], torch_image[1], torch_image[2]);
		
	}

	public void tick() {
		torch_anim.runAnimation();
	}
	
	public void render(Graphics g) {
		torch_anim.drawAnimation(g, x, y, 0);
	}
	public Rectangle getBounds() {
		return new Rectangle(x+6, y, 20, 24);
	}

}
