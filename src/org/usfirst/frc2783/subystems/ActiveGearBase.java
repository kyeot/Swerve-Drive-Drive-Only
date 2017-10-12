package org.usfirst.frc2783.subystems;

import org.usfirst.frc2783.commands.ActiveGearDrive;
import org.usfirst.frc2783.robot.Constants;
import org.usfirst.frc2783.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ActiveGearBase extends Subsystem {
	
	private VictorSP gearRoller;
	private CANTalon gearLifter;
	
	public Encoder lifterEnc;
	
	public ActiveGearBase(){
		gearRoller = new VictorSP(Constants.kGearRollerId);
		gearLifter = new CANTalon(Constants.kGearHolderId);
	}
	
	public void spinRoller(double speed){
		if(Robot.limitSwitches[0].get() && speed > 0) {
			speed = 0;
		}
		
		gearRoller.set(speed);
	}
	
	public void setLifterSpeedVbus(double speed) {
		if(Robot.limitSwitches[1].get() && speed > 0) {
			speed = 0;
		}
		
		gearLifter.set(speed);
    }

    public void initDefaultCommand() {
    	setDefaultCommand(new ActiveGearDrive());
    }
}

