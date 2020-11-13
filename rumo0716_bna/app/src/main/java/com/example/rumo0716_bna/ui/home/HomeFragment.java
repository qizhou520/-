package com.example.rumo0716_bna.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rumo0716_bna.R;
import com.example.rumo0716_bna.ui.LoginFragment;
import com.example.rumo0716_bna.ui.adapter.HomeAlertAdapter;
import com.example.rumo0716_bna.ui.adapter.HomeAllAdapter;
import com.example.rumo0716_bna.ui.adapter.HomeFakeAdapter;
import com.example.rumo0716_bna.ui.wordpage.ButtonActivity;

import android.view.View.OnClickListener;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class HomeFragment extends Fragment implements OnClickListener{
    private View view;//定義view用來設定fragment的layout
    //定義RecyclerView
    public RecyclerView mHomeRecyclerView;
    //定義以Home_item實體類為物件的資料集合
    private ArrayList<Home_item> Home_item_List = new ArrayList<Home_item>();
    //自定義recyclerveiw的介面卡
    private HomeAllAdapter mHomeAllAdapter;
    private HomeAlertAdapter mHomeAlertAdapter;
    private HomeFakeAdapter mHomeFakeAdapter;

    private Spinner spin;
    private Button btn_All;
    private Button btn_Alert;
    private Button btn_Fake;

    private OnButtonClick onButtonClick;
    private HomeViewModel homeViewModel;
    Spinner spinner;
    public static String str[];//GetFake
    public static String str1[];//GetCorrect
    public static String ju_source ;
    public static String ju_article ;
    public static String ju_context ;
    public static String ju_love ;
    public static String ju_search ;
    public static int ju_home_text_id;
    public static boolean ju_correct_or_fake;//alter=correct fake=fake
    public static String ju_key1 ;
    public static String ju_key2 ;
    public static String ju_key3 ;
    public static String ju_face1;
    public static String ju_face2;
    public static String ju_face3;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //獲取fragment的layout
        view = inflater.inflate(R.layout.fragment_home, container, false);
        //對recycleview進行配置
        initRecyclerView();
        Thread thread = new Thread(mthread);
        thread.start();
        try{
            thread.join();}
        catch (Exception e){
        }
        setHasOptionsMenu(true);

        btn_All = (Button) view.findViewById(R.id.btn_all);
        btn_Alert = (Button) view.findViewById(R.id.btn_alert);
        btn_Fake = (Button) view.findViewById(R.id.btn_fake);
        btn_All.setOnClickListener(this);
        btn_Alert.setOnClickListener(this);
        btn_Fake.setOnClickListener(this);
        Home_item_List.clear();
        for(int i =0;i<str.length;i++){
            Home_item fake = new Home_item();
            String s1 = str[i];
            String ss1[]=s1.split(",");
            fake.source=ss1[1].replace("SOURCE","").replace("\"","").replace(":","");
            String coarticle=ss1[2].replace("CONTENT","").replace("\"","").replace(":","");
            fake.text_all=coarticle;
            if(coarticle.length()>21){fake.article=coarticle.substring(0,21);
                if(coarticle.length()>60)fake.context=coarticle.substring(0,59);
                else{fake.context=coarticle;}}
            else{fake.article=coarticle;fake.context=coarticle;}
            fake.love=ss1[6].replace("COLLECT","").replace("\"","").replace(":","");
            fake.search=ss1[7].replace("WATCH","").replace("\"","").replace(":","");
            fake.home_text_id=Integer.parseInt(ss1[0].replace("ID","").replace("\"","").replace(":","").replace("[","").replace("{",""));
            fake.correct_or_fake =false;
            fake.key1=ss1[3].replace("KEY1","").replace("\"","").replace(":","");
            fake.key2=ss1[4].replace("KEY2","").replace("\"","").replace(":","");
            fake.key3=ss1[5].replace("KEY3","").replace("\"","").replace(":","");
            fake.emo1=ss1[8].replace("Face1","").replace("\"","").replace(":","");
            fake.emo2=ss1[9].replace("Face2","").replace("\"","").replace(":","");
            fake.emo3=ss1[10].replace("Face3","").replace("\"","").replace(":","");
            Home_item_List.add(fake);
        }
        for(int i =0;i<str1.length;i++){
            Home_item alert = new Home_item();
            String s1 = str1[i];
            String ss1[]=s1.split(",");
            alert.source=ss1[1].replace("SOURCE","").replace("\"","").replace(":","");
            String coarticle=ss1[2].replace("CONTENT","").replace("\"","").replace(":","");
            alert.text_all=coarticle;
            if(coarticle.length()>21){alert.article=coarticle.substring(0,21);
                if(coarticle.length()>60)alert.context=coarticle.substring(0,59);
                else{alert.context=coarticle;}}
            else{alert.article=coarticle;alert.context=coarticle;}
            alert.love=ss1[6].replace("COLLECT","").replace("\"","").replace(":","");
            alert.search=ss1[7].replace("WATCH","").replace("\"","").replace(":","");
            alert.home_text_id=Integer.parseInt(ss1[0].replace("ID","").replace("\"","").replace(":","").replace("[","").replace("{",""));
            alert.correct_or_fake =true;
            alert.key1=ss1[3].replace("KEY1","").replace("\"","").replace(":","");
            alert.key2=ss1[4].replace("KEY2","").replace("\"","").replace(":","");
            alert.key3=ss1[5].replace("KEY3","").replace("\"","").replace(":","");
            alert.emo1=ss1[8].replace("Face1","").replace("\"","").replace(":","");
            alert.emo2=ss1[9].replace("Face2","").replace("\"","").replace(":","");
            alert.emo3=ss1[10].replace("Face3","").replace("\"","").replace(":","");
            Home_item_List.add(alert);
        }
        Collections.shuffle(Home_item_List,new Random(555));
        mHomeAllAdapter = new HomeAllAdapter(Home_item_List);
        //給RecyclerView設定adapter
        mHomeRecyclerView.setAdapter(mHomeAllAdapter);
        mHomeRecyclerView.callOnClick();
        mHomeAllAdapter.setOnItemClickListener(new HomeAllAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, Home_item data) {
                //此處進行item監聽事件的處理
                ju_article=data.article;
                ju_context= data.text_all;
                ju_correct_or_fake=data.correct_or_fake;
                ju_home_text_id=data.home_text_id;
                ju_love=data.love;
                ju_search=data.search;
                ju_source=data.source;
                ju_key1=data.key1;
                ju_key2=data.key2;
                ju_key3=data.key3;
                ju_face1=data.emo1;
                ju_face2=data.emo2;
                ju_face3=data.emo3;
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_fragment_wordpage_result);
            }
        });
        SearchView sv = view.findViewById(R.id.searchview);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ArrayList<Home_item> m1 = new ArrayList<Home_item>();

                for(int i=0;i<Home_item_List.size();i++){
                        if (Home_item_List.get(i).context.contains(s)) {
                            m1.add(Home_item_List.get(i));
                        }

                }
                mHomeAllAdapter = new HomeAllAdapter(m1);
                //給RecyclerView設定adapter
                mHomeRecyclerView.setAdapter(mHomeAllAdapter);
                mHomeRecyclerView.callOnClick();
                mHomeAllAdapter.setOnItemClickListener(new HomeAllAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, Home_item data) {
                        //此處進行item監聽事件的處理
                        ju_article=data.article;
                        ju_context= data.text_all;
                        ju_correct_or_fake=data.correct_or_fake;
                        ju_home_text_id=data.home_text_id;
                        ju_love=data.love;
                        ju_search=data.search;
                        ju_source=data.source;
                        ju_key1=data.key1;
                        ju_key2=data.key2;
                        ju_key3=data.key3;
                        ju_face1=data.emo1;
                        ju_face2=data.emo2;
                        ju_face3=data.emo3;
                        Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_fragment_wordpage_result);
                    }
                });

                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        spin = view.findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected
                    (AdapterView<?> adapterView, View view, int position, long l) {
                if(spin.getSelectedItem().toString().equals("瀏覽數")){
                    Collections.sort(Home_item_List,
                            new Comparator<Home_item>() {
                                public int compare(Home_item o1, Home_item o2) {
                                    return Integer.parseInt(o2.search) - Integer.parseInt(o1.search);
                                }
                            });
                    mHomeAllAdapter = new HomeAllAdapter(Home_item_List);
                    //給RecyclerView設定adapter
                    mHomeRecyclerView.setAdapter(mHomeAllAdapter);
                    System.out.println("瀏覽");
                }
                else if(spin.getSelectedItem().toString().equals("收藏數")){
                    Collections.sort(Home_item_List,
                            new Comparator<Home_item>() {
                                public int compare(Home_item o1, Home_item o2) {
                                    return Integer.parseInt(o2.love) - Integer.parseInt(o1.love);
                                }
                            });
                    mHomeAllAdapter = new HomeAllAdapter(Home_item_List);
                    //給RecyclerView設定adapter
                    mHomeRecyclerView.setAdapter(mHomeAllAdapter);
                    System.out.println("收藏");
                }
                else{}
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }
    private Runnable mthread = new Runnable() {
        @Override
        public void run() {
            try {
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
                    box = box.replace("[{", "");
                    box = box.replace("}]", "");

                    String s = "\\}\\,\\{";
                    str = box.split(s);
                    inputStream.close(); // 關閉輸入串流
                    connection.disconnect();
                }
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
                        connection1.disconnect();
                    }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }

    };
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_login){
            if(LoginFragment.usid!=null&&LoginFragment.password!=null){
                Toast.makeText(getContext(),"請先登出再進行登入",Toast.LENGTH_SHORT).show();
            }
            else{Navigation.findNavController(requireView()).navigate(R.id.loginFragment);}
            //跳登入
            return true;
        }else if(item.getItemId() == R.id.action_logout){
            //登出
            LoginFragment.urfake=null;
            LoginFragment.urcorrect=null;
            LoginFragment.password=null;
            LoginFragment.usid=null;
            Toast.makeText(getContext(),"您已登出",Toast.LENGTH_SHORT).show();
            return true;
        }else {
            return false;
        }
    }


    /**
     * 對recycleview進行配置
     */

    private void initRecyclerView(){
        //獲取RecyclerView
        mHomeRecyclerView=(RecyclerView)view.findViewById(R.id.home);
        //設定layoutManager,可以設定顯示效果，是線性佈局、grid佈局，還是瀑布流佈局
        //引數是：上下文、列表方向（橫向還是縱向）、是否倒敘
        mHomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //設定item的分割線
        mHomeRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        //Home_item ex = new Home_item("1","2","3","4","5"); 測試
        //Home_item_List.add(ex);
        //建立adapter
        mHomeAllAdapter = new HomeAllAdapter(Home_item_List);
        //給RecyclerView設定adapter
        mHomeRecyclerView.setAdapter(mHomeAllAdapter);
        //RecyclerView中沒有item的監聽事件，需要自己寫一個監聽事件的介面。引數根據Adapter
        mHomeRecyclerView.callOnClick();
        mHomeAllAdapter.setOnItemClickListener(new HomeAllAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, Home_item data) {
                //此處進行item監聽事件的處理 - 剛進主頁面未點任何標籤 - 複製All

            }
        });
        
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_all:
                //建立adapter並清空item_List + RecycleView
                Home_item_List.clear();
                for(int i =0;i<str.length;i++){
                    Home_item fake = new Home_item();
                    String s1 = str[i];
                    String ss1[]=s1.split(",");
                    fake.source=ss1[1].replace("SOURCE","").replace("\"","").replace(":","");
                    String coarticle=ss1[2].replace("CONTENT","").replace("\"","").replace(":","");
                    fake.text_all=coarticle;//全文
                    if(coarticle.length()>21){fake.article=coarticle.substring(0,20);
                        if(coarticle.length()>60)fake.context=coarticle.substring(0,59);
                        else{fake.context=coarticle;}}
                    else{fake.article=coarticle;fake.context=coarticle;}
                    fake.love=ss1[6].replace("COLLECT","").replace("\"","").replace(":","");
                    fake.search=ss1[7].replace("WATCH","").replace("\"","").replace(":","");
                    fake.home_text_id=Integer.parseInt(ss1[0].replace("ID","").replace("\"","").replace(":","").replace("[","").replace("{",""));
                    fake.correct_or_fake =false;
                    fake.key1=ss1[3].replace("KEY1","").replace("\"","").replace(":","");
                    fake.key2=ss1[4].replace("KEY2","").replace("\"","").replace(":","");
                    fake.key3=ss1[5].replace("KEY3","").replace("\"","").replace(":","");
                    fake.emo1=ss1[8].replace("Face1","").replace("\"","").replace(":","");
                    fake.emo2=ss1[9].replace("Face2","").replace("\"","").replace(":","");
                    fake.emo3=ss1[10].replace("Face3","").replace("\"","").replace(":","");
                    Home_item_List.add(fake);
                }
                for(int i =0;i<str1.length;i++){
                    Home_item alert = new Home_item();
                    String s1 = str1[i];
                    String ss1[]=s1.split(",");
                    alert.source=ss1[1].replace("SOURCE","").replace("\"","").replace(":","");
                    String coarticle=ss1[2].replace("CONTENT","").replace("\"","").replace(":","");
                    if(coarticle.length()>21){alert.article=coarticle.substring(0,20);
                        if(coarticle.length()>60)alert.context=coarticle.substring(0,59);
                        else{alert.context=coarticle;}}
                    else{alert.article=coarticle;alert.context=coarticle;}
                    alert.love=ss1[6].replace("COLLECT","").replace("\"","").replace(":","");
                    alert.search=ss1[7].replace("WATCH","").replace("\"","").replace(":","");
                    alert.home_text_id=Integer.parseInt(ss1[0].replace("ID","").replace("\"","").replace(":","").replace("[","").replace("{",""));
                    alert.correct_or_fake =true;
                    alert.key1=ss1[3].replace("KEY1","").replace("\"","").replace(":","");
                    alert.key2=ss1[4].replace("KEY2","").replace("\"","").replace(":","");
                    alert.key3=ss1[5].replace("KEY3","").replace("\"","").replace(":","");
                    alert.emo1=ss1[8].replace("Face1","").replace("\"","").replace(":","");
                    alert.emo2=ss1[9].replace("Face2","").replace("\"","").replace(":","");
                    alert.emo3=ss1[10].replace("Face3","").replace("\"","").replace(":","");
                    Home_item_List.add(alert);
                }
                Collections.shuffle(Home_item_List,new Random(555));
                mHomeAllAdapter = new HomeAllAdapter(Home_item_List);
                //給RecyclerView設定adapter
                mHomeRecyclerView.setAdapter(mHomeAllAdapter);
                //標籤顏色更改
                btn_All.setBackgroundResource(R.drawable.red_label);
                btn_Alert.setBackgroundResource(R.drawable.label);
                btn_Fake.setBackgroundResource(R.drawable.label);
                //字體顏色更改
                btn_All.setTextColor(Color.WHITE);
                btn_Alert.setTextColor(Color.BLACK);
                btn_Fake.setTextColor(Color.BLACK);
                spin.setSelection(0);
                //RecyclerView中沒有item的監聽事件，需要自己在介面卡中寫一個監聽事件的介面。引數根據自定義
                mHomeRecyclerView.callOnClick();
                mHomeAllAdapter.setOnItemClickListener(new HomeAllAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, Home_item data) {
                        //此處進行item監聽事件的處理
                        ju_article=data.article;
                        ju_context= data.text_all;
                        ju_correct_or_fake=data.correct_or_fake;
                        ju_home_text_id=data.home_text_id;
                        ju_love=data.love;
                        ju_search=data.search;
                        ju_source=data.source;
                        ju_key1=data.key1;
                        ju_key2=data.key2;
                        ju_key3=data.key3;
                        ju_face1=data.emo1;
                        ju_face2=data.emo2;
                        ju_face3=data.emo3;
                        Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_fragment_wordpage_result);
                    }
                });

                break;
            case R.id.btn_alert:
                //建立adapter並清空item_List + RecycleView
                Home_item_List.clear();
                mHomeRecyclerView.removeAllViewsInLayout();
                for(int i=0;i<str1.length;i++) {
                    Home_item al_co = new Home_item();
                    String s1 = str1[i];
                    String ss1[]=s1.split(",");
                    al_co.source=ss1[1].replace("SOURCE","").replace("\"","").replace(":","");
                    String coarticle=ss1[2].replace("CONTENT","").replace("\"","").replace(":","");
                    al_co.text_all=coarticle;
                    if(coarticle.length()>21){al_co.article=coarticle.substring(0,20);
                        if(coarticle.length()>60)al_co.context=coarticle.substring(0,59);
                        else{al_co.context=coarticle;}}
                    else{al_co.article=coarticle;al_co.context=coarticle;}
                    al_co.love=ss1[6].replace("COLLECT","").replace("\"","").replace(":","");
                    al_co.search=ss1[7].replace("WATCH","").replace("\"","").replace(":","");
                    al_co.home_text_id=Integer.parseInt(ss1[0].replace("ID","").replace("\"","").replace(":","").replace("[","").replace("{",""));
                    al_co.correct_or_fake =true;
                    al_co.key1=ss1[3].replace("KEY1","").replace("\"","").replace(":","");
                    al_co.key2=ss1[4].replace("KEY2","").replace("\"","").replace(":","");
                    al_co.key3=ss1[5].replace("KEY3","").replace("\"","").replace(":","");
                    al_co.emo1=ss1[8].replace("Face1","").replace("\"","").replace(":","");
                    al_co.emo2=ss1[9].replace("Face2","").replace("\"","").replace(":","");
                    al_co.emo3=ss1[10].replace("Face3","").replace("\"","").replace(":","");
                    Home_item_List.add(al_co);
                }
                mHomeAlertAdapter = new HomeAlertAdapter(Home_item_List);
                //給RecyclerView設定adapter
                mHomeRecyclerView.setAdapter(mHomeAlertAdapter);

                //標籤顏色更改
                btn_All.setBackgroundResource(R.drawable.label);
                btn_Alert.setBackgroundResource(R.drawable.red_label);
                btn_Fake.setBackgroundResource(R.drawable.label);
                //字體顏色更改
                btn_All.setTextColor(Color.BLACK);
                btn_Alert.setTextColor(Color.WHITE);
                btn_Fake.setTextColor(Color.BLACK);
                spin.setSelection(0);
                //RecyclerView中沒有item的監聽事件，需要自己在介面卡中寫一個監聽事件的介面。引數根據自定義
                mHomeRecyclerView.callOnClick();
                mHomeAlertAdapter.setOnItemClickListener(new HomeAlertAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, Home_item data) {
                        //此處進行item監聽事件的處理
                        Toast.makeText(getActivity(),"Click_alert!"+data.source,Toast.LENGTH_SHORT).show();
                        ju_article=data.article;
                        ju_context= data.text_all;
                        ju_correct_or_fake=data.correct_or_fake;
                        ju_home_text_id=data.home_text_id;
                        ju_love=data.love;
                        ju_search=data.search;
                        ju_source=data.source;
                        ju_key1=data.key1;
                        ju_key2=data.key2;
                        ju_key3=data.key3;
                        ju_face1=data.emo1;
                        ju_face2=data.emo2;
                        ju_face3=data.emo3;
                        Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_fragment_wordpage_result);
                    }
                });

                break;
            case R.id.btn_fake:

                //建立adapter並清空item_List + RecycleView
                Home_item_List.clear();
                mHomeRecyclerView.removeAllViewsInLayout();
                for(int i=0;i<str.length;i++) {
                    Home_item co = new Home_item();
                    String s1 = str[i];
                    String ss1[]=s1.split(",");
                    co.source=ss1[1].replace("SOURCE","").replace("\"","").replace(":","");
                    String coarticle=ss1[2].replace("CONTENT","").replace("\"","").replace(":","");
                    co.text_all=coarticle;
                    if(coarticle.length()>21){co.article=coarticle.substring(0,20);
                                             if(coarticle.length()>60)co.context=coarticle.substring(0,59);
                                             else{co.context=coarticle;}}
                    else{co.article=coarticle;co.context=coarticle;}
                    co.love=ss1[6].replace("COLLECT","").replace("\"","").replace(":","");
                    co.search=ss1[7].replace("WATCH","").replace("\"","").replace(":","");
                    co.home_text_id=Integer.parseInt(ss1[0].replace("ID","").replace("\"","").replace(":","").replace("[","").replace("{",""));
                    co.correct_or_fake =false;
                    co.key1=ss1[3].replace("KEY1","").replace("\"","").replace(":","");
                    co.key2=ss1[4].replace("KEY2","").replace("\"","").replace(":","");
                    co.key3=ss1[5].replace("KEY3","").replace("\"","").replace(":","");
                    co.emo1=ss1[8].replace("Face1","").replace("\"","").replace(":","");
                    co.emo2=ss1[9].replace("Face2","").replace("\"","").replace(":","");
                    co.emo3=ss1[10].replace("Face3","").replace("\"","").replace(":","");
                    Home_item_List.add(co);
                }
                Collections.shuffle(Home_item_List,new Random(555));
                mHomeFakeAdapter = new HomeFakeAdapter(Home_item_List);
                //給RecyclerView設定adapter
                mHomeRecyclerView.setAdapter(mHomeFakeAdapter);
                //標籤顏色更改
                btn_All.setBackgroundResource(R.drawable.label);
                btn_Alert.setBackgroundResource(R.drawable.label);
                btn_Fake.setBackgroundResource(R.drawable.red_label);
                //字體顏色更改
                btn_All.setTextColor(Color.BLACK);
                btn_Alert.setTextColor(Color.BLACK);
                btn_Fake.setTextColor(Color.WHITE);
                spin.setSelection(0);
                //RecyclerView中沒有item的監聽事件，需要自己在介面卡中寫一個監聽事件的介面。引數根據自定義
                mHomeRecyclerView.callOnClick();
                mHomeFakeAdapter.setOnItemClickListener(new HomeFakeAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, Home_item data) {
                        //此處進行item監聽事件的處理
                        ju_article=data.article;
                        ju_context= data.text_all;
                        ju_correct_or_fake=data.correct_or_fake;
                        ju_home_text_id=data.home_text_id;
                        System.out.println(data.home_text_id);
                        ju_love=data.love;
                        ju_search=data.search;
                        ju_source=data.source;
                        ju_key1=data.key1;
                        ju_key2=data.key2;
                        ju_key3=data.key3;
                        ju_face1=data.emo1;
                        ju_face2=data.emo2;
                        ju_face3=data.emo3;
                        Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_fragment_wordpage_result);
                    }
                });
                break;
        }
    }

    public interface OnButtonClick{
        public void onClick(View view);
    }
}
