package com.franciscorivera.patitas.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.franciscorivera.patitas.R;
import com.franciscorivera.patitas.poo.cMascota;
import com.franciscorivera.patitas.sqlite.cMascotasSQL;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

public class DetalleActivity extends AppCompatActivity
{
    private MaterialToolbar oMaterialToolbar;

    private TextView oTextViewNameMascota;
    private TextView oTextViewTypeMascota;
    private TextView oTextViewNacimiento;
    private Chip oChip;
    private Chip oChipVacuna;
    private Chip oChipInscription;

    private TextView oTextViewPesoMascota;
    private TextView oTextViewNameDuenio;
    private TextView oTextViewEmailDuenio;
    private TextView oTextViewPhoneDuenio;
    private TextView oTextViewDirDuenio;

    private AppCompatImageView oAppCompatImageViewMascota;




    private AlertDialog.Builder oABuilder;
    private AlertDialog oAlertDialog;
    private View oViewAlert;
    private TextView oTNameAlert;
    private String oStringNameMascota;
    private cMascota oMascota;
    private cMascotasSQL oMascotasSQL;

    private MaterialButton oMaterialButtonAlertDelete;
    private MaterialButton oMaterialButtonAlertCancel;
    private TextInputEditText oTextInputEditTextClaveDelete;
    private boolean banderaDelete = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        this.oMaterialToolbar = findViewById(R.id.idMaterialToolbarDetails);
        this.oTextViewNameMascota = findViewById(R.id.NameMascota);
        this.oTextViewTypeMascota = findViewById(R.id.TypeMascota);
        this.oTextViewNacimiento = findViewById(R.id.NacimientoMascota);
        this.oChip = findViewById(R.id.id_Chip);
        this.oChipVacuna = findViewById(R.id.id_Vacunation);
        this.oChipInscription = findViewById(R.id.id_Inscription);
        this.oTextViewPesoMascota = findViewById(R.id.PesoMascota);
        this.oTextViewNameDuenio = findViewById(R.id.NameDuenio);
        this.oTextViewEmailDuenio = findViewById(R.id.EmailDuenio);
        this.oTextViewPhoneDuenio = findViewById(R.id.PhoneDuenio);
        this.oTextViewDirDuenio = findViewById(R.id.DirDuenio);
        this.oAppCompatImageViewMascota =  findViewById(R.id.FotoMascota);


        this.oMascotasSQL = new cMascotasSQL(DetalleActivity.this);
        this.oStringNameMascota = getIntent().getStringExtra("mascota");

        this.oMascota = this.oMascotasSQL.readDataBase(this.oStringNameMascota);

        this.oViewAlert = LayoutInflater.from(DetalleActivity.this).inflate(R.layout.view_delete,null,false);

        initDatos();

    }

    private void initDatos()
    {
        this.oTextViewNameMascota.setText(this.oMascota.getNameMascota());
        this.oTextViewTypeMascota.setText(this.oMascota.getTypeMascota());
        this.oTextViewNacimiento.setText(this.oMascota.getDateMascota());
        this.oChip.setText(this.oMascota.isChip() ? "Tiene Chip" : "No tiene Chip");
        this.oChipVacuna.setText(this.oMascota.isVacuna() == 1 ? "Tiene vacuna" : "No tiene vacuna");
        this.oChipInscription.setText(this.oMascota.isInscription() == 1 ? "Esta inscripto" : "No esta inscripto");
        this.oTextViewPesoMascota.setText(String.valueOf(this.oMascota.getPesoMascota()));
        this.oTextViewNameDuenio.setText(this.oMascota.getoD().getNameDuenio());
        this.oTextViewEmailDuenio.setText(this.oMascota.getoD().getEmailDuenio());
        this.oTextViewPhoneDuenio.setText(this.oMascota.getoD().getPhoneDuenio());
        this.oTextViewDirDuenio.setText(this.oMascota.getoD().getDirDuenio());

        Picasso.get()
                .load(getIdFotoImg(this.oMascota.getTypeMascota()))
                .into(this.oAppCompatImageViewMascota);
    }

    private int getIdFotoImg(String typeMascota)
    {
        int idMAscotaFoto = R.drawable.dog;
        switch (typeMascota)
        {
            case "Perro":
                idMAscotaFoto =  (R.drawable.dog);
                break;
            case "Gato":
                idMAscotaFoto = (R.drawable.cat);
                break;
            case "Conejo":
                idMAscotaFoto = (R.drawable.rabbit);
                break;
            case "Tortuga":
                idMAscotaFoto = (R.drawable.turtle);
                break;
            case "Serpiente":
                idMAscotaFoto = (R.drawable.snake);
                break;
            case "Hámster":
                idMAscotaFoto = (R.drawable.hamster);
                break;
            case "Huron":
                idMAscotaFoto = (R.drawable.huron);
                break;
            case "Canario":
                idMAscotaFoto = (R.drawable.canario);
                break;
            case "Pez payaso":
                idMAscotaFoto = (R.drawable.payaso);
                break;
            case "Loro":
                idMAscotaFoto = (R.drawable.loro);
                break;
        }
        return  idMAscotaFoto;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.oMaterialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {

                switch (item.getItemId()){
                    case R.id.menu_delete:
                        createAlertDelete(oMascota.getNameMascota());
                        oAlertDialog.show();
                        break;
                    case R.id.menu_edit:
                        starEditActivity();
                        break;
                }
                return true;
            }
        });
    }

    private void createAlertDelete(String name)
    {
        if (oAlertDialog==null){
            oTNameAlert = oViewAlert.findViewById(R.id.idTextNameMascotaDelete);
            oMaterialButtonAlertCancel = oViewAlert.findViewById(R.id.idAlertCancelMascota);
            oMaterialButtonAlertDelete = oViewAlert.findViewById(R.id.idAlertDeleteMascota);
            oTextInputEditTextClaveDelete = oViewAlert.findViewById(R.id.idTextPassMascotaDelete);
            oTNameAlert.setText(name);
            oABuilder = new AlertDialog.Builder(DetalleActivity.this);
            oABuilder.setView(oViewAlert);
            oAlertDialog =  oABuilder.create();
        }else{
            oTNameAlert.setText(name);
        }

        this.oMaterialButtonAlertDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                banderaDelete = false;

                if (oTextInputEditTextClaveDelete.getText().toString().equals("patitasfelices.2021"))
                {
                    if (oMascotasSQL.deleteDataBaseMascota(oMascota.getNameMascota())){
                        Toast.makeText(DetalleActivity.this, "MASCOTA ELIMINADA", Toast.LENGTH_SHORT).show();
                        banderaDelete =  true;
                        finishDeleteResult();
                        finish();
                    }else{
                        Toast.makeText(DetalleActivity.this, "ERROR AL ELIMINAR MASCOTA", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(DetalleActivity.this, "CLAVE INCORRECTA", Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.oMaterialButtonAlertCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                oAlertDialog.cancel();
                oAlertDialog.dismiss();
            }
        });

    }

    private void finishDeleteResult()
    {
        Intent oI = new Intent();
        oI.putExtra("bandera",banderaDelete);
        setResult(Activity.RESULT_OK,oI);
    }

    private void starEditActivity()
    {
        Intent oI = new Intent(DetalleActivity.this,RegisterActivity.class);
        oI.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        oI.putExtra("RegisterOrUpdate",false);
        oI.putExtra("mascota",this.oMascota.getNameMascota());
        startActivity(oI);
    }

}