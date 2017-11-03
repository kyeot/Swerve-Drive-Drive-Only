package org.usfirst.frc2783.robot;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc2783.util.Bearing;
import org.usfirst.frc2783.util.NavSensor;
import org.usfirst.frc2783.util.Transform;
import org.usfirst.frc2783.util.Vector;
import org.usfirst.frc2783.vision.TargetInfo;

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
	
	Transform cameraToRobot;
	Bearing camYaw;
	Bearing camPitch;
	double camHeight;
	double camToGoal;
	
	FieldTransform() {
		cameraToRobot = new Transform(Constants.cameraXOffset, 
										Constants.cameraYOffset,
										Constants.cameraYawOffset);
		camPitch = new Bearing(-Constants.cameraPitchOffset);
		camHeight = Constants.cameraZOffset;
		camToGoal = Constants.goalHeight - camHeight;
	}
	
	public Transform getRobotPose() {
		return new Transform(0.0,0.0,-gyro.getAngle(false));
	}
	
	public Transform getFieldToCamera() {
		return getRobotPose().transform(cameraToRobot);
	}
	
	public List<Vector> getFieldToTargets() {
		List<Vector> v = new ArrayList<Vector>();
		if(!targets.isEmpty()) {
			for(TargetInfo t : targets) {
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
                	SmartDashboard.putString("DB/String 0", Double.toString(dist));
                	Bearing angle = new Bearing(new Vector(xr, yr));
                	Vector targetToCam = new Vector(angle.cos()*dist, angle.sin()*dist);
                	SmartDashboard.putString("DB/String 1", Double.toString(new Bearing(targetToCam).getTheta()));
                	v.add(getFieldToCamera().getTranslation().translate(targetToCam.rotateBy(cameraToRobot.getRotation())).rotateBy(getRobotPose().getRotation()));
                	
                	
        			SmartDashboard.putString("DB/String 5", "Angle to Target: " + Double.toString(Math.floor(getFieldToCamera().getTranslation().translate(targetToCam.rotateBy(cameraToRobot.getRotation())).rotateBy(getRobotPose().getRotation()).dir().getTheta())));
        			SmartDashboard.putString("DB/String 6", "Angle to Gyro: " + Double.toString(Math.floor(getRobotPose().getRotation().getTheta())));
                }
			}
		}
		return v;
	}
	
	public void addVisionTargets(List<TargetInfo> t) {
		targets = t;
	}

}
