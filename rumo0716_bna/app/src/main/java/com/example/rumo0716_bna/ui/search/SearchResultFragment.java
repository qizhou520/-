package com.example.rumo0716_bna.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.rumo0716_bna.R;
import com.example.rumo0716_bna.ui.home.HomeFragment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;


public class SearchResultFragment extends Fragment {

    TextView editSearch_02;
    String str[];//GetFake
    String str1[];//GetCorrect
    String str2[];//GetU
    String sw[] ;
    String us;//儲存使用者輸入
    String usarticle;
    String uscontext;
    String box3 = ""; // 宣告存放用字串
    Boolean cofa ;//推薦文章為假還是小心
    int cid ;//
    String love;
    String watch;
    String source;
    String key1;
    String key2;
    String key3;
    String f1;
    String f2;
    String f3;
    int usinput = -1;//跟資料庫比對過後，跟Correct完全一樣是0，跟Fake完全一樣是1
    int a = 1000;//有幾%是假的
    private ListView listView;
    private ImageView imageView14;
    private TextView textView8;
    private TextView textView15;
    private ImageView imageView6;
    private ListAdapter listAdapter;
    Button button3;
    //listAdapter = new ArrayAdapter<String>

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_result, container, false);
        editSearch_02 = root.findViewById(R.id.textView4);
        button3 = root.findViewById(R.id.button3);




        editSearch_02.setText(SearchFragment.tex);
        listView = root.findViewById(R.id.listView);
        imageView14 = root.findViewById(R.id.imageView14);
        textView15 = root.findViewById(R.id.textView15);
        textView8 = root.findViewById(R.id.textView8);
        imageView6 = root.findViewById(R.id.imageView6);


        // sw = new String[]{"0", "0", "0"};

        us = SearchFragment.tex + "";
        Thread thread = new Thread(mthread);
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {

        }
        if(sw[0]==null){sw= new String[]{"0", "0", "0"};}
        listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sw);
        //System.out.println("sw="+sw[0]);
        listView.setAdapter(listAdapter);


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                HomeFragment.ju_article = usarticle;
                HomeFragment.ju_context = uscontext;
                HomeFragment.ju_correct_or_fake=cofa;
                HomeFragment.ju_home_text_id=cid;
                HomeFragment.ju_love=love;
                HomeFragment.ju_search=watch;
                HomeFragment.ju_source=source;
                HomeFragment.ju_key1=key1;
                HomeFragment.ju_key2=key2;
                HomeFragment.ju_key3=key3;
                HomeFragment.ju_face1=f1;
                HomeFragment.ju_face2=f2;
                HomeFragment.ju_face3=f3;
                Navigation.findNavController(v).navigate(R.id.action_fragment_search_result_to_fragment_wordpage_result);*/
                Toast.makeText(getContext(),"此功能尚未開放",Toast.LENGTH_SHORT).show();
            }
        });

        //充電圖示-有多假

        int b = -1;
        if (a > 75) {
            b = 0;        }//76~100%
        if (a > 50 && a <= 75) {
            b = 1;
        }//51~75%
        if (a > 25 && a <= 50) {
            b = 2;
        }//26~50%
        if (a > 0 && a <= 25) {
            b = 3;
        }//1~25%
        if (a == 0) {
            b = 4;
        }//0%

        switch (b) {
            case 0:
                imageView14.setImageResource(R.drawable.battery_4);
                break;
            case 1:
                imageView14.setImageResource(R.drawable.battery_3);
                break;
            case 2:
                imageView14.setImageResource(R.drawable.battery_2);
                break;
            case 3:
                imageView14.setImageResource(R.drawable.battery_1);
                break;
            case 4:
                imageView14.setImageResource(R.drawable.battery_0);
                break;
        }


        if (usinput == 0) {
            textView8.setText("真的，放心觀看");

            imageView6.setImageResource(R.drawable.alert);
            imageView14.setImageResource(R.drawable.battery_0);
        } else if (usinput == 1) {
            textView8.setText("這是假消息喔!!!");
            imageView6.setImageResource(R.drawable.wrong);
            imageView14.setImageResource(R.drawable.battery_4);
        } else {
            textView8.setText(a + "%");
            imageView6.setImageResource(R.drawable.main);

        }


        return root;
    }
    /*public void onClick(View root) {

        Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_fragment_wordpage_result);

    }*/

    private Runnable mthread = new Runnable() {
        @Override
        public void run() {
            try {

                //------------------更新資料庫資料開始---------------------
                URL ur3 = new URL("http://192.168.43.17/UpdateU.php");
                HttpURLConnection con2= (HttpURLConnection) ur3.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                con2.setRequestMethod("POST");
                // 設定連線方式為 POST
                con2.setDoOutput(true); // 允許輸出
                con2.setDoInput(true); // 允許讀入
                con2.setUseCaches(false); // 不使用快取
                con2.connect();

                con2.getOutputStream().write(us.getBytes());
                con2.getOutputStream().flush();
                con2.getOutputStream().close();
                int code = con2.getResponseCode();
                System.out.println("code   " + code);
                System.out.println(con2.getResponseCode());
                con2.disconnect();
                //------------------更新資料庫資料結束---------------------
                //------------------運行jieba.py檔開始--------------------
                URL ur4 = new URL("http://192.168.43.17/userjieba.php");
                HttpURLConnection con3= (HttpURLConnection) ur4.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                con3.setRequestMethod("POST");
                // 設定連線方式為 POST
                con3.setDoOutput(true); // 允許輸出
                con3.setDoInput(true); // 允許讀入
                con3.setUseCaches(false); // 不使用快取
                con3.connect();


                int code2 = con3.getResponseCode();
                System.out.println("code   " + code2);
                System.out.println(con3.getResponseCode());
                con3.disconnect();
                //------------------運行jieba.py檔結束--------------------
                URL url = new URL("http://192.168.43.17/GetFake.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(false); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線
                int responseCode =
                        connection.getResponseCode();
                // 建立取得回應的物件
                if (responseCode ==
                        HttpURLConnection.HTTP_OK) {
                    // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                    InputStream inputStream =
                            connection.getInputStream();
                    // 取得輸入串流
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    // 讀取輸入串流的資料
                    String box = ""; // 宣告存放用字串
                    String line = null; // 宣告讀取用的字串
                    while ((line = bufReader.readLine()) != null) {
                        box = box + line;
                    }
                    box=box.replace("[{", "");
                    box=box.replace("}]", "");

                    String s = "\\}\\,\\{";
                    str = box.split(s);
                    inputStream.close(); // 關閉輸入串流
                    //抓取正確資訊
                    URL url1 = new URL("http://192.168.43.17/GetCorrect.php");
                    HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
                    // 建立 Google 比較挺的 HttpURLConnection 物件
                    connection1.setRequestMethod("POST");
                    // 設定連線方式為 POST
                    connection1.setDoOutput(false); // 允許輸出
                    connection1.setDoInput(true); // 允許讀入
                    connection1.setUseCaches(false); // 不使用快取
                    connection1.connect(); // 開始連線
                    int responseCode1 =
                            connection1.getResponseCode();
                    // 建立取得回應的物件
                    if (responseCode1 ==
                            HttpURLConnection.HTTP_OK) {
                        // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                        InputStream inputStream1 =
                                connection1.getInputStream();
                        // 取得輸入串流
                        BufferedReader bufReader1 = new BufferedReader(new InputStreamReader(inputStream1, "utf-8"));
                        // 讀取輸入串流的資料
                        String box1 = ""; // 宣告存放用字串
                        String line1 = null; // 宣告讀取用的字串
                        while ((line1 = bufReader1.readLine()) != null) {
                            box1 = box1 + line1;
                        }

                        box1=box1.replace("["+"{", "");
                        box1=box1.replace("}"+"]", "");
                        String s1 = "\\}\\,\\{";
                        str1 = box1.split(s1);
                        inputStream1.close(); // 關閉輸入串流

                        //抓取正確資訊
                        URL url3 = new URL("http://192.168.43.17/GetU.php");
                        HttpURLConnection connection3 = (HttpURLConnection) url3.openConnection();
                        // 建立 Google 比較挺的 HttpURLConnection 物件
                        connection3.setRequestMethod("POST");
                        // 設定連線方式為 POST
                        connection3.setDoOutput(false); // 允許輸出
                        connection3.setDoInput(true); // 允許讀入
                        connection3.setUseCaches(false); // 不使用快取
                        connection3.connect(); // 開始連線
                        int responseCode3 =
                                connection3.getResponseCode();
                        // 建立取得回應的物件
                        if (responseCode3 ==
                                HttpURLConnection.HTTP_OK) {
                            // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                            InputStream inputStream3 =
                                    connection3.getInputStream();
                            // 取得輸入串流
                            BufferedReader bufReader3 = new BufferedReader(new InputStreamReader(inputStream3, "utf-8"));
                            // 讀取輸入串流的資料

                            String line3 = null; // 宣告讀取用的字串
                            while ((line1 = bufReader3.readLine()) != null) {
                                box3 = box3 + line1;
                            }
                            box3=box3.replace("["+"{", "");
                            box3=box3.replace("}"+"]", "");
                            inputStream3.close(); // 關閉輸入串流
                            box3 =box3.replace("ID", "").replace(":", "").replace("SOURCE", "");
                            box3=box3.replace("CONTENT", "").replace("KEY1", "").replace("KEY2", "").replace("KEY3", "").replace("COLLECT", "").replace("WATCH", "");
                            box3= box3.replace("\"", "");

                        }


                    }
                }
                for (int i = 0; i < str1.length; i++) {//GetFake=0
                    String s1 = str1[i];
                    s1 = s1.replace("[", "").replace("{", "").replace("ID", "").replace(":", "").replace("SOURCE", "");
                    s1 = s1.replace("CONTENT", "").replace("KEY1", "").replace("KEY2", "").replace("KEY3", "").replace("COLLECT", "").replace("WATCH", "");
                    s1 = s1.replace("}", "").replace("]", "").replace("\"", "");
                    String ss1[] = s1.split(",");
                    if (ss1[2].equals(us)) {
                        sw= new String[3];
                        sw[0]=ss1[3];
                        sw[1]=ss1[4];
                        sw[2]=ss1[5];

                        usinput = 0;

                        textView15.setText(ss1[2].substring(0, 10) + "....");
                        cofa=true;
                        usarticle = ss1[2].substring(0, 21);
                        uscontext = ss1[2];
                        //setturn(ss1);
                        break;
                    }
                }//填入CORRECT資料庫中的關鍵字和推薦
                for (int i = 0; i < str.length; i++) {//GetFake=1
                    String s1 = str[i];
                    s1 = s1.replace("[", "").replace("{", "").replace("ID", "").replace(":", "").replace("SOURCE", "");
                    s1 = s1.replace("CONTENT", "").replace("KEY1", "").replace("KEY2", "").replace("KEY3", "").replace("COLLECT", "").replace("WATCH", "");
                    s1 = s1.replace("}", "").replace("]", "").replace("\"", "");
                    String ss1[] = s1.split(",");
                    if (ss1[2].equals(us)) {
                        sw= new String[3];
                        sw[0]=ss1[3];
                        sw[1]=ss1[4];
                        sw[2]=ss1[5];
                        usinput = 1;
                        textView15.setText(ss1[2].substring(0, 10) + "....");
                        cofa=false;
                        usarticle = ss1[2].substring(0, 21);
                        uscontext = ss1[2];
                        //setturn(ss1);
                        break;
                    }
                }//填入FAKE資料庫中的關鍵字和推薦


                if(usinput==-1){
                    String[]ss3=box3.split(",");
                    sw= new String[3];
                    sw[0]=ss3[2];
                    System.out.println("ss3[2]"+ss3[2]);
                    sw[1]=ss3[3];
                    System.out.println("ss3[3]"+ss3[3]);
                    sw[2]=ss3[4];
                    System.out.println("ss3[4]"+ss3[4]);
                    //ss3[1]是使用者輸入
                    textView15.setText(ss3[1].substring(0, 10) + "....");
                    //usarticle = ss3[1].substring(0, 21);
                    //uscontext = ss3[1];
                    deeplearn(ss3[1]);
                    for (int i = 0; i < str1.length; i++) {//GetFake=1
                        String s1 = str1[i];
                        s1 = s1.replace("[", "").replace("{", "").replace("ID", "").replace(":", "").replace("SOURCE", "");
                        s1 = s1.replace("CONTENT", "").replace("KEY1", "").replace("KEY2", "").replace("KEY3", "").replace("COLLECT", "").replace("WATCH", "");
                        s1 = s1.replace("}", "").replace("]", "").replace("\"", "");
                        String ss1[] = s1.split(",");
                        if (ss3[2].equals(ss1[3])||ss3[2].equals(ss1[4])||ss3[2].equals(ss1[5])) {
                            textView15.setText(ss1[2].substring(0, 10) + "....");
                            usarticle = ss1[2].substring(0, 21);
                            uscontext = ss1[2];
                            cofa=true;
                            //setturn(ss1);
                            break;
                        }
                    }//填入CORRECT資料庫中推薦
                    for (int i = 0; i < str.length; i++) {//GetFake=1
                        String s1 = str[i];
                        s1 = s1.replace("[", "").replace("{", "").replace("ID", "").replace(":", "").replace("SOURCE", "");
                        s1 = s1.replace("CONTENT", "").replace("KEY1", "").replace("KEY2", "").replace("KEY3", "").replace("COLLECT", "").replace("WATCH", "");
                        s1 = s1.replace("}", "").replace("]", "").replace("\"", "");
                        String ss1[] = s1.split(",");
                        if (ss3[2].equals(ss1[3])||ss3[2].equals(ss1[4])||ss3[2].equals(ss1[5])) {
                            textView15.setText(ss1[2].substring(0, 10) + "....");
                            usarticle = ss1[2].substring(0, 21);
                            uscontext = ss1[2];
                            cofa=false;
                            //setturn(ss1);
                            break;
                        }
                    }//填入FAKE資料庫中推薦

                }//填入使用者輸入的關鍵字的關鍵字和最相關推薦
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    };
    public void deeplearn(String s){
        String address = "192.168.43.17";
        int port = 8080;
        InetSocketAddress isa;
        Socket client = new Socket();
        isa = new InetSocketAddress(address, port);
        try {
            client.connect(isa);
            BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
            String t =s;
            //a=a.replace("\\n","").replace(" ","");
            byte b[] = t.getBytes();
            out.write(b);
            out.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String info="";
            String data="";
            while((info=br.readLine())!=null){
                System.out.println("我是客戶端,Python伺服器說:"+info);
                data=data+info;
            }
            a=(int)((Double.parseDouble(data))*100);
            out.close();
            out = null;
            br.close();
            client.close();
            client = null;
        } catch (java.io.IOException e) {
            System.out.println("Socket連線有問題 !");
            System.out.println("IOException :" + e.toString());
        }
    }
    private void setturn(String ss1[]){



        cid=Integer.parseInt(ss1[0]);
        love=ss1[6];
        watch=ss1[7];
        source=ss1[1];
        key1=ss1[4];
        key2=ss1[5];
        key3=ss1[6];
        f1=ss1[8];
        f2=ss1[9];
        f3=ss1[10];

    }

}
