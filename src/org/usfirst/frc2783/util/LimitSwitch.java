package org.usfirst.frc2783.util;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.buttons.Button;

public class LimitSwitch extends Button {
	
	public AnalogInput limitSwitch;
	
	boolean reversed;
	
	public LimitSwitch(int id, boolean reversed){
		limitSwitch = new AnalogInput(id);
		this.reversed = reversed;
	}

	@Override
	public boolean get() {
		// TODO Auto-generated method stub
		if(reversed) {
			return !(limitSwitch.getValue() < 10);
		} else {
			return limitSwitch.getValue() < 10;
		}
	}

}
