package org.usfirst.frc2783.subystems;

public class Turret implements Subsystem {

	private enum SubsystemState {
		IDLE,
		AIMING,
		AIMED,
		STOWING,
		STOWED;
	}
	
	private enum WantedState {
		IDLE,
		AIM,
		STOW;
	}
	
	public SubsystemState state;
	public WantedState wantedState;
	
	public Turret() {
		state = SubsystemState.IDLE;
		wantedState = WantedState.STOW;
	}
	
	public void 
	
}
