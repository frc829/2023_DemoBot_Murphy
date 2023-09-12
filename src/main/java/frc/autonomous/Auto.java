package frc.autonomous;

public abstract class Auto {
	
	public String name;
	public int step = 0;
	
	public Auto(String name) {
		this.name = name;
		step = 0;
	}
	
	public abstract void execute();
	public void nextStep() {
		step++;
	}
	
}
