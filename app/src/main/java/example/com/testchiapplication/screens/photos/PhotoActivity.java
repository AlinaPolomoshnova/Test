package example.com.testchiapplication.screens.photos;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.transition.Fade;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import example.com.testchiapplication.R;

public class PhotoActivity extends DaggerAppCompatActivity implements PhotoFragment.OnFragmentInteractionListener {

    @Inject
    PhotoFragment mPhotoFragment;
    @Inject
    OneLargePhotoFragment mOneLargePhotoFragment;

    public static Intent newIntent(Context context) {
        return new Intent(context, PhotoActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        changeFragmentToAllPhotos();
    }

    private void changeFragmentToAllPhotos() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, mPhotoFragment);
        ft.commit();
    }

    private void changeFragmentToOneLargePhoto(String photoStr, ImageView sharedImageView) {
        // set shared element and transition
        mOneLargePhotoFragment.setPhotoPath(photoStr);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mOneLargePhotoFragment.setSharedElementEnterTransition(new DetailsTransition());
            mOneLargePhotoFragment.setEnterTransition(new Fade());
            mPhotoFragment.setExitTransition(new Fade());
            mOneLargePhotoFragment.setSharedElementReturnTransition(new DetailsTransition());
        }
        ft.replace(R.id.container, mOneLargePhotoFragment);
        ft.addSharedElement(sharedImageView, getString(R.string.photo_transition));
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onFragmentImageInteraction(String photoStr, ImageView sharedImageView) {
        changeFragmentToOneLargePhoto(photoStr, sharedImageView);
    }
}
