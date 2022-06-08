package banking;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
This class launches the JavaFX application
Places user interface controls in a scene and displays it in a stage.
@author Dharma Wijesinghe, Min Sun You
*/
public class BankTellerMain extends Application {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;

    /**
    Default constructor of this class.
    */
    public BankTellerMain() {
        
    }
    /**
    Creates a scene and displays it in a stage.
    @param primaryStage the stage.
    */
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("BankTellerView.fxml"));
			Scene scene = new Scene(root, WIDTH, HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("BankTeller User Interface");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	Launches the JavaFX application.
	@param args the command line arguments
	*/
	public static void main(String[] args) {
		launch(args);
	}
}