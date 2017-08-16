package stackoverflowqapp.ac.za.stackoverflowquestionapp.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import stackoverflowqapp.ac.za.stackoverflowquestionapp.Classes.Question;
import stackoverflowqapp.ac.za.stackoverflowquestionapp.Helpers.Adapter.QuestionsAdapter;
import stackoverflowqapp.ac.za.stackoverflowquestionapp.Helpers.Background.QuestionAsyncTask;
import stackoverflowqapp.ac.za.stackoverflowquestionapp.Helpers.InternetChecker;
import stackoverflowqapp.ac.za.stackoverflowquestionapp.Interface.BackgroundCallback;
import stackoverflowqapp.ac.za.stackoverflowquestionapp.R;

public class MainActivity extends AppCompatActivity implements BackgroundCallback, View.OnTouchListener {
    private TextView tvTap,tvTagged;
    private RecyclerView recyclerQuestions;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWidgetById();
        setListener();
        if(InternetChecker.checkInternetConnectivity(this)){
            showProgress();
            buildQuestions();
        }else {
            manipulateVisibility(1);
        }
    }

    /**
     * Method invoked from the onCreate() method
     * The purpose of the method is to find all the widget on the activity by ID
     */
    public void getWidgetById() {
        recyclerQuestions = (RecyclerView)findViewById(R.id.recyclerQuestions);
        tvTap = (TextView)findViewById(R.id.tvTap);
        tvTagged = (TextView)findViewById(R.id.tvTagged);
    }

    /**
     * Method invoked from the onCreate method()
     * The purpose the method is to set the text touch listener
     */
    private void setListener() {
        tvTap.setOnTouchListener(this);
    }

    /**
     * Progress dialog - show when loading the questions
     */
    private void showProgress(){
        progressDialog = ProgressDialog.show(this,getString(R.string.please_wait),getString(R.string.loading));
    }


    /**
     * Method invoked from the onCreate() and the OnTouch() after checking internet connectivity
     * The purpose of the method is to build the questions and show to the user
     */
    private void buildQuestions(){
        String url = buildUrl();
        QuestionAsyncTask qAT = new QuestionAsyncTask(MainActivity.this);
        qAT.execute(url);
    }

    /**
     * The purpose of the method is to create URL for the questions in order to get the questions form the stackoverflow webservice
     * @return - String value of the url
     */
    private String buildUrl(){
        StringBuilder builder = new StringBuilder("https://api.stackexchange.com/2.2/questions?");
        builder.append("order=desc");
        builder.append("&sort=activity");
        builder.append("&tagged=Android");
        builder.append("&site=stackoverflow");
        return builder.toString();
    }

    /**
     * Method to manipulate the display on the screen
     * If no internet connectivity - a textview placeholder will be place else questions will be shown
     * @param decider
     */
    private void manipulateVisibility(int decider){
        switch (decider){
            case 0 : //Hide internet connectivity error message
                tvTap.setVisibility(View.GONE);
                recyclerQuestions.setVisibility(View.VISIBLE);
                tvTagged.setVisibility(View.VISIBLE);
                break;
            case 1: //Show Internet connectivity error message
                tvTap.setVisibility(View.VISIBLE);
                recyclerQuestions.setVisibility(View.GONE);
                tvTagged.setVisibility(View.GONE);
                break;
        }

    }

    /**
     * Method invoked from the onPostExecute of the QuestionAsyncTask
     * The purpose of the method is to provide callback from the background thread to the UI with the retrieved data
     * @param questionList - List of Questions
     */
    @Override
    public void isDownloadedCallback(List<Question> questionList) {
        if(progressDialog!= null)progressDialog.dismiss();
        manipulateVisibility(0);
        QuestionsAdapter questionAdapter = new QuestionsAdapter(this,questionList);
        recyclerQuestions.setAdapter(questionAdapter);
        recyclerQuestions.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    /**
     * Dialog shown - When tapping the no network error message to switch on your connection
     * @return
     */
    private Dialog internetIntentDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.strError));
        builder.setMessage(getString(R.string.strInternetRequired));
        builder.setPositiveButton(getString(R.string.strEnable),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        builder.setNegativeButton(getString(R.string.strCancel),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.tvTap:
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(InternetChecker.checkInternetConnectivity(this)){
                        manipulateVisibility(0);
                        showProgress();
                        buildQuestions();
                    }else {
                        internetIntentDialog().show();
                        manipulateVisibility(1);
                    }
                }
                return true;
            default:
                return false;
        }
    }
}