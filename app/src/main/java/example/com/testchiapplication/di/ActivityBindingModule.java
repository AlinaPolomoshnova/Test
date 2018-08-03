package example.com.testchiapplication.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import example.com.testchiapplication.screens.main.MainActivity;
import example.com.testchiapplication.screens.main.MainModule;
import example.com.testchiapplication.screens.photos.PhotoActivity;
import example.com.testchiapplication.screens.photos.PhotoModule;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = PhotoModule.class)
    abstract PhotoActivity photoActivity();

}



