package example.com.testchiapplication.screens.main;

import dagger.Binds;
import dagger.Module;
import example.com.testchiapplication.di.ActivityScoped;

@Module
public abstract class MainModule {

    @ActivityScoped
    @Binds
    abstract MainContract.Presenter mainPresenter(MainPresenter presenter);
}
