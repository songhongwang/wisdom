package com.cbt.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.model.Data;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class MessageAdapter extends RecyclerView.Adapter {
    private final List<Data> mDataList = new ArrayList<>();
    private final CropCircleTransformation mCropCircleTransformation;
    private MessagePicturesLayout.Callback mCallback;
    private Context mContext;

    public MessageAdapter(Context context) {
         mCropCircleTransformation = new CropCircleTransformation();
         mContext = context;
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
        TextView tNickname, tTime, tContent, tvReply;
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
            lPictures.setCallback(mCallback);
        }

        void refresh(int pos) {
            mData = mDataList.get(pos);
            Picasso.with(itemView.getContext()).load(mData.getAvatar()).placeholder(R.drawable.default_avatar)
                    .transform(mCropCircleTransformation)
                    .into(iAvatar);

            tNickname.setText(mData.getNickname());
            tTime.setText(mData.getCreateTime());
            tContent.setText(mData.getContent());
            lPictures.set(mData.getPictureThumbList(), mData.getPictureList());
            tvReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RongIM.getInstance().startPrivateChat(mContext, "18600211553", "vigorous");
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moments, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).refresh(position % mDataList.size());
    }

    @Override
    public int getItemCount() {
        return 99999;
    }
}
