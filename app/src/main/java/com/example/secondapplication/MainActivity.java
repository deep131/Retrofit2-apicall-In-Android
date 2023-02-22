package com.example.secondapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycle=findViewById(R.id.recyclerview);

        listingdata();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recycle.setLayoutManager(linearLayoutManager);
    }

    private void listingdata() {
        ApiInterface apiInterface=Retrofit.getRetrofit().create(ApiInterface.class);
        Call<List<Example>> listingdata=apiInterface.getData();
        listingdata.enqueue(new Callback<List<Example>>() {
            @Override
            public void onResponse(@NonNull Call<List<Example>> call, @NonNull Response<List<Example>> response) {
                if(response.isSuccessful()){

                    Recycleradapter adapter=new Recycleradapter(response.body());
                    recycle.setAdapter(adapter);
                    Log.d("api is success","Success");
                }


            }

            @Override
            public void onFailure(@NonNull Call<List<Example>> call, @NonNull Throwable t) {
                Log.d("api is success","Failure");
            }

        });
    }

    class Recycleradapter extends RecyclerView.Adapter<Recycleradapter.MyViewHolder>{

        List<Example> list;
        public  Recycleradapter(List<Example>list){

            this.list=list;
        }

        @NonNull
        @Override
        public Recycleradapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           @SuppressLint("InflateParams") View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Recycleradapter.MyViewHolder holder, int position) {
            holder.titles.setText("Hello");
    
            Picasso.with(getApplicationContext())
                    .load(list.get(position).getImage())
                    .placeholder(R.drawable.ic_launcher_background)
                    .fit()
                    .into(holder.imageView);



        }

        @Override
        public int getItemCount() {
            return list.size();
        }

         class MyViewHolder extends RecyclerView.ViewHolder{
            TextView id,titles,price,description,category,rate;
            ImageView imageView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                id=findViewById(R.id.ids);
                titles=findViewById(R.id.titles);
                price=findViewById(R.id.price);
                description=findViewById(R.id.description);
                category=findViewById(R.id.category);
                rate=findViewById(R.id.rate);
            }
        }
    }

}