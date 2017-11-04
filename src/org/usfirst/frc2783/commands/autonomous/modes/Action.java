package org.usfirst.frc2783.commands.autonomous.modes;

public interface Action {
	
	public void start();
	
	public void perform();
	
	public boolean done();
	
	public void finish();
	
	public boolean fail();

	public String getId();
	
}
