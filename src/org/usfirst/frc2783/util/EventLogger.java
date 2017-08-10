package org.usfirst.frc2783.util;

import org.usfirst.frc2783.robot.Constants;

import edu.wpi.first.wpilibj.Utility;

/**
 * Abstract class which executes code when the abstract method event() returns true.
 * 
 * @author 2783
 */
public abstract class EventLogger {
	
	String msg;
	String lvl;
	double timeLast = 0;
	
	public EventLogger(String msg, String loggerLevel) {
		this.msg = msg;
		this.lvl = loggerLevel;
	}

	public abstract boolean event();
	
	public void handleEvent() {
		if(event() && ((Utility.getFPGATime()-timeLast) > Constants.kEventDelay*1000000)) {
			Logger.log(lvl, msg);
			timeLast = Utility.getFPGATime();
		}
	}
	
}
