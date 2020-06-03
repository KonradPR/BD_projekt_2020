package Gui;

import Entities.*;
import Handlers.DataAccessHandler;
import Handlers.DataModificationHandler;
import Handlers.TransactionHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AppFrame extends Application {

    private TransactionHandler trH = new TransactionHandler();
    private DataModificationHandler dmH = new DataModificationHandler();
    private DataAccessHandler acH = new DataAccessHandler();

    private BorderPane borderPane;
    private Scene scene;
    private TableView<Patient> patientTable;
    private VBox panelPatient;
    private TableView<Doctor>  doctorTable;
    private VBox panelDoctor;
    private TableView<Medicine>  medicineTable;
    private VBox panelMedicine;
    private TableView<Supplier>  supplierTable;
    private VBox panelSupplier;
    private TableView<Prescription>  prescriptionTable;
    private VBox panelPrescription;
    private MenuBar menuBar;

    private TableView<Patient> patientCurOnMedTable;
    private TableView<Patient> patientWhoUsedMedTable;
    private TableView<PrescriptionElement> prescriptionElementTable;
    private TableView<Supplier> suppliersOfMedTable;
    private TableView<Medicine> medsFromSupplierTable;

    @Override
    public void start(Stage stage) throws Exception {
        borderPane = new BorderPane();
        menuBar = new MenuBar();

        stage.setTitle("Hospital Medicine Stockpile App");
        stage.setOnCloseRequest(event -> Platform.exit());

        //Tables
        patientTable = createPatientsTable();
        doctorTable = createDoctorsTable();
        supplierTable = createSupplierTable();
        medicineTable = createMedicineTable();
        prescriptionTable = createPrescriptionTable();

        //Forms
        GridPane patientForm = createFormPane();
        setUpPatientForm(patientForm);
        GridPane doctorForm = createFormPane();
        setUpDoctorForm(doctorForm);
        GridPane medicineForm = createFormPane();
        setUpMedicineFrom(medicineForm);
        GridPane prescriptionForm = createFormPane();
        setUpPrescriptionFrom(prescriptionForm);
        GridPane supplierForm = createFormPane();
        setUpSupplierFrom(supplierForm);

        GridPane doctorModificationForm = createFormPane();
        setUpDoctorModForm(doctorModificationForm);
        GridPane patientModificationForm = createFormPane();
        setUpPatientModForm(patientModificationForm);
        GridPane medicineOrderForm = createFormPane();
        setUpMedicineOrderForm(medicineOrderForm);
        GridPane supplierModificationForm = createFormPane();
        setUpSupplierModForm(supplierModificationForm);

        //Buttons & events
        Button addPatientButton = new Button("Nowy");
        addPatientButton.setOnAction(event -> { borderPane.setCenter(patientForm); });
        Button addDoctorButton = new Button("Nowy");
        addDoctorButton.setOnAction(event -> { borderPane.setCenter(doctorForm);});
        Button addMedicineButton = new Button("Nowy");
        addMedicineButton.setOnAction(event -> {borderPane.setCenter(medicineForm);});
        Button addPrescriptionButton = new Button("Nowy");
        addPrescriptionButton.setOnAction(event -> { borderPane.setCenter(prescriptionForm); });
        Button addSupplierButton = new Button("Nowy");
        addSupplierButton.setOnAction(event -> { borderPane.setCenter(supplierForm); });

        Button modPatientButton = new Button("Edytuj");
        modPatientButton.setOnAction(event -> borderPane.setCenter(patientModificationForm));
        Button modDoctorButton = new Button("Edytuj");
        modDoctorButton.setOnAction(event -> borderPane.setCenter(doctorModificationForm));
        Button modSupplierButton = new Button("Edytuj");
        modSupplierButton.setOnAction(event -> borderPane.setCenter(supplierModificationForm));
        Button placeOrderButton = new Button("Zamów");
        placeOrderButton.setOnAction(event -> borderPane.setCenter(medicineOrderForm));

        Button patientCurOnMedButton = new Button("Zużycie");
        patientCurOnMedButton.setOnAction(event -> {
            int id = idPopUpForm("ID Leku");
            try {
                patientCurOnMedTable = createPatientsCurOnMedTable(id);
                borderPane.setCenter(patientCurOnMedTable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button patientOnMedButton = new Button("Historia");
        patientOnMedButton.setOnAction(event -> {
            int id = idPopUpForm("ID Leku");
            try {
                patientWhoUsedMedTable = createPatientsWhoUsedMedTable(id);
                borderPane.setCenter(patientWhoUsedMedTable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button suppliersOfMedicineButton = new Button("Dostawcy");
        suppliersOfMedicineButton.setOnAction(event -> {
            int id = idPopUpForm("ID Leku");
            try {
                suppliersOfMedTable = createSupplierOfMedTable(id);
                borderPane.setCenter(suppliersOfMedTable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button medicineFromSupplier = new Button("Leki");
        medicineFromSupplier.setOnAction(event -> {
            int id = idPopUpForm("ID Dostawcy");
            try {
                medsFromSupplierTable = createMedicineFromSupplierTable(id);
                borderPane.setCenter(medsFromSupplierTable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button connectSupplierButton = new Button("Dodaj dostawcę");
        connectSupplierButton.setOnAction(event ->{
            int medId = idPopUpForm("ID Leku");
            int supId = idPopUpForm("ID Dostawcy");
            try{
                trH.addMedicineSupplier(acH.getMedicineById(medId),acH.getSupplierById(supId));
            }catch(Exception ex){
                System.out.println(ex);
            }
        });

        //Adding buttons to views
        Button [] buttonsPatient = {addPatientButton,modPatientButton};
        Button [] buttonsDoctor = {addDoctorButton,modDoctorButton};
        Button [] buttonsMedicine = {addMedicineButton,placeOrderButton,patientCurOnMedButton,patientOnMedButton,suppliersOfMedicineButton,connectSupplierButton};
        Button [] buttonsPrescription = {addPrescriptionButton};
        Button [] buttonsSupplier = {addSupplierButton,modSupplierButton,medicineFromSupplier};

        panelPatient = createRightSidePanel(buttonsPatient);
        panelDoctor = createRightSidePanel(buttonsDoctor);
        panelMedicine = createRightSidePanel(buttonsMedicine);
        panelPrescription = createRightSidePanel(buttonsPrescription);
        panelSupplier = createRightSidePanel(buttonsSupplier);

        createFieldInMenuBar("Pacjenci").setOnMouseClicked(event->{
            borderPane.setCenter(patientTable);
            borderPane.setRight(panelPatient);
        });
        createFieldInMenuBar("Lekarze").setOnMouseClicked(event->{
            borderPane.setCenter(doctorTable);
            borderPane.setRight(panelDoctor);
        });
        createFieldInMenuBar("Dostawcy").setOnMouseClicked(event->{
            borderPane.setCenter(supplierTable);
            borderPane.setRight(panelSupplier);
        });
        createFieldInMenuBar("Leki").setOnMouseClicked(event->{
            borderPane.setCenter(medicineTable);
            borderPane.setRight(panelMedicine);
        });
        createFieldInMenuBar("Recepty").setOnMouseClicked(event->{
            borderPane.setCenter(prescriptionTable);
            borderPane.setRight(panelPrescription);
        });

        borderPane.setTop(menuBar);
        borderPane.setCenter(new Label("Witaj w szpitalu!"));

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

    //Right side panel
    private VBox createRightSidePanel(Button [] buttons){ //zawsze mozna zmienic na Button[] buttons i zrobic fora
        VBox panel = new VBox();
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setPadding(new Insets(10));
        panel.setSpacing(10);
        panel.setPrefWidth(150);

        Label title = new Label("Opcje");
        title.setPadding(new Insets(0,5,5,5));
        title.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
        title.setFont(Font.font("Arial", 14));

        panel.getChildren().add(title);
        for(Button button : buttons){
            panel.getChildren().add(button);
        }

        return panel;
    }

    //Getters for tables
    private ObservableList<Patient> getPatients() throws Exception {
        return FXCollections.observableList(acH.getAllPatients());
    }
    private ObservableList<Patient> getPatientsCurOnMed(int medId) throws Exception {
        return FXCollections.observableList(acH.getAllPatientsCurrentlyOnMed(medId));
    }
    private ObservableList<Patient> getPatientsWhoUsedMed(int medId) throws Exception {
        return FXCollections.observableList(acH.getAllPatientsThatUsedMed(medId));
    }
    private ObservableList<Medicine> getMedicines() throws Exception {
        return FXCollections.observableList(acH.getAllMedicines());
    }
    private ObservableList<Medicine> getMedicinesFromSupplier(int supId) throws Exception {
        return FXCollections.observableList(acH.getMedicinesFromSupplier(supId));
    }
    private ObservableList<Doctor> getDoctors() throws Exception {
        return FXCollections.observableList(acH.getAllDoctors());
    }
    private ObservableList<Supplier> getSuppliers() throws Exception {
        return FXCollections.observableList(acH.getAllSuppliers());
    }
    private ObservableList<Supplier> getSuppliersOfMed(int medId) throws Exception {
        return FXCollections.observableList(acH.getMedicineSuppliers(medId));
    }
    private ObservableList<Prescription> getPrescriptions() throws Exception {
        return FXCollections.observableList(acH.getAllPrescriptions());
    }
    private ObservableList<PrescriptionElement> getPrescriptionsElements(int presId) throws Exception {
        return FXCollections.observableList(acH.getPrescriptionElements(presId));
    }

    //Tables with data
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
    private TableView<Patient> createPatientsCurOnMedTable(int medId) throws Exception{
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
        patientTableView.setItems(getPatientsCurOnMed(medId));
        patientTableView.getColumns().addAll(idColumn,nameColumn,surnameColumn,dateOfBirthColumn,genderColumn);

        return patientTableView;
    }
    private TableView<Patient> createPatientsWhoUsedMedTable(int medId) throws Exception{
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
        patientTableView.setItems(getPatientsWhoUsedMed(medId));
        patientTableView.getColumns().addAll(idColumn,nameColumn,surnameColumn,dateOfBirthColumn,genderColumn);

        return patientTableView;
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
    private TableView<Medicine> createMedicineTable() throws Exception{
        TableColumn<Medicine, Integer> idColumn = new TableColumn<>("ID Leku");
        idColumn.setMinWidth(25);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("evidenceNumber"));

        TableColumn<Medicine, String> nameColumn = new TableColumn<>("Nazwa");
        nameColumn.setMinWidth(80);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Medicine, String> suggestedDoseColumn = new TableColumn<>("Dawka");
        suggestedDoseColumn.setMinWidth(200);
        suggestedDoseColumn.setCellValueFactory(new PropertyValueFactory<>("suggestedDose"));

        TableColumn<Medicine, Integer> inStockColumn = new TableColumn<>("Dostępne");
        inStockColumn.setMinWidth(100);
        inStockColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));

        TableView<Medicine> medicineTableView = new TableView<>();
        medicineTableView.setItems(getMedicines());
        medicineTableView.getColumns().addAll(idColumn,nameColumn,suggestedDoseColumn,inStockColumn);

        return medicineTableView;
    }
    private TableView<Medicine> createMedicineFromSupplierTable(int supId) throws Exception{
        TableColumn<Medicine, Integer> idColumn = new TableColumn<>("ID Leku");
        idColumn.setMinWidth(25);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("evidenceNumber"));

        TableColumn<Medicine, String> nameColumn = new TableColumn<>("Nazwa");
        nameColumn.setMinWidth(80);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Medicine, String> suggestedDoseColumn = new TableColumn<>("Dawka");
        suggestedDoseColumn.setMinWidth(200);
        suggestedDoseColumn.setCellValueFactory(new PropertyValueFactory<>("suggestedDose"));

        TableColumn<Medicine, Integer> inStockColumn = new TableColumn<>("Dostępne");
        inStockColumn.setMinWidth(100);
        inStockColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));

        TableView<Medicine> medicineTableView = new TableView<>();
        medicineTableView.setItems(getMedicinesFromSupplier(supId));
        medicineTableView.getColumns().addAll(idColumn,nameColumn,suggestedDoseColumn,inStockColumn);

        return medicineTableView;
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
    private TableView<Supplier> createSupplierOfMedTable(int medId) throws Exception{
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
        supplierTableView.setItems(getSuppliersOfMed(medId));
        supplierTableView.getColumns().addAll(idColumn,companyNameColumn,phoneColumn,addressColumn);

        return supplierTableView;
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

        medicineTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    int presId = medicineTableView.getSelectionModel().getSelectedItem().getId();
                    try {
                        prescriptionElementTable = createPrescriptionElementsTable(presId);
                        borderPane.setCenter(prescriptionElementTable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return medicineTableView;
    }
    private TableView<PrescriptionElement> createPrescriptionElementsTable(int presId) throws Exception{
        TableColumn<PrescriptionElement, Prescription> prescriptionColumn = new TableColumn<>("ID Recepty");
        prescriptionColumn.setMinWidth(50);
        prescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("prescription"));

        TableColumn<PrescriptionElement, Medicine> medicineColumn = new TableColumn<>("ID Leku");
        medicineColumn.setMinWidth(50);
        medicineColumn.setCellValueFactory(new PropertyValueFactory<>("medicine"));

        TableColumn<PrescriptionElement, PrescriptionElementID> doseColumn = new TableColumn<>("Dawka");
        doseColumn.setMinWidth(100);
        doseColumn.setCellValueFactory(new PropertyValueFactory("dose"));

        TableView<PrescriptionElement> prescriptionElementTableView = new TableView<>();
        prescriptionElementTableView.setItems(getPrescriptionsElements(presId));
        prescriptionElementTableView.getColumns().addAll(prescriptionColumn,medicineColumn,doseColumn);

        return prescriptionElementTableView;
    }

    //Forms for adding entities
    private GridPane createFormPane(){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40,40,40,40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints columnConstraints1 = new ColumnConstraints(150,150,Double.MAX_VALUE);
        columnConstraints1.setHalignment(HPos.RIGHT);
        ColumnConstraints columnConstraints2 = new ColumnConstraints(150,150,Double.MAX_VALUE);
        columnConstraints2.setHgrow(Priority.SOMETIMES);
        gridPane.getColumnConstraints().addAll(columnConstraints1,columnConstraints2);
        return gridPane;
    }
    private void setUpPatientForm(GridPane gridPane){
        createFormHeader(gridPane,"Nowy Pacjent");

        TextField nameField = createFormTextFiled(gridPane,"Imie",1);
        TextField surnameField = createFormTextFiled(gridPane,"Nazwisko",2);
        DatePicker dateField = createFormDateField(gridPane,"Data Urodzenia",3);
        TextField genderField =createFormTextFiled(gridPane,"Płeć",4);
        Button submitButton = createSaveButton(gridPane,5);

        //Saving patient to database
        submitButton.setOnAction(event -> {
            if (nameField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz imię pacjenta!");
                return;
            }
            if (surnameField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz nazwisko pacjenta!");
                return;
            }
            if (dateField.getValue().toString().isEmpty()) { //XD
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz datę urodzenia!");
                return;
            }
            if (genderField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz płeć pacjenta!");
                return;
            }
            String name = nameField.getText();
            String surname = surnameField.getText();
            Date date = Date.valueOf(dateField.getValue());
            String gender = genderField.getText();
            try {
                trH.addPatient(name, surname, date, gender);
                //refresh table
                patientTable.getItems().clear();
                patientTable.getItems().addAll(getPatients());
                //change view back to patient list
                borderPane.setCenter(patientTable);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
    }
    private void setUpDoctorForm(GridPane gridPane){
        createFormHeader(gridPane,"Nowy Doktor");

        TextField nameField = createFormTextFiled(gridPane,"Imie",1);
        TextField surnameField = createFormTextFiled(gridPane,"Nazwisko",2);
        DatePicker dateField = createFormDateField(gridPane,"Data Urodzenia",3);
        TextField phoneField = createFormTextFiled(gridPane,"Telefon",4);
        TextField emailFiled = createFormTextFiled(gridPane,"Email",5);
        TextField speicialityField = createFormTextFiled(gridPane,"Specjalność",6);

        Button submitButton = createSaveButton(gridPane,7);
        //Saving doctor to database
        submitButton.setOnAction(event -> {
            if (nameField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz imię doktora!");
                return;
            }
            if (surnameField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz nazwisko doktora!");
                return;
            }
            if (dateField.getValue().toString().isEmpty()) { //XD
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz datę urodzenia!");
                return;
            }
            if (phoneField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz numer doktora!");
                return;
            }
            if (emailFiled.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz email doktora!");
                return;
            }
            if (speicialityField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz specjalność doktora!");
                return;
            }
            String name = nameField.getText();
            String surname = surnameField.getText();
            Date date = Date.valueOf(dateField.getValue());
            String phone = phoneField.getText();
            String email = emailFiled.getText();
            String speciality = speicialityField.getText();
            try {
                trH.addDoctor(name, surname, date, phone,email,speciality);
                //refresh table
                doctorTable.getItems().clear();
                doctorTable.getItems().addAll(getDoctors());
                //change view back to patient list
                borderPane.setCenter(doctorTable);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
    }
    private void setUpSupplierFrom(GridPane gridPane){
        createFormHeader(gridPane,"Nowy Dostawca");

        TextField companyNameField = createFormTextFiled(gridPane,"Nazwa Firmy",1);
        TextField phoneField = createFormTextFiled(gridPane,"Telefon",2);
        TextField streetField = createFormTextFiled(gridPane,"Ulica",3);
        TextField cityField = createFormTextFiled(gridPane,"Miasto",4);
        TextField zipField = createFormTextFiled(gridPane,"Kod Pocztowy",5);
        //Save button
        Button submitButton = createSaveButton(gridPane,6);

        //Saving patient to database
        submitButton.setOnAction(event -> {
            if (companyNameField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz nazwę firmy!");
                return;
            }
            if (phoneField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz numer telefonu!");
                return;
            }
            if (streetField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz ulicę!");
                return;
            }
            if (cityField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz miasto!");
                return;
            }
            if (zipField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz kod pocztowy!");
                return;
            }
            String companyName = companyNameField.getText();
            String phone = phoneField.getText();
            String street = streetField.getText();
            String city = cityField.getText();
            String zipCode = zipField.getText();

            try {
                trH.addSupplier(companyName, phone,street,city,zipCode);
                //refresh table
                supplierTable.getItems().clear();
                supplierTable.getItems().addAll(getSuppliers());
                //change view back to medicine list
                borderPane.setCenter(supplierTable);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
    }
    private void setUpMedicineFrom(GridPane gridPane){
        createFormHeader(gridPane,"Nowy Lek");

        TextField nameField = createFormTextFiled(gridPane,"Nazwa",1);
        TextField doseField = createFormTextFiled(gridPane,"Sugerowana Dawka",2);
        TextField inStockField = createFormTextFiled(gridPane,"Dostępne",3);

        Button submitButton = createSaveButton(gridPane,4);
        //Saving patient to database
        submitButton.setOnAction(event -> {
            if (nameField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz nazwę leku!");
                return;
            }
            if (doseField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz sugerowaną dawkę!");
                return;
            }
            if (inStockField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz dostępną ilość leku!");
                return;
            }
            String name = nameField.getText();
            String dose = doseField.getText();
            int inStock = Integer.parseInt(inStockField.getText());

            try {
                trH.addMedicine(name,dose, inStock);
                //refresh table
                medicineTable.getItems().clear();
                medicineTable.getItems().addAll(getMedicines());
                //change view back to medicine list
                borderPane.setCenter(medicineTable);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
    }
    private void setUpPrescriptionFrom(GridPane gridPane){
        createFormHeader(gridPane,"Nowa Recepta");

        DatePicker givenField = createFormDateField(gridPane,"Ważna od",1);
        DatePicker expiresField = createFormDateField(gridPane,"Ważna do",2);
        TextField doctorField = createFormTextFiled(gridPane,"ID Doktora",3);
        TextField patientField = createFormTextFiled(gridPane,"ID Pacjenta",4);

        TextField medsField = createFormTextFiled(gridPane,"ID Leku",5);
        TextField dosageFiled = createFormTextFiled(gridPane,"Dawka",6);
        List<String> dosages = new ArrayList<>();
        List<Medicine> medicines = new ArrayList<>();

        Button addMedButton = new Button("Dodaj lek");
        addMedButton.setPrefHeight(40);
        addMedButton.setDefaultButton(true);
        addMedButton.setPrefWidth(100);
        gridPane.add(addMedButton, 3, 6, 2, 1);
        GridPane.setHalignment(addMedButton, HPos.CENTER);
        GridPane.setMargin(addMedButton, new Insets(0, 0,0,0));

        addMedButton.setOnAction(event ->{
            if (medsField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz ID leku!");
                return;
            }
            if (dosageFiled.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz dawke!");
                return;
            }
            int medId = Integer.parseInt(medsField.getText());
            String dosage = dosageFiled.getText();
            try {
                if(acH.getMedicineById(medId) != null){
                    medicines.add(acH.getMedicineById(medId));
                    dosages.add(dosage);
                    showAlert(Alert.AlertType.CONFIRMATION,gridPane.getScene().getWindow(),"Ok","Dodano lek do recepty!");
                    medsField.clear();
                    dosageFiled.clear();
                }
                else{
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Lek nie istnieje", "Wybierz inny lek!");
                    return;
                }
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        });

        Button submitButton = createSaveButton(gridPane,7); //todo zmienic row na (chyba) 7 jak bedzie gotowa reszta
        submitButton.setOnAction(event -> {
            if (givenField.getValue().toString().isEmpty()) { //XD
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz datę wystawienia!");
                return;
            }
            if (expiresField.getValue().toString().isEmpty()) { //XD
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz datę ważności!");
                return;
            }
            if (doctorField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz ID doktora!");
                return;
            }
            if (patientField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz ID pacjenta!");
                return;
            }
            Date given = Date.valueOf(givenField.getValue());
            Date expires = Date.valueOf(expiresField.getValue());
            int doctorId = Integer.parseInt(doctorField.getText());
            int patientId = Integer.parseInt(patientField.getText());
            doctorField.clear();
            patientField.clear();
            try {
                trH.addPrescription(given, expires,doctorId,patientId,medicines,dosages);
                //refresh table
                prescriptionTable.getItems().clear();
                prescriptionTable.getItems().addAll(getPrescriptions());
                //change view back to medicine list
                borderPane.setCenter(prescriptionTable);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
    }

    //Forms for modifying entites
    private void setUpDoctorModForm(GridPane gridPane){
        createFormHeader(gridPane,"Nowy Doktor");

        TextField doctorIdField = createFormTextFiled(gridPane,"ID",1);
        TextField nameField = createFormTextFiled(gridPane,"Imie",2);
        TextField surnameField = createFormTextFiled(gridPane,"Nazwisko",3);
        TextField phoneField = createFormTextFiled(gridPane,"Telefon",4);
        TextField emailFiled = createFormTextFiled(gridPane,"Email",5);
        TextField speicialityField = createFormTextFiled(gridPane,"Specjalność",6);

        Button submitButton = createSaveButton(gridPane,7);
        //Saving doctor to database
        submitButton.setOnAction(event -> {
            if (doctorIdField.getText().isEmpty()) { //XD
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz ID Doktora!");
                return;
            }
            int Id = Integer.parseInt(doctorIdField.getText());
            String name =  nameField.getText().isEmpty() ? null : nameField.getText() ;
            String surname = surnameField.getText().isEmpty() ? null : surnameField.getText();
            String phone = phoneField.getText().isEmpty() ? null : phoneField.getText();
            String email = emailFiled.getText().isEmpty() ? null : emailFiled.getText();
            String speciality = speicialityField.getText().isEmpty() ? null : speicialityField.getText();
            try {
                dmH.modifyDoctorById(Id,email,name,phone,speciality,surname);
                //refresh table
                doctorTable.getItems().clear();
                doctorTable.getItems().addAll(getDoctors());
                //change view back to patient list
                borderPane.setCenter(doctorTable);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
    }
    private void setUpPatientModForm(GridPane gridPane){
        createFormHeader(gridPane,"Edytuj Pacjenta");

        TextField patientIDField = createFormTextFiled(gridPane,"ID",1);
        TextField nameField = createFormTextFiled(gridPane,"Imie",2);
        TextField surnameField = createFormTextFiled(gridPane,"Nazwisko",3);
        Button submitButton = createSaveButton(gridPane,4);

        //Saving patient to database
        submitButton.setOnAction(event -> {
            if (patientIDField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz ID pacjenta!");
                return;
            }
            String name =  nameField.getText().isEmpty() ? null : nameField.getText() ;
            String surname = surnameField.getText().isEmpty() ? null : surnameField.getText();
            int Id = Integer.parseInt(patientIDField.getText());
            try {
                dmH.modifyPatientById(Id,name,surname);
                //refresh table
                patientTable.getItems().clear();
                patientTable.getItems().addAll(getPatients());
                //change view back to patient list
                borderPane.setCenter(patientTable);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
    }
    private void setUpMedicineOrderForm(GridPane gridPane){
        createFormHeader(gridPane,"Zamów lek");

        TextField medIdField = createFormTextFiled(gridPane,"ID Leku",1);
        TextField supIdField = createFormTextFiled(gridPane,"ID Dostawcy",2);
        TextField quantityField = createFormTextFiled(gridPane,"Ilość",3);
        Button submitButton = createSaveButton(gridPane,4);

        //Saving patient to database
        submitButton.setOnAction(event -> {
            if (medIdField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz ID leku!");
                return;
            }
            if (supIdField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz ID dostawcy!");
                return;
            }
            if (quantityField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz ilość leku do zamówienia!");
                return;
            }
            int medId =  Integer.parseInt(medIdField.getText());
            int supId =  Integer.parseInt(supIdField.getText());
            int quantity =  Integer.parseInt(quantityField.getText());
            try {
                dmH.placeOrderForMedicine(medId,quantity,supId);
                //refresh table
                medicineTable.getItems().clear();
                medicineTable.getItems().addAll(getMedicines());
                //change view back to patient list
                borderPane.setCenter(medicineTable);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
    }
    private void setUpSupplierModForm(GridPane gridPane){
        createFormHeader(gridPane,"Edytuj Dostawcę");

        TextField supplierIdField = createFormTextFiled(gridPane,"ID",1);
        TextField companyNameField = createFormTextFiled(gridPane,"Nazwa Firmy",2);
        TextField phoneField = createFormTextFiled(gridPane,"Telefon",3);
        TextField streetField = createFormTextFiled(gridPane,"Ulica",4);
        TextField cityField = createFormTextFiled(gridPane,"Miasto",5);
        TextField zipField = createFormTextFiled(gridPane,"Kod Pocztowy",6);
        //Save button
        Button submitButton = createSaveButton(gridPane,7);

        submitButton.setOnAction(event -> {
            if (supplierIdField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz ID dostawcy!");
                return;
            }
            int id = Integer.parseInt(supplierIdField.getText());
            String companyName = companyNameField.getText().isEmpty() ? null : companyNameField.getText();
            String phone = phoneField.getText().isEmpty() ? null : phoneField.getText();
            String street = streetField.getText().isEmpty() ? null : streetField.getText();
            String city = cityField.getText().isEmpty() ? null : cityField.getText();
            String zipCode = zipField.getText().isEmpty() ? null : zipField.getText();

            try {
                dmH.modifySupplierById(id,companyName,street,city,zipCode,phone);
                //refresh table
                supplierTable.getItems().clear();
                supplierTable.getItems().addAll(getSuppliers());
                borderPane.setCenter(supplierTable);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
    }

    //Helpers (creating textfield etc)
    private TextField createFormTextFiled(GridPane gridPane, String labelText, int row){
        Label label = new Label(labelText + " : ");
        gridPane.add(label,0,row);
        TextField textField = new TextField();
        textField.setPrefHeight(40);
        gridPane.add(textField, 1, row);
        return textField;
    }
    private DatePicker createFormDateField(GridPane gridPane,String labelText,int row){
        // Add Date Label
        Label label = new Label(labelText + " : ");
        gridPane.add(label, 0, row);
        // Add DatePicker Field
        DatePicker datePicker = new DatePicker();
        datePicker.setPrefHeight(40);
        gridPane.add(datePicker, 1, row);
        return datePicker;
    }
    private void createFormHeader(GridPane gridPane, String labelText){
        Label headerLabel = new Label(labelText);
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
    }
    private Button createSaveButton(GridPane gridPane,int row){
        Button submitButton = new Button("Zapisz");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, row, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0,20,0));
        return submitButton;
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    private int idPopUpForm(String textLabel){
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Wpisz ID");
        Label label1= new Label(textLabel);
        TextField idField =  new TextField();
        Button button1 = new Button("Szukaj");
        final int[] id = {0};
        button1.setOnAction(e -> {
            id[0] = idField.getText().isEmpty() ? 0 : Integer.parseInt(idField.getText());
            popupWindow.close();
        });
        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1,idField, button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupWindow.setScene(scene1);
        popupWindow.showAndWait();
        return id[0];
    }
}
