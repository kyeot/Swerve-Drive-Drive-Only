package com.team2783.vision;

import com.team2783.vision.comm.CameraTargetInfo;
import com.team2783.vision.comm.RobotConnection;
import com.team2783.vision.comm.VisionUpdate;
import com.team2783.vision.comm.messages.TargetUpdateMessage;

import org.opencv.android.BetterCamera2Renderer;
import org.opencv.android.BetterCameraGLSurfaceView;

import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.SurfaceHolder;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class VisionTrackerGLSurfaceView extends BetterCameraGLSurfaceView implements BetterCameraGLSurfaceView.CameraTextureListener {

    static final String LOGTAG = "VTGLSurfaceView";
    protected int procMode = NativePart.DISP_MODE_TARGETS_PLUS;
    public static final String[] PROC_MODE_NAMES = new String[]{"Raw image", "Threshholded image", "Targets", "Targets plus"};
    protected int frameCounter;
    protected long lastNanoTime;
    TextView mFpsText = null;
    TextView mYvector = null;
    TextView mZvector = null;
    TextView mAngle = null;
    TextView mDist = null;
    private RobotConnection mRobotConnection;
    private Preferences m_prefs;

    static final int kHeight = 480;
    static final int kWidth = 640;
    static final double kCenterCol = ((double) kWidth) / 2.0 - .5;
    static final double kCenterRow = ((double) kHeight) / 2.0 - .5;
    static NativePart.TargetsInfo.Target target1;
    static NativePart.TargetsInfo.Target target2;
    static final double separationMin = 0;  //****ADD VALUE FOR THIS****
    static final double separationMax = 0;  //****ADD VALUE FOR THIS****
    static final double yDeltaMin = 0;      //****ADD VALUE FOR THIS****
    static final double yDeltaMax = 0;      //****ADD VALUE FOR THIS****

    static BetterCamera2Renderer.Settings getCameraSettings() {
        BetterCamera2Renderer.Settings settings = new BetterCamera2Renderer.Settings();
        settings.height = kHeight;
        settings.width = kWidth;
        settings.camera_settings = new HashMap<>();
        settings.camera_settings.put(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_OFF);
        settings.camera_settings.put(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE_OFF);
        settings.camera_settings.put(CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE, CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE_ON);
        settings.camera_settings.put(CaptureRequest.SENSOR_EXPOSURE_TIME, 1000000L);
        settings.camera_settings.put(CaptureRequest.LENS_FOCUS_DISTANCE, .2f);
        return settings;
    }

    public VisionTrackerGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs, getCameraSettings());
    }

    public void openOptionsMenu() {
        ((Activity) getContext()).openOptionsMenu();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
    }

    public void setProcessingMode(int newMode) {
        if (newMode >= 0 && newMode < PROC_MODE_NAMES.length)
            procMode = newMode;
        else
            Log.e(LOGTAG, "Ignoring invalid processing mode: " + newMode);
    }

    public int getProcessingMode() {
        return procMode;
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        ((Activity) getContext()).runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getContext(), "onCameraViewStarted", Toast.LENGTH_SHORT).show();
            }
        });
        // NativePart.initCL();
        frameCounter = 0;
        lastNanoTime = System.nanoTime();
    }

    @Override
    public void onCameraViewStopped() {
        ((Activity) getContext()).runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getContext(), "onCameraViewStopped", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCameraTexture(int texIn, int texOut, int width, int height, long image_timestamp) {
        Log.d(LOGTAG, "onCameraTexture - Timestamp " + image_timestamp + ", current time " + System.nanoTime() / 1E9);
        // FPS
        frameCounter++;
        if (frameCounter >= 30) {
            final int fps = (int) (frameCounter * 1e9 / (System.nanoTime() - lastNanoTime));
            Log.i(LOGTAG, "drawFrame() FPS: " + fps);
            if (mFpsText != null) {
                Runnable fpsUpdater = new Runnable() {
                    public void run() {
                        mFpsText.setText("FPS: " + fps);
                    }
                };
                new Handler(Looper.getMainLooper()).post(fpsUpdater);
            } else {
                Log.d(LOGTAG, "mFpsText == null");
                mFpsText = (TextView) ((Activity) getContext()).findViewById(R.id.fps_text_view);
            }
            frameCounter = 0;
            lastNanoTime = System.nanoTime();
        }
        NativePart.TargetsInfo targetsInfo = new NativePart.TargetsInfo();
        Pair<Integer, Integer> hRange = m_prefs != null ? m_prefs.getThresholdHRange() : blankPair();
        Pair<Integer, Integer> sRange = m_prefs != null ? m_prefs.getThresholdSRange() : blankPair();
        Pair<Integer, Integer> vRange = m_prefs != null ? m_prefs.getThresholdVRange() : blankPair();
        NativePart.processFrame(texIn, texOut, width, height, procMode, hRange.first, hRange.second,
                sRange.first, sRange.second, vRange.first, vRange.second, targetsInfo);

        VisionUpdate visionUpdate = new VisionUpdate(image_timestamp);
        Log.i(LOGTAG, "Num targets = " + targetsInfo.numTargets);
        double separation = 0;
        double yDelta = 0;
        double separatedCentroidX = 0;
        double separatedCentroidY = 0;
        boolean centroidValid = false;
        for (int x = 0; x < targetsInfo.numTargets; x++) {                      //sets target to be checked
            NativePart.TargetsInfo.Target target = targetsInfo.targets[x];
            for (int i = x + 1; i < targetsInfo.numTargets; i++) {                      //checks target against all other targets (excluding previous numbers that have already been checked with it)
                NativePart.TargetsInfo.Target targetArray = targetsInfo.targets[i];
                if (target.centroidX < targetArray.centroidX) {
                    separation = targetArray.centroidX - target.centroidX;
                    separatedCentroidX = target.centroidX + (separation / 2);            //maybe average centroid x values instead of adding half the separation to the lower one
                } else if (target.centroidX > targetArray.centroidX) {
                    separation = target.centroidX - targetArray.centroidX;
                    separatedCentroidX = targetArray.centroidX + (separation / 2);
                }
                Log.i(LOGTAG, "Distance between targets: " + separation);
                if (separation >= separationMin && separation <= separationMax) {
                    if (target.centroidY < targetArray.centroidY) {
                        yDelta = targetArray.centroidY - target.centroidY;
                    } else if (target.centroidY > targetArray.centroidY) {
                        yDelta = target.centroidY - targetArray.centroidY;
                    }
                    if (yDelta >= yDeltaMin && yDelta <= yDeltaMax) {
                        separatedCentroidY = (target.centroidY + targetArray.centroidY) / 2;      //centroid y value is the average of the two valid targets y value
                        centroidValid = true;
                        break;
                    }
                }
            }
            if (centroidValid){               //breaks the for loop when valid centroid for targets is found
                break;
            }
        }
                if (centroidValid) {            //makes sure that vector and robot transmission of centroid only happens when there is a valid centroid found
                    // Convert to a homogeneous 3d vector with x = 1
                    final double y = -(separatedCentroidX - kCenterCol) / getFocalLengthPixels();
                    final double z = (separatedCentroidY - kCenterRow) / getFocalLengthPixels();
                    Log.i(LOGTAG, "Target at: " + y + ", " + z);
                    visionUpdate.addCameraTargetInfo(
                            new CameraTargetInfo(y, z));

                    if (mYvector != null || mZvector != null) {
                        Runnable vectorUpdater = new Runnable() {
                            public void run() {
                                mYvector.setText("Y Vector: " + y);
                                mZvector.setText("Z Vector: " + z);
                            }
                        };
                        new Handler(Looper.getMainLooper()).post(vectorUpdater);
                    } else {
                        mYvector = (TextView) ((Activity) getContext()).findViewById(R.id.y_vector_textview);
                        mZvector = (TextView) ((Activity) getContext()).findViewById(R.id.z_vector_textview);
                    }
                    if (mRobotConnection != null) {
                        TargetUpdateMessage update = new TargetUpdateMessage(visionUpdate, System.nanoTime());
                        mRobotConnection.send(update);
                    }
                }
        return true;
    }

    public void setRobotConnection(RobotConnection robotConnection) {
        mRobotConnection = robotConnection;
    }

    public void setPreferences(Preferences prefs) {
        m_prefs = prefs;
    }

    private static Pair<Integer, Integer> blankPair() {
        return new Pair<Integer, Integer>(0, 255);
    }
}
