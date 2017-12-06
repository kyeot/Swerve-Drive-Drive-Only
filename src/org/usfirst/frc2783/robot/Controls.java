package org.usfirst.frc2783.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controls {
	private static Controls mControls = new Controls();
	
	public static Controls getInstance() {
		return mControls;
	}
	
	Joystick mDriver;
	Joystick mGunner;
	
	Controls() {
		mDriver = new Joystick(Constants.kDriverControllerId);
		mGunner = new Joystick(Constants.kGunnerControllerId);
	}
	
	// --=DRIVER CONTROLS=--
	public double getTranslateX() {
		return Math.abs(mDriver.getRawAxis(0)) > Constants.kDriverTranslateDeadband ?
						mDriver.getRawAxis(0) : 0;  //Left Stick X
	}
	
	public double getTranslateY() {
		return Math.abs(mDriver.getRawAxis(1)) > Constants.kDriverTranslateDeadband ?
						-mDriver.getRawAxis(1) : 0; //Left Stick Y
	}
	
	public double getRotate() {
		return Math.abs(mDriver.getRawAxis(4)) > Constants.kDriverRotateDeadband ? 
						mDriver.getRawAxis(4) : 0; //Right Stick X
	}
	
	public boolean getRealignWheels() {
		return mDriver.getRawButton(3); //X
	}
	
	public boolean getRealignGyro() {
		return mDriver.getRawButton(4); //Y
	}
	
	public boolean getFast() {
		return mDriver.getRawButton(5); //LB
	}
	
	public boolean getSlow() {
		return mDriver.getRawButton(6); //RB
	}
	
	public boolean getVisionDebug() {
		return mDriver.getRawButton(1); //A
	}
	
	// --=GUNNER CONTROLS=--
	// Closed Loop
	public boolean getActiveGearDeploy() {
		return mGunner.getRawButton(1); //A
	}
	
	public boolean getActiveGearThrow() {
		return mGunner.getRawButton(2); //B
	}
	
	public boolean getActiveGearAbort() {
		return mGunner.getRawButton(3); //X
	}
	
	// Open Loop (Manual Override)
	public boolean getOverride() {
		return mGunner.getRawButton(5) && mGunner.getRawButton(6); //LB && RB
	}
	
	public double getArmRate() {
		return Math.abs(mGunner.getRawAxis(1)) > Constants.kGunnerArmDeadband ?
						-mGunner.getRawAxis(1) : 0; //Left Stick Y
	}
	
	public double getRollerRate() {
		return Math.abs(mGunner.getRawAxis(4)) > Constants.kGunnerRollerDeadband ?
						-mGunner.getRawAxis(4) : 0; //Right Stick Y
	}
	
}
