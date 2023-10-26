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
    