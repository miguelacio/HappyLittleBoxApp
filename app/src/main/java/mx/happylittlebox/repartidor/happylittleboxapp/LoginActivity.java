package mx.happylittlebox.repartidor.happylittleboxapp;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import mx.happylittlebox.repartidor.happylittleboxapp.models.User;
import mx.happylittlebox.repartidor.happylittleboxapp.utils.DialogUtil;
import mx.happylittlebox.repartidor.happylittleboxapp.utils.JSONParser;
import mx.happylittlebox.repartidor.happylittleboxapp.utils.Keys;
import mx.happylittlebox.repartidor.happylittleboxapp.utils.SessionStateManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextEmail, editTextPassword;
    Button buttonLogIn;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    SessionStateManager sessionStateManager;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this);
        sessionStateManager = new SessionStateManager(this);
        editTextEmail = (EditText) findViewById(R.id.edit_text_email);
        editTextPassword = (EditText) findViewById(R.id.edit_text_password);
        buttonLogIn = (Button) findViewById(R.id.button_log_in);

        buttonLogIn.setOnClickListener(this);
        checkLocationPermission();

        if (sessionStateManager.getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Se necesita el permiso para localización")
                        .setMessage("Acepta para poder seguir")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(LoginActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "true 2", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_log_in:
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()){


                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(Keys.KEY_USUARIO, email);
                        jsonObject.put(Keys.KEY_PASSWORD, password);
                        jsonObject.put(Keys.KEY_API, Keys.KEY_API_KEY);

                        makeJSONRequest(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(LoginActivity.this, "Faltan campos para llenar", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }

    private void makeJSONRequest(JSONObject jsonObject) {

        progressDialog = DialogUtil.showProgressDialog(LoginActivity.this,
                "Iniciando sesión", "Espere por favor");


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Keys.URL_LOGIN, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    Log.i("JSONllegada", response.toString());
                    if (response.getInt(Keys.KEY_CODIGO_STATUS) == 200) {

                        User user = JSONParser.parseUserJSON(response);
                        sessionStateManager.saveSession(user);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setExitTransition(new Explode());
                            startActivity(intent,
                                    ActivityOptions
                                            .makeSceneTransitionAnimation(LoginActivity.this).toBundle());
                            finish();
                        } else {
                            startActivity(intent);
                            finish();
                        }


                    } else {
                        DialogUtil.createSimpleDialog(LoginActivity.this, "Error iniciando sesión", response.toString()).show();
                    }
                } catch (JSONException e) {
                    DialogUtil.createSimpleDialog(LoginActivity.this, "error parseando json", e.getMessage()).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                DialogUtil.createSimpleDialog(LoginActivity.this, "Error iniciando sesión", error.getMessage()).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}
