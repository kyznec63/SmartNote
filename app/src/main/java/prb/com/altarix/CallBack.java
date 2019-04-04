package prb.com.altarix;

import java.util.List;

import prb.com.altarix.model.Note;

/**
 * Created by Босс on 30.03.2019.
 */

public interface CallBack {
    void noteAdded();
    void noteDeleted();
    void noteUpdated();
    void errorAdded();
    void loadAllNotes(List<Note> taskEntries);
}
