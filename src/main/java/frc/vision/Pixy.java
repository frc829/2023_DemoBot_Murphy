package frc.vision;

import java.awt.Rectangle;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

public class Pixy {

	public I2C pixy;
	public byte[] values = new byte[64];
	
	public class PixyPacket {
		
		public int x, y, width, height;
		
		public PixyPacket(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		
		public Rectangle getRect() {
			return new Rectangle(x, y, width, height);
		}
		
	}
	
	public Pixy() {
		pixy = new I2C(Port.kOnboard, 0x54);
	}
	
	public boolean getValues() {
		return pixy.readOnly(values, 64);
	}
	
	public PixyPacket getPacket() {
		if(!getValues()) {
			int i = 0;
			while(!(((values[i] & 0xff) == 0x55) && ((values[i+1] & 0xff) == 0xaa)) && i < 50) {
				i++;
			}
			i++;
			if(i > 50) {
				i = 49;
			}
			while(!(((values[i] & 0xff) == 0x55) && ((values[i+1] & 0xff) == 0xaa)) && i < 50) {
				i++;
			}
			int xPos = (char) (((values[i + 7] & 0xff) << 8) | values[i+6] & 0xff);
			int yPos = (char) (((values[i + 9] & 0xff) << 8) | values[i+8] & 0xff);
			int width = (char) (((values[i + 11] & 0xff) << 8) | values[i+10] & 0xff);
			int height = (char) (((values[i + 13] & 0xff) << 8) | values[i+12] & 0xff);
			return new PixyPacket(xPos, yPos, width, height);
		}
		return new PixyPacket(0, 0, 0, 0);
	}
	
}
