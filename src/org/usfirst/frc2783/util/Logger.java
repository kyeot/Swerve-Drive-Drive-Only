package org.usfirst.frc2783.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.UUID;

import org.usfirst.frc2783.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;

public class Logger {
	
	public static void log(String msg) {
		try (PrintWriter writer = new PrintWriter(new FileWriter("/home/lvuser/log.txt", true))) {
            writer.print(msg);
            writer.print(", ");
            writer.print("[" + new Date().toString() + "]");
            writer.print(", ");
            writer.print("[" + Robot.parseMatchTime() + "]");

            writer.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
