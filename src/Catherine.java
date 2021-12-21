import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Catherine extends GameObject  {
	
	
	Handler handler;
	Game game;
	
	private BufferedImage[] player_image_down = new BufferedImage[3];
	private BufferedImage[] player_image_left = new BufferedImage[3];
	private BufferedImage[] player_image_right = new BufferedImage[3];
	private BufferedImage[] player_image_up = new BufferedImage[3];
	
	Animation anim_down, anim_up, anim_left, anim_right;

	public Catherine(int x, int y, ID id, Handler handler, Game game, SpriteSheet Sprites) {
		super(x, y, id, Sprites, false);
		this.handler = handler;
		this.game = game;
		
		player_image_down[0] = Sprites.grabImage(4, 9, 32, 32);
		player_image_down[1] = Sprites.grabImage(5, 9, 32, 32);
		player_image_down[2] = Sprites.grabImage(6, 9, 32, 32);
		
		player_image_up[0] = Sprites.grabImage(4, 12, 32, 32);
		player_image_up[1] = Sprites.grabImage(5, 12, 32, 32);
		player_image_up[2] = Sprites.grabImage(6, 12, 32, 32);
		
		player_image_left[0] = Sprites.grabImage(4, 10, 32, 32);
		player_image_left[1] = Sprites.grabImage(5, 10, 32, 32);
		player_image_left[2] = Sprites.grabImage(6, 10, 32, 32);
		
		player_image_right[0] = Sprites.grabImage(4, 11, 32, 32);
		player_image_right[1] = Sprites.grabImage(5, 11, 32, 32);
		player_image_right[2] = Sprites.grabImage(6, 11, 32, 32);
		
		
		anim_down = new Animation(3, player_image_down[0], player_image_down[1], player_image_down[2]);
		anim_up = new Animation(3, player_image_up[0], player_image_up[1], player_image_up[2]);
		anim_left = new Animation(3, player_image_left[0], player_image_left[1], player_image_left[2]);
		anim_right = new Animation(3, player_image_right[0], player_image_right[1], player_image_right[2]);
		
				
	}

	public void tick() {
		x += velX;
		y += velY;
		
		collision();
		
		//here be the movemnt controls for our lord and savior Gaben.
		if(handler.isUp()) velY = -2;
		else if(!handler.isDown()) velY = 0;
		
		if(handler.isDown()) velY = 2;
		else if(!handler.isUp()) velY = 0;
		
		if(handler.isRight()) velX = 2;
		else if(!handler.isLeft()) velX = 0;
		
		if(handler.isLeft()) velX = -2;
		else if(!handler.isRight()) velX = 0;
		
		anim_down.runAnimation();
		anim_left.runAnimation();
		anim_up.runAnimation();
		anim_right.runAnimation();
		
		
	}
	
	private void collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			if(getBounds().intersects(tempObject.getBounds())) {
				tempObject.objectstatus = true;
			}
			if(tempObject.getId() == ID.Block) {
				if(getBounds().intersects(tempObject.getBounds())) {
					x += velX * -1;
					y += velY * -1;
				}
				
			}
			
			if(tempObject.getId() == ID.Crate) {
				if(getBounds().intersects(tempObject.getBounds())) {
						game.ammo += 10;
						handler.removeObject(tempObject);
				}
				
			}
			
			if(tempObject.getId() == ID.Enemy) {
				if(getBounds().intersects(tempObject.getBounds())) {
					game.hp--;
					
				}
				
			}
			if(tempObject.getId() == ID.HazardLite) {
				if(getBounds().intersects(tempObject.getBounds())) {
					game.hp--;
					x += (velX*2) * -1;
					y += (velY*2) * -1;
					velX *= -1;
					velY *= -1;
					
				}
				
			}
		}
	}

	public void render(Graphics g)  {
		if(velX <0)
			anim_left.drawAnimation(g, x, y, 0);
		if(velX >0)
			anim_right.drawAnimation(g, x, y, 0);
		if(velY >0)
			anim_down.drawAnimation(g, x, y, 0);
		if(velY <0)
			anim_up.drawAnimation(g, x, y, 0);
		if(velY ==0 && velX == 0)
			g.drawImage(player_image_down[0],x ,y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle(x+6, y, 20, 32);
	}

}
