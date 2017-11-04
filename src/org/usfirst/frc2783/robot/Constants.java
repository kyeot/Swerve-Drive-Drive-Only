package org.usfirst.frc2783.robot;

/**
 * For storing unchanging variables that can be accessed anywhere in the code.
 * 
 * @author 2783
 */
public class Constants {
	
	public static final double kPeriod = 0.01;
	public static final double kAutoPeriod = 0.01;
	
	public static final double kSwerveModP = 0.022;
	public static final double kSwerveModI = 0.04; 
	public static final double kSwerveModD = 0.025;
	
	public static final double kSwerveP = 0.04;
	public static final double kSwerveI = 0.00;
	public static final double kSwerveD = 0.15;
	
	public static final double kAngleToEncoderTick = 0.875;
	
	public static final double kEventDelay = 4;
	
	public static final int kAndroidAppTcpPort = 8254;
	
	public static final double kCameraFrameRate = 30;
	public static final double kTargetMaxAge = 0.3; //seconds
	
	public static final double kGyroMaxAge = 0.4;
	
	public static final double kCameraXOffset = 13;
	public static final double kCameraYOffset = 0.25;
	public static final double kCameraZOffset = 3;
	public static final double kCameraPitchOffset = 0.0;
	public static final double kCameraRollOffset = 0.0;
	public static final double kCameraYawOffset = 0.0;
	
	public static final double kGoalHeight = 8;
	
	public static final int kDriverControllerId = 0;
	
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
