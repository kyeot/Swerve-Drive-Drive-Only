package org.usfirst.frc2783.loops;

import org.usfirst.frc2783.util.EventLogger;

import edu.wpi.first.wpilibj.DriverStation;

public class LogData implements Loop{
	
	EventLogger batteryHandler = new EventLogger("Brown Out!"){
		@Override
		public boolean event() {
			return DriverStation.getInstance().isBrownedOut();
		}
	};

	@Override
	public void onStart() {
		
	}

	@Override
	public void onLoop() {
		batteryHandler.handleEvent();
	}

	@Override
	public void onStop() {
		
	}

}
