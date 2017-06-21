package com.naicop.naicopapp.Widgets;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.naicop.naicopapp.Adapters.EventsAdapter;
import com.naicop.naicopapp.Entitites.Category;
import com.naicop.naicopapp.Helpers.UnitHelper;
import com.naicop.naicopapp.R;

import java.util.List;

/**
 * Created by pazos on 20-Jun-17.
 */
public abstract class FilterCategoryView {

    protected Context context;
    protected Activity activity;
    protected LinearLayout categoriesLayout;
    protected RelativeLayout opacity;

    public FilterCategoryView(Activity activity,Context context){
        this.activity =activity;
        this.context = context;
    }

    public FilterCategoryView comeToLife(List<Category> categories){
        opacity = (RelativeLayout)activity.findViewById(R.id.opacity);
        categoriesLayout = (LinearLayout)activity.findViewById(R.id.filterCategoriesContainer);
        categoriesLayout.removeAllViews();
        LayoutInflater inflater = activity.getLayoutInflater();
        for(final Category category : categories){
            RelativeLayout individualCategoryLayout = (RelativeLayout)inflater.inflate(R.layout.individual_category,null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UnitHelper.dpToPx(context,50) );
            individualCategoryLayout.setLayoutParams(params);
            TextView categoryNameText = (TextView)individualCategoryLayout.findViewById(R.id.categoryNameText);
            categoryNameText.setText(category.name);
            categoryNameText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    categoryClicked(category);
                }
            });
            categoriesLayout.addView(individualCategoryLayout);
        }
        return this;
    }

    public FilterCategoryView show(){
        if(categoriesLayout.getVisibility() == View.GONE){
            categoriesLayout.setVisibility(View.VISIBLE);
            Animation a = new ScaleAnimation(1, 1, 0, 1);
            a.setDuration(500);
            categoriesLayout.startAnimation(a);
            if(opacity != null)
                opacity.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public FilterCategoryView hide(){
        if(categoriesLayout.getVisibility() == View.VISIBLE){
            Animation a = new ScaleAnimation(1,1,1,0);
            a.setDuration(500);
            a.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    categoriesLayout.setVisibility(View.GONE);
                    if(opacity != null)
                        opacity.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            categoriesLayout.startAnimation(a);
        }

        return this;
    }

    public abstract void categoryClicked(Category category);


}
