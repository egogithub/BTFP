package com.worldline.ego.pebbletransport;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.worldline.ego.pebbletransport.dummy.DummyContent;
import com.worldline.ego.pebbletransport.dummy.DummyContent.DummyItem;
import com.worldline.ego.pebbletransport.helpers.WaitingTimeHelper;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RealtimeFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private String mLineNumber;
    private String mTransportMode;
    private String mDirection;
    private String mDestination;
    private int mStopId;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RealtimeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RealtimeFragment newInstance(String lineNumber, String transportMode, String direction,
                                               String destination, int stopId) {
        RealtimeFragment fragment = new RealtimeFragment();
        Bundle args = new Bundle();
        args.putString("line", lineNumber);
        args.putString("mode", transportMode);
        args.putString("dir", direction);
        args.putString("dest", destination);
        args.putInt("stopid", stopId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mLineNumber = getArguments().getString("line");
            mTransportMode = getArguments().getString("mode");
            mDirection = getArguments().getString("dir");
            mDestination = getArguments().getString("dest");
            mStopId = getArguments().getInt("stopid");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_realtime_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyRealtimeRecyclerViewAdapter(WaitingTimeHelper.getWaitingTimes(mLineNumber, mTransportMode, mDirection, mStopId), mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
