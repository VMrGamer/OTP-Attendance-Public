package teamarv.otpattendancesystem;


import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;


import android.widget.Toast;
import android.os.Handler;


import android.view.ViewGroup;
import android.view.MenuInflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecyclerViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class RecyclerViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView recyclerView;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;


    private RecyclerViewAdapter mAdapter;

    private ArrayList<AbstractModel> modelList = new ArrayList<>();


    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecyclerViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecyclerViewFragment newInstance(String param1, String param2) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static RecyclerViewFragment newInstance() {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        // ButterKnife.bind(this);
        findViews(view);

        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAdapter();


    }


    private void findViews(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    }


    private void setAdapter() {


        modelList.add(new AbstractModel("Account Details", "Details"));

        if(MainActivity.userTypeString.toLowerCase().equals("student")){
            modelList.add(new AbstractModel("Student Attendance Summary", "Summary"));
            modelList.add(new AbstractModel("Date-Wise Attendance Summary", "Summary"));
            modelList.add(new AbstractModel("Enter OTP", "Attendance"));
        }
        else if(MainActivity.userTypeString.toLowerCase().equals("teacher")){
            modelList.add(new AbstractModel("Teacher Summary", "Summary"));
            modelList.add(new AbstractModel("Create OTP", "Attendance"));
        }
        else{
            modelList.add(new AbstractModel("Admin Summary", "Summary"));
            modelList.add(new AbstractModel("Add New Course", "Course Edit"));
            modelList.add(new AbstractModel("Add Teacher Course", "Teacher Edit"));
            modelList.add(new AbstractModel("Add Class Course", "Batch Edit"));
        }


        mAdapter = new RecyclerViewAdapter(getActivity(), modelList);

        recyclerView.setHasFixedSize(true);


        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, AbstractModel model) {
                if(model.getTitle().equals("Account Details")){
                    Toast.makeText(getActivity(), model.getTitle(), Toast.LENGTH_SHORT).show();
                }
                else if(model.getTitle().equals("Student Attendance Summary")){
                    Toast.makeText(getActivity(), model.getTitle(), Toast.LENGTH_SHORT).show();

                }
                else if(model.getTitle().equals("Date-Wise Attendance Summary")){
                    Toast.makeText(getActivity(), model.getTitle(), Toast.LENGTH_SHORT).show();
                }
                else if(model.getTitle().equals("Enter OTP")){
                    Toast.makeText(getActivity(), model.getTitle(), Toast.LENGTH_SHORT).show();
                    UserActivity.openFragment('0');
                }
                else if(model.getTitle().equals("Teacher Summary")){
                    Toast.makeText(getActivity(), model.getTitle(), Toast.LENGTH_SHORT).show();

                }
                else if(model.getTitle().equals("Create OTP")){
                    Toast.makeText(getActivity(), model.getTitle(), Toast.LENGTH_SHORT).show();
                    UserActivity.openFragment('1');
                }
                else if(model.getTitle().equals("Admin Summary")){
                    Toast.makeText(getActivity(), model.getTitle(), Toast.LENGTH_SHORT).show();

                }
                else if(model.getTitle().equals("Add New Course")){
                    Toast.makeText(getActivity(), model.getTitle(), Toast.LENGTH_SHORT).show();

                }
                else if(model.getTitle().equals("Add Teacher Course")){
                    Toast.makeText(getActivity(), model.getTitle(), Toast.LENGTH_SHORT).show();

                }
                else if(model.getTitle().equals("Add Class Course")){
                    Toast.makeText(getActivity(), model.getTitle(), Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

}
