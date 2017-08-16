package stackoverflowqapp.ac.za.stackoverflowquestionapp.Helpers;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Thato on 2017/08/10.
 * The purpose of this class is to read the URL with the usage of the Third Party Library OkHttp
 */

public class DownloadUrl {

    public String readURL(String url) throws IOException{
        String data = "-1";
        OkHttpClient client = new OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build();

        Request request = new Request.Builder().get().url(url).build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if(response.isSuccessful()){
            data = response.body().string();
        }
        response.body().close();

        return data;
    }

}
