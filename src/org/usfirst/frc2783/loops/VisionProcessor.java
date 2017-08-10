package org.usfirst.frc2783.loops;

import org.usfirst.frc2783.util.Logger;
import org.usfirst.frc2783.robot.FieldTransform;
import org.usfirst.frc2783.vision.VisionUpdate;
import org.usfirst.frc2783.vision.VisionUpdateReceiver;

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
        fieldTransform.addVisionTargets(update.getTargets());
    }

    @Override
    public void onStop() {
        // no-op
    }

    @Override
    public synchronized void gotUpdate(VisionUpdate update) {
    	Logger.log("INFO", "gotUpdate");
        update_ = update;
    }

}
