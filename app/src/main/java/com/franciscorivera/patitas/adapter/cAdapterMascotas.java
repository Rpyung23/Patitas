package com.franciscorivera.patitas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.franciscorivera.patitas.R;
import com.franciscorivera.patitas.interfaces.OnClickListenerRecyclerView;
import com.franciscorivera.patitas.poo.cMascota;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class cAdapterMascotas extends RecyclerView.Adapter<cAdapterMascotas.cViewHolder> {
    private ArrayList<cMascota> oMascotaArrayList;
    private OnClickListenerRecyclerView onClickListenerRecyclerView;
    private RecyclerView oRecyclerView;


    public cAdapterMascotas(ArrayList<cMascota> oMascotaArrayList,
                            OnClickListenerRecyclerView onClickListenerRecyclerView,
                            RecyclerView mRecyclerView)
    {
        this.oMascotaArrayList = oMascotaArrayList;
        this.onClickListenerRecyclerView = onClickListenerRecyclerView;
        this.oRecyclerView = mRecyclerView;
    }

    @NonNull
    @Override
    public cViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View oV = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_animal,parent,false);
        return new cViewHolder(oV);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolder holder, int position)
    {
        holder.oTextViewNameMascota.setText(oMascotaArrayList.get(position).getNameMascota());
        holder.oTextViewNameDuenio.setText(oMascotaArrayList.get(position).getoD().getNameDuenio());
        switch (oMascotaArrayList.get(position).getTypeMascota())
        {
            case "Perro":
                insertImg(R.drawable.dog,holder.oAppCompatImageViewTypeAnimal);
                break;
            case "Gato":
                insertImg(R.drawable.cat,holder.oAppCompatImageViewTypeAnimal);
                break;
            case "Conejo":
                insertImg(R.drawable.rabbit,holder.oAppCompatImageViewTypeAnimal);
                break;
            case "Tortuga":
                insertImg(R.drawable.turtle,holder.oAppCompatImageViewTypeAnimal);
                break;
            case "Serpiente":
                insertImg(R.drawable.snake,holder.oAppCompatImageViewTypeAnimal);
                break;
            case "Hámster":
                insertImg(R.drawable.hamster,holder.oAppCompatImageViewTypeAnimal);
                break;
            case "Huron":
                insertImg(R.drawable.huron,holder.oAppCompatImageViewTypeAnimal);
                break;
            case "Canario":
                insertImg(R.drawable.canario,holder.oAppCompatImageViewTypeAnimal);
            break;
            case "Pez payaso":
                insertImg(R.drawable.payaso,holder.oAppCompatImageViewTypeAnimal);
                break;
            case "Loro":
                insertImg(R.drawable.loro,holder.oAppCompatImageViewTypeAnimal);
                break;

        }


    }

    private void insertImg(int id,AppCompatImageView oAppCompatImageViewTypeAnimal)
    {
        Picasso.get()
                .load(id)
                .into(oAppCompatImageViewTypeAnimal);
    }

    @Override
    public int getItemCount() {
        return oMascotaArrayList.size();
    }

    public class cViewHolder extends RecyclerView.ViewHolder
    {
        private AppCompatImageView oAppCompatImageViewTypeAnimal;
        private TextView oTextViewNameMascota;
        private TextView oTextViewNameDuenio;

        public cViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.oAppCompatImageViewTypeAnimal = itemView.findViewById(R.id.ImgAnimal);
            this.oTextViewNameMascota = itemView.findViewById(R.id.nameAnimal);
            this.oTextViewNameDuenio = itemView.findViewById(R.id.nameDuenio);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    onClickListenerRecyclerView.OnClick(oRecyclerView.getChildAdapterPosition(itemView));
                }
            });
        }
    }
}
