package org.usfirst.frc2783.commands.autonomous.modes;

import java.util.ArrayList;

import org.usfirst.frc2783.robot.Constants;
import org.usfirst.frc2783.subystems.Subsystem;
import org.usfirst.frc2783.util.CrashTrackingRunnable;
import org.usfirst.frc2783.util.Logger;

import edu.wpi.first.wpilibj.Notifier;

public class ActionScheduler {
	
	public class Task {
		ArrayList<Action> queue;
		Action action;
		boolean active;
		
		Subsystem mSubsystem;
		
		public Task(Subsystem id) {
			mSubsystem = id;
		}
		
		public void setAction(Action a) {
			active = true;
			this.action = a;
		}
		
		public void queue(Action a) {
			active = true;
			queue.add(a);
		}
		
		public void removeLast() {
			queue.remove(0);
		}
		
		public boolean isActive() {
			return queue.isEmpty() && (action == null);
		}
		
		public ArrayList<Action> getQueue() {
			return queue;
		}
		
		public Action getAction() {
			return action;
		}
		
		public Subsystem getSubsystem() {
			return mSubsystem;
		}
	}
	
	Notifier thread;
	
	boolean active = false;
	
	ArrayList<Task> tasks;
	Action mStagedAction;
	
	
	public ActionScheduler() {
		tasks = new ArrayList<Task>();
		thread = new Notifier(new CrashTrackingRunnable(){
			
			@Override
			public void runCrashTracked() {
				ArrayList<Task> removeTasks = new ArrayList<Task>();
				for(Task t : tasks) {
					if(t.isActive()) {
						mStagedAction = t.getAction();
						t.getAction().perform();
						if(t.getAction().done()) {
							t.action.finish();
							Logger.info("Action " + t.getAction().getId() + " has finished and quit");
							
							if(!t.getQueue().isEmpty()) {
								t.setAction(t.getQueue().get(0));
								mStagedAction = t.getQueue().get(0);
								t.getQueue().get(0).start();
								t.getQueue().remove(0);
							} else {
								removeTasks.add(t);
							}
						}
						if(t.getAction().fail()) {
							Logger.error("Action " + t.getAction().getId() + " has failed and quit");
							
							if(!t.getQueue().isEmpty()) {
								t.setAction(t.getQueue().get(0));
								mStagedAction = t.getQueue().get(0);
								t.getQueue().get(0).start();
								t.getQueue().remove(0);
							} else {
								removeTasks.add(t);
							}
						}
					}
				}
				tasks.removeAll(removeTasks);
				if(!isActive()) {
					mStagedAction = null;
				}
			}
			
			@Override
			public void logCrash() {
				if(mStagedAction != null) {
					Logger.error("Exception caught in Actions: " + mStagedAction.getId());
				} else {
					Logger.error("Exception caught in Actions with no staged Action");
				}
			}
		});
	}
	
	public void setAction(Action action) {
		for(Task t : tasks) {
			if(action.getSubsystem() == t.getSubsystem()) {
				t.setAction(action);
				return;
			}
		}
		tasks.add(new Task(action.getSubsystem()));
		tasks.get(tasks.size()-1).setAction(action);
	}
	
	public void setGroup(ActionGroup group) {
		for(Action a : group.getActions()) {
			queue(a);
		}
	}
	
	public void queue(Action action) {
		if(action == null) {
			return;
		}
		for(Task t : tasks) {
			if(action.getSubsystem() == t.getSubsystem()) {
				t.queue(action);
				return;
			}
		}
		tasks.add(new Task(action.getSubsystem()));
		tasks.get(tasks.size()-1).queue(action);
		
	}
	
	public void start() {
		for(Task t : tasks) {
			if(!t.getQueue().isEmpty()) {
				t.setAction(t.getQueue().get(0));
				t.getQueue().remove(0);
			}
		}
		thread.startPeriodic(Constants.kAutoPeriod);
	}
	
	public void stop() {
		if(isActive()) {
			thread.stop();
		}
	}
	
	public boolean isTaskActive(Subsystem s) {
		for(Task t : tasks) {
			if(s == t.getSubsystem()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isActive() {
		return !tasks.isEmpty();
	}
}
