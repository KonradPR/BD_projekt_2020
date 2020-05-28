package Gui;

import Handlers.DataAccessHandler;
import Handlers.DataModificationHandler;
import Handlers.TransactionHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppFrame extends Application {

    private TransactionHandler trH = new TransactionHandler();
    private DataModificationHandler dmH = new DataModificationHandler();
    private DataAccessHandler acH = new DataAccessHandler();

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Hospital Medicine Stockpile App");
        stage.setOnCloseRequest(event -> Platform.exit());
        //TODO refoactor jakas funckje do tworzenia menu i zrobić to w pętli jak człowiek
        //  labele to must żeby sie dało zrobić on clicka tako rzecze stackoverflow
        MenuBar menuBar = new MenuBar();
        Label label1 = new Label("Pacjenci");
        label1.setOnMouseClicked(event->{});
        Menu m1 = new Menu("",label1);
        Label label2 = new Label("Lekarze");
        label2.setOnMouseClicked(event->{});
        Menu m2 = new Menu("",label2);
        Label label3 = new Label("Dostawcy");
        label3.setOnMouseClicked(event->{});
        Menu m3 = new Menu("",label3);
        Label label4 = new Label("Leki");
        label4.setOnMouseClicked(event->{});
        Menu m4 = new Menu("",label4);
        Label label5 = new Label("Recepty");
        label5.setOnMouseClicked(event->{});
        Menu m5 = new Menu("",label5);
        menuBar.getMenus().add(m1);
        menuBar.getMenus().add(m2);
        menuBar.getMenus().add(m3);
        menuBar.getMenus().add(m4);
        menuBar.getMenus().add(m5);
        VBox rootNode = new VBox(menuBar);
        AppScene scene = new AppScene(rootNode, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void show(String[] args){
        launch(args);
    }

}
