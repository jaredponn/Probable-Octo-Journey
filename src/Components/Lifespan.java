package Components;

import poj.Component.Component;

public class Lifespan implements Component {

	private double lifespan;
	private double spawnTime;
	
	public Lifespan( double totalTime , double initialTime ) {
		this.lifespan = totalTime;
		this.spawnTime = initialTime;
	}
	
	public double getLifespan() {
		return this.lifespan;
	}
	
	public double getSpawnTime() {
		return this.spawnTime;
	}
	
	public void print() {
		System.out.println("Lifespan is "+ this.lifespan );
	}
}
