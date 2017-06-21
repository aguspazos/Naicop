package com.naicop.naicopapp.Widgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.R;

/**
 * Created by pazos on 18-Jun-17.
 */
public abstract class MenuWithSearchBar extends MenuBar {


    protected EditText searchText;
    public MenuWithSearchBar(NaicopActivity activity, Context context) {
        super(activity, context);
    }

    @Override
    public MenuWithSearchBar comeToLife(){
        super.comeToLife();
        RelativeLayout searchBar = (RelativeLayout)activity.findViewById(R.id.searchBarContainer);
            searchBar.setVisibility(View.VISIBLE);
            searchText = (EditText)searchBar.findViewById(R.id.searchText);
            searchText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    textChanged(editable.toString());
                }
            });
        return this;
    }

    public void resetSearchText(){
        if(searchText != null)
            searchText.setText("");
    }

    public abstract void textChanged(String text);
}
