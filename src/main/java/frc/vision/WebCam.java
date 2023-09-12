package frc.vision;

import edu.wpi.first.cameraserver.CameraServer;

public class WebCam {
	
	public WebCam() {		
	}
	
	public void startCapture() {
		CameraServer.startAutomaticCapture();
	}
	
	public void stopCapture() {
	}
	
}
