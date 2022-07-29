package com.rifcode.masvideochat.Utils;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rifcode.masvideochat.Adapter.GiftViewHolders;
import com.rifcode.masvideochat.Models.DataFire;
import com.rifcode.masvideochat.Models.Gift;
import com.rifcode.masvideochat.R;
import com.rifcode.masvideochat.View.Random.VideoCallActivity;
import com.squareup.picasso.Picasso;

public class BSDChooseGift extends BottomSheetDialogFragment {

    private BottomSheetListener mListener;
    private View v;
    private RecyclerView rcv_gift_list;
    private GridLayoutManager mGLayoutManager;
    private FirebaseRecyclerAdapter<Gift, GiftViewHolders> firebaseRecyclerAdapter;
    private DataFire dataFire;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.dialog_gifts_choose, container, false);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.bottomSheetDialogTheme);
        dataFire=new DataFire();
        wedgets();


       
        return v;
    }


    private void wedgets() {
        rcv_gift_list=v.findViewById(R.id.rcv_gift_list);
        rcv_gift_list.setHasFixedSize(true);
        mGLayoutManager = new GridLayoutManager(getActivity(),2,LinearLayoutManager.HORIZONTAL,false);
        rcv_gift_list.setLayoutManager(mGLayoutManager);
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }


    @Override
    public void onStart() {
        super.onStart();
        Query qr = dataFire.getDbRefGifts().orderByChild("Time");
        fra(qr);
    }

    private void fra(Query qr){

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Gift, GiftViewHolders>(
                Gift.class,
                R.layout.item_gifts_list_layout,
                GiftViewHolders.class,
                qr
        ) {

            @Override
            protected void populateViewHolder(final GiftViewHolders viewHolder, final Gift model, int position) {

                final String listPostKey = getRef(position).getKey();

                dataFire.getDbRefGifts().child(listPostKey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String image_preview = String.valueOf(dataSnapshot.child("image_preview").getValue());
                        final String link_json = String.valueOf(dataSnapshot.child("link_json").getValue());
                        String name = String.valueOf(dataSnapshot.child("name").getValue());

                        Picasso.get().load(image_preview).into(viewHolder.iv_gift_icon);
                        viewHolder.tv_gift_name.setText(name);

                        viewHolder.iv_gift_icon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {
                                    dataFire.getDbSendGift()
                                            .child(VideoCallActivity.userIDvisited)
                                            .child(dataFire.getUserID())
                                            .child("link_json").setValue(link_json).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            try {
                                                Toast.makeText(getContext(), R.string.g_s_c, Toast.LENGTH_SHORT).show();
                                            }catch (Exception e){
                                            }
                                        }
                                    });

                                }catch (Exception e){

                                }
                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        };
        rcv_gift_list.setAdapter(firebaseRecyclerAdapter);
    }
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
                mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }


    }

}
