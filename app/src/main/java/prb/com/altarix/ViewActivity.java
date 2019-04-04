package prb.com.altarix;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import prb.com.altarix.model.Note;
import prb.com.altarix.viewmodel.AddViewModel;
import prb.com.altarix.viewmodel.AddViewModelRes;

public class ViewActivity extends AppCompatActivity implements CallBack {


    public int mNote = -1;
    public static final String INSTANCE_NOTE = "instanceNote";
    private static final int NOTE_ID = -1;


    AddViewModel viewModel;
    AddViewModelRes res;
    @BindView(R.id.tvTitle)
    TextView title;

    @BindView(R.id.tvDesc)
    TextView desc;
    @BindView(R.id.btnDelete)
    Button delete;

    @BindView(R.id.btnEdit)
    Button edit;

    @BindView(R.id.btnLoad)
    Button load;

    @BindView(R.id.image)
    ImageView image;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);
        res = new AddViewModelRes(getApplication(),mNote);
        viewModel = ViewModelProviders.of(this,res).get(AddViewModel.class);

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_NOTE)) {
            mNote = savedInstanceState.getInt(INSTANCE_NOTE, NOTE_ID);
        }

      Intent intent = getIntent();
      final int id = intent.getIntExtra("id", 0);


      viewModel.getNoteById(id).observe(this, new Observer<Note>() {
          @Override
          public void onChanged(@Nullable Note note) {
              if(note!= null) {
                  populateUI(note);
              }
          }
      });
      edit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(ViewActivity.this, EditActivity.class);
              intent.putExtra("id",id);
              startActivity(intent);
          }
      });



        }



    private void populateUI(final Note note) {

        desc.setText(note.getDescription());
        title.setText(note.getTitle());

        byte[] sImage = note.getImage();



       ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(sImage);

       Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
       image.setImageBitmap(bitmap);

              delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteTask(ViewActivity.this, note);
                finish();
            }
        });
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateNoteOnSD(note.getTitle(), note.getDescription());
            }
        });
    }
    public void generateNoteOnSD(String sFileName, String sBody) {
        viewModel.saveTxt(sFileName, sBody);
        Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show();
    }

     public void finish(){
      Intent intent = new Intent(ViewActivity.this, MainActivity.class);
      startActivity(intent);
     }



    @Override
    public void noteAdded() {

    }

    @Override
    public void noteDeleted() {

    }

    @Override
    public void noteUpdated() {

    }

    @Override
    public void errorAdded() {

    }

    @Override
    public void loadAllNotes(List<Note> taskEntries) {

    }
}
