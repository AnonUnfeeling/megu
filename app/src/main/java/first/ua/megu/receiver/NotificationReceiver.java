package first.ua.megu.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Contacts;
import android.util.Log;

import java.util.concurrent.ExecutionException;

import first.ua.megu.R;
import first.ua.megu.file.WorkingWithFile;

public class NotificationReceiver extends BroadcastReceiver {

    private NotificationManager notificationManager;
    private int NOTFICATION_ID = 101;
    private WorkingWithFile workingWithFile = new WorkingWithFile();
    private String pathForTask = "Task";
    private String section;
    private String pathForSection = "Section";
    private String urlForDenne = "http://lotyuk.ukrwest.net/denne-viddilennya.html";
    private String urlForZaochne = "http://lotyuk.ukrwest.net/zaochne-viddilennya.html";
    private String berofeLink = "";
    private String afterLink = "";
    private UpdataTask updataTask = new UpdataTask();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(isOnline(context)==true){
             section = workingWithFile.readFile(pathForSection);
            if(section!=null) {
                if (section.equals("denne")) {
                    berofeLink = workingWithFile.readFile(pathForTask);
                    updataTask.execute(urlForDenne);
                    try {
                        afterLink = updataTask.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    if (!afterLink.equals(berofeLink)) {
                        workingWithFile.writeFile(pathForTask, afterLink);
                        showNotification(context);
                    } else {
                        Log.d("Log", "no updata");
                    }
                } else if (section.equals("zaochne")) {
                    berofeLink = workingWithFile.readFile(pathForTask);
                    updataTask.execute(urlForZaochne);
                    try {
                        afterLink = updataTask.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    if (!afterLink.equals(berofeLink)) {
                        workingWithFile.writeFile(pathForTask, afterLink);
                        showNotification(context);
                    } else {
                        Log.d("Log", "no updata");
                    }
                }
            }
        }
    }

    public void showNotification(Context context) {
        notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notifyDetails = new Notification(R.drawable.flag,
                "Мегу", System.currentTimeMillis());

        PendingIntent pendingIntentIntent = PendingIntent.getActivity(context, 0,
                new Intent(Intent.ACTION_VIEW, Contacts.People.CONTENT_URI), 0);

        notifyDetails.setLatestEventInfo(context, "Розклад",
                "Розклад було обновлено", pendingIntentIntent);

        notifyDetails.flags = notifyDetails.flags | Notification.FLAG_SHOW_LIGHTS;
        notifyDetails.flags |= Notification.FLAG_AUTO_CANCEL;
        notifyDetails.flags |= Notification.DEFAULT_SOUND;

        notificationManager.notify(NOTFICATION_ID, notifyDetails);
    }

    private boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isConnected = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting();
        if (!isConnected)
            return true;
        else return false;
    }
}
