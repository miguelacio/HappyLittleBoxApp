package mx.happylittlebox.repartidor.happylittleboxapp.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import mx.happylittlebox.repartidor.happylittleboxapp.models.User;

/**
 * Created by miguelacio on 19/10/17.
 */

public class AlwaysRunningBackgroundService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    Handler mHandler = new Handler();
    Context context = this;
    RequestQueue requestQueue;
    SessionStateManager sessionStateManager;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    User currentUser;
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // TODO Auto-generated method stub
        Intent restartService = new Intent(getApplicationContext(),
                this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);

        //Restart the service once it has been killed android


        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +100, restartServicePI);

    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        requestQueue = Volley.newRequestQueue(context);
        sessionStateManager = new SessionStateManager(context);
        currentUser = sessionStateManager.getCurrentUser();


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
        mHandler.postDelayed(ToastRunnable, Keys.KEY_TIME_UPDATE);

    }

    final Runnable ToastRunnable = new Runnable() {
        public void run() {

            makeJSONRequest();
            mHandler.postDelayed(ToastRunnable, Keys.KEY_TIME_UPDATE);

        }
    };

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void makeJSONRequest() {

        String latitud = String.valueOf(mLastLocation.getLatitude());
        String longitud = String.valueOf(mLastLocation.getLongitude());
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(Keys.KEY_REPARTIDOR_ID, currentUser.getRepartidor_id());
            jsonObject.put(Keys.KEY_LATITUDE, latitud);
            jsonObject.put(Keys.KEY_LONGITUDE, longitud);
            jsonObject.put(Keys.KEY_API, Keys.KEY_API_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.stopSelf();
    }
}
