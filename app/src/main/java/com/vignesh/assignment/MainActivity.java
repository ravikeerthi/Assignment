package com.vignesh.assignment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_view)
    SwipeRefreshLayout swipeRefreshLayout;

    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        swipeRefreshLayout.setColorScheme(R.color.colorPrimaryDark, R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);
        getData();
    }

    private void getData() {
        swipeRefreshLayout.setRefreshing(true);
        fetchJSON();
    }

    private void fetchJSON(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        MInterface api = retrofit.create(MInterface.class);

        Call<String> call = api.getString();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body().toString());

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        writeRecycler(jsonresponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private void writeRecycler(String response){

        try {
            JSONObject obj = new JSONObject(response);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(obj.getString("title"));
            if(obj.optString("title").equals("About Canada"))
            {
                ArrayList<ModelOne> modelRecyclerArrayList = new ArrayList<>();
                JSONArray dataArray  = obj.getJSONArray("rows");

                for (int i = 0; i < dataArray.length(); i++)
                {
                    ModelOne modelRecycler = new ModelOne();
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    modelRecycler.setImageHref(dataobj.getString("imageHref"));
                    modelRecycler.setTitle(dataobj.getString("title"));
                    modelRecycler.setDescription(dataobj.getString("description"));
                    modelRecyclerArrayList.add(modelRecycler);
                }
                RetrofitOneAdapter retrofitAdapter = new RetrofitOneAdapter(this, modelRecyclerArrayList);
                recyclerView.setAdapter(retrofitAdapter);
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            }else {
                Toast.makeText(MainActivity.this, obj.optString("message")+"Nothing", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void onRefresh() {
        getData();
    }
}

