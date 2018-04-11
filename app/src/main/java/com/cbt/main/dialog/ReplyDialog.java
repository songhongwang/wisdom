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
    private String mIid;
    private String mReplyId;
    private String mReplyName;

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

    public void setIid(String iid){
        mIid = iid;
    }
    public void setReplyId(String rid){
        mReplyId = rid;
    }
    public void setReplyName(String rname){
        mReplyName = rname;
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
                dismiss();

                replay(content);
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
    }

    private void replay(String content){
        ApiClient.getInstance().getBasicService(mContext).replyFeed(mIid, mReplyId, content).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}
