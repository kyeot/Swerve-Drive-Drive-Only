package org.usfirst.frc2783.vision.tracking;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.usfirst.frc2783.robot.Constants;
import org.usfirst.frc2783.util.Timestamp;
import org.usfirst.frc2783.util.Vector;

public class TargetTracker {

	Map<Timestamp, Vector> history = new TreeMap<>();
	
	public TargetTracker() {
	}
	
	public void register(Timestamp timestamp, Vector target) {
		history.put(timestamp, target);
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
	
	public Vector getLatestTarget() {
		return history.get(history.keySet().toArray()[0]);
	}
	
	/**
	 * Averages the entire history of Targets to get a single, smoother target (interpolation)
	 */
	public Vector getSmoothTarget() {
		double x = 0;
		double y = 0;
		for(Vector v : history.values()) {
			x += v.getA();
			y += v.getB();
		}
		x /= history.size();
		y /= history.size();
		return new Vector(x, y);
	}
	
	/**
	 * Returns a stability value between 0-1
	 * (Team 254's idea)
	 */
	public double getStability() {
		return Math.min(1, history.size() / (Constants.kCameraFrameRate * Constants.kTargetMaxAge)); //if theres more frames than targets in the history, its not a stable target
	}
	
	public boolean isAlive() {
		return !history.isEmpty();
	}
	
	ArrayList<Double> getTimeArray() {
		ArrayList<Double> times = new ArrayList<Double>();
		for(Timestamp t : history.keySet()) {
			times.add(t.getTime());
		}
		return times;
	}
	
}
