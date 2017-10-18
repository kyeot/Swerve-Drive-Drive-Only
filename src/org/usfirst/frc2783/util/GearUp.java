package org.usfirst.frc2783.util;

import org.usfirst.frc2783.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearUp extends Command {

    public GearUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.activeGearBase.setLifterSpeedVbus(0.7);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.limitSwitches[1].get();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.activeGearBase.setLifterSpeedVbus(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
