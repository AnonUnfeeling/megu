package first.ua.megu.menu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import first.ua.megu.R;
import first.ua.megu.viewsite.ViewSite;
import first.ua.megu.file.WorkingWithFile;

public class MenuActivity extends Activity{

    public static final int KEY_FOR_HOME_PAGE=1;
    public static final int KEY_FOR_APPLICANT=2;
    public static final int KEY_FOR_EDUCATIONAL=3;
    public static final int KEY_FOR_DEPARTAMENTS=4;
    public static final int KEY_FOR_CONTACT=5;
    public static final int KEY_FOR_SCIENTIFIC_WORK=6;
    public static final int KEY_FOR_ABOUT_AS=7;
    private ListView listView;
    private WorkingWithFile workingWithFile = new WorkingWithFile();
    private String pathForTask = "Task";
    private String url=null;
    private String pathForSection = "Section";
    private String urlForDenne = "http://lotyuk.ukrwest.net/denne-viddilennya.html";
    private String urlForZaochne = "http://lotyuk.ukrwest.net/zaochne-viddilennya.html";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        listView = (ListView) findViewById(R.id.listView);
        viewMenu();
    }

    void viewMenu() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mainMenu,R.layout.list_style);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        createNewViewSite(KEY_FOR_HOME_PAGE);
                        break;
                    case 1:
                        if (workingWithFile.readFile(pathForSection).equals("denne")) {
                                new UpdataTask().execute(urlForDenne);
                                url = workingWithFile.readFile(pathForTask);
                                if (url != null) {
                                    showTask(url);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Розклад відсутній", Toast.LENGTH_LONG).show();
                                }
                            } else if (workingWithFile.readFile(pathForSection).equals("zaochne")) {
                            new UpdataTask().execute(urlForZaochne);
                            url = workingWithFile.readFile(pathForTask);
                            if (url != null) {
                                showTask(url);
                            } else {
                                Toast.makeText(getApplicationContext(), "Розклад відсутній", Toast.LENGTH_LONG).show();
                            }
                        }
                        break;
                    case 2:
                        createNewViewSite(KEY_FOR_APPLICANT);
                        break;
                    case 3:
                        createNewViewSite(KEY_FOR_EDUCATIONAL);
                        break;
                    case 4:
                        createNewViewSite(KEY_FOR_DEPARTAMENTS);
                        break;
                    case 5:
                        createNewViewSite(KEY_FOR_CONTACT);
                        break;
                    case 6:
                        createNewViewSite(KEY_FOR_SCIENTIFIC_WORK);
                        break;
                    case 7:
                        createNewViewSite(KEY_FOR_ABOUT_AS);
                        break;
                }
            }
        });
    }

    public void showTask(String url){
        Uri address = Uri.parse("http://docs.google.com/gview?embedded=true&url=http://lotyuk.ukrwest.net" + url);
        Intent openLink = new Intent(Intent.ACTION_VIEW, address);
        startActivity(openLink);
    }

    public void createNewViewSite(int key){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),ViewSite.class);
        intent.setFlags(key);
        startActivity(intent);
    }
}
