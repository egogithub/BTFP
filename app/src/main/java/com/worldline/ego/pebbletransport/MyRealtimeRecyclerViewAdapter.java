package com.worldline.ego.pebbletransport;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.worldline.ego.pebbletransport.pojo.WaitingTime;
import com.worldline.ego.pebbletransport.pojo.WaitingTimeStop;

import org.w3c.dom.Text;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRealtimeRecyclerViewAdapter extends RecyclerView.Adapter<MyRealtimeRecyclerViewAdapter.ViewHolder> {

    private final List<WaitingTime> mValues;
    private final RealtimeFragment.OnListFragmentInteractionListener mListener;

    public MyRealtimeRecyclerViewAdapter(WaitingTimeStop waitingTimes, RealtimeFragment.OnListFragmentInteractionListener listener) {
        mValues = waitingTimes.getWaitingTimes();
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_realtime, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).line);
        holder.mDestToView.setText(mValues.get(position).destination);
        holder.mTimeLeftView.setText(""+mValues.get(position).minutes);

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
        public final TextView mIdView;
        public final TextView mDestToView;
        public final TextView mTimeLeftView;
        public WaitingTime mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mDestToView = (TextView) view.findViewById(R.id.destto);
            mTimeLeftView = (TextView) view.findViewById(R.id.timeleft);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }
}
