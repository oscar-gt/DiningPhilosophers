// package edu.uwb.css; // Commented out by me (Oscar)

import java.lang.Thread.State;
import java.util.concurrent.locks.Condition;	// For Condition[] self

/**
 *
 * Created by mike on 7/11/2016.
 */
public class DiningPhilosophers {
    DiningState[] state;	// Can be either thinking, hungry, or eating
    Condition[] self;		// Will either wait() or signal()

    // This will let all the tests run (and fail)
    // You'll want to remove it once you actually create an array :)
    int nPhil;
    
    // -------------------	Constructor		-------------------
    public DiningPhilosophers(int nPhilosophers) {
        nPhil =  nPhilosophers;
        state = new DiningState[numPhilosophers()];
        self = new Condition[numPhilosophers()];
        for(int i = 0; i < numPhilosophers(); i++)
        {
        	// All philosophers thinking initially, 
        	// just chillin'
        	state[i] = DiningState.THINKING;
        	// self = ...
        }
        
        
    }
   
    // -----------------	takeForks()		-----------------
    public void takeForks(int i) {
        Main.TPrint( "TakeForksAS:   i=" + i);
        // Philosophers want to eat!
        state[i] = DiningState.HUNGRY;
        test(i);
        Main.TPrint("state[" + i + "]" + " after test(" + i + ") is:  " + state[i]);
        while(state[i] != DiningState.EATING)
        {
        	try {
				//self[i].wait();
        		wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
    }

    // ----------------		returnForks()	-----------------
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
    public synchronized void test(int i)
    {
    	int oneLess = numPhilosophers() - 1;
    	int rightNeighbor = (i + oneLess) % numPhilosophers();
    	int leftNeighbor = (i + 1) % numPhilosophers();
    	// Checking if left, right neighbors are NOT eating and also
    	// checking of current philosopher is hungry
    	if( (state[rightNeighbor] != DiningState.EATING) && (state[i] == DiningState.HUNGRY)
    			&& (state[leftNeighbor] != DiningState.EATING) )
    	{
    		// Eating 
    		state[i] = DiningState.EATING;
    		//self[i].signal();
    		try {
				//self[i].wait();
        		notifyAll();
			} catch (IllegalMonitorStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
    	}
    }

    // ---------------		numPhilosophers()	------------
    public int numPhilosophers() {
        return nPhil;
    }

    // ---------------		getDiningState9)	------------
    public DiningState getDiningState(int i) {
        return state[i];
    }
}
