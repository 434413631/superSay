package android.view.ext;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class commentActivity extends Activity {
    private EditText littleSay;
    private ImageView img;
    private Button ding;
    private Button cai;
    private Button summit;
    private int countDing=0;//顶的次数
    private int countCai=0;//踩的次数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.comment);
        final String title=getIntent().getStringExtra("title");
        initComponents();
        summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String littleSayText=littleSay.getText().toString();
                SummitData(littleSayText,title);
            }
        });
        ding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先查看数据库，对于某个团购，用户只能顶一次
                   countDing++;
            }
        });
        cai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先查看数据库，对于某个团购，用户只能踩一次
                   countCai++;
            }
        });
    }

    private void SummitData(String littleSayText, String tuanName) {
           new Thread(new ThreadSummit(littleSayText,tuanName)).start();

    }


    private void initComponents() {   //初始化组件
        littleSay=(EditText)findViewById(R.id.littleEdit);//简评
        img=(ImageView)findViewById(R.id.imagefor);
        ding=(Button)findViewById(R.id.buttonding);
        cai=(Button)findViewById(R.id.buttoncai);
        summit=(Button)findViewById(R.id.summit);
    }

    class ThreadSummit extends Thread{    //联网提交线程
        private String say;
        private String tuanname;

       public ThreadSummit(String littleSayText,String tuanName) {
           this.say=littleSayText;
           this.tuanname=tuanName;
        }
        public void run(){
            Message message=new Message();
            message.what=1;
            Socket socket = null;
            DataOutputStream dataout = null;
            DataInputStream datain = null;
            try {
                socket=new Socket("192.168.1.1",8083);
                dataout = new DataOutputStream(socket.getOutputStream());
                datain = new DataInputStream(socket.getInputStream());
                dataout.writeUTF("<#SUMMIT#>"+say+"|"+tuanname);
                //String msg = datain.readUTF();//接收服务器发来的消息
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
