package ninja.cyplay.com.playretail_api.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.model.singleton.CustomerContext;
import ninja.cyplay.com.playretail_api.model.singleton.SellerContext;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.apilibrary.utils.LocalImagesUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseActivity;
import ninja.cyplay.com.playretail_api.model.business.EFeature;
import ninja.cyplay.com.playretail_api.ui.custom.MessageProgressDialog;
import ninja.cyplay.com.playretail_api.ui.fragment.HomeFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.StatisticsFragment;
import ninja.cyplay.com.playretail_api.ui.presenter.AuthenticationPresenter;
import ninja.cyplay.com.playretail_api.ui.view.AuthenticationView;
import ninja.cyplay.com.playretail_api.utils.ConfigHelper;

/**
 * Created by damien on 01/07/15.
 */
public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, AuthenticationView {

    @Inject
    SellerContext sellerContext;

    @Inject
    CustomerContext customerContext;

    @Inject
    AuthenticationPresenter authenticationPresenter;

    @Optional
    @InjectView(R.id.navigation)
    NavigationView navigationView;

    @Optional
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Optional
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Optional
    @InjectView(R.id.menu_seller_name)
    TextView sellerName;

    @Optional
    @InjectView(R.id.menu_header_background)
    View menuHeaderBackground;

    // Picture Stuff
    private String photoPath;
    private File photoFile = null;

    public static final int RESULT_IMAGE_CAPTURE = 1;
    public static final int RESULT_IMAGE_CROPPED = 2;

    // Fragment(s)

    private int selectedOption;

    private MessageProgressDialog progress;

    private final static String NAVIGATE_OPTION_ID = "NAVIGATE_OPTION_ID";

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_home, null);
        setContentView(view);
        injectViews();

        // show back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        authenticationPresenter.setView(this);
        progress = new MessageProgressDialog(HomeActivity.this);

        updateDesign();
        initToolbar();
        initDrawer();

        // load saved navigation state if present
        setFragmentFromMenuOption((null == savedInstanceState) ?
                R.id.action_home : savedInstanceState.getInt(NAVIGATE_OPTION_ID));
    }

    @Override
    protected void onResume() {
        super.onResume();
        customerContext.clearCustomerContext();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        if (homeDesignFragment != null && getSupportFragmentManager().findFragmentById(R.id.container) == homeDesignFragment)
//            getSupportFragmentManager().putFragment(outState, HOME_DESIGN_FRAGMENT, homeDesignFragment);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // call android response camera
            Intent intent = new Intent(HomeActivity.this, CropActivity.class);
            intent.putExtra(Constants.EXTRA_PHOTO_PATH, photoPath);
            intent.putExtra(Constants.EXTRA_SELLE_NAME, sellerContext.getSeller().getUn());
            startActivityForResult(intent, RESULT_IMAGE_CROPPED);
        } else if (requestCode == RESULT_IMAGE_CROPPED && resultCode == Activity.RESULT_OK) {
            if (data != null && data.hasExtra(Constants.EXTRA_CROPPED_PHOTO_PATH)) {
                photoPath = data.getStringExtra(Constants.EXTRA_CROPPED_PHOTO_PATH);
                //TODO : REDO
//                sellerInitialsView.setSellerImage(photoPath);
            }
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void updateDesign() {
        ColorUtils.setBackgroundColor(menuHeaderBackground, ColorUtils.FeatureColor.PRIMARY_LIGHT);
    }

    private void initToolbar() {
        int primary_dark_color = Color.parseColor(ColorUtils.FeatureColor.getColorHex(ColorUtils.FeatureColor.PRIMARY_DARK));
        toolbar.setBackgroundColor(primary_dark_color);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null && sellerContext.getSeller() != null)
            getSupportActionBar().setTitle(sellerContext.getSeller().getFormatName());
    }

    private void setFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    private void setFragmentFromMenuOption(int menuOption) {
        switch (menuOption) {
            case R.id.action_home:
                if (selectedOption != R.id.action_home) {
                    selectedOption = menuOption;
                    setFragment(new HomeFragment());
                }
                break;
            case R.id.action_stats:
                if (selectedOption != R.id.action_stats) {
                    selectedOption = menuOption;
                    setFragment(new StatisticsFragment());
                }
                break;
        }
    }

    private void modPicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                if (sellerContext != null && sellerContext.getSeller() != null) {
                    photoFile = LocalImagesUtils.createImageFile(sellerContext.getSeller().getUn());
                    photoPath = "file:" + photoFile.getAbsolutePath();
                }
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, RESULT_IMAGE_CAPTURE);
            }
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Drawer
    // -------------------------------------------------------------------------------------------

    private void initDrawer() {

        if (sellerName != null)
            sellerName.setText(sellerContext.getSeller().getFormatName());

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(this);


        // Initializing Drawer Layout and ActionBarToggle
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Override(s)
    // -------------------------------------------------------------------------------------------

    private Boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            if (doubleBackToExitPressedOnce) {
                authenticationPresenter.invalidateToken();
//                super.onBackPressed();
                return;
            }
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.back_logout), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        //Closing drawer on item click
        drawerLayout.closeDrawers();

        Intent intent = null;
        //Check to see which item was being clicked and perform appropriate action
        switch (menuItem.getItemId()) {

            case R.id.action_mod_picture:
                modPicture();
                return true;
            case R.id.action_contact_manager:
                intent = new Intent(HomeActivity.this, ContactManagerActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_logout:
                authenticationPresenter.invalidateToken();
                return true;
            default:
                setFragmentFromMenuOption(menuItem.getItemId());
                return true;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        if (ConfigHelper.getInstance().isFeatureActivated(EFeature.BASKET))
            menu.findItem(R.id.menu_cart).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.menu_cart:
                Intent intent = new Intent(HomeActivity.this, BasketActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_no_anim);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void showLoading() {
        if (progress != null)
            progress.show();
    }

    @Override
    public void hideLoading() {
        if (progress != null)
            progress.dismiss();
    }

    @Override
    public void onAuthenticationSuccess() {

    }

    @Override
    public void onGetOauth2DetailsSuccess() {

    }

    @Override
    public void onAccessTokenSuccess() {

    }

    @Override
    public void onInvalidateTokenSuccess() {
        finish();
    }

    @Override
    public void onGetSellerSuccess() {

    }
}
