package stackoverflowqapp.ac.za.stackoverflowquestionapp.Helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import stackoverflowqapp.ac.za.stackoverflowquestionapp.Classes.Question;

/**
 * Created by Thato on 2017/08/10.
 * The purpose of this class is to manipulate the json data and return list of questions
 */

public class DataParser {
    public List<Question> parse(String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getQuestions(jsonArray);
    }

    private List<Question> getQuestions(JSONArray jsonArray) {
        int size = jsonArray.length();
        List<Question> questions = new ArrayList<>();
        Question singleQuestion;

        try{

            for(int x = 0; x < size; x++){
                if(x != 50){
                    singleQuestion = getQuestion(jsonArray.getJSONObject(x));
                    if(singleQuestion != null)
                        questions.add(singleQuestion);
                }else {
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return questions;
    }

    private Question getQuestion(JSONObject jsonObject) throws JSONException {
        Question question;

        JSONArray tagsArray = jsonObject.getJSONArray("tags");
        List<String> tagList = new ArrayList<>();
        for(int x  = 0; x < tagsArray.length(); x++){
            tagList.add(tagsArray.getString(x));
        }

        boolean isAnswered = jsonObject.getBoolean("is_answered");
        int answerCount = jsonObject.getInt("answer_count");
        String date = String.valueOf(jsonObject.getInt("last_activity_date"));
        String title = jsonObject.getString("title");


        question = new Question(tagList,answerCount,date,title,isAnswered);

        return question;
    }
}
