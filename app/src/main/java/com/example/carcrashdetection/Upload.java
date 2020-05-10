package com.example.carcrashdetection;

public class Upload {
    private String uplaodUri;

    public Upload(){

    }
    public Upload(String imageUri){
        uplaodUri = imageUri;

    }

    public String getUplaodUri() {
        return uplaodUri;
    }

    public void setUplaodUri(String uplaodUri) {
        this.uplaodUri = uplaodUri;
    }
}
