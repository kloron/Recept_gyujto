package com.gyula.recipebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> { // Ennél az activity-nél lészítjük el a felületet a listázáshoz, illetve a töbsszörös adatmegjelenítés kezelését
    // variable for our array list and context
    private  ArrayList<Recipe> listSQLite;
    private final Context context;

    public RecycleViewAdapter(ArrayList<Recipe> list, Context context) {
        this.listSQLite = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_read_sqlite, parent, false);
        return new ViewHolder(view);
    }

    public void filterList(ArrayList<Recipe> filterlist) {
        listSQLite = filterlist;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe db = listSQLite.get(position);
        String categoriesHelp = " Kategória: " + db.getCategories();
        String titleHelp = " Cím: " + db.getTitle();
        String timeHelp = " Idő: " + db.getTime();
        String timeTypeHelp = " Idő típusa: " + db.getTimeType();

        holder.categories.setText(categoriesHelp);
        holder.title.setText(titleHelp);
        holder.time.setText(timeHelp);
        holder.timeType.setText(timeTypeHelp);
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return listSQLite.size();
    }

    public Context getContext() {
        return context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        public TextView categories, title, time, timeType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            categories = itemView.findViewById(R.id.kartyaSQL);
            title = itemView.findViewById(R.id.modulSQL);
            time = itemView.findViewById(R.id.nevSQL);
            timeType = itemView.findViewById(R.id.varosSQL);
        }
    }
}

