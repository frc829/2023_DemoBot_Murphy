package frc.robot;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.autonomous.Auto;
import frc.autonomous.CenterGearAuto;
import frc.autonomous.DoNothingAuto;
import frc.autonomous.DriveForwardAuto;
import frc.autonomous.ShootDriveAuto;
import frc.autonomous.ShootGearAuto;
import frc.autonomous.ShootTurnDriveAuto;
import frc.autonomous.SideGearAuto;
import frc.controller.LogitechController;
import frc.system.Climb;
import frc.system.Drive;
import frc.system.Gear;
import frc.system.RobotMap;
import frc.system.Shooter;
import frc.system.Drive.DIRECTION;

public class Robot extends TimedRobot {

	// Controllers for the drivers
	LogitechController driver, operator;

	// Starting angle
	public static double START_ANGLE;

	// Auto Chooser
	SendableChooser<Auto> autoChooser = new SendableChooser<Auto>();

	public void robotInit() {

		// Setup Robot systems
		RobotMap.setup();

		// Create controllers
		driver = new LogitechController(0);
		operator = new LogitechController(1);

		// SmartDashboard
		//RobotMap.webCam.startCapture();
		addAutos();

		// Reset NavX
		// RobotMap.navX.reset();
		// RobotMap.navX.zeroYaw();
		// RobotMap.navX.resetDisplacement();

		RobotMap.shooterLight.set(Relay.Value.kForward);

		// Set starting angle
		//START_ANGLE = RobotMap.navX.getAngle();

	}

	public void addAutos() {

		// Add and display autos
		autoChooser.addOption("Place Center Gear", new CenterGearAuto());
		autoChooser.addOption("Shoot Drive Blue", new ShootDriveAuto(Alliance.Blue));
		autoChooser.addOption("Shoot Drive Red", new ShootDriveAuto(Alliance.Red));
		autoChooser.addOption("Shoot Gear Blue", new ShootGearAuto(Alliance.Blue));
		autoChooser.addOption("Shoot Gear Red", new ShootGearAuto(Alliance.Red));
		autoChooser.addOption("Shoot Turn Red", new ShootTurnDriveAuto(Alliance.Red));
		autoChooser.addOption("Shoot Turn Blue", new ShootTurnDriveAuto(Alliance.Blue));
		autoChooser.addOption("Place Side Gear Left", new SideGearAuto(DIRECTION.LEFT));
		autoChooser.addOption("Place Side Gear Right", new SideGearAuto(DIRECTION.RIGHT));
		autoChooser.addOption("Drive Forward", new DriveForwardAuto());
		autoChooser.addOption("Do Nothing", new DoNothingAuto());
		SmartDashboard.putData("Auto Chooser", autoChooser);

	}

	public void autonomousInit() {

		// SmartDashboard
		SmartDashboard.putData("Auto Chooser", autoChooser);

		// Reset again to be safe
		// RobotMap.navX.reset();
		// RobotMap.navX.zeroYaw();
		// RobotMap.navX.resetDisplacement();

	}

	public void autonomousPeriodic() {

		// SmartDashboard
		SmartDashboard.putData("Auto Chooser", autoChooser);

		// Run selected Auto
		autoChooser.getSelected().execute();

	}

	public void teleopInit() {
		Shooter.stopCentrifuge();
		Shooter.stopShooter();
		Shooter.stopSingulator();
	}

	public void teleopPeriodic() {

		// Place Gear
		if (operator.getRightJoyButton()) {
			placeGear();
		}

		// Climb
		Climb.setClimbSpeed(-Math.abs(operator.getAxisValue("lefty")));

		// Drive
		double left = -driver.getAxisValue("lefty"), right = -driver.getAxisValue("righty");
		Drive.setDriveSpeed(left, right);

		if (driver.getStartButton()) {
			Drive.togglePrecise();
		}

		// Gear
		if (operator.getSelectButton()) {
			Gear.pivotUp();
		} else if (operator.getStartButton()) {
			Gear.pivotDown();
		} else {
			Gear.stopPivot();
			;
		}

		if (operator.getAButton()) {
			Gear.grabGear();
		} else if (operator.getBButton()) {
			Gear.releaseGear();
		} else {
			Gear.stopGear();
		}

		// Shooter
		if (operator.getLeftBumper() && operator.getRightBumper()) {
			Shooter.runShooter();
		} else {
			Shooter.stopShooter();
		}

		if (operator.getAxisValue("lefttrigger") == 1 && operator.getAxisValue("righttrigger") == 1) {
			Shooter.spinSingulator();
		} else {
			Shooter.stopSingulator();
		}

		if (operator.getXButton()) {
			Shooter.spinCentrifuge();
		} else {
			Shooter.stopCentrifuge();
		}

		if (driver.getSelectButton()) {
			Shooter.toggleShooterLight();
		}
		Shooter.updateLight();

		if (driver.getAButton() && Drive.target()) {
		}

	}

	public void testInit() {
	}

	public void testPeriodic() {
	}

	public void disabledInit() {
		//RobotMap.webCam.stopCapture();
	}

	// Place a gear
	public static void placeGear() {

		boolean functionRunning = true;
		int step = 0;
		long startTime = System.currentTimeMillis();

		while (functionRunning) {
			switch (step) {
				case 0:
					startTime = System.currentTimeMillis();
					step++;
					break;
				case 1:
					if (System.currentTimeMillis() - startTime >= 350) {
						startTime = System.currentTimeMillis();
						step++;
					} else {
						Gear.pivotDown();
						Gear.grabGear();
						Drive.setDriveSpeed(-.350, -.350);
					}
					break;
				case 2:
					if (System.currentTimeMillis() - startTime >= 250) {
						startTime = System.currentTimeMillis();
						step++;
					} else {
						Gear.stopPivot();
						Gear.grabGear();
						Drive.setDriveSpeed(-.350, -.350);
					}
					break;
				case 3:
					if (System.currentTimeMillis() - startTime >= 500) {
						startTime = System.currentTimeMillis();
						step++;
					} else {
						Gear.pivotUp();
					}
					break;
				case 4:
					Drive.setDriveSpeed(0.000, 0.000);
					Gear.stopGear();
					Gear.stopPivot();
					functionRunning = false;
					break;
			}
		}

	}

	public static void placeGearAuto() {

		boolean functionRunning = true;
		int step = 0;
		long startTime = System.currentTimeMillis();

		while (functionRunning) {
			switch (step) {
				case 0:
					startTime = System.currentTimeMillis();
					step++;
					break;
				case 1:
					if (System.currentTimeMillis() - startTime >= 350) {
						startTime = System.currentTimeMillis();
						step++;
					} else {
						Gear.pivotDown();
						Gear.setGearRollerSpeed(0.25);
						Drive.setDriveSpeed(-.350, -.350);
					}
					break;
				case 2:
					if (System.currentTimeMillis() - startTime >= 250) {
						startTime = System.currentTimeMillis();
						step++;
					} else {
						Gear.stopPivot();
						Gear.setGearRollerSpeed(0.25);
						Drive.setDriveSpeed(-.350, -.350);
					}
					break;
				case 3:
					if (System.currentTimeMillis() - startTime >= 500) {
						startTime = System.currentTimeMillis();
						step++;
					} else {
						Gear.pivotUp();
					}
					break;
				case 4:
					Drive.setDriveSpeed(0.000, 0.000);
					Gear.stopGear();
					Gear.stopPivot();
					functionRunning = false;
					break;
			}
		}

	}

}
