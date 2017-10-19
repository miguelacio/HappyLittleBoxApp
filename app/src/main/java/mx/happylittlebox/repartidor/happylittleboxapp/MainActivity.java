package mx.happylittlebox.repartidor.happylittleboxapp;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmList;
import mx.happylittlebox.repartidor.happylittleboxapp.models.Order;
import mx.happylittlebox.repartidor.happylittleboxapp.models.User;
import mx.happylittlebox.repartidor.happylittleboxapp.utils.BackgroundService;
import mx.happylittlebox.repartidor.happylittleboxapp.utils.DialogUtil;
import mx.happylittlebox.repartidor.happylittleboxapp.utils.JSONParser;
import mx.happylittlebox.repartidor.happylittleboxapp.utils.Keys;
import mx.happylittlebox.repartidor.happylittleboxapp.utils.OrderAdapter;
import mx.happylittlebox.repartidor.happylittleboxapp.utils.SessionStateManager;

public class MainActivity extends AppCompatActivity implements OrderAdapter.OrderCallBacks {
    SessionStateManager sessionStateManager;
    User currentUser;
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    ArrayList<Order> orderArrayList = new ArrayList<>();
    OrderAdapter orderAdapter;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionStateManager = new SessionStateManager(this);
        requestQueue = Volley.newRequestQueue(this);
        currentUser = sessionStateManager.getCurrentUser();
        setTitle("Bienvenido " + currentUser.getName());
        recyclerView = (RecyclerView) findViewById(R.id.ventas_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        Realm.init(this);
        realm = Realm.getDefaultInstance();


        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Keys.KEY_API, Keys.KEY_API_KEY);
            jsonObject.put(Keys.KEY_REPARTIDOR_ID, currentUser.getRepartidor_id());
            makeJSONRequest(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        startService(new Intent(this, BackgroundService.class));;


    }

    private void makeJSONRequest(JSONObject jsonObject) {
        progressDialog = DialogUtil.showProgressDialog(MainActivity.this,
                "Obteniendo pedidos", "Espere por favor");


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Keys.URL_SALES, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    Log.i("JSONllegada", response.toString());
                    if (response.getInt(Keys.KEY_CODIGO_STATUS) == 200) {

                        orderArrayList = JSONParser.parseOrdersJSON(response);

                        orderAdapter = new OrderAdapter(MainActivity.this, orderArrayList);


                        recyclerView.setAdapter(orderAdapter);

                        realm.beginTransaction();
                        realm.deleteAll();
                        realm.commitTransaction();
                        for (int i = 0; i < orderArrayList.size(); i++) {
                            realm.beginTransaction();
                            realm.copyToRealm(orderArrayList.get(i));
                            realm.commitTransaction();
                        }

                    } else {
                        DialogUtil.createSimpleDialog(MainActivity.this, "Error iniciando sesión", response.toString()).show();
                    }
                } catch (JSONException e) {
                    DialogUtil.createSimpleDialog(MainActivity.this, "error parseando json", e.getMessage()).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                DialogUtil.createSimpleDialog(MainActivity.this, "Error iniciando sesión", error.getMessage()).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_log_out:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Atención");
                builder.setMessage("¿Estás seguro que deseas cerrar sesión?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sessionStateManager.logOut();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNeutralButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert1 = builder.create();
                alert1.show();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOrderSelected(Order order) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", order.getId());
        startActivity(intent);
    }
}
