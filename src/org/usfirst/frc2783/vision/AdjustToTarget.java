package org.usfirst.frc2783.vision;

import org.usfirst.frc2783.robot.FieldTransform;
import org.usfirst.frc2783.subystems.SwerveController;
import org.usfirst.frc2783.util.Bearing;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AdjustToTarget extends Command {
	
	SwerveController swerveController = SwerveController.getInstance();

	boolean end = false;
	
    public AdjustToTarget() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		if(!FieldTransform.fieldTransform.getFieldToTargets().isEmpty()){
			swerveController.setPose(new Bearing(FieldTransform.fieldTransform.getFieldToTargets().get(0).dir().getTheta()));
		}
		if(swerveController.posePid.getError() <=1 && swerveController.posePid.getError() >= -1){
			end = true;
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return end;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
