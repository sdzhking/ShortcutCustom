package com.sdzhking.shortcut;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

    private RecyclerView icoList;
    private List<ItemEntity> chooseDataList;
    private ShortCutUtil shortCutUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(3)//线程池内加载的数量
                .memoryCache(new LRULimitedMemoryCache(4*1024*1024))
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(30 * 1024 * 1024) // 30 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .imageDownloader(new AliImageLoader(this))
                .build();
        //.writeDebugLogs() // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);

        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        shortCutUtil = new ShortCutUtil(this);
        icoList = findViewById(R.id.icon_list);
        chooseDataList = new ArrayList<>();
        chooseDataList.add(new ItemEntity("太乙真人", "http://5b0988e595225.cdn.sohucs.com/images/20190806/17323a86693b4a269414c3fe99ecab90.jpeg", R.mipmap.group_head));
        chooseDataList.add(new ItemEntity("哪吒", "http://5b0988e595225.cdn.sohucs.com/images/20190806/f7c8ccaa6e36423da59bca2030f8b932.jpeg", R.mipmap.group_head));
        chooseDataList.add(new ItemEntity("杨戬", "http://5b0988e595225.cdn.sohucs.com/images/20190806/53c900aef8a9462cb2b67b2988cdad06.jpeg", R.mipmap.group_head));

        icoList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ChooseAdapter mAdapter = new ChooseAdapter(chooseDataList);
        icoList.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.create:
                        ItemEntity item = chooseDataList.get(position);
                        shortCutUtil.createShortCut(item.getName(), item.getIconurl(), String.valueOf(position + 1));
                        break;

                    default:
                        break;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class ChooseAdapter extends BaseQuickAdapter<ItemEntity, BaseViewHolder> {
        private DisplayImageOptions options;
        public ChooseAdapter(@Nullable List<ItemEntity> data) {
            super(R.layout.item_list, data);
            options = new DisplayImageOptions.Builder()
                    .cacheInMemory(false)
                    .cacheOnDisk(true)
                    .build();
        }

        @Override
        protected void convert(BaseViewHolder helper, ItemEntity item) {

            ImageView avatar = helper.getView(R.id.appimg);
            ImageLoader.getInstance().displayImage(item.getIconurl(), avatar, options);
//            avatar.setImageResource(item.getResId());
            TextView title = helper.getView(R.id.name);
            title.setText(item.getName());

            helper.addOnClickListener(R.id.create);
        }
    }

    class ItemEntity {
        private String name;
        private String iconurl;
        private int resId;

        public ItemEntity(String name, String iconurl, int resId) {
            this.name = name;
            this.iconurl = iconurl;
            this.resId = resId;
        }

        public String getName() {
            return name;
        }

        public String getIconurl() {
            return iconurl;
        }

        public int getResId() {
            return resId;
        }
    }
}
