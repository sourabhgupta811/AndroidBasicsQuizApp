package com.sourabh.android.androidbasicsquizapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sourabh.android.androidbasicsquizapp.adapter.ListViewAdapter;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        LayoutInflater inflater = LayoutInflater.from(this);
        View toastLayout = inflater.inflate(R.layout.toast_layout, null);
        final TextView toastTextView = toastLayout.findViewById(R.id.toastAnswerTextView);
        final TextView userAnswerCountTitle = findViewById(R.id.userAnswerCountTitle);
        final TextView userAnswerCountTextView = findViewById(R.id.userAnswerCount);
        userAnswerCountTextView.setTranslationY(-1000);
        userAnswerCountTitle.setTranslationY(-1000);
        ListView listView = findViewById(R.id.listView);
        int userAnswerCount = getIntent().getIntExtra("userAnswerCount", 0);
        userAnswerCountTextView.setText(String.valueOf(userAnswerCount) + " / 6");
        ListViewAdapter adapter = new ListViewAdapter(this);
        listView.setAdapter(adapter);
        //show textViewScore answer after below toast has disappeared
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                userAnswerCountTextView.animate().translationYBy(1000).setDuration(1000);
                userAnswerCountTitle.animate().translationYBy(1000).setDuration(1000);
            }
        }, 2000);
        //show toastScore
        Toast toast = new Toast(getApplicationContext());
        toastTextView.setText(String.valueOf(userAnswerCount) + " / 6");
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 200);
        toast.setView(toastLayout);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.result_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.retry) {
            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
