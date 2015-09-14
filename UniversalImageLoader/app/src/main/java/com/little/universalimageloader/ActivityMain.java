package com.little.universalimageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class ActivityMain extends Activity {

    AdapterForListView adapterForListView;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initImageLoader();

        listView = (ListView)this.findViewById(R.id.listView);
        listView.setAdapter(new AdapterForListView(this));


    }

    private void initImageLoader() {

        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();

    }

    class AdapterForListView extends BaseAdapter {

        ArrayList<Image> listImage;
        Context context;
        DisplayImageOptions displayImageOptions;
        ImageLoadingListenerImpl imageLoadingListener;


        public AdapterForListView(Context context) {
            this.context = context;
            this.listImage = new ArrayList<>();
            for(int i=0;i<5;i++) {
                listImage.add( new Image("http://img1.imgtn.bdimg.com/it/u=1825165654,1935296637&fm=21&gp=0.jpg") );
                listImage.add( new Image("http://img3.imgtn.bdimg.com/it/u=3841157212,2135341815&fm=21&gp=0.jpg") );
                listImage.add( new Image("http://img0.imgtn.bdimg.com/it/u=2578125358,1342349492&fm=21&gp=0.jpg") );
                listImage.add( new Image("http://img5.imgtn.bdimg.com/it/u=1413520858,2550742243&fm=21&gp=0.jpg") );
                listImage.add( new Image("http://img0.imgtn.bdimg.com/it/u=3273096996,3231747784&fm=21&gp=0.jpg") );

            }



            int defaultImageId = R.drawable.ic_launcher;
            displayImageOptions = new DisplayImageOptions.Builder()
                    .showStubImage(defaultImageId)
                    .showImageForEmptyUri(defaultImageId)
                    .showImageOnFail(defaultImageId)
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .resetViewBeforeLoading()
                    .build();

            imageLoadingListener = new ImageLoadingListenerImpl();
        }

        @Override
        public int getCount() {
            return listImage.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.lv_item,null);
                TextView tv = (TextView)convertView.findViewById(R.id.imageTitle);
                tv.setText(listImage.get(position).url);

                ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
                ImageLoader.getInstance().displayImage(
                                            listImage.get(position).url,
                                            imageView,
                                            displayImageOptions,
                                            imageLoadingListener

                        );

            }
            return convertView;
        }
    }


    class Image {
        String url;

        public Image(String url) {
            this.url = url;
        }
    }


    //监听图片异步加载
    public static class ImageLoadingListenerImpl extends SimpleImageLoadingListener {

        public static final List<String> displayedImages =
                Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,Bitmap bitmap) {
            if (bitmap != null) {
                ImageView imageView = (ImageView) view;
                boolean isFirstDisplay = !displayedImages.contains(imageUri);
                if (isFirstDisplay) {
                    //图片的淡入效果
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                    System.out.println("===> loading "+imageUri);
                }
            }
        }
    }
}
