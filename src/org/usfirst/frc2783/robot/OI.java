package org.usfirst.frc2783.robot;

import org.usfirst.frc2783.util.AxisButton;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 */
public class OI {
	
	public static Joystick driver = new Joystick(Constants.kDriverControllerId);
	public static Joystick gunner = new Joystick(Constants.kGunnerControllerId);
	
	AxisButton gearLift = new AxisButton(gunner, 5);
	AxisButton gearHolder = new AxisButton(gunner, 1);
	AxisButton Climber = new AxisButton(gunner, 3);

    public OI() {
    	
    }

}

