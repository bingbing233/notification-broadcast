package com.example.notification.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.notification.MyApplication;
import com.example.notification.R;

import java.util.ArrayList;
import java.util.List;

public class AppListActivity extends AppCompatActivity{

    final String TAG = "AppListActivity";
    RecyclerView recyclerView;
    AppAdapter appAdapter;
    List<App> apps;
    Handler handler;
    ProgressBar progressBar;
    boolean selectAll = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.app_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.select_all){
            selectAll = !selectAll;
            for(App app:apps){
                app.setChecked(selectAll);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        getActionBar().setTitle("白名单");
        progressBar = findViewById(R.id.progress_bar);

        apps = new ArrayList<>();
        recyclerView = findViewById(R.id.app_list);
        appAdapter = new AppAdapter(apps);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(appAdapter);

        //获取已安装app列表
        new Thread(new Runnable() {
            @Override
            public void run() {
                getApp();
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }).start();

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                if(message.what==1){
                    progressBar.setVisibility(View.INVISIBLE);
                    appAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
    }

    public void getApp(){
        List<PackageInfo> packageInfos = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo packageInfo = packageInfos.get(i);
            //非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                App app = new App();
                app.setPackageName(packageInfo.packageName);
                app.setName(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());
                app.setIcon(packageInfo.applicationInfo.loadIcon(getPackageManager()));
                app.setChecked(MyApplication.getPreferences().getBoolean(app.getName(),false));
                apps.add(app);
            }
        }
    }

}