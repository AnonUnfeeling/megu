package first.ua.megu.receiver;

import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URL;

public class UpdataTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return link;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
