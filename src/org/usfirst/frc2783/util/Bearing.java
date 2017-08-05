package org.usfirst.frc2783.util;

public class Bearing {
	double theta;
	
	public Bearing(double theta) {
		this.theta = theta;
	}
	
	public Bearing(Vector v) {
		theta = v.dir();
	}
	
	public Bearing add(Bearing b) {
		return new Bearing((this.theta+b.getTheta())%360);
	}
	
	public double sin() {
		return Math.sin(Math.toRadians(theta));
	}
	
	public double cos() {
		return Math.cos(Math.toRadians(theta));
	}
	
	public double getTheta() {
		return theta;
	}
}
