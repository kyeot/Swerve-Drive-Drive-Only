package org.usfirst.frc2783.commands.autonomous.modes;

import java.util.ArrayList;

public class ActionGroup {

	ArrayList<Action> actions = new ArrayList<Action>();
	
	public ActionGroup() {
		actions = new ArrayList<Action>();
	}
	
	protected void addAction(Action action) {
		actions.add(action);
	}
	
	public ArrayList<Action> getActions() {
		return actions;
	}
	
}
