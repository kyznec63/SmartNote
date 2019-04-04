package prb.com.altarix;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import prb.com.altarix.model.Note;
import prb.com.altarix.viewmodel.AddViewModel;
import prb.com.altarix.viewmodel.AddViewModelRes;

public class AddActivity extends AppCompatActivity implements CallBack {



    public static final int HIGH = 1;
    public static final int MEDIUM = 2;
    public static final int LOW = 3;
    public static final int NOT= 4;


    public static final String INSTANCE_NOTE = "instanceNote";
    private static final int NOTE_ID = -1;




    Bitmap photo;

    private int mNote = -1;

    @BindView(R.id.editTextTitle)
    EditText etTitle;

    @BindView(R.id.editTextDesc)
    EditText mEditText;

    @BindView(R.id.image)
    ImageView imageView;

    AddViewModelRes res;
    AddViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        res = new AddViewModelRes(getApplication(),mNote);
        viewModel = ViewModelProviders.of(this,res).get(AddViewModel.class);
        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_NOTE)) {
            mNote = savedInstanceState.getInt(INSTANCE_NOTE, NOTE_ID);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if(CameraIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(CameraIntent, 1);
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);



        }
    }}


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_NOTE, mNote);
        super.onSaveInstanceState(outState);
    }



    @OnClick(R.id.saveButton)
    public void OnClick(View view){
        onSaveButtonClicked();
    }

    public void onSaveButtonClicked() {


        if(photo!= null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            String description = mEditText.getText().toString().trim();
            int priority = getPriorityFromViews();
            String title = etTitle.getText().toString().trim();

            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            photo.recycle();

            final Note taskEntry = new Note(description, priority, title, byteArray);
            viewModel.insertTask(AddActivity.this, taskEntry);
            finish();


        }
        else {
            Toast.makeText(this, "Пожалуйста, добавьте фотографию", Toast.LENGTH_LONG).show();
        }

    }

    public int getPriorityFromViews() {
        int priority = 4;
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
