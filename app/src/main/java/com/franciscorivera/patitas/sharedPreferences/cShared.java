package com.franciscorivera.patitas.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class cShared
{
    public static void registerShared(Context oContext,String type_mascota)
    {
        SharedPreferences oSharedPreferences = oContext.getSharedPreferences("Filtro",oContext.MODE_PRIVATE);
        SharedPreferences.Editor oE = oSharedPreferences.edit();
        oE.putString("mascota",type_mascota);
        oE.commit();
    }

    public static String readShared(Context oContext)
    {
        SharedPreferences oSharedPreferences = oContext.getSharedPreferences("Filtro",oContext.MODE_PRIVATE);
        return oSharedPreferences.getString("mascota",null);
    }
}
