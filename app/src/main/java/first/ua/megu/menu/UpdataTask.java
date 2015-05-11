package first.ua.megu.menu;

import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URL;
import first.ua.megu.file.WorkingWithFile;

public class UpdataTask extends AsyncTask<String, Void, String> {

    private WorkingWithFile workingWithFile = new WorkingWithFile();
    private String pathForTask="Task";
    private int ID=0;

    public UpdataTask(){}

    public UpdataTask(int ID){
        this.ID=ID;
    }

    @Override
    public String doInBackground(String... urls) {
        String link = null;
            try {
                URL url = new URL(urls[0]);
                    Document doc = Jsoup.connect(url.toString()).get();
                    Elements links = doc.select("a");
                    for (Element lnk : links) {
                        String str = lnk.attr("href");
                        if (str.contains(".pdf")) {
                            link = str;
                        }
                    }

                    if (link != null&&ID==0) {
                        workingWithFile.writeFile(pathForTask, link);
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }

        return link;
    }
}


