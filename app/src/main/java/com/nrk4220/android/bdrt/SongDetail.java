package com.nrk4220.android.bdrt;

public class SongDetail {

    private String eng;
    private String jap;
    private int image;
    private String url;
    private static String streamUrl;

    public SongDetail (String eng, String jap, int image, String url){
        this.eng = eng;
        this.jap = jap;
        this.image = image;
        this.url = url;
    }

    public SongDetail (String eng, String jap, int image){
        this.eng = eng;
        this.jap = jap;
        this.image = image;
    }

    public String getEngName(){
        return eng;
    }

    public String getJapName(){
        return jap;
    }

    public int getImageData(){
        return image;
    }

    public String getUrl(){
        return url;
    }

    public static void setStreamUrl(String string){
        streamUrl = string;
    }

    public static String getStreamUrl(){
        return streamUrl;
    }


}
