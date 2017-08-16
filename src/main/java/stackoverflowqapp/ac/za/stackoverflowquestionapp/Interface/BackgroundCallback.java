package stackoverflowqapp.ac.za.stackoverflowquestionapp.Interface;

import java.util.List;

import stackoverflowqapp.ac.za.stackoverflowquestionapp.Classes.Question;

/**
 * Created by Thato on 2017/08/10.
 */

public interface BackgroundCallback {
   void isDownloadedCallback(List<Question> questionList);
}
