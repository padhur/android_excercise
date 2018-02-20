package app.android.com.androidexcercise.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.RelativeLayout;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import app.android.com.androidexcercise.R;
import app.android.com.androidexcercise.app.AEApplication;
import app.android.com.androidexcercise.app.dagger.AppComponent;


public abstract class BaseMVPActivity<V extends BaseMVPView, P extends BaseMVPPresenter<V>> extends MvpActivity<V,P> implements BaseMVPView{

    private ProgressDialog progressDialog;

    /**
     * Method to show the progress
     */
    @Override
    public void showProgressDialog() {
        try {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
                progressDialog.setCancelable(false);
            }
            progressDialog.show();
        }
        catch (Exception e){
            Log.e("BaseMVPActivity",e.getLocalizedMessage());
        }
    }

    /**
     * Method to hide the progress
     */
    @Override
    public void hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (IllegalArgumentException e) {
            Log.e("BaseMVPActivity",e.getLocalizedMessage());
        }
        progressDialog = null;
    }

    /**
     * Method to Show a dialog
     * @param title
     * @param message
     */
    @Override
    public void showDialog(String title, String message) {
        showDialog(title, message, null);
    }

    /**
     * Shows dialog with action
     * @param title
     * @param message
     * @param onClickListener
     */
    @Override
    public void showDialog(String title, String message, DialogInterface.OnClickListener onClickListener) {
        if (onClickListener == null) {
            onClickListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
        }
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                onClickListener);
        alertDialog.show();
    }

    @Override
    public void showErrorDialog() {
        showDialog("Error", "Error retrieving data");
    }

    /**
     * Method to get the app component
     * @return
     */
    public final AppComponent getAppComponent() {
        return ((AEApplication) getApplication()).getAppComponent();
    }

}
