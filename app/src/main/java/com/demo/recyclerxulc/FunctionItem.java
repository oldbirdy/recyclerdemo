package com.demo.recyclerxulc;

public class FunctionItem {

    public String name;
    public boolean isSelect = false;
    public String imageUrl = "";
    public String background ="";
    public boolean isTitle = false;
    public int subItemCount = 0;



    public FunctionItem(String name, boolean isSelect, String imageUrl, String background) {
        this.name = name;
        this.isSelect = isSelect;
        this.imageUrl = imageUrl;
        this.background = background;
    }

    public FunctionItem(String name,boolean isTitle,int subItemCount){
        this.name = name;
        this.isTitle = isTitle;
        this.subItemCount = subItemCount;
    }

    public FunctionItem(String name,boolean isTitle){
        this.name = name;
        this.isTitle = isTitle;
    }

}
