package mx.happylittlebox.repartidor.happylittleboxapp.models;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by miguelacio on 17/10/17.
 */

public class Order extends RealmObject{
    @PrimaryKey
    private String id;
    private String repartidor_id;
    private String venta_id;
    private String status;
    private String folio;
    private String ciudad;
    private String latitude;
    private String longitude;
    private String direccion_calle;
    private String direccion_no;
    private String direccion_colonia;
    private String direccion_cp;
    private String direccion_estado;
    private String direccion_ciudad;
    private RealmList<Product> productArrayList;

    public Order() {
        this.id = "";
        this.repartidor_id = "";
        this.venta_id = "";
        this.status = "";
        this.folio = "";
        this.ciudad = "";
        this.latitude = "";
        this.longitude = "";
        this.direccion_calle = "";
        this.direccion_no = "";
        this.direccion_colonia = "";
        this.direccion_cp = "";
        this.direccion_estado = "";
        this.direccion_ciudad = "";
        this.productArrayList = new RealmList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepartidor_id() {
        return repartidor_id;
    }

    public void setRepartidor_id(String repartidor_id) {
        this.repartidor_id = repartidor_id;
    }

    public String getVenta_id() {
        return venta_id;
    }

    public void setVenta_id(String venta_id) {
        this.venta_id = venta_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDireccion_calle() {
        return direccion_calle;
    }

    public void setDireccion_calle(String direccion_calle) {
        this.direccion_calle = direccion_calle;
    }

    public String getDireccion_no() {
        return direccion_no;
    }

    public void setDireccion_no(String direccion_no) {
        this.direccion_no = direccion_no;
    }

    public String getDireccion_colonia() {
        return direccion_colonia;
    }

    public void setDireccion_colonia(String direccion_colonia) {
        this.direccion_colonia = direccion_colonia;
    }

    public String getDireccion_cp() {
        return direccion_cp;
    }

    public void setDireccion_cp(String direccion_cp) {
        this.direccion_cp = direccion_cp;
    }

    public String getDireccion_estado() {
        return direccion_estado;
    }

    public void setDireccion_estado(String direccion_estado) {
        this.direccion_estado = direccion_estado;
    }

    public String getDireccion_ciudad() {
        return direccion_ciudad;
    }

    public void setDireccion_ciudad(String direccion_ciudad) {
        this.direccion_ciudad = direccion_ciudad;
    }

    public RealmList<Product> getProductArrayList() {
        return productArrayList;
    }

    public void setProductArrayList(RealmList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }
}
