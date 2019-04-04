package prb.com.altarix.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import prb.com.altarix.CallBack;
import prb.com.altarix.model.Note;
import prb.com.altarix.model.Repository;

/**
 * Created by Босс on 30.03.2019.
 */

public class AddViewModel extends ViewModel {
    private Repository mRepository;
    int noteId;

    public AddViewModel(Application application, int noteId){
        mRepository = Repository.getsInstance(application);
        this.noteId = noteId;
    }

    public LiveData<Note> getNoteById(int id){
        return mRepository.loadTaskById(id);
    }

    public void insertTask(CallBack dataCallBack, Note note){
        mRepository.addTask(dataCallBack,note);
    }
    public void deleteTask(CallBack dataCallBack , Note note){
        mRepository.deleteTask(dataCallBack,note);
    }

    public void saveTxt(String title, String desc){
        mRepository.SaveTxt(title, desc);
    }

    public void updateTask(CallBack dataCallBack, Note note){
        mRepository.updateTask(dataCallBack,note);
    }

}
