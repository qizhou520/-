package com.example.rumo0716_bna.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.rumo0716_bna.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterFragment extends Fragment {
    private View view;
    String all;
    String s1;
    String s2;
    EditText e1;
    EditText e2;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        Thread t2 = new Thread(resthread);
        try{
            t2.start();
            t2.join();
        }
        catch (Exception e){
        }
         e1 =view.findViewById(R.id.editText2);
         e2 =view.findViewById(R.id.editText);
        Button btn = view.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1 =e1.getText().toString();
                s2 =e2.getText().toString();
                if(rescheck(s1)){
                    if(s1!=""&&s2!=""){
                        Toast.makeText(getContext(),"該使用者名稱已被使用",Toast.LENGTH_SHORT).show();}
                }
                else{
                    try{
                        Thread t3 = new Thread(creusthread);
                        t3.start();
                        t3.join();
                        Toast.makeText(getContext(),"帳戶建立成功",Toast.LENGTH_LONG).show();
                        Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment);
                    }
                    catch (Exception e){
                    }
                }

            }
        });

        return view;
    }
    private Runnable resthread = new Runnable() {
        @Override
        public void run() {
            try{
                URL url = new URL("http://192.168.43.17/GetUser.php");
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
                    BufferedReader br= new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    all="";
                    String a="";
                    while ((a=br.readLine())!=null){
                        all=all+a;
                    }
                    all = all.replace("[","");
                    all = all.replace("]","");

                }
                connection.disconnect();
            }
            catch (Exception e){

            }
        }
    };
    private  Runnable creusthread=new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL("http://192.168.43.17/creatuser.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(false); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線
                OutputStream os = connection.getOutputStream();
                OutputStreamWriter ow = new OutputStreamWriter(os);
                BufferedWriter br = new BufferedWriter(ow);
                String a = s1+"-"+s2;
                br.write(a,0,a.length());
                br.flush();
                br.close();
                ow.close();
                os.close();
                InputStream is = connection.getInputStream();
                is.close();
                connection.disconnect();
            }
            catch (Exception e){

            }
        }
    };
    private boolean rescheck(String s1){
        String us []= all.split("\\}\\,\\{");
        for(int i=0;i<us.length;i++){
            if(us[i].contains("\"ID\":\""+s1)){
                return true;
            }
        }
        return false;
    }
}