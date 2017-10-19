package mx.happylittlebox.repartidor.happylittleboxapp.utils;

/**
 * Created by miguelacio on 17/10/17.
 */

public class Keys {
    //TIEMPO PARA ENVIAR UBICACIONES DEL REPARTIDOR
    public static final int KEY_TIME_UPDATE = 600000;
    //ESTÁ EN MILISEGUNDOS 10 MINUTOS = 600000 MILISEGUNDOS


    //API KEY
    public static final String KEY_API_KEY = "te98LB8h8g7gf7g00@98h**89*17$$";

    //UPDATE LOCATION
    public static final String URL_UPDATE_LOCATION = "https://api.happylittlebox.mx/index.php?/ubicacion/insertUbicacion";

    //UPDATE STATUS
    public static final String URL_UPDATE = "https://api.happylittlebox.mx/index.php?/ventas/cambiaEstatus";

    //LOGIN
    public static final String URL_LOGIN = "https://api.happylittlebox.mx/index.php?/usuarios/login";
    public static final String KEY_CODIGO_STATUS = "codigostatus";

    //VENTAS
    public static final String URL_SALES = "https://api.happylittlebox.mx/index.php?/ventas/getVentasAsignadas";
    public static final String KEY_RESULTADO = "resultado";
    public static final String KEY_VENTA_ID = "venta_id";
    public static final String KEY_VENTA = "venta";
    public static final String KEY_STATUS = "status";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_DIRECCION_CALLE= "direccion_calle";
    public static final String KEY_DIRECCION_NO = "direccion_no";
    public static final String KEY_DIRECCION_COLONIA = "direccion_colonia";
    public static final String KEY_DIRECCION_CP = "direccion_cp";
    public static final String KEY_DIRECCION_CIUDAD = "direccion_ciudad";
    public static final String KEY_DIRECCION_ESTADO = "direccion_estado";
    public static final String KEY_ITEMS = "items";
    public static final String KEY_CANTIDAD = "cantidad";
    public static final String KEY_PRODUCTO_NOMBRE = "producto_nombre";
    public static final String KEY_PRODUCTO_ID = "producto_id";


    //USER
    public static final String KEY_USUARIO = "usuario";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_API = "api";
    public static final String KEY_NOMBRES = "nombres";
    public static final String KEY_APATERNO = "apaterno";
    public static final String KEY_AMATERNO = "amaterno";
    public static final String KEY_CELULAR = "celular";
    public static final String KEY_REPARTIDOR_ID= "repartidor_id";
    public static final String KEY_PRODUCTO = "producto";
    public static final String KEY_ID = "id";
}
