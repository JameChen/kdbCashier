package com.yiku.kdb_flat.double_screen;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yiku.kdb_flat.R;
import com.youth.banner.loader.ImageLoader;


public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择    RequestOptions options = new RequestOptions()
        RequestOptions options = new RequestOptions()
                //.centerCrop()
                .placeholder(R.drawable.first_load_bg)
                .error(R.drawable.first_load_bg)
                .fallback(R.drawable.first_load_bg);

        Glide.with(context.getApplicationContext())
                .load(path)
                .apply(options)
                .into(imageView);
    }

//    @Override
//    public ImageView createImageView(Context context) {
//        //圆角
//        return new RoundAngleImageView(context);
//    }
}
