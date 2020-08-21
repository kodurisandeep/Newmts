package com.cody.newmts;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by cody on 22-11-2017.
 */

public class Logo_Page extends AppCompatActivity {

    String id;
    List<Object> readStory;
    WordListAdapter adapter;
    private WordViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.logo_page);

        readStory = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.rViewRead);
        adapter = new WordListAdapter(readStory, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, new Observer<List<Object>>() {
            @Override
            public void onChanged(@Nullable final List<Object> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
            }
        });

        /*mWordViewModel.checkData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String words) {
                // Update the cached copy of the words in the adapter.
            }
        });*/

        id = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("id", "0");
        if (id.equals("0")) {
            id = getUID();
            if (id != null)
                checkData();
            else {
                showToast("Try Later.");
                finish();
            }
            Log.i(" ### ", id);
        } else
            startLoad();

    }

    private void checkData(){
        mWordViewModel.getData("https://www.janathapulse.com/mts_saveId.php?id=" + id);
        startLoad();
    }

    private void startLoad() {
        mWordViewModel.getData("https://www.janathapulse.com/mts_getstories.php/?id=" + id);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public String getUID() {
        String idShort = "18" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.MODEL.length() % 10) + (Build.DEVICE.length() % 10)
                + (Build.MANUFACTURER.length() % 10) + (Build.PRODUCT.length() % 10);
        String serial = null;
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
        } catch (Exception e) {
            serial = "serial";
        }
        return String.valueOf(new UUID(idShort.hashCode(), serial.hashCode()));
    }


}
