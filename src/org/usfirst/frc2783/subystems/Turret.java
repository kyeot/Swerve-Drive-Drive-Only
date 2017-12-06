package org.usfirst.frc2783.subystems;

import org.usfirst.frc2783.commands.autonomous.modes.Action;
import org.usfirst.frc2783.loops.Loop;
import org.usfirst.frc2783.robot.Constants;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

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
	
	String id = "Turret";
	
	public SubsystemState mState;
	public WantedState mWantedState;
	
	CANTalon mAdjuster;
	
	Loop loop = new Loop() {

		@Override
		public void onStart() {
			mWantedState = WantedState.STOW;
			stowTurret();
		}

		@Override
		public void onLoop() {
			updateState();
		}

		@Override
		public void onStop() {
		}
		
	};
	
	public Turret() {
		mState = SubsystemState.IDLE;
		mWantedState = WantedState.IDLE;
		
		mAdjuster.changeControlMode(TalonControlMode.Position);
		mAdjuster.setPID(Constants.kTurretP, Constants.kTurretI, Constants.kTurretD);
	}
	
	public boolean isOnTarget() {
		return Math.abs(mAdjuster.getError()) < Constants.kTurretAngleTolerance;
	}
	
	public void visionAimTurret() {
		setWantedState(WantedState.AIM);
		mAdjuster.set(90);
	}
	
	public void stowTurret() {
		mAdjuster.set(Constants.kTurretStowedAngle);
	}
	
	public void updateState() {
		if(mWantedState == WantedState.STOW) {
			if(isOnTarget()) {
				mState = SubsystemState.STOWED;
			} else {
				mState = SubsystemState.STOWING;
			}
		} else if(mWantedState == WantedState.AIM) {
			if(isOnTarget()) {
				mState = SubsystemState.AIMED;
			} else {
				mState = SubsystemState.AIMING;
			}
		} if (mWantedState == WantedState.IDLE) {
			mState = SubsystemState.IDLE;
		}
	}
	
	public void setWantedState(WantedState state) {
		mWantedState = state;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Action getStagedAction() {
		return null;
	}

	@Override
	public boolean isBusy() {
		return false;
	}
	
}
