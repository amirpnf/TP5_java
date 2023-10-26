package fr.uge.manifest;


public class Main {

	public static void main(String[] args) {
    var container8 = new Container("Russia", 450);
    var container9 = new Container("China", 200);
    var container10 = new Container("Russia", 125);
    var passenger2 = new Passenger("Russia");
    var manifest4 = new Manifest();
    manifest4.add(container8);
    manifest4.add(container9);
    manifest4.add(container10);
    manifest4.add(passenger2);
    manifest4.removeAllContainersFrom("Russia");
    System.out.println(manifest4);
    // 1. China 200kg
    // 2. Russia (passenger)
	}

}
