package mx.happylittlebox.repartidor.happylittleboxapp.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by miguelacio on 18/10/17.
 */

public class Product extends RealmObject{

    @PrimaryKey
    private String id;
    private String cantidad;
    private String name;

    public Product() {
        this.id = "";
        this.cantidad = "";
        this.name = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
