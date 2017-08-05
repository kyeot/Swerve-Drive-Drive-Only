package org.usfirst.frc2783.robot;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc2783.util.Bearing;
import org.usfirst.frc2783.util.Transform;
import org.usfirst.frc2783.util.Vector;
import org.usfirst.frc2783.vision.TargetInfo;

public class FieldTransform {
	public static FieldTransform fieldTransform = new FieldTransform();
	
	public static FieldTransform getInstance() {
		return fieldTransform;
	}
	
	List<TargetInfo> targets;
	
	Vector cameraToRobot;
	Bearing camYaw;
	Bearing camPitch;
	double camHeight;
	double camToGoal;
	
	FieldTransform() {
		cameraToRobot = new Vector(Constants.cameraXOffset, 
										Constants.cameraYOffset);
		camYaw = new Bearing(Constants.cameraYawOffset);
		camPitch = new Bearing(Constants.cameraPitchOffset);
		camHeight = Constants.cameraZOffset;
		camToGoal = Constants.goalHeight - camHeight;
	}
	
	public Transform getRobotPose() {
		return new Transform(0.0,0.0,0.0);
	}
	
	public Vector getFieldToCamera() {
		return getRobotPose().getTranslation().add(cameraToRobot);
	}
	
	public List<Vector> getRobotToTargets() {
		List<Vector> v = new ArrayList<Vector>();
		if(!targets.isEmpty()) {
			for(TargetInfo t : targets) {
                // Compensate for camera yaw
                double xyaw = t.getX() * camYaw.cos() + t.getY() * camYaw.sin();
                double yyaw = t.getY() * camYaw.cos() - t.getX() * camYaw.sin();
                double zyaw = t.getZ();

                // Compensate for camera pitch
                double xr = zyaw * camPitch.sin() + xyaw * camPitch.cos();
                double yr = yyaw;
                double zr = zyaw * camPitch.cos() - xyaw * camPitch.sin();

                // find intersection with the goal
                if (zr > 0) {
                    double scaling =  camToGoal / zr;
                    double distance = Math.hypot(xr, yr) * scaling;
                    Bearing angle = new Bearing(new Vector(xr, yr));
                    v.add(getFieldToCamera()
                            .add(new Vector(distance * angle.cos(), distance * angle.sin())));
                }
			}
		}
		return v;
	}
	
	public void addVisionTargets(List<TargetInfo> t) {
		targets = t;
	}

}
