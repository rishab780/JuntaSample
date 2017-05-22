package com.skeleton.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skeleton.R;
import com.skeleton.fragment.ProfileCompleteness2Fragment;
import com.skeleton.model.userProfile.Categories;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Rishab on 18-05-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Categories> mCategories;
    private HashMap<String, String> map;
    private Context context;
    private Fragment fragment;
    private ProfileCompleteness2Fragment profileCompleteness;

    /**
     * constructor of adapter
     *
     * @param categories list
     * @param context    context of activity
     * @param fragment   object of fragment
     */
    public RecyclerAdapter(final List<Categories> categories, final Context context, final Fragment fragment) {
        this.mCategories = categories;
        this.fragment = fragment;
        this.context = context;

    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_profilecompletness2, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter.ViewHolder holder, final int position) {
        final Categories categories = mCategories.get(position);

        holder.tvInterestName.setText(categories.getName());
        holder.tvInterestName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int selection = profileCompleteness.getCategorylistsize();
                if (holder.ivCheck.getVisibility() == View.INVISIBLE && (selection < 5)) {
                    categories.setChecked(true);
                    holder.ivCheck.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    /**
     * view holder class
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivInterest, ivOnSelectedShade, ivCheck;
        private TextView tvInterestName;

        /**
         * constructor
         *
         * @param itemView itemview for the display
         */
        public ViewHolder(final View itemView) {
            super(itemView);
            ivInterest = (ImageView) itemView.findViewById(R.id.ivInterest);
            ivOnSelectedShade = (ImageView) itemView.findViewById(R.id.ivOnSelectedShade);
            ivCheck = (ImageView) itemView.findViewById(R.id.ivCheck);
            tvInterestName = (TextView) itemView.findViewById(R.id.tvInterestName);
            ivCheck.setVisibility(View.INVISIBLE);
            ivOnSelectedShade.setVisibility(View.INVISIBLE);
        }
    }
}
