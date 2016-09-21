package com.example.finance.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.finance.R;
import com.example.finance.model.DataManager;
import com.example.finance.model.MultipleChoiceAnswer;
import com.example.finance.model.MultipleChoiceQuestion;
import com.example.finance.model.Question;
import com.example.finance.model.SingleChoiceAnswer;
import com.example.finance.model.SingleChoiceQuestion;

import java.util.HashSet;
import java.util.Set;

import butterknife.Bind;


/**
 * View holder for multiple choice
 */
public class MultipleChoiceQuestionViewHolder extends InfoViewHolder implements CompoundButton.OnCheckedChangeListener {
    private MultipleChoiceQuestion multipleChoiceQuestion;
    private OnQuestionCompletedListener onQuestionCompletedListener;

    @Bind(R.id.questionTextView)
    TextView questionTextView;
    @Bind(R.id.choiceListLinearLayout)
    LinearLayout choiceListLinearLayout;

    public MultipleChoiceQuestionViewHolder(View itemView, DataManager dataManager, OnQuestionCompletedListener onQuestionCompletedListener) {
        super(itemView, dataManager);
        this.onQuestionCompletedListener = onQuestionCompletedListener;
    }

    @Override
    public void bind(Question question) {
        multipleChoiceQuestion = (MultipleChoiceQuestion) question;

        //
        //Address question text
        //
        questionTextView.setText(multipleChoiceQuestion.getQuestion());

        //
        //Remove entries if needed
        //
        if (choiceListLinearLayout.getChildCount() > multipleChoiceQuestion.getChoices().length) {
            for (int i = multipleChoiceQuestion.getChoices().length; i < choiceListLinearLayout.getChildCount(); i++) {
                choiceListLinearLayout.removeViewAt(i);
            }
        }

        //
        //Add entries if needed
        //
        if (choiceListLinearLayout.getChildCount() < multipleChoiceQuestion.getChoices().length) {
            for (int i = choiceListLinearLayout.getChildCount(); i < multipleChoiceQuestion.getChoices().length; i++) {
                View view = LayoutInflater.from(choiceListLinearLayout.getContext())
                        .inflate(R.layout.choice, choiceListLinearLayout, false);

                CheckBox choiceCheckBox = (CheckBox) view.findViewById(R.id.choiceCheckBox);
                choiceCheckBox.setTag(i);
                choiceCheckBox.setOnCheckedChangeListener(this);

                choiceListLinearLayout.addView(view, i);
            }
        }

        //
        //Correctly set Text and Radio Button status
        //
        for (int i = 0; i < choiceListLinearLayout.getChildCount(); i++) {
            View choiceView = choiceListLinearLayout.getChildAt(i);

            TextView choiceTextView = (TextView) choiceView.findViewById(R.id.choiceTextView);
            choiceTextView.setText(multipleChoiceQuestion.getChoices()[i]);

            CheckBox choiceCheckBox = (CheckBox) choiceView.findViewById(R.id.choiceCheckBox);

            MultipleChoiceAnswer multipleChoiceAnswer = (MultipleChoiceAnswer) dataManager.getAnswer(multipleChoiceQuestion.getQuestionNumber());
            if (multipleChoiceAnswer != null) {
                if (multipleChoiceAnswer.getAnswers().contains(i)) {
                    choiceCheckBox.setChecked(true);
                } else {
                    choiceCheckBox.setChecked(false);
                }
            } else {
                choiceCheckBox.setChecked(false);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Set<Integer> answerSet = new HashSet<>();
        for (int i = 0; i < choiceListLinearLayout.getChildCount(); i++) {
            View choiceView = choiceListLinearLayout.getChildAt(i);
            CheckBox choiceCheckBox = (CheckBox) choiceView.findViewById(R.id.choiceCheckBox);
            if (choiceCheckBox.isChecked()) {
                answerSet.add(i);
            }
        }

        //store answer
        if (answerSet.isEmpty()) {
            dataManager.removeAnswer(multipleChoiceQuestion.getQuestionNumber());
            onQuestionCompletedListener.onIncomplete(multipleChoiceQuestion.getQuestionNumber());
        } else {
            MultipleChoiceAnswer multipleChoiceAnswer = new MultipleChoiceAnswer(answerSet);
            dataManager.setAnswer(multipleChoiceQuestion.getQuestionNumber(), multipleChoiceAnswer);
            onQuestionCompletedListener.onComplete(multipleChoiceQuestion.getQuestionNumber());
        }
    }
}
