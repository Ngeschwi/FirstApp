package com.example.fristapp.ui.quiz;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fristapp.data.Question;
import com.example.fristapp.data.QuestionRepository;

import java.util.List;

public class QuizViewModel extends ViewModel {

    private QuestionRepository questionRepository;
    private List<Question> questions;
    private Integer currentQuestionIndex;

    MutableLiveData<Question> currentQuestion = new MutableLiveData<Question>();
    MutableLiveData<Integer> score = new MutableLiveData<Integer>(0);
    MutableLiveData<Boolean> isLastQuestion = new MutableLiveData<Boolean>(false);

    public QuizViewModel(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void startQuiz() {
        Integer startIndex = 0;

        questions = questionRepository.getQuestions();
        currentQuestion.postValue(questions.get(startIndex));
        currentQuestionIndex = startIndex;
    }

    public boolean isAnswerValid(int answerIndex) {
        Question question = currentQuestion.getValue();
        boolean isValid = question != null && question.getAnswerIndex() == answerIndex;
        Integer currentScore = score.getValue();
        if (currentScore != null && isValid) {
            score.setValue(currentScore + 1);
        }
        return isValid;
    }

    public void nextQuestion() {
        Integer nextIndex = currentQuestionIndex + 1;
        if (nextIndex >= questions.size()) {
            return;
        } else if (nextIndex == questions.size() - 1) {
            isLastQuestion.postValue(true);
        }
        currentQuestionIndex = nextIndex;
        currentQuestion.postValue(questions.get(currentQuestionIndex));
    }
}
