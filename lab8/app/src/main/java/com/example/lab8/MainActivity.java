package com.example.lab8;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;
import org.json.*;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView text;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.text=findViewById(R.id.textView);
        this.img = findViewById(R.id.imageView);
        OkHttpClient client = new OkHttpClient() ;
        String url = "https://picsum.photos/v2/list?page=3&limit=1";
        final Request request = new  Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String res = response.body().string();
                    res= res.substring(1, res.length()-2);
                    try {
                        final JSONObject picture = new JSONObject(res);

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run(){
                                try{
                                text.setText(picture.getString("download_url"));
                               //Picasso.with(MainActivity.this).load("https://i.picsum.photos/id/835/200/300.jpg?hmac=P0UE0lbf-pAbtXKtn98GDElmyOKaljrh5G9Ga20LObw").into(img);
                              Picasso.with(MainActivity.this).load(picture.getString("download_url")).into(img);

                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text.setText("Could not fetch data");
                        }
                    });
                }
            }
        });
    }

}