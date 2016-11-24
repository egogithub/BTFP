package com.worldline.ego.pebbletransport;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.worldline.ego.pebbletransport.LinesFragment.OnListFragmentInteractionListener;
import com.worldline.ego.pebbletransport.dummy.DummyContent;
import com.worldline.ego.pebbletransport.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyLinesRecyclerViewAdapter extends RecyclerView.Adapter<MyLinesRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyLinesRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        Log.d("MyLinesRecyclerViewAdap", "Creating object with "+items.size()+" items");
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_lines_rel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Log.d("MyLinesRecyclerViewAdap", "Setting lineid at position "+position);
        Log.d("MyLinesRecyclerViewAdap", "lineid = "+mValues.get(position).lineid);
        holder.mLineIdView.setText(mValues.get(position).lineid);
        holder.mDestFromView.setText(mValues.get(position).destfrom);
        holder.mDestToView.setText(mValues.get(position).destto);

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
        public final TextView mLineIdView;
        public final TextView mDestFromView;
        public final TextView mDestToView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mLineIdView = (TextView) view.findViewById(R.id.lineid);
            mDestFromView = (TextView) view.findViewById(R.id.destfrom);
            mDestToView = (TextView) view.findViewById(R.id.destto);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mLineIdView.getText() + "'";
        }

    }
}
