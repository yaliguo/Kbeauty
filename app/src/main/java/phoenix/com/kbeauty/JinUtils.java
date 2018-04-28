package phoenix.com.kbeauty;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by guoyali
 * <p>
 * on 2018/4/27.
 * <p>
 * use
 */
public class JinUtils {

    public static void createText(Context context){
        String FILENAME = "hello_file.txt";
        String string = "hello world!";

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(string.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void addText(Context context){
        String FILENAME = "hello_file.txt";
        String string = "Ha ha  Ha ha";

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_APPEND);
            fos.write(string.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static native String readText(String path);
}
