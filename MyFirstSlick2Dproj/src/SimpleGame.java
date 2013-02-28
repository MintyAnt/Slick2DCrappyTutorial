import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

//----------------------------------------------------------------------------//
public class SimpleGame extends BasicGame
{
	// Members //
	Image mLand = null;
	Player mPlayer;
	Input mLastInput = null;
	
	// Methods//
	//----------------------------------------------------------------------------//
	public SimpleGame()
	{
		super("My First Slick2D Gaem!");
	}

	//----------------------------------------------------------------------------//
	@Override
	public void init(GameContainer inContainer) throws SlickException 
	{
		final float PLAYER_SCALE = 1.0f;
		final float PLAYER_SPEED = 0.4f;
		final float PLAYER_ROTATIONAL_SPEED = 0.2f;
		final float PLAYER_SCALE_SPEED = 0.1f;
		final float PLAYER_FIRE_COOLDOWN = 0.1f;
		mPlayer = new Player(PLAYER_SCALE, PLAYER_SPEED, PLAYER_ROTATIONAL_SPEED, PLAYER_SCALE_SPEED, PLAYER_FIRE_COOLDOWN);
		
		mLand = new Image("data/land.jpg");
		mPlayer.Initialize("data/plane.png", "data/Laser.png");
	}
	
	//----------------------------------------------------------------------------//
	@Override
	public void render(GameContainer inContainer, Graphics inGraphics) throws SlickException 
	{
		mLand.draw(0,0);
		mPlayer.Render();
	}

	//----------------------------------------------------------------------------//
	@Override
	public void update(GameContainer inContainer, int inDeltaTime) throws SlickException 
	{
		UpdateInput(inContainer);
		
		mPlayer.UpdateInput(mLastInput, inDeltaTime);
		mPlayer.Update(inDeltaTime);
	}
	
	//----------------------------------------------------------------------------//
	private void UpdateInput(GameContainer inContainer)
	{
		mLastInput = inContainer.getInput();
	}

	//----------------------------------------------------------------------------//
	// Main //
	//----------------------------------------------------------------------------//
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer application = new AppGameContainer(new SimpleGame());
		
		application.setDisplayMode(800, 600, false);
		application.setTargetFrameRate(60);
		application.start();
	}
}
