package frc.autonomous;

import frc.system.Drive;

public class DriveForwardAuto extends Auto {

	long startTime;
	
	public DriveForwardAuto() {
		super("Drive Forward");
	}
	
	public void execute() {
		
		long now = System.currentTimeMillis();
		
		switch(step) {
		case 0:
			Drive.precise = 1;
			startTime = now;
			Drive.setStart();
			nextStep();
			break;
		case 1:
			if(now - startTime >= 7000) {
				nextStep();
			} else {
				Drive.driveStraight(.25, .25);
			}
			break;
		case 2:
			Drive.setDriveSpeed(0, 0);
			break;
		}
		
	}
	
}
