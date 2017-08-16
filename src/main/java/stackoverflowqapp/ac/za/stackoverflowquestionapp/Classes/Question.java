package stackoverflowqapp.ac.za.stackoverflowquestionapp.Classes;

import java.util.List;

/**
 * Created by Thato on 2017/08/10.
 * A representation of a question class
 */

public class Question {
    private List<String> tags;
    private int answer_count;
    private String datePosted;
    private String questionTitle;
    private boolean isQuestionAnswered;


    public Question(List<String> tags, int answer_count, String datePosted, String questionTitle, boolean isQuestionAnswered) {
        setTags(tags);
        setAnswer_count(answer_count);
        setDatePosted(datePosted);
        setQuestionTitle(questionTitle);
        setQuestionAnswered(isQuestionAnswered);
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getAnswer_count() {
        return answer_count;
    }

    public void setAnswer_count(int answer_count) {
        this.answer_count = answer_count;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public boolean isQuestionAnswered() {
        return isQuestionAnswered;
    }

    public void setQuestionAnswered(boolean questionAnswered) {
        isQuestionAnswered = questionAnswered;
    }
}
