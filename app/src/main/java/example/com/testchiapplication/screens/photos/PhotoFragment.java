package example.com.testchiapplication.screens.photos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import example.com.testchiapplication.R;

public class PhotoFragment extends DaggerFragment implements PhotoContract.View, PhotoAdapter.OnItemClickListener{

    @BindView(R.id.photo_recycler_view)
    RecyclerView mPhotoRecyclerView;

    private PhotoAdapter mPhotoAdapter;
    private List<String> mPhotos = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    @Inject
    PhotoContract.Presenter mPresenter;

    @Inject
    public PhotoFragment() {
        // Required empty public constructor
    }

    public static PhotoFragment newInstance() {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        ButterKnife.bind(this, view);
        initViews();
        mPresenter.takeView(this);
        mPresenter.getAllShownImagesPath();
        return view;
    }

    private void initViews() {
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mPhotoRecyclerView.setLayoutManager(mLayoutManager);
        mPhotoAdapter = new PhotoAdapter(getContext(), mPhotos,this);
        mPhotoRecyclerView.setAdapter(mPhotoAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(String item, ImageView sharedImageView, String transitionName) {
        mListener.onFragmentImageInteraction(item, sharedImageView);
    }


    @Override
    public void setImages(List<String> imagesPaths) {
        if (imagesPaths != null) {
            mPhotos.clear();
            mPhotos.addAll(imagesPaths);
            mPhotoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentImageInteraction(String photoStr, ImageView sharedImageView);
    }
}
