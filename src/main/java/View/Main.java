package View;

import Model.*;
import Model.ReadFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        ReadFile rf = new ReadFile();
        rf.readFiles("corpus");*/
        Queue<String> s= new LinkedList<String>();
        Parse p = new Parse(s);
        s.add("999");
        s.add("10,123");
        s.add("123");
        s.add("thousand");
        s.add("1010.56");
        s.add("10,123,000");
        s.add("55");
        s.add("Million");
        s.add("10,123,000,000");
        s.add("55");
        s.add("Billion");
        s.add("7");
        s.add("Trillion");
        s.add("6%");
        s.add("6");
        s.add("percent");
        s.add("6000");
        s.add("percentage");
        s.add("1.7320");
        s.add("Dollars");
        s.add("$450,000");

        p.run();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
