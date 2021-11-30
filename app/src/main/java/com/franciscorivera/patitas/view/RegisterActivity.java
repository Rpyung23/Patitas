package com.franciscorivera.patitas.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.franciscorivera.patitas.R;
import com.franciscorivera.patitas.poo.cDuenio;
import com.franciscorivera.patitas.poo.cMascota;
import com.franciscorivera.patitas.sqlite.cMascotasSQL;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class RegisterActivity extends AppCompatActivity
{
    private AppCompatSpinner oAutoCompleteTextViewTypeMascota;
    private AppCompatSpinner oAutoCompleteTextViewTypeTamanios;

    private MaterialToolbar oMaterialToolbar;
    private MaterialButton oMaterialButtonCancel;
    private MaterialButton oMaterialButtonSave;
    private TextInputEditText oTextInputEditTextCalendar;
    private MaterialDatePicker oMaterialDatePickerNacimiento;
    private cMascotasSQL oMascotasSQL;

    private TextInputEditText oTextInputEditTextNameMascota;
    private TextInputEditText oTextInputEditTextPesoMascota;
    private TextInputEditText oTextInputEditTextNameDuenio;
    private TextInputEditText oTextInputEditTextEmailDuenio;
    private TextInputEditText oTextInputEditTextPhoneDuenio;
    private TextInputEditText oTextInputEditTextDirDuenio;

    private MaterialRadioButton oMaterialRadioButtonChipYes;
    private MaterialRadioButton oMaterialRadioButtonChipNo;

    private MaterialRadioButton oMaterialRadioButtonVacunaYes;
    private MaterialRadioButton oMaterialRadioButtonVacunaNo;
    private MaterialRadioButton oMaterialRadioButtonVacunaYesNo;

    private MaterialRadioButton oMaterialRadioButtonInscriptionYes;
    private MaterialRadioButton oMaterialRadioButtonInscriptionNo;
    private MaterialRadioButton oMaterialRadioButtonInscriptionYesNo;

    private String oTypeMascota = null;
    private String oTamanioMascota = null;

    /** true -> register    ---  false -> update**/
    private boolean banderaRegisterOrUpdate = true;
    private boolean banderaLectura = false;
    private String oMascotaUpdate = null;

    private String[] oTypeMascotaList;
    private String[] oTypeTamaniosMascotaList;

    private cMascota oMascota;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.oAutoCompleteTextViewTypeMascota = findViewById(R.id.idAutoCompleteTypeAnimalList);
        this.oAutoCompleteTextViewTypeTamanios = findViewById(R.id.idAutoCompleteTamanioAnimal);
        this.oMaterialToolbar = findViewById(R.id.idMaterialToolbar);
        this.oMaterialButtonCancel = findViewById(R.id.idbtnCancel);
        this.oMaterialButtonSave = findViewById(R.id.idbtnSave);
        this.oTextInputEditTextCalendar = findViewById(R.id.idTextInputCalendar);

        this.oTextInputEditTextNameMascota = findViewById(R.id.idRegisterNameMascota);
        this.oTextInputEditTextPesoMascota = findViewById(R.id.idRegisterPesoMascota);
        this.oTextInputEditTextNameDuenio = findViewById(R.id.idRegisterNameDuenio);
        this.oTextInputEditTextEmailDuenio = findViewById(R.id.idRegisterEmailDuenio);
        this.oTextInputEditTextPhoneDuenio = findViewById(R.id.idRegisterPhoneDuenio);
        this.oTextInputEditTextDirDuenio = findViewById(R.id.idRegisterDirDuenio);

        this.oMaterialRadioButtonChipYes = findViewById(R.id.idChipRegisterYes);
        this.oMaterialRadioButtonChipNo = findViewById(R.id.idChipRegisterNo);

        this.oMaterialRadioButtonVacunaYes = findViewById(R.id.idVacunaRegisterYes);
        this.oMaterialRadioButtonVacunaNo = findViewById(R.id.idVacunaRegisterNo);
        this.oMaterialRadioButtonVacunaYesNo = findViewById(R.id.idVacunaRegisterYesNo);

        this.oMaterialRadioButtonInscriptionYes = findViewById(R.id.idVacunaInscriptionYes);
        this.oMaterialRadioButtonInscriptionNo = findViewById(R.id.idVacunaInscriptionNo);
        this.oMaterialRadioButtonInscriptionYesNo = findViewById(R.id.idVacunaInscriptionYesNo);

        this.oTypeMascotaList = getApplicationContext().getResources().getStringArray(R.array.typePets);
        this.oTypeTamaniosMascotaList = getApplicationContext().getResources().getStringArray(R.array.tamPets);

        this.oMascotasSQL = new cMascotasSQL(RegisterActivity.this);
        this.oTextInputEditTextCalendar.setFocusable(false);
        initAdapterAutocomplete();
        initCalendar();

        this.banderaRegisterOrUpdate = getIntent().getBooleanExtra("RegisterOrUpdate",true);
        this.oMascotaUpdate = getIntent().getStringExtra("mascota");

        if (!this.banderaRegisterOrUpdate){
            initLecturaUpdate();
        }

    }

    private void initLecturaUpdate()
    {
        this.oMascota = this.oMascotasSQL.readDataBase(this.oMascotaUpdate);

        //this.oAutoCompleteTextViewTypeMascota.setText(this.oMascota.getTypeMascota());
        //this.oAutoCompleteTextViewTypeTamanios.setText(this.oMascota.getTamMascota());
        boolean banderaSearch = false;
        int position = 0;
        while (!banderaSearch)
        {
            Log.e("SEARCH",this.oMascota.getTypeMascota() +" == "+ oTypeMascotaList[position]);
            if (this.oMascota.getTypeMascota().equals(oTypeMascotaList[position])){
                banderaSearch = true;
                this.oAutoCompleteTextViewTypeMascota.setSelection(position);
            }else{
                position++;
            }
        }

        banderaSearch = false;
        position = 0;

        while (!banderaSearch)
        {
            if (this.oMascota.getTamMascota().equals(this.oTypeTamaniosMascotaList[position])){
                banderaSearch = true;
                this.oAutoCompleteTextViewTypeTamanios.setSelection(position);
            }else{
                position++;
            }
        }





        this.oTextInputEditTextCalendar.setText(this.oMascota.getDateMascota());
        this.oTextInputEditTextNameMascota.setText(this.oMascota.getNameMascota());
        this.oTextInputEditTextPesoMascota.setText(String.valueOf(this.oMascota.getPesoMascota()));
        this.oTextInputEditTextNameDuenio.setText(this.oMascota.getoD().getNameDuenio());
        this.oTextInputEditTextEmailDuenio.setText(this.oMascota.getoD().getEmailDuenio());
        this.oTextInputEditTextPhoneDuenio.setText(this.oMascota.getoD().getPhoneDuenio());
        this.oTextInputEditTextDirDuenio.setText(this.oMascota.getoD().getDirDuenio());

        this.oMaterialRadioButtonChipYes.setChecked(this.oMascota.isChip());
        this.oMaterialRadioButtonChipNo.setChecked(!this.oMascota.isChip() ? true : false);

        if (this.oMascota.isInscription() == 1){
            this.oMaterialRadioButtonInscriptionYes.setChecked(true);
        }else if (this.oMascota.isInscription() == 2){
            this.oMaterialRadioButtonInscriptionNo.setChecked(false);
        }else{
            this.oMaterialRadioButtonInscriptionYesNo.setChecked(false);
        }


        if (this.oMascota.isVacuna() == 1){
            this.oMaterialRadioButtonVacunaYes.setChecked(true);
        }else if (this.oMascota.isVacuna() == 2){
            this.oMaterialRadioButtonVacunaNo.setChecked(true);
        }else{
            this.oMaterialRadioButtonVacunaYesNo.setChecked(true);
        }


    }

    private void initCalendar()
    {
        MaterialDatePicker.Builder oB = MaterialDatePicker.Builder.datePicker();
        CalendarConstraints.Builder oCB = new CalendarConstraints.Builder();
        oCB.setValidator(DateValidatorPointBackward.now());
        oCB.setStart(getFechaInicial());
        oCB.setEnd(getFechaFinal());
        CalendarConstraints oC = oCB.build();




        oB.setCalendarConstraints(oC);
        oB.setTitleText(R.string.fecha_nacimiento_mascota);
        oB.setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR);
        this.oMaterialDatePickerNacimiento = oB.build();

        this.oMaterialDatePickerNacimiento.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection)
            {
                Date mDate = new Date((Long) selection);
                SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                mSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("American/Chile"));
                oTextInputEditTextCalendar.setText(mSimpleDateFormat.format(mDate));
            }
        });


    }

    private long getFechaFinal()
    {
        Calendar oCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        oCalendar.setTimeInMillis(MaterialDatePicker.todayInUtcMilliseconds() - 86400000);
        return oCalendar.getTimeInMillis();
    }

    private long getFechaInicial()
    {
        Calendar oCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        oCalendar.setTimeInMillis(Long.parseLong("946684800000"));
        return oCalendar.getTimeInMillis();
    }

    private void initAdapterAutocomplete()
    {
        ArrayAdapter<String> oStringArrayAdapterTypePets = new ArrayAdapter<>(RegisterActivity.this,
                android.R.layout.simple_spinner_dropdown_item,this.oTypeMascotaList);

        ArrayAdapter<String> oStringArrayAdapterTamanio = new ArrayAdapter<>(RegisterActivity.this,
                android.R.layout.simple_spinner_dropdown_item,this.oTypeTamaniosMascotaList);

        this.oAutoCompleteTextViewTypeTamanios.setAdapter(oStringArrayAdapterTamanio);
        this.oAutoCompleteTextViewTypeMascota.setAdapter(oStringArrayAdapterTypePets);

    }


    @Override
    protected void onResume() {
        super.onResume();
        this.oMaterialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                banderaLectura = false;
                finish();
            }
        });

        this.oAutoCompleteTextViewTypeMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                oTypeMascota = oTypeMascotaList[position].toString();
                //Toast.makeText(RegisterActivity.this, oTypeMascota, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        /*this.oAutoCompleteTextViewTypeMascota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                oTypeMascota = oTypeMascotaList[position].toString();
                //Toast.makeText(RegisterActivity.this, oTypeMascota, Toast.LENGTH_SHORT).show();
            }
        });*/
        /*this.oAutoCompleteTextViewTypeTamanios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                oTamanioMascota = oTypeTamaniosMascotaList[position].toString();
                //Toast.makeText(RegisterActivity.this, oTamanioMascota, Toast.LENGTH_SHORT).show();
            }
        });*/

        this.oAutoCompleteTextViewTypeTamanios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                oTamanioMascota = oTypeTamaniosMascotaList[position].toString();
                //Toast.makeText(RegisterActivity.this, oTamanioMascota, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        this.oMaterialButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        this.oMaterialButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                banderaLectura = true;

                if (banderaRegisterOrUpdate){
                    cDuenio oD = new cDuenio();
                    oD.setNameDuenio(oTextInputEditTextNameDuenio.getText().toString());
                    oD.setDirDuenio(oTextInputEditTextDirDuenio.getText().toString());
                    oD.setEmailDuenio(oTextInputEditTextEmailDuenio.getText().toString());
                    oD.setPhoneDuenio(oTextInputEditTextPhoneDuenio.getText().toString());
                    cMascota oM = new cMascota(oD);
                    oM.setTypeMascota(oTypeMascota);
                    oM.setTamMascota(oTamanioMascota);
                    oM.setNameMascota(oTextInputEditTextNameMascota.getText().toString());
                    oM.setChip(oMaterialRadioButtonChipYes.isChecked() ? true : false);

                    if (oMaterialRadioButtonInscriptionYes.isChecked()){
                        oM.setInscription(1);
                    }else if (oMaterialRadioButtonInscriptionNo.isChecked()){
                        oM.setInscription(2);
                    }else{
                        oM.setInscription(3);
                    }



                    if (oMaterialRadioButtonVacunaYes.isChecked()){
                        oM.setVacuna(1);
                    }else if (oMaterialRadioButtonVacunaNo.isChecked()){
                        oM.setVacuna(2);
                    }else{
                        oM.setVacuna(3);
                    }


                    oM.setPesoMascota(Double.parseDouble(oTextInputEditTextPesoMascota.getText().toString()));
                    oM.setDateMascota(oTextInputEditTextCalendar.getText().toString());

                    if (oMascotasSQL.registerMascota(oM))
                    {
                        setResultIntent();
                        Toast.makeText(RegisterActivity.this, "Mascota registrada", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "UPDATE", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        this.oTextInputEditTextCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                oMaterialDatePickerNacimiento.show(RegisterActivity.this.getSupportFragmentManager(),null);
            }
        });
    }

    private void setResultIntent()
    {
        Intent oIntent = new Intent();
        oIntent.putExtra("lectura",banderaLectura);
        this.setResult(Activity.RESULT_OK,oIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}