package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/** The Javadoc folder can be located in the HTML document titled "index" within the folder Titled Software 1 Final*/
public class Main extends Application {
/**********assigned Parent root so that mainScreen is loaded as first screen*********/
    @Override
    public void start( Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(new Scene(root,900,400));
        primaryStage.show();


    }
    public static void main(String[] args){
        launch(args);
    }


}
