package org.usfirst.frc2783.subystems;

import org.usfirst.frc2783.commands.autonomous.modes.Action;
import org.usfirst.frc2783.robot.Constants;
import org.usfirst.frc2783.util.Timer;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;

public class Intake implements Subsystem {
	
	public class IntakeGear extends Action {
		public IntakeGear() {
			super(Superstructure.getInstance().mIntake, "IntakeGear");
		}

		@Override
		public void perform() {
			setOpenLoop(0.75);
		}

		@Override
		public boolean done() {
			return checkGear();
		}
		
		@Override
		public void finish() {
			setOpenLoop(0);
		}
	}
	
	public class ReleaseGear extends Action {
		boolean noGear = false;
		Timer timer;
		
		public ReleaseGear() {
			super(Superstructure.getInstance().mIntake, "ReleaseGear");
			timer = new Timer(0.5);
		}

		@Override
		public void perform() {
			setOpenLoop(-0.75);	
			if(!noGear && !checkGear()) {
				noGear = true;
				timer.start();
			}
		}

		@Override
		public boolean done() {
			return timer.ring();
		}
		
	}
	
	public enum SubsystemState {
		IDLE,
		INTAKING,
		RELEASING,
		NO_GEAR,
		HAS_GEAR,
		OPEN_LOOP;
	}
	
	public enum WantedState {
		IDLE,
		INTAKE,
		RELEASE,
		OPEN_LOOP;
	}
	
	String id = "Intake";
	
	SubsystemState mState;
	WantedState mWantedState;
	
	Action mStagedAction;
	
	VictorSP mRoller;
	
	AnalogInput mGearCheck;
	
	Intake() {
		mState = SubsystemState.IDLE;
		mWantedState = WantedState.IDLE;
		
		mRoller = new VictorSP(Constants.kRollerMotorId);
		
		mGearCheck = new AnalogInput(Constants.kGearCheckSwitchId);
	}
	
	public boolean checkGear() {
		return mGearCheck.getValue() > 10;
	}
	
	public void setOpenLoop(double speed) {
		mRoller.set(speed);
	}
	
	public synchronized void intakeGear() {
		setWantedState(WantedState.INTAKE);
	}
	
	public synchronized void releaseGear() {
		setWantedState(WantedState.RELEASE);
	}
	
	private void stageAction(Action a) {
		mStagedAction = a;
	}
	
	private void setWantedState(WantedState state) {
		mWantedState = state;
	}
	
	@Override
	public void updateState() {
		switch(mWantedState) {
		case INTAKE:
			if(mState == SubsystemState.HAS_GEAR) {
				stageAction(null);
				break;
			}
			if(checkGear()) {
				stageAction(null);
				mState = SubsystemState.HAS_GEAR;
				break;
			}
			mState = SubsystemState.INTAKING;
			stageAction(new IntakeGear());
		case RELEASE:
			if(mState == SubsystemState.NO_GEAR) {
				stageAction(null);
				break;
			}
			if(!checkGear()) {
				stageAction(null);
				mState = SubsystemState.NO_GEAR;
				break;
			}
			mState = SubsystemState.RELEASING;
			stageAction(new ReleaseGear());
		}
	}
	
	@Override
	public Action getStagedAction() {
		return mStagedAction;
	}
	
	@Override
	public boolean isBusy() {
		return mStagedAction != null;
	}

	@Override
	public String getId() {
		return id;
	}
}
