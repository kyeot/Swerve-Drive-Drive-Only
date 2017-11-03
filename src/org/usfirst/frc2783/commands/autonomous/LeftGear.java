package org.usfirst.frc2783.commands.autonomous;

import org.usfirst.frc2783.commands.autonomous.modes.AutoDrive;
import org.usfirst.frc2783.robot.FieldTransform;
import org.usfirst.frc2783.vision.AdjustToTarget;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftGear extends CommandGroup {

    public LeftGear() {
    	
    	addSequential(new AutoDrive(0, 0.25, 0, true, 2){
    		public boolean finish() {return false;}
    	});
    	addSequential(new AutoDrive(0, 0, 0.5, true, 1){
    		public boolean finish() {return false;}
    	});
    	addSequential(new AutoDrive(0, 0.25, 0, true, 1000){
    		public boolean finish() {
    			return !FieldTransform.getInstance().getFieldToTargets().isEmpty();
    		}
    	});
    	addSequential(new AdjustToTarget());
    	addSequential(new AutoDrive(FieldTransform.getInstance().getFieldToTargets().get(0).dir().getTheta(), 0.25, 0, true, 4){
    		public boolean finish() {return false;}
    	});
      	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
