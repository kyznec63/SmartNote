package prb.com.altarix;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import prb.com.altarix.model.Note;
import prb.com.altarix.viewmodel.AddViewModel;
import prb.com.altarix.viewmodel.AddViewModelRes;

public class EditActivity extends AppCompatActivity implements CallBack {


    AddViewModel viewModel;




    public static final int HIGH = 1;
    public static final int MEDIUM = 2;
    public static final int LOW = 3;
    public static final int NOT= 4;

    Bitmap bitmap;

    @BindView(R.id.etDesc)
    EditText etDesc;
    @BindView(R.id.etTitle)
    EditText etTitle;

    @BindView(R.id.btnSave)
    Button save;
    AddViewModelRes res;


    public int mNote = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);

        res = new AddViewModelRes(getApplication(),mNote);
        viewModel = ViewModelProviders.of(this,res).get(AddViewModel.class);


        Intent intent = getIntent();
        final int id = intent.getIntExtra("id", 0);



        viewModel.getNoteById(id).observe(this, new Observer<Note>() {
            @Override
            public void onChanged(@Nullable Note note) {
                if(note != null) {
                    populateUI(note);
                }

            }
        });
    }
    public int getPriorityFromViews() {
        int priority = 1;
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        switch (checkedId) {

            case R.id.radButton1:
                priority = HIGH;
                break;
            case R.id.radButton2:
                priority = MEDIUM;
                break;
            case R.id.radButton3:
                priority = LOW;
                break;
            case R.id.radButton4:
                priority = NOT;

        }
        return priority;
    }
    public void setPriorityInViews(int priority) {
        switch (priority) {
            case HIGH:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton1);
                break;
            case MEDIUM:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton2);
                break;
            case LOW:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton2);
                break;
            case NOT:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton4);
        }}

    private void populateUI(final Note note) {
        etDesc.setText(note.getDescription());
        etTitle.setText(note.getTitle());
        setPriorityInViews(note.getPriority());
        final byte[] byteArray = note.getImage();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = getIntent();
                final int id = intent.getIntExtra("id", 0);

                final int priority = getPriorityFromViews();

                final String title = etTitle.getText().toString();
                final String description = etDesc.getText().toString();

                final Note taskEntry = new Note(id, description,priority,title, byteArray);
                viewModel.updateTask(EditActivity.this, taskEntry);
                close();
            }
        });

    }

    @OnClick(R.id.btncancel)
    public void onClick(View view){
        onBackPressed();
    }

    @Override
    public void noteAdded() {

    }

    @Override
    public void noteDeleted() {

    }

    @Override
    public void noteUpdated() {
        Toast.makeText(EditActivity.this,"Обновлено",Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorAdded() {

    }

    @Override
    public void loadAllNotes(List<Note> taskEntries) {

    }
    public void close(){
        Intent intent = new Intent(EditActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
