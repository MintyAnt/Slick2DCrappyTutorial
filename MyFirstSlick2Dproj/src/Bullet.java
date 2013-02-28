import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Image;

//----------------------------------------------------------------------------//
public class Bullet
{
	// Members //
	Image mImage = null;
	
	Vector2f mPosition;
	Vector2f mHeading;
	
	float mSpeed;
	float mTimeToLive;
	
	// Accessors // 
	public boolean IsExpired() { return mTimeToLive <= 0.0f; }
	
	// Methods //
	//----------------------------------------------------------------------------//
	public Bullet()
	{
		
	}
	
	//----------------------------------------------------------------------------//
	public void Initialize(Image inBulletImage, Vector2f inStartPosition, Vector2f inHeading, float inSpeed, float inTimeToLiveInSeconds)
	{
		mImage = inBulletImage;
		mPosition = inStartPosition;
		mHeading = inHeading;
		mTimeToLive = inTimeToLiveInSeconds * 1000.0f;
		mSpeed = inSpeed;
		
		//final Vector2f ORIENTATION_UP = new Vector2f(0, 0);
		
		float angle = (float)mHeading.getTheta();
		inBulletImage.rotate(angle);
	}
	
	//----------------------------------------------------------------------------//
	public void Render()
	{
		mImage.drawCentered(mPosition.getX(), mPosition.getY());
	}
	
	//----------------------------------------------------------------------------//
	public void Update(float inDeltaTime)
	{
		if (!IsExpired())
		{
			mTimeToLive -= inDeltaTime;
			
			//@HACK I assume when its updating that it's a live bullet. BEST HAVE YO SAFTEY GOGGLES ON
			float addX = mPosition.getX() + (mHeading.getX() * mSpeed * inDeltaTime);
			float addY = mPosition.getY() + (mHeading.getY() * mSpeed * inDeltaTime);
			mPosition.set(addX, addY);
		}
	}
}
