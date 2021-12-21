import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Bullet extends GameObject {
	
	Handler handler;

	public Bullet(int x, int y, ID id, Handler handler, int mX, int mY, SpriteSheet Sprites) {
		super(x, y, id, Sprites, false);
		this.handler = handler;
		
		velX = (mX - x) / 10;
		velY = (mY - y) / 10;
	}
	public void tick() {
		x += velX;
		y += velY;
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block) {
				if(getBounds().intersects(tempObject.getBounds())){
					handler.removeObject(this);
					//handler.addObject(new Bullet(x, y, ID.Bullet, handler, velX, velY));
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, 4, 4);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, 4, 4);
		
	}
	public Rectangle getBounds() {
		return new Rectangle(x, y, 4, 4);
	}

}
