package org.usfirst.frc2783.util;

public class Transform {
	
	public class Vector {
		double a;
		double b;
		
		public Vector(double a, double b) {
			this.a = a;
			this.b = b;
		}
		
		public Vector add(Vector v) {
			double c = a + v.getA();
			double d = b + v.getB();
			return new Vector(c, d);
		}
		
		public double mag() {
			return Math.abs(Math.sqrt((a*a)+(b*b)));
		}
		
		public double dir() {
			return Math.atan2(a,b);
		}
		
		public double getA() {
			return a;
		}
		
		public double getB() {
			return b;
		}
	}
	
	public class Bearing {
		double theta;
		
		public Bearing(double theta) {
			this.theta = theta;
		}
		
		public double getTheta() {
			return theta;
		}
	}
	
	Vector translation;
	Bearing rotation;
	
	public Transform(double x, double y, double theta) {
		this.translation = new Vector(x, y);
		this.rotation = new Bearing(theta);
	}
	
	public Transform(Vector translation, Bearing rotation) {
		this.translation = translation;
		this.rotation = rotation;
	}
	
	public Vector getTranslation() {
		return this.translation;
	}
	
	public Bearing getRotation() {
		return this.rotation;
	}

}



















