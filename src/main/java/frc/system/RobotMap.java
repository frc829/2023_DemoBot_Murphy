package frc.system;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import frc.vision.Pixy;

public class RobotMap {
	
	// Numerical values for CAN Talons
	private static final int DRIVE_BACK_LEFT = 10;
	private static final int DRIVE_FRONT_LEFT = 11;
	private static final int DRIVE_BACK_RIGHT = 12;
	private static final int DRIVE_FRONT_RIGHT = 13;
	private static final int GEAR_PIVOT = 16;
	private static final int GEAR_ROLLER = 15;
	private static final int SHOOTER_SHOOTER = 14;
	private static final int SHOOTER_CENTRIFUGE = 17;
	
	// Numerical values for Talons
	private static final int CLIMB_CLIMB_A = 1;
	private static final int CLIMB_CLIMB_B = 2;
	private static final int SHOOTER_SINGULATOR = 0;
	
	// Numerical values for Relays
	private static final int SHOOTER_LIGHT = 0;
	
	public static final PowerDistribution pdp = new PowerDistribution(0, ModuleType.kCTRE);
	
	// NavX-MXP
	//public static AHRS navX;
	
	// Webcam
	//public static WebCam webCam;
	
	// Pixy
	public static Pixy pixy;
	
	// Climb
	public static Talon climbClimbA, climbClimbB;
	
	// Drive
	public static TalonSRX driveBackLeft, driveFrontLeft, driveBackRight, driveFrontRight;
	
	// Gear
	public static TalonSRX gearPivot, gearRoller;
	
	// Shooter
	public static TalonSRX shooterShooter, shooterCentrifuge;
	public static Talon shooterSingulator;
	public static Relay shooterLight;
	
	public static void setup() {
		
		navXInit();
		pixySetup();
		webCamInit();
		climbInit();
		driveInit();
		gearInit();
		shooterInit();
		
	}
	
	public static void navXInit() {
		
		// try {
		// 	navX = new AHRS(SerialPort.Port.kUSB);
		// 	navX.zeroYaw();
		// } catch(Exception e) {
		// 	DriverStation.reportError("NavX Error: " + e.getMessage(), true);
		// }
		
	}
	
	public static void pixySetup() {
		
		try {
			pixy = new Pixy();
		} catch(Exception e) {
			DriverStation.reportError("Pixy Error: " + e.getMessage(), true);
		}
		
	}
	
	public static void webCamInit() {
		
		// try {
		// 	webCam = new WebCam();
		// } catch(Exception e) {
		// 	DriverStation.reportError("WebCam Error: " + e.getMessage(), true);
		// }
		
	}
	
	public static void climbInit() {
		
		try {
			climbClimbA = new Talon(CLIMB_CLIMB_A);
			climbClimbB = new Talon(CLIMB_CLIMB_B);
		} catch(Exception e) {
			DriverStation.reportError("Climb Error: " + e.getMessage(), true);
		}
		
	}
	
	public static void driveInit() {
		
		try {
			driveBackLeft = new TalonSRX(DRIVE_BACK_LEFT);
			driveBackLeft.setInverted(true);
			driveFrontLeft = new TalonSRX(DRIVE_FRONT_LEFT);
			driveBackRight = new TalonSRX(DRIVE_BACK_RIGHT);
			driveFrontRight = new TalonSRX(DRIVE_FRONT_RIGHT);
			Drive.preciseTime = System.currentTimeMillis();
			driveBackLeft.setInverted(true);
		} catch(Exception e) {
			DriverStation.reportError("Drive Error: " + e.getMessage(), true);
		}
		
	}
	
	public static void gearInit() {
		
		try {
			gearPivot = new TalonSRX(GEAR_PIVOT);
			gearPivot.setInverted(true);
			gearRoller = new TalonSRX(GEAR_ROLLER);
		} catch(Exception e) {
			DriverStation.reportError("Gear Error: " + e.getMessage(), true);
		}
		
	}
	
	public static void shooterInit() {
		
		try {
			shooterCentrifuge = new TalonSRX(SHOOTER_CENTRIFUGE);
			shooterShooter = new TalonSRX(SHOOTER_SHOOTER);
			shooterShooter.setInverted(true);
			shooterSingulator = new Talon(SHOOTER_SINGULATOR);
			shooterSingulator.setInverted(true);
			shooterLight = new Relay(SHOOTER_LIGHT);
			Shooter.lightTime = System.currentTimeMillis();
		} catch(Exception e) {
			DriverStation.reportError("Shooter Error: " + e.getMessage(), true);
		}
		
	}
	
}
