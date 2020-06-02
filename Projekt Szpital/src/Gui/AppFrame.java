package Gui;

import Entities.*;
import Handlers.DataAccessHandler;
import Handlers.DataModificationHandler;
import Handlers.TransactionHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

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

        Button addPatientButton = new Button("Dodaj Pacjenta");
        addPatientButton.setOnAction(event -> {
            borderPane.setCenter(patientForm);
        });
        Button addDoctorButton = new Button("Dodaj Doktora");
        addDoctorButton.setOnAction(event -> {
            borderPane.setCenter(doctorForm);
        });
        Button addMedicineButton = new Button("Dodaj Lek");
        addMedicineButton.setOnAction(event -> {
            borderPane.setCenter(medicineForm);
        });
        Button addPrescriptionButton = new Button("Dodaj Recepte");
        addPrescriptionButton.setOnAction(event -> {
            borderPane.setCenter(prescriptionForm);
        });
        Button addSupplierButton = new Button("Dodaj Dostawce");
        addSupplierButton.setOnAction(event -> {
            borderPane.setCenter(supplierForm);
        });

        buttonsPatient = createRightSidePanel(addPatientButton);
        buttonsDoctor = createRightSidePanel(addDoctorButton);
        buttonsMedicine = createRightSidePanel(addMedicineButton);
        buttonsPrescription = createRightSidePanel(addPrescriptionButton);
        buttonsSupplier = createRightSidePanel(addSupplierButton);

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

    //Right side panel
    private VBox createRightSidePanel(Button button){ //zawsze mozna zmienic na Button[] buttons i zrobic fora
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
        panel.getChildren().add(button);

        return panel;
    }

    //Tables with data
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

    //Forms for adding entities
    private GridPane createFormPane(){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40,40,40,40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints columnConstraints1 = new ColumnConstraints(150,100,Double.MAX_VALUE);
        columnConstraints1.setHalignment(HPos.RIGHT);
        ColumnConstraints columnConstraints2 = new ColumnConstraints(200,200,Double.MAX_VALUE);
        columnConstraints2.setHgrow(Priority.ALWAYS);
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
            String surname = speicialityField.getText();
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
    //todo ogarnac czemu wpisana dawnka nie chce przejsc weryfikacji regexa
    private void setUpMedicineFrom(GridPane gridPane){
        createFormHeader(gridPane,"Nowy Lek");

        TextField doseField = createFormTextFiled(gridPane,"Sugerowana Dawka",1);
        TextField inStockField = createFormTextFiled(gridPane,"Dostępne",2);

        Button submitButton = createSaveButton(gridPane,3);
        //Saving patient to database
        submitButton.setOnAction(event -> {
            if (doseField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz sugerowaną dawkę!");
                return;
            }
            if (inStockField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Puste pole!", "Wpisz dostępną ilość leku!");
                return;
            }
            String dose = doseField.getText();
            int inStock = Integer.parseInt(inStockField.getText());

            try {
                trH.addMedicine(dose, inStock);
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

        TextField medsField = createFormTextFiled(gridPane,"Ważna do",2); // todo wtf jak to ogarnac
        TextField expiersField = createFormTextFiled(gridPane,"Ważna do",2); //i to też ^

        Button submitButton = createSaveButton(gridPane,5); //todo zmienic row na (chyba) 7 jak bedzie gotowa reszta
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

            try {
                trH.addPrescription(given, expires,doctorId,patientId,null,null); //todo zmienic meds i dosages
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
}
