package com.example.rumo0716_bna.ui.wordpage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.rumo0716_bna.R;
import com.example.rumo0716_bna.ui.LoginFragment;
import com.example.rumo0716_bna.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;


public class WordPageFragment  extends Fragment {
    private View view;
    public String wpstr[];
    public int id[];//推薦id
    private boolean check;
    static String  tt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wordpage, container, false);
        Thread thread = new Thread(mthread);//觀看數增加一
        thread.start();
        try{
            thread.join();}
        catch (Exception e){
        }
        if(HomeFragment.ju_source.equals("事實審查中心")){
            ImageView m=view.findViewById(R.id.block);
            m.setBackground(getResources().getDrawable(R.drawable.logo_2));
        }
        if(HomeFragment.ju_correct_or_fake){
            ImageView cross = view.findViewById(R.id.cross4);
            cross.setBackground(getResources().getDrawable(R.drawable.alert));
            TextView icon = view.findViewById(R.id.textView36);
            icon.setText("小心");
        }
        TextView text = view.findViewById(R.id.textView26);//文章內容
        text.setText(HomeFragment.ju_context);
        TextView title = view.findViewById(R.id.textView27);//標題
        title.setText(HomeFragment.ju_article);
        //關鍵字
        TextView keyword1 = view.findViewById(R.id.textView23);
        TextView keyword2 = view.findViewById(R.id.textView24);
        TextView keyword3 = view.findViewById(R.id.textView25);
        keyword1.setText(HomeFragment.ju_key1);
        keyword2.setText(HomeFragment.ju_key2);
        keyword3.setText(HomeFragment.ju_key3);
        //觀看數與收藏數
        TextView watch_count = view.findViewById(R.id.textView28);
        TextView collect_count = view.findViewById(R.id.textView31);
        watch_count.setText(HomeFragment.ju_search);
        collect_count.setText(HomeFragment.ju_love);
        //你可能感興趣的文章
        if(HomeFragment.ju_correct_or_fake){
            interesting(HomeFragment.str);
            wpstr=HomeFragment.str;
        }
        else{
            interesting(HomeFragment.str1);
            wpstr=HomeFragment.str1;
        }
        //心情按鈕
        Button facebtn1 = view.findViewById(R.id.button8);
        facebtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                System.out.println("我是按鈕1");
                facebutton(1);
            }
        });
        Button facebtn2 = view.findViewById(R.id.button9);
        facebtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                System.out.println("我是按鈕2");
                facebutton(2);
            }
        });
        Button facebtn3 = view.findViewById(R.id.button10);
        facebtn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                System.out.println("我是按鈕3");
                facebutton(3);
            }
        });
        //收藏功能
        System.out.println(HomeFragment.ju_home_text_id);
        Button collect = view.findViewById(R.id.button11);
        collect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (LoginFragment.usid == null || LoginFragment.usid.equals("")) {
                    Toast.makeText(getContext(), "請先登入", Toast.LENGTH_SHORT).show();
                } else {
                    tt="";
                    if (HomeFragment.ju_correct_or_fake) {tt = LoginFragment.urcorrect;}
                    else if(HomeFragment.ju_correct_or_fake==false){
                        System.out.println("錯1");
                        tt = LoginFragment.urfake;
                    }
                    if(tt.contains(HomeFragment.ju_home_text_id+"")){
                        check=true;
                        Toast.makeText(getContext(),"取消收藏",Toast.LENGTH_SHORT).show();
                    }
                    else{check=false;
                    Toast.makeText(getContext(),"收藏成功",Toast.LENGTH_SHORT).show();
                    }
                    System.out.println(LoginFragment.urfake);
                    Thread t123 = new Thread(tcollect);
                    t123.start();
                    try {
                        t123.join();
                    } catch (Exception e) {

                    }
                }
            }
        });
        //心情累計條
        ImageView emo1= view.findViewById(R.id.imageView20);
        emo1.setMinimumHeight(emo1.getHeight()+Integer.parseInt(HomeFragment.ju_face1));
        ImageView emo2= view.findViewById(R.id.imageView21);
        emo2.setMinimumHeight(emo2.getHeight()+Integer.parseInt(HomeFragment.ju_face2));
        ImageView emo3= view.findViewById(R.id.imageView23);
        emo3.setMinimumHeight(emo2.getHeight()+Integer.parseInt(HomeFragment.ju_face3));
        //心情累計
        TextView E1 = view.findViewById(R.id.textView37);
        E1.setText(HomeFragment.ju_face1);
        TextView E2 = view.findViewById(R.id.textView38);
        E2.setText(HomeFragment.ju_face2);
        TextView E3 = view.findViewById(R.id.textView39);
        E3.setText(HomeFragment.ju_face3);
        //推薦欄位
        Button combtn1 = view.findViewById(R.id.button2);
        combtn1.setBackgroundColor(Color.TRANSPARENT);
        combtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String www = wpstr[id[0]+1];
                String ss1[]=www.split(",");
                HomeFragment.ju_source=ss1[1].replace("SOURCE","").replace("\"","").replace(":","");
                String coarticle=ss1[2].replace("CONTENT","").replace("\"","").replace(":","");
                HomeFragment.ju_context=coarticle;
                if(coarticle.length()>21){HomeFragment.ju_article=coarticle.substring(0,21);
                    if(coarticle.length()>60)HomeFragment.ju_context=coarticle.substring(0,59);
                    else{HomeFragment.ju_context=coarticle;}}
                else{HomeFragment.ju_article=coarticle;HomeFragment.ju_context=coarticle;}
                HomeFragment.ju_context=coarticle;
                HomeFragment.ju_love=ss1[6].replace("COLLECT","").replace("\"","").replace(":","");
                HomeFragment.ju_search=ss1[7].replace("WATCH","").replace("\"","").replace(":","");
                HomeFragment.ju_home_text_id=Integer.parseInt(ss1[0].replace("ID","").replace("\"","").replace(":","").replace("[","").replace("{",""));
                HomeFragment.ju_correct_or_fake =false;
                HomeFragment.ju_key1=ss1[3].replace("KEY1","").replace("\"","").replace(":","");
                HomeFragment.ju_key2=ss1[4].replace("KEY2","").replace("\"","").replace(":","");
                HomeFragment.ju_key3=ss1[5].replace("KEY3","").replace("\"","").replace(":","");
                HomeFragment.ju_face1=ss1[8].replace("Face1","").replace("\"","").replace(":","");
                HomeFragment.ju_face2=ss1[9].replace("Face2","").replace("\"","").replace(":","");
                HomeFragment.ju_face3=ss1[10].replace("Face3","").replace("\"","").replace(":","");
                Navigation.findNavController(view).navigate(R.id.action_fragment_wordpage_result_self);
            }
        });
        Button combtn2 = view.findViewById(R.id.button4);
        combtn2.setBackgroundColor(Color.TRANSPARENT);
        combtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String www = wpstr[id[1]+1];
                String ss1[]=www.split(",");
                HomeFragment.ju_source=ss1[1].replace("SOURCE","").replace("\"","").replace(":","");
                String coarticle=ss1[2].replace("CONTENT","").replace("\"","").replace(":","");
                HomeFragment.ju_context=coarticle;
                if(coarticle.length()>21){HomeFragment.ju_article=coarticle.substring(0,21);
                    if(coarticle.length()>60)HomeFragment.ju_context=coarticle.substring(0,59);
                    else{HomeFragment.ju_context=coarticle;}}
                else{HomeFragment.ju_article=coarticle;HomeFragment.ju_context=coarticle;}
                HomeFragment.ju_context=coarticle;
                HomeFragment.ju_love=ss1[6].replace("COLLECT","").replace("\"","").replace(":","");
                HomeFragment.ju_search=ss1[7].replace("WATCH","").replace("\"","").replace(":","");
                HomeFragment.ju_home_text_id=Integer.parseInt(ss1[0].replace("ID","").replace("\"","").replace(":","").replace("[","").replace("{",""));
                HomeFragment.ju_correct_or_fake =false;
                HomeFragment.ju_key1=ss1[3].replace("KEY1","").replace("\"","").replace(":","");
                HomeFragment.ju_key2=ss1[4].replace("KEY2","").replace("\"","").replace(":","");
                HomeFragment.ju_key3=ss1[5].replace("KEY3","").replace("\"","").replace(":","");
                HomeFragment.ju_face1=ss1[8].replace("Face1","").replace("\"","").replace(":","");
                HomeFragment.ju_face2=ss1[9].replace("Face2","").replace("\"","").replace(":","");
                HomeFragment.ju_face3=ss1[10].replace("Face3","").replace("\"","").replace(":","");
                Navigation.findNavController(view).navigate(R.id.action_fragment_wordpage_result_self);
            }
        });
        Button combtn3 = view.findViewById(R.id.button5);
        combtn3.setBackgroundColor(Color.TRANSPARENT);
        combtn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String www = wpstr[id[2]+1];
                String ss1[]=www.split(",");
                HomeFragment.ju_source=ss1[1].replace("SOURCE","").replace("\"","").replace(":","");
                String coarticle=ss1[2].replace("CONTENT","").replace("\"","").replace(":","");
                HomeFragment.ju_context=coarticle;
                if(coarticle.length()>21){HomeFragment.ju_article=coarticle.substring(0,21);
                    if(coarticle.length()>60)HomeFragment.ju_context=coarticle.substring(0,59);
                    else{HomeFragment.ju_context=coarticle;}}
                else{HomeFragment.ju_article=coarticle;HomeFragment.ju_context=coarticle;}
                HomeFragment.ju_context=coarticle;
                HomeFragment.ju_love=ss1[6].replace("COLLECT","").replace("\"","").replace(":","");
                HomeFragment.ju_search=ss1[7].replace("WATCH","").replace("\"","").replace(":","");
                HomeFragment.ju_home_text_id=Integer.parseInt(ss1[0].replace("ID","").replace("\"","").replace(":","").replace("[","").replace("{",""));
                HomeFragment.ju_correct_or_fake =false;
                HomeFragment.ju_key1=ss1[3].replace("KEY1","").replace("\"","").replace(":","");
                HomeFragment.ju_key2=ss1[4].replace("KEY2","").replace("\"","").replace(":","");
                HomeFragment.ju_key3=ss1[5].replace("KEY3","").replace("\"","").replace(":","");
                HomeFragment.ju_face1=ss1[8].replace("Face1","").replace("\"","").replace(":","");
                HomeFragment.ju_face2=ss1[9].replace("Face2","").replace("\"","").replace(":","");
                HomeFragment.ju_face3=ss1[10].replace("Face3","").replace("\"","").replace(":","");
                Navigation.findNavController(view).navigate(R.id.action_fragment_wordpage_result_self);
            }
        });

        //分享功能
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                if(HomeFragment.ju_correct_or_fake){
                sendIntent.putExtra(Intent.EXTRA_TEXT,"來自rumo闢謠小天使發現的正確訊息  "+HomeFragment.ju_context);}
                else if(!HomeFragment.ju_correct_or_fake){
                    sendIntent.putExtra(Intent.EXTRA_TEXT,"來自rumo闢謠小天使發現的假訊息  "+HomeFragment.ju_context);
                }
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        return view;
    }
    private Runnable mthread=new Runnable(){
        public void run(){
        try{
            if(HomeFragment.ju_correct_or_fake) {
                URL url = new URL("http://192.168.43.17/WatchAddOneCo.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線
                OutputStream ot = connection.getOutputStream();
                OutputStreamWriter or = new OutputStreamWriter(ot, "UTF-8");
                or.write(String.valueOf(HomeFragment.ju_home_text_id));
                or.flush();
                or.close();
                ot.close();
                InputStream is = connection.getInputStream();
                InputStreamReader ir= new InputStreamReader(is);
                ir.close();
                is.close();
                connection.disconnect();


            }
            else {
                URL url = new URL("http://192.168.43.17/WatchAddOneFa.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線
                OutputStream ot = connection.getOutputStream();
                OutputStreamWriter or = new OutputStreamWriter(ot,"UTF-8");
                or.write(String.valueOf(HomeFragment.ju_home_text_id));
                or.flush();
                or.close();
                ot.close();
                InputStream is = connection.getInputStream();
                InputStreamReader ir= new InputStreamReader(is);
                ir.close();
                is.close();
                connection.disconnect();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    };
    public void interesting(String txt[]){
        int idlist[]=new int[3];
        int cnt=0;
        for(int i=0;i<txt.length;i++) {
            if (i == HomeFragment.ju_home_text_id - 1) i++;
            String each[] = txt[i].split(",");
            String k1 = each[3].replace("KEY1", "").replace("\"", "").replace(":", "");
            String k2 = each[4].replace("KEY2", "").replace("\"", "").replace(":", "");
            String k3 = each[5].replace("KEY3", "").replace("\"", "").replace(":", "");
            ArrayList a = new ArrayList();
            a.add(k1);
            a.add(k2);
            a.add(k3);
            if (a.contains(HomeFragment.ju_key1) || a.contains(HomeFragment.ju_key2) || a.contains(HomeFragment.ju_key3)) {
                idlist[cnt] = Integer.parseInt(each[0].replace("ID", "").replace("\"", "").replace(":", "").replace("[", "").replace("{", ""));
                cnt++;
            }
            if(cnt==3)break;
            if(i==txt.length-1){
                for(int j=cnt;j<3;j++){
                    idlist[j]= (int)(Math.random()*1000)+1;
                }
            }
            id=idlist;
        }
        String recommend[]=txt[idlist[0]+1].split(",");
        TextView s1 =view.findViewById(R.id.textView2);
        TextView t1 =view.findViewById(R.id.textView9);
        TextView w1 =view.findViewById(R.id.textView29);
        TextView l1 =view.findViewById(R.id.textView30);
        s1.setText(recommend[1].replace("SOURCE","").replace("\"","").replace(":",""));
        t1.setText(recommend[2].replace("CONTENT","").replace("\"","").replace(":",""));
        w1.setText(recommend[7].replace("WATCH","").replace("\"","").replace(":",""));
        l1.setText(recommend[6].replace("COLLECT","").replace("\"","").replace(":",""));

        String recommend1[]=txt[idlist[1]+1].split(",");
        TextView s2 =view.findViewById(R.id.textView13);
        TextView t2 =view.findViewById(R.id.textView18);
        TextView w2 =view.findViewById(R.id.textView32);
        TextView l2 =view.findViewById(R.id.textView33);
        s2.setText(recommend1[1].replace("SOURCE","").replace("\"","").replace(":",""));
        t2.setText(recommend1[2].replace("CONTENT","").replace("\"","").replace(":",""));
        w2.setText(recommend1[7].replace("WATCH","").replace("\"","").replace(":",""));
        l2.setText(recommend1[6].replace("COLLECT","").replace("\"","").replace(":",""));

        String recommend2[]=txt[idlist[2]+1].split(",");
        TextView s3 =view.findViewById(R.id.textView21);
        TextView t3 =view.findViewById(R.id.textView22);
        TextView w3 =view.findViewById(R.id.textView34);
        TextView l3 =view.findViewById(R.id.textView35);
        s3.setText(recommend2[1].replace("SOURCE","").replace("\"","").replace(":",""));
        t3.setText(recommend2[2].replace("CONTENT","").replace("\"","").replace(":",""));
        w3.setText(recommend2[7].replace("WATCH","").replace("\"","").replace(":",""));
        l3.setText(recommend2[6].replace("COLLECT","").replace("\"","").replace(":",""));

    }
    public void facebutton(int a)  {
        try {
            if (a == 1) {
                Thread t = new Thread(fa1);
                t.start();
                t.join();
            } else if (a == 2) {
                Thread t = new Thread(fa2);
                t.start();
                t.join();
            } else if (a == 3) {
                Thread t = new Thread(fa3);
                t.start();
                t.join();
            }
        }
        catch (Exception e){

        }
    }
    private Runnable fa1=new Runnable(){
        public void run(){
            try{
                URL url;
                if(HomeFragment.ju_correct_or_fake) {
                    url = new URL("http://192.168.43.17/Face1co.php");
                }
                else{
                    url = new URL("http://192.168.43.17/Face1fa.php");
                }
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // 建立 Google 比較挺的 HttpURLConnection 物件
                    connection.setRequestMethod("POST");
                    // 設定連線方式為 POST
                    connection.setDoOutput(true); // 允許輸出
                    connection.setDoInput(true); // 允許讀入
                    connection.setUseCaches(false); // 不使用快取
                    connection.connect(); // 開始連線
                    OutputStream ot = connection.getOutputStream();
                    OutputStreamWriter or = new OutputStreamWriter(ot, "UTF-8");
                    or.write(String.valueOf(HomeFragment.ju_home_text_id));
                    or.flush();
                    or.close();
                    ot.close();
                    InputStream is = connection.getInputStream();
                    InputStreamReader ir= new InputStreamReader(is);
                    ir.close();
                    is.close();
                    connection.disconnect();
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    };
    private Runnable fa2=new Runnable(){
        public void run(){
            try{
                URL url;
                if(HomeFragment.ju_correct_or_fake) {
                    url = new URL("http://192.168.43.17/Face2co.php");
                }
                else{
                    url = new URL("http://192.168.43.17/Face2fa.php");
                }
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線
                OutputStream ot = connection.getOutputStream();
                OutputStreamWriter or = new OutputStreamWriter(ot, "UTF-8");
                or.write(String.valueOf(HomeFragment.ju_home_text_id));
                or.flush();
                or.close();
                ot.close();
                InputStream is = connection.getInputStream();
                InputStreamReader ir= new InputStreamReader(is);
                ir.close();
                is.close();
                connection.disconnect();
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    };
    private Runnable fa3=new Runnable(){
        public void run(){
            try{
                URL url;
                if(HomeFragment.ju_correct_or_fake) {
                    url = new URL("http://192.168.43.17/Face3co.php");
                }
                else{
                    url = new URL("http://192.168.43.17/Face3fa.php");
                }
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線
                OutputStream ot = connection.getOutputStream();
                OutputStreamWriter or = new OutputStreamWriter(ot, "UTF-8");
                or.write(String.valueOf(HomeFragment.ju_home_text_id));
                or.flush();
                or.close();
                ot.close();
                InputStream is = connection.getInputStream();
                InputStreamReader ir= new InputStreamReader(is);
                ir.close();
                is.close();
                connection.disconnect();
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    };
    private Runnable tcollect=new Runnable(){
        public void run(){
            try {
                if(check){
                        if(tt.contains(".")){
                            if(tt.contains(HomeFragment.ju_home_text_id+".")) {
                                tt = tt.replace(HomeFragment.ju_home_text_id+".","");
                            }
                            else {
                                tt = tt.replace("." + HomeFragment.ju_home_text_id + "", "");
                            }
                        }
                        else{
                            tt="";
                        }
                        String op=tt;
                        URL url;
                        if (HomeFragment.ju_correct_or_fake) {
                            url = new URL("http://192.168.43.17/collectco.php");
                            LoginFragment.urcorrect=tt;
                        } else {
                            url = new URL("http://192.168.43.17/collectf.php");
                            LoginFragment.urfake=tt;
                        }
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        // 建立 Google 比較挺的 HttpURLConnection 物件
                        connection.setRequestMethod("POST");
                        // 設定連線方式為 POST
                        connection.setDoOutput(true); // 允許輸出
                        connection.setDoInput(true); // 允許讀入
                        connection.setUseCaches(false); // 不使用快取
                        connection.connect(); // 開始連線
                        OutputStream ot = connection.getOutputStream();
                        OutputStreamWriter or = new OutputStreamWriter(ot, "UTF-8");
                        BufferedWriter br = new BufferedWriter(or);
                        br.write(tt);
                        br.flush();
                        br.close();
                        or.close();
                        ot.close();
                        InputStream is = connection.getInputStream();
                        InputStreamReader ir = new InputStreamReader(is);
                        ir.close();
                        is.close();
                        connection.disconnect();
                    }
                    else {
                        String op=tt;//存輸出

                        String adress="";
                        if (!HomeFragment.ju_correct_or_fake) {
                            adress="http://192.168.43.17/collectf.php";
                        } else {
                           adress="http://192.168.43.17/collectco.php";
                        }
                        URL url = new URL(adress);
                        System.out.println(op);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        // 建立 Google 比較挺的 HttpURLConnection 物件
                        connection.setRequestMethod("POST");
                        // 設定連線方式為 POST
                        connection.setDoOutput(true); // 允許輸出
                        connection.setDoInput(true); // 允許讀入
                        connection.setUseCaches(false); // 不使用快取
                        connection.connect(); // 開始連線
                        OutputStream ot = connection.getOutputStream();
                        OutputStreamWriter or = new OutputStreamWriter(ot, "UTF-8");
                        //BufferedWriter br = new BufferedWriter(or);
                        String ss;
                        if(op.equals("")||op==null){
                            ss=HomeFragment.ju_home_text_id+"";
                            System.out.println("錯");
                        }
                        else{ss=op + "." + HomeFragment.ju_home_text_id;}
                        if(HomeFragment.ju_correct_or_fake){
                            LoginFragment.urcorrect=ss;
                        }
                        else{
                            LoginFragment.urfake=ss;
                        }
                        or.write(ss);
                        or.flush();
                        or.close();
                        ot.close();
                        InputStream is = connection.getInputStream();
                        InputStreamReader ir = new InputStreamReader(is);
                        ir.close();
                        is.close();
                        connection.disconnect();
                    }

            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    };

}
