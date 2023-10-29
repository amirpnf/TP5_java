# TP5 Java - Amirhossein Pouyanfar - Group 2 Mr. Rémy Forax

## Exercise 1 :
Un porte-conteneur (container en Anglais) est un bateau qui, comme son nom l'indique, transporte des conteneurs d'un port à l'autre. Chaque porte-conteneur possède un manifeste (manifest), qui est un document papier contenant une liste de l'ensemble des conteneurs qu'il transporte.
Le but de ce TP est de modéliser ce document papier.

Vous écrirez toutes les classes de ce TP dans un package nommé fr.uge.manifest. Vous devez tester toutes les méthodes demandées et vous écrirez tous vos tests dans la classe Main du package fr.uge.manifest.main. 

1. Dans un premier temps, on cherche à définir un Container. Un conteneur possède une destination sous forme de chaîne de caractères ainsi qu'un poids (weight en Anglais) qui est une valeur entière. Il ne doit pas être possible de créer un conteneur avec des valeurs invalides: la destination doit exister et le poids doit être positif ou nul.
Écrire le type Container de telle façon à ce que le code suivant fonctionne:
```java
public static void main(String[] args) {
  var container = new Container("Germany", 500);
  System.out.println(container.destination());  // Germany
  System.out.println(container.weight());  // 500
}
```     

**Answer** : Here's a simple `Container` record :
```java
package fr.uge.manifest;

import java.util.*;

public record Container(String destination, int weight) {
  
  public Container(String destination, int weight) {
  	Objects.requireNonNull(destination);
  	if(weight < 0) {
  	  throw new IllegalArgumentException("weight < 0");
  	}
  	this.destination = destination;
  	this.weight = weight;
  }
  
  public static void main(String[] args) {
    // To be continued...
  }
}  
```

2. On veut maintenant introduire la notion de Manifest, un manifeste contient une liste de conteneurs. Pour l'instant, un manifeste définit une seule méthode add(conteneur) qui permet d'ajouter un conteneur au manifeste.
Il ne doit pas être possible d'ajouter un conteneur null.
Écrire le type Manifest tel que le code suivant fonctionne:
```java
public static void main(String[] args) {
  ...
  var container2 = new Container("Italy", 400);
  var container3 = new Container("Austria", 200);
  var manifest = new Manifest();
  manifest.add(container2);
  manifest.add(container3);
  System.out.println(manifest.weight());  // 600
}
```  

**Answer** : Here's a `Manifest` class implementing the required features :

```java
package fr.uge.manifest;

import java.util.ArrayList;
import java.util.Objects;

public class Manifest {
  private final ArrayList<Container> containers = new ArrayList<>();
  public void add(Container other) {
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
}
```

3. On souhaite maintenant pouvoir afficher un manifeste. Afficher un manifeste revient à afficher chaque conteneur sur une ligne, avec un numéro, 1 pour le premier conteneur, 2 pour le suivant, etc, suivi de la destination du conteneur ainsi que de son poids.
Le formatage exact pour une ligne est:

```bash
[numéro]. [destination] [poids]kg
```    
suivi d'un retour à la ligne (y compris après la dernière ligne).
Modifier le type Manifest pour que le code suivant ait le comportement attendu:

```java
public static void main(String[] args) {
    ...
  var container4 = new Container("Spain", 250);
  var container5 = new Container("Swiss", 200);
  var manifest2 = new Manifest();
  manifest2.add(container4);
  manifest2.add(container5);
  System.out.println(manifest2);
  // 1. Spain 250kg
  // 2. Swiss 200kg
  }
```    
    
**Answer** :  Here's the modified Manifest class, functioning as required :

```java
package fr.uge.manifest;

import java.util.ArrayList;
import java.util.HashMap;
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
}  
```
And in order to ensure that this method functions well, we must override the `toString()` method in
`container` record :
```java
@Override
public String toString() {
	return destination + ' ' + weight + "kg";
}
```

4. Un porte conteneur comme son nom ne l'indique pas peut aussi transporter des passagers. Un Passenger est défini par une destination uniquement, les passagers ne sont pas assez lourd pour avoir un vrai poids.
Dans un premier temps, comment définir un Passenger tel que l'on puisse créer un passager uniquement avec sa destination. Puis expliquer comment modifier Manifest pour que l'on puisse enregistrer aussi bien des conteneurs que des passagers.
Pour l'affichage, un passager affiche la destination ainsi que "(passenger)" entre parenthèse (cf le code plus bas).
Ecrire le code de Passenger et modifier le code de Manifest tel que le code ci-dessous fonctionne.
```java
public static void main(String[] args) {
  ...
  var passenger1 = new Passenger("France");
  var container6 = new Container("England", 350);
  var manifest3 = new Manifest();
  manifest3.add(passenger1);
  manifest3.add(container6);
  System.out.println(manifest3);
  // 1. France (passenger)
  // 2. England 350kg
}
```  

**Answer** : We can let a `Manifest` object contain several types of things, including passengers or containers, this would
be possible using the **Interface** notion in Java. So let's create a new interface named `Thing`, then we shall modify the 
`Manifest` class and define the new `Passenger` record:  
- `Thing` interface :
```java
package fr.uge.manifest;

public interface Thing {
	int weight();
}
```  
- The new `Passenger` record : 
```java
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
}

```  
- We are going to modify the `Manifest` method, and add an `implements` keyword to our `Container` record : 
```java
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
}
```
- Lastly, let's add the `implements` keyword to our `Container` method. From now on, `Passenger` and `Container` are 
two types of `Thing`.
```java
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
}
```

5. On souhaite ajouter une méthode price à Manifest qui calcul le prix pour qu'un conteneur ou qu'un passager soit sur le bateau.

Le prix pour un passager est 10.
Le prix pour un conteneur est le poid du conteneur multiplié par 2.

Ajouter une méthode price à Manifest et faite en sorte que le prix soit calculés correctement.
```java
System.out.println(manifest3.price()); // 710
```

**Answer** : If we want to calculate the price for a `Manifest` Object, which can contain different types of `Thing`, like 
`Passenger` and `Container`, we have to add a `price` method to our `Thing` interface and override this method in the mentionned
records implementing `Thing`.

- Modified `Thing` interface :
```java
package fr.uge.manifest;

public interface Thing {
	int weight();
	
	int price();
}
```

- Modified `Container` record :
```java
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
	
	@Override // As you can see we've overridden the price method here
	public int price() {
		return this.weight() * 2;
	}
}
```

- Also the modified `Passenger` record :
```java
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
	
	@Override  // As you can see we've overridden the price method here again
	public int price() {
		return 10;
	}
}
```
- Lastly, let's define a `price` method in the `Manifest` class :

```java
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
	
	public int price() { // This is the method we've added
		var sum = 0;
		for(var obj : containers) {
			sum += obj.price();
		}
		return sum;
	}
}
```

6. Il arrive que l'on soit obligé de décharger tous les conteneurs liés à une destination s'il y a des problèmes d'embargo (quand un dictateur se dit qu'il s'offirait bien une partie d'un pays voisin par exemple). Dans ce cas, il faut aussi supprimer tous les conteneurs liés à cette destination au niveau du manifeste (mais pas les passagers).
Pour prendre en compte cela, on introduit une méthode removeAllContainersFrom(destination) qui supprime tous les conteneurs liés à une destination. S'il n'y a pas de conteneur pour cette destination, on ne fait rien.
Modifier le code pour introduire cette méthode pour que l'exemple ci-dessous fonctionne:
Note: pour savoir si il s'agit d'un conteneur ou pas, on pourrait introduire une méthode isContainer dont l'implantation par défaut sera de retourner false.

```java
public static void main(String[] args) {
  ...  
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
```    

**Answer** : First of all, we have to define the `isContainer` method in the `Thing` interface, and
override it in `Passenger` and `Container` records. 
In order to avoid using a loop, Iterator object, to loop through the `Thing` Arraylist, we've decided to use the `removeIf` method from Collections. We have to define a `getDestination` method in our `Thing` interface, and override it in all records implementing
this interface. Then we could use the `removeIf` method in our new `removeAllContainersFrom` method.

- Modified `Thing` interface :
```java
package fr.uge.manifest;

public interface Thing {
	int weight();
	
	int price();
	
	boolean isContainer();

	String getDestination();
}
```

- `Passenger` record :
```java
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
	public String getDestination() {
		return destination;
	}
	
	@Override
	public boolean isContainer() {
		return false;
	}
}
```

- `Container` record :
```java
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
	public String getDestination() {
		return destination;
	}
	
	@Override
	public boolean isContainer() {
		return true;
	}
}

```
- The modified `Manifest` class and the new `removeAllContainersFrom` method :
```java
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
	
	public void removeAllContainersFrom(String dest) { // Here's our new method
		containers.removeIf(obj -> obj.isContainer() && obj.getDestination().equals(dest));
	}
}
```

7. Pour résoudre la question précédente, au lieu de faire des appels de méthode, on peut aussi utiliser instanceof. Expliquer comment on peut utiliser instanceof. Puis expliquer selon vous quel est le problème d'utiliser instanceof dans ce contexte et pourquoi on ne doit pas l'utiliser. 

**Answer** : Using `instanceof` can work in this context, however, there are some downsides using `instanceof` in certain
situations :
- If you use `instanceof` in the `removeFromAllContainers` method, you are involuntarily violating the **open-closed principle**,
this means, if you add a new type to your `Thing` interface, you'll have to modify the mentioned method, to accommodate the new type. The **Open-closed principle** states that software entities should be open for extension but closed to modification.  
- `instanceof` is a runtime type-check, it doesn't provide strong compile-time type safety, so it may lead to potential bugs and errors if the class is hierarchy evolves.      

8.  Question optionnelle:
On met les conteneurs ayant la même destination au même endroit sur le porte-conteneur, et si un porte-conteneur est mal équilibré il a une fâcheuse tendance à se retourner. Donc, pour aider au placement des conteneurs, il doit être possible de fournir un dictionnaire qui, pour chaque destination, indique le poids de l'ensemble des conteneurs liés à cette destination.
Pour cela, écrire une méthode weightPerDestination qui, pour un manifeste donné, renvoie un dictionnaire qui indique le poids des conteneurs pour chaque destination.
Par exemple, avec le code ci-dessous, il y a deux conteneurs qui ont comme destination "Monaco", avec un poids combiné de 100 + 300 = 400, tandis que "Luxembourg" a un seul conteneur de poids 200.
```java
public static void main(String[] args) {
  ...
  var container11 = new Container("Monaco", 100);
  var container12 = new Container("Luxembourg", 200);
  var container13 = new Container("Monaco", 300);
  var passenger3 = new Passenger("Paris");
  var manifest8 = new Manifest();
  manifest8.add(container11);
  manifest8.add(container12);
  manifest8.add(container13);
  manifest8.add(passenger3);
  System.out.println(manifest8.weightPerDestination());
  // {Monaco=400, Paris=0, Luxembourg=200}
}
```  

**Answer** : Here's a new `weightPerDestination` method, returning the required result. This method uses a particular 
Hash Map method, `getOrDefault`. This last method gets the value of a given key, and if this value doesn't exist, the 
method sets a default value for the key. Note that you we cannot create a Hash Map having primitive type value (Java doesn't allow that). We should use the Integer class (immutable by the way).

```java
public HashMap<String, Integer> weightPerDestination(){
  HashMap<String, Integer> weightMap = new HashMap<>();
  for(var obj : containers) {
  	weightMap.put(obj.getDestination(), weightMap.getOrDefault(obj.getDestination(), 0) + obj.weight());
  }
  return weightMap;
}
```
    

