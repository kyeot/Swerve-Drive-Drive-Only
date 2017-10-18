package org.usfirst.frc2783.commands;

import org.usfirst.frc2783.robot.OI;
import org.usfirst.frc2783.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ActiveGearDrive extends Command {

	
    public ActiveGearDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.activeGearBase);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double liftValue = -OI.gunner.getRawAxis(1);
    	double rollValue = OI.gunner.getRawAxis(5);
    	
    	if(Math.abs(rollValue) > .2) {
    		Robot.activeGearBase.spinRoller(rollValue);
    	} else {
    		Robot.activeGearBase.spinRoller(0);
    	}
    	
    	if(Math.abs(liftValue) > .2){
    		Robot.activeGearBase.setLifterSpeedVbus(liftValue);
    	} else {
    		Robot.activeGearBase.setLifterSpeedVbus(0);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double liftValue = -OI.gunner.getRawAxis(1)*0.6;
    	double rollValue = OI.gunner.getRawAxis(5);
   
    	if(Math.abs(rollValue) > .2) {
    		Robot.activeGearBase.spinRoller(rollValue);
    	} else {
    		Robot.activeGearBase.spinRoller(0);
    	}
 	
    	if(Math.abs(liftValue) > .12){
    		Robot.activeGearBase.setLifterSpeedVbus(liftValue);
    	} else {
    		Robot.activeGearBase.setLifterSpeedVbus(0);
    	}
    	
    	if(Robot.limitSwitches[0].get()) {
    		SmartDashboard.putString("DB/String 0", "Gear Limit Switch: On");
    	} else {
    		SmartDashboard.putString("DB/String 0", "Gear Limit Switch: Off");
    	}
    	
    	if(Robot.limitSwitches[1].get()) {
    		SmartDashboard.putString("DB/String 1", "Arm Limit Switch: On");
    	} else {
    		SmartDashboard.putString("DB/String 1", "Arm Limit Switch: Off");
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;    
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.activeGearBase.spinRoller(0);
    	Robot.activeGearBase.setLifterSpeedVbus(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.activeGearBase.setLifterSpeedVbus(0);
    	Robot.activeGearBase.spinRoller(0);
    }
}
