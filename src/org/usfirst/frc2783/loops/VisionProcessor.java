package org.usfirst.frc2783.loops;

import org.usfirst.frc2783.robot.FieldTransform;
import org.usfirst.frc2783.util.Bearing;
import org.usfirst.frc2783.util.NavSensor;
import org.usfirst.frc2783.util.Timestamp;
import org.usfirst.frc2783.vision.server.VisionUpdate;
import org.usfirst.frc2783.vision.server.VisionUpdateReceiver;
import org.usfirst.frc2783.vision.tracking.GyroTracker;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This function adds vision updates (from the Nexus smartphone) to a list in
 * RobotState. This helps keep track of goals detected by the vision system. The
 * code to determine the best goal to shoot at and prune old Goal tracks is in
 * GoalTracker.java
 * 
 * @author 254
 */
public class VisionProcessor implements Loop, VisionUpdateReceiver {
    static VisionProcessor instance_ = new VisionProcessor();
    VisionUpdate update_ = null;
    FieldTransform fieldTransform = FieldTransform.getInstance();
    
    public GyroTracker gyroHistory = new GyroTracker();

    public static VisionProcessor getInstance() {
        return instance_;
    }

    VisionProcessor() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onLoop() {
        VisionUpdate update;
        synchronized (this) {
            if (update_ == null) {
                return;
            }
            update = update_;
            update_ = null;
        }
        SmartDashboard.putString("DB/String 4", "Timestamp: " + Double.toString(Utility.getFPGATime() - update.getCapturedAtTimestamp()));
        
        gyroHistory.register(Timestamp.setNewTime(), new Bearing(-NavSensor.getInstance().getAngle(false)));
        
        fieldTransform.addVisionTargets(update.getTargets(), update.getCapturedAtTimestamp());
    }

    @Override
    public void onStop() {
        // no-op
    }

    @Override
    public synchronized void gotUpdate(VisionUpdate update) {
        update_ = update;
    }

}
