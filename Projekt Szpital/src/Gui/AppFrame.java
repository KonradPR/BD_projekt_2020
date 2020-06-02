package Gui;

import Entities.*;
import Handlers.DataAccessHandler;
import Handlers.DataModificationHandler;
import Handlers.TransactionHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Date;

public class AppFrame extends Application {

    private TransactionHandler trH = new TransactionHandler();
    private DataModificationHandler dmH = new DataModificationHandler();
    private DataAccessHandler acH = new DataAccessHandler();

    private BorderPane borderPane;
    private Scene scene;
    private TableView<Patient> patientTable;
    private VBox buttonsPatient;
    private TableView<Doctor>  doctorTable;
    private VBox buttonsDoctor;
    private TableView<Medicine>  medicineTable;
    private VBox buttonsMedicine;
    private TableView<Supplier>  supplierTable;
    private VBox buttonsSupplier;
    private TableView<Prescription>  prescriptionTable;
    private VBox buttonsPrescription;
    private MenuBar menuBar;

    @Override
    public void start(Stage stage) throws Exception{
        borderPane = new BorderPane();
        menuBar = new MenuBar();

        stage.setTitle("Hospital Medicine Stockpile App");
        stage.setOnCloseRequest(event -> Platform.exit());

        patientTable = createPatientsTable();
        doctorTable = createDoctorsTable();
        supplierTable = createSupplierTable();
        medicineTable = createMedicineTable();
        prescriptionTable = createPrescriptionTable();

        Button addPatientButton = new Button("Dodaj Pacjenta");
        addPatientButton.setOnAction(event -> {
            System.out.println("nowy pacjent");
        });
        Button addDoctorButton = new Button("Dodaj Doktora");
        Button addMedicineButton = new Button("Dodaj Lek");
        Button addPrescriptionButton = new Button("Dodaj Recepte");
        Button addSupplierButton = new Button("Dodaj Dostawce");

        buttonsPatient = new VBox(addPatientButton);
        buttonsDoctor = new VBox(addDoctorButton);
        buttonsMedicine = new VBox(addMedicineButton);
        buttonsPrescription = new VBox(addPrescriptionButton);
        buttonsSupplier = new VBox(addSupplierButton);

        createFieldInMenuBar("Pacjenci").setOnMouseClicked(event->{
            borderPane.setCenter(patientTable);
            borderPane.setRight(buttonsPatient);
        });
        createFieldInMenuBar("Lekarze").setOnMouseClicked(event->{
            borderPane.setCenter(doctorTable);
            borderPane.setRight(buttonsDoctor);
        });
        createFieldInMenuBar("Dostawcy").setOnMouseClicked(event->{
            borderPane.setCenter(supplierTable);
            borderPane.setRight(buttonsSupplier);
        });
        createFieldInMenuBar("Leki").setOnMouseClicked(event->{
            borderPane.setCenter(medicineTable);
            borderPane.setRight(buttonsMedicine);
        });
        createFieldInMenuBar("Recepty").setOnMouseClicked(event->{
            borderPane.setCenter(prescriptionTable);
            borderPane.setRight(buttonsPrescription);
        });

        borderPane.setTop(menuBar);
        borderPane.setCenter(new Label("Witaj w szpitalu! Cieszymy się że nie jesteś martwy"));

        scene = new Scene(borderPane, 800, 600);
        stage.setScene(scene);

        stage.show();
    }
    public void show(String[] args){
        launch(args);
    }

    private Label createFieldInMenuBar(String labelText){
        Label label = new Label(labelText);
        Menu menu = new Menu("",label);
        this.menuBar.getMenus().add(menu);
        return label;
    }

    //todo ocenic gdzie to powinno byc
    private ObservableList<Patient> getPatients() throws Exception {
        return FXCollections.observableList(acH.getAllPatients());
    }
    private TableView<Patient> createPatientsTable() throws Exception{
        TableColumn<Patient, Integer> idColumn = new TableColumn<>("ID Pacjenta");
        idColumn.setMinWidth(25);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("patientID"));

        TableColumn<Patient, String> nameColumn = new TableColumn<>("Imię");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Patient, String> surnameColumn = new TableColumn<>("Nazwisko");
        surnameColumn.setMinWidth(100);
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
        idColumn.setMinWidth(25);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));

        TableColumn<Doctor, String> nameColumn = new TableColumn<>("Imię");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Doctor, String> surnameColumn = new TableColumn<>("Nazwisko");
        surnameColumn.setMinWidth(100);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<Doctor, Date> dateOfBirthColumn = new TableColumn<>("Data Urodzenia");
        dateOfBirthColumn.setMinWidth(100);
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        TableColumn<Doctor, String> phoneColumn = new TableColumn<>("Telefon");
        phoneColumn.setMinWidth(80);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Doctor, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setMinWidth(100);
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
        idColumn.setMinWidth(25);
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
        idColumn.setMinWidth(25);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("supplierID"));

        TableColumn<Supplier, String> companyNameColumn = new TableColumn<>("Nazwa Firmy");
        companyNameColumn.setMinWidth(200);
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));

        TableColumn<Supplier, String> phoneColumn = new TableColumn<>("Telefon");
        phoneColumn.setMinWidth(100);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Supplier, Address> addressColumn = new TableColumn<>("Adres");
        addressColumn.setMinWidth(300);
        addressColumn.setCellValueFactory(new PropertyValueFactory("address"));

        TableView<Supplier> supplierTableView = new TableView<>();
        supplierTableView.setItems(getSuppliers());
        supplierTableView.getColumns().addAll(idColumn,companyNameColumn,phoneColumn,addressColumn);

        return supplierTableView;
    }

    private ObservableList<Prescription> getPrescriptions() throws Exception {
        return FXCollections.observableList(acH.getAllPrescriptions());
    }
    private TableView<Prescription> createPrescriptionTable() throws Exception{
        TableColumn<Prescription, Integer> idColumn = new TableColumn<>("ID Recepty");
        idColumn.setMinWidth(25);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("prescriptionNumber"));

        TableColumn<Prescription, Date> givenColumn = new TableColumn<>("Ważna od");
        givenColumn.setMinWidth(100);
        givenColumn.setCellValueFactory(new PropertyValueFactory<>("given"));

        TableColumn<Prescription, Date> expiresColumn = new TableColumn<>("Ważna do");
        expiresColumn.setMinWidth(100);
        expiresColumn.setCellValueFactory(new PropertyValueFactory<>("expires"));

        TableColumn<Prescription,String> doctorColumn = new TableColumn<>("Lekarz");
        doctorColumn.setMinWidth(100);
        doctorColumn.setCellValueFactory(new PropertyValueFactory<>("doctorFullName"));

        TableColumn<Prescription,String> patientColumn = new TableColumn<>("Pacjent");
        patientColumn.setMinWidth(100);
        patientColumn.setCellValueFactory(new PropertyValueFactory<>("patientFullName"));

        TableView<Prescription> medicineTableView = new TableView<>();
        medicineTableView.setItems(getPrescriptions());
        medicineTableView.getColumns().addAll(idColumn,givenColumn,expiresColumn,doctorColumn,patientColumn);

        return medicineTableView;
    }
}
