package fr.uge.manifest;

import java.util.*;

public record Container(String destination, int weight) implements Thing {
	
	public Container(String destination, int weight) {
		Objects.requireNonNull(destination);
		if(weight < 0) {
			throw new IllegalArgumentException("weight < 0");
		}
		this.destination = destination;
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		return destination + ' ' + weight + "kg";
	}
	
	@Override
	public int price() {
		return this.weight() * 2;
	}
	
	@Override
	public boolean sanctionable() {
		return true;
	}
}
