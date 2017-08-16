package stackoverflowqapp.ac.za.stackoverflowquestionapp.Helpers.Background;

import android.os.AsyncTask;

import java.io.IOException;

import stackoverflowqapp.ac.za.stackoverflowquestionapp.Activities.MainActivity;
import stackoverflowqapp.ac.za.stackoverflowquestionapp.Helpers.DataParser;
import stackoverflowqapp.ac.za.stackoverflowquestionapp.Helpers.DownloadUrl;

/**
 * Created by Thato on 2017/08/10.
 */

public class QuestionAsyncTask extends AsyncTask<Object,String,String>{
    String url;
    private MainActivity delegate;

    public QuestionAsyncTask(MainActivity delegate){
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(Object... objects) {
        url = (String)objects[0];
        String questionsData = "-1";
        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            questionsData = downloadUrl.readURL(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionsData;
    }

    @Override
    protected void onPostExecute(String s) {
        if(!s.equals("-1")){
            DataParser dataParser = new DataParser();
            delegate.isDownloadedCallback(dataParser.parse(s));
        }
    }
}
