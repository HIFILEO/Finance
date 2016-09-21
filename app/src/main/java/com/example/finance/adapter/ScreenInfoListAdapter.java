package com.example.finance.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finance.R;
import com.example.finance.model.DataManager;
import com.example.finance.model.Question;
import com.example.finance.model.QuestionType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Adapter for showing screen information based on list of Questions.
 */
public class ScreenInfoListAdapter extends RecyclerArrayAdapter<Question, InfoViewHolder> implements OnQuestionCompletedListener {
    private Set<Integer> completedQuestionsSet = new HashSet<>();
    private OnDataCompletedListener onDataCompletedListener;
    private DataManager dataManager;

    /**
     * Constructor
     * @param objects
     * @param onDataCompletedListener
     * @param dataManager
     */
    public ScreenInfoListAdapter(List<Question> objects, OnDataCompletedListener onDataCompletedListener,
                                 DataManager dataManager) {
        super(objects);
        this.onDataCompletedListener = onDataCompletedListener;
        this.dataManager = dataManager;
    }

    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //
        //Inflate & Create Holder
        //
        switch (viewType) {
            case QuestionType.TEXTUAL: {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.text_question_item, parent, false);
                return new TextQuestionViewHolder(view, dataManager, this);
            }
            case QuestionType.NUMERICAL: {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.numerical_question_item, parent, false);
                return new NumericalQuestionViewHolder(view, dataManager, this);
            }
            case QuestionType.SINGLE_CHOICE: {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.multiple_choice_question_item, parent, false);
                return new SingleChoiceQuestionViewHolder(view, dataManager, this);
            }
            case QuestionType.MULTIPLE_CHOICE: {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.multiple_choice_question_item, parent, false);
                return new MultipleChoiceQuestionViewHolder(view, dataManager, this);
            }
            default:
                throw new RuntimeException("No type in data.");
        }
    }

    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
        Question question = getItem(position);
        int number = question.getType();
        return number;
    }

    @Override
    public void onComplete(int questionNumber) {
        completedQuestionsSet.add(questionNumber);
        if (completedQuestionsSet.size() == this.getItemCount()) {
            onDataCompletedListener.showNextButton(true);
        } else {
            onDataCompletedListener.showNextButton(false);
        }
    }

    @Override
    public void onIncomplete(int questionNumber) {
        completedQuestionsSet.remove(questionNumber);
        if (completedQuestionsSet.size() != this.getItemCount()) {
            onDataCompletedListener.showNextButton(false);
        }
    }

    @Override
    public void replace(List<Question> newList) {
        super.replace(newList);
        completedQuestionsSet.clear();
    }
}
