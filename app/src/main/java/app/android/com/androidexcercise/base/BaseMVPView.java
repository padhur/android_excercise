package app.android.com.androidexcercise.base;

import android.content.DialogInterface;
import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface BaseMVPView extends MvpView {

    void showProgressDialog();

    void hideProgressDialog();

    void showDialog(String title, String message);

    void showDialog(String title, String message, DialogInterface.OnClickListener onClickListener);

    void showErrorDialog();


}
