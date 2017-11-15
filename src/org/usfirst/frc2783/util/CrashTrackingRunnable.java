package org.usfirst.frc2783.util;


/**
 * Abstract class that will execute the code under runCrashTracked()
 * in a try/catch with run()
 * 
 * @author 254
 */
public abstract class CrashTrackingRunnable implements Runnable {
	
	@Override
	public void run() {
		try {
			runCrashTracked();
		} catch(Throwable t) {
			Logger.error("Exception caught in Loops");
			t.printStackTrace();
			throw(t);
		}
	}
	
	public abstract void runCrashTracked();

}
