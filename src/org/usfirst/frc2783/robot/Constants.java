package org.usfirst.frc2783.robot;

/**
 * For storing unchanging variables that can be accessed anywhere in the code.
 * 
 * @author 2783
 */
public class Constants {
	
	public static final double kPeriod = 0.01;
	
	public static final double kSwerveModP = 0.022;
	public static final double kSwerveModI = 0.04; 
	public static final double kSwerveModD = 0.025;
	
	public static final double kSwerveP = 0.04;
	public static final double kSwerveI = 0.00;
	public static final double kSwerveD = 0.1;
	
	public static final double kAngleToEncoderTick = 0.875;
	
	public static final double kEventDelay = 4;
	
	public static final int kAndroidAppTcpPort = 8254;
	
	public static final double cameraXOffset = 0.0;
	public static final double cameraYOffset = 0.0;
	public static final double cameraZOffset = 3.5;
	public static final double cameraPitchOffset = 20.0;
	public static final double cameraRollOffset = 0.0;
	public static final double cameraYawOffset = 90.0;
	
	public static final double goalHeight = 81.6;
	
	public static final int kDriverControllerId = 0;
	public static final int kGunnerControllerId = 1;
	
	public static final int kGearRollerId = 5;
	public static final int kGearHolderId = 4;
	
	public static final int kClimberMotorId = 4;
	
	public static final int kFrontLeftWheelId = 2;
	public static final int kFrontRightWheelId = 0;
	public static final int kRearLeftWheelId = 3;
	public static final int kRearRightWheelId = 1;
	
	public static final int kFrontLeftSwivelId = 2;
	public static final int kFrontRightSwivelId = 0;
	public static final int kRearLeftSwivelId = 3;
	public static final int kRearRightSwivelId = 1;
	
	public static final int kFrontLeftEncoderA = 6;
	public static final int kFrontLeftEncoderB = 7;
	public static final int kFrontRightEncoderA = 2;
	public static final int kFrontRightEncoderB = 3;
	public static final int kRearLeftEncoderA = 4;
	public static final int kRearLeftEncoderB = 5;
	public static final int kRearRightEncoderA = 0;
	public static final int kRearRightEncoderB = 1;

	

}
