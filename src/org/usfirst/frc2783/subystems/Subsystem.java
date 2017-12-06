package org.usfirst.frc2783.subystems;

import org.usfirst.frc2783.commands.autonomous.modes.Action;

public interface Subsystem {
	
	public void updateState();

	public Action getStagedAction();
	
	public boolean isBusy();
	
	public String getId();
	
}
