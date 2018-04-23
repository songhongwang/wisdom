package com.cbt.main.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.cbt.main.R;
import com.cbt.main.utils.net.ApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vigorous on 17/12/28.
 *
 */

public class ReplyDialog extends Dialog {

    @BindView(R.id.rc_send_toggle)
    FrameLayout mReplyBtn;
    @BindView(R.id.rc_edit_text)
    EditText mEtContent;
    private Context mContext;
    private int huifutype;
    private String mIid;
    private String mReplyId;
    private String mReplyName;
    private OnReplySuccessListener mOnReplySuccessListener;

    public ReplyDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        init();
    }

    public ReplyDialog(@NonNull Context context, int theme) {
        super(context, theme);
        mContext = context;
        init();
    }

    public void setOnReplySuccessListener(OnReplySuccessListener onReplySuccessListener) {
        mOnReplySuccessListener = onReplySuccessListener;
    }

    public void setIid(String iid){
        mIid = iid;
    }
    public void setReplyId(String rid){
        mReplyId = rid;
    }
    public void setReplyName(String rname){
        mReplyName = rname;
    }
    public void setHuifutype(int typeint){
        huifutype = typeint;
    }

    private void init(){
        getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;

        setContentView(R.layout.layout_reply);
        ButterKnife.bind(this);

        mReplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = mEtContent.getText().toString();

                mEtContent.setText("");
				replay(content);
                dismiss();
            }
        });
    }


    @Override
    public void show() {
        Window win = getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

        mEtContent.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInputFromWindow(mEtContent.getWindowToken(), 0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 100);

        if(!TextUtils.isEmpty(mReplyName)){
            mEtContent.setHint("回复：" + mReplyName);
        }

        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mIid = "";
        mReplyId = "";
        mReplyName = "";
        huifutype = 0;
    }

    private void replay(String content){
        if (huifutype == 0)
        {
            ApiClient.getInstance().getBasicService(mContext).replyZaiqing(mIid, mReplyId, content).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if(mOnReplySuccessListener!=null){
                        mOnReplySuccessListener.onSuccess();
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {

                }
            });
        }
        else if (huifutype == 1)
        {
            ApiClient.getInstance().getBasicService(mContext).replyFeed(mIid, mReplyId, content).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if(mOnReplySuccessListener!=null){
                        mOnReplySuccessListener.onSuccess();
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {

                }
            });
        }
        else
        {
            ApiClient.getInstance().getBasicService(mContext).replyShichang(mIid, mReplyId, content).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if(mOnReplySuccessListener!=null){
                        mOnReplySuccessListener.onSuccess();
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {

                }
            });
        }
    }


    public interface OnReplySuccessListener{
        void onSuccess();
    }

}
