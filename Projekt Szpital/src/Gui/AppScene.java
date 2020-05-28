package Gui;

import Entities.Patient;
import Handlers.DataAccessHandler;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.awt.*;

public class AppScene extends Scene {

    private Group group;

    AppScene(Pane rootPane,int width,int height){
        super(rootPane, width, height);
        group = new Group();
        ListView<Patient>ls = new ListView<Patient>();
        group.getChildren().add(ls);
        rootPane.getChildren().add(group);
    }

}
