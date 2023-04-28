package com.example.mynewsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface{
private RecyclerView newsRV, categoryRV;
private ProgressBar loadingPB;
private ArrayList<Articles> articlesArrayList;
private ArrayList<CategoryRVModal> categoryRVModalArrayList;
private CategoryRVAdapter categoryRVAdapter;
private NewsRVAdapter newsRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRV=findViewById(R.id.idRVNews);
        categoryRV=findViewById(R.id.idRVCategories);
        loadingPB=findViewById(R.id.idPBLoading);
        categoryRVModalArrayList=new ArrayList<>();
        articlesArrayList = new ArrayList<>(); // initialize the ArrayList
        newsRVAdapter= new NewsRVAdapter(articlesArrayList,this);
        categoryRVAdapter=new CategoryRVAdapter(categoryRVModalArrayList,this,this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategories();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();
    }

    private void getCategories(){
        categoryRVModalArrayList.add(new CategoryRVModal("All","https://images.unsplash.com/photo-1495020689067-958852a7765e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjB8fGFsbCUyMG5ld3N8ZW58MHx8MHx8&auto=format&fit=crop&w=600&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology","https://images.unsplash.com/photo-1461749280684-dccba630e2f6?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTl8fHRlY2glMjBuZXdzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=600&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science","https://images.unsplash.com/photo-1513624954087-ca7109c0f710?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OXx8c2NpZW5jZSUyMG5ld3N8ZW58MHx8MHx8&auto=format&fit=crop&w=600&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports","https://images.unsplash.com/photo-1609867026696-7b08408e874b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8c3BvcnRzJTIwbmV3c3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=600&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("General","https://images.unsplash.com/photo-1485115905815-74a5c9fda2f5?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8Z2VuZXJhbCUyMG5ld3N8ZW58MHx8MHx8&auto=format&fit=crop&w=600&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Business","https://images.unsplash.com/photo-1460925895917-afdab827c52f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8Z2VuZXJhbCUyMG5ld3N8ZW58MHx8MHx8&auto=format&fit=crop&w=600&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment","https://images.unsplash.com/photo-1596482046040-6fb45b3abd42?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8ZW50ZXJ0YWlubWVudCUyMG5ld3N8ZW58MHx8MHx8&auto=format&fit=crop&w=600&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Health","https://images.unsplash.com/photo-1636629198288-8fe85b92110a?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fGhlYWx0aCUyMG5ld3N8ZW58MHx8MHx8&auto=format&fit=crop&w=600&q=60"));
    categoryRVAdapter.notifyDataSetChanged();
    }
    public void getNews(String category){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL="https://newsapi.org/v2/top-headlines?country=in&category="+category+"&apiKey=6eeedba56c7b4c069feaa8e3fef1c0b0";
        String url="https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apikey=6eeedba56c7b4c069feaa8e3fef1c0b0";
        String Base_URL="https://newsapi.org/";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI= retrofit.create(RetrofitAPI.class);
        Call<NewsModal>call;
        if(category.equals("All")){
            call=retrofitAPI.getAllNews(url);

        }
        else {
            call=retrofitAPI.getNewsByCategory(categoryURL);
        }
call.enqueue(new Callback<NewsModal>() {
    @Override
    public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
            NewsModal newsModal=response.body();
            loadingPB.setVisibility(View.GONE);
            ArrayList<Articles> articles=newsModal.getArticles();
            for (int i=0; i<articles.size(); i++){
               articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),
                       articles.get(i).getUrl(),articles.get(i).getContent()));
            }
newsRVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Call<NewsModal> call, Throwable t) {
        Toast.makeText(MainActivity.this,"failed to get text", Toast.LENGTH_SHORT).show();
    }
});
    }
    @Override
    public void onCategoryClick(int position) {
        String category=categoryRVModalArrayList.get(position).getCategory();
        getNews(category);
    }
}