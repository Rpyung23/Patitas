package com.franciscorivera.patitas.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.franciscorivera.patitas.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DetalleActivity extends AppCompatActivity
{
    private MaterialToolbar oMaterialToolbar;
    private AlertDialog.Builder oABuilder;
    private AlertDialog oAlertDialog;
    private View oViewAlert;
    private TextView oTNameAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        this.oMaterialToolbar = findViewById(R.id.idMaterialToolbarDetails);
        this.oViewAlert = LayoutInflater.from(DetalleActivity.this).inflate(R.layout.view_delete,null,false);
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
                        createAlertDelete("Hambugueso");
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
            oTNameAlert.setText(name);
            oABuilder = new AlertDialog.Builder(DetalleActivity.this);
            oABuilder.setView(oViewAlert);
            oAlertDialog =  oABuilder.create();
        }else{
            oTNameAlert.setText(name);
        }
    }

    private void starEditActivity()
    {
        Intent oI = new Intent(DetalleActivity.this,RegisterActivity.class);
        oI.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(oI);
    }
}