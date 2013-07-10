package android.view.ext;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-7-10
 * Time: 下午9:01
 * To change this template use File | Settings | File Templates.
 */
public class commentActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);
    }
}
