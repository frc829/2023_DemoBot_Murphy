package frc.controller;

import edu.wpi.first.wpilibj.Joystick;

// Controller wrapper for Logitech F310 Gamepad
public class LogitechController extends Joystick {

	// Numerical values for axes
	private final int LEFT_X_AXIS = 0;
	private final int LEFT_Y_AXIS = 1;
	private final int LEFT_TRIGGER_AXIS = 2;
	private final int RIGHT_TRIGGER_AXIS = 3;
	private final int RIGHT_X_AXIS = 4;
	private final int RIGHT_Y_AXIS = 5;
	
	// Numerical values for buttons
	private final int A_BUTTON = 2;
	private final int B_BUTTON = 1;
	private final int Y_BUTTON = 4;
	private final int X_BUTTON = 3;
	private final int LEFT_BUMPER = 5;
	private final int RIGHT_BUMPER = 6;
	private final int SELECT_BUTTON = 7;
	private final int START_BUTTON = 8;
	private final int LEFT_BUTTON = 9;
	private final int RIGHT_BUTTON = 10;
	
	// Create Object
	public LogitechController(int port) {
		super(port);
	}
	
	// Get specific axis
	public double getAxisValue(String axis) {
		
		switch(axis) {
		case "leftx": return getRawAxis(LEFT_X_AXIS);
		case "lefty": return getRawAxis(LEFT_Y_AXIS);
		case "lefttrigger": return getRawAxis(LEFT_TRIGGER_AXIS);
		case "righttrigger": return getRawAxis(RIGHT_TRIGGER_AXIS);
		case "rightx": return getRawAxis(RIGHT_X_AXIS);
		case "righty": return getRawAxis(RIGHT_Y_AXIS);
		}
		
		return 0;
		
	}
	
	// Check button states
	public boolean getAButton() {
		return getRawButton(A_BUTTON);
	}
	
	public boolean getBButton() {
		return getRawButton(B_BUTTON);
	}
	
	public boolean getXButton() {
		return getRawButton(X_BUTTON);
	}
	
	public boolean getYButton() {
		return getRawButton(Y_BUTTON);
	}
	
	public boolean getLeftBumper() {
		return getRawButton(LEFT_BUMPER);
	}
	
	public boolean getRightBumper() {
		return getRawButton(RIGHT_BUMPER);
	}
	
	public boolean getSelectButton() {
		return getRawButton(SELECT_BUTTON);
	}
	
	public boolean getStartButton() {
		return getRawButton(START_BUTTON);
	}
	
	public boolean getLeftJoyButton() {
		return getRawButton(LEFT_BUTTON);
	}
	
	public boolean getRightJoyButton() {
		return getRawButton(RIGHT_BUTTON);
	}
	
}
