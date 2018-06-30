package com.demo.ceshi;



import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by xulc on 2018/6/29.
 */

public class Ceshi {
    private static Ceshi ceshi;

    public static void main(String args[]){
        ceshi = new Ceshi();
        startCeshi();
    }

    private static void startCeshi() {
        ceshi.initData();
    }
    private  void initData()  {
        ArrayList<TabItem> tabItems = new ArrayList<>();
        ArrayList<FunctionItem> arrayList = new ArrayList<>();
        arrayList.add(new FunctionItem("充值中心",false,"icon_home_selected","#86c751"));
        arrayList.add(new FunctionItem("信用卡还款",false,"icon_home_selected","#86c751"));
        arrayList.add(new FunctionItem("生活缴费",false,"icon_home_selected","#86c751"));
        arrayList.add(new FunctionItem("城市服务",false,"icon_home_selected","#86c751"));
        arrayList.add(new FunctionItem("生活号",false,"icon_home_selected","#86c751"));
        arrayList.add(new FunctionItem("我的客服",false,"icon_home_selected","#86c751"));
        arrayList.add(new FunctionItem("我的快递",false,"icon_home_selected","#86c751"));
        arrayList.add(new FunctionItem("医疗健康",false,"icon_home_selected","#86c751"));
        arrayList.add(new FunctionItem("记账本",false,"icon_home_selected","#86c751"));
        arrayList.add(new FunctionItem("城市一卡通",false,"icon_home_selected","#86c751"));
        arrayList.add(new FunctionItem("发票管家",false,"icon_home_selected","#86c751"));
        arrayList.add(new FunctionItem("蚂蚁宝卡",false,"icon_home_selected","#86c751"));
        arrayList.add(new FunctionItem("车主服务",false,"icon_home_selected","#86c751"));
        arrayList.add(new FunctionItem("天天有料",false,"icon_home_selected","#86c751"));
        TabItem tabItem = new TabItem("便民生活",arrayList);
        tabItems.add(tabItem);

        ArrayList<FunctionItem> arrayList1 = new ArrayList<>();
        arrayList1.add(new FunctionItem("余额宝",false,"icon_home_selected","#86c751"));
        arrayList1.add(new FunctionItem("花呗",false,"icon_home_selected","#86c751"));
        arrayList1.add(new FunctionItem("芝麻信用",false,"icon_home_selected","#86c751"));
        arrayList1.add(new FunctionItem("蚂蚁借呗",false,"icon_home_selected","#86c751"));
        arrayList1.add(new FunctionItem("股票",false,"icon_home_selected","#86c751"));
        arrayList1.add(new FunctionItem("保险服务",false,"icon_home_selected","#86c751"));
        arrayList1.add(new FunctionItem("汇率换算",false,"icon_home_selected","#86c751"));
        arrayList1.add(new FunctionItem("理财小工具",false,"icon_home_selected","#86c751"));

        TabItem tabItem1 = new TabItem("财务管理",arrayList1);
        tabItems.add(tabItem1);

        ArrayList<FunctionItem> arrayList2 = new ArrayList<>();
        arrayList2.add(new FunctionItem("转账",false,"icon_home_selected","#86c751"));
        arrayList2.add(new FunctionItem("红包",false,"icon_home_selected","#86c751"));
        arrayList2.add(new FunctionItem("AA收款",false,"icon_home_selected","#86c751"));
        arrayList2.add(new FunctionItem("亲密付",false,"icon_home_selected","#86c751"));
        arrayList2.add(new FunctionItem("上银汇款",false,"icon_home_selected","#86c751"));
        arrayList2.add(new FunctionItem("话费卡转让",false,"icon_home_selected","#86c751"));
        TabItem tabItem2 = new TabItem("资金往来",arrayList2);
        tabItems.add(tabItem2);

        ArrayList<FunctionItem> arrayList3 = new ArrayList<>();
        arrayList3.add(new FunctionItem("游戏中心",false,"icon_home_selected","#86c751"));
        arrayList3.add(new FunctionItem("出境",false,"icon_home_selected","#86c751"));
        arrayList3.add(new FunctionItem("彩票",false,"icon_home_selected","#86c751"));
        arrayList3.add(new FunctionItem("人脸识别",false,"icon_home_selected","#86c751"));
        arrayList3.add(new FunctionItem("奖励金",false,"icon_home_selected","#86c751"));
        arrayList3.add(new FunctionItem("世界杯",false,"icon_home_selected","#86c751"));
        TabItem tabItem3 = new TabItem("购物娱乐",arrayList3);
        tabItems.add(tabItem3);

        ArrayList<FunctionItem> arrayList4 = new ArrayList<>();
        arrayList4.add(new FunctionItem("大学生活",false,"icon_home_selected","#86c751"));
        arrayList4.add(new FunctionItem("爱心捐赠",false,"icon_home_selected","#86c751"));
        arrayList4.add(new FunctionItem("蚂蚁森林",false,"icon_home_selected","#86c751"));
        arrayList4.add(new FunctionItem("蚂蚁庄园",false,"icon_home_selected","#86c751"));
        arrayList4.add(new FunctionItem("中小学",false,"icon_home_selected","#86c751"));
        arrayList4.add(new FunctionItem("运动",false,"icon_home_selected","#86c751"));
        TabItem tabItem4 = new TabItem("教育公益",arrayList4);
        tabItems.add(tabItem4);

        ArrayList<FunctionItem> arrayList5 = new ArrayList<>();
        arrayList5.add(new FunctionItem("淘票票",false,"icon_home_selected","#86c751"));
        arrayList5.add(new FunctionItem("滴滴出行",false,"icon_home_selected","#86c751"));
        arrayList5.add(new FunctionItem("饿了么外卖",false,"icon_home_selected","#86c751"));
        arrayList5.add(new FunctionItem("天猫",false,"icon_home_selected","#86c751"));
        arrayList5.add(new FunctionItem("淘宝",false,"icon_home_selected","#86c751"));
        arrayList5.add(new FunctionItem("火车票机票",false,"icon_home_selected","#86c751"));
        TabItem tabItem5 = new TabItem("第三方服务",arrayList5);
        tabItems.add(tabItem5);

        Gson gson = new Gson();
        String json = gson.toJson(tabItems);
        try {
            FileOutputStream fos = new FileOutputStream("ceshi.xml");
            fos.write(json.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
