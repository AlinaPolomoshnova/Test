package example.com.testchiapplication.screens.photos;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import example.com.testchiapplication.R;

public class OneLargePhotoFragment extends DaggerFragment {

    private static final String PHOTO_PATH = "photo-path";
    private String mPhotoPath;

    @BindView(R.id.image)
    ImageView image;

    @Inject
    public OneLargePhotoFragment() {
        // Required empty public constructor
    }

    public void setPhotoPath(String photoPath) {
        mPhotoPath = photoPath;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPhotoPath = getArguments().getString(PHOTO_PATH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one_large_photo, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide
                .with(getContext())
                .load(mPhotoPath)
                .into(image);
    }
}
