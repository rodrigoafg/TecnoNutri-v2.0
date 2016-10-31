package com.example.rodri.testetecnonutri;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rodri on 30/10/2016.
 * Classe principal, deveria estar mais limpa, classes de acesso a dados deveriam estar separadas.
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String strUrl = "http://api.tecnonutri.com.br/api/v4/feed";
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(strUrl);
        mRecyclerView = (RecyclerView) findViewById(R.id.feed_recycle_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(getApplicationContext(), new ArrayList());
        mRecyclerView.setAdapter(mAdapter);

    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();

        } catch (Exception e) {
            Log.d("Exception while DL url", e.toString());
        } finally {
            iStream.close();
        }

        return data;
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {
        String data = null;

        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {

            ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();
            listViewLoaderTask.execute(result);
        }
    }

    private class ListViewLoaderTask extends AsyncTask<String, Void, MyAdapter> {

        JSONObject jObject;

        @Override
        protected MyAdapter doInBackground(String... strJson) {
            try {
                jObject = new JSONObject(strJson[0]);
                ItemJSONParser itemJsonParser = new ItemJSONParser();
                itemJsonParser.parse(jObject);
            } catch (Exception e) {
                Log.d("JSON Exception1", e.toString());
            }

            ItemJSONParser itemJsonParser = new ItemJSONParser();

            List<HashMap<String, Object>> posts = null;

            try {
                posts = itemJsonParser.parse(jObject);
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }

            MyAdapter adapter = new MyAdapter(getBaseContext(), posts);
            return adapter;
        }

        @Override
        protected void onPostExecute(MyAdapter adapter) {
            mRecyclerView.setAdapter(adapter);

        }
    }
}

