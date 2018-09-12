package org.usfirst.frc2783.robot;

import java.io.File;
import java.io.IOException;

import org.usfirst.frc2783.loops.LogData;
import org.usfirst.frc2783.loops.Looper;
import org.usfirst.frc2783.loops.VisionProcessor;
import org.usfirst.frc2783.subystems.SwerveDriveBase;
import org.usfirst.frc2783.util.Logger;
import org.usfirst.frc2783.util.NavSensor;
import org.usfirst.frc2783.vision.server.VisionServer;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Robot extends IterativeRobot {

    public static OI oi;
    public static Looper looper = new Looper();
    
    VisionServer mVisionServer = VisionServer.getInstance();
    
    public static SwerveDriveBase swerveBase = new SwerveDriveBase();
    
    public void robotInit() {
        oi = new OI();
        
        mVisionServer.addVisionUpdateReceiver(VisionProcessor.getInstance());
        
        looper.addLoop(new LogData());
        looper.addLoop(VisionProcessor.getInstance());
        Logger.info("Starting Loops");
        looper.startLoops();
        
        NavSensor.getInstance().updateHistory();
        
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
