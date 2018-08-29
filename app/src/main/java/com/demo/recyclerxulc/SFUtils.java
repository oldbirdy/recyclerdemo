package com.demo.recyclerxulc;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xulc on 2018/6/29.
 */

public class SFUtils {
    private Context context;
    private SharedPreferences sp = null;

    public SFUtils(Context context) {
        this.context = context;
        sp = context.getSharedPreferences("ceshi",Context.MODE_PRIVATE);
    }

    public List<FunctionItem> getAllFunctionWithState(){
        String allData = sp.getString("allData","");
        List<FunctionItem> functionItems = new ArrayList<>();
        List<TabItem> tabItems = null;
        if("".equals(allData)){
            try {
                InputStream is = context.getAssets().open("ceshi.txt");
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String str = null;
                StringBuffer sbuffer = new StringBuffer();
                while ((str = br.readLine())!=null){
                    sbuffer.append(str);
                }
                br.close();
                isr.close();
                is.close();
                allData = sbuffer.toString();
                Gson gson = new Gson();
                tabItems =  gson.fromJson(allData,new TypeToken<List<TabItem>>(){}.getType());
                if(tabItems!=null){
                    for(int i=0;i< tabItems.size();i++){
                        FunctionItem functionItem = new FunctionItem(tabItems.get(i).getTabName(),true);
                        functionItems.add(functionItem);
                        functionItems.addAll(tabItems.get(i).getFunctionItems());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Gson gson = new Gson();
            functionItems =  gson.fromJson(allData,new TypeToken<List<FunctionItem>>(){}.getType());
        }
        return functionItems;
    }

    public List<FunctionItem> getSelectFunctionItem(){
        String selData = sp.getString("selData","");
        List<FunctionItem> functionItems = null;
        if("".equals(selData)){
            functionItems =  new ArrayList<>();
        }else{
            Gson gson = new Gson();
            functionItems =  gson.fromJson(selData,new TypeToken<List<FunctionItem>>(){}.getType());
        }
        return functionItems;
    }

    public List<FunctionItem> getTabNames(){
        String allData = sp.getString("allData","");
        List<FunctionItem> functionItems = new ArrayList<>();
        List<TabItem> tabItems = null;
        if("".equals(allData)){
            try {
                InputStream is = context.getAssets().open("ceshi.txt");
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String str = null;
                StringBuffer sbuffer = new StringBuffer();
                while ((str = br.readLine())!=null){
                    sbuffer.append(str);
                }
                br.close();
                isr.close();
                is.close();
                allData = sbuffer.toString();
                Gson gson = new Gson();
                tabItems =  gson.fromJson(allData,new TypeToken<List<TabItem>>(){}.getType());
                if(tabItems!=null){
                    int tabItemsCount = 0; //用于标记之后滑动的位置
                    for(int i=0;i< tabItems.size();i++){
                        FunctionItem functionItem = new FunctionItem(tabItems.get(i).getTabName(),true,tabItemsCount);
                        tabItemsCount = tabItemsCount + tabItems.get(i).getFunctionItems().size() +1;
                        functionItems.add(functionItem);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        else{
//            Gson gson = new Gson();
//            functionItems =  gson.fromJson(allData,new TypeToken<List<FunctionItem>>(){}.getType());
//        }
        return functionItems;
    }
    public void saveSelectFunctionItem(List<FunctionItem> selData){
        Gson gson = new Gson();
        sp.edit().putString("selData",gson.toJson(selData)).apply();
    }

    public void saveAllFunctionWithState(List<FunctionItem> allData){
        Gson gson = new Gson();
        sp.edit().putString("allData",gson.toJson(allData)).apply();
    }

}
