package com.example.rumo0716_bna.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.rumo0716_bna.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginFragment extends Fragment {
    public static String  urfake;
    public static String urcorrect;
    public static String usid;
    public static String password;
    private View view;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        Button btn = view.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(checkpwsd()) {
                    Navigation.findNavController(requireView()).navigate(R.id.navigation_home);
                }
                else{
                    Toast.makeText(getContext(),"使用者名稱或密碼錯誤",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button btn1 = view.findViewById(R.id.button5);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigate(R.id.registerFragment);
            }
        });
        return view;
    }
    public boolean checkpwsd(){
        EditText e1 = view.findViewById(R.id.editText2);
        EditText e2 =view.findViewById(R.id.editText);
        usid =e1.getText().toString();
        password=e2.getText().toString();
        Thread t1 = new Thread(psthread);
        try{
            t1.start();
            t1.join();
        }
        catch (Exception e){
            System.out.println(e);
        }
        if(urcorrect!=null&&urfake!=null){
            return true;
        }
     return false;
    }
    private Runnable psthread = new Runnable() {
        @Override
        public void run() {
            try {
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
                    String all="";
                    String a="";
                    while ((a=br.readLine())!=null){
                        all=all+a;
                    }
                    all = all.replace("[","");
                    all = all.replace("]","");
                    String us []= all.split("\\}\\,\\{");
                    for(int i=0;i<us.length;i++){
                        if(us[i].contains("\"ID\":\""+usid+"\",\"PASSWORD\":\""+password)){
                            String edit[] = us[i].split(",");
                            urfake=edit[2].replace("\"FAKE\":\"","").replace("\"","");
                            urcorrect=edit[3].replace("\"CORRECT\":\"","").replace("\"}","").replace("\"","");
                        }
                    }
                }
                connection.disconnect();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    };
}