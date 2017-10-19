package mx.happylittlebox.repartidor.happylittleboxapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.RealmList;
import mx.happylittlebox.repartidor.happylittleboxapp.models.Order;
import mx.happylittlebox.repartidor.happylittleboxapp.models.Product;
import mx.happylittlebox.repartidor.happylittleboxapp.models.User;

/**
 * Created by miguelacio on 17/10/17.
 */

public class JSONParser {

    public static User parseUserJSON(JSONObject response) throws JSONException {


        User u = new User();

        if (response.has(Keys.KEY_NOMBRES)) {
            u.setName(response.getString(Keys.KEY_NOMBRES));
        }
        if (response.has(Keys.KEY_REPARTIDOR_ID)) {
            u.setRepartidor_id(response.getString(Keys.KEY_REPARTIDOR_ID));
        }
        if (response.has(Keys.KEY_AMATERNO)) {
            u.setaMaterno(response.getString(Keys.KEY_AMATERNO));
        }
        if (response.has(Keys.KEY_APATERNO)) {
            u.setaPaterno(response.getString(Keys.KEY_APATERNO));
        }
        if (response.has(Keys.KEY_APATERNO)) {
            u.setaPaterno(response.getString(Keys.KEY_APATERNO));
        }
        if (response.has(Keys.KEY_CELULAR)) {
            u.setCelular(response.getString(Keys.KEY_CELULAR));
        }

        return u;
    }

    public static ArrayList<Order> parseOrdersJSON(JSONObject response) throws JSONException {
        ArrayList<Order> orderArrayList = new ArrayList<>();
        JSONArray jsonArrayOrders = response.getJSONArray(Keys.KEY_RESULTADO);
        Product p;
        Order o;

        for (int i = 0; i < jsonArrayOrders.length(); i++) {
            JSONObject jsonObjectOrder = jsonArrayOrders.getJSONObject(i);
            JSONObject jsonObjectVenta = jsonObjectOrder.getJSONObject(Keys.KEY_VENTA);
            o = new Order();
            o.setId(jsonObjectOrder.getString(Keys.KEY_VENTA_ID));
            o.setStatus(jsonObjectOrder.getString(Keys.KEY_STATUS));
            o.setFecha_venta(jsonObjectVenta.getString(Keys.KEY_FECHA_VENTA));
            o.setLatitude(jsonObjectVenta.getString(Keys.KEY_LATITUDE));
            o.setLongitude(jsonObjectVenta.getString(Keys.KEY_LONGITUDE));
            o.setLatitude(jsonObjectVenta.getString(Keys.KEY_LATITUDE));
            o.setDireccion_calle(jsonObjectVenta.getString(Keys.KEY_DIRECCION_CALLE));
            o.setDireccion_no(jsonObjectVenta.getString(Keys.KEY_DIRECCION_NO));
            o.setDireccion_colonia(jsonObjectVenta.getString(Keys.KEY_DIRECCION_COLONIA));
            o.setDireccion_cp(jsonObjectVenta.getString(Keys.KEY_DIRECCION_CP));
            o.setDireccion_ciudad(jsonObjectVenta.getString(Keys.KEY_DIRECCION_CIUDAD));
            o.setDireccion_estado(jsonObjectVenta.getString(Keys.KEY_DIRECCION_ESTADO));
            JSONArray jsonArrayItems = jsonObjectVenta.getJSONArray(Keys.KEY_ITEMS);
            RealmList<Product> products = new RealmList<>();

            for (int j = 0; j < jsonArrayItems.length(); j++) {
                p = new Product();
                JSONObject jsonObjectProduct = jsonArrayItems.getJSONObject(j);
                JSONObject jsonObjectdetail = jsonObjectProduct.getJSONObject(Keys.KEY_PRODUCTO);
                p.setId(jsonObjectProduct.getString(Keys.KEY_ID));
                p.setCantidad(jsonObjectProduct.getString(Keys.KEY_CANTIDAD));
                p.setName(jsonObjectdetail.getString(Keys.KEY_PRODUCTO_NOMBRE));
                products.add(p);
            }
            o.setProductArrayList(products);
            orderArrayList.add(o);

        }

        return orderArrayList;
    }

}
