package com.cbt.main.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cbt.main.app.GlobalApplication;

/**
 */

public class ImageLoaderUtils {
    //加载图片
    public static void loadImage(Integer source, ImageView iv) {
        Glide.with(GlobalApplication.mApp).load(source).into(iv);
    }

    //加载图片
    public static void loadImage(String url, ImageView iv) {
        loadImage(GlobalApplication.mApp, url, iv);
    }

    //加载图片
    public static void loadImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).centerCrop().into(iv);
    }

    //加载图片
    public static void loadImage(String url, ImageView iv, int placeholder) {
        loadImage(GlobalApplication.mApp, url, iv, placeholder);
    }

    //加载图片
    public static void loadImage(Context context, String url, ImageView iv, int placeholderResID) {
        Glide.with(context).load(url).centerCrop().placeholder(placeholderResID).into(iv);
    }

    //加载图片
    public static void loadImage(String url, ImageView iv, int placeholder, int error) {
        Glide.with(GlobalApplication.mApp).load(url).centerCrop().placeholder(placeholder).error(error).into(iv);
    }

    //加载图片
    public static void loadDontAnimateImage(String url, ImageView iv) {
        Glide.with(GlobalApplication.mApp).load(url)
                .asBitmap()
                .dontAnimate()
                .into(iv);
    }

    //加载图片
    public static void loadDontAnimateWithThumbnailImage(String url, ImageView iv, float sizeMultiplier) {
        Glide.with(GlobalApplication.mApp).load(url)
                .thumbnail(sizeMultiplier)
                .dontAnimate()
                .into(iv);
    }

    //加载图片
    public static void loadDontAnimateWithThumbnailImage(Integer resource, ImageView iv, float sizeMultiplier) {
        Glide.with(GlobalApplication.mApp).load(resource)
                .thumbnail(sizeMultiplier)
                .dontAnimate()
                .into(iv);
    }

    //加载图片
    public static void loadDontAnimateImage(String url, ImageView iv, int placeholder, int error) {
        Glide.with(GlobalApplication.mApp).load(url)
                .asBitmap()
                .placeholder(placeholder)
                .error(error)
                .dontAnimate()
                .into(iv);
    }

    //加载圆形图片
    public static void loadCircleImage(String url, ImageView iv, int placeholder, int error) {
        Glide.with(GlobalApplication.mApp).load(url).placeholder(placeholder).error(error)
                .bitmapTransform(new GlideCircleTransform(GlobalApplication.mApp)).into(iv);
    }

    //加载圆形图片
    public static void loadCircleImage(String url, ImageView iv, int placeholder) {
        Glide.with(GlobalApplication.mApp).load(url).placeholder(placeholder)
                .bitmapTransform(new GlideCircleTransform(GlobalApplication.mApp)).into(iv);
    }

    //加载圆形图片
    public static void loadCircleImage(String url, ImageView iv) {
        Glide.with(GlobalApplication.mApp).load(url)
                .bitmapTransform(new GlideCircleTransform(GlobalApplication.mApp)).into(iv);
    }

    //加载圆角图片
    public static void loadRoundImage(String url, ImageView iv, int placeholder, int error) {
        Glide.with(GlobalApplication.mApp).load(url).placeholder(placeholder).error(error)
                .bitmapTransform(new GlideRoundTransform(GlobalApplication.mApp)).into(iv);
    }

    //加载圆角图片
    public static void loadRoundImage(String url, ImageView iv, int placeholder) {
        Glide.with(GlobalApplication.mApp).load(url).placeholder(placeholder)
                .bitmapTransform(new GlideRoundTransform(GlobalApplication.mApp)).into(iv);
    }

    //加载圆角图片
    public static void loadRoundImage(String url, ImageView iv) {
        Glide.with(GlobalApplication.mApp).load(url)
                .bitmapTransform(new GlideRoundTransform(GlobalApplication.mApp)).into(iv);
    }

    //加载图片
    public static void loadImageAsBitmap(String url, final ImageView iv, int placeholder, int error, int width, int height) {
        Glide.with(GlobalApplication.mApp).load(url)
                .asBitmap()
                .placeholder(placeholder)
                .error(error)
                .into(new SimpleTarget<Bitmap>(width, height) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        // setImageBitmap(bitmap) on CircleImageView
                        iv.setImageBitmap(bitmap);
                    }
                });
    }

    //加载图片
    public static void loadImageAsBitmap(String url, final ImageView iv, int width, int height) {
        Glide.with(GlobalApplication.mApp).load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(width, height) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        // setImageBitmap(bitmap) on CircleImageView
                        iv.setImageBitmap(bitmap);
                    }
                });
    }

    //加载图片
    public static void loadImageAsBitmap(String url, final IAsBitmap iAsBitmap, int width, int height) {
        Glide.with(GlobalApplication.mApp).load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(width, height) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        // setImageBitmap(bitmap) on CircleImageView
                        if (iAsBitmap != null) {
                            iAsBitmap.onResourceReady(bitmap);
                        }
                    }
                });
    }

    //加载图片
    public static void loadImageAsBitmap(Activity activity, String url, final ImageView iv, int width, int height) {
        Glide.with(activity).load(url)
                .asBitmap()
                .centerCrop()
                .into(new SimpleTarget<Bitmap>(width, height) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        // setImageBitmap(bitmap) on CircleImageView
                        iv.setImageBitmap(bitmap);
                    }
                });
    }

    //加载图片
    public static void loadImageAsBitmap(String url, final IAsBitmap iAsBitmap) {
        Glide.with(GlobalApplication.mApp).load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        if (iAsBitmap != null) {
                            iAsBitmap.onResourceReady(bitmap);
                        }
                    }
                });
    }

    //加载图片
    public static void loadImageAsBitmap(String url, int width, int height, final IAsBitmap iAsBitmap) {
        Glide.with(GlobalApplication.mApp).load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(width, height) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        if (iAsBitmap != null) {
                            iAsBitmap.onResourceReady(bitmap);
                        }
                    }
                });
    }

    //加载图片
    public static void loadOverrideWithCenterCropImage(String url, final ImageView iv, int width, int height) {
        Glide.with(GlobalApplication.mApp)
                .load(url)
                .override(width, height)
                .centerCrop()
                .into(iv);
    }

    //加载图片
    public static void loadOverrideWithCenterCropImage(String url, final ImageView iv, int placeholder, int error, int width, int height) {
        Glide.with(GlobalApplication.mApp)
                .load(url)
                .placeholder(placeholder)
                .error(error)
                .override(width, height)
                .centerCrop()
                .into(iv);
    }

    //加载图片
    public static void loadOverrideImage(String url, final ImageView iv, int width, int height) {
        Glide.with(GlobalApplication.mApp)
                .load(url)
                .override(width, height)
                .into(iv);
    }

    //加载图片
    public static void loadOverrideImage(String url, final ImageView iv, int placeholder, int error, int width, int height) {
        Glide.with(GlobalApplication.mApp)
                .load(url)
                .error(error)
                .placeholder(placeholder)
                .override(width, height)
                .into(iv);
    }

    //加载gif图
    public static void loadGifImage(String url, ImageView iv) {
        Glide.with(GlobalApplication.mApp).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
    }

    public static void pauseRequests() {
        Glide.with(GlobalApplication.mApp).pauseRequests();
    }

    public static void resumeRequests() {
        Glide.with(GlobalApplication.mApp).resumeRequests();
    }

    public interface IAsBitmap {
        void onResourceReady(Bitmap resource);
    }

}
