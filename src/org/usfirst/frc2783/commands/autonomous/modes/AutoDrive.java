package org.usfirst.frc2783.commands.autonomous.modes;

import java.util.UUID;

import org.usfirst.frc2783.robot.Robot;
import org.usfirst.frc2783.subystems.Superstructure;
import org.usfirst.frc2783.subystems.SwerveController;
import org.usfirst.frc2783.util.Logger;
import org.usfirst.frc2783.util.Timer;
import org.usfirst.frc2783.util.Vector;

public class AutoDrive extends Action {
	
	Timer timer;
	
	double ang;
	double vel;
	double rot;
	
	public AutoDrive(double ang, double vel, double rot, double time) {
		super(Superstructure.getInstance().mArm, "AutoDrive");
		
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
}
