import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/enseignants.fxml"));
        Scene scene = new Scene(root);

        // Charger le fichier CSS
        scene.getStylesheets().add(getClass().getResource("/css/enseignant.css").toExternalForm());

        primaryStage.setTitle("Gestion des Enseignants");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
