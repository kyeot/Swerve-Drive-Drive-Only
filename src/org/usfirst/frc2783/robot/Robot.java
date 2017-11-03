package org.usfirst.frc2783.robot;

import java.io.File;
import java.io.IOException;

import org.usfirst.frc2783.commands.autonomous.LeftGear;
import org.usfirst.frc2783.loops.LogData;
import org.usfirst.frc2783.loops.Looper;
import org.usfirst.frc2783.loops.VisionProcessor;
import org.usfirst.frc2783.subystems.ActiveGearBase;
import org.usfirst.frc2783.subystems.ClimberBase;
import org.usfirst.frc2783.subystems.SwerveDriveBase;
import org.usfirst.frc2783.util.LimitSwitch;
import org.usfirst.frc2783.util.Logger;
import org.usfirst.frc2783.vision.VisionServer;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Robot extends IterativeRobot {

    public static OI oi;
    public static Looper looper = new Looper();
    
    VisionServer mVisionServer = VisionServer.getInstance();
   
	public static Command autonomous;
    
    public static SwerveDriveBase swerveBase = new SwerveDriveBase();
    public static ActiveGearBase activeGearBase = new ActiveGearBase();
    public static ClimberBase climberBase = new ClimberBase();
    
    public static LimitSwitch gearChecker = new LimitSwitch(1, false);
	public static LimitSwitch holderPos = new LimitSwitch(0, false);
	public static LimitSwitch[] limitSwitches = new LimitSwitch[]{gearChecker, holderPos};

	public static NetworkTable smartDashTable;
    
    public void robotInit() {
        oi = new OI();
        
        mVisionServer.addVisionUpdateReceiver(VisionProcessor.getInstance());
        
        looper.addLoop(new LogData());
        looper.addLoop(VisionProcessor.getInstance());
        Logger.info("Starting Loops");
        looper.startLoops();
        
    	this.smartDashTable = NetworkTable.getTable("SmartDashboard");
		//this.visionControl = NetworkTable.getTable("Usage");
		
		String[] autonomousList = {"Left Side Gear"};
        this.smartDashTable.putStringArray("Auto List", autonomousList);
        
        File logFile = new File("/home/lvuser/log.txt");
        try {
			logFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void disabledInit(){
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
    	Logger.info("Starting Autonomous");
    	
	String autoSelected = SmartDashboard.getString("Auto Selector", "None");
    	
		switch(autoSelected) {
			case "Left Side Gear":
				autonomous = new LeftGear();
				break;
			case "None":
			default:
				autonomous = null;
				break;
		} 
		
    	if(autonomous != null) {
    		autonomous.start();
    	}
    }
    
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	Logger.info("Starting Teleop");
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    public void testPeriodic() {
        LiveWindow.run();
    }
    
    public static String parseMatchTime() {
    	double s = DriverStation.getInstance().getMatchTime();
    	
    	if(s != -1.0) {
	    	if(DriverStation.getInstance().isAutonomous()) {
	    		int t = (int) (15-Math.ceil(s));
	    		return ":" + Integer.toString((int) t) + " (Auton)";
	    	} else if(DriverStation.getInstance().isOperatorControl()) {
	    		int t = (int) (135-Math.ceil(s));
	    		return Integer.toString((int) Math.floor(t/60)) + ":" + Integer.toString((int) t%60) + " (TeleOp)";
	    	} else {
	    		return "Disabled";
	    	}
    	} else {
    		return "Not Practice";
    	}
    	
    }
    
}
