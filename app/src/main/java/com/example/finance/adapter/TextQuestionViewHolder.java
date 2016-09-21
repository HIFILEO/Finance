package com.example.finance.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finance.R;
import com.example.finance.model.DataManager;
import com.example.finance.model.Question;
import com.example.finance.model.TextAnswer;
import com.example.finance.model.TextQuestion;

import butterknife.Bind;

/**
 * Text question view holder.
 */
public class TextQuestionViewHolder extends InfoViewHolder {
    private TextQuestion textQuestion;
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
    public TextQuestionViewHolder(View itemView, DataManager dataManager,
                                  OnQuestionCompletedListener onQuestionCompletedListener) {
        super(itemView, dataManager);
        this.onQuestionCompletedListener = onQuestionCompletedListener;
    }

    @Override
    public void bind(Question question) {
        textQuestion = (TextQuestion) question;

        questionTextView.setText(textQuestion.getQuestion());

        TextAnswer textAnswer = (TextAnswer) dataManager.getAnswer(textQuestion.getQuestionNumber());
        if (textAnswer != null) {
            answerEditText.setText(textAnswer.getAnswer());
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
                    onQuestionCompletedListener.onIncomplete(textQuestion.getQuestionNumber());
                } else {
                    onQuestionCompletedListener.onComplete(textQuestion.getQuestionNumber());
                }
                dataManager.setAnswer(textQuestion.getQuestionNumber(), new TextAnswer(answer));
            }
        });

    }
}
