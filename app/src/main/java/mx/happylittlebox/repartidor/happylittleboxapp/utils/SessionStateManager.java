package mx.happylittlebox.repartidor.happylittleboxapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import mx.happylittlebox.repartidor.happylittleboxapp.models.User;

/**
 * Created by miguelacio on 17/10/17.
 */

public class SessionStateManager {

    private SharedPreferences sharedPreferences;

    public SessionStateManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public void saveSession(User user) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(Keys.KEY_USUARIO, user.getUsuario());
        editor.putString(Keys.KEY_NOMBRES, user.getName());
        editor.putString(Keys.KEY_API, user.getApi());
        editor.putString(Keys.KEY_REPARTIDOR_ID, user.getRepartidor_id());
        editor.apply();
    }

    public User getCurrentUser() {

        String usuario = sharedPreferences.getString(Keys.KEY_USUARIO, "");
        String name = sharedPreferences.getString(Keys.KEY_NOMBRES, "");
        String api = sharedPreferences.getString(Keys.KEY_API, "");
        String id = sharedPreferences.getString(Keys.KEY_REPARTIDOR_ID, "");

        if (id.isEmpty()) {
            return null;
        } else {
            User user = new User();
            user.setUsuario(usuario);
            user.setName(name);
            user.setApi(api);
            user.setRepartidor_id(id);

            return user;
        }
    }

    public void logOut() {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
