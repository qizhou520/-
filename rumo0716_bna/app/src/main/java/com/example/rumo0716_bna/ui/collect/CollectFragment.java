package com.example.rumo0716_bna.ui.collect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rumo0716_bna.R;
import com.example.rumo0716_bna.ui.LoginFragment;
import com.example.rumo0716_bna.ui.adapter.CollectRecycleAdapter;
import com.example.rumo0716_bna.ui.home.HomeFragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CollectFragment extends Fragment {
    private View view;//定義view用來設定fragment的layout
    public RecyclerView mCollectRecyclerView;//定義RecyclerView
    //定義以collectArticles實體類為物件的資料集合
    private ArrayList<CollectArticles> collectArticlesList = new ArrayList<CollectArticles>();
    private ArrayList<CollectArticles> recommendArticlesList = new ArrayList<CollectArticles>();
    //自定義recyclerveiw的介面卡
    private CollectRecycleAdapter mCollectRecyclerAdapter;

    String username="yen";//使用者帳號
    String result1, result2, result3;//儲存資料用的字串
    String alert_id,wrong_id;
    private int id;//儲存文章id
    String[] keys;//儲存蒐藏文章關鍵字
    HashMap<String, Integer> map= new HashMap<String, Integer>();

    int alert_num=0;//正確數量
    int wrong_num=0;//錯誤數量

    TextView alertnum, wrongnum;

    private CollectViewModel collectViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //獲取fragment的layout
            view = inflater.inflate(R.layout.fragment_collect, container, false);
            if(LoginFragment.usid==null||LoginFragment.usid==""){
            }
            if(LoginFragment.urcorrect==null)LoginFragment.urcorrect="";
            if(LoginFragment.urfake==null)   LoginFragment.urfake="";
            alertnum = (TextView) view.findViewById(R.id.alertnum);
            wrongnum = (TextView) view.findViewById(R.id.wrongnum);
            //對recycleview進行配置
                initRecyclerView_collect();
            //模擬資料
             if(LoginFragment.urcorrect!=""||LoginFragment.urfake!="") {
                initData_collect();
            }
            alertnum.setText(alert_num + "");
            wrongnum.setText(wrong_num + "");
            //對recycleview進行配置
            initRecyclerView_recommend();
            //模擬資料
        if(LoginFragment.urcorrect!=""||LoginFragment.urfake!="") {
            initData_recommend();
        }
        return view;
    }
    /**
     *  模擬資料
     */

    private void initData_collect() { //去資料庫撈
        collectArticlesList.clear();
        alert_id=LoginFragment.urcorrect;
        wrong_id=LoginFragment.urfake;
        alert_num=alert_id.split("\\.").length;
        wrong_num=wrong_id.split("\\.").length;
        if(alert_id=="")alert_num=0;
        if(wrong_id=="")wrong_num=0;
        int type=0, cnt=0;
        String imgPath="";
        keys=new String[(alert_num+wrong_num)*3];
        for (int i=0;i<alert_num+wrong_num;i++){
            if(i<alert_num){
                if(alert_num!=0&&alert_id!=""){
                id=Integer.parseInt(alert_id.split("\\.")[i]);
                type=0;
                imgPath="alert";}
            }else{
                if(wrong_num!=0&&wrong_id!=""){
                id=Integer.parseInt(wrong_id.split("\\.")[i-alert_num]);
                type=1;
                imgPath="wromg";}
            }
            if(imgPath.equals("alert")){setResult2(true);}
            else if(imgPath.equals("wromg")){setResult2(false);}
            CollectArticles collectArticles=new CollectArticles();
            collectArticles.id=id;
            collectArticles.setImgPath(imgPath);
            collectArticles.setSource(result2.split(",")[1].split(":")[1].split("\"")[1]);
            collectArticles.setContent(result2.split(",")[2].split(":")[1].substring(1,20)+"...");
            for(int j=3;j<=5;j++){
                keys[cnt++]=result2.split(",")[j].split(":")[1].replace("\"","");
            }
            collectArticlesList.add(collectArticles);
        }
    }
    private void setResult2(boolean a){
        if(a){
            String[] ss=HomeFragment.str1;
            for(int i=0;i<ss.length;i++){
                if(Integer.parseInt(ss[i].split(",")[0].split(":")[1].split("\"")[1])==id){
                    result2=ss[i];
                }
            }
        }
        else{
            String[] ss=HomeFragment.str;
            for(int i=0;i<ss.length;i++){
                if(Integer.parseInt(ss[i].split(",")[0].split(":")[1].split("\"")[1])==id){
                   result2=ss[i];
                }
            }
        }
    }


    private class articleThread implements Runnable{
        int id;
        int type;
        public articleThread(int id,int type) {
            this.id=id;
            this.type=type;
        }

        @Override
        public void run() {
            try {
                URL url;
                if(type==0){
                    url = new URL("http://192.168.43.17/GetCorrect.php");
                }else{
                    url = new URL("http://192.168.43.17/GetFake.php");
                }
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的

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
                if(responseCode ==
                        HttpURLConnection.HTTP_OK){
                    // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                    InputStream inputStream =
                            connection.getInputStream();
                    // 取得輸入串流
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    // 讀取輸入串流的資料
                    String box = ""; // 宣告存放用字串
                    String line = null; // 宣告讀取用的字串
                    while((line = bufReader.readLine()) != null) {
                        String[] ss=line.split("\\}\\,\\{");
                        for(int i=0;i<ss.length;i++){
                            if(Integer.parseInt(ss[i].split(",")[0].split(":")[1].split("\"")[1])==id){
                                if(i==0){
                                    box=ss[i].split("\\{")[1];
                                }else if(i==ss.length-1){
                                    box=ss[i].split("\\}")[0];
                                }else{
                                    box=ss[i];
                                }
                                break;
                            }
                        }

                    }
                    inputStream.close(); // 關閉輸入串流
                    result2 = box; // 把存放用字串放到全域變數

                }
            } catch(Exception e) {
                result2 = e.toString(); // 如果出事，回傳錯誤訊息
            }
        }
    }

    /**
     *  對recycleview進行配置
     */

    private void initRecyclerView_collect() {
        //獲取RecyclerView
        mCollectRecyclerView=(RecyclerView)view.findViewById(R.id.collect_recyclerView);
        //設定layoutManager,可以設定顯示效果，是線性佈局、grid佈局，還是瀑布流佈局
        //引數是：上下文、列表方向（橫向還是縱向）、是否倒敘
        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //設定article的分割線
        mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        //建立adapter
        mCollectRecyclerAdapter = new CollectRecycleAdapter(getActivity(), collectArticlesList);
        //給RecyclerView設定adapter
        mCollectRecyclerView.setAdapter(mCollectRecyclerAdapter);
        //RecyclerView中沒有item的監聽事件，需要自己在介面卡中寫一個監聽事件的介面。引數根據自定義
        mCollectRecyclerAdapter.setOnItemClickListener(new CollectRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, CollectArticles data) {
                //此處進行監聽事件的業務處理
                String str[];
                boolean a;
                if(data.getImgPath().equals("alert")){
                    str=HomeFragment.str1;
                    a=true;
                }
                else{
                    str=HomeFragment.str;
                    a=false;
                }
                String ss1[]=str[data.id-1].split(",");
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
                HomeFragment.ju_correct_or_fake =a;
                HomeFragment.ju_key1=ss1[3].replace("KEY1","").replace("\"","").replace(":","");
                HomeFragment.ju_key2=ss1[4].replace("KEY2","").replace("\"","").replace(":","");
                HomeFragment.ju_key3=ss1[5].replace("KEY3","").replace("\"","").replace(":","");
                HomeFragment.ju_face1=ss1[8].replace("Face1","").replace("\"","").replace(":","");
                HomeFragment.ju_face2=ss1[9].replace("Face2","").replace("\"","").replace(":","");
                HomeFragment.ju_face3=ss1[10].replace("Face3","").replace("\"","").replace(":","");

                Navigation.findNavController(view).navigate(R.id.action_navigation_collect_to_fragment_wordpage_result);
            }
        });
    }

    private void initData_recommend() { //去資料庫撈
        recommendArticlesList.clear();
        Thread t = new Thread(recommendThread);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        String[] re=new String[3];
        int cnt=0;
        for (int i=0;i<map.size(); i++) {
            boolean a=false;
            String rlt=list.get(i).getKey().split(":")[0];
            String re_id=list.get(i).getKey().split(":")[1];
            if(rlt.equals("alert")){
                for(int j=0;j<alert_num;j++){
                    if(Integer.parseInt(re_id)==Integer.parseInt(alert_id.split("\\.")[j])){
                        a=true;
                    }
                }
            }else if(rlt.equals("wrong")){
                for(int j=0;j<wrong_num;j++){
                    if(Integer.parseInt(re_id)==Integer.parseInt(wrong_id.split("\\.")[j])){
                        a=true;
                    }
                }
            }
            if(!(a)){
                re[cnt++]=list.get(i).getKey()+ ","+list.get(i).getValue();
            }
            if(cnt>=3){
                break;
            }
        }
        for (int i=0;i<3;i++){
            int type=0;
            if(re[i].split(":")[0].equals("wrong")){
                type=1;
            }
            articleThread articleThread = new articleThread(Integer.parseInt(re[i].split(":")[1].split(",")[0]),type);
            Thread ret = new Thread(articleThread);
            ret.start();
            try {
                ret.join();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            CollectArticles recommendArticles=new CollectArticles();
            if(type==0){
                recommendArticles.setImgPath("alert");
            }else if(type==1){
                recommendArticles.setImgPath("wrong");
            }
            recommendArticles.setSource(result2.split(",")[1].split(":")[1].split("\"")[1]);
            recommendArticles.setContent(result2.split(",")[2].split(":")[1].substring(1,20)+"...");
            recommendArticlesList.add(recommendArticles);
        }
    }
    private Runnable recommendThread = new Runnable(){
        public void run()
        {
            try {
                URL url = new URL("http://192.168.43.17/GetCorrect.php");
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
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
                if(responseCode ==
                        HttpURLConnection.HTTP_OK){
                    // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                    InputStream inputStream =
                            connection.getInputStream();
                    // 取得輸入串流
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    // 讀取輸入串流的資料
                    String line = null; // 宣告讀取用的字串

                    while((line = bufReader.readLine()) != null) {
                        String[] ss=line.split("\\}\\,\\{");
                        for(int i=1;i<=ss.length;i++){
                            int num=0;
                            String[] key={ss[i].split(",")[3].split(":")[1].replace("\"",""),ss[i].split(",")[4].split(":")[1].replace("\"",""),ss[i].split(",")[5].split(":")[1].replace("\"","")};
                            for(int j=0;j<keys.length;j++){
                                for(int k=0;k<3;k++){
                                    if(key[k].equals(keys[j])){
                                        num++;
                                    }
                                }
                            }
                            map.put("alert:"+i,num);
                        }
                    }
                    inputStream.close(); // 關閉輸入串流

                }
            } catch(Exception e) {
                result3 = e.toString(); // 如果出事，回傳錯誤訊息
            }
            try {
                URL url = new URL("http://192.168.43.17/GetFake.php");
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
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
                if(responseCode ==
                        HttpURLConnection.HTTP_OK){
                    // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                    InputStream inputStream =
                            connection.getInputStream();
                    // 取得輸入串流
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    // 讀取輸入串流的資料
                    String box = ""; // 宣告存放用字串
                    String line = null; // 宣告讀取用的字串

                    while((line = bufReader.readLine()) != null) {
                        String[] ss=line.split("\\}\\,\\{");
                        for(int i=1;i<=ss.length;i++){
                            int num=0;
                            String[] key={ss[i].split(",")[3].split(":")[1].replace("\"",""),ss[i].split(",")[4].split(":")[1].replace("\"",""),ss[i].split(",")[5].split(":")[1].replace("\"","")};
                            for(int j=0;j<keys.length;j++){
                                for(int k=0;k<3;k++){
                                    if(key[k].equals(keys[j])){
                                        num++;
                                    }
                                }
                            }
                            map.put("wrong:"+i,num);
                        }
                    }
                    inputStream.close(); // 關閉輸入串流
                }
            } catch(Exception e) {
                result3 = e.toString(); // 如果出事，回傳錯誤訊息
            }
        }
    };
    private void initRecyclerView_recommend() {
        //獲取RecyclerView
        mCollectRecyclerView=(RecyclerView)view.findViewById(R.id.recommend_recyclerview);
        //建立adapter
        mCollectRecyclerAdapter = new CollectRecycleAdapter(getActivity(), recommendArticlesList);
        //給RecyclerView設定adapter
        mCollectRecyclerView.setAdapter(mCollectRecyclerAdapter);
        //設定layoutManager,可以設定顯示效果，是線性佈局、grid佈局，還是瀑布流佈局
        //引數是：上下文、列表方向（橫向還是縱向）、是否倒敘
        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //設定article的分割線
        mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        //RecyclerView中沒有item的監聽事件，需要自己在介面卡中寫一個監聽事件的介面。引數根據自定義
        mCollectRecyclerAdapter.setOnItemClickListener(new CollectRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, CollectArticles data) {
                Toast.makeText(getContext(),"此功能尚未開放",Toast.LENGTH_SHORT).show();
                //此處進行監聽事件的業務處理
                /*
                String str[];
                boolean a;
                if(data.getImgPath().equals("alert")){
                    str=HomeFragment.str1;
                    a=true;
                }
                else{
                    str=HomeFragment.str;
                    a=false;
                }
                String ss1[]=str[data.id-1].split(",");
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
                HomeFragment.ju_correct_or_fake =a;
                HomeFragment.ju_key1=ss1[3].replace("KEY1","").replace("\"","").replace(":","");
                HomeFragment.ju_key2=ss1[4].replace("KEY2","").replace("\"","").replace(":","");
                HomeFragment.ju_key3=ss1[5].replace("KEY3","").replace("\"","").replace(":","");
                HomeFragment.ju_face1=ss1[8].replace("Face1","").replace("\"","").replace(":","");
                HomeFragment.ju_face2=ss1[9].replace("Face2","").replace("\"","").replace(":","");
                HomeFragment.ju_face3=ss1[10].replace("Face3","").replace("\"","").replace(":","");
                Navigation.findNavController(view).navigate(R.id.action_navigation_collect_to_fragment_wordpage_result);*/
            }
        });
    }


}


