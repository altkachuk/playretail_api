package ninja.cyplay.com.playretail_api.ui.view;

import java.io.File;

/**
 * Created by damien on 13/05/15.
 */
public interface CropPictureView extends View {

    void showLoading();

    void hideLoading();

    void onPictureCroped(File file);

    void onPictureUploaded();


}
