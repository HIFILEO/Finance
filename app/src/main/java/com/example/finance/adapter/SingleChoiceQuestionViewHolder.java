package com.example.finance.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.finance.R;
import com.example.finance.model.DataManager;
import com.example.finance.model.Question;
import com.example.finance.model.SingleChoiceAnswer;
import com.example.finance.model.SingleChoiceQuestion;

import butterknife.Bind;


/**
 * View holder for single choice
 */
public class SingleChoiceQuestionViewHolder extends InfoViewHolder implements CompoundButton.OnCheckedChangeListener {
    private SingleChoiceQuestion singleChoiceQuestion;
    private OnQuestionCompletedListener onQuestionCompletedListener;

    @Bind(R.id.questionTextView)
    TextView questionTextView;
    @Bind(R.id.choiceListLinearLayout)
    LinearLayout choiceListLinearLayout;

    /**
     * Constructor
     * @param itemView
     * @param dataManager
     * @param onQuestionCompletedListener
     */
    public SingleChoiceQuestionViewHolder(View itemView, DataManager dataManager, OnQuestionCompletedListener onQuestionCompletedListener) {
        super(itemView, dataManager);
        this.onQuestionCompletedListener = onQuestionCompletedListener;
    }

    @Override
    public void bind(Question question) {
        singleChoiceQuestion = (SingleChoiceQuestion) question;

        //
        //Address question text
        //
        questionTextView.setText(singleChoiceQuestion.getQuestion());

        //
        //Remove entries if needed
        //
        if (choiceListLinearLayout.getChildCount() > singleChoiceQuestion.getChoices().length) {
            for (int i = singleChoiceQuestion.getChoices().length; i < choiceListLinearLayout.getChildCount(); i++) {
                choiceListLinearLayout.removeViewAt(i);
            }
        }

        //
        //Add entries if needed
        //
        if (choiceListLinearLayout.getChildCount() < singleChoiceQuestion.getChoices().length) {
            for (int i = choiceListLinearLayout.getChildCount(); i < singleChoiceQuestion.getChoices().length; i++) {
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
            choiceTextView.setText(singleChoiceQuestion.getChoices()[i]);

            CheckBox choiceCheckBox = (CheckBox) choiceView.findViewById(R.id.choiceCheckBox);

            SingleChoiceAnswer singleChoiceAnswer = (SingleChoiceAnswer) dataManager.getAnswer(singleChoiceQuestion.getQuestionNumber());
            if (singleChoiceAnswer != null) {
                if (singleChoiceAnswer.getAnswer() == i) {
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
        if (isChecked) {
            //
            //Uncheck all other choices
            //
            int tagNumber = (int) buttonView.getTag();
            for (int i = 0; i < choiceListLinearLayout.getChildCount(); i++) {
                if (i != tagNumber) {
                    View choiceView = choiceListLinearLayout.getChildAt(i);
                    CheckBox choiceCheckBox = (CheckBox) choiceView.findViewById(R.id.choiceCheckBox);
                    choiceCheckBox.setChecked(false);
                }
            }

            //store answer
            SingleChoiceAnswer singleChoiceAnswer = new SingleChoiceAnswer((Integer) buttonView.getTag());
            dataManager.setAnswer(singleChoiceQuestion.getQuestionNumber(), singleChoiceAnswer);

            //notify
            onQuestionCompletedListener.onComplete(singleChoiceQuestion.getQuestionNumber());
        } else {
            //remove answer
            dataManager.removeAnswer(singleChoiceQuestion.getQuestionNumber());

            //notify
            onQuestionCompletedListener.onIncomplete(singleChoiceQuestion.getQuestionNumber());
        }
    }
}
