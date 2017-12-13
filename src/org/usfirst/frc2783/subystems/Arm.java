package org.usfirst.frc2783.subystems;

import org.usfirst.frc2783.commands.autonomous.modes.Action;
import org.usfirst.frc2783.robot.Constants;
import org.usfirst.frc2783.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogInput;

public class Arm implements Subsystem {
	
	public class LowerArm extends Action {
		public LowerArm() {
			super(Superstructure.getInstance().mArm, "LowerArm");
		}
		
		@Override
		public void perform() {
			setOpenLoop(-0.75);
		}
		
		public boolean done() {
			return isLowered();
		}
		
		@Override
		public void finish() {
			setOpenLoop(0);
		}
	}
	
	public class RaiseArm extends Action {
		public RaiseArm() {
			super(Superstructure.getInstance().mArm, "RaiseArm");
		}
		
		@Override
		public void perform() {
			setOpenLoop(0.75);
		}
		
		public boolean done() {
			return isStowed();
		}
		
		public void finish() {
			setOpenLoop(0);
		}
	}
	
	public enum SubsystemState {
		IDLE,
		LOWERING,
		READY,
		RAISING,
		STOWED,
		OPEN_LOOP;
	}
	
	public enum WantedState {
		IDLE,
		READY,
		STOW,
		OPEN_LOOP;
	}
	
	String id = "Arm";
	
	SubsystemState mState;
	WantedState mWantedState;
	
	Action mStagedAction;
	
	CANTalon mArm;
	
	AnalogInput mArmTopSwitch;
	
	public Arm() {
		mState = SubsystemState.IDLE;
		mWantedState = WantedState.IDLE;
		
		mArm = new CANTalon(Constants.kArmMotorId);
		
		mArmTopSwitch = new AnalogInput(Constants.kArmTopSwitchId);
	}
	
	public boolean isStowed() {
		return mArmTopSwitch.getValue() > 10;
	}
	
	public boolean isLowered() {
		return Robot.pdp.getCurrent(Constants.kArmPdpPortId) > Constants.kArmCurrentLoweredThreshold;
	}
	
	public void setOpenLoop(double speed) {
		mArm.set(speed);
	}
	
	public synchronized void readyArm() {
		setWantedState(WantedState.READY);
	}
	
	public synchronized void stowArm() {
		setWantedState(WantedState.STOW);
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
		case READY:
			if(mState == SubsystemState.READY) {
				stageAction(null);
				break;
			}
			if(isLowered()) {
				stageAction(null);
				mState = SubsystemState.READY;
				break;
			}
			mState = SubsystemState.LOWERING;
			stageAction(new LowerArm());
			break;
		case STOW:
			if(mState == SubsystemState.STOWED) {
				stageAction(null);
				break;
			}
			if(isStowed()) {
				stageAction(null);
				mState = SubsystemState.STOWED;
				break;
			}
			mState = SubsystemState.RAISING;
			stageAction(new RaiseArm());
			break;
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
