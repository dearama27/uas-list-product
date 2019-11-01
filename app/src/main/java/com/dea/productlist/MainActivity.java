package com.dea.productlist;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dea.productlist.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<Product> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView) findViewById(R.id.rv_container) ;

        load();  // ambil data api
    }

    private void load() {
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
//        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=53d501a66e5727848b4593a55939f9cc&language=en-US";
        String url = "https://demo.perkasafurniture.com/index.php?route=api/product&api_token&limit=20";

        Log.i (String.valueOf(R.string.app_name), url); //lihat url

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //konversi ke ARRAY
                        try{
                            JSONArray json = response.getJSONArray("products");
                        //siapkan class now playing
                        list = new ArrayList<>();

                        /* rincikan array pada now playing dan bersiap untuk menampung ke class
                        catatan : class berfungsi mirip seperti database hanya saja bersifat sementara
                         */

                        //siapkan variable
                            if (json.length() > 0){
                                for (int i = 0; i < json.length(); i++){
                                    JSONObject data = json.getJSONObject(i);

                                    //masukkan kedalam class
                                    list.add(new Product(
                                            data.getString("name").toString().trim(),
                                            data.getString("thumb").toString().trim(),
                                            data.getString("price").toString().trim(),
                                            data.getString("description").toString().trim()
                                    ));
                                }
                                showRecyclerView(list);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //lihat hasil yang diterima dari API
                        Log.i(String.valueOf(R.string.app_name), String.valueOf(response));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) { //time out or there is no connection
                    Toast.makeText(getBaseContext(), "Time out. try again", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) { //there was an Authentication Failure
                    Toast.makeText(getBaseContext(), "Auth failed", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) { //server responded with a error response
                    Toast.makeText(getBaseContext(), "Server problem", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {//network error while performing the request
                    Toast.makeText(getBaseContext(), "Please check your connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {//the server response could not be parsed
                    Toast.makeText(getBaseContext(), "Reading data failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
        queue.add(jsObjRequest);
    }

    private void showRecyclerView(ArrayList list) {
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        ProductAdapter adapter = new ProductAdapter(list, MainActivity.this);
        rv.setAdapter(adapter);
    }

}
