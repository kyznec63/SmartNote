package prb.com.altarix.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Босс on 30.03.2019.
 */

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY priority")
    LiveData<List<Note>> loadAllNote();

    @Query("SELECT * FROM notes ORDER BY priority")
    Flowable<List<Note>> loadAllNotesObeservale();

    @Insert
    void insertTask(Note note);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM notes WHERE id =:id")
    LiveData<Note> loadNoteById(int id);

}