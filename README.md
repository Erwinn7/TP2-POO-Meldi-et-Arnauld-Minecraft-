
# MINECRAFT - CONSOLE

## GAMEPLAY

>  Ce jeu est une version lite de minecraft assez intuitive en console.

Lancer le jeu, saisissez votre nom : 

    Entrez votre nom : _

Le menu suivant vous sera proposer :
    
    Vous souhaitez jouer : 
    1- En Solo
    2- En PVP
    3- Quitter
    Choix : _

 
 ### 1. EN SOLO 

> Vous apparaitrez dans l'univers du jeu avec des équipements basique (kit de survie).

Vous aurez plusieurs actions possible suivant le menu: 

    ---------- MENU ----------
    1- Se déplacer
    2- Consulter l'inventaire
    3- Crafter
    4- Consulter la map
    5- Utiliser un item
    6- Afficher le profil du joueur
    7- Quitter le jeu
    choix: _

Lorsque vous sélectionnez : **1- Se déplacer** 

Il vous est proposer de choisir la direction :

    Choisissez une direction : 
    1- Haut
    2- Bas
    3- Gauche
    4- Droite
    choix: _

En fonction de votre direction vous pouvez rencontrer un monstre ou des items.

*A vous de jouer et prenez garde à ne pas tomber sur des montres trop puissants.*


### 2. EN PVP

> Un combat avec un autre joueur vous sera immédiatement proposer.

---

## LES CLASSES 

### Jeu
La classe `Jeu` est le point d'entrée principal du programme. Elle gère le déroulement du jeu, y compris l'initialisation du joueur et le menu principal qui permet au joueur de se déplacer, de consulter son inventaire, de crafter des objets, et d'interagir avec le monde du jeu.

### Combat
La classe `Combat` gère les combats entre deux joueurs. Elle initialise les joueurs et démarre le combat, en utilisant un serveur pour gérer les interactions en temps réel. La logique de combat est commentée, indiquant que des fonctionnalités supplémentaires peuvent être ajoutées pour gérer les tours de chaque joueur.

### Bloc
La classe `Bloc` représente un bloc dans le jeu. Elle contient des attributs pour le type de bloc, sa solidité, sa cassabilité, et sa consommabilité. Elle fournit également des méthodes pour accéder à ces attributs et une méthode `toString` pour afficher les informations du bloc.

### Item
La classe `Item` représente un objet que le joueur peut utiliser dans le jeu. Elle contient des attributs pour le nom, le type, l'effet, la valeur, et si l'item est échangeable. La classe inclut des méthodes pour utiliser l'item et appliquer ses effets sur un joueur.

### Effet
L'énumération `Effet` définit les différents effets qu'un item peut avoir sur un joueur, tels que l'augmentation ou la diminution des points de vie, d'attaque, ou de défense.

### Entite
La classe abstraite `Entite` est la classe de base pour toutes les entités du jeu, y compris les joueurs et les monstres. Elle contient des attributs pour la position, le nom, les points de vie, d'attaque, et de défense, ainsi que des méthodes pour attaquer et vérifier si l'entité est en vie.

### Joueur
La classe `Joueur` hérite de `Entite` et représente un joueur dans le jeu. Elle gère l'inventaire du joueur, les déplacements, l'utilisation d'items, et les interactions avec d'autres entités. Elle inclut également des méthodes pour crafter des items et se connecter à un serveur.

### Monstre
La classe `Monstre` hérite de `Entite` et représente un monstre dans le jeu. Elle contient des attributs spécifiques au monstre et une méthode `toString` pour afficher ses informations.

### TypeItem
L'énumération `TypeItem` définit les différentes catégories d'items disponibles dans le jeu, telles que les armes, les armures, et les ressources. Chaque type a un nom et une initiale associée.

---
