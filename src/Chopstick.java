/**
 * Chopstick class (each object represents 1 chopstick)
 * Includes methods to prevent starvation (last eaten philosopher has lower priority),
 * to check if the chopstick is current in used, set the chopstick current status (in used/not in use)
 */

public class Chopstick 
{
	private int lastUserID = 0;
	private boolean isInUse = true;
	
	/**
	 * Constructor
	 */
	public Chopstick()
	{
		isInUse = false;
		lastUserID = 0;
	}
	
	/**
	 * Set the chopstick status to "in used", so it can't be used by other users.
	 * Also associate the chopstick to the current user's ID.
	 */
	public void inUse(int userID)
	{
		isInUse = true;
		lastUserID = userID;
	}
	
	/**
	 * Set the chopstick status to "not in use", so it is available for other users.
	 */
	public void notInUse()
	{
		isInUse = false;
	}
	
	/**
	 * @return Return if the chopstick is being used by other user.
	 */
	public boolean ifCurrentInUsed()
	{
		return isInUse;	
	}

	/**
	 * @return Return the ID of the last user of this chopstick.
	 */
	public int getLastUserID() 
	{
		return lastUserID;
	}
}
