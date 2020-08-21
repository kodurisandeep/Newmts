package com.cody.newmts;

/**
 * Created by cody on 22-07-2017.
 */

public class DraftStory {

    String title,content,storyId,author,liked,viewCount,likeCount,comCount,created,category;

    DraftStory(String title,String content,String storyId,String author,
               String liked,String viewCount,String likeCount,String comCount,String created,String category) {
        this.title=title;
        this.content=content;
        this.storyId=storyId;
        this.author=author;
        this.liked=liked;
        this.viewCount=viewCount;
        this.likeCount=likeCount;
        this.comCount=comCount;
        this.created=created;
        this.category=category;
    }
}
