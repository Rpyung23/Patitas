package com.franciscorivera.patitas.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.franciscorivera.patitas.R;
import com.franciscorivera.patitas.adapter.cAdapterMascotas;
import com.franciscorivera.patitas.interfaces.OnClickListenerRecyclerView;
import com.franciscorivera.patitas.poo.cDuenio;
import com.franciscorivera.patitas.poo.cMascota;
import com.franciscorivera.patitas.sharedPreferences.cShared;
import com.franciscorivera.patitas.sqlite.cMascotasSQL;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements OnClickListenerRecyclerView
{
    private String[] oStringMascotas;
    private AppCompatSpinner oAutoCompleteTextView;
    private ArrayAdapter<String> oStringArrayAdapter;
    private MaterialToolbar oMaterialToolbar;
    private RecyclerView oRecyclerViewMascotas;

    private ArrayList<cMascota>oMascotaArrayList;
    private cAdapterMascotas oAdapterMascotas;
    private cMascotasSQL oMascotasSQL;
    private TextView oTextViewTotalRecyclerView;
    private int REQUESTCODEDETALLE = 500;
    private int oPositionSelectRecyclerView = 0;
    private String oStringMascotaSelect = null;
    private int REGISTERMASCOTA = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.oAutoCompleteTextView = findViewById(R.id.idAutoCompleteTypeAnimalList);
        this.oMaterialToolbar = findViewById(R.id.idMaterialToolbar);
        this.oRecyclerViewMascotas = findViewById(R.id.RecyclerViewAnimales);
        this.oTextViewTotalRecyclerView = findViewById(R.id.totRecyclerView);
        this.oMascotasSQL = new cMascotasSQL(HomeActivity.this);
        this.oStringMascotas = getApplicationContext().getResources().getStringArray(R.array.typePetsSearch);
        this.oStringArrayAdapter = new  ArrayAdapter<String>(HomeActivity.this,
                android.R.layout.simple_spinner_dropdown_item,oStringMascotas);
        this.oAutoCompleteTextView.setAdapter(this.oStringArrayAdapter);
        this.oStringMascotaSelect = cShared.readShared(HomeActivity.this);
        //this.oAutoCompleteTextView.setSelection(0);

        initSpinner();

        this.oMascotaArrayList = new ArrayList<>();
        initRecyclerView();

    }

    private void initSpinner()
    {
        boolean banderaSearch = false;
        int position = 0;
        if (this.oStringMascotaSelect!= null){
            while (!banderaSearch)
            {

                if (this.oStringMascotaSelect.equals(oStringMascotas[position])){
                    banderaSearch = true;
                    this.oAutoCompleteTextView.setSelection(position);
                }else{
                    position++;
                }
            }
        }else{
            this.oAutoCompleteTextView.setSelection(0);
        }
    }

    private void initRecyclerView()
    {
        oAdapterMascotas = new cAdapterMascotas(this.oMascotaArrayList,this,this.oRecyclerViewMascotas);
        this.oRecyclerViewMascotas.setAdapter(oAdapterMascotas);
        //getListMascotas(oStringMascotaSelect);
    }

    private void getListMascotas(String typeMascota)
    {
        ArrayList<cMascota>  oMas =  this.oMascotasSQL.readDataBaseAll(typeMascota);
        this.oMascotaArrayList.clear();
        for (int i = 0;i<this.oMascotaArrayList.size();i++)
        {
            this.oMascotaArrayList.remove(i);
        }
        this.oMascotaArrayList.addAll(oMas);
        this.oAdapterMascotas.notifyDataSetChanged();
        oTextViewTotalRecyclerView.setText("Total "+this.oAdapterMascotas.getItemCount()+" mascotas");
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*this.oAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
               //Toast.makeText(HomeActivity.this, oStringMascotas[position].toString(), Toast.LENGTH_SHORT).show();
                oStringMascotaSelect = position == 0 ? null : oStringMascotas[position];
                getListMascotas(oStringMascotaSelect);
            }
        });*/

        this.oAutoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                oStringMascotaSelect = (position == 0 ? null : oStringMascotas[position]);
                cShared.registerShared(HomeActivity.this,oStringMascotaSelect);
                getListMascotas(oStringMascotaSelect);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        this.oMaterialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {

                if (item.getItemId() == R.id.menu_add)
                {
                    Intent oI = new Intent(HomeActivity.this,RegisterActivity.class);
                    //oI.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(oI,REGISTERMASCOTA);
                }
                return true;
            }
        });
    }


    @Override
    protected void onPostResume()
    {
        super.onPostResume();
    }

    @Override
    public void OnClick(int position)
    {
        oPositionSelectRecyclerView = position;

        cMascota oM = this.oMascotaArrayList.get(position);
        Intent oI = new Intent(HomeActivity.this,DetalleActivity.class);
        //oI.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        oI.putExtra("mascota",oM.getNameMascota());
        startActivityForResult(oI,REQUESTCODEDETALLE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {

        if (requestCode == REQUESTCODEDETALLE && resultCode == Activity.RESULT_OK)
        {
            if (data!=null && data.getBooleanExtra("bandera",false) == true)
            {
                getListMascotas(oStringMascotaSelect);
            }
        }else if(requestCode == REGISTERMASCOTA && resultCode == Activity.RESULT_OK)
        {
            if (data!=null && data.getBooleanExtra("lectura",false) == true)
            {
                getListMascotas(oStringMascotaSelect);
            }
        }
    }
}