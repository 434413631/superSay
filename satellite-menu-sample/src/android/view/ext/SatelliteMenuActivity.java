package android.view.ext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.adapters.GridAdapter;
import android.view.entities.message;
import android.view.ext.SatelliteMenu.SateliteClickedListener;
import android.view.tools.jxHtml;
import android.widget.GridView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SatelliteMenuActivity extends Activity {
    private GridView gridView;
    private GridAdapter gridAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    gridView = (GridView) findViewById(R.id.gridview);
                    List<message> list = (List<message>) msg.obj;
                    gridAdapter = new GridAdapter(SatelliteMenuActivity.this, list);
                    gridView.setAdapter(gridAdapter);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        new Thread(new getMessageThread()).start();
        SatelliteMenu menu = (SatelliteMenu) findViewById(R.id.menu);
        List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
        items.add(new SatelliteMenuItem(1, R.drawable.ic_1));
        items.add(new SatelliteMenuItem(3, R.drawable.ic_3));
        items.add(new SatelliteMenuItem(4, R.drawable.ic_4));
        items.add(new SatelliteMenuItem(2, R.drawable.ic_2));
        menu.addItems(items);

        menu.setOnItemClickedListener(new SateliteClickedListener() {
            public void eventOccured(int id) {
                Log.i("sat", "Clicked on " + id);
            }
        });
    }


    class getMessageThread extends Thread {
        public void run() {
            try {
                Message msg = Message.obtain();
                msg.what = 1;
                jxHtml jx = new jxHtml();
                msg.obj = jx.getMessage();
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void buy(String buyUrl) {
        Intent intent = new Intent(SatelliteMenuActivity.this, webViewActivity.class);
        intent.putExtra("buyUrl", buyUrl);
        startActivity(intent);
    }

}