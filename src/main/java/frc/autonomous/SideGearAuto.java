package frc.autonomous;

import frc.robot.Robot;
import frc.system.Drive;

public class SideGearAuto extends Auto {

	Drive.DIRECTION direction;
	boolean calibrate = false;
	long startTime;
	
	public SideGearAuto(Drive.DIRECTION direction) {
		super("Place Side Gear " + direction.toString().toLowerCase());
		this.direction = direction;
	}
	
	public void execute() {
		
		System.out.println(name);
		
		switch(step) {
		case 0:
			Drive.precise = 2;
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
			if(Drive.driveToAngle(30, direction)) {
				nextStep();
				Drive.setStart();
				startTime = System.currentTimeMillis();
			}
			break;
		case 2:
			if(System.currentTimeMillis() - startTime >= 2000) {
				nextStep();
			} else {
				Drive.driveStraight(.25, .25);
			}
			break;
		case 3:
			Drive.setDriveSpeed(0, 0);
			System.out.println("At position");
			Robot.placeGearAuto();
			nextStep();
			break;
		}
		
	}
	
}
