package com.demo.recyclerxulc;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerViewExist, recyclerViewAll;

    private HorizontalScrollView horizonLScrollView;
    private RadioGroup rg_tab;

    private FunctionBlockAdapter blockAdapter;
    private FunctionAdapter functionAdapter;
    private GridLayoutManager gridManager;

    private List<String> scrollTab = new ArrayList<>();

    private int itemWidth = 0;
    private int lastRow = 0;
    private boolean isMove = false;//滑动状态
    private int scrollPosition = 0;
    private String currentTab;//当前的标签
    private int tabWidth = 0;//标签宽度


    private List<FunctionItem> allData;
    private List<FunctionItem> selData;
    private SFUtils sfUtils;
    private static final int MAX_COUNT = 14;
    private boolean isDrag =false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        addListener();
    }




    public void init() {
        getSupportActionBar().hide();
        recyclerViewExist = (RecyclerView) findViewById(R.id.recyclerViewExist);
        horizonLScrollView = (HorizontalScrollView) findViewById(R.id.horizonLScrollView);
        rg_tab = (RadioGroup) findViewById(R.id.rg_tab);
        recyclerViewAll = (RecyclerView) findViewById(R.id.recyclerViewAll);
        sfUtils  = new SFUtils(this);
        allData = sfUtils.getAllFunctionWithState();
        selData = sfUtils.getSelectFunctionItem();

        blockAdapter = new FunctionBlockAdapter(this, selData);
        recyclerViewExist.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerViewExist.setAdapter(blockAdapter);
        recyclerViewExist.addItemDecoration(new SpaceItemDecoration(4, dip2px(this, 10)));

        DefaultItemCallback callback = new DefaultItemCallback(blockAdapter);
        DefaultItemTouchHelper helper = new DefaultItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerViewExist);

        gridManager = new GridLayoutManager(this, 4);
        gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                FunctionItem fi = allData.get(position);
                return fi.isTitle ? 4 : 1;
            }
        });

        functionAdapter = new FunctionAdapter(this, allData);
        recyclerViewAll.setLayoutManager(gridManager);
        recyclerViewAll.setAdapter(functionAdapter);
        SpaceItemDecoration spaceDecoration = new SpaceItemDecoration(4, dip2px(this, 10));
        recyclerViewAll.addItemDecoration(spaceDecoration);

        itemWidth = getAtyWidth(this) / 4 + dip2px(this, 2);

        resetEditHeight(selData.size());

        initTab();
    }


    public  int getAtyWidth(Context context) {
        try {
            DisplayMetrics mDm = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay()
                    .getMetrics(mDm);
            return mDm.widthPixels;
        } catch (Exception e) {
            return 0;
        }
    }

    public void addListener() {
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sfUtils.saveSelectFunctionItem(selData);
                sfUtils.saveAllFunctionWithState(allData);
            }
        });
        functionAdapter.setOnItemAddListener(new FunctionAdapter.OnItemAddListener() {
            @Override
            public boolean add(FunctionItem item) {
                if (selData != null && selData.size() < MAX_COUNT) {   // 更新选择列表，所有列表已在内部进行更新
                    try {
                        selData.add(item);
                        resetEditHeight(selData.size());
                        blockAdapter.notifyDataSetChanged();
                        item.isSelect = true;
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                } else {
                    Toast.makeText(MainActivity.this,"选中的模块不能超过"+MAX_COUNT+"个",Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });

        blockAdapter.setOnItemRemoveListener(new FunctionBlockAdapter.OnItemRemoveListener() {
            @Override
            public void remove(FunctionItem item) {
                // 更新所有列表，选择列表已在内部进行更新
                try {
                    if (item != null && item.name != null) {
                        for (int i = 0; i < allData.size(); i++) {
                            FunctionItem data = allData.get(i);
                            if (data != null && data.name != null) {
                                if (item.name.equals(data.name)) {
                                    data.isSelect = false;
                                    break;
                                }
                            }
                        }
                        functionAdapter.notifyDataSetChanged();
                    }
                    resetEditHeight(selData.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        recyclerViewAll.addOnScrollListener(onScrollListener);
    }

    @Override
    public void onClick(View v) {
    }

    private void initTab() {
        try {
            List<FunctionItem> tabs = sfUtils.getTabNames();


            if (tabs != null && tabs.size() > 0) {
                currentTab = tabs.get(0).name;
                int padding = dip2px(this, 10);
                int size = tabs.size();
                for (int i = 0; i < size; i++) {
                    FunctionItem item = tabs.get(i);
                    if(item.isTitle){
                        scrollTab.add(item.name);
                        RadioButton rb = new RadioButton(this);
                        rb.setPadding(padding, 0, padding, 0);
                        rb.setButtonDrawable(null);
                        rb.setGravity(Gravity.CENTER);
                        rb.setText(item.name);
                        rb.setTag(item.subItemCount);
                        rb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                        try {
                            rb.setTextColor(getResources().getColorStateList(R.color.bg_block_text));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        rb.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.bg_block_tab));
                        rb.setOnCheckedChangeListener(onCheckedChangeListener);
                        rg_tab.addView(rb);
                    }
                }
                ((RadioButton) rg_tab.getChildAt(0)).setChecked(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            try {
                int position = (int) buttonView.getTag();
                String text = buttonView.getText().toString();
                if (!currentTab.equals(text) && isChecked) {
                    currentTab = text;
                    moveToPosition(position);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    private void resetEditHeight(int size) {
        try {
            if (size == 0) {
                size = 1;
            }
            int row = size / 4 + (size % 4 > 0 ? 1 : 0);
            if (row <= 0)
                row = 1;
            if (lastRow != row) {
                lastRow = row;
                ViewGroup.LayoutParams params = recyclerViewExist.getLayoutParams();
                params.height = itemWidth * row;
                recyclerViewExist.setLayoutParams(params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void moveToPosition(int position) {
        int first = gridManager.findFirstVisibleItemPosition();
        int end = gridManager.findLastVisibleItemPosition();
        if (first == -1 || end == -1)
            return;
        if (position <= first) {      //移动到前面
            gridManager.scrollToPosition(position);
        } else if (position >= end) {      //移动到后面
            isMove = true;
            scrollPosition = position;
            gridManager.smoothScrollToPosition(recyclerViewAll, null, position);
        } else {//中间部分
            int n = position - gridManager.findFirstVisibleItemPosition();
            if (n > 0 && n < allData.size()) {
                int top = gridManager.findViewByPosition(position).getTop();
                recyclerViewAll.scrollBy(0, top);
            }
        }
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            try {
                if (isMove && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isMove = false;
                    View view = gridManager.findViewByPosition(scrollPosition);
                    if (view != null) {
                        int top = (int) view.getTop();
                        recyclerView.scrollBy(0, top);
                    }
                }
                if(newState==RecyclerView.SCROLL_STATE_DRAGGING){
                    isDrag = true;
                }else{
                    isDrag = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if(isDrag){  //拖动过程中
                int position = gridManager.findFirstVisibleItemPosition();
                if(position>0){
                    for(int i=0;i<position+1;i++){
                        if(allData.get(i).isTitle){
                            currentTab = allData.get(i).name;
                        }
                    }
                    scrollTab(currentTab);
                }
            }
        }
    };



    private void scrollTab(String newTab) {
        try {
            int position = scrollTab.indexOf(currentTab);
            int targetPosition = scrollTab.indexOf(newTab);
            currentTab = newTab;
            if (targetPosition != -1) {
                int x = (targetPosition - position) * getTabWidth();
                RadioButton radioButton = ((RadioButton) rg_tab.getChildAt(targetPosition));
                radioButton.setOnCheckedChangeListener(null);
                radioButton.setChecked(true);
                radioButton.setOnCheckedChangeListener(onCheckedChangeListener);
                horizonLScrollView.scrollBy(x, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getTabWidth() {
        if (tabWidth == 0) {
            if (rg_tab != null && rg_tab.getChildCount() != 0) {
                tabWidth = rg_tab.getWidth() / rg_tab.getChildCount();
            }
        }
        return tabWidth;
    }

    public  int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
