package com.example.finance.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finance.R;
import com.example.finance.model.DataManager;
import com.example.finance.model.NumericalAnswer;
import com.example.finance.model.NumericalQuestion;
import com.example.finance.model.Question;

import butterknife.Bind;

/**
 * View holder for Numerical Questions
 */
public class NumericalQuestionViewHolder extends InfoViewHolder {
    private NumericalQuestion numericalQuestion;
    private OnQuestionCompletedListener onQuestionCompletedListener;

    @Bind(R.id.questionTextView)
    TextView questionTextView;
    @Bind(R.id.answerEditText)
    EditText answerEditText;

    /**
     * Constructor
     * @param itemView
     * @param dataManager
     * @param onQuestionCompletedListener
     */
    public NumericalQuestionViewHolder(View itemView, DataManager dataManager,
                                       OnQuestionCompletedListener onQuestionCompletedListener) {
        super(itemView, dataManager);
        this.onQuestionCompletedListener = onQuestionCompletedListener;
    }

    @Override
    public void bind(Question question) {
        numericalQuestion = (NumericalQuestion) question;

        questionTextView.setText(numericalQuestion.getQuestion());

        NumericalAnswer numericalAnswer = (NumericalAnswer) dataManager.getAnswer(numericalQuestion.getQuestionNumber());
        if (numericalAnswer != null) {
            answerEditText.setText(numericalAnswer.getAnswer());
        } else {
            answerEditText.setText("");
        }
        answerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //NO-OP
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //NO-OP
            }

            @Override
            public void afterTextChanged(Editable s) {
                //
                //Note - just save every character, you could have used RX to create a timer for saving but I wanted
                //to get this completed.
                String answer = answerEditText.getText().toString();
                if (answer.isEmpty()) {
                    onQuestionCompletedListener.onIncomplete(numericalQuestion.getQuestionNumber());
                } else {
                    onQuestionCompletedListener.onComplete(numericalQuestion.getQuestionNumber());
                }

                int number = 0;
                if (!answer.isEmpty()) {
                    number = Integer.valueOf(answer);
                }
                dataManager.setAnswer(numericalQuestion.getQuestionNumber(), new NumericalAnswer(number));
            }
        });

    }
}
