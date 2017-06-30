package org.usfirst.frc2783.util;

import org.usfirst.frc2783.robot.Constants;

import edu.wpi.first.wpilibj.Utility;

public abstract class EventLogger {
	
	String msg;
	double timeLast = 0;
	
	public EventLogger(String msg) {
		this.msg = msg;
	}

	public abstract boolean event();
	
	public void handleEvent() {
		if(event() && ((Utility.getFPGATime()-timeLast) > Constants.kEventDelay*1000000)) {
			Logger.log(msg);
			timeLast = Utility.getFPGATime();
		}
	}
	
}
