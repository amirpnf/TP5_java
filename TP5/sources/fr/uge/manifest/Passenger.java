package fr.uge.manifest;

import java.util.Objects;

public record Passenger(String destination) implements Thing {
	public Passenger {
		Objects.requireNonNull(destination); 
	}
	
	@Override
	public String toString() {
		return destination + " (passenger)"; 
	}
	
	@Override
  public int weight() {
  	return 0;
  }
	
	@Override
	public int price() {
		return 10;
	}
	
	@Override
	public boolean sanctionable() {
		return false;
	}
}
