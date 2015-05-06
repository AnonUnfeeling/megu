package first.ua.megu.file;

import android.os.Environment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class WorkingWithFile {

    public void writeFile(String path, String data){
        try {
            File file = new File(Environment.getExternalStorageDirectory().toString() + File.separator + path);
            if (file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + File.separator + path, false);

            fos.write(data.getBytes());
            fos.flush();
            fos.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public String readFile(String path) {
        String inputLine;
        String data = null;

        try {
            File file = new File(Environment.getExternalStorageDirectory().toString() + File.separator + path);
            if (file.exists()) {
                file.createNewFile();
            }
            BufferedReader br = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().toString() + File.separator + path));

            while ((inputLine = br.readLine()) != null) {
                data = inputLine;
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
