package frc.autonomous;



import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.Robot;
import frc.robot.Variables;
import frc.system.Drive;
import frc.system.NavX;
import frc.system.Shooter;
import frc.system.Drive.DIRECTION;

public class ShootTurnDriveAuto extends Auto {

	long startTime, now;
	Alliance alliance;
	DIRECTION direction;
	
	public ShootTurnDriveAuto(Alliance alliance) {
		super("Shoot turn drive auto " + alliance.toString());
		this.alliance = alliance;
		direction = (alliance.toString().equalsIgnoreCase("red")) ? DIRECTION.RIGHT : DIRECTION.LEFT;
		step = -1;
	}
	
	@Override
	public void execute() {
		
		now = System.currentTimeMillis();
		
		switch(step) {
		case -1:
			startTime = now;
			nextStep();
			break;
		case 0:
			Shooter.setShooterVoltage((alliance.toString().equals("blue")) ? Variables.SHOOTER_RUN_SPEED : -8.50);
			if(now - startTime >= 1000) {
				nextStep();
			}
			break;
		case 1:
			Shooter.setShooterVoltage((alliance.toString().equals("blue")) ? Variables.SHOOTER_RUN_SPEED : -8.50);
			Shooter.spinCentrifuge();
			Shooter.spinSingulator();
			if(now - startTime >= 7000) {
				startTime = now;
				Drive.setStart();
				Shooter.stopCentrifuge();
				Shooter.stopShooter();
				Shooter.stopSingulator();
				Robot.START_ANGLE = NavX.getAngleRotation(); 
				nextStep();
			}
			break;
		case 2:
			if(alliance.toString().equalsIgnoreCase("blue")) {
				if(Drive.driveToAngle(15, direction)) {
					startTime = now;
					Drive.setStart();
					nextStep();
				}
			} else {
				if(Drive.driveToAngle(50, direction)) {
					startTime = now;
					Drive.setStart();
					nextStep();
				}
			}
			break;
		case 3:
			if(now - startTime >= 5000) {
				Drive.setDriveSpeed(0, 0);
				nextStep();
			}
			Drive.driveStraight(.5, .5);
			break;
		case 4:
			Robot.placeGearAuto();
			nextStep();
			break;
		}
		
	}

}
