package Gui;

import Handlers.DataAccessHandler;
import Handlers.DataModificationHandler;
import Handlers.TransactionHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AppFrame extends Application {

    private TransactionHandler trH = new TransactionHandler();
    private DataModificationHandler dmH = new DataModificationHandler();
    private DataAccessHandler acH = new DataAccessHandler();

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Hospital Medicine Stockpile App");
        stage.setOnCloseRequest(event -> Platform.exit());
        Pane rootNode = new Pane();
        AppScene scene = new AppScene(rootNode, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public void show(String[] args){
        launch(args);
    }

}
