# Projet de Gestion des Enseignants et des Séances dans une école .

## Description du projet

Cette application JavaFX permet de gérer les emplois du temps d'une école. Elle inclut les fonctionnalités suivantes :
- Enregistrement des enseignants.
- Enregistrement des séances de cours.
- Affichage des emplois du temps par classe et matière.

Le projet utilise **MySQL** pour la gestion des données et **JavaFX** pour l'interface graphique.


### Fonctionnalités principales :
- Une **partie Login/Register** pour sécuriser l'accès à l'application et différencier les utilisateurs.
- **Formulaire d'enregistrement des enseignants** : Permet d'ajouter, modifier, supprimer et rechercher des enseignants dans la base de données.
- **Formulaire d'enregistrement des séances** : Permet d'ajouter des séances de cours, de sélectionner une classe, une matière, un jour, une heure, et l'enseignant associé.
- **Affichage des enseignants et des séances** : Des tableaux sont utilisés pour afficher la liste des enseignants et des séances inscrites dans la base de données.
- **Requêtes** : Permet de consulter et filtrer les séances selon divers critères.

---

## Technologies utilisées

- **JavaFX** : Pour la création des interfaces graphiques et la gestion de l'affichage.
- **FXML** : Pour la définition des interfaces utilisateurs de manière déclarative.
- **MySQL** : Pour la gestion de la base de données, stockant les informations des enseignants et des séances.
- **CSS** : Pour le style et la personnalisation des éléments graphiques.

---

## Étapes de développement

Suite la creaction de projet et pour que le projet fonctionne correctement, vous devez ajouter certaines bibliothèques et dépendances.
.JDBC (Java Database Connectivity) : Pour interagir avec la base de données MySQL depuis Java. Vous devrez ajouter le JDBC Driver MySQL à votre projet.
    .Téléchargez le connecteur JDBC MySQL et ajoutez le fichier .jar à votre projet.

.JavaFX : Si vous utilisez Java 11 ou une version plus récente, vous devrez ajouter les bibliothèques JavaFX séparément, car elles ne sont plus incluses dans le JDK par défaut.
    .Téléchargez JavaFX depuis le site officiel de Gluon, puis configurez votre projet pour inclure les fichiers .jar nécessaires.


    Dans VS Code, vous pouvez ajouter ces bibliothèques comme suit :

Ajoutez les fichiers .jar nécessaires dans votre dossier lib du projet.
Modifiez les paramètres de votre projet pour inclure les chemins vers ces bibliothèques dans le classpath.

### 1. **Conception de la base de données**
   - Création de la base de données `emploidutemps_db` dans MySQL.
   - Création des tables `enseignants`, `seances` et `admin` avec les colonnes suivantes :
    
   
   ```sql
   CREATE DATABASE emploidutemps_db;
   
   CREATE TABLE enseignants (
       id INT AUTO_INCREMENT PRIMARY KEY,
       nom VARCHAR(255),
       matricule VARCHAR(50) UNIQUE,
       contact VARCHAR(50)
   );
   
   CREATE TABLE seances (
       id INT AUTO_INCREMENT PRIMARY KEY,
       id_enseignant INT,
       classe VARCHAR(50),
       matiere VARCHAR(100),
       jour VARCHAR(50),
       heure VARCHAR(50),
       FOREIGN KEY (id_enseignant) REFERENCES enseignants(id)
   );

   CREATE TABLE admin (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(255) NOT NULL,
       email VARCHAR(255) NOT NULL UNIQUE,
       password VARCHAR(255) NOT NULL
   );


### 2. **Développement de l'interface graphique a l'aide de Scene Builder**

Scene Builder est un outil graphique qui permet de concevoir des interfaces JavaFX sans écrire directement du code XML (FXML).

Voici les étapes pour utiliser Scene Builder dans votre projet :

.Téléchargez et installez Scene Builder depuis ce lien.
.Créez des fichiers FXML pour chaque vue de l'application (par exemple, enseignantView.fxml pour l'interface des enseignants).
.Dans Scene Builder, vous pouvez glisser-déposer des éléments graphiques (boutons, champs de texte, tableaux) pour créer l'interface de manière visuelle.
.Vous pouvez ensuite associer chaque élément graphique à une méthode dans le code Java .

Une fois l'interface créée, vous obtenez un fichier FXML que vous pouvez charger dans votre code Java pour l'afficher.

### 3. **Développement du contrôleur Java**

### 4. **Personnalisation de l'interface avec CSS**

### 5. **Tests**

---

## Conclusion
Ce projet permet de maîtriser l'utilisation de JavaFX pour créer des interfaces graphiques, ainsi que l'intégration de Scene Builder pour une conception visuelle rapide et efficace des interfaces. 

L'utilisation de MySQL pour la gestion des données et de FXML pour la structure de l'interface contribue à séparer la logique de l'application de l'affichage, ce qui améliore la maintenabilité du code.