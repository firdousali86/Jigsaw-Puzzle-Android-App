package com.firdous.makepic.makepicture;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GridFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GridFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final int blankCellIndex = 8;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int emptyCellIndex = blankCellIndex;
    private Integer emptyCellId = 0;
    private BitmapWrapper[] bitmapsArray;

    private List<Integer> allCellsIds;
    private View _fragmentView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GridFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GridFragment newInstance(String param1, String param2) {
        GridFragment fragment = new GridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public GridFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        _fragmentView = inflater.inflate(R.layout.fragment_grid, container, false);

        createImageBits();
        initializeGridCellList();

        setImageBits();

        return _fragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void createImageBits(){
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.markhor);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 300, 300, true);

        bitmapsArray = new BitmapWrapper[8];

        bitmapsArray[0] = BitmapWrapper.bitmapFactory(Bitmap.createBitmap(bMapScaled, 0, 0, 100, 100), 0);
        bitmapsArray[1] = BitmapWrapper.bitmapFactory(Bitmap.createBitmap(bMapScaled, 100, 0, 100, 100), 1);
        bitmapsArray[2] = BitmapWrapper.bitmapFactory(Bitmap.createBitmap(bMapScaled, 200, 0, 100, 100), 2);
        bitmapsArray[3] = BitmapWrapper.bitmapFactory(Bitmap.createBitmap(bMapScaled, 0, 100, 100, 100), 3);
        bitmapsArray[4] = BitmapWrapper.bitmapFactory(Bitmap.createBitmap(bMapScaled, 100, 100, 100, 100), 4);
        bitmapsArray[5] = BitmapWrapper.bitmapFactory(Bitmap.createBitmap(bMapScaled, 200, 100, 100, 100), 5);
        bitmapsArray[6] = BitmapWrapper.bitmapFactory(Bitmap.createBitmap(bMapScaled, 0, 200, 100, 100), 6);
        bitmapsArray[7] = BitmapWrapper.bitmapFactory(Bitmap.createBitmap(bMapScaled, 100, 200, 100, 100), 7);
        //bitmapsArray[7] = BitmapWrapper.bitmapFactory(Bitmap.createBitmap(bMapScaled, 200, 200, 100, 100), 8);
    }

    public void initializeGridCellList(){
        View parentLayout = _fragmentView.findViewById(R.id.parentLayout);

        allCellsIds = new ArrayList<Integer>();

        for(int i=0; i<((ViewGroup)parentLayout).getChildCount(); ++i) {
            View horizontalLayout = ((ViewGroup)parentLayout).getChildAt(i);

            for(int j=0; j<((ViewGroup)horizontalLayout).getChildCount(); ++j) {
                GridCell gridCellLayout = (GridCell) ((ViewGroup)horizontalLayout).getChildAt(j);
                gridCellLayout.setOnClickListener(this);
                gridCellLayout.lineIndex = i;

                allCellsIds.add(gridCellLayout.getId());
            }
        }
    }

    public void setImageBits(){

        emptyCellIndex = blankCellIndex;
        emptyCellId = (int) allCellsIds.get(blankCellIndex);

        int indexer = 0;

        for (Integer cellId : allCellsIds){
            GridCell gridCell = (GridCell) _fragmentView.findViewById(cellId);

            if (indexer == blankCellIndex) {
                gridCell.setImageWrapperObject(null);
            }
            else{
                gridCell.setImageWrapperObject(((BitmapWrapper)bitmapsArray[indexer]));
            }

            indexer++;
        }
    }

    public void shuffleImages(){
        bitmapsArray = GridFragment.shuffleArray(bitmapsArray);
        setImageBits();
    }

    static BitmapWrapper[] shuffleArray(BitmapWrapper[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            BitmapWrapper a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }

        return ar;
    }

    public boolean checkImageCompletion(){
        for (Integer cellId : allCellsIds){
            GridCell gridCell = (GridCell) _fragmentView.findViewById(cellId);
            int gridIndex = Integer.parseInt((String)gridCell.getTag());

            if (gridCell.bitmapWrapper != null && gridIndex != gridCell.bitmapWrapper.bitIndex){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        int tappedCellIndex = Integer.parseInt((String)v.getTag());
        int tappedCellId = v.getId();
        GridCell tappedCell = (GridCell)v;
        GridCell emptyCell = (GridCell)_fragmentView.findViewById(emptyCellId);

        //if user tapped empty cell
        if (tappedCellIndex == emptyCellIndex || tappedCell.bitmapWrapper == null){
            return;
        }

        //if user tapped next line with diff of 1
        if (Math.abs(tappedCellIndex - emptyCellIndex) == 1 && tappedCell.lineIndex != emptyCell.lineIndex){
            return;
        }

        if (Math.abs(tappedCellIndex - emptyCellIndex) == 1 || Math.abs(tappedCellIndex - emptyCellIndex) == 3){
            if (tappedCell != null && emptyCell != null){
                emptyCell.setImageWrapperObject(tappedCell.bitmapWrapper);
                tappedCell.setImageWrapperObject(null);

                emptyCellIndex = tappedCellIndex;
                emptyCellId = tappedCellId;
            }
        }

        if (checkImageCompletion()){
            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
        }
    }
}
