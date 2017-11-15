package org.usfirst.frc2783.util;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.usfirst.frc2783.robot.Constants;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Utility;

/**
 * Singleton class for getting the angle read by the gyro sensor mounted on the roborio,
 * can also store a history of poses in a Map class
 *
 * @author 2783
 */
public class NavSensor {
	static NavSensor gyro = new NavSensor();
	
	public static NavSensor getInstance() {
		return gyro;
	}
	
	Map<Double, Bearing> history = new TreeMap<Double, Bearing>();
	
	NavSensor() {
		try {
	         navSensor = new AHRS(SPI.Port.kMXP);
	     } catch (RuntimeException ex ) {
	         DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
	     }
	}
	
	AHRS navSensor;
	double offset = 0;
	
	public double getAngle(boolean reversed) {
    	if(reversed) {
    		return 360-((((navSensor.getAngle()+180.0)%360)+360)%360);
    	} else {
    		return 360-(((navSensor.getAngle()%360)+360)%360);
    	}
    }
	
	public double getRawAngle() {
		return navSensor.getAngle();
	}
	
	public void resetGyroNorth(double angle, double north) {
    	navSensor.reset();
    	offset = angle - north;
    }

}
