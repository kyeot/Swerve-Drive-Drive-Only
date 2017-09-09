package org.usfirst.frc2783.subystems;

import org.usfirst.frc2783.robot.Constants;
import org.usfirst.frc2783.util.NavSensor;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for controlling the camera stabilization pan & tilt on chassis.
 * Uses 3-axis gyro to estimate rattling, and move the gimbal against it.
 * 
 * @author 
 */
public class Gimbal extends Subsystem {

    NavSensor gyro = NavSensor.getInstance();
    
    Servo pitchServo;
    Servo rollServo;
    Servo yawServo;
    
    double defaultPitch;
    
    public Gimbal() {
    	//PWM Ports
    	pitchServo = new Servo(9);
//    	rollServo = new Servo(8);
//    	yawServo = new Servo(7);
    	defaultPitch = Constants.cameraPitchOffset;
    }
    
    public void stabilizeToGyro() {
    	//Negative or Positive?
    	pitchServo.setAngle(gyro.getRawPitch()+(90+defaultPitch));
    }

    public void initDefaultCommand() {
        setDefaultCommand(new UpdateGimbal());
    }
}

