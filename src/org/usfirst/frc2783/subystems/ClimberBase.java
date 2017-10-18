package org.usfirst.frc2783.subystems;

import org.usfirst.frc2783.commands.ClimberDrive;
import org.usfirst.frc2783.robot.Constants;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimberBase extends Subsystem {
	
	VictorSP climberMotor;
	
	public ClimberBase(){
		climberMotor = new VictorSP(Constants.kClimberMotorId);
		
	}
	
	public void climb(double speed) {
		climberMotor.set(speed);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	setDefaultCommand(new ClimberDrive());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

