package org.usfirst.frc2783.vision;

/**
 * A basic interface for classes that get VisionUpdates. Classes that implement
 * this interface specify what to do when VisionUpdates are received.
 * 
 * @author 254
 */
public interface VisionUpdateReceiver {
    void gotUpdate(VisionUpdate update);
}