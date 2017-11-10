package org.usfirst.frc2783.vision.tracking;

import java.util.Map;
import java.util.TreeMap;

import org.usfirst.frc2783.robot.Constants;
import org.usfirst.frc2783.util.Bearing;
import org.usfirst.frc2783.util.Timestamp;

public class GyroTracker {
	
	Map<Timestamp, Bearing> history = new TreeMap();
	
	public GyroTracker() {
	}
	
	public void register(Timestamp t, Bearing b) {
		history.put(t, b);
		update();
	}
	
	public void update() {
		if(isAlive()) {
			for(Timestamp t : history.keySet()) {
				if(t.getAge() > Constants.kTargetMaxAge) {
					history.remove(t);
				}
			}
		}
	}
	
	public Bearing getLatestAngle() {
		return history.get(history.keySet().toArray()[0]);
	}
	
	public Bearing getAngleAtTime(Timestamp time) {
		if(history.get(time) != null) {
			return history.get(time);
		} else {
			boolean comparison = false;
			for(Timestamp t : history.keySet()) {
				comparison = t.getTime()-time.getTime() < 0;
				if(comparison) {
					return history.get(t);
				}
			}
			return null;
		}
	}
	
	public boolean isAlive() {
		return !history.isEmpty();
	}

}
