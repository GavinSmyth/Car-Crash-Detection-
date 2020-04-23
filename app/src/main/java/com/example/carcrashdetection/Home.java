package com.example.carcrashdetection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private RecyclerView eventsPlace;
    private View homeView;
    RecyclerView contactsView;
    private FirebaseRecyclerOptions<Event> options;
    private FirebaseRecyclerOptions<contact> options1;
    private FirebaseRecyclerAdapter<contact, contactAdapter>adapter1;
    private int lastCheckedPos = -1;
    private FirebaseRecyclerAdapter<Event, eventAdapter>adapter;
    contactAdapter ContactAdpater;
    List<contact> contactList;
    eventAdapter EventAdapter;
    private ArrayList<Event> eventList;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    Event event;
    Cars cars;
    public static String number;







    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.home, container, false);
        return homeView;



    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        adapter1.startListening();
        if (adapter == null) {
            System.out.print("Doesnt Exist");
        }
        if(adapter1 == null){
            System.out.print("Doesnt Exist");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
       adapter.stopListening();
       adapter1.stopListening();
    }



    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final SharedPreferences sharedPref = Home.this.getActivity().getPreferences(Context.MODE_PRIVATE);
        final boolean checked = sharedPref.getBoolean("checkbox", false);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cars");
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("phone");
        event = new Event();
        eventsPlace = (RecyclerView) getView().findViewById(R.id.eventsplace);
        contactsView = (RecyclerView) getView().findViewById(R.id.contactsView);
        databaseReference1.keepSynced(true);
        databaseReference.keepSynced(true);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final SharedPreferences.Editor editor = preferences.edit();
        options1 = new FirebaseRecyclerOptions.Builder<contact>().setQuery(databaseReference1,contact.class).build();
        options = new FirebaseRecyclerOptions.Builder<Event>().setQuery(databaseReference,Event.class).build();
        adapter1 = new FirebaseRecyclerAdapter<contact, contactAdapter>(options1) {
            @Override
            protected void onBindViewHolder(@NonNull final contactAdapter holder, final int position, @NonNull contact model) {
                holder.contactName.setText(model.getContactName());
                holder.contactPhone.setText(model.getContactPhone());
                if(lastCheckedPos == position){
                    holder.chk.setChecked(true);
                }
                else{
                    holder.chk.setChecked(false);
                }
                holder.chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (position == lastCheckedPos) {
                            holder.chk.setChecked(checked);
                            lastCheckedPos = -1;
                        }else {
                            lastCheckedPos = position;
                            for (int i = 0; i < adapter1.getItemCount(); i++) {
                                if (adapter1.getItem(i).isSelected(true)) {
                                    number = ((TextView) holder.itemView.findViewById(R.id.contactPhone)).getText().toString();
                                    Toast.makeText(getActivity(), "Data Inserted....." + number, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                                }
                            }
                            contactsView.post(new Runnable()
                            {
                                @Override
                                public void run() {
                                    adapter1.notifyDataSetChanged();
                                }
                            });

                        }
                    }
                });
            }

            @NonNull
            @Override
            public contactAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new contactAdapter(LayoutInflater.from(getActivity()).inflate(R.layout.item_contacts, parent, false));

            }
        };
        contactsView.setAdapter(adapter1);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        contactsView.setLayoutManager(layoutManager1);






        adapter = new FirebaseRecyclerAdapter<Event, eventAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull eventAdapter holder, int position, @NonNull final Event model) {
                holder.make.setText(model.getCarMake());
                holder.type.setText(model.getCarType());
                holder.year.setText(model.getCarYear());

//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getActivity(), contact.class);
//                        intent.putExtra("make", model.getCarMake());
//                        intent.putExtra("type", model.getCarType());
//                        intent.putExtra("year", model.getCarYear());
//                        startActivity(intent);
//                    }
//                });

            }

            @NonNull
            @Override
            public eventAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new eventAdapter(LayoutInflater.from(getActivity()).inflate(R.layout.item_event, parent, false));

            }
        };
        eventsPlace.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        eventsPlace.setLayoutManager(layoutManager);









    }



}
