package org.usfirst.frc2783.commands;

import org.usfirst.frc2783.robot.Controls;
import org.usfirst.frc2783.robot.FieldTransform;
import org.usfirst.frc2783.robot.OI;
import org.usfirst.frc2783.robot.Robot;
import org.usfirst.frc2783.subystems.SwerveController;
import org.usfirst.frc2783.util.NavSensor;
import org.usfirst.frc2783.util.Vector;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to run swerve based on controller inputs,
 * default command of SwerveDriveBase
 *
 * @author 2783
 */
public class SwerveDrive extends Command {
	
	NavSensor gyro = NavSensor.getInstance();
	SwerveController swerveController = SwerveController.getInstance();
	FieldTransform fieldTransform = FieldTransform.getInstance();
	Controls controls = Controls.getInstance();

	//Makes SwerveDrive require the subsystem swerveBase
    public SwerveDrive() {
    	requires(Robot.swerveBase);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//Sets input for swerveDrive method as input from controller stick axes. Note: FBValue is negative as required by doc linked to in swerveDrive method
    	Double fbValue = controls.getTranslateY()/2;
    	Double rlValue = -controls.getTranslateX()/2;
    	Double rotValue = controls.getRotate()/2;
    	
    	//While the left bumper is held go full speed
    	if(controls.getFast()) {
    		fbValue *= 2;
    		rlValue *= 2;
    		rotValue *= 2;
    	}
    	
    	if(controls.getSlow()) {
    		fbValue /= 2;
    		rlValue /= 2;
    		rotValue /= 2;
    	}
    	
    	//If the X button is pressed resets the Swerve Modules
    	if(controls.getRealignWheels()) {
    		Robot.swerveBase.setZero();
    	}
    	
    	//If Y is pressed resets the field orientation
    	if(controls.getRealignGyro()) {
    		gyro.resetGyroNorth(0, 0);
    	}
    	
    	if(controls.getVisionDebug()) {
//    		swerveController.slide(fbValue, rlValue);
    		if(fieldTransform.targetHistory.getLatestTarget() != null) {
        		swerveController.setPose(fieldTransform.targetHistory.getSmoothTarget().dir());

    		}
    	} else {
    		swerveController.move(new Vector(fbValue, rlValue), rotValue);
    	}
    	
    	swerveController.update(true);
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

}