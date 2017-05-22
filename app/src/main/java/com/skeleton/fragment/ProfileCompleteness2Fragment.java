package com.skeleton.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.skeleton.R;
import com.skeleton.adapter.RecyclerAdapter;
import com.skeleton.database.CommonData;
import com.skeleton.model.userProfile.Categories;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.Log;

import java.util.List;

/**
 * Created by Rishab on 18-05-2017.
 */

public class ProfileCompleteness2Fragment extends BaseFragment {
    private Button btnSaveContinue;
    private List<Categories> categories;
    private View cSelectorVP1, cSelectorVP2, cSelectorVP3, cSelectorVP4, cSelectorVP5;
    private RecyclerView rvProfileCompleteness2;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profilecompleteness2, container, false);
        init(view);
        getCatergorylist();
        return view;
    }
    /**
     * @return size of category list
     */
    public int getCategorylistsize() {
        Log.d("debug", String.valueOf(categories.size()));
        return categories.size();
    }

    /**
     * initialisation
     * @param view view of the fragment
     */
    public void init(final View view) {
        btnSaveContinue = (Button) view.findViewById(R.id.btnSaveContinue);
        cSelectorVP1 = (View) view.findViewById(R.id.selector_vp1);
        cSelectorVP2 = (View) view.findViewById(R.id.selector_vp2);
        cSelectorVP3 = (View) view.findViewById(R.id.selector_vp3);
        cSelectorVP4 = (View) view.findViewById(R.id.selector_vp4);
        cSelectorVP5 = (View) view.findViewById(R.id.selector_vp5);
        rvProfileCompleteness2 = (RecyclerView) view.findViewById(R.id.rv_profile2);

    }

    /**
     * gets Catergory list form server
     *
     * @return category list
     */
    public List<Categories> getCatergorylist() {
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.getInterests("bearer " + CommonData.getAccessToken(), "INTEREST")
                .enqueue(new ResponseResolver<com.skeleton.model.userProfile.Response>(getActivity(), true, true) {
                    @Override
                    public void success(final com.skeleton.model.userProfile.Response response) {
                        Log.d("debug", String.valueOf(response.getStatusCode()));
                        if ("200".equals(String.valueOf(response.getStatusCode()))) {
                            categories = response.getData().getCategories();
                            RecyclerAdapter recyclerAdaptor = new RecyclerAdapter(categories, getContext(), ProfileCompleteness2Fragment.this);
                            rvProfileCompleteness2.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                            rvProfileCompleteness2.setAdapter(recyclerAdaptor);
                        }
                    }

                    @Override
                    public void failure(final APIError error) {
                        Log.d("debug", error.getMessage());
                    }
                });
        return categories;
    }
}
