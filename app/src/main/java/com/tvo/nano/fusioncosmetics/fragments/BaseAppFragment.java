package com.tvo.nano.fusioncosmetics.fragments;

import android.support.v4.app.Fragment;

import com.tvo.nano.fusioncosmetics.activities.FrameContentActivity;

/**
 * Created by Son on 17-Apr-15.
 */
public class BaseAppFragment extends Fragment {

    public void setHeadingPage(int id) {
        ((FrameContentActivity) getActivity()).setHeadingPage(id);
    }

    public void setImageLeftActionbarBackground(int id) {
        ((FrameContentActivity) getActivity()).setImageLeftActionbarBackground(id);
    }

    public void setImageRightActionbarBackground(int id) {
        ((FrameContentActivity) getActivity()).setImageRightActionbarBackground(id);
    }

    public void replaceFragment(Fragment fm, boolean addToBackStack) {
        ((FrameContentActivity) getActivity()).replaceFragment(fm, addToBackStack);
    }

    public void setItemSoldCount(String count) {
        ((FrameContentActivity) getActivity()).setItemSoldCount(count);
    }

    public void setItemSoldCountVisible(boolean isVisible) {
        ((FrameContentActivity) getActivity()).setItemSoldCountVisible(isVisible);
    }

    public void decodeAndReadJson(String salesdatecut) {
        ((FrameContentActivity) getActivity()).decodeAndReadJson(salesdatecut);
    }

    public void encodeAndSaveJson() {
        ((FrameContentActivity) getActivity()).encodeAndSaveJson();
    }
}
