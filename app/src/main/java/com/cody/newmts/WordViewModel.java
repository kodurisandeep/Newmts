package com.cody.newmts;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class WordViewModel extends AndroidViewModel implements AsyncResponse{

    private MutableLiveData<List<Object>> mAllWords;
    private List<Object> readStory;
    //LiveData<String> result;

    public WordViewModel (Application application) {
        super(application);
        mAllWords=new MutableLiveData<>();
        readStory=new ArrayList<>();
    }

    public void getData(String url){
        new WordRepository(WordViewModel.this).execute(url);
    }

    /*public LiveData<String> checkData(){
        return result;
        //new WordRepository(WordViewModel.this).execute(url);
    }*/

    LiveData<List<Object>> getAllWords() {
        return mAllWords;
    }


    @Override
    public void processFinish(String output, String address) {
        try {
            if(address.contains("mts_getstories")) {
                output = "{\"read\":" + output + "}";
                JSONObject jsonObject = new JSONObject(output);
                JSONArray jsonArray = jsonObject.optJSONArray("read");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    readStory.add(new DraftStory(jsonObject1.optString("title").toString(),
                            jsonObject1.optString("content").toString(),
                            jsonObject1.optString("storyId").toString(), jsonObject1.optString("author").toString(), null,
                            jsonObject1.optString("view_count").toString(),// + " V",
                            jsonObject1.optString("like_count").toString(),// + " L",
                            jsonObject1.optString("com_count").toString(),// + " C",
                            jsonObject1.optString("created").toString().substring(0, 10),
                            jsonObject1.optString("category_name").toString()));
                }
                mAllWords.setValue(readStory);
            }
            else{
               // result=output;
                //context.getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("id",output).apply();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
