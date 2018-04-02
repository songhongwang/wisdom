package com.cbt.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.dialog.ReleaseDialog;
import com.cbt.main.dialog.ReplyDialog;
import com.cbt.main.model.Data;
import com.cbt.main.model.ReplyModel;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.utils.net.Constants;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessageAdapter extends RecyclerView.Adapter {
    private final List<Data> mDataList = new ArrayList<>();
    private final CropCircleTransformation mCropCircleTransformation;
    private MessagePicturesLayout.Callback mCallback;
    private Context mContext;
    ReplyDialog replyDialog ;

    public MessageAdapter(Context context) {
         mCropCircleTransformation = new CropCircleTransformation();
         mContext = context;
        replyDialog = new ReplyDialog(mContext, R.style.TransparentDialog);
    }

    public MessageAdapter setPictureClickCallback(MessagePicturesLayout.Callback callback) {
        mCallback = callback;
        return this;
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
        TextView tNickname, tTime, tContent, tvReply, tvReplyCount;
        LinearLayout llReplyList;
        MessagePicturesLayout lPictures;

        Data mData;

        ViewHolder(View itemView) {
            super(itemView);
            iAvatar = (ImageView) itemView.findViewById(R.id.i_avatar);
            tNickname = (TextView) itemView.findViewById(R.id.t_nickname);
            tTime = (TextView) itemView.findViewById(R.id.t_time);
            tContent = (TextView) itemView.findViewById(R.id.t_content);
            lPictures = (MessagePicturesLayout) itemView.findViewById(R.id.l_pictures);
            tvReply = (TextView) itemView.findViewById(R.id.tv_reply);
            llReplyList = (LinearLayout) itemView.findViewById(R.id.ll_reply_list);
            tvReplyCount = (TextView) itemView.findViewById(R.id.tv_reply_count);
            lPictures.setCallback(mCallback);
        }

        void refresh(final int pos) {
            mData = mDataList.get(pos);
            Picasso.with(itemView.getContext()).load(Constants.getBaseUrl() + mData.getAvatar()).placeholder(R.drawable.default_image_error)
                    .transform(mCropCircleTransformation)
                    .into(iAvatar);

            tNickname.setText(mData.getNickname());
            tTime.setText(mData.getCreateTime());
            tContent.setText(mData.getContent());
            lPictures.set(mData.getPictureThumbList(), mData.getPictureList());
            iAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RongIM.getInstance().startPrivateChat(mContext, "18600211553", "vigorous");
                }
            });
            tvReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    replyDialog.setIid(mData.getIid());
                    replyDialog.show();
                }
            });

            if(mData.getReplyList().size() > 0){
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

                llReplyList.removeAllViews();
                llReplyList.setVisibility(View.INVISIBLE);
            }

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moments, parent, false));
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
