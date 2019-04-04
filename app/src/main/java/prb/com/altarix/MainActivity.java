package prb.com.altarix;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import prb.com.altarix.adapter.NoteAdapter;
import prb.com.altarix.model.Note;
import prb.com.altarix.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements NoteAdapter.ItemClickListener,CallBack {

    private NoteAdapter mAdapter;
    @BindView(R.id.recyclervw)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar topToolBar;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NoteAdapter(this,this);
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        setSupportActionBar(topToolBar);
        setUpViewMode();
    }

    private void setUpViewMode(){
              viewModel.loadAllData(MainActivity.this);
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
    public void loadAllNotes(List<Note> note) {
        mAdapter.setTasks(note);
    }

    @Override
    public void onItemClickListener(int id) {
        Intent intent = new Intent(MainActivity.this, ViewActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_add){
            Intent intent = new Intent(MainActivity.this,AddActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnMap)
    public void onClick(View view){{
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }
    }



}
