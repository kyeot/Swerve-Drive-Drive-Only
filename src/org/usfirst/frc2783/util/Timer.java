package org.usfirst.frc2783.util;

import edu.wpi.first.wpilibj.Utility;

public class Timer {

	double startTime;
	double endTime;
	double time;
	
	public Timer(double time) {
		this.time = time;
		startTime = 0;
		endTime = 0;
	}
	
	public void start(){
		startTime = Utility.getFPGATime();
		endTime = startTime + (1000000 * time);
	}
	
	public boolean ring() {
		return Utility.getFPGATime() >= endTime;
	}
	
}
