package prb.com.altarix.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Босс on 30.03.2019.
 */

@Database(entities = {Note.class},version = 1,exportSchema = false)

public abstract class NoteDatabase extends RoomDatabase {

    private static final Object OBJECT = new Object();
    private static final String DATABASE_NAME = "notes";
    private static NoteDatabase rInstance;

    public static NoteDatabase getsInstance(Context context) {
        if(rInstance == null){
            synchronized (OBJECT){
                rInstance = Room.databaseBuilder(context.getApplicationContext(),
                        NoteDatabase.class,
                        DATABASE_NAME)
                        .build();
            }
        }
        return rInstance;
    }

    public abstract NoteDao taskDao();
}
