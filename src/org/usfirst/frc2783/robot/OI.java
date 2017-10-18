package org.usfirst.frc2783.robot;

import org.usfirst.frc2783.util.AxisButton;
import org.usfirst.frc2783.util.GearUp;
import org.usfirst.frc2783.util.LimitSwitch;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class OI {
	
	public static Joystick driver = new Joystick(Constants.kDriverControllerId);
	public static Joystick gunner = new Joystick(Constants.kGunnerControllerId);
	
	AxisButton gearLift = new AxisButton(gunner, 5);
	AxisButton gearHolder = new AxisButton(gunner, 1);
	AxisButton climber = new AxisButton(gunner, 3);
	
    public OI() {
    	Robot.limitSwitches[0].whenActive(new GearUp());
    	
    }

}

