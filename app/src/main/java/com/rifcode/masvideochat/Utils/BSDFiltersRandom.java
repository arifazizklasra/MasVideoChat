package com.rifcode.masvideochat.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hbb20.CountryCodePicker;
import com.rifcode.masvideochat.Models.DataFire;
import com.rifcode.masvideochat.R;

import static android.content.Context.MODE_PRIVATE;

public class BSDFiltersRandom extends BottomSheetDialogFragment {

    private BottomSheetListener mListener;
    private View v;
    private LinearLayout llFilterDialogChooseGenderGirl,llFilterDialogChooseGenderBoy,llFilterDialogChooseGenderBoth;
    private TextView tv_match_filter_select_both,tv_match_filter_select_girl,tv_match_filter_select_boy;
    private SharedPreferences prefscheckOption,prefscheckMatchWith;
    private SharedPreferences.Editor matchWith;
    private String checkOption,strMatchWith;
    private TextView tvTitleBDSRandom;
    private TextView tvGlobaly,tvNearby;
    private LinearLayout llFilterDialogCountry;
    private TextView tvSelectCountry;
    private DataFire dataFire;
    private String get_region_preferences;
    private SharedPreferences prefs_region_preferences;
    private SharedPreferences.Editor edit_region_preferences;
    private CountryCodePicker ccpSearchPreLocation;
    private SharedPreferences.Editor edit_country_selected;
    private SharedPreferences prefs_country_selected;
    private String get_country_selected;
    private String get_country_code_selected;
    private SharedPreferences.Editor edit_country_code_selected;
    private SharedPreferences prefs_country_code_selected;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.dialog_discover_new_match_filter_bottom, container, false);
       setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.bottomSheetDialogTheme);
       dataFire=new DataFire();
        widgets();

        // check video or voice call
        prefscheckOption = getActivity().getSharedPreferences("checkOption", Context.MODE_PRIVATE);
        checkOption = prefscheckOption.getString("checkOption", null);

        // match with
        matchWith = getActivity().getSharedPreferences("match_with", MODE_PRIVATE).edit();
        prefscheckMatchWith = getActivity().getSharedPreferences("match_with", Context.MODE_PRIVATE);
        strMatchWith = prefscheckMatchWith.getString("match_with", null);

        // region preferences
        edit_region_preferences = getActivity().getSharedPreferences("region_preferences", MODE_PRIVATE).edit();
        prefs_region_preferences = getActivity().getSharedPreferences("region_preferences", Context.MODE_PRIVATE);
        get_region_preferences = prefs_region_preferences.getString("region_preferences", null);

        edit_country_selected = getActivity().getSharedPreferences("country", MODE_PRIVATE).edit();
        prefs_country_selected = getActivity().getSharedPreferences("country", Context.MODE_PRIVATE);
        get_country_selected = prefs_country_selected.getString("country", null);

        edit_country_code_selected = getActivity().getSharedPreferences("country_code", MODE_PRIVATE).edit();
        prefs_country_code_selected = getActivity().getSharedPreferences("country_code", Context.MODE_PRIVATE);
        get_country_code_selected = prefs_country_code_selected.getString("country_code", null);

        ccpSearchPreLocation.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                edit_country_selected.putString("country", ccpSearchPreLocation.getSelectedCountryName());
                edit_country_selected.apply();
                tvSelectCountry.setText(ccpSearchPreLocation.getSelectedCountryName());

                edit_country_code_selected.putString("country_code", ccpSearchPreLocation.getSelectedCountryNameCode());
                edit_country_code_selected.apply();
            }
        });

        if (get_country_selected==null){
           ccpSearchPreLocation.setAutoDetectedCountry(true);
            edit_country_selected.putString("country", ccpSearchPreLocation.getSelectedCountryName());
            edit_country_selected.apply();
            edit_country_code_selected.putString("country_code", ccpSearchPreLocation.getSelectedCountryNameCode());
            edit_country_code_selected.apply();
        }
        else {
            if(get_country_code_selected!=null)
                 ccpSearchPreLocation.setCountryForNameCode(get_country_code_selected);
        }
        
        if (get_region_preferences==null){
            setupWidgetsSelectorsRegion();
            tvGlobaly.setSelected(true);
            tvGlobaly.setTextColor(getActivity().getResources().getColor(R.color.white_normal));
            edit_region_preferences.putString("region_preferences", "global");
            edit_region_preferences.apply();
        }
        else {
            if (get_region_preferences.equals("select_country")) {

                setupWidgetsSelectorsRegion();
                llFilterDialogCountry.setSelected(true);
                tvSelectCountry.setTextColor(getActivity().getResources().getColor(R.color.white_normal));

            } else if(get_region_preferences.equals("nearby")) {

                setupWidgetsSelectorsRegion();
                tvNearby.setSelected(true);
                tvNearby.setTextColor(getActivity().getResources().getColor(R.color.white_normal));

            }else if(get_region_preferences.equals("global")) {
                setupWidgetsSelectorsRegion();
                tvGlobaly.setSelected(true);
                tvGlobaly.setTextColor(getActivity().getResources().getColor(R.color.white_normal));
            }
        }

        if (checkOption==null){
            tvTitleBDSRandom.setText(getString(R.string.string_voice_filter));
        }
        else {
            if (checkOption.equals("video")) {
                tvTitleBDSRandom.setText(getString(R.string.string_video_filter));
            } else {
                tvTitleBDSRandom.setText(getString(R.string.string_voice_filter));
            }
        }

        if (strMatchWith==null){
            setupWidgetSelectorsMatch();
            llFilterDialogChooseGenderGirl.setSelected(true);
            tv_match_filter_select_girl.setTextColor(getActivity().getResources().getColor(R.color.white_normal));
            matchWith.putString("match_with", "both");
            matchWith.apply();
        }
        else {
            if (strMatchWith.equals("guy")) {
                setupWidgetSelectorsMatch();
                llFilterDialogChooseGenderBoy.setSelected(true);
                tv_match_filter_select_boy.setTextColor(getActivity().getResources().getColor(R.color.white_normal));
                matchWith.putString("match_with", "guy");
                matchWith.apply();
            } else if(strMatchWith.equals("girl")) {
                setupWidgetSelectorsMatch();
                llFilterDialogChooseGenderGirl.setSelected(true);
                tv_match_filter_select_girl.setTextColor(getActivity().getResources().getColor(R.color.white_normal));
                matchWith.putString("match_with", "girl");
                matchWith.apply();
            }
            else if(strMatchWith.equals("both")) {
                setupWidgetSelectorsMatch();
                llFilterDialogChooseGenderBoth.setSelected(true);
                tv_match_filter_select_both.setTextColor(getActivity().getResources().getColor(R.color.white_normal));
                matchWith.putString("match_with", "both");
                matchWith.apply();
            }
        }


        llFilterDialogChooseGenderGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setupWidgetSelectorsMatch();
                llFilterDialogChooseGenderGirl.setSelected(true);
                tv_match_filter_select_girl.setTextColor(getActivity().getResources().getColor(R.color.white_normal));
                matchWith.putString("match_with", "girl");
                matchWith.apply();
            }
        });

        llFilterDialogChooseGenderBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupWidgetSelectorsMatch();
                llFilterDialogChooseGenderBoy.setSelected(true);
                tv_match_filter_select_boy.setTextColor(getActivity().getResources().getColor(R.color.white_normal));
                matchWith.putString("match_with", "guy");
                matchWith.apply();
            }
        });

        llFilterDialogChooseGenderBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupWidgetSelectorsMatch();
                llFilterDialogChooseGenderBoth.setSelected(true);
                tv_match_filter_select_both.setTextColor(getActivity().getResources().getColor(R.color.white_normal));
                matchWith.putString("match_with", "both");
                matchWith.apply();
            }
        });
        
        llFilterDialogCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupWidgetsSelectorsRegion();
                llFilterDialogCountry.setSelected(true);
                tvSelectCountry.setTextColor(getActivity().getResources().getColor(R.color.white_normal));
                edit_region_preferences.putString("region_preferences", "select_country");
                edit_region_preferences.apply();
            }
        });


        tvGlobaly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupWidgetsSelectorsRegion();
                tvGlobaly.setSelected(true);
                tvGlobaly.setTextColor(getActivity().getResources().getColor(R.color.white_normal));
                edit_region_preferences.putString("region_preferences", "global");
                edit_region_preferences.apply();
            }
        });

        tvNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupWidgetsSelectorsRegion();
                tvNearby.setSelected(true);
                tvNearby.setTextColor(getActivity().getResources().getColor(R.color.white_normal));
                edit_region_preferences.putString("region_preferences", "nearby");
                edit_region_preferences.apply();
            }
        });

        return v;
    }

    private void setupWidgetSelectorsMatch(){
        llFilterDialogChooseGenderGirl.setSelected(false);
        llFilterDialogChooseGenderBoth.setSelected(false);
        llFilterDialogChooseGenderBoy.setSelected(false);
        tv_match_filter_select_both.setTextColor(getActivity().getResources().getColor(R.color.gray_7a242323));
        tv_match_filter_select_boy.setTextColor(getActivity().getResources().getColor(R.color.gray_7a242323));
        tv_match_filter_select_girl.setTextColor(getActivity().getResources().getColor(R.color.gray_7a242323));
    }
    
    private void setupWidgetsSelectorsRegion(){
        llFilterDialogCountry.setSelected(false);
        tvNearby.setSelected(false);
        tvGlobaly.setSelected(false);
        tvNearby.setTextColor(getActivity().getResources().getColor(R.color.gray_7a242323));
        tvGlobaly.setTextColor(getActivity().getResources().getColor(R.color.gray_7a242323));
        tvSelectCountry.setTextColor(getActivity().getResources().getColor(R.color.gray_7a242323));
    }

    private void widgets() {

        //ll
        llFilterDialogChooseGenderGirl = v.findViewById(R.id.llFilterDialogChooseGenderGirl);
        llFilterDialogChooseGenderBoy = v.findViewById(R.id.llFilterDialogChooseGenderBoy);
        llFilterDialogChooseGenderBoth = v.findViewById(R.id.llFilterDialogChooseGenderBoth);
        llFilterDialogCountry = v.findViewById(R.id.llFilterDialogCountry);

        //tv
        tvTitleBDSRandom = v.findViewById(R.id.tvTitleBDSRandom);
        tv_match_filter_select_boy = v.findViewById(R.id.tv_match_filter_select_boy);
        tv_match_filter_select_girl = v.findViewById(R.id.tv_match_filter_select_girl);
        tv_match_filter_select_both = v.findViewById(R.id.tv_match_filter_select_both);
        
        tvGlobaly = v.findViewById(R.id.tvGlobaly);
        tvNearby = v.findViewById(R.id.tvNearby);
        tvSelectCountry = v.findViewById(R.id.tvSelectCountry);

        ccpSearchPreLocation = v.findViewById(R.id.ccpSearchPreLocation);

    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
                mListener = (BottomSheetListener) context;
           // mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }

    }

}
