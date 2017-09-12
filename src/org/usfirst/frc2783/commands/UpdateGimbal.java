package org.usfirst.frc2783.commands;

import org.usfirst.frc2783.robot.Robot;
import org.usfirst.frc2783.subystems.Gimbal;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class UpdateGimbal extends Command {
	
    public UpdateGimbal() {
    	requires(Robot.gimbalBase);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.gimbalBase.stabilizeToGyro();
    	
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
