package example.com.testchiapplication.di;


import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;


public class TestApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
