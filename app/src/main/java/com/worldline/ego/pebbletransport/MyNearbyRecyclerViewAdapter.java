package com.worldline.ego.pebbletransport;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.worldline.ego.pebbletransport.NearbyFragment.OnListFragmentInteractionListener;
import com.worldline.ego.pebbletransport.dummy.DummyContent.DummyItem;
import com.worldline.ego.pebbletransport.pojo.ItiStop;
import com.worldline.ego.pebbletransport.utils.HaversineAlgorithm;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyNearbyRecyclerViewAdapter extends RecyclerView.Adapter<MyNearbyRecyclerViewAdapter.ViewHolder> {

    private final List<ItiStop> mValues;
    private final OnListFragmentInteractionListener mListener;
    private double mLatitude;
    private double mLongitude;

    public MyNearbyRecyclerViewAdapter(List<ItiStop> stops, double latitude, double longitude,OnListFragmentInteractionListener listener) {
        this.mLatitude=latitude;
        this.mLongitude=longitude;
        this.mValues = stops;
        this.mListener = listener;
        Log.d("RecyclerViewAdapter", "mValues.size = "+mValues.size());
    }
/*
    @Override
    public int getItemViewType(int position){
        if (mValues.get(position).)
    }
*/
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_nearby, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
        holder.mDistanceView.setText("("+HaversineAlgorithm.HaversineInM(mLatitude, mLongitude, mValues.get(position).latitude, mValues.get(position).longitude)+"m)");
        holder.mContentView.setText(mValues.get(position).getStopName());
        if (holder.mItem.dest.size()>0) { // there are some lines in that stop
            //
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDistanceView;
        public final TextView mContentView;
        public final ListView mLinesView;
        public ItiStop mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.stopname);
            mDistanceView = (TextView) view.findViewById(R.id.distance);
            mLinesView = (ListView) view.findViewById(R.id.lineslist);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }


}
