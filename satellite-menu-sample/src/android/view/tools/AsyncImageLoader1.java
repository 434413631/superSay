package android.view.tools;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class AsyncImageLoader1 {
    private HashMap<String, SoftReference<Drawable>> imageCache;
    public AsyncImageLoader1() {
        imageCache = new HashMap<String, SoftReference<Drawable>>();
    }
    
    public Drawable loadDrawable(final String imageUrl,final ImageView imageView, final ImageCallback imageCallback){
        if (imageCache.containsKey(imageUrl)) {
            SoftReference<Drawable> softReference = imageCache.get(imageUrl);
            Drawable drawable = softReference.get();
            if (drawable != null) {
                return drawable;
            }
        }
        final Handler handler = new Handler() {
            public void handleMessage(Message message) {
                imageCallback.imageLoaded((Drawable) message.obj, imageView,imageUrl);
            }
        };
        new Thread() {
            @Override
            public void run() {
                Drawable drawable = loadImageFromUrl(imageUrl);
                imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
                Message message = handler.obtainMessage(0, drawable);
                handler.sendMessage(message);
            }
        }.start();
        return null;
    }
    public Drawable loadDrawable2(final String imageUrl,final RelativeLayout rl, final ImageCallback2 imageCallback){
        if (imageCache.containsKey(imageUrl)) {
            SoftReference<Drawable> softReference = imageCache.get(imageUrl);
            Drawable drawable = softReference.get();
            if (drawable != null) {
                return drawable;
            }
        }
        final Handler handler = new Handler() {
            public void handleMessage(Message message) {
                imageCallback.imageLoaded((Drawable) message.obj, rl,imageUrl);
            }
        };
        new Thread() {
            @Override
            public void run() {
                Drawable drawable = loadImageFromUrl(imageUrl);
                imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
                Message message = handler.obtainMessage(0, drawable);
                handler.sendMessage(message);
            }
        }.start();
        return null;
    }
    public  Drawable loadImageFromUrl(String url) {
        URL m;
        InputStream i = null;
        Drawable d=null;
        try {
            m = new URL(url);
            i = (InputStream) m.getContent();
            try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            d = Drawable.createFromStream(i, "src");
            
           /* BitmapFactory.Options opts = new BitmapFactory.Options(); 
            opts.inSampleSize = 4;
            Bitmap bitmap = BitmapFactory.decodeStream(i, null, opts); 
            d =new BitmapDrawable(bitmap);*/
           // bitmap.recycle();



            
            
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	try {
				i.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }       
        return d;
    }
    public interface ImageCallback2 {
        public void imageLoaded(Drawable imageDrawable, RelativeLayout rl, String imageUrl);
    }
    public interface ImageCallback {
        public void imageLoaded(Drawable imageDrawable, ImageView imageView, String imageUrl);
    }


}
