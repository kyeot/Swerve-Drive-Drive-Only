package org.usfirst.frc2783.robot;

import org.usfirst.frc2783.util.AxisButton;
import org.usfirst.frc2783.util.GearUp;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 *
 */
public class OI {
	
	public static Joystick driver = new Joystick(Constants.kDriverControllerId);
	public static Joystick gunner = new Joystick(Constants.kGunnerControllerId);
	
	AxisButton gearLift = new AxisButton(gunner, 5);
	AxisButton gearHolder = new AxisButton(gunner, 1);
	AxisButton climber = new AxisButton(gunner, 3);
	
	Button visionButton = new JoystickButton(driver, 1);
	
    public OI() {
    	Robot.limitSwitches[0].whenActive(new GearUp());
    	
    }

}

