package phoenix.com.kbeauty.net;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by phoenix on 2016/11/25.
 *
 * @Package com.farmlink.ps.engine.netLayer
 * @Description:
 */
public class LogUtilHelper {
    public static boolean isEmpty(String line) {

        return TextUtils.isEmpty(line) || line.equals("\n") || line.equals("\t") || TextUtils.isEmpty(line.trim());

    }



    public static void printLine(String tag, boolean isTop) {

        if (isTop) {

            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");

        } else {

            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");

        }

    }



    public static void printDivider(String tag) {

        Log.d(tag, "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");



    }
}
