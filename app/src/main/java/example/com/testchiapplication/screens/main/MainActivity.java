package example.com.testchiapplication.screens.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import example.com.testchiapplication.Constants;
import example.com.testchiapplication.R;
import example.com.testchiapplication.model.UserStory;
import example.com.testchiapplication.screens.photos.PhotoActivity;
import example.com.testchiapplication.utils.Utils;

import static example.com.testchiapplication.screens.main.UserStoryType.STORY_TYPE;
import static example.com.testchiapplication.screens.main.UserStoryType.USER_TYPE;

public class MainActivity extends DaggerAppCompatActivity implements MainContract.View {

    private static final int REQUEST_CODE_GET_PHOTO = 22;
    private volatile boolean isLoading;
    private LinearLayoutManager mStoryLayoutManager;
    private LinearLayoutManager mUserLayoutManager;

    @Inject
    MainPresenter mPresenter;

    private List<UserStory> mStories = new ArrayList<>();
    private StoryAdapter mStoryAdapter;
    private StoryAdapter mUserAdapter;
    private volatile static int page;
    private volatile boolean isLastPage = false;
    private volatile boolean isInternetAvailable;

    @BindView(R.id.story_recycler_view)
    RecyclerView mHitRecyclerView;
    @BindView(R.id.user_recycler_view)
    RecyclerView mUserRecyclerView;

    @BindView(R.id.preloader)
    ProgressBar mPreloader;

    private OnScrollListener mHitScrollListener;
    private OnScrollListener mUserScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        initListeners();
    }

    private void initViews() {
        mStoryLayoutManager = new LinearLayoutManager(this);
        mHitRecyclerView.setLayoutManager(mStoryLayoutManager);
        mStoryAdapter = new StoryAdapter(this, STORY_TYPE, mStories);
        mHitRecyclerView.setAdapter(mStoryAdapter);
        page = 0;

        mUserLayoutManager = new LinearLayoutManager(this);
        mUserRecyclerView.setLayoutManager(mUserLayoutManager);
        mUserAdapter = new StoryAdapter(this, USER_TYPE, mStories);
        mUserRecyclerView.setAdapter(mUserAdapter);
        page = 0;
    }

    private void initListeners() {
        mHitScrollListener = new OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // syncronizing RecyclerViews
                mUserRecyclerView.removeOnScrollListener(mUserScrollListener);
                mUserRecyclerView.scrollBy(dx, dy);
                mUserRecyclerView.addOnScrollListener(mUserScrollListener);

                // pagination
                paginate(dy, mStoryLayoutManager);
            }
        };
        mHitRecyclerView.addOnScrollListener(mHitScrollListener);

        mUserScrollListener = new OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // syncronizing RecyclerViews
                mHitRecyclerView.removeOnScrollListener(mHitScrollListener);
                mHitRecyclerView.scrollBy(dx, dy);
                mHitRecyclerView.addOnScrollListener(mHitScrollListener);

                // pagination
                paginate(dy, mStoryLayoutManager);
            }
        };
        mUserRecyclerView.addOnScrollListener(mUserScrollListener);
    }

    private void paginate(int dy, LinearLayoutManager layoutManager) {

        // for pagination
        if (dy > 0) {
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage && isInternetAvailable) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0 && totalItemCount >= Constants.PAGE_SIZE) {
                    isLoading = true;
                    mPreloader.setVisibility(View.VISIBLE);
                    mPresenter.getStories(page);
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.takeView(this);
        mPreloader.setVisibility(View.VISIBLE);
        isInternetAvailable = Utils.isOnline(this);
        if (isInternetAvailable) {
            mPresenter.getStories(page);
        } else {
            mPresenter.getStoriesFromDB();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.dispose();
        mPresenter.dropView();
    }

    @Override
    public void showStrories(List<UserStory> stories) {
        mPreloader.setVisibility(View.GONE);
        mStories.addAll(stories);
        mStoryAdapter.notifyDataSetChanged();

        mUserAdapter.notifyDataSetChanged();

        if (stories.size() < Constants.PAGE_SIZE) {
            isLastPage = true;
        }
        page++;
        isLoading = false;
    }

    @Override
    public void showError(String message) {
        mPreloader.setVisibility(View.GONE);
        isLoading = false;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.fab)
    public void onClickFab() {
        checkPermissionForStorage();
    }

    public void checkPermissionForStorage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GET_PHOTO);
        } else {
            startActivity(PhotoActivity.newIntent(this));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startActivity(PhotoActivity.newIntent(this));
        } else {
            Toast.makeText(this, getString(R.string.permission_not_granted), Toast.LENGTH_LONG).show();
        }
    }
}
