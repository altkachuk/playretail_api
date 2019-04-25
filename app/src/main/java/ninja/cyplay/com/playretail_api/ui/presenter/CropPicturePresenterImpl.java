package ninja.cyplay.com.playretail_api.ui.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.edmodo.cropper.CropImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;

import ninja.cyplay.com.apilibrary.domain.interactor.SellerInteractor;
import ninja.cyplay.com.playretail_api.model.singleton.SellerContext;
import ninja.cyplay.com.apilibrary.utils.LocalImagesUtils;
import ninja.cyplay.com.playretail_api.app.BasePresenter;
import ninja.cyplay.com.playretail_api.ui.view.CropPictureView;

/**
 * Created by damien on 13/05/15.
 */
public class CropPicturePresenterImpl extends BasePresenter implements CropPicturePresenter {

    @Inject
    SellerContext sellerContext;

    private Context context;
    private CropPictureView     cropPictureView;

    @Inject
    public SellerInteractor sellerInteractor;

    File cropped_file;
    public CropPicturePresenterImpl(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public void cropPicture(String seller_name, CropImageView cropImageView) {
        cropPictureView.showLoading();
///file:/storage/emulated/0/Pictures/JPEG_NADINE.CHARTRE_-1128993763.jpg

        File file = null;
        OutputStream out = null;

        try {
            this.cropped_file = LocalImagesUtils.createCroppedImageFile(seller_name);
            out = new BufferedOutputStream(new FileOutputStream(cropped_file));
            cropImageView.getCroppedImage().compress(Bitmap.CompressFormat.PNG, 85, out);
            out.flush();
            out.close();

            // get the file data
            byte[] data = new byte[0];
            data = LocalImagesUtils.readFileToByteArray(cropped_file);


            uploadPicture(data);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            cropPictureView.onError();
        } catch (IOException e) {
            e.printStackTrace();
            cropPictureView.onError();
        }
        finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //onPictureCroped(file);
        }
/*
        try {
            FileOutputStream fOut = new FileOutputStream( photoFile);
            cropImageView.getCroppedImage().compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();

            // get the file data
            byte[] data = new byte[0];
            data = LocalImagesUtils.readFileToByteArray(photoFile);


            uploadPicture(data);

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            cropPictureView.onError();}
        catch (IOException e) {
            e.printStackTrace();
            cropPictureView.onError();
        }
        finally {
            onPictureCroped();
        }
*/
    }

    @Override
    public void uploadPicture(byte[] picture) {
            //call the interactor
            sellerInteractor.updateSellerPicture(sellerContext.getSeller().getUn(), picture,
                    new SellerInteractor.UpdateSellerPictureCallback() {
                        @Override
                        public void onSuccess(String version, File image) {
                            onPictureCroped(cropped_file);
                        }

                        @Override
                        public void onError() {
                            cropPictureView.onError();
                        }
                    });
    }

    @Override
    public void onPictureCroped(File file) {
        cropPictureView.hideLoading();
        cropPictureView.onPictureCroped(file);
    }



    @Override
    public void initialize() {

    }

    @Override
    public void setView(CropPictureView view) {
        this.cropPictureView = view;
    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewResume() {

    }

    @Override
    public void onViewDestroy() {

    }
}
