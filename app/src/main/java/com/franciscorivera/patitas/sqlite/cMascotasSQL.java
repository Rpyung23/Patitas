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
            oD.setNameDuenio(oCursor.getString(2));
            cMascota oM = new cMascota(oD);
            oM.setNameMascota(oCursor.getString(0));
            oM.setTypeMascota(oCursor.getString(1));
            oMascotaArrayList.add(oM);
        }

        return oMascotaArrayList;
    }


    public boolean deleteDataBaseMascota(String name)
    {
        try{
            this.oSqLiteDatabaseMascota = this.oMascotasDbHelper.getWritableDatabase();
            String sql_ = "delete from mascotas where name_mascota = '"+name+"'";
            this.oSqLiteDatabaseMascota.execSQL(sql_);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public cMascota readDataBase(String name)
    {
        cDuenio oD = new cDuenio();
        cMascota oM = new cMascota(oD);

        this.oSqLiteDatabaseMascota = this.oMascotasDbHelper.getReadableDatabase();
        String sql_ = "select * from mascotas where name_mascota = '"+name+"'";
        Cursor oCursor = this.oSqLiteDatabaseMascota.rawQuery(sql_,null);
        Log.e("Cursor"," : "+oCursor.getCount());
        Log.e("SQL",sql_);
        while (oCursor.moveToNext())
        {

            oM.getoD().setNameDuenio(oCursor.getString(8));
            oM.getoD().setPhoneDuenio(oCursor.getString(10));
            oM.getoD().setEmailDuenio(oCursor.getString(9));
            oM.getoD().setDirDuenio(oCursor.getString(11));

            oM.setDateMascota(oCursor.getString(2));
            oM.setPesoMascota(oCursor.getDouble(7));
            oM.setTypeMascota(oCursor.getString(1));
            oM.setNameMascota(oCursor.getString(0));
            oM.setVacuna(oCursor.getInt(4));
            oM.setChip(oCursor.getInt(3) == 1 ? true : false);
            oM.setInscription(oCursor.getInt(5));
            oM.setTamMascota(oCursor.getString(6));

        }

        return oM;
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

    public boolean updateMascota(cMascota oM,String name_aux){
        this.oSqLiteDatabaseMascota = this.oMascotasDbHelper.getWritableDatabase();
        try{
            //this.oSqLiteDatabaseMascota.beginTransaction();
            /*String sqlInsert = "insert into mascotas(name_mascota,type_mascota," +
                    "date_mascota,chip,vacuna,inscription,tamnio,peso,name_duenio," +
                    "email_duenio,phone_duenio,dir_duenio) values('"+oM.getNameMascota()+"','"+oM.getTypeMascota()+"'" +
                    ",'"+oM.getDateMascota()+"',"+oM.isChip()+","+oM.isVacuna()+","+oM.isInscription()+"" +
                    ",'"+oM.getTamMascota()+"',"+oM.getPesoMascota()+",'"+oM.getoD().getNameDuenio()+"'" +
                    ",'"+oM.getoD().getEmailDuenio()+"','"+oM.getoD().getPhoneDuenio()+"','"+oM.getoD().getDirDuenio()+"')";*/
            String sqlUpdate = "update mascotas set name_mascota = '"+oM.getNameMascota()+"',type_mascota = '"+oM.getTypeMascota()+"'," +
                    "date_mascota = '"+oM.getDateMascota()+"',chip = "+oM.isChip()+",vacuna = "+oM.isVacuna()+"" +
                    ",inscription = "+oM.isInscription()+",tamnio = '"+oM.getTamMascota()+"',peso = "+oM.getPesoMascota()+"," +
                    "name_duenio = '"+oM.getoD().getNameDuenio()+"',email_duenio = '"+oM.getoD().getEmailDuenio()+"'," +
                    "phone_duenio = '"+oM.getoD().getPhoneDuenio()+"',dir_duenio = '"+oM.getoD().getDirDuenio()+"' where name_mascota = '"+name_aux+"'";
            Log.e("UPDATE",sqlUpdate);
            this.oSqLiteDatabaseMascota.execSQL(sqlUpdate);
            return true;
        }catch (Exception e)
        {
            Log.e("UPDATE",e.getMessage());
            return false;
        }
    }

}
