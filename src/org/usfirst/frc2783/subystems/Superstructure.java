package org.usfirst.frc2783.subystems;

import java.util.ArrayList;

import org.usfirst.frc2783.commands.autonomous.modes.ActionScheduler;
import org.usfirst.frc2783.loops.Loop;

public class Superstructure {
	public static Superstructure mSuperstructure = new Superstructure();
	
	public static Superstructure getInstance() {
		return mSuperstructure;
	}
	
	private enum SystemState {
		IDLE,
		STOWING,
		STOWED,
		DEPLOYING,
		DEPLOYED,
		THROWING,
		OPEN_LOOP;
	}
	
	private enum WantedState {
		IDLE,
		STOW,
		DEPLOY,
		THROW,
		OPEN_LOOP;
	}
	
	ActionScheduler scheduler;
	
	ArrayList<Subsystem> mSubsystems;
	
	SystemState mState;
	WantedState mWantedState;
	
//	Turret mTurret = new Turret();
	Intake mIntake = new Intake();
	public Arm mArm = new Arm();
	
	Superstructure() {
//		mSubsystems.add(mTurret);
		mSubsystems.add(mIntake);
		mSubsystems.add(mArm);
		
		scheduler = new ActionScheduler();
		scheduler.start();
	}
	
	Loop loop = new Loop() {

		@Override
		public void onStart() {
		}

		@Override
		public void onLoop() {
			for(Subsystem s : mSubsystems) {
				s.updateState();
			}	
			
			updateState();
			stageActions();
		}

		@Override
		public void onStop() {
			
		}
		
	};
	
	public void getGear() {
		setWantedState(WantedState.DEPLOY);
	}
	
	public void throwGear() {
		setWantedState(WantedState.THROW);
	}
	
	public void setOpenLoop(double arm, double intake) {
		mIntake.setOpenLoop(intake);
		mArm.setOpenLoop(arm);
	}
	
	public void stageActions() {
		for(Subsystem s : mSubsystems) {
			if(!s.isBusy()) {
				scheduler.queue(mIntake.getStagedAction());
			}
		}
	}
	
	public void updateState() { 
		switch(mWantedState) {
		case STOW:
			if(mState == SystemState.STOWED) {
				break;
			}
			mState = SystemState.STOWING;
			mIntake.intakeGear();
			if(!scheduler.isTaskActive(mIntake)) {
				mArm.stowArm();
			} else {
				mArm.readyArm();
			}
			if(!scheduler.isActive()) {
				mState = SystemState.STOWED;
				break;
			}
		case DEPLOY:
			mState = SystemState.DEPLOYING;
			mArm.readyArm();
			mIntake.intakeGear();
			if(!scheduler.isTaskActive(mArm)) {
				mState = SystemState.DEPLOYED;
			}
			if(!scheduler.isActive()) {
				setWantedState(WantedState.STOW);
			}
		}
	}
	
	public boolean isBusy() {
		return mState == SystemState.THROWING ||
				mState == SystemState.DEPLOYING ||
				mState == SystemState.STOWING;
	}
	
	public void setWantedState(WantedState state) {
		mWantedState = state;
	}

}