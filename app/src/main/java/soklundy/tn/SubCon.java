package soklundy.tn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.google.gson.Gson;
import com.kosalgeek.android.json.JsonConverter;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

import soklundy.tn.dtoContent.ListContent;

public class SubCon extends AppCompatActivity implements AsyncResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_con);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PostResponseAsyncTask response = new PostResponseAsyncTask(SubCon.this,this);
        response.execute("http://10.0.3.2:8080/TNews/listall.php");
    }

    @Override
    public void processFinish(String result) {

        /*using to start universal image loader
        *  UTL Config method using to config UIL : http://www.kosalgeek.com/2015/09/android-load-image-from-url-2.html
        *  need to using compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.4'
        *   */
        ImageLoader.getInstance().init(UILConfig());

        /*all code below using fundapter*/
        final ArrayList<ListContent> listCon = new JsonConverter<ListContent>().toArrayList(result, ListContent.class);
        BindDictionary<ListContent> listcontent = new BindDictionary<ListContent>();

        listcontent.addStringField(R.id.titleLayout, new StringExtractor<ListContent>() {
            @Override
            public String getStringValue(ListContent item, int position) {
                return item.getTitle();
            }
        });

        /*
        * using to load image with fundapter and UIL
        * */
        listcontent.addDynamicImageField(R.id.imageView2, new StringExtractor<ListContent>() {
            @Override
            public String getStringValue(ListContent item, int position) {
                return item.getImgUrl();
            }
        }, new DynamicImageLoader() {
            @Override
            public void loadImage(String url, ImageView imageView) {
                ImageLoader.getInstance().displayImage(url, imageView);
            }
        });

        listcontent.addStringField(R.id.date, new StringExtractor<ListContent>() {
            @Override
            public String getStringValue(ListContent item, int position) {
                return item.getPublicDate();
            }
        });

        FunDapter<ListContent> adpter = new FunDapter<ListContent>(SubCon.this,listCon,R.layout.newtitle_layout,listcontent);
        final ListView lvtitle =(ListView)findViewById(R.id.vlistView);
        lvtitle.setAdapter(adpter);

        /*list view on item click*/
        lvtitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Gson gson = new Gson();
                String jsonlistCon = gson.toJson(listCon);
                Intent intent = new Intent(SubCon.this, ContentDetail.class);
                intent.putExtra("position",""+position);
                intent.putExtra("listCon",jsonlistCon);
                startActivity(intent);
            }
        });

    }
    /*//region oncclick_lvtiltle_listener

    //endregion
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }*/

    private ImageLoaderConfiguration UILConfig(){
        DisplayImageOptions defaultOptions =
                new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .showImageOnLoading(android.R.drawable.stat_sys_download)
                        .showImageForEmptyUri(android.R.drawable.ic_dialog_alert)
                        .showImageOnFail(android.R.drawable.stat_notify_error)
                        .considerExifParams(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                        .build();

        ImageLoaderConfiguration config =
                new ImageLoaderConfiguration
                        .Builder(getApplicationContext())
                        .threadPriority(Thread.NORM_PRIORITY - 2)
                        .denyCacheImageMultipleSizesInMemory()
                        .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                        .tasksProcessingOrder(QueueProcessingType.LIFO)
                        .defaultDisplayImageOptions(defaultOptions)
                        .build();

        return config;
    }

}
