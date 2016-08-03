// package edu.uwb.css; // Commented out by me (Oscar)

import java.lang.Thread.State;
import java.util.concurrent.locks.Condition;	// For Condition[] self

/**
 *
 * Created by mike on 7/11/2016.
 */
public class DiningPhilosophers {
    DiningState[] state;	// Can be either thinking, hungry, or eating

    // This will let all the tests run (and fail)
    // You'll want to remove it once you actually create an array :)
    int nPhil;
    
    // -------------------	Constructor		-------------------
    public DiningPhilosophers(int nPhilosophers) {
        nPhil =  nPhilosophers;
        state = new DiningState[numPhilosophers()];
        for(int i = 0; i < numPhilosophers(); i++)
        {
        	// All philosophers thinking initially, 
        	// just chillin'
        	state[i] = DiningState.THINKING;
        }    
    }
   
    // -----------------	takeForks()		-----------------
    // Changes state to HUNGRY and philosopher
    // is given the opportunity to eat.
    public void takeForks(int i) {
        Main.TPrint( "TakeForksAS:   i=" + i);
        // Philosophers want to eat!
        state[i] = DiningState.HUNGRY;
        test(i);
        Main.TPrint("state[" + i + "]" + " after test(" + i + ") is:  " + state[i]);
        while(state[i] != DiningState.EATING)
        {
        	try {
        		wait(); // If not allowed to eat, we wait
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(IllegalMonitorStateException e) {
				//e.printStackTrace();
			}
        }
        
    }

    // ----------------		returnForks()	-----------------
    // When a philosopher is done eating, his/her state
    // returns to THINKING and then the neighbors are 
    // given an opportunity to eat.
    public void returnForks(int i) {
        Main.TPrint( "returnForks:   i=" + i );
        state[i] = DiningState.THINKING;	// Done eating
        // Testing left and right neighbors.
        int oneLess = numPhilosophers() - 1;
    	int rightNeighbor = (i + oneLess) % numPhilosophers();
    	int leftNeighbor = (i + 1) % numPhilosophers();
    	test(rightNeighbor);
    	test(leftNeighbor);
    }
    
    // ----------------		test()		------------------
    // This method lets a philosopher eat by checking
    // that both neighbors are not eating. This is 
    // the only code that can chagen a philosopher's state
    // to EATING.
    public synchronized void test(int i)
    {
    	int oneLess = numPhilosophers() - 1;
    	int rightNeighbor = (i + oneLess) % numPhilosophers();
    	int leftNeighbor = (i + 1) % numPhilosophers();
    	// Checking if left, right neighbors are NOT eating and also
    	// checking of current philosopher is hungry
    	if( (state[rightNeighbor] != DiningState.EATING) 
    			&& (state[i] == DiningState.HUNGRY)
    			&& (state[leftNeighbor] != DiningState.EATING) )
    	{
    		// Eating now
    		state[i] = DiningState.EATING;
    		try {
        		notifyAll(); // Wakes up all processes waiting
			} catch (IllegalMonitorStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
    	}
    }

    // ---------------		numPhilosophers()	------------
    // Returns the number of philosophers
    public int numPhilosophers() {
        return nPhil;
    }

    // ---------------		getDiningState(int i)	------------
    // Returns state of philosopher
    public DiningState getDiningState(int i) {
        return state[i];
    }
}
