package mx.happylittlebox.repartidor.happylittleboxapp;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import mx.happylittlebox.repartidor.happylittleboxapp.models.Order;
import mx.happylittlebox.repartidor.happylittleboxapp.models.Product;
import mx.happylittlebox.repartidor.happylittleboxapp.models.User;
import mx.happylittlebox.repartidor.happylittleboxapp.utils.DialogUtil;
import mx.happylittlebox.repartidor.happylittleboxapp.utils.JSONParser;
import mx.happylittlebox.repartidor.happylittleboxapp.utils.Keys;
import mx.happylittlebox.repartidor.happylittleboxapp.utils.ProductAdapter;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    Realm realm;
    Order order = new Order();
    Button buttonGoToMap, buttonChangeStatus;
    TextView textViewDireccion;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        buttonGoToMap = (Button) findViewById(R.id.button_go_to_map);
        buttonChangeStatus = (Button) findViewById(R.id.button_change_status);
        textViewDireccion = (TextView) findViewById(R.id.text_view_direccion);
        requestQueue = Volley.newRequestQueue(this);

        recyclerView = (RecyclerView) findViewById(R.id.productos_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DetailActivity.this);
        recyclerView.setLayoutManager(layoutManager);



        buttonGoToMap.setOnClickListener(this);
        buttonChangeStatus.setOnClickListener(this);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        String id = getIntent().getExtras().getString("id");


        realm.beginTransaction();
        order = realm.where(Order.class).equalTo("id", id).findFirst();
        realm.commitTransaction();

        textViewDireccion.setText(order.getDireccion_calle() + " "
                + order.getDireccion_no() + " "
                + order.getDireccion_colonia() + " "
                + order.getDireccion_cp() + " "
                + order.getDireccion_ciudad() + " "
                + order.getDireccion_estado() + " ");

        RealmList<Product> products = order.getProductArrayList();
        ArrayList<Product> productArrayList = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            productArrayList.add(p);

        }
        productAdapter = new ProductAdapter(DetailActivity.this, productArrayList);
        recyclerView.setAdapter(productAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_go_to_map:

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<"+order.getLatitude()+">,<"+order.getLongitude()+">?q=<"+order.getLatitude()+">,<"+order.getLongitude()+">(Label+Name)"));
                startActivity(intent);

                break;
            case R.id.button_change_status:
                progressDialog = DialogUtil.showProgressDialog(DetailActivity.this,
                    "Cambiando estado", "Espere por favor");


                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(Keys.KEY_STATUS, "3");
                    jsonObject.put(Keys.KEY_VENTA_ID, order.getId());
                    jsonObject.put(Keys.KEY_API, Keys.KEY_API_KEY);

                    makeJSONRequest(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;
        }
    }

    private void makeJSONRequest(JSONObject jsonObject) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Keys.URL_UPDATE, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    if (response.getInt(Keys.KEY_CODIGO_STATUS) == 200) {

                        DialogUtil.createSimpleDialog(DetailActivity.this, "Estatus cambiado", response.getString("msj")).show();


                    } else {
                        DialogUtil.createSimpleDialog(DetailActivity.this, "Error Cambiando estado", response.toString()).show();
                    }
                } catch (JSONException e) {
                    DialogUtil.createSimpleDialog(DetailActivity.this, "Error Cambiando estado", e.getMessage()).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                DialogUtil.createSimpleDialog(DetailActivity.this, "Error Cambiando estado", error.getMessage()).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
