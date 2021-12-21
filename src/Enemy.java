import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject{
	
	private Handler handler;
	Random r = new Random();
	Random r2d2 = new Random();
	int choose = 0;
	int hp = 100;
	
	private BufferedImage[] enemy_image_down = new BufferedImage[3];
	private BufferedImage[] enemy_image_left = new BufferedImage[3];
	private BufferedImage[] enemy_image_right = new BufferedImage[3];
	private BufferedImage[] enemy_image_up = new BufferedImage[3];
	Animation anim_down, anim_up, anim_left, anim_right;
	
	public Enemy(int x, int y, ID id, Handler handler, SpriteSheet Sprites) {
		super(x, y, id, Sprites, false);
		this.handler = handler;
		
		enemy_image_down[0] = Sprites.grabImage(7, 9, 32, 32);
		enemy_image_down[1] = Sprites.grabImage(8, 9, 32, 32);
		enemy_image_down[2] = Sprites.grabImage(9, 9, 32, 32);
		
		enemy_image_up[0] = Sprites.grabImage(7, 12, 32, 32);
		enemy_image_up[1] = Sprites.grabImage(8, 12, 32, 32);
		enemy_image_up[2] = Sprites.grabImage(9, 12, 32, 32);
		
		enemy_image_left[0] = Sprites.grabImage(7, 10, 32, 32);
		enemy_image_left[1] = Sprites.grabImage(8, 10, 32, 32);
		enemy_image_left[2] = Sprites.grabImage(9, 10, 32, 32);
		
		enemy_image_right[0] = Sprites.grabImage(7, 11, 32, 32);
		enemy_image_right[1] = Sprites.grabImage(8, 11, 32, 32);
		enemy_image_right[2] = Sprites.grabImage(9, 11, 32, 32);
		
		
		anim_down = new Animation(3, enemy_image_down[0], enemy_image_down[1], enemy_image_down[2]);
		anim_up = new Animation(3, enemy_image_up[0], enemy_image_up[1], enemy_image_up[2]);
		anim_left = new Animation(3, enemy_image_left[0], enemy_image_left[1], enemy_image_left[2]);
		anim_right = new Animation(3, enemy_image_right[0], enemy_image_right[1], enemy_image_right[2]);
		
	}

	public void tick() {
		x += velX;
		y += velY;
		
		choose = r.nextInt(5);
		if(choose == 0) {
			velX = (r.nextInt(2 - -2) + -2);
			velY = (r.nextInt(2 - -2) + -2);
		}
		

		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block) {
				if(getBoundsBig().intersects(tempObject.getBounds())) {
					x += (velX*2) * -1;
					y += (velY*2) * -1;
					velX *= -1;
					velY *= -1;
				}
					if(choose == 0) {
						velX = (r.nextInt(2 - -2) + -2);
						velY = (r.nextInt(2 - -2) + -2);
					
					}
				}
			if(tempObject.getId() == ID.HazardLite) {
				if(getBoundsBig().intersects(tempObject.getBounds())) {
					x += (velX*2) * -1;
					y += (velY*2) * -1;
					velX *= -1;
					velY *= -1;
				}
					if(choose == 0) {
						velX = (r.nextInt(2 - -2) + -2);
						velY = (r.nextInt(2 - -2) + -2);
					
					}
				}
			
			
			if(tempObject.getId() == ID.Bullet) {
				if(getBounds().intersects(tempObject.getBounds())) {
				hp -= 50;
				
				handler.removeObject(tempObject);
					}
				}
			}
		
		anim_down.runAnimation();
		anim_left.runAnimation();
		anim_up.runAnimation();
		anim_right.runAnimation();
		if(hp <= 0) handler.removeObject(this);
		}
		
		
		
	

	public void render(Graphics g) {		
		if(velX <0)
			anim_left.drawAnimation(g, x, y, 0);
		if(velX >0)
			anim_right.drawAnimation(g, x, y, 0);
		if(velY >0)
			anim_down.drawAnimation(g, x, y, 0);
		if(velY <0)
			anim_up.drawAnimation(g, x, y, 0);
		if(velY ==0 && velX == 0)
			g.drawImage(enemy_image_down[0],x ,y, null);
		g.setColor(Color.GRAY);
		g.fillRect(x, y, 32, 5);
		g.setColor(Color.red);
		g.fillRect(x, y, hp/3, 5);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 28, 28);
	}
	
	public Rectangle getBoundsBig() {
		return new Rectangle(x, y, 32, 32);
	}

}
