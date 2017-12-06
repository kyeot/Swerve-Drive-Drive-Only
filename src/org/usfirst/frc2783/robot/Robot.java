package org.usfirst.frc2783.robot;

import java.io.File;
import java.io.IOException;

import org.usfirst.frc2783.commands.autonomous.modes.ActionScheduler;
import org.usfirst.frc2783.loops.LogData;
import org.usfirst.frc2783.loops.Looper;
import org.usfirst.frc2783.loops.VisionProcessor;
import org.usfirst.frc2783.subystems.Superstructure;
import org.usfirst.frc2783.subystems.SwerveDriveBase;
import org.usfirst.frc2783.util.Logger;
import org.usfirst.frc2783.util.NavSensor;
import org.usfirst.frc2783.vision.server.VisionServer;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
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
    
    static VisionServer mVisionServer = VisionServer.getInstance();
    static Controls mControls = Controls.getInstance();
    static Superstructure mSuperstructure = Superstructure.getInstance();
    
    NetworkTable smartDashTable;
    
	public static ActionScheduler autoScheduler;
    
    public static SwerveDriveBase swerveBase = new SwerveDriveBase();
    public static PowerDistributionPanel pdp = new PowerDistributionPanel();
    
    public void robotInit() {
        oi = new OI();
        
        mVisionServer.addVisionUpdateReceiver(VisionProcessor.getInstance());
        
        NavSensor.getInstance().resetGyroNorth(180, 0);
        
        looper.addLoop(new LogData());
        looper.addLoop(VisionProcessor.getInstance());
        Logger.info("Starting Loops");
        looper.startLoops();
        
        NavSensor.getInstance().updateHistory();

        autoScheduler = new ActionScheduler();
        
    	this.smartDashTable = NetworkTable.getTable("SmartDashboard");
		//this.visionControl = NetworkTable.getTable("Usage");
		
		String[] autonomousList = {"Drive Forward", "Right Gear"};
        this.smartDashTable.putStringArray("Auto List", autonomousList);
        
        File logFile = new File("/home/lvuser/log.txt");
        try {
			logFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void disabledInit(){
    	autoScheduler.stop();
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
    	Logger.info("Starting Autonomous");
    	
    	String autoSelected = SmartDashboard.getString("Auto Selector", "None");
    	
		switch(autoSelected) {
			case "Drive Forward":
//				autoScheduler.setGroup(new DriveForward());
				break;
			case "Right Gear":
//				autoScheduler.setGroup(new RightGear());
				break;
				
		} 
		autoScheduler.start();
    }
    
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	autoScheduler.stop();
    	Logger.info("Starting Teleop");
    }

    public void teleopPeriodic() {
    	try {
    		mSuperstructure.setOpenLoop(mControls.getArmRate(), mControls.getRollerRate());
    		SmartDashboard.putString("DB/String 0", "" + pdp.getCurrent(5));
    		SmartDashboard.putString("DB/String 1", "" + pdp.getCurrent(15));
    		
    	} catch(Throwable t) {
    		Logger.error("Exception caught in Control Loop");
    		throw(t);
    	}
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
