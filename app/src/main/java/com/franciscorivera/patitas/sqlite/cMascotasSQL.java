package com.franciscorivera.patitas.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.franciscorivera.patitas.poo.cDuenio;
import com.franciscorivera.patitas.poo.cMascota;

import java.util.ArrayList;

public class cMascotasSQL
{
    private Context oContext;
    private SQLiteDatabase oSqLiteDatabaseMascota;
    private MascotasDbHelper oMascotasDbHelper;

    public cMascotasSQL(Context oC)
    {
        this.oContext = oC;
        this.oMascotasDbHelper = new MascotasDbHelper(this.oContext);
    }


    public ArrayList<cMascota> readDataBaseAll(String type)
    {
        ArrayList<cMascota> oMascotaArrayList = new ArrayList<>();
        this.oSqLiteDatabaseMascota = this.oMascotasDbHelper.getReadableDatabase();
        String sql_ = "";
        if (type == null){
            sql_ = "select name_mascota,type_mascota,name_duenio from mascotas";
        }else{
            sql_ = "select name_mascota,type_mascota,name_duenio from mascotas where type_mascota = '"+type+"'";
        }
        Cursor oCursor = this.oSqLiteDatabaseMascota.rawQuery(sql_,null);
        Log.e("Cursor"," : "+oCursor.getCount());
        while (oCursor.moveToNext())
        {
            cDuenio oD = new cDuenio();
            oD.setNameDuenio(oCursor.getString(0));
            cMascota oM = new cMascota(oD);
            oM.setNameMascota(oCursor.getString(2));
            oM.setTypeMascota(oCursor.getString(1));
            oMascotaArrayList.add(oM);
        }

        return oMascotaArrayList;
    }

    public boolean registerMascota(cMascota oM){
        this.oSqLiteDatabaseMascota = this.oMascotasDbHelper.getWritableDatabase();
        try{
            //this.oSqLiteDatabaseMascota.beginTransaction();
            String sqlInsert = "insert into mascotas(name_mascota,type_mascota," +
                    "date_mascota,chip,vacuna,inscription,tamnio,peso,name_duenio," +
                    "email_duenio,phone_duenio,dir_duenio) values('"+oM.getNameMascota()+"','"+oM.getTypeMascota()+"'" +
                    ",'"+oM.getDateMascota()+"',"+oM.isChip()+","+oM.isVacuna()+","+oM.isInscription()+"" +
                    ",'"+oM.getTamMascota()+"',"+oM.getPesoMascota()+",'"+oM.getoD().getNameDuenio()+"'" +
                    ",'"+oM.getoD().getEmailDuenio()+"','"+oM.getoD().getPhoneDuenio()+"','"+oM.getoD().getDirDuenio()+"')";
            Log.e("INSERT",sqlInsert);
            this.oSqLiteDatabaseMascota.execSQL(sqlInsert);
            return true;
        }catch (Exception e)
        {
            Log.e("INSERT",e.getMessage());
            return false;
        }
    }

}
