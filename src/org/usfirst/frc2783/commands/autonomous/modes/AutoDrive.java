package org.usfirst.frc2783.commands.autonomous.modes;

import java.util.UUID;

import org.usfirst.frc2783.subystems.SwerveController;
import org.usfirst.frc2783.util.Logger;
import org.usfirst.frc2783.util.Timer;
import org.usfirst.frc2783.util.Vector;

public class AutoDrive implements Action {

	private String id;
	
	Timer timer;
	
	double ang;
	double vel;
	double rot;
	
	public AutoDrive(double ang, double vel, double rot, double time) {
		id = "AutoDrive:" + UUID.randomUUID().toString();
		
		this.ang = ang;
		this.vel = vel;
		this.rot = rot;
		
		timer = new Timer(time);
	}
	
	@Override
	public void start() {
		timer.start();
	}
	
	@Override
	public void perform() {
		SwerveController.getInstance().move(new Vector(ang, vel, true), rot);
		SwerveController.getInstance().update(true);
	}
	
	@Override
	public boolean done() {
		return timer.ring();
	}

	@Override
	public void finish() {
	}
	
	
	@Override
	public boolean fail() {
		return false;
	}
	
	public String getId() {
		return id;
	}

	

	
}
