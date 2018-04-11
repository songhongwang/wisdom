package com.cbt.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cbt.main.R;
import com.cbt.main.moments.ImageWatcher;
import com.cbt.main.utils.Utils;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vigorous on 17/12/27.
 */

public class ExpertConsultActAdapter extends AppBaseAdapter implements ImageWatcher.OnPictureLongPressListener,MessagePicturesLayout.Callback {
    ImageWatcher vImageWatcher;

    public ExpertConsultActAdapter(Context context, List<String> dataList) {
        super(dataList, context);
        initOther(true);
    }

    @Override
    public View getCView(int position, View convertView, ViewGroup viewGroup) {
        ItemHolder holder;

        if (convertView == null) {
            holder = new ItemHolder();
            convertView = mInflater.inflate(R.layout.item_expert_consult_activity, null);
            holder.lPictures = (MessagePicturesLayout) convertView.findViewById(R.id.l_pictures);
            holder.ivAvatar = (ImageView) convertView.findViewById(R.id.iv_img);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
            holder = (ItemHolder) convertView.getTag();
        }

        String s = "https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg";
        Glide.with(mContext).load(s).into(holder.ivAvatar);

        List datas  = new ArrayList();
        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
        holder.lPictures.set(datas, datas);
        holder.lPictures.setCallback(this);

        holder.tvName.setText(position + "");
        holder.tvContent.setText("World");

        return  convertView;
    }

    @Override
    public void onPictureLongPress(ImageView v, String url, int pos) {

    }

    @Override
    public void onThumbPictureClick(ImageView i, List<ImageView> imageGroupList, List<String> urlList) {
        vImageWatcher.show(i, imageGroupList, urlList);
    }


    static class ItemHolder{
        MessagePicturesLayout lPictures;
        ImageView ivAvatar;
        TextView tvName;
        TextView tvContent;
    }

    private void initOther(boolean isTranslucentStatus) {
        vImageWatcher = ImageWatcher.Helper.with((Activity) mContext) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setTranslucentStatus(!isTranslucentStatus ? Utils.calcStatusBarHeight(mContext) : 0)
                .setErrorImageRes(R.mipmap.error_picture) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setOnPictureLongPressListener(this) // 长按图片的回调，你可以显示一个框继续提供一些复制，发送等功能
                .setLoader(new ImageWatcher.Loader() {
                    @Override
                    public void load(Context context, String url, final ImageWatcher.LoadCallback lc) {
                        Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                lc.onResourceReady(resource);
                            }

                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                lc.onLoadStarted(placeholder);
                            }

                            @Override
                            public void onLoadFailed(Drawable errorDrawable) {
                                lc.onLoadFailed(errorDrawable);
                            }
                        });

                    }
                })
                .create();
    }

}
