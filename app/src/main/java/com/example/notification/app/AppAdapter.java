package com.example.notification.app;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notification.MyApplication;
import com.example.notification.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    List<App> apps;
    final String TAG = "AppAdapter";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final App app = apps.get(position);
        holder.appName.setText(app.getName());
        holder.appPackageName.setText(app.getPackageName());
        holder.icon.setImageDrawable(app.getIcon());
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(MyApplication.getPreferences().getBoolean(app.getPackageName(),false));

        //当checkBox状态改变时，保存preference
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MyApplication.getEditor().putBoolean(app.getPackageName(),b);
                MyApplication.getEditor().apply();
            }
        });



        //点击item时，相当于点击了checkBox
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.checkBox.setChecked(!holder.checkBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
       ImageView icon;
       TextView appName,appPackageName;
       CheckBox checkBox;
       View view;

       public ViewHolder(View view){
           super(view);
           icon = view.findViewById(R.id.app_icon);
           appName = view.findViewById(R.id.app_name);
           appPackageName = view.findViewById(R.id.app_packageName);
           checkBox = view.findViewById(R.id.app_check);
           this.view = view;
       }
   }

   public AppAdapter(List<App> apps){
       this.apps = apps;
   }
}
