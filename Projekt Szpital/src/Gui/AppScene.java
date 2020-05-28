package Gui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class AppScene extends Scene {

    private Group group;

    AppScene(Pane rootPane,int width,int height){
        super(rootPane, width, height);
        group = new Group();
        rootPane.getChildren().add(group);
    }

}
