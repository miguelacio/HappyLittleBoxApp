package mx.happylittlebox.repartidor.happylittleboxapp.utils;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.IntentService;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.transition.Explode;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import mx.happylittlebox.repartidor.happylittleboxapp.LoginActivity;
import mx.happylittlebox.repartidor.happylittleboxapp.MainActivity;
import mx.happylittlebox.repartidor.happylittleboxapp.models.User;

import static android.content.ContentValues.TAG;

/**
 * Created by miguelacio on 18/10/17.
 */

public class BackgroundService extends IntentService implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    Handler mHandler = new Handler();
    Context context = this;
    RequestQueue requestQueue;
    SessionStateManager sessionStateManager;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    User currentUser;


    public BackgroundService() {
        super("BackgrounndService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Toast.makeText(this, "onhandleintent", Toast.LENGTH_SHORT).show();
        requestQueue = Volley.newRequestQueue(context);
        sessionStateManager = new SessionStateManager(context);
        currentUser = sessionStateManager.getCurrentUser();

        mHandler.postDelayed(ToastRunnable, 5000);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();

    }

    final Runnable ToastRunnable = new Runnable() {
        public void run() {

            String latitud = String.valueOf(mLastLocation.getLatitude());
            String longitud = String.valueOf(mLastLocation.getLongitude());
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put(Keys.KEY_REPARTIDOR_ID, currentUser.getRepartidor_id());
                jsonObject.put(Keys.KEY_LATITUDE, latitud);
                jsonObject.put(Keys.KEY_LONGITUDE, longitud);
                jsonObject.put(Keys.KEY_API, Keys.KEY_API_KEY);
                makeJSONRequest(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mHandler.postDelayed(ToastRunnable, 5000);

        }
    };


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Toast.makeText(this, mLastLocation.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void makeJSONRequest(JSONObject jsonObject) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Keys.URL_UPDATE_LOCATION, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

}
