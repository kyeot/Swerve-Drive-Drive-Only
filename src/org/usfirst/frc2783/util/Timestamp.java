package org.usfirst.frc2783.util;

import edu.wpi.first.wpilibj.Utility;

public class Timestamp {

	double t;
	
	public Timestamp(double t) {
		this.t = t;
	}
	
	public static Timestamp setNewTime() {
		return new Timestamp(Utility.getFPGATime());
	}
	
	public double getTime() {
		return t;
	}
	
	public double getAge() {
		return Utility.getFPGATime() - t;
	}
	
}
