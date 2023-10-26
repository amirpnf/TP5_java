package fr.uge.manifest;

import java.util.ArrayList;
import java.util.Objects;

public class Manifest {
	private final ArrayList<Thing> containers = new ArrayList<>();
	
	public void add(Thing other) {
		Objects.requireNonNull(other);
		containers.add(other);
	}
	
	public int weight() {
		var result = 0;
		for(var obj : containers) {
			result += obj.weight();
		}
		return result;
	}
	
	@Override
	public String toString() {
		var builder = new StringBuilder();
		var i = 1;
		for(var obj : containers) {
			builder.append(i).append(". ").append(obj).append("\n");
			i++;
		}
		return builder.toString();
	}
	
	public int price() {
		var sum = 0;
		for(var obj : containers) {
			sum += obj.price();
		}
		return sum;
	}
	
	public void removeAllContainersFrom(String dest) {
		
	}
}
