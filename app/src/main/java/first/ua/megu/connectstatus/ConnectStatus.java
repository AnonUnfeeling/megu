package first.ua.megu.connectstatus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ConnectStatus{
    public boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isConnected = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting();
        return isConnected;
    }

    public void noOnline(Context context){
        Toast.makeText(context.getApplicationContext(), "Відсутній доступ до мережі", Toast.LENGTH_SHORT).show();
    }
}
