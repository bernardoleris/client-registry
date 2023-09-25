package org.openjfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {
    PreparedStatement pst;
    Connection con;
    int myIndex;
    int id;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnInsert;

    @FXML
    private TableColumn<Clientes, String> dataColumn;

    @FXML
    private TableColumn<Clientes, String> documentoColumn;

    @FXML
    private TableColumn<Clientes, String> emailColumn;

    @FXML
    private TableColumn<Clientes, String> idColumn;

    @FXML
    private TableColumn<Clientes, String> nomeColumn;

    @FXML
    private TableView<Clientes> table;

    @FXML
    private TableColumn<Clientes, String> telefoneColumn;

    @FXML
    private TextField txtData;

    @FXML
    private TextField txtDocumento;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone;

    @FXML
    void Delete(ActionEvent event) {
        if (table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nenhum Item Selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um registro para excluir.");
            alert.showAndWait();
            return;
        }

        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));

        try {
            pst = con.prepareStatement("DELETE FROM clientes WHERE id = ?");
            pst.setInt(1, id);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro de Clientes");
            alert.setHeaderText("Registro de Clientes");
            alert.setContentText("Registro excluído com sucesso.");
            alert.showAndWait();

            table();
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void Update(ActionEvent event) {
        String nome, email, telefone, documento, data;

        if (table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nenhum Item Selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um registro para atualizar.");
            alert.showAndWait();
            return;
        }

        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));

        nome = txtNome.getText().trim();
        email = txtEmail.getText().trim();
        telefone = txtTelefone.getText();
        documento = txtDocumento.getText();
        data = txtData.getText();

        if (!telefone.matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Telefone Inválido");
            alert.setHeaderText(null);
            alert.setContentText("O campo telefone deve conter apenas números e sem espaçamento entre eles.");
            alert.showAndWait();
            return;
        }

        if (!documento.matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Documento Inválido");
            alert.setHeaderText(null);
            alert.setContentText("O campo documento deve conter apenas números e sem espaçamento entre eles.");
            alert.showAndWait();
            return;
        }

        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || documento.isEmpty() || data.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos em Branco");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos antes de atualizar o registro.");
            alert.showAndWait();
            return;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!email.matches(emailRegex)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Email Inválido");
            alert.setHeaderText(null);
            alert.setContentText("Insira um email válido.");
            alert.showAndWait();
            return;
        }

        String dataRegex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";

        if (!data.matches(dataRegex)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Formato de Data Inválido");
            alert.setHeaderText(null);
            alert.setContentText("Insira a data no formato DD/MM/AAAA.");
            alert.showAndWait();
            return;
        }

        try {
            PreparedStatement checkDocumento = con.prepareStatement("SELECT id FROM clientes WHERE documento = ? AND id != ?");
            checkDocumento.setString(1, documento);
            checkDocumento.setInt(2, id);
            ResultSet rs = checkDocumento.executeQuery();

            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Documento Duplicado");
                alert.setHeaderText(null);
                alert.setContentText("O documento já existe na base de dados.");
                alert.showAndWait();
                return;
            }

            pst = con.prepareStatement("UPDATE clientes SET nome = ?, email = ?, telefone = ?, documento = ?, data_nascfund = ? WHERE id = ?");
            pst.setString(1, nome);
            pst.setString(2, email);
            pst.setString(3, telefone);
            pst.setString(4, documento);
            pst.setString(5, data);
            pst.setInt(6, id);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro de Clientes");
            alert.setHeaderText("Registro de Clientes");
            alert.setContentText("Registro atualizado com sucesso.");
            alert.showAndWait();

            table();
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    void Insert(ActionEvent event) {
        String nome, email, telefone, documento, data;

        nome = txtNome.getText().trim();
        email = txtEmail.getText().trim();
        telefone = txtTelefone.getText();
        documento = txtDocumento.getText();
        data = txtData.getText();

        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || documento.isEmpty() || data.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos em Branco");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos antes de adicionar o registro.");
            alert.showAndWait();
            return;
        }

        if (!telefone.matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Telefone Inválido");
            alert.setHeaderText(null);
            alert.setContentText("O número de telefone deve conter apenas números e sem espaçamento entre eles.");
            alert.showAndWait();
            return;
        }

        if (!documento.matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Documento Inválido");
            alert.setHeaderText(null);
            alert.setContentText("O documento deve conter apenas números e sem espaçamento entre eles.");
            alert.showAndWait();
            return;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!email.matches(emailRegex)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Email Inválido");
            alert.setHeaderText(null);
            alert.setContentText("Insira um email válido.");
            alert.showAndWait();
            return;
        }

        String dataRegex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";

        if (!data.matches(dataRegex)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Formato de Data Inválido");
            alert.setHeaderText(null);
            alert.setContentText("Insira a data no formato DD/MM/AAAA.");
            alert.showAndWait();
            return;
        }

        try {
            pst = con.prepareStatement("INSERT INTO clientes (nome, email, telefone, documento, data_nascfund) VALUES (?, ?, ?, ?, ?)");
            pst.setString(1, nome);
            pst.setString(2, email);
            pst.setString(3, telefone);
            pst.setString(4, documento);
            pst.setString(5, data);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro de Clientes");
            alert.setHeaderText("Registro de Clientes");
            alert.setContentText("Registro adicionado com sucesso.");
            alert.showAndWait();

            table();

            txtNome.setText("");
            txtEmail.setText("");
            txtTelefone.setText("");
            txtDocumento.setText("");
            txtData.setText("");

            txtNome.requestFocus();
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("23505")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Documento Duplicado");
                alert.setHeaderText(null);
                alert.setContentText("O documento já existe na base de dados.");
                alert.showAndWait();
            } else {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        con = connect("Clientes", "postgres", "1234");
        table();
    }

    public void table() {
        ObservableList<Clientes> clientes = FXCollections.observableArrayList();
        try {
            pst = con.prepareStatement("SELECT id, nome, email, telefone, documento, data_nascfund FROM clientes");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Clientes st = new Clientes();
                st.setId(rs.getString("id"));
                st.setNome(rs.getString("nome"));
                st.setEmail(rs.getString("email"));
                st.setTelefone(rs.getString("telefone"));
                st.setDocumento(rs.getString("documento"));
                st.setData(rs.getString("data_nascfund"));
                clientes.add(st);
            }

            table.setItems(clientes);

            idColumn.setCellValueFactory(f -> f.getValue().idProperty());
            nomeColumn.setCellValueFactory(f -> f.getValue().nomeProperty());
            emailColumn.setCellValueFactory(f -> f.getValue().emailProperty());
            telefoneColumn.setCellValueFactory(f -> f.getValue().telefoneProperty());
            documentoColumn.setCellValueFactory(f -> f.getValue().documentoProperty());
            dataColumn.setCellValueFactory(f -> f.getValue().dataProperty());

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setRowFactory(tv -> {
            TableRow<Clientes> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table.getSelectionModel().getSelectedIndex();
                    id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));

                    txtNome.setText(table.getItems().get(myIndex).getNome());
                    txtEmail.setText(table.getItems().get(myIndex).getEmail());
                    txtTelefone.setText(table.getItems().get(myIndex).getTelefone());
                    txtDocumento.setText(table.getItems().get(myIndex).getDocumento());
                    txtData.setText(table.getItems().get(myIndex).getData());
                }
            });
            return myRow;
        });
    }

    public Connection connect(String dbname, String user, String pass){
        Connection conn = null;
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
            if(conn!=null){
                System.out.println("Connection established");
            }else{
                System.out.println("Connection failed");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return conn;
    }
}
