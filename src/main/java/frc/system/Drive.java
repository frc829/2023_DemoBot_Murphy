package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Robot;
import frc.robot.Variables;
import frc.vision.Pixy.PixyPacket;

// Class that holds Drive functions
public class Drive {

	public static int precise = 0;
	public static long preciseTime;
	public static double startLocation, startAngle;
	
	public static enum DIRECTION {
		LEFT,
		RIGHT
	}
	
	// Set drive speed
	public static void setDriveSpeed(double leftSpeed, double rightSpeed) {
		
		double finalLeft = leftSpeed * Variables.DRIVE_LEFT_MODIFIER;
		double finalRight = -rightSpeed * Variables.DRIVE_RIGHT_MODIFIER;
		
		if(precise == 0) {
			finalLeft *= Variables.DRIVE_PRECISE_MODIFIER;
			finalRight *= Variables.DRIVE_PRECISE_MODIFIER;
		} else if(precise == 1) {
			finalLeft *= Variables.DRIVE_SORT_OF_PRECISE_MODIFIER;
			finalRight *= Variables.DRIVE_SORT_OF_PRECISE_MODIFIER;
		}
		
		RobotMap.driveBackLeft.set(ControlMode.PercentOutput, -finalLeft);
		RobotMap.driveFrontLeft.set(ControlMode.PercentOutput, finalLeft);
		RobotMap.driveBackRight.set(ControlMode.PercentOutput, finalRight);
		RobotMap.driveFrontRight.set(ControlMode.PercentOutput, finalRight);
		
	}
	
	// Toggle precise
	public static void togglePrecise() {
		if(System.currentTimeMillis() - preciseTime >= 500) {
			precise = (precise == 2) ? 0 : precise + 1;
			preciseTime = System.currentTimeMillis();
		}
	}
	
	public static boolean target() {
		PixyPacket packet = RobotMap.pixy.getPacket();
		int centerPoint = packet.x + (packet.width/2);
		System.out.println("Center Point: " + centerPoint);
		int dist = 200 - centerPoint;
		System.out.println("Distance: " + dist);
		int tolerance = 10;
		if(Math.abs(dist) > tolerance) {
			if(dist > 0) { turn(DIRECTION.LEFT, .225); System.out.println("Turning Left"); }
			if(dist < 0) { turn(DIRECTION.RIGHT, .225); System.out.println("Turning Right"); }
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean target(boolean val) {
		PixyPacket packet = RobotMap.pixy.getPacket();
		int centerPoint = packet.x + (packet.width/2);
		System.out.println("Center Point: " + centerPoint);
		int dist = 200 - centerPoint;
		System.out.println("Distance: " + dist);
		int tolerance = 10;
		if(Math.abs(dist) > tolerance) {
			if(dist > 0) { turn(DIRECTION.RIGHT, .225); System.out.println("Turning Right"); }
			if(dist < 0) { turn(DIRECTION.LEFT, .225); System.out.println("Turning Left"); }
			return false;
		} else {
			return true;
		}
	}
	
	// Drive time
	public static void driveTime(long time, double leftSpeed, double rightSpeed) {
		boolean going = true;
		long startTime = System.currentTimeMillis();
		while(going) {
			if(System.currentTimeMillis() - startTime < time)
				setDriveSpeed(leftSpeed, rightSpeed);
			else
				going = false;
		}
	}
	
	public static void setStart() {
		startLocation = NavX.getDisplacement("x");
		startAngle = NavX.getAngleRotation();
	}
	
	// Drive distance
	public static boolean driveDistance(double distance, double leftSpeed, double rightSpeed) {
		if(Math.abs(NavX.getDisplacement("x") - startLocation) >= distance) {
			return true;
		}
		else {
			System.out.println("Driving");
			System.out.println("Distance: " + (Math.abs(NavX.getDisplacement("x") - startLocation) * Variables.UNITS_PER_FEET + "ft"));
			setDriveSpeed(adjustDrive(leftSpeed, rightSpeed)[0], adjustDrive(leftSpeed, rightSpeed)[1]);
			return false;
		}
	}
	
	public static void driveStraight(double leftSpeed, double rightSpeed) {
		setDriveSpeed(adjustDrive(leftSpeed, rightSpeed)[0], adjustDrive(leftSpeed, rightSpeed)[1]);
	}
	
	// Drive straight
	public static double[] adjustDrive(double leftSpeed, double rightSpeed) {
		double[] ans = new double[2];
		ans[0] = leftSpeed;
		ans[1] = rightSpeed;
		if(NavX.getAngleRotation() < Robot.START_ANGLE) {
			ans[0] *= 1.20;
		} else if(NavX.getAngleRotation() > Robot.START_ANGLE) {
			ans[1] *= 1.20;
		}
		return ans;
	}
	
	// Drive to angle
	public static boolean driveToAngle(double angle, DIRECTION direction) {
		if(Math.abs(NavX.getAngleRotation() - Robot.START_ANGLE) <= angle) {
			setDriveSpeed((direction == DIRECTION.RIGHT) ? .65 : -.65, (direction == DIRECTION.LEFT) ? .65 : -.65);
			return false;
		}
		else {
			return true;
		}
	}
	
	// Drive to angle
	public static boolean driveToAngle(double angle, DIRECTION direction, boolean val) {
		if(Math.abs(NavX.getAngleRotation() - Robot.START_ANGLE) <= angle) {
			setDriveSpeed((direction == DIRECTION.RIGHT) ? .25 : -.25, (direction == DIRECTION.LEFT) ? .25 : -.25);
			return false;
		}
		else {
			return true;
		}
	}
	
	// Turn
	public static void turn(DIRECTION direction, double speed) {
		setDriveSpeed((direction == DIRECTION.RIGHT) ? speed : -speed, (direction == DIRECTION.RIGHT) ? -speed : speed);
	}
	
}
