package com.yiku.kdb_flat.double_screen;

import android.content.Context;
import android.os.Bundle;
import android.view.Display;

import com.yiku.kdb_flat.BWApplication;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.di.module.HttpManager;
import com.yiku.kdb_flat.eventbus.BusEvent;
import com.yiku.kdb_flat.eventbus.EventBusId;
import com.yiku.kdb_flat.model.http.CommonSubscriber;
import com.yiku.kdb_flat.model.http.response.KDBResponse;
import com.yiku.kdb_flat.utils.ListUtils;
import com.yiku.kdb_flat.utils.RxUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.transformer.ZoomInTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jame on 2018/12/7.
 */

public class DifferentDislay extends BasePresentation {
    private static String TAG = DifferentDislay.class.getSimpleName();
    private Banner banner;
    private List<String> images = new ArrayList();

    public DifferentDislay(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_screen);
        if (images == null)
            images = new ArrayList();
//        images.add("http://common-img-server.b0.upaiyun.com/system/ads/ca20e0474e871f1443a37d26713f93bc.jpg");
//        images.add("http://common-img-server.b0.upaiyun.com/system/ads/c72c13862ffbbe079dcb7f1cfe904bd5.jpg");
//        images.add("http://common-img-server.b0.upaiyun.com/system/ads/cb159c39cbdfc762bb9923f3d3c8230b.jpg");
        banner = (Banner) findViewById(R.id.banner);
        banner.updateBannerStyle(BannerConfig.NOT_INDICATOR);
        banner.setDelayTime(1000 * 5);
        banner.setBannerAnimation(ZoomInTransformer.class);
        getAd();
    }

    public void onEventMainThread(BusEvent event) {
        if (event != null) {
            if (event.id == EventBusId.SEARCH_刷新广告) {
                getAd();
            }
        }
    }

    private void getAd() {
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).GetADList()
                .compose(RxUtil.<KDBResponse<List<String>>>rxSchedulerHelper())
                .compose(RxUtil.<List<String>>handleResult())
                .subscribeWith(new CommonSubscriber<List<String>>(BWApplication.getInstance()) {
                    @Override
                    public void onNext(List<String> data) {
                        super.onNext(data);
                        images.clear();
                        if (!ListUtils.isEmpty(data))
                            images.addAll(data);
                        banner.releaseBanner();
                        // banner.stopAutoPlay();
                        banner.setImageLoader(new GlideImageLoader());
//        banner.setOnBannerListener(new OnBannerListener() {
//            @Override
//            public void OnBannerClick(int position) {
//                // Log.e("yu","==>"+position+"");
//            }
//        });
                        banner.setImages(images);
                        banner.start();
                    }
                }));
    }
}