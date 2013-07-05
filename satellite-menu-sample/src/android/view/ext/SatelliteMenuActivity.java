package android.view.ext;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.adapters.GridAdapter;
import android.view.entities.message;
import android.view.ext.SatelliteMenu.SateliteClickedListener;
import android.widget.GridView;

public class SatelliteMenuActivity extends Activity {
    private GridView gridView;
    private GridAdapter gridAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
        gridView.setAdapter(gridAdapter);
        SatelliteMenu menu = (SatelliteMenu) findViewById(R.id.menu);
        
//		  Set from XML, possible to programmatically set        
//        float distance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 170, getResources().getDisplayMetrics());
//        menu.setSatelliteDistance((int) distance);
//        menu.setExpandDuration(500);
//        menu.setCloseItemsOnClick(false);
//        menu.setTotalSpacingDegree(60);
        
        List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
        items.add(new SatelliteMenuItem(4, R.drawable.ic_1));
        items.add(new SatelliteMenuItem(4, R.drawable.ic_3));
        items.add(new SatelliteMenuItem(4, R.drawable.ic_4));
       // items.add(new SatelliteMenuItem(3, R.drawable.ic_5));
        //items.add(new SatelliteMenuItem(2, R.drawable.ic_6));
        items.add(new SatelliteMenuItem(1, R.drawable.ic_2));
//        items.add(new SatelliteMenuItem(5, R.drawable.sat_item));
        menu.addItems(items);        
        
        menu.setOnItemClickedListener(new SateliteClickedListener() {
			
			public void eventOccured(int id) {
				Log.i("sat", "Clicked on " + id);
			}
		});
    }

    private void init() {
        gridView=(GridView)findViewById(R.id.gridview);
        List<message> msg=new ArrayList<message>();
        message message1=new message("test","test","test","test");
        message message2=new message("test","test","test","test");
        message message3=new message("test","test","test","test");
        message message4=new message("test","test","test","test");
        message message5=new message("test","test","test","test");
        message message6=new message("test","test","test","test");

        msg.add(message1);
        msg.add(message2);
        msg.add(message3);
        msg.add(message4);
        msg.add(message5);
        msg.add(message6);
        gridAdapter=new GridAdapter(this, msg);

    }
}