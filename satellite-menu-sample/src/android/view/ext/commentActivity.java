package android.view.ext;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.tools.UploadUtil;
import android.widget.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class commentActivity extends Activity implements View.OnClickListener,UploadUtil.OnUploadProcessListener {
    private EditText littleSay;
    private ImageView img;
    private Button ding;
    private Button cai;
    private Button summit;
    private Button selectImg;
    private static final String TAG = "uploadImage";
    private Button uploadPic;
    /**
	 * 去上传文件
	 */
	protected static final int TO_UPLOAD_FILE = 1;
	/**
	 * 上传文件响应
	 */
	protected static final int UPLOAD_FILE_DONE = 2;  //
	/**
	 * 选择文件
	 */
	public static final int TO_SELECT_PHOTO = 3;
	/**
	 * 上传初始化
	 */
	private static final int UPLOAD_INIT_PROCESS = 4;
	/**
	 * 上传中
	 */
	private static final int UPLOAD_IN_PROCESS = 5;
	/***
	 * 这里的这个URL是我服务器的javaEE环境URL
	 */
    private static String requestURL = "http://192.168.10.160:8080/fileUpload/p/file!upload";
    private int countDing = 0;//顶的次数
    private int countCai = 0;//踩的次数
    private String title = null;
    private String picPath = null;
    private ProgressBar progressBar;
    private TextView uploadImageResult;
    private Handler handler = new Handler(){
    		@Override
    		public void handleMessage(Message msg) {
    			switch (msg.what) {
    			case TO_UPLOAD_FILE:
    				toUploadFile();
    				break;

    			case UPLOAD_INIT_PROCESS:
    				progressBar.setMax(msg.arg1);
    				break;
    			case UPLOAD_IN_PROCESS:
    				progressBar.setProgress(msg.arg1);
    				break;
    			case UPLOAD_FILE_DONE:
    				String result = "响应码："+msg.arg1+"\n响应信息："+msg.obj+"\n耗时："+UploadUtil.getRequestTime()+"秒";
    				uploadImageResult.setText(result);
    				break;
    			default:
    				break;
    			}
    			super.handleMessage(msg);
    		}

    	};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.comment);
        initComponents();
    }

    private void SummitData(String littleSayText, String tuanName) {
        new Thread(new ThreadSummit(littleSayText, tuanName)).start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {
            picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
            Log.i(TAG, "最终选择的图片=" + picPath);
            Bitmap bm = BitmapFactory.decodeFile(picPath);
            img.setImageBitmap(bm);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initComponents() {   //初始化组件
        uploadImageResult=(TextView)findViewById(R.id.uploadImageResult);
        uploadPic=(Button)findViewById(R.id.uploadImag);
        selectImg = (Button) findViewById(R.id.selectImg);
        img = (ImageView) findViewById(R.id.imagefor);
        ding = (Button) findViewById(R.id.buttonding);
        cai = (Button) findViewById(R.id.buttoncai);
        summit = (Button) findViewById(R.id.summit);
        littleSay = (EditText) findViewById(R.id.littleEdit2);//简评
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.summit:
                title=getIntent().getStringExtra("title");
                String littleSayText = littleSay.getText().toString();
                SummitData(littleSayText, title);
            case R.id.selectImg:
                Intent intent = new Intent(this, SelectPicActivity.class);
                startActivityForResult(intent, TO_SELECT_PHOTO);
                break;
                case R.id.uploadImag:
        			if(picPath!=null)
        			{
        				handler.sendEmptyMessage(TO_UPLOAD_FILE);
        			}else{
        				Toast.makeText(this, "上传的文件路径出错", Toast.LENGTH_LONG).show();
        			}
        			break;
            default:
                break;
        }
    }

    @Override
    public void onUploadDone(int responseCode, String message) {
        Message msg = Message.obtain();
        		msg.what = UPLOAD_FILE_DONE;
        		msg.arg1 = responseCode;
        		msg.obj = message;
        		handler.sendMessage(msg);
    }

    @Override
    public void onUploadProcess(int uploadSize) {
        Message msg = Message.obtain();
        		msg.what = UPLOAD_IN_PROCESS;
        		msg.arg1 = uploadSize;
        		handler.sendMessage(msg );
    }

    @Override
    public void initUpload(int fileSize) {
        Message msg = Message.obtain();
        		msg.what = UPLOAD_INIT_PROCESS;
        		msg.arg1 = fileSize;
        		handler.sendMessage(msg );
    }

    private void toUploadFile()
    	{
    		String fileKey = "pic";
    		UploadUtil uploadUtil = UploadUtil.getInstance();;
    		uploadUtil.setOnUploadProcessListener(this);  //设置监听器监听上传状态

    		Map<String, String> params = new HashMap<String, String>();
    		params.put("orderId", "11111");
    		uploadUtil.uploadFile( picPath,fileKey, requestURL,params);
    	}

    class ThreadSummit extends Thread {    //联网提交线程
        private String say;
        private String tuanname;

        public ThreadSummit(String littleSayText, String tuanName) {
            this.say = littleSayText;
            this.tuanname = tuanName;
        }

        public void run() {
            Message message = new Message();
            message.what = 1;
            Socket socket = null;
            DataOutputStream dataout = null;
            DataInputStream datain = null;
            try {
                socket = new Socket("10.25.153.128", 8083);
                dataout = new DataOutputStream(socket.getOutputStream());
                datain = new DataInputStream(socket.getInputStream());
                dataout.writeUTF("<#SUMMIT#>" + tuanname + "|" + say);
                //String msg = datain.readUTF();//接收服务器发来的消息
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
