package first.ua.megu.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Bundle;
import android.widget.Toast;
import java.io.File;
import first.ua.megu.R;
import first.ua.megu.file.WorkingWithFile;
import first.ua.megu.menu.MenuActivity;
import first.ua.megu.menu.ParsingLinkForTask;
import first.ua.megu.receiver.NotificationReceiver;

public class MainActivity extends Activity {

    private WorkingWithFile workingWithFile = new WorkingWithFile();
    private String pathForSection = "Section";
    private String urlForDenne = "http://lotyuk.ukrwest.net/denne-viddilennya.html";
    private String urlForZaochne = "http://lotyuk.ukrwest.net/zaochne-viddilennya.html";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context=getApplication();

        File file = new File(Environment.getExternalStorageDirectory().toString() + File.separator + pathForSection);
        if (file.isFile()) {
            finish();
            startNewActivity();
        } else {
            showDialog(0);
        }
    }

    void startNewActivity(){
        Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
        startActivity(intent);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setItems(getResources().getStringArray(R.array.section), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if(item==0){
                            if(isOnline()==true) {
                                workingWithFile.writeFile(pathForSection,"denne");
                                new ParsingLinkForTask().execute(urlForDenne);
                                context.sendBroadcast(new Intent(context, NotificationReceiver.class));
                                finish();
                                startNewActivity();
                            }else {
                                noOnline();
                            }
                        }else {
                            if(isOnline()==true) {
                                workingWithFile.writeFile(pathForSection,"zaochne");
                                new ParsingLinkForTask().execute(urlForZaochne);
                                context.sendBroadcast(new Intent(context, NotificationReceiver.class));
                                finish();
                                startNewActivity();
                            }else {
                                noOnline();
                            }
                        }
                    }
                });
                builder.setCancelable(false);
                return builder.create();
            default:
                return null;
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isConnected = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting();
        if (!isConnected)
            return true;
        else return false;
    }

    public void noOnline(){
        Toast.makeText(getApplicationContext(), "Відсутній доступ до мережі", Toast.LENGTH_SHORT).show();
    }
}
