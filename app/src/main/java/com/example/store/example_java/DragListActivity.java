package com.example.store.example_java;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import com.example.store.R;

import java.util.Objects;

public class DragListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_list);
//
        if (savedInstanceState == null) {
            showFragment(BoardFragment.newInstance());
        }
       // showFragment(BoardFragment.newInstance());
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_color,null)));
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment, "fragment").commit();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        boolean listFragment = getSupportFragmentManager().findFragmentByTag("fragment") instanceof ListFragment;
//        menu.findItem(R.id.action_lists).setVisible(!listFragment);
//        menu.findItem(R.id.action_board).setVisible(listFragment);
//
//        return true;
//    }

//    @SuppressLint("NonConstantResourceId")
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_lists:
//                showFragment(ListFragment.newInstance());
//                return true;
//            case R.id.action_board:
//                showFragment(BoardFragment.newInstance());
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}