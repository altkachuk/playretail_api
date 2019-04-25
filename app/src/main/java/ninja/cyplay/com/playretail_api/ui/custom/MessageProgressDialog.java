package ninja.cyplay.com.playretail_api.ui.custom;

import android.app.ProgressDialog;
import android.content.Context;

import ninja.cyplay.com.playretail_api.R;

/**
 * Created by damien on 07/07/15.
 */
public class MessageProgressDialog extends ProgressDialog {

    private Context context;

    public MessageProgressDialog(Context context) {
        super(context);
        init(context);
    }

    public MessageProgressDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setTitle(context.getString(R.string.loading));
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void show() {
        setMessage(context.getString(R.string.wait_serv_resp));
        super.show();
    }

    public void showWithMessage(String message) {
        setMessage(message);
        super.show();
    }

}
