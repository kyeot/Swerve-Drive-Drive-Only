package org.usfirst.frc2783.loops;

import java.util.List;
import java.util.ArrayList;

import org.usfirst.frc2783.robot.Constants;
import org.usfirst.frc2783.util.CrashTrackingRunnable;
import org.usfirst.frc2783.util.Logger;

import edu.wpi.first.wpilibj.Notifier;

/**
 * Manages a List of loops, starting and stopping them under a
 * crash tracker.
 * 
 * @author 2783
 */
public class Looper {
	
	CrashTrackingRunnable runnable = new CrashTrackingRunnable() {
		@Override
		public void runCrashTracked() {
			for(Loop l : loops) {
				l.onLoop();
			}
		}
		
		@Override
		public void logCrash() {
			Logger.error("Exception caught in Loops");
		}
	};
	
	List<Loop> loops;
	Notifier notifier;
	
	public Looper() {
		loops = new ArrayList<Loop>();
		notifier = new Notifier(runnable);
	}
	
	public void startLoops() {
		for(Loop l : loops) {
			l.onStart();
		}
		notifier.startPeriodic(Constants.kPeriod);
		
	}
	
	public void addLoop(Loop l) {
		loops.add(l);
	}
	
	public void stopLoops() {
		notifier.stop();
		for(Loop l : loops) {
			l.onStop();
		}
	}

}
