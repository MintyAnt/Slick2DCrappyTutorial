import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

//----------------------------------------------------------------------------//
public class Player
{
	// Members //
	Image mImage = null;
	Image mBulletImage = null;
	
	Vector2f mPosition;
	Vector2f mHeading;
	
	float mScale;
	float mCurrentRotation;
	float mRotationSpeed;
	float mSpeed;
	float mScaleSpeed;
	
	List<Bullet> mLiveBullets;
	float mFirePulse;
	float mFireCooldown;

	// Methods //
	//----------------------------------------------------------------------------//
	public Player(float inScale, float inSpeed, float inRotationSpeed, float inScaleSpeed, float inFireCooldown)
	{
		mScale = inScale;
		mSpeed = inSpeed;
		mRotationSpeed = inRotationSpeed;
		mScaleSpeed = inScaleSpeed;
		mFireCooldown = inFireCooldown;
		
		mPosition = new Vector2f();
		mHeading = new Vector2f(1, 0);
		
		mFirePulse = 0.0f;
		
		mLiveBullets = new LinkedList<Bullet>();
	}

	//----------------------------------------------------------------------------//
	public void Initialize(String inImageName, String inBulletImageName) throws SlickException
	{
		mImage = new Image(inImageName);
		mBulletImage = new Image(inBulletImageName);
	}
	
	//----------------------------------------------------------------------------//
	public void Update(float inDeltaTime)
	{
		mFirePulse -= inDeltaTime;
		
		List<Bullet> expiredBullets = new ArrayList<Bullet>();
		for (Bullet currentBullet : mLiveBullets)
		{
			currentBullet.Update(inDeltaTime);
			if (currentBullet.IsExpired())
				expiredBullets.add(currentBullet);
		}
		
		for (Bullet expiredBullet : expiredBullets)
		{
			mLiveBullets.remove(expiredBullet);
		}
	}
	
	//----------------------------------------------------------------------------//
	public void UpdateInput(Input inCurrentInput, float inDeltaTime)
	{
		// Rotate Left
		if (inCurrentInput.isKeyDown(Input.KEY_A))
		{
			float rotation = (-mRotationSpeed * inDeltaTime);
			mImage.rotate(rotation);
			mHeading.add(rotation);
			mHeading.normalise();
		}
		
		// Rotate Right
		if (inCurrentInput.isKeyDown(Input.KEY_D))
		{
			float rotation = (mRotationSpeed * inDeltaTime);
			mImage.rotate(rotation);
			mHeading.add(rotation);
			mHeading.normalise();
		}
		
		// Move foward
		if (inCurrentInput.isKeyDown(Input.KEY_W))
		{
			float movementDistance = mSpeed * inDeltaTime;
			
			float x = mPosition.getX() + (mHeading.getX() * movementDistance);
			float y = (mPosition.getY() + (mHeading.getY() * movementDistance));
			
			mPosition.set(x, y);
		}

		// Scale Up
		if (inCurrentInput.isKeyDown(Input.KEY_A))
		{
			if (mScale >= 5.0f)
				mScale += 0.1f;
			
            mImage.setCenterOfRotation(mImage.getWidth() / 2.0f * mScale, 
            		mImage.getHeight() / 2.0f * mScale);
		}

		// Scale Down
		if (inCurrentInput.isKeyDown(Input.KEY_A))
		{
			if (mScale >= 1.0f)
				mScale -= 0.1f;
			
            mImage.setCenterOfRotation(mImage.getWidth() / 2.0f * mScale, 
            		mImage.getHeight() / 2.0f * mScale);
		}
		
		// Attack
		if (inCurrentInput.isKeyPressed(Input.KEY_SPACE))
		{
			SpawnBullet();
		}
	}
	
	//----------------------------------------------------------------------------//
	public void Render()
	{
		mImage.drawCentered(mPosition.getX(), mPosition.getY());
		
		for (Bullet currentBullet : mLiveBullets)
		{
			currentBullet.Render();
		}
	}
	
	//----------------------------------------------------------------------------//
	private void SpawnBullet()
	{
		if (mFirePulse > 0.0f)
			return;
		
		Bullet newBullet = new Bullet();
		
		final float BULLET_SPEED = 2.0f;
		final float BULLET_LIVE_TIME = 2.0f;
		
		// Assuming it's drawn centered...
		Vector2f spawnPos = new Vector2f(mImage.getWidth() / 2.0f, 0);
		spawnPos.add(mHeading.getTheta());
		spawnPos.add(mPosition);
		
		newBullet.Initialize(mBulletImage.copy(), spawnPos, new Vector2f(mHeading), BULLET_SPEED, BULLET_LIVE_TIME);
		
		mLiveBullets.add(newBullet);
		
		mFirePulse = mFireCooldown;
	}
}
