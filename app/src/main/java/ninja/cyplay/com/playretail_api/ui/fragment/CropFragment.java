package ninja.cyplay.com.playretail_api.ui.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.edmodo.cropper.CropImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

import javax.inject.Inject;

import butterknife.InjectView;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.apilibrary.utils.ScreenUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.ui.presenter.CropPicturePresenter;
import ninja.cyplay.com.playretail_api.ui.view.CropPictureView;

/**
 * Created by damien on 13/05/15.
 */
public class CropFragment extends BaseFragment implements CropPictureView {

    @Inject
    CropPicturePresenter cropPicturePresenter;

    @InjectView(R.id.background_loading)
    View backgroundLoading;

    @InjectView(R.id.progress_bar)
    ProgressBar wait;

    @InjectView(R.id.crop_image_view)
    CropImageView cropImageView;

    private String photoPath;
    private File photoFile;
    private String seller_name;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crop, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        cropPicturePresenter.initialize();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cropPicturePresenter.setView(this);
        init();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void init() {
        if (getActivity().getIntent() != null && getActivity().getIntent().hasExtra(Constants.EXTRA_PHOTO_PATH))
            photoPath = getActivity().getIntent().getStringExtra(Constants.EXTRA_PHOTO_PATH);
        if (getActivity().getIntent() != null && getActivity().getIntent().hasExtra(Constants.EXTRA_SELLE_NAME))
            seller_name = getActivity().getIntent().getStringExtra(Constants.EXTRA_SELLE_NAME);

        photoFile = new File(photoPath);

        backgroundLoading.setVisibility(View.GONE);
        wait.setVisibility(View.GONE);

        Picasso.with(getActivity())
                .load(photoPath)
                .centerCrop()
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        cropImageView.setImageBitmap(bitmap);
                        cropImageView.getViewTreeObserver().addOnGlobalLayoutListener(
                                new ViewTreeObserver.OnGlobalLayoutListener() {

                                    @Override
                                    public void onGlobalLayout() {
                                        if (Build.VERSION.SDK_INT < 16)
                                            cropImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                        else
                                            cropImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                        cropImageView.setAspectRatio(1, 1);
                                        cropImageView.setFixedAspectRatio(true);
                                    }
                                });
                    }
                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    };

    private void tryToQuit() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        getActivity().finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        new AlertDialogWrapper.Builder(getActivity())
                .setMessage(getString(R.string.picture_not_save))
                .setPositiveButton(getString(R.string.ok), dialogClickListener)
                .setNegativeButton(getString(R.string.cancel), dialogClickListener).show();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Override(s)
    // -------------------------------------------------------------------------------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_valid) {
            cropPicturePresenter.cropPicture(seller_name, cropImageView);
        }
        else if (item.getItemId() == android.R.id.home) {
            tryToQuit();
        }
        return super.onOptionsItemSelected(item);
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void showLoading() {
        backgroundLoading.setVisibility(View.VISIBLE);
        wait.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        backgroundLoading.setVisibility(View.GONE);
        wait.setVisibility(View.GONE);
    }

    @Override
    public void onPictureCroped(File path) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.EXTRA_CROPPED_PHOTO_PATH, path.getAbsoluteFile().toString());
        getActivity().setResult(Activity.RESULT_OK, returnIntent);
        getActivity().finish();
    }

    @Override
    public void onPictureUploaded() {

    }
    
    @Override
    public void displayPopUp(String message) {

    }
}
