package com.example.carcrashdetection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

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
    TextView mEmptyListMessage;
    TextView noContacts;

    Cars cars;
    public static String number;
    private FirebaseUser user;
    private FirebaseAuth auth;
    String uid;


    public Home() {
    }

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

        event = new Event();
        eventsPlace = (RecyclerView) getView().findViewById(R.id.eventsplace);
        contactsView = (RecyclerView) getView().findViewById(R.id.contactsView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        contactsView.addItemDecoration(dividerItemDecoration);
//        databaseReference1.keepSynced(true);
//        databaseReference.keepSynced(true);
        mEmptyListMessage = (TextView) getActivity().findViewById(R.id.mEmptyListMessage);
        noContacts = (TextView) getActivity().findViewById(R.id.noContacts);
        auth = FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        uid = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cars").child(user.getUid());
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("phone").child(user.getUid());
        options1 = new FirebaseRecyclerOptions.Builder<contact>().setQuery(databaseReference1,contact.class).build();
        options = new FirebaseRecyclerOptions.Builder<Event>().setQuery(databaseReference,Event.class).build();

        adapter1 = new FirebaseRecyclerAdapter<contact, contactAdapter>(options1) {
            @Override
            protected void onBindViewHolder(@NonNull final contactAdapter holder, final int position, @NonNull final contact model) {
                holder.contactName.setText(model.getContactName());
                holder.contactPhone.setText(model.getContactPhone());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String key = adapter1.getRef(position).getKey();
                        Intent intent = new Intent(getActivity(), updateDelete.class);
                        intent.putExtra("contactName", model.getContactName());
                        intent.putExtra("contactPhone", model.getContactPhone());
                        intent.putExtra("key", key);
                        startActivity(intent);


                    }
                });
                SharedPreferences prefs =  Home.this.getActivity().getSharedPreferences("check", MODE_PRIVATE);
                String check_state = prefs.getString( "state", "default");
                if(check_state.equals("true"+String.valueOf(position))){
                    holder.chk.setChecked(true);
                }
                else if(check_state.equals("false"+String.valueOf(position))){
                    holder.chk.setChecked(false);
                }else{
                    holder.chk.setChecked(false);
                }
                holder.chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            holder.chk.setChecked(true);
                            for (int i = 0; i < adapter1.getItemCount(); i++) {
                                if (adapter1.getItem(i).isSelected(true)) {
                                    number = ((TextView) holder.itemView.findViewById(R.id.contactPhone)).getText().toString();
                                    Toast.makeText(getActivity(), "Data Inserted....." + number, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                                }
                            }
                            SharedPreferences.Editor editor = Home.this.getActivity().getSharedPreferences("check", MODE_PRIVATE).edit();
                            editor.putString("state", "true"+String.valueOf(position));
                            editor.apply();
                            adapter1.notifyDataSetChanged();
                        }else {
                            holder.chk.setChecked(false);
                            SharedPreferences.Editor editor =  Home.this.getActivity().getSharedPreferences("check", MODE_PRIVATE).edit();
                            editor.putString("state", "false"+String.valueOf(position));
                            editor.apply();
                            adapter1.notifyDataSetChanged();
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

        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noContacts.setVisibility(adapter1.getItemCount() == 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        adapter = new FirebaseRecyclerAdapter<Event, eventAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull eventAdapter holder, final int position, @NonNull final Event model) {
                holder.make.setText(model.getCarMake());
                holder.type.setText(model.getCarType());
                holder.year.setText(model.getCarYear());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String key = adapter.getRef(position).getKey();
                        Intent intent = new Intent(getActivity(), updateDeleteCars.class);
                        intent.putExtra("make", model.getCarMake());
                        intent.putExtra("type", model.getCarType());
                        intent.putExtra("year", model.getCarYear());
                        intent.putExtra("key", key);
                        startActivity(intent);
                    }
                });

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

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mEmptyListMessage.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }






}
