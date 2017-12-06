package org.usfirst.frc2783.commands.autonomous.modes;

import java.util.UUID;

import org.usfirst.frc2783.subystems.Subsystem;

public abstract class Action {
	Subsystem mReqSubsystem;
	String mId;
	
	public Action(Subsystem req, String id) {
		mReqSubsystem = req;
		mId = id + ":" + UUID.randomUUID().toString();
	}
	
	public void start() {
	}
	
	public abstract void perform();
	
	public abstract boolean done();

	public void finish() {	
	}
	
	public boolean fail() {
		return false;
	}

	public String getId() {
		return mId;
	}
	
	public Subsystem getSubsystem() {
		return mReqSubsystem;
	}
	
}
