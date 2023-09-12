package frc.autonomous;

import frc.robot.Robot;
import frc.system.Drive;
import frc.system.Gear;

public class CenterGearAuto extends Auto {

	long startTime;
	
	public CenterGearAuto() {
		super("Place Center Gear");
	}
	
	public void execute() {
		
		System.out.println(name);
		//double dist = Variables.UNITS_PER_FEET * 7;
		
		switch(step) {
		case 0:
			Drive.precise = 2;
			Drive.setStart();
			startTime = System.currentTimeMillis();
			nextStep();
			break;
		case 1:
			/*if(Drive.driveDistance(dist, .25, .25) ){
				nextStep();
			} else */if(System.currentTimeMillis() - startTime >= 3000) {
				nextStep();
			} else {
				Drive.driveStraight(.25, .25);
				Gear.stopPivot();
			}
			break;
		case 2:
			Drive.setDriveSpeed(0, 0);
			System.out.println("At position");
			Robot.placeGear();
			nextStep();
			break;
		}
		
	}
	
}
