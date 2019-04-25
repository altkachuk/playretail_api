package ninja.cyplay.com.playretail_api.ui.presenter;

import com.edmodo.cropper.CropImageView;

import java.io.File;

import ninja.cyplay.com.playretail_api.ui.view.CropPictureView;

/**
 * Created by damien on 13/05/15.
 */
public interface CropPicturePresenter extends Presenter<CropPictureView> {

    public void cropPicture(String seller_name, CropImageView cropImageView);

    public void onPictureCroped(File file);

    public void uploadPicture(byte[] picture);

}