package org.usfirst.frc2783.util;

public abstract class CrashTrackingRunnable implements Runnable {
	
	@Override
	public void run() {
		try {
			runCrashTracked();
		} catch(Throwable t) {
			Logger.error("Exception caught in Loops");
			throw(t);
		}
	}
	
	public abstract void runCrashTracked();

}
