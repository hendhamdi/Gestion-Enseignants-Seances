package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Enseignant;
import model.Seance;
import util.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

public class EnseignantController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField matriculeField;
    @FXML
    private TextField contactField;
    @FXML
    private ComboBox<String> classeComboBox;
    @FXML
    private ComboBox<String> matiereComboBox;
    @FXML
    private ComboBox<String> jourComboBox;
    @FXML
    private ComboBox<String> heureComboBox;
    @FXML
    private TextField matriculeEnseignantField;
    @FXML
    private TableView<Enseignant> enseignantsTable;
    @FXML
    private TableColumn<Enseignant, String> matriculeColumn;
    @FXML
    private TableColumn<Enseignant, String> nomColumn;
    @FXML
    private TableColumn<Enseignant, String> contactColumn;

    // Nouvelle TableView pour les séances
    @FXML
    private TableView<Seance> seancesTable;
    @FXML
    private TableColumn<Seance, String> classeSeanceColumn;
    @FXML
    private TableColumn<Seance, String> matiereSeanceColumn;
    @FXML
    private TableColumn<Seance, String> jourSeanceColumn;
    @FXML
    private TableColumn<Seance, String> heureSeanceColumn;
    @FXML
    private TableColumn<Seance, String> matriculeEnseignantSeanceColumn;

    // Initialisation des données pour les ComboBox
    private ObservableList<String> classes = FXCollections.observableArrayList("Licence", "Master 1", "Master 2");
    private ObservableList<String> matieres = FXCollections.observableArrayList("Dev.Web", "Dev.Mobile", "Anglais",
            "Réseaux", "SE");
    private ObservableList<String> jours = FXCollections.observableArrayList("Lundi", "Mardi", "Mercredi", "Jeudi",
            "Vendredi");
    private ObservableList<String> heures = FXCollections.observableArrayList("08:00", "10:00", "12:00", "14:00",
            "16:00", "18:00");

    @FXML
    public void initialize() {
        // Liaison des données aux ComboBox
        classeComboBox.setItems(classes);
        matiereComboBox.setItems(matieres);
        jourComboBox.setItems(jours);
        heureComboBox.setItems(heures);

        // Configurer les colonnes de la table des enseignants
        matriculeColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMatricule()));
        nomColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNom()));
        contactColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getContact()));

        // Configurer les colonnes de la table des séances
        classeSeanceColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getClasse()));
        matiereSeanceColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMatiere()));
        jourSeanceColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getJour()));
        heureSeanceColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getHeure()));
        matriculeEnseignantSeanceColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getMatriculeEnseignant()));

        // Charger les enseignants depuis la base de données
        afficherEnseignants();
        afficherSeances();
    }

    @FXML
    private void chercherEnseignant() {
        String matricule = matriculeField.getText();
        if (matricule.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Erreur", "Veuillez entrer un matricule.");
            return;
        }

        String query = "SELECT * FROM enseignants WHERE matricule = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, matricule);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nomField.setText(resultSet.getString("nom"));
                contactField.setText(resultSet.getString("contact"));
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Enseignant trouvé.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Erreur", "Aucun enseignant trouvé avec ce matricule.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Problème de recherche : " + e.getMessage());
        }
    }

    @FXML
    private void enregistrerEnseignant() {
        String matricule = matriculeField.getText();
        String nom = nomField.getText();
        String contact = contactField.getText();

        if (matricule.isEmpty() || nom.isEmpty() || contact.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Erreur", "Tous les champs sont obligatoires.");
            return;
        }

        String query = "INSERT INTO enseignants (matricule, nom, contact) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, matricule);
            statement.setString(2, nom);
            statement.setString(3, contact);

            statement.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Enseignant enregistré avec succès.");

            // Actualiser l'affichage des enseignants
            afficherEnseignants();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'enregistrer l'enseignant : " + e.getMessage());
        }
    }

    private void afficherEnseignants() {
        ObservableList<Enseignant> enseignants = FXCollections.observableArrayList();
        String query = "SELECT * FROM enseignants";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                enseignants.add(new Enseignant(
                        resultSet.getString("matricule"),
                        resultSet.getString("nom"),
                        resultSet.getString("contact")));
            }

            enseignantsTable.setItems(enseignants);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les enseignants : " + e.getMessage());
        }
    }

    @FXML
    private void enregistrerSeance() {
        String classe = classeComboBox.getValue();
        String matiere = matiereComboBox.getValue();
        String jour = jourComboBox.getValue();
        String heure = heureComboBox.getValue();
        String matricule = matriculeEnseignantField.getText();

        if (classe == null || matiere == null || jour == null || heure == null || matricule.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Erreur",
                    "Tous les champs sont obligatoires pour enregistrer une séance.");
            return;
        }

        String query = "INSERT INTO seances (classe, matiere, jour, heure, matricule_enseignant) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, classe);
            statement.setString(2, matiere);
            statement.setString(3, jour);
            statement.setString(4, heure);
            statement.setString(5, matricule);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Séance enregistrée avec succès.");
                // Actualiser l'affichage des séances
                afficherSeances();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'enregistrer la séance : " + e.getMessage());
        }
    }

    @FXML
    private void afficherSeances() {
        ObservableList<Seance> seances = FXCollections.observableArrayList();
        String query = "SELECT * FROM seances";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                seances.add(new Seance(
                        resultSet.getString("classe"),
                        resultSet.getString("matiere"),
                        resultSet.getString("jour"),
                        resultSet.getString("heure"),
                        resultSet.getString("matricule_enseignant")));
            }

            seancesTable.setItems(seances);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les séances : " + e.getMessage());
        }
    }

    // Méthode pour afficher une alerte
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
