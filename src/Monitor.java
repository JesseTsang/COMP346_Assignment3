/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	Chopstick[] chopsticks;
	int numberOfPhilosophers;
	int leftChopstick = 0;
	int rightChopstick = 0;
	boolean talkingStatus = false;


	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		numberOfPhilosophers = piNumberOfPhilosophers;
		
		chopsticks = new Chopstick[numberOfPhilosophers + 1]; //(N+1)/2 pairs of chopsticks, N = # of philosophers
		
		for(int i=0; i<chopsticks.length; i++)
		{
			chopsticks[i] = new Chopstick();
			
			System.out.println("Chopstick " + i + " initialized.");
		}
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{
		leftChopstick = piTID - 1;
		rightChopstick = piTID;
		
		while(true)
		{
			//When both chopsticks are available
			if(!chopsticks[leftChopstick].ifCurrentInUsed() && !chopsticks[rightChopstick].ifCurrentInUsed())
			{
				//Check if the user also use the chopsticks last turn
				if((chopsticks[leftChopstick].getLastUserID() != piTID) || (chopsticks[rightChopstick].getLastUserID() != piTID))
				{
					//Pick up both chopsticks if no
					chopsticks[leftChopstick].inUse(piTID);
					chopsticks[rightChopstick].inUse(piTID);

					break;
				}
			}		
			
			//If the chopsticks are unavailable ... then wait.
			try 
			{
				wait();
			} 
			catch (InterruptedException e)
			{
				System.err.println("Monitor.pickUp():");
				DiningPhilosophers.reportException(e);
				System.exit(1);
			}			
		}	
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		leftChopstick = piTID - 1;
		rightChopstick = piTID;
		
		chopsticks[leftChopstick].notInUse();
		chopsticks[rightChopstick].notInUse();
		
		notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		while(talkingStatus == true)
		{
			//Wait when someone is talking
			try 
			{
				System.out.println("Start waiting ....");
				wait();
			}
			catch (InterruptedException e) 
			{
				System.err.println("Monitor.requestTalk():");
				DiningPhilosophers.reportException(e);
				System.exit(1);
			}
		}
		
		//When someone is done talking ... take the talking permit ... 
		talkingStatus = true;
		System.out.println("Start talking ....");
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		talkingStatus = false;
		notifyAll();
		System.out.println("Done talking ....");
	}
}

// EOF
