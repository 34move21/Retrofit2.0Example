package norakomi.retrofit20example;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends ListActivity implements Callback<SovietArtMePosters> {

    private final String BASE_URL = "http://www.norakomi.com/assets/json";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<Poster> arrayAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new ArrayList<Poster>());
        setListAdapter(arrayAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        SovietArtMeAPI api = retrofit.create(SovietArtMeAPI.class);

        Call<SovietArtMePosters> call = api.loadPosters();
        //asynchronous call
        call.enqueue(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onResponse(Response<SovietArtMePosters> response, Retrofit retrofit) {
        ArrayAdapter<Poster> adapter = (ArrayAdapter<Poster>) getListAdapter();
        adapter.clear();
        adapter.addAll(response.body().posters);
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}

