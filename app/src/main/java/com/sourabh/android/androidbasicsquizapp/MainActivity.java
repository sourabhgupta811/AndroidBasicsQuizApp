package com.sourabh.android.androidbasicsquizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.badoualy.stepperindicator.StepperIndicator;
import com.sourabh.android.androidbasicsquizapp.adapter.QuizPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private StepperIndicator indicator;
    private List<View> pagerList = new ArrayList<>();
    private String[] questions;
    private String[] options;
    private String[] answers;
    private LayoutInflater inflater;
    private QuizPagerAdapter adapter;
    private AlertDialog submitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>QuizApp</font>"));
        inflater = LayoutInflater.from(this);
        indicator = findViewById(R.id.indicator);
        questions = getResources().getStringArray(R.array.questions);
        options = getResources().getStringArray(R.array.options);
        answers = getResources().getStringArray(R.array.answers);
        loadQuesIntoView();
        ViewPager pager = findViewById(R.id.viewpager);
        adapter = new QuizPagerAdapter(pagerList);
        pager.setAdapter(adapter);
        indicator.setViewPager(pager, pager.getAdapter().getCount());
        //show this dialog when submitting answers
        submitDialog = new AlertDialog.Builder(this)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String[] userAnswer = checkAnswers();
                        String[] correctAnswer = getResources().getStringArray(R.array.answers);
                        //count for correct answers
                        int count = 0;
                        for (int i = 0; i < userAnswer.length; i++) {
                            if (userAnswer[i].equals(correctAnswer[i]))
                                count++;
                        }
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putExtra("userAnswerCount", count);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setTitle("Submit Answer")
                .setMessage("Are you sure?")
                .create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.submit) {
            submitDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadQuesIntoView() {
        for (int i = 0; i < options.length; i++) {
            String[] particularQuestionOptions = options[i].split("\\*");
            if (i == 5) {
                //load checkbox layout into viewpager
                View checkBoxView;
                checkBoxView = inflater.inflate(R.layout.check_box_pager_item, null);
                TextView checkBoxQuesTextView = checkBoxView.findViewById(R.id.checkboxQuesTextView);
                CheckBox checkBox1 = checkBoxView.findViewById(R.id.checkbox1);
                CheckBox checkBox2 = checkBoxView.findViewById(R.id.checkbox2);
                CheckBox checkBox3 = checkBoxView.findViewById(R.id.checkbox3);
                CheckBox checkBox4 = checkBoxView.findViewById(R.id.checkbox4);
                checkBoxQuesTextView.setText(questions[i]);
                checkBox1.setText(particularQuestionOptions[0]);
                checkBox2.setText(particularQuestionOptions[1]);
                checkBox3.setText(particularQuestionOptions[2]);
                checkBox4.setText(particularQuestionOptions[3]);
                pagerList.add(checkBoxView);
            } else {
                switch (particularQuestionOptions.length) {
                    case 1:
                        //load editText layout into viewpager
                        View editTextView;
                        editTextView = inflater.inflate(R.layout.edit_text_pager_item, null);
                        TextView editTextQuestionTextView = editTextView.findViewById(R.id.editTextQuesTextView);
                        editTextQuestionTextView.setText(questions[i]);
                        pagerList.add(editTextView);
                        break;
                    case 2:
                        //load radioView layout into viewpager
                        View radioView;
                        radioView = inflater.inflate(R.layout.radio_pager_item, null);
                        TextView radioQuesTextView = radioView.findViewById(R.id.radioQuestion);
                        RadioButton radio1 = radioView.findViewById(R.id.radioAnswer1);
                        RadioButton radio2 = radioView.findViewById(R.id.radioAnswer2);
                        radioQuesTextView.setText(questions[i]);
                        radio1.setText(particularQuestionOptions[0]);
                        radio2.setText(particularQuestionOptions[1]);
                        pagerList.add(radioView);
                        break;
                    case 4:
                        //load mcqView layout into viewpager
                        View mcqView;
                        mcqView = inflater.inflate(R.layout.mcq_pager_item, null);
                        TextView mcqQuesTextView = mcqView.findViewById(R.id.mcqQuesTextView);
                        RadioButton mcq1 = mcqView.findViewById(R.id.mcq1);
                        RadioButton mcq2 = mcqView.findViewById(R.id.mcq2);
                        RadioButton mcq3 = mcqView.findViewById(R.id.mcq3);
                        RadioButton mcq4 = mcqView.findViewById(R.id.mcq4);
                        mcqQuesTextView.setText(questions[i]);
                        mcq1.setText(particularQuestionOptions[0]);
                        mcq2.setText(particularQuestionOptions[1]);
                        mcq3.setText(particularQuestionOptions[2]);
                        mcq4.setText(particularQuestionOptions[3]);
                        pagerList.add(mcqView);
                        break;
                }
            }
        }
    }

    private String[] checkAnswers() {
        String[] userAnswer = {"-1", "-1", "-1", "-1", "-1", "-1"};
        List<View> viewPagerList = adapter.getPagerList();
        for (int i = 0; i < viewPagerList.size(); i++) {
            View v = viewPagerList.get(i);
            switch (v.getId()) {
                case R.id.editTextView:
                    EditText editText = v.findViewById(R.id.editTextAnswer);
                    String answer = editText.getText().toString();
                    userAnswer[i] = answer;
                    break;
                case R.id.radioView:
                    RadioGroup group = v.findViewById(R.id.radioAnswerGroup);
                    int checkedRadioButtonId = group.getCheckedRadioButtonId();
                    if (checkedRadioButtonId == R.id.radioAnswer1) {
                        userAnswer[i] = "1";
                    } else if (checkedRadioButtonId == R.id.radioAnswer2) {
                        userAnswer[i] = "2";
                    }
                    break;
                case R.id.mcqView:
                    RadioGroup mcqGroup = v.findViewById(R.id.mcqAnswerGroup);
                    int checkedMcqButtonId = mcqGroup.getCheckedRadioButtonId();
                    if (checkedMcqButtonId == R.id.mcq1) {
                        userAnswer[i] = "1";
                    } else if (checkedMcqButtonId == R.id.mcq2) {
                        userAnswer[i] = "2";
                    } else if (checkedMcqButtonId == R.id.mcq3) {
                        userAnswer[i] = "3";
                    } else if (checkedMcqButtonId == R.id.mcq4) {
                        userAnswer[i] = "4";
                    }
                    break;
                case R.id.checkBoxView:
                    CheckBox box1 = v.findViewById(R.id.checkbox1);
                    CheckBox box2 = v.findViewById(R.id.checkbox2);
                    CheckBox box3 = v.findViewById(R.id.checkbox3);
                    CheckBox box4 = v.findViewById(R.id.checkbox4);
                    String checkBoxAnswer = "";
                    //keep appending all selected options in a string
                    if (box1.isChecked()) {
                        checkBoxAnswer = "1,";
                    }
                    if (box2.isChecked()) {
                        checkBoxAnswer = checkBoxAnswer + "2,";
                    }
                    if (box3.isChecked()) {
                        checkBoxAnswer = checkBoxAnswer + "3,";
                    }
                    if (box4.isChecked()) {
                        checkBoxAnswer = checkBoxAnswer + "4,";
                    }
                    userAnswer[i] = checkBoxAnswer;
                    break;
            }
        }
        return userAnswer;
    }
}
