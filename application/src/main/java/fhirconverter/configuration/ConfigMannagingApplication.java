package fhirconverter.configuration;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConfigMannagingApplication extends Application {
 
    public static void main(String[] args) {
        launch(args);
    }
   
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Config Mannaaging Application");
        Group root = new Group();
        Scene scene = new Scene(root, 640, 480, Color.WHITE);
        
        StackPane configSelection = initConfigSelectionPane(20, 20);
        
       
        root.getChildren().add(configSelection);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private StackPane initConfigSelectionPane(int x, int y) {
    	 StackPane configSelection = new StackPane();  
         
         Text text = new Text(0, 0, "Select config:");
         text.setFont(Font.font(null, FontWeight.BOLD, 12));
         text.setFill(Color.rgb(0, 0, 0, 1));
         configSelection.getChildren().add(text);
         
         
         
         configSelection.setLayoutX(x);
         configSelection.setLayoutY(y);
         return configSelection;
    }
 
}


