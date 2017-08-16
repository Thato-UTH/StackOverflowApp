package stackoverflowqapp.ac.za.stackoverflowquestionapp.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Thato on 2017/08/10.
 * Special class - to check if the is internet connectivity on your device.
 */

public class InternetChecker {
    public static boolean checkInternetConnectivity(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
