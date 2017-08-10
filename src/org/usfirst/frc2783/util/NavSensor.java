package org.usfirst.frc2783.util;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;

/**
 * Singleton class for getting the angle read by the gyro sensor mounted on the roborio
 *
 * @author 2783
 */
public class NavSensor {
	static NavSensor gyro = new NavSensor();
	
	public static NavSensor getInstance() {
		return gyro;
	}
	
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
    		return (navSensor.getAngle()+180.0)%360 - offset;
    	} else {
    		return navSensor.getAngle()%360 - offset;
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
