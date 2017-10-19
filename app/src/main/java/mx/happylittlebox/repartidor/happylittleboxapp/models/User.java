package mx.happylittlebox.repartidor.happylittleboxapp.models;

/**
 * Created by miguelacio on 17/10/17.
 */

public class User {
    private String usuario;
    private String api;
    private String name;
    private String aMaterno;
    private String aPaterno;
    private String celular;
    private String repartidor_id;

    public User() {
        this.usuario = "";
        this.api = "";
        this.name = "";
        this.aMaterno = "";
        this.aPaterno = "";
        this.celular = "";
        this.repartidor_id = "";
    }

    public String getaMaterno() {
        return aMaterno;
    }

    public void setaMaterno(String aMaterno) {
        this.aMaterno = aMaterno;
    }

    public String getaPaterno() {
        return aPaterno;
    }

    public void setaPaterno(String aPaterno) {
        this.aPaterno = aPaterno;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getRepartidor_id() {
        return repartidor_id;
    }

    public void setRepartidor_id(String repartidor_id) {
        this.repartidor_id = repartidor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
