# EJBContainer
Une implémentation personnelle d'un conteneur d'EJB.

Fonctionnalités :
* Gestion de beans singleton et stateless. Pour ce faire, l'utilisateur du framework n'a qu'à placer des annotations @Singleton ou @Stateless dans le code qui implémente un EJB.
*  Injection d'EJB. Il est possible d'annoter un ou plusieurs champs d'une classe avec @EJB afin de demander au conteneur d'injecter des managed bean dans ces derniers.
* Gestion des transactions. Les méthodes peuvent être annotées de manière à indiquer si elles nécessitent l'ouverture d'une nouvelle transaction (@REQUIRES_NEW) ou si elles peuvent utiliser une transaction déjà ouverte par une autre méthode (@REQUIRED). Chaque transaction est associée à l'identifiant du thread qui l'a ouverte de manière à garantir un fonctionnement thread safe du gestionnaire de transactions.
* Injection d'entity manager à l'aide de l'annotation @PersistenceContext. Le manager utilise ensuite le gestionnaire de transaction pour récupérer la transaction courante et y effectuer ses opérations.
* Gestion de fonctions de rappel pour l'initialisation et la destruction d'EJB (@PostConstruct et @PreDestroy).

Difficultés rencontrées :
* Test Driven Development difficile à mettre en œuvre.
