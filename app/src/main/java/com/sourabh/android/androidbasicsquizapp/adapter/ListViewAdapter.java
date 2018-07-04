package com.sourabh.android.androidbasicsquizapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sourabh.android.androidbasicsquizapp.R;

public class ListViewAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private LayoutInflater mInflater;
    private String[] questions;
    private String[] answers;

    public ListViewAdapter(@NonNull Context context) {
        super(context, 0);
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        questions = context.getResources().getStringArray(R.array.questions);
        answers = context.getResources().getStringArray(R.array.answers_in_strings);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (convertView == null) {
            v = mInflater.inflate(R.layout.listview_item, parent, false);
        }
        TextView quesTextView = v.findViewById(R.id.resultPageListQues);
        TextView answerTextView = v.findViewById(R.id.resultPageListAnswer);
        quesTextView.setText(questions[position]);
        answerTextView.setText(answers[position]);
        return v;
    }

    @Override
    public int getCount() {
        return questions.length;
    }
}
