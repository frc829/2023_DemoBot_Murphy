package frc.autonomous;



import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.Robot;
import frc.system.Drive;
import frc.system.Shooter;
import frc.system.Drive.DIRECTION;

public class ShootGearAuto extends Auto {

	Drive.DIRECTION direction;
	boolean calibrate = false;
	long startTime;
	
	public ShootGearAuto(Alliance alliance) {
		super("Place Side Gear " + alliance.toString());
		this.direction = (alliance.toString().equalsIgnoreCase("red")) ? DIRECTION.LEFT : DIRECTION.RIGHT;
	}
	
	public void execute() {
		
		switch(step) {
		case 0:
			Drive.precise = 2;
			Shooter.runShooter();
			if(!calibrate) {
				Drive.setStart();
				calibrate = true;
				startTime = System.currentTimeMillis();
			}
			if(System.currentTimeMillis() - startTime >= 2500) {
				nextStep();
			} else {
				Drive.driveStraight(.25, .25);
			}
			break;
		case 1:
			if(Drive.driveToAngle(25, direction)) {
				nextStep();
				Drive.setStart();
				startTime = System.currentTimeMillis();
			}
			break;
		case 2:
			if(System.currentTimeMillis() - startTime >= 2250) {
				startTime = System.currentTimeMillis();
				nextStep();
			} else {
				Drive.driveStraight(.25, .25);
			}
			break;
		case 3:
			Robot.placeGearAuto();
			startTime = System.currentTimeMillis();
			nextStep();
			break;
		case 4:
			if(System.currentTimeMillis() - startTime >= 625) {
				nextStep();
			} else {
				Drive.driveStraight(-.25, -.25);
			}
			break;
		case 5:
			if(Drive.target()) {
				nextStep();
			}
			break;
		case 6:
			Drive.setDriveSpeed(0, 0);
			Shooter.spinCentrifuge();
			Shooter.spinSingulator();
			Shooter.runShooter();
			break;
		}
		
	}
	
}
