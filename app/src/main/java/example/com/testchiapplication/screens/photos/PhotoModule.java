package example.com.testchiapplication.screens.photos;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import example.com.testchiapplication.di.ActivityScoped;
import example.com.testchiapplication.di.FragmentScoped;

@Module
public abstract class PhotoModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract PhotoFragment photoFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract OneLargePhotoFragment oneLargePhotoFragment();

    @ActivityScoped
    @Binds
    abstract PhotoContract.Presenter photoPresenter(PhotoPresenter presenter);
}
