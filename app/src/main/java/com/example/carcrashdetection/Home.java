package com.example.carcrashdetection;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    RecyclerView eventsPlace;

    eventAdapter EventAdapter;
    List<Event> eventList;
    private SensorManager sm;
    private float acelVal; // current acceleration value and gravity
    private float acelLast; //Last acceloration value and gravity
    private float shake;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home, container, false);



    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        eventsPlace = (RecyclerView) getView().findViewById(R.id.eventsplace);
        eventList = new ArrayList<>();
        eventList.add(
                new Event(
                        "Mercedes",
                        "Saloon",
                        "2004"
                )
        );
        eventList.add(
                new Event(
                        "Mercedes",
                        "Saloon",
                        "2004"
                )
        );
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        eventsPlace.setLayoutManager(linearLayoutManager);
        eventsPlace.setHasFixedSize(true);

        EventAdapter = new eventAdapter(getActivity(), eventList);
        eventsPlace.setAdapter(EventAdapter);
        final SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(eventsPlace);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //DO Something ater 1ms = 100ms
                RecyclerView.ViewHolder viewHolderDefault = eventsPlace.findViewHolderForAdapterPosition(0);
                LinearLayout eventparentDefault = viewHolderDefault.itemView.findViewById(R.id.eventParent);
                eventparentDefault.animate().scaleY(1).scaleX(1).setDuration(350).setInterpolator(new AccelerateInterpolator()).start();
                LinearLayout eventcategoryDefault = viewHolderDefault.itemView.findViewById(R.id.eventbadge);
                eventcategoryDefault.animate().alpha(1).setDuration(300).start();


            }
            },100);


        //set a timer for defaul item
        eventsPlace.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    View view = snapHelper.findSnapView(linearLayoutManager);
                    int pos = linearLayoutManager.getPosition(view);
                    RecyclerView.ViewHolder viewHolder = eventsPlace .findViewHolderForAdapterPosition(pos);

                    LinearLayout eventParent  = viewHolder.itemView.findViewById(R.id.eventParent);
                    eventParent.animate().scaleX(1).scaleY(1).setDuration(350).setInterpolator(new AccelerateInterpolator()).start();
                    LinearLayout eventcategory = viewHolder.itemView.findViewById(R.id.eventbadge);
                    eventcategory.animate().alpha(1).setDuration(300).start();

                }else{
                    View view = snapHelper.findSnapView(linearLayoutManager);
                    int pos = linearLayoutManager.getPosition(view);
                    RecyclerView.ViewHolder viewHolder = eventsPlace .findViewHolderForAdapterPosition(pos);

                    LinearLayout eventParent  = viewHolder.itemView.findViewById(R.id.eventParent);
                    eventParent.animate().scaleX(0.6f).scaleY(0.6f).setDuration(350).setInterpolator(new AccelerateInterpolator()).start();
                    LinearLayout eventcategory = viewHolder.itemView.findViewById(R.id.eventbadge);
                    eventcategory.animate().alpha(1).setDuration(300).start();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });




    }


}
