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
	
	public static final double kSwerveP = 0.001;
	public static final double kSwerveI = 0.001;
	public static final double kSwerveD = 0.001;
	
	public static final double kAngleToEncoderTick = 0.875;
	
	public static final double kEventDelay = 4;
	
	public static final int kAndroidAppTcpPort = 8254;
	
	public static final double cameraXOffset = 0.0;
	public static final double cameraYOffset = 0.0;
	public static final double cameraZOffset = 0.0;
	public static final double cameraYawOffset = 0.0;
	public static final double cameraPitchOffset = 0.0;
	public static final double cameraRollOffset = 0.0;
	
	public static final double goalHeight = 89.0;
	
	public static final int kDriverControllerId = 0;
	
	public static final int kFrontLeftWheelId = 5;
	public static final int kFrontRightWheelId = 1;
	public static final int kRearLeftWheelId = 4;
	public static final int kRearRightWheelId = 2;
	
	public static final int kFrontLeftSwivelId = 2;
	public static final int kFrontRightSwivelId = 0;
	public static final int kRearLeftSwivelId = 3;
	public static final int kRearRightSwivelId = 1;
	
	public static final int kFrontLeftEncoderA = 4;
	public static final int kFrontLeftEncoderB = 5;
	public static final int kFrontRightEncoderA = 0;
	public static final int kFrontRightEncoderB = 1;
	public static final int kRearLeftEncoderA = 6;
	public static final int kRearLeftEncoderB = 7;
	public static final int kRearRightEncoderA = 2;
	public static final int kRearRightEncoderB = 3;

}
