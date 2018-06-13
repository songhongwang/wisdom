package com.cbt.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
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
import com.cbt.main.activity.UserActivity;
import com.cbt.main.dialog.ReleaseDialog;
import com.cbt.main.dialog.ReplyDialog;
import com.cbt.main.model.Data;
import com.cbt.main.model.ReplyModel;
import com.cbt.main.utils.ToastUtils;
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
    private int ismydo;
    ReplyDialog replyDialog ;

    public MessageAdapter(Context context,int ismy) {
         mCropCircleTransformation = new CropCircleTransformation();
         mContext = context;
        ismydo = ismy;
        replyDialog = new ReplyDialog(mContext, R.style.TransparentDialog);
        replyDialog.setHuifutype(1);
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

    public void setOnReplySuccessListener(ReplyDialog.OnReplySuccessListener onReplySuccessListener){
        if(replyDialog != null){
            replyDialog.setOnReplySuccessListener(onReplySuccessListener);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iAvatar;
        TextView tNickname, tTime, tContent, tvReply, tvReplyCount,tv_zan, tv_shoucang,t_type,t_des,tv_reply,tv_reply_count;
        View v_content_line;
        LinearLayout llReplyList;
        MessagePicturesLayout lPictures;

        Data mData;

        ViewHolder(View itemView) {
            super(itemView);
            tv_zan = (TextView) itemView.findViewById(R.id.tv_zan);
            tv_shoucang = (TextView) itemView.findViewById(R.id.tv_shoucang);
            iAvatar = (ImageView) itemView.findViewById(R.id.i_avatar);
            tNickname = (TextView) itemView.findViewById(R.id.t_nickname);
            tTime = (TextView) itemView.findViewById(R.id.t_time);
            tContent = (TextView) itemView.findViewById(R.id.t_content);
            lPictures = (MessagePicturesLayout) itemView.findViewById(R.id.l_pictures);
            tvReply = (TextView) itemView.findViewById(R.id.tv_reply);
            llReplyList = (LinearLayout) itemView.findViewById(R.id.ll_reply_list);
            tvReplyCount = (TextView) itemView.findViewById(R.id.tv_reply_count);

            t_type = (TextView) itemView.findViewById(R.id.t_type);
            t_des = (TextView) itemView.findViewById(R.id.t_des);
            v_content_line = (View) itemView.findViewById(R.id.v_content_line);

            lPictures.setCallback(mCallback);
            if (ismydo == 2)
            {
                tv_zan.setVisibility(View.GONE);
                tv_shoucang.setText(" 取消收藏     | ");
            }
            else if (ismydo == 1 || ismydo == 8)
            {
                iAvatar.setVisibility(View.GONE);
                tNickname.setVisibility(View.GONE);
                t_type.setVisibility(View.GONE);
                t_des.setVisibility(View.GONE);
                v_content_line.setVisibility(View.GONE);
                tvReplyCount.setVisibility(View.GONE);
                tvReply.setVisibility(View.GONE);
                tv_zan.setVisibility(View.GONE);
                tv_shoucang.setVisibility(View.GONE);
            }
            else
            {
                iAvatar.setVisibility(View.VISIBLE);
                tNickname.setVisibility(View.VISIBLE);
                t_type.setVisibility(View.VISIBLE);
                t_des.setVisibility(View.VISIBLE);
                v_content_line.setVisibility(View.VISIBLE);
                tvReplyCount.setVisibility(View.VISIBLE);
                tvReply.setVisibility(View.VISIBLE);
                tv_zan.setVisibility(View.VISIBLE);
                tv_shoucang.setVisibility(View.VISIBLE);
            }
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
                    intent.putExtra("otheruserid", mData.getUid());
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

            llReplyList.removeAllViews();
            if (mData.getIszan() != null && mData.getIszan().equals("1"))
            {
                tv_zan.setText(" 已点赞     | ");
                tv_zan.setTextColor(Color.BLACK);
                tv_zan.setClickable(false);
            }
            else
            {
                tv_zan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ApiClient.getInstance().getBasicService(mContext).dianzan(mData.getIid(), 3).enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                ToastUtils.show(mContext, "已点赞");
                                tv_zan.setText(" 已点赞     | ");
                                tv_zan.setTextColor(Color.BLACK);
                                tv_zan.setClickable(false);
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {

                            }
                        });
                    }
                });
            }
            if (mData.getIsccang() != null && mData.getIsccang().equals("1") && ismydo != 2)
            {
                tv_shoucang.setText(" 已收藏     | ");
                tv_shoucang.setTextColor(Color.BLACK);
                tv_shoucang.setClickable(false);
            }
            else
            {
                tv_shoucang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (tv_shoucang.getText().toString().contains("取消"))
                        {
                            ApiClient.getInstance().getBasicService(mContext).delfollowComm(mData.getIid(),"", 1).enqueue(new Callback<Object>() {
                                @Override
                                public void onResponse(Call<Object> call, Response<Object> response) {
                                    ToastUtils.show(mContext, "已取消收藏");
                                    tv_shoucang.setText(" 收藏     | ");
                                }

                                @Override
                                public void onFailure(Call<Object> call, Throwable t) {

                                }
                            });
                        }
                        else
                        {
                            ApiClient.getInstance().getBasicService(mContext).followComm(mData.getIid(),"", 1).enqueue(new Callback<Object>() {
                                @Override
                                public void onResponse(Call<Object> call, Response<Object> response) {
                                    ToastUtils.show(mContext, "已收藏");
                                    tv_shoucang.setText(" 已收藏     | ");
                                    tv_shoucang.setTextColor(Color.BLACK);
                                    tv_shoucang.setClickable(false);
                                }

                                @Override
                                public void onFailure(Call<Object> call, Throwable t) {

                                }
                            });
                        }
                    }
                });
            }


            if(mData.getReplyList() != null && mData.getReplyList().size() > 0){
                llReplyList.setVisibility(View.VISIBLE);
                for(final ReplyModel replyModel :mData.getReplyList()){
                    TextView textView = new TextView(mContext);
                    textView.setPadding(5,5,5,5);
                    String tip = replyModel.getReplayusername();
                    if (replyModel.getCommentname() != null && !replyModel.getCommentname().equals(""))
                    {
                        tip += "回复"+replyModel.getCommentname();
                    }
                    tip += ":" + replyModel.getContent();
                    SpannableString spannableString = new SpannableString(tip);
                    int end = tip.indexOf(":");
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#51a067")), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textView.setText(spannableString);

                    llReplyList.addView(textView);

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
							replyDialog.setHuifutype(1);
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
