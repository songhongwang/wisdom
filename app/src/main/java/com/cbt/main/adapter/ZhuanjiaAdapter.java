package com.cbt.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.activity.ExpertConsultActivity;
import com.cbt.main.activity.MarketDetailActivity;
import com.cbt.main.activity.UserActivity;
import com.cbt.main.dialog.ReplyDialog;
import com.cbt.main.model.Data;
import com.cbt.main.model.ReplyModel;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.net.Constants;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.rong.imkit.RongIM;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class ZhuanjiaAdapter extends RecyclerView.Adapter {
    private final List<Data> mDataList = new ArrayList<>();
    private final CropCircleTransformation mCropCircleTransformation;
    private MessagePicturesLayout.Callback mCallback;
    private Context mContext;
    ReplyDialog replyDialog ;

    public ZhuanjiaAdapter(Context context) {
         mCropCircleTransformation = new CropCircleTransformation();
         mContext = context;
        replyDialog = new ReplyDialog(mContext, R.style.TransparentDialog);
    }

    public ZhuanjiaAdapter setPictureClickCallback(MessagePicturesLayout.Callback callback) {
        mCallback = callback;
        return this;
    }
    public void setOnReplySuccessListener(ReplyDialog.OnReplySuccessListener onReplySuccessListener){
        if(replyDialog != null){
            replyDialog.setOnReplySuccessListener(onReplySuccessListener);
        }
    }
    public void set(List<Data> dataList) {
        mDataList.clear();
        if (dataList != null) {
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iAvatar;
        TextView tNickname, tTime, tContent, tvType, tvReplyCount;
        MessagePicturesLayout lPictures;

        Data mData;

        ViewHolder(View itemView) {
            super(itemView);

            iAvatar = (ImageView) itemView.findViewById(R.id.i_avatarzi);
            tNickname = (TextView) itemView.findViewById(R.id.t_nicknamezi);
            tTime = (TextView) itemView.findViewById(R.id.tv_timezi);
            tContent = (TextView) itemView.findViewById(R.id.t_contentzi);
            lPictures = (MessagePicturesLayout) itemView.findViewById(R.id.l_pictureszi);
            tvType = (TextView) itemView.findViewById(R.id.t_shenfenzi);
            tvReplyCount = (TextView) itemView.findViewById(R.id.tv_reply_countzi);
            lPictures.setCallback(mCallback);
        }

        void refresh(final int pos) {
            mData = mDataList.get(pos);
            if(!TextUtils.isEmpty(mData.getAvatar())){
                Picasso.with(itemView.getContext()).load(Constants.getBaseUrl() + mData.getAvatar()).placeholder(R.drawable.login_default_icon)
                        .transform(mCropCircleTransformation)
                        .into(iAvatar);
            }else{
                iAvatar.setImageResource(R.drawable.login_default_icon);
            }

            tNickname.setText(mData.getNickname());
            tTime.setText(mData.getCreateTime());
            tContent.setText(mData.getContent());
            lPictures.set(mData.getPictureThumbList(), mData.getPictureList());
            iAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, UserActivity.class);
                    intent.putExtra("otheruserid", mData.getUid());
                    mContext.startActivity(intent);
                }
            });
            tvReplyCount.setText(" | "+mData.getRplaycount()+"条");
            tvType.setText(mData.getShenfen());

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zixuncontent, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mDataList.size() != 0) {
            final Data mdatatemp = mDataList.get(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ExpertConsultActivity.class);
                    intent.putExtra("iid", mdatatemp.getIid());
                    mContext.startActivity(intent);
                }
            });

            ((ViewHolder) holder).refresh(position % mDataList.size());
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

}
