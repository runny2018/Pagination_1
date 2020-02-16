package com.example.pagination_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager manager;
    Adapter adapter;

    ArrayList list;

    Boolean isScrolling=false;
    int currentItems, totalItems, scrollOutItems; //scrollOutItems is scrolledOutItems


    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);
        manager=new LinearLayoutManager(this);

        String[] a={"14","26", "35","21","59","43", "90", "65", "78", "23","29","35","86","27","54"
        ,"29","10","20","30"};
        list=new ArrayList(Arrays.asList(a));

        adapter=new Adapter(list, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);



        progressBar=findViewById(R.id.progress);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling=true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems=manager.getChildCount();   //number of items visible
                totalItems=manager.getItemCount();      //total number of items
                scrollOutItems=manager.findFirstVisibleItemPosition();     //items that are above and are not visible

                if (isScrolling && (currentItems+scrollOutItems==totalItems))
                {
                    isScrolling=false;
                    fetchData();
                }
            }
        });
    }



    private void fetchData(){
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<5;i++)
                {
                    list.add(Math.floor(Math.random()*100)+"");
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, 5000);
    }
}
