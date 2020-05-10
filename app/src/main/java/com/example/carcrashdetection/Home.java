package com.example.carcrashdetection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

public class Home extends Fragment {

    private RecyclerView eventsPlace;
    private View homeView;
    RecyclerView contactsView;
    private FirebaseRecyclerOptions<Event> options;
    private FirebaseRecyclerOptions<contact> options1;
    private FirebaseRecyclerAdapter<contact, contactAdapter>adapter1;
    private FirebaseRecyclerAdapter<Event, eventAdapter>adapter;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    Event event;
    TextView mEmptyListMessage;
    TextView noContacts;
    ImageView emptyContacts;
    ImageView emptyCars;
    Button startsCar;
    public int start = 1;
    public static String number;
    public static String name;
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
//telling the adapter to startlistening to firebase to get the data.
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

//telling the adpter to stop listening to firebase.
    @Override
    public void onStop() {
        super.onStop();
       adapter.stopListening();
       adapter1.stopListening();
    }



    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        event = new Event();
        eventsPlace = (RecyclerView) getView().findViewById(R.id.eventsPlace);
        contactsView = (RecyclerView) getView().findViewById(R.id.contactsView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        contactsView.addItemDecoration(dividerItemDecoration);
//        databaseReference1.keepSynced(true);
//        databaseReference.keepSynced(true);
        startsCar = (Button) getActivity().findViewById(R.id.startsCar);
        emptyCars = (ImageView) getActivity().findViewById(R.id.emptyCars);
        emptyContacts = (ImageView) getActivity().findViewById(R.id.emptyContacts);
        mEmptyListMessage = (TextView) getActivity().findViewById(R.id.mEmptyListMessage);
        noContacts = (TextView) getActivity().findViewById(R.id.noContacts);
        auth = FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        uid = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cars").child(user.getUid());
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("phone").child(user.getUid());
        options1 = new FirebaseRecyclerOptions.Builder<contact>().setQuery(databaseReference1,contact.class).build();
        options = new FirebaseRecyclerOptions.Builder<Event>().setQuery(databaseReference,Event.class).build();

        //this is a firebaserecycler adapeter used for getting the contact information added to firebase by the user and pulling
        //it down into the home fragment
        adapter1 = new FirebaseRecyclerAdapter<contact, contactAdapter>(options1) {
            @Override
            protected void onBindViewHolder(@NonNull final contactAdapter holder, final int position, @NonNull final contact model) {
                holder.contactName.setText(model.getContactName());
                holder.contactPhone.setText(model.getContactPhone());

                //the is is so that each item in the recyclerview can be clicked. It will then bring you to the updatDelete class
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
                //setting up the sharepreference so the clicked state is saved when you exit the app
                SharedPreferences prefs =  Home.this.getActivity().getSharedPreferences("check", MODE_PRIVATE);
                String check_state = prefs.getString( "state", "default");

                //this allows user to only click onto one contact in the recyclerview
                if(check_state.equals("true"+String.valueOf(position))){
                    holder.chk.setChecked(true);
                }
                else if(check_state.equals("false"+String.valueOf(position))){
                    holder.chk.setChecked(false);
                }else{
                    holder.chk.setChecked(false);
                }

                //starting some actions for when an item is checked
                holder.chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            holder.chk.setChecked(true);
                            //if an item is checked then loop through the item to get the number textfeild and add it to string number
                            for (int i = 0; i < adapter1.getItemCount(); i++) {
                                if (adapter1.getItem(i).isSelected(true)) {
                                    number = ((TextView) holder.itemView.findViewById(R.id.contactPhone)).getText().toString();
                                    Toast.makeText(getActivity(), "Data Inserted....." + number, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                                }
                            }
                            //Adding the item state to the shared preference and updating adapter/ this way state is saved when exiting the app
                            //saving the number to sharedpreference as it is being used in the alert activity to send a message
                            SharedPreferences.Editor editor = Home.this.getActivity().getSharedPreferences("check", MODE_PRIVATE).edit();
                            editor.putString("state", "true"+String.valueOf(position));
                            editor.putString("number", number);
                            editor.apply();
                            adapter1.notifyDataSetChanged();
                        }else {
                            //setting the items that are unchecked and updating the adapter
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
        //adding the recyclerview to the activity
        contactsView.setAdapter(adapter1);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        contactsView.setLayoutManager(layoutManager1);

        //if recyclerview is empty a picture and a message shows. when items are in the recyclerview the image and the textview are not visible
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noContacts.setVisibility(adapter1.getItemCount() == 0 ? View.VISIBLE : View.GONE);
                emptyContacts.setVisibility(adapter1.getItemCount() == 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //this is the firebaserecyclerview adapter for the cars items
        adapter = new FirebaseRecyclerAdapter<Event, eventAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final eventAdapter holder, final int position, @NonNull final Event model) {

                holder.make.setText(model.getCarMake());
                holder.type.setText(model.getCarType());
                holder.year.setText(model.getCarYear());
                holder.itemView.animate().scaleX(1f);
                holder.itemView.animate().scaleY(1f);


                final SharedPreferences sharedPreferences =  Home.this.getActivity().getSharedPreferences("check", MODE_PRIVATE);
                final String state = sharedPreferences.getString( "key", "default");
                //Savin the state of the cars. if sevice is turned on button on the car item will say "stop car" else "Start Car"
                if(!state.equals("false")){
                    holder.startsCar.setText("Stop Car");
                }else {
                    holder.startsCar.setText("Start Car");
                }
                holder.startsCar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(state.equals("false")){
                            //starting up the foreground service needed to read the car crash
                            holder.startsCar.setText("Stop Car");
                            Intent i = new Intent(Home.this.getActivity(), MQTTService.class);
                            //looping through the item to get the name of car to be included in the message sent to emergency contact
                            ContextCompat.startForegroundService(Home.this.getActivity(), i);
                            for (int j = 0; j < adapter.getItemCount(); j++) {
                                if (adapter.getItem(j).isSelected(true)) {
                                     name = ((TextView) holder.itemView.findViewById(R.id.make)).getText().toString();
                                } else {
                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                                }
                            }
                            //saving name and car state to a sharepreference
                            SharedPreferences.Editor editor = Home.this.getActivity().getSharedPreferences("check", MODE_PRIVATE).edit();
                            editor.putString("key", "true");
                            editor.putString("name", name);
                            editor.apply();
                            adapter.notifyDataSetChanged();
                            //Toast.makeText(Home.this.getContext(), "Service Started", Toast.LENGTH_SHORT).show();
                        }else{
                            //saving name and car state to a sharepreference when service is off
                            holder.startsCar.setText("Start Car");
                            Intent i = new Intent(Home.this.getActivity(), MQTTService.class);
                            getActivity().stopService(i);
                            Toast.makeText(Home.this.getContext(), "Service Stoped", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor =  Home.this.getActivity().getSharedPreferences("check", MODE_PRIVATE).edit();
                            editor.putString("key", "false");
                            editor.apply();
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

                //setting it up so that items in this recyclerview can be clicked. brings you to the updateDeleteCars class
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
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        eventsPlace.setLayoutManager(layoutManager);
        //used so when you are scrolling it locks onto the nearest item
        final SnapHelper snapHelper  = new GravitySnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(eventsPlace);
       // set a timer for defaul item



        //this is used for the scolling of the items. in the xml item in the view are scaled down. this sets it up so that if you scroll
        //onto an item in the view it scales the item back to normal size 1.
        eventsPlace.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    View view = snapHelper.findSnapView(layoutManager);
                    int pos = layoutManager.getPosition(view);
                    RecyclerView.ViewHolder viewHolder = eventsPlace.findViewHolderForAdapterPosition(pos);

                    LinearLayout eventparent = viewHolder.itemView.findViewById(R.id.eventParent);
                    eventparent.animate().scaleY(1).scaleX(1).setDuration(350).setInterpolator(new AccelerateInterpolator()).start();



                }
                else {
                    //when it is not on the item it brings it back down
                    View view = snapHelper.findSnapView(layoutManager);
                    int pos =  layoutManager.getPosition(view);
                    RecyclerView.ViewHolder viewHolder = eventsPlace.findViewHolderForAdapterPosition(pos);

                    LinearLayout eventparent = viewHolder.itemView.findViewById(R.id.eventParent);
                    eventparent.animate().scaleY(0.7f).scaleX(0.7f).setDuration(350).setInterpolator(new AccelerateInterpolator()).start();


                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        //this shows a picture and textview when the recyclerview is empty and geting rid of them when items are it the recyclerview.
        //it also expands the first item that is in the recyclerview if it exists. this is so that you dont have to touch the first item#
        //for it to expand
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mEmptyListMessage.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
                emptyCars.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
                final Handler handler = new Handler();
                if(dataSnapshot.exists()) {
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            //DO Something ater 1ms = 100m
                            RecyclerView.ViewHolder viewHolderDefault = eventsPlace.findViewHolderForAdapterPosition(0);
                            LinearLayout eventparentDefault = viewHolderDefault.itemView.findViewById(R.id.eventParent);
                            eventparentDefault.animate().scaleY(1).scaleX(1).setDuration(350).setInterpolator(new AccelerateInterpolator()).start();


                        }
                    }, 100);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }






}
