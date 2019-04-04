package prb.com.altarix.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Босс on 30.03.2019.
 */

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private int priority;
    private String title;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "image")
    private byte[] image;


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }





    @Ignore
    public Note(String description, int priority, String title, byte[] image) {
        this.description = description;
        this.priority = priority;
        this.title = title;
        this.image = image;

    }

    public Note(int id, String description, int priority, String title, byte[] image) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.title = title;
        this.image = image;
        //this.latitude = latitude;
        //this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
