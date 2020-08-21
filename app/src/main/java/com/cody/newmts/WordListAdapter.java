package com.cody.newmts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder1> {

    class ViewHolder1 extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView content, title;//, viewCount, likeCount, comCount;
        private TextView shareStory, createdTime,stats;
        ImageView categoryImage;

        private ViewHolder1(View itemView,List<Object> readStory) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cViewRead);
            title = (TextView) itemView.findViewById(R.id.readHeading);
            content = (TextView) itemView.findViewById(R.id.readContent);
            createdTime = (TextView) itemView.findViewById(R.id.createdTime);
            categoryImage = (ImageView) itemView.findViewById(R.id.categoryImage);
            stats=itemView.findViewById(R.id.stats);
        }
    }

    private final LayoutInflater mInflater;
    //private List<Word> mWords; // Cached copy of words
    private List<Object> readStory;
    Context mcontext;

    WordListAdapter(List<Object> readStory,Context context)
    {
        this.readStory = readStory;
        this.mcontext=context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.card_posts, parent, false);
        return new ViewHolder1(itemView,readStory);
    }

    @Override
    public void onBindViewHolder(ViewHolder1 viewHolder, int position) {

        ViewHolder1 viewHolder1 = (ViewHolder1) viewHolder;
        DraftStory draftStory = (DraftStory) readStory.get(position);
        viewHolder1.title.setText(draftStory.title);
        viewHolder1.content.setText(draftStory.content);
            /*viewHolder1.viewCount.setText(draftStory.viewCount);
            viewHolder1.likeCount.setText(draftStory.likeCount);
            viewHolder1.comCount.setText(draftStory.comCount);*/
        viewHolder1.createdTime.setText(draftStory.created);
        String temp = draftStory.viewCount + " Views " +draftStory.likeCount+" Likes " + draftStory.comCount +" Comments";
        viewHolder1.stats.setText(temp);
        viewHolder1.categoryImage.setImageResource(R.mipmap.ic_launcher);
    }

    void setWords(List<Object> words){
        readStory = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (readStory != null)
            return readStory.size();
        else return 0;
    }
}
