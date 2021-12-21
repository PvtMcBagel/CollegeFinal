import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
	
	private Handler handler;
	private Camera camera;
	private Game game;
	private SpriteSheet Sprites;
	
	public MouseInput(Handler handler, Camera camera, Game game, SpriteSheet Sprites) {
		this.handler = handler;
		this.camera = camera;
		this.game = game;
		this.Sprites = Sprites;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = (int) (e.getX() + camera.getX());
		int my = (int) (e.getY() + camera.getY());
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player && game.ammo >= 1) {
				handler.addObject(new Bullet(tempObject.getX()+8, tempObject.getY()+8, ID.Bullet, handler, mx, my, Sprites));
				game.ammo--;
				game.score--;
			}
		}
	}

}
 