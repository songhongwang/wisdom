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

import io.rong.imkit.RongIM;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class ZaiqingAdapter extends RecyclerView.Adapter {
    private final List<Data> mDataList = new ArrayList<>();
    private final CropCircleTransformation mCropCircleTransformation;
    private MessagePicturesLayout.Callback mCallback;
    private Context mContext;
    private int ismydo;
    ReplyDialog replyDialog ;

    public ZaiqingAdapter(Context context,int ismy) {
         mCropCircleTransformation = new CropCircleTransformation();
         mContext = context;
        ismydo = ismy;
        replyDialog = new ReplyDialog(mContext, R.style.TransparentDialog);
        replyDialog.setHuifutype(0);
    }

    public ZaiqingAdapter setPictureClickCallback(MessagePicturesLayout.Callback callback) {
        mCallback = callback;
        return this;
    }
    public void setOnReplySuccessListener(ReplyDialog.OnReplySuccessListener onReplySuccessListener){
        if(replyDialog != null){
            replyDialog.setOnReplySuccessListener(onReplySuccessListener);
        }
    }
    public void set(List<Data> dataList) {
        if (dataList != null) {
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    public List<Data> getDataList() {
        return mDataList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iAvatar;
        TextView tNickname, tTime, tContent, tvReply, tvReplyCount,tv_zan, tv_shoucang;
        LinearLayout llReplyList;
        MessagePicturesLayout lPictures;

        Data mData;

        ViewHolder(View itemView) {
            super(itemView);
            tv_zan = (TextView) itemView.findViewById(R.id.tv_zan);
            tv_shoucang = (TextView) itemView.findViewById(R.id.tv_shoucang);
            iAvatar = (ImageView) itemView.findViewById(R.id.i_avatarzai);
            tNickname = (TextView) itemView.findViewById(R.id.t_nicknamezai);
            tTime = (TextView) itemView.findViewById(R.id.t_timezai);
            tContent = (TextView) itemView.findViewById(R.id.t_contentzai);
            lPictures = (MessagePicturesLayout) itemView.findViewById(R.id.l_pictureszai);
            tvReply = (TextView) itemView.findViewById(R.id.tv_replyzai);
            llReplyList = (LinearLayout) itemView.findViewById(R.id.ll_reply_listzai);
            tvReplyCount = (TextView) itemView.findViewById(R.id.tv_reply_countzai);
            lPictures.setCallback(mCallback);
        }

        void refresh(final int pos) {
            mData = mDataList.get(pos);
            if(!TextUtils.isEmpty(mData.getAvatar())){
                Picasso.with(itemView.getContext()).load(Constants.getBaseUrl() + mData.getAvatar()).placeholder(R.drawable.default_image_error)
                        .transform(mCropCircleTransformation)
                        .into(iAvatar);
            }else{
                iAvatar.setImageResource(R.drawable.login_default_icon);
            }
            if(TextUtils.isEmpty(mData.getNickname())){
                tNickname.setText("匿名");
            }else{
                tNickname.setText(mData.getNickname());
            }

            tTime.setText(mData.getCreateTime());
            tContent.setText(mData.getContent());
            lPictures.set(mData.getPictureThumbList(), mData.getPictureList());
            iAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, UserActivity.class);
                    intent.putExtra("model", mData);
                    mContext.startActivity(intent);
                }
            });
            tvReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    replyDialog.setIid(mData.getIid());
                    replyDialog.show();
                }
            });
            tv_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.show(mContext, "给你点个赞");
                }
            });
            tv_shoucang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.show(mContext, "收藏一下");
                }
            });

            llReplyList.removeAllViews();

            if(mData.getReplyList() != null && mData.getReplyList().size() > 0){
                llReplyList.setVisibility(View.VISIBLE);
                for(final ReplyModel replyModel :mData.getReplyList()){
                    TextView textView = new TextView(mContext);
                    textView.setPadding(5,5,5,5);

                    String tip = replyModel.getReplayusername() + ":" + replyModel.getContent();
                    SpannableString spannableString = new SpannableString(tip);
                    int end = tip.indexOf(":");
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#51a067")), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textView.setText(spannableString);

                    llReplyList.addView(textView);

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            replyDialog.setHuifutype(0);
                            replyDialog.setIid(mData.getIid());
                            replyDialog.setReplyId(replyModel.getReplayuserid());
                            replyDialog.setReplyName(replyModel.getReplayusername());
                            replyDialog.show();
                        }
                    });
                }

                tvReplyCount.setText(mData.getReplyList().size() + "");
            }else{
                tvReplyCount.setText("0");

                llReplyList.setVisibility(View.INVISIBLE);
            }

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zaicontent, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mDataList.size() != 0)
        ((ViewHolder) holder).refresh(position % mDataList.size());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

}
