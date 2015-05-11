package first.ua.megu.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import java.util.concurrent.ExecutionException;
import first.ua.megu.connectstatus.ConnectStatus;
import first.ua.megu.R;
import first.ua.megu.file.WorkingWithFile;
import first.ua.megu.menu.UpdataTask;

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
    private UpdataTask updataTask = new UpdataTask(1);
    ConnectStatus connectStatus;

    @Override
    public void onReceive(Context context, Intent intent) {
        connectStatus = new ConnectStatus();
        if (!connectStatus.isOnline(context)) {
            try {
                section = workingWithFile.readFile(pathForSection);
                if (section != null) {
                    if (section.equals("denne") && isNewTask(urlForDenne)) {
                        showNotification(context);
                    } else if (section.equals("zaochne") && isNewTask(urlForZaochne)) {
                        showNotification(context);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean isNewTask(String url){
        berofeLink = workingWithFile.readFile(pathForTask);
        updataTask.execute(url);
        try {
            afterLink = updataTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (!afterLink.equals(berofeLink)) {
            workingWithFile.writeFile(pathForTask, afterLink);
            return true;
        } else {
            return false;
        }
    }

    public void showNotification(Context context) {
        notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notifyDetails = new Notification(R.drawable.flag,
                "Мегу", System.currentTimeMillis());

        PendingIntent pendingIntentIntent = PendingIntent.getActivity(context, 0,
                new Intent(Intent.ACTION_VIEW, Uri.parse("http://docs.google.com/gview?embedded=true&url=http://lotyuk.ukrwest.net" + afterLink)), 0);

        notifyDetails.setLatestEventInfo(context, "Розклад",
                "Розклад було обновлено", pendingIntentIntent);

        notifyDetails.flags = notifyDetails.flags | Notification.FLAG_SHOW_LIGHTS;
        notifyDetails.flags |= Notification.FLAG_AUTO_CANCEL;
        notifyDetails.flags |= Notification.DEFAULT_SOUND;

        notificationManager.notify(NOTFICATION_ID, notifyDetails);
    }
}


