package stackoverflowqapp.ac.za.stackoverflowquestionapp.Helpers.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.List;

import stackoverflowqapp.ac.za.stackoverflowquestionapp.Classes.Question;
import stackoverflowqapp.ac.za.stackoverflowquestionapp.R;

/**
 * Created by Thato on 2017/08/10.
 * Custom Adapter - To inflate the layout created (R.layout.question_layout_item)
 * And set the list of question retrieved from the webservice and also manipulate relevant data
 * such as converting the Unix Time and Show relevant background color to questions that are answered and also
 * building the tags dynamically on the layout.
 */

public class QuestionsAdapter extends
        RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>{
    private List<Question> questionList;
    private Context context;
    private LayoutInflater inflater;

    public QuestionsAdapter(Context context,List<Question> questionList){
        this.context = context;
        this.questionList = questionList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.question_layout_item,parent,false);
        QuestionsViewHolder questionsViewHolder = new QuestionsViewHolder(view);
        return questionsViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(QuestionsViewHolder holder, int position) {
        Question singleQuestion = questionList.get(position);
        int answerCount = singleQuestion.getAnswer_count();
        if(answerCount > 0) {
            holder.tvQAnswersCount.setBackground(context.getDrawable(R.drawable.circular_textview_green));
        }else {
            holder.tvQAnswersCount.setBackground(context.getDrawable(R.drawable.circular_textview_default));
        }
        holder.tvQAnswersCount.setText(""+answerCount);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            holder.tvQuestionTitle.setText(Html.fromHtml(singleQuestion.getQuestionTitle(),Html.FROM_HTML_MODE_COMPACT));
        }else {
            holder.tvQuestionTitle.setText(Html.fromHtml(singleQuestion.getQuestionTitle()));
        }



        /**
         * the line below is a source from the following link
         * From link : https://stackoverflow.com/questions/12031333/converting-unix-timestamp-to-string-with-joda-time/12031383
         */
        DateTime postedDateTime = new DateTime(Long.parseLong(singleQuestion.getDatePosted()) * 1000L);
        DateTime now = new DateTime();
        Period period = new Period(postedDateTime,now);
        PeriodFormatter format = new PeriodFormatterBuilder()
                .appendHours().appendSuffix(" hours ago\n")
                .printZeroNever()
                .toFormatter();
        String hours = format.print(period);
        if (hours.equals("")){
            format = new PeriodFormatterBuilder()
                    .appendSeconds().appendSuffix(" seconds ago\n")
                    .printZeroNever()
                    .toFormatter();

            hours = format.print(period);
        }
        holder.tvPostDateTime.setText(hours);
        holder.questionContainer.removeAllViewsInLayout();
        holder.mainContainer.removeAllViewsInLayout();

        List<String> tags = singleQuestion.getTags();

        for(int x = 0; x < tags.size();x++){
            TextView tvTags = new TextView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(5,0,5,0);
            tvTags.setPadding(10,0,10,0);
            tvTags.setMaxLines(1);
            tvTags.setLayoutParams(params);
            String tag = tags.get(x);
            tvTags.setBackground(context.getDrawable(R.drawable.rectangle_textview));
            int length = tag.length();
            if(length >= 10)
                tag = tag.substring(0,length-3) + "...";
            tvTags.setText(tag);
            holder.tagsContainer.addView(tvTags);
        }



        holder.mainContainer.addView(holder.answerContainer);
        holder.questionContainer.addView(holder.tvQuestionTitle);
        holder.questionContainer.addView(holder.tagsContainer);
        holder.questionContainer.addView(holder.tvPostDateTime);
        holder.mainContainer.addView(holder.questionContainer);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder {
        TextView tvQAnswersCount,tvQuestionTitle,tvPostDateTime;
        LinearLayout tagsContainer,mainContainer,questionContainer,answerContainer;
        public QuestionsViewHolder(View itemView) {
            super(itemView);
            tvQAnswersCount = (TextView)itemView.findViewById(R.id.tvQAnswersCount);
            tvQuestionTitle = (TextView)itemView.findViewById(R.id.tvQuestionTitle);
            tvPostDateTime = (TextView)itemView.findViewById(R.id.tvPostDateTime);
            tagsContainer = (LinearLayout)itemView.findViewById(R.id.tagsContainer);
            mainContainer = (LinearLayout)itemView.findViewById(R.id.mainContainer);
            questionContainer = (LinearLayout)itemView.findViewById(R.id.questionContainer);
            answerContainer = (LinearLayout)itemView.findViewById(R.id.answerContainer);
        }
    }
}
