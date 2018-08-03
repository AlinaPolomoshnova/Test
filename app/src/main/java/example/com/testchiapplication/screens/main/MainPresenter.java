package example.com.testchiapplication.screens.main;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import example.com.testchiapplication.Constants;
import example.com.testchiapplication.db.AppDatabase;
import example.com.testchiapplication.model.Hit;
import example.com.testchiapplication.model.User;
import example.com.testchiapplication.model.UserStory;
import example.com.testchiapplication.net.IRestClient;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MainPresenter implements MainContract.Presenter {
    private static final String TAG = MainPresenter.class.getName();

    @Nullable
    private MainContract.View mView;

    private IRestClient mRestClient;
    private AppDatabase mDb;
    private List<UserStory> userStories = new ArrayList<>();
    CompositeDisposable mDisposables;

    @Inject
    MainPresenter(IRestClient iRestClient, AppDatabase db) {
        mRestClient = iRestClient;
        mDb = db;
        mDisposables = new CompositeDisposable();
    }

    @Override
    public void takeView(MainContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void getStories(int page) {

        mRestClient.getStories(page, Constants.STORY)
                .flatMapIterable(story -> story.getHits())
                .flatMap(new Function<Hit, ObservableSource<UserStory>>() {
                    @Override
                    public ObservableSource<UserStory> apply(Hit hit) throws Exception {
                        return Observable.zip(Observable.just(hit),
                                mRestClient.getUser(hit.getAuthor()),
                                new BiFunction<Hit, User, UserStory>() {
                                    @Override
                                    public UserStory apply(Hit hit, User user) throws Exception {
                                        Log.d(TAG, "apply UserStory");
                                        UserStory userStory = new UserStory();
                                        userStory.setStoryId(Long.parseLong(hit.getObjectID()));
                                        userStory.setAuthor(hit.getAuthor());
                                        userStory.setKarma(user.getKarma());
                                        userStory.setStoryDate(hit.getCreatedAt());
                                        userStory.setTags(hit.getTags());
                                        userStory.setTitle(hit.getTitle());
                                        userStory.setUserDate(user.getCreatedAt());
                                        userStory.setUsername(user.getUsername());
                                        return userStory;
                                    }
                                });
                    }
                }).toList()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<UserStory>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "getStories onSubscribe");
                    }

                    @Override
                    public void onNext(List<UserStory> stories) {
                        Log.d(TAG, "getStories onNext");
                        if (userStories != null) {
                            userStories.clear();
                            userStories.addAll(stories);
                            if (mView != null) {
                                mView.showStrories(userStories);
                            }
                            insertToDb(userStories, page);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "getStories onError = " + e.getMessage());
                        if (mView != null) {
                            mView.showError("Get stories on error = " + e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "getStories onComplete");
                    }
                });

    }

    @Override
    public void getStoriesFromDB() {
        Disposable getFromDbDisposable = mDb.userStoryDao().getAllStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null) {
                        userStories.clear();
                        userStories.addAll(response);
                        if (mView != null) {
                            mView.showStrories(userStories);
                        }
                    }
                });
        mDisposables.add(getFromDbDisposable);
    }

    private void insertToDb(List<UserStory> userStories, int page) {
        Disposable insertToDb = Single.fromCallable(() ->
                mDb.userStoryDao().insertUserStories(userStories))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.d(TAG, "Stories from page" + page + " sucessfully insert in DB");
                }, throwable -> {
                    Log.d(TAG, "Stories from page" + page + " NOT insert in DB");
                });
        mDisposables.add(insertToDb);
    }

    @Override
    public void dispose() {
        mDisposables.clear();
    }
}
