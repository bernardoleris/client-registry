package org.openjfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Clientes {
    private final StringProperty id;
    private final StringProperty nome;
    private final StringProperty email;
    private final StringProperty telefone;
    private final StringProperty documento;
    private final StringProperty data;

    public Clientes() {
        id = new SimpleStringProperty(this, "id");
        nome = new SimpleStringProperty(this, "nome");
        email = new SimpleStringProperty(this, "email");
        telefone = new SimpleStringProperty(this, "telefone");
        documento = new SimpleStringProperty(this, "documento");
        data = new SimpleStringProperty(this, "data");
    }

    public StringProperty idProperty() { return id; }
    public String getId() { return id.get(); }
    public void setId(String newId) { id.set(newId); }

    public StringProperty nomeProperty() { return nome; }
    public String getNome() { return nome.get(); }
    public void setNome(String newNome) { nome.set(newNome); }

    public StringProperty emailProperty() { return email; }
    public String getEmail() { return email.get(); }
    public void setEmail(String newEmail) { email.set(newEmail); }

    public StringProperty telefoneProperty() { return telefone; }
    public String getTelefone() { return telefone.get(); }
    public void setTelefone(String newTelefone) { telefone.set(newTelefone); }

    public StringProperty documentoProperty() { return documento; }
    public String getDocumento() { return documento.get(); }
    public void setDocumento(String newDocumento) { documento.set(newDocumento); }

    public StringProperty dataProperty() { return data; }
    public String getData() { return data.get(); }
    public void setData(String newData) { data.set(newData); }
}
