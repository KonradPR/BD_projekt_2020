package Gui;

import Entities.*;
import Handlers.DataAccessHandler;
import Handlers.DataModificationHandler;
import Handlers.TransactionHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.print.Doc;
import java.sql.Date;

public class AppFrame extends Application {

    private TransactionHandler trH = new TransactionHandler();
    private DataModificationHandler dmH = new DataModificationHandler();
    private DataAccessHandler acH = new DataAccessHandler();

    private AppScene scene;
    private AppScene patientScene;
    private AppScene doctorScene;
    private AppScene medicineScene;
    private AppScene supplierScene;
    private AppScene prescriptionScene;
    private MenuBar menuBar;

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Hospital Medicine Stockpile App");
        stage.setOnCloseRequest(event -> Platform.exit());
        //TODO refoactor jakas funckje do tworzenia menu i zrobić to w pętli jak człowiek
        //  labele to must żeby sie dało zrobić on clicka tako rzecze stackoverflow
        menuBar = new MenuBar();
        Label label1 = new Label("Pacjenci");
        label1.setOnMouseClicked(event->{  stage.setScene(patientScene); });
        Menu m1 = new Menu("",label1);
        Label label2 = new Label("Lekarze");
        label2.setOnMouseClicked(event->{ stage.setScene(doctorScene); });
        Menu m2 = new Menu("",label2);
        Label label3 = new Label("Dostawcy");
        label3.setOnMouseClicked(event->{ stage.setScene(supplierScene); });
        Menu m3 = new Menu("",label3);
        Label label4 = new Label("Leki");
        label4.setOnMouseClicked(event->{ stage.setScene(medicineScene); });
        Menu m4 = new Menu("",label4);
        Label label5 = new Label("Recepty");
        label5.setOnMouseClicked(event->{ stage.setScene(prescriptionScene); });
        Menu m5 = new Menu("",label5);
        menuBar.getMenus().add(m1);
        menuBar.getMenus().add(m2);
        menuBar.getMenus().add(m3);
        menuBar.getMenus().add(m4);
        menuBar.getMenus().add(m5);

        VBox patientLayout = new VBox(menuBar);
        patientLayout.getChildren().addAll(createPatientsTable());
        patientScene = new AppScene(patientLayout, 800, 600);

        VBox doctorLayout = new VBox(menuBar);
        doctorLayout.getChildren().addAll(createDoctorsTable());
        doctorScene = new AppScene(doctorLayout, 800, 600);

        VBox medicineLayout = new VBox(menuBar);
        medicineLayout.getChildren().addAll(createMedicineTable());
        medicineScene = new AppScene(medicineLayout, 800, 600);

        VBox supplierLayout = new VBox(menuBar);
        supplierLayout.getChildren().addAll(createSupplierTable());
        supplierScene = new AppScene(supplierLayout, 800, 600);

        VBox prescriptionLayout = new VBox(menuBar);
        prescriptionLayout.getChildren().addAll(createPrescriptionTable());
        prescriptionScene = new AppScene(prescriptionLayout, 800, 600);

        VBox rootNode = new VBox(menuBar);
        scene = new AppScene(rootNode, 800, 600);
        stage.setScene(scene);

        stage.show();
    }

    public void show(String[] args){
        launch(args);
    }

    //todo ocenic gdzie to powinno byc
    private ObservableList<Patient> getPatients() throws Exception {
        return FXCollections.observableList(acH.getAllPatients());
    }
    private TableView<Patient> createPatientsTable() throws Exception{
        TableColumn<Patient, Integer> idColumn = new TableColumn<>("ID Pacjenta");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("patientID"));

        TableColumn<Patient, String> nameColumn = new TableColumn<>("Imię");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Patient, String> surnameColumn = new TableColumn<>("Nazwisko");
        surnameColumn.setMinWidth(200);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<Patient, Date> dateOfBirthColumn = new TableColumn<>("Data Urodzenia");
        dateOfBirthColumn.setMinWidth(100);
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        TableColumn<Patient, String> genderColumn = new TableColumn<>("Płeć");
        genderColumn.setMinWidth(50);
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableView<Patient> patientTableView = new TableView<>();
        patientTableView.setItems(getPatients());
        patientTableView.getColumns().addAll(idColumn,nameColumn,surnameColumn,dateOfBirthColumn,genderColumn);

        return patientTableView;
    }

    private ObservableList<Doctor> getDoctors() throws Exception {
        return FXCollections.observableList(acH.getAllDoctors());
    }
    private TableView<Doctor> createDoctorsTable() throws Exception{
        TableColumn<Doctor, Integer> idColumn = new TableColumn<>("ID Doktora");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));

        TableColumn<Doctor, String> nameColumn = new TableColumn<>("Imię");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Doctor, String> surnameColumn = new TableColumn<>("Nazwisko");
        surnameColumn.setMinWidth(200);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<Doctor, Date> dateOfBirthColumn = new TableColumn<>("Data Urodzenia");
        dateOfBirthColumn.setMinWidth(100);
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        TableColumn<Doctor, String> phoneColumn = new TableColumn<>("Telefon");
        phoneColumn.setMinWidth(80);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Doctor, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setMinWidth(80);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Doctor, String> specialityColumn = new TableColumn<>("Specjalność");
        specialityColumn.setMinWidth(80);
        specialityColumn.setCellValueFactory(new PropertyValueFactory<>("speciality"));

        TableView<Doctor> doctorTableView = new TableView<>();
        doctorTableView.setItems(getDoctors());
        doctorTableView.getColumns().addAll(idColumn,nameColumn,surnameColumn,dateOfBirthColumn,phoneColumn,emailColumn,specialityColumn);

        return doctorTableView;
    }

    private ObservableList<Medicine> getMedicines() throws Exception {
        return FXCollections.observableList(acH.getAllMedicines());
    }
    private TableView<Medicine> createMedicineTable() throws Exception{
        TableColumn<Medicine, Integer> idColumn = new TableColumn<>("ID Leku");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("evidenceNumber"));

        TableColumn<Medicine, String> suggestedDoseColumn = new TableColumn<>("Dawka");
        suggestedDoseColumn.setMinWidth(200);
        suggestedDoseColumn.setCellValueFactory(new PropertyValueFactory<>("suggestedDose"));

        TableColumn<Medicine, Integer> inStockColumn = new TableColumn<>("Dostępne");
        inStockColumn.setMinWidth(100);
        inStockColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));

        TableView<Medicine> medicineTableView = new TableView<>();
        medicineTableView.setItems(getMedicines());
        medicineTableView.getColumns().addAll(idColumn,suggestedDoseColumn,inStockColumn);

        return medicineTableView;
    }

    private ObservableList<Supplier> getSuppliers() throws Exception {
        return FXCollections.observableList(acH.getAllSuppliers());
    }
    private TableView<Supplier> createSupplierTable() throws Exception{
        TableColumn<Supplier, Integer> idColumn = new TableColumn<>("ID Dostawcy");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("supplierID"));

        TableColumn<Supplier, String> companyNameColumn = new TableColumn<>("Nazwa Firmy");
        companyNameColumn.setMinWidth(200);
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));

        TableColumn<Supplier, String> phoneColumn = new TableColumn<>("Telefon");
        phoneColumn.setMinWidth(100);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        //todo do wymyslenia jak ogarnac adres
//        TableColumn<Supplier, String> addressColumn = new TableColumn<>("Adres");
//        phoneColumn.setMinWidth(300);
//        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableView<Supplier> supplierTableView = new TableView<>();
        supplierTableView.setItems(getSuppliers());
        supplierTableView.getColumns().addAll(idColumn,companyNameColumn,phoneColumn/*,addressColumn*/);

        return supplierTableView;
    }

    private ObservableList<Prescription> getPrescriptions() throws Exception {
        return FXCollections.observableList(acH.getAllPrescriptions());
    }
    private TableView<Prescription> createPrescriptionTable() throws Exception{
        TableColumn<Prescription, Integer> idColumn = new TableColumn<>("ID Recepty");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("prescriptionNumber"));

        TableColumn<Prescription, Date> givenColumn = new TableColumn<>("Ważna od");
        givenColumn.setMinWidth(100);
        givenColumn.setCellValueFactory(new PropertyValueFactory<>("given"));

        TableColumn<Prescription, Date> expiresColumn = new TableColumn<>("Ważna do");
        expiresColumn.setMinWidth(100);
        expiresColumn.setCellValueFactory(new PropertyValueFactory<>("expires"));

        TableView<Prescription> medicineTableView = new TableView<>();
        medicineTableView.setItems(getPrescriptions());
        medicineTableView.getColumns().addAll(idColumn,givenColumn,expiresColumn);

        return medicineTableView;
    }
}
