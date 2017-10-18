package org.usfirst.frc2783.loops;

import org.usfirst.frc2783.util.EventLogger;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Simple loop that runs the events of EventLogger
 * 
 * @author 2783
 */
public class LogData implements Loop{
	
	EventLogger batteryHandler = new EventLogger("Battery Browned Out!", "WARN"){
		@Override
		public boolean event() {
			return DriverStation.getInstance().isBrownedOut();
		}
	};
	
	EventLogger dcHandler = new EventLogger("The Driver Station is Disconnected!", "WARN"){
		@Override
		public boolean event() {
			return !DriverStation.getInstance().isDSAttached();
		}
	};

	@Override
	public void onStart() {
		
	}

	@Override
	public void onLoop() {
		batteryHandler.handleEvent();
		dcHandler.handleEvent();
	}

	@Override
	public void onStop() {
		
	}

}
