package org.usfirst.frc2783.subsystems;

import org.usfirst.frc2783.robot.Constants;
import org.usfirst.frc2783.robot.Robot;
import org.usfirst.frc2783.util.Bearing;
import org.usfirst.frc2783.util.NavSensor;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Singleton class which can control swerve from anywhere in the code.
 * 
 * @author 2783
 */
public class SwerveController {
	static SwerveController swerveCont = new SwerveController();
	
	public static SwerveController getInstance(){
		return swerveCont;
	}
	
	class SwervePoseOut implements PIDOutput {
		@Override
		public void pidWrite(double output) {
			rot = output;
		}
	}
	
	class GyroSource implements PIDSource {
		PIDSourceType sourceType;
		
		public GyroSource() {
			setPIDSourceType(PIDSourceType.kDisplacement);
		}

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			sourceType = pidSource;
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return sourceType;
		}

		@Override
		public double pidGet() {
			return gyro.getAngle(true);
		}
		
	}
	
	NavSensor gyro = NavSensor.getInstance();
	
	double fb;
	double rl;
	double rot;
	
	PIDController posePid;
	SwervePoseOut posePidOut;
	GyroSource posePidSource;
	
	SwerveController(){
		posePidOut = new SwervePoseOut();
		posePidSource = new GyroSource();
		posePid = new PIDController(Constants.kSwerveP, Constants.kSwerveI, Constants.kSwerveD, 
										posePidSource, 
										posePidOut);
	}
	
	public void slide(double fb, double rl) {
		this.fb = fb;
		this.rl = rl;
	}
	
	public void rotate(double rot) {
		posePid.disable();
		this.rot = rot;
	}
	
	public void move(double fb, double rl, double rot) {
		slide(fb, rl);
		rotate(rot);
	}
	
	public void setPose(Bearing b) {
		posePid.setSetpoint(b.getTheta());
		posePid.enable();
	}
	
	public void update(boolean fieldOriented) {
		Robot.swerveBase.swerveDrive(fb, rl, rot, fieldOriented);
	}
}
