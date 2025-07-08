package uas.melvin;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    private PersonService service = new PersonService();
    private ObservableList<Person> data = FXCollections.observableArrayList();
    private TableView<Person> table = new TableView<>();
    private TextField tfNama = new TextField();
    private TextField tfAlamat = new TextField();
    private Person selectedPerson = null;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("CRUD Person - JavaFX");

        // Kolom Tabel
        TableColumn<Person, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setMinWidth(50);

        TableColumn<Person, String> colNama = new TableColumn<>("Nama");
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colNama.setMinWidth(200);

        TableColumn<Person, String> colAlamat = new TableColumn<>("Alamat");
        colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat")); 
        colAlamat.setMinWidth(200);

        table.getColumns().addAll(colId, colNama, colAlamat);
        table.setItems(data);

        // Selection Listener
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedPerson = newVal;
            if (newVal != null) {
                tfNama.setText(newVal.getNama());
                tfAlamat.setText(newVal.getAlamat());
            }
        });

        tfNama.setPromptText("Nama");
        tfAlamat.setPromptText("Alamat");

        Button btnTambah = new Button("Tambah");
        Button btnUpdate = new Button("Update");
        Button btnHapus = new Button("Hapus");

        btnTambah.setOnAction(e -> {
            String nama = tfNama.getText().trim();
            String alamat = tfAlamat.getText().trim();

            if (!nama.isEmpty() && !alamat.isEmpty()) {  // ✅ diperbaiki
                service.tambahPerson(nama, alamat);
                refreshTable();
                clearForm();
            }
        });

        btnUpdate.setOnAction(e -> {
            if (selectedPerson != null) {
                String namaBaru = tfNama.getText().trim();
                String alamatBaru = tfAlamat.getText().trim();

                if (!namaBaru.isEmpty() && !alamatBaru.isEmpty()) {  // ✅ diperbaiki
                    service.updatePerson(selectedPerson.getId(), namaBaru, alamatBaru);
                    refreshTable();
                    clearForm();
                }
            }
        });

        btnHapus.setOnAction(e -> {
            if (selectedPerson != null) {
                service.hapusPerson(selectedPerson.getId());
                refreshTable();
                clearForm();
            }
        });

        HBox formBox = new HBox(10, tfNama, tfAlamat, btnTambah, btnUpdate, btnHapus);
        VBox root = new VBox(10, formBox, table);
        root.setPadding(new javafx.geometry.Insets(10));

        stage.setScene(new Scene(root, 700, 400));
        stage.show();

        refreshTable();
    }

    private void refreshTable() {
        data.setAll(service.getAll());
    }

    private void clearForm() {
        tfNama.clear();
        tfAlamat.clear();
        selectedPerson = null;
    }
}
