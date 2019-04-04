package prb.com.altarix.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import io.reactivex.annotations.NonNull;

/**
 * Created by Босс on 30.03.2019.
 */
public class AddViewModelRes extends ViewModelProvider.NewInstanceFactory {
    private final Application application;
    private int noteId;


    public AddViewModelRes(Application application, int noteId) {
        this.application = application;
        this.noteId = noteId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddViewModel(application,noteId);
    }
}
