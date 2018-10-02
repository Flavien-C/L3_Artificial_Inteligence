# TP. Représentation des objets et contraintes

### Représentations
Créer un package Java nommé representations, et y créer
+ une classe Variable, possédant un nom (String) et un domaine (Set<String>)
+ une interface Constraint, possédant des méthodes getScope() et isSatisfiedBy(Map<Variable, String>)
+ une classe AllEqualConstraint, pour représenter les contraintes imposant qu'un ensemble donné de variables prennent la même valeur
+ une classe Rule, pour représenter les contraintes de la forme (x = a && y = b && ...) -> (z = c || t = d || ...)
+ une classe Disjunction, pour représenter les contraintes de la forme (z = c || t = d || ...) (réutiliser la classe Rule)
+ une classe IncompatibilityConstraint, pour représenter les contraintes de la forme ! (x = a && y = b && ...) (réutiliser la classe Rule)


Tester, avec des exemples bien choisis, l'implémentation de la méthode isSatisfiedBy dans ces classes. En particulier, pour la classe Rule, tester avec au moins quatre exemples différents (satisfaisant ou falsifiant la prémisse, et satisfaisant ou falsifiant la conclusion).


### Exemple
Créer un package Java nommé examples, et y créer une classe donnant accès à un ensemble de variables et de contraintes représentant l'exemple suivant :

+ Les voitures du catalogue ont toutes le toit, le capot et le hayon de la même couleur
+ Toutes vérifient qu'au moins l'un des deux côtés (gauche et droite) est de la même couleur que le toit
+ Aucune n'a les deux côtés noirs
+ Enfin, aucune voiture n'a à la fois un toit ouvrant et une sono


Pour la seconde contrainte, on pourra la réécrire comme un ensemble de règles (une par couleur), ou encore créer une classe ConstraintDisjunction permettant de représenter des contraintes consistant en la disjonction de plusieurs contraintes (design pattern « Composite »).





# TP Backtracking

### Backtracking
Créer un package Java nommé ppc, et y créer

+ Une classe nommée Backtracking qui prend en paramètre de constructeur les variables et les contraintes.
+ Avec une méthode solution() qui ressort la prochaine solution du CSP, ou null s’il n’y en a plus.
+ En utilisant l'algorithme de backtracking


Appliquez cette résolution à vos variables et contrainte de l'exemple de voiture,
le but est d’énumérer toutes les solutions possibles, c'est-à-dire toutes les configuration de voiture respectant les directives de l'exemple.



### Filtrage
Maintenant que l'on a un backtracking fonctionnel, on souhaite améliorer ses performances.
On va implémenter une étape de filtrage, c'est-à-dire réduire le domaine des variables non assignées en enlevant les valeurs non viables.
Il faut donc maintenir le domaine des variables non assignées, dans une Map<Variable, Set<String>>.

Pour cela, ajoutez à l'interface Constraint la fonction filter(Map<Variable, String>, Map<Variable, Set<String>>) retournant un booléen indiquant s'il y a eu du filtrage.
Testez, avec des exemples bien choisis, l'implémentation de la méthode filter dans les classes implémentant Constraint.

Puis, dans l'algorithme de backtrack après l'étape de test de satisfaction, ajouter l'étape de filtrage.
Il faut appeler filter sur les contraintes temps qu'au moins une contrainte filtre.
Si le domaine d'une variable est de taille 0 alors cette étape du backtrack est insatisfiable.
Si le domaine d'une variable est de taille 1 alors il faut assigner la variable et refaire la vérification de satisfaction et de filtrage sur ce niveau.

Note d'implémentation: pensez à copier votre Map<Variable, Set<String>> entre chaque niveau de backtrack,
sinon les Map étant passées par référence vont se modifier entre les niveaux.





### Heuristique
Avec la réduction des domaines, on peut maintenant différencier les variables sur le critère de la taille de leur domaine.
Ajoutez l'implémentation des heuristiques de choix de variables et valeurs suivante, les heuristiques choisies étant configurées à la création de la classe Backtrack.
+ Choix de la variable la moins/plus contrainte
+ Choix de la variable avec le domaine le plus petit/grand
+ Choix de la plus petite/grande valeur
+ Choix de valeur au hasard