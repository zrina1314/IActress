package com.sf.iactress.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sf.iactress.R;
import com.sf.iactress.ui.widget.dialog.ProgressDialog;

public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    private ProgressDialog mProgressDialog;
    protected View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        process();
    }

    /**
     * 业务逻辑处理
     */
    protected abstract void process();

    protected void showProgress() {
        if (getActivity() instanceof IProgress) {
            IProgress progress = (IProgress) getActivity();
            progress.showProgress();
        } else {
            if (mProgressDialog == null)
                mProgressDialog = new ProgressDialog(getActivity());

            if (!mProgressDialog.isShowing())
                mProgressDialog.show();
        }
    }

    protected void dismissProgress() {
        if (getActivity() instanceof IProgress) {
            IProgress progress = (IProgress) getActivity();
            progress.dismissProgress();
        } else {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }
}
