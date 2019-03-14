package com.liangwei.kugouxia.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.show.api.ShowApiRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.adapter.AdapterAcrostic;
import com.liangwei.kugouxia.frame.AppConfig;
import com.liangwei.kugouxia.frame.ToastUtils;
import com.liangwei.kugouxia.frame.loading.WbLoadingDialog;

public class FragmentAcrostic extends Fragment {
    @BindView(R.id.fragment_acrostic_et_text) MaterialEditText et_text;
    @BindView(R.id.fragment_acrostic_rb_HowWord) RadioGroup radioGroup_howWord;
    @BindView(R.id.fragment_acrostic_rb_HowPosition) RadioGroup radioGroup_howPosition;
    @BindView(R.id.fragment_acrostic_rb_HowRhyme) RadioGroup radioGroup_howRhyme;
    @BindView(R.id.fragment_acrostic_rv) RecyclerView rv;
    private AdapterAcrostic adapter;
    private String howWord = "5";
    private String howPosition = "1";
    private String howRhyme = "1";
    List<String> acrostics = new ArrayList<>();
    @OnClick(R.id.fragment_acrostic_btnMake) public void make(){
        if(acrostics.size()!=0){
            acrostics.clear();
            adapter.notifyDataSetChanged();
        }
        WbLoadingDialog wbLoadingDialog = new WbLoadingDialog(getActivity(),"请求中",false);
        wbLoadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ShowApiRequest showApiRequest = (ShowApiRequest) new ShowApiRequest("http://route.showapi.com/950-1", AppConfig.SHOW_API_APP_ID, AppConfig.SHOW_API_APP_SECRET)
                        .addTextPara("num", howWord)
                        .addTextPara("type", howPosition)
                        .addTextPara("yayuntype", howRhyme)
                        .addTextPara("key", et_text.getText().toString());
                String res = showApiRequest.post();
                try {
                    parseJson(res);
                    wbLoadingDialog.dismiss();
                    Log.i("kgx",acrostics.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    wbLoadingDialog.dismiss();
                    ToastUtils.ShowToast(getActivity(),"错误："+e.toString());
                }
            }
        }).start();


    }
    public FragmentAcrostic() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_acrostic, container, false);
        ButterKnife.bind(this, view);
        /*获取radioGroupp 并且给howWord赋值*/
        radioGroup_howWord.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.fragment_acrostic_rb_fiveWord:
                        howWord = "5";
                        break;
                    case R.id.fragment_acrostic_rb_sevenWord:
                        howWord = "7";
                        break;
                }
            }
        });
        /*获取radioGroupp 并且给howPosition赋值*/
        radioGroup_howPosition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.fragment_acrostic_rb_head:
                        howPosition = "1";
                        break;
                    case R.id.fragment_acrostic_rb_foot:
                        howPosition = "2";
                        break;
                    case R.id.fragment_acrostic_rb_center:
                        howPosition = "3";
                        break;
                    case R.id.fragment_acrostic_rb_incrementing:
                        howPosition = "4";
                        break;
                    case R.id.fragment_acrostic_rb_diminishing:
                        howPosition = "5";
                        break;
                }
            }
        });
        /*获取radioGroupp 并且给howRhyme赋值*/
        radioGroup_howRhyme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.fragment_acrostic_rb_DoubleSentenceFormOne:
                        howRhyme = "1";
                        break;
                    case R.id.fragment_acrostic_rb_DoubleSentence:
                        howRhyme = "2";
                        break;
                    case R.id.fragment_acrostic_rb_OneThreeFour:
                        howRhyme = "3";
                        break;
                }
            }
        });
        initRecyclerView();
        return view;
    }
    private void initRecyclerView(){
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setNestedScrollingEnabled(true );
        adapter = new AdapterAcrostic(getActivity(),acrostics);
        rv.setAdapter(adapter);
    }

    /**
     * 解析返回的藏头诗数据
     * @param body
     * @throws JSONException
     */
    private void parseJson(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);
        JSONObject jsonObjectBody = jsonObject.getJSONObject("showapi_res_body");
        JSONArray jsonArrayAcrostics = jsonObjectBody.getJSONArray("list");
        for (int i=0;i<jsonArrayAcrostics.length();i++){
            acrostics.add(jsonArrayAcrostics.getString(i));
        }
        rv.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });

    }

}
