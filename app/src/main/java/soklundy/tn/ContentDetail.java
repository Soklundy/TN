package soklundy.tn;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import soklundy.tn.dtoContent.ListContent;

public class ContentDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get from SubCon activity
        String listConAsString = getIntent().getStringExtra("listCon");
        int position = Integer.parseInt(getIntent().getStringExtra("position"));

        Gson gson = new Gson();
        Type type = new TypeToken<List<ListContent>>(){}.getType();
        ArrayList<ListContent> listCon = gson.fromJson(listConAsString, type);
        //end blog code get from SubCon activity

        /*delaration Image View*/
        ImageView imageView = (ImageView) findViewById(R.id.imageDetail);

        /*String url*/
        String url =listCon.get(position).getImgUrl();

        //load image
        // Universal Image loader
        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
        imageLoader.displayImage(url, imageView);

        // Load image, decode it to Bitmap and return Bitmap to callback
        imageLoader.loadImage(url, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }
        });
        Bitmap bmp = imageLoader.loadImageSync(url);
        //end blog code Universal image loader

        TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        TextView tvDetail = (TextView)findViewById(R.id.tvDetail);
        TextView tvDate = (TextView)findViewById(R.id.tvDate);

        tvTitle.setText("Title: "+listCon.get(position).getTitle());
        tvDetail.setText(listCon.get(position).getDiscription());
        tvDate.setText("Publish Date: "+listCon.get(position).getPublicDate());
    }

}
