package cc.aiknow.basicandroid.androidviewpager.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import cc.aiknow.basicandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewPagerItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPagerItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private View rootView;
    private TextView descText;

    public ViewPagerItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ViewPagerItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewPagerItemFragment newInstance(String param1) {
        ViewPagerItemFragment fragment = new ViewPagerItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_view_pager_item, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        if (rootView == null) {
            return;
        }
        int color = 0xFF000000 | new Random().nextInt(0x00FFFFFF);
        rootView.setBackgroundColor(color);
        descText = rootView.findViewById(R.id.descText);
        descText.setText(mParam1);
    }
}