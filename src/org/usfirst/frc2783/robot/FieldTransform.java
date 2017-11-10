package org.usfirst.frc2783.robot;

import java.util.List;

import org.usfirst.frc2783.loops.VisionProcessor;
import org.usfirst.frc2783.util.Bearing;
import org.usfirst.frc2783.util.NavSensor;
import org.usfirst.frc2783.util.Timestamp;
import org.usfirst.frc2783.util.Transform;
import org.usfirst.frc2783.util.Vector;
import org.usfirst.frc2783.vision.server.TargetInfo;
import org.usfirst.frc2783.vision.tracking.GyroTracker;
import org.usfirst.frc2783.vision.tracking.TargetTracker;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Singleton class used to get useful Transformations of things tracked on the field,
 *  i.e. the robot or vision targets
 * 
 * @author 2783
 */
public class FieldTransform {
	public static FieldTransform fieldTransform = new FieldTransform();
	
	public static FieldTransform getInstance() {
		return fieldTransform;
	}
	
	NavSensor gyro = NavSensor.getInstance();
	
	List<TargetInfo> targets;
	double targetsTimestamp;
	
	TargetTracker targetHistory;
	GyroTracker gyroHistory;
	
	Transform cameraToRobot;
	Bearing camYaw;
	Bearing camPitch;
	double camHeight;
	double camToGoal;
	
	FieldTransform() {
		cameraToRobot = new Transform(Constants.kCameraXOffset, 
										Constants.kCameraYOffset,
										Constants.kCameraYawOffset);
		camPitch = new Bearing(-Constants.kCameraPitchOffset);
		camHeight = Constants.kCameraZOffset;
		camToGoal = Constants.kGoalHeight - camHeight;
		
		targetHistory = new TargetTracker();
		gyroHistory = new GyroTracker();
	}
	
	public Transform getRobotPose(Timestamp t) {
		return new Transform(0.0,0.0,VisionProcessor.getInstance().gyroHistory.getAngleAtTime(t).getTheta());
	}
	
	public Transform getFieldToCamera(Timestamp t) {
		return getRobotPose(t).transform(new Transform(cameraToRobot.getTranslation().rotateBy(getRobotPose(t).getRotation()), cameraToRobot.getRotation()));
	}
	
	public void trackLatestTarget() {
		if(!targets.isEmpty()) {
			TargetInfo t = targets.get(0);
			double x = t.getX();
			double y = t.getY();
			double z = t.getZ();
			
			//Rotate target direction to compensate for camera pitch (rotation matrix)
			double xr = z * camPitch.sin() + x * camPitch.cos();
            double yr = y;
            double zr = z * camPitch.cos() - x * camPitch.sin();
            
            if(zr > 0) {
            	double s = camToGoal / zr;
            	double dist = Math.hypot(xr, yr) * s;
            	Bearing angle = new Bearing(new Vector(xr, yr));
            	Vector targetToCam = new Vector(angle.cos()*dist, angle.sin()*dist);
            	
            	Timestamp time = new Timestamp(targetsTimestamp);
            	targetHistory.register(time, getFieldToCamera(time).getTranslation().translate(targetToCam).rotateBy(getFieldToCamera(time).getRotation()));
            	
            	SmartDashboard.putString("DB/String 0", "Angle to Robot: " + Math.floor(targetHistory.getLatestTarget().dir().getTheta()));
			}
		}
	}
	
	public void addVisionTargets(List<TargetInfo> t, double time) {
		targets = t;
		targetsTimestamp = time;
	}

}
