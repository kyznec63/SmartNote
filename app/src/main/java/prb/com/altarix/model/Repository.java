package prb.com.altarix.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import prb.com.altarix.CallBack;

/**
 * Created by Босс on 30.03.2019.
 */

public class Repository {
    private static final Object LOCK = new Object();
    private NoteDatabase mDb;
    private final NoteDao noteDao;
    private static Repository sInstance;

    private Repository(Application application) {
        mDb = NoteDatabase.getsInstance(application.getApplicationContext());
        noteDao = mDb.taskDao();
    }

    public synchronized static Repository getsInstance(Application application) {
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new Repository(application);
            }
        }
        return sInstance;
    }

    public void addTask(final CallBack dataCallBack, final Note taskEntry){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                noteDao.insertTask(taskEntry);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        dataCallBack.noteAdded();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dataCallBack.errorAdded();
                    }
                });
    }
    public void deleteTask(final CallBack dataCallBack, final Note taskEntry){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                noteDao.deleteNote(taskEntry);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        dataCallBack.noteAdded();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dataCallBack.errorAdded();
                    }
                });
    }





    public void updateTask(final CallBack dataCallBack, final Note taskEntry){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                noteDao.updateNote(taskEntry);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        dataCallBack.noteUpdated();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dataCallBack.errorAdded();
                    }
                });
    }

    public void loadDataObserver(final CallBack dataCallBack){
        noteDao.loadAllNotesObeservale()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Note>>() {
                    @Override
                    public void accept(List<Note> taskEntries) throws Exception {
                        dataCallBack.loadAllNotes(taskEntries);
                    }
                });
    }

    public LiveData<List<Note>> loadTasks(){
        return noteDao.loadAllNote();
    }

    public LiveData<Note> loadTaskById(int id){
        return noteDao.loadNoteById(id);
    }
    public void SaveTxt(String title, String desc){
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, title);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(desc);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
