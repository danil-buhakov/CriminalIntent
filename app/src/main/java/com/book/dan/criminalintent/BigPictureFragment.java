package com.book.dan.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class BigPictureFragment extends DialogFragment {
    private final static String ARGS_PHOTO_PATH = "photo path";
    private ImageView mPhotoView;
    public static BigPictureFragment newInstance(String path){
        Bundle args = new Bundle();
        args.putString(ARGS_PHOTO_PATH, path);
        BigPictureFragment fragment = new BigPictureFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_big_picture,null);
        mPhotoView = (ImageView) v.findViewById(R.id.big_image);
        mPhotoView.setImageBitmap(PictureUtils.getScaledBitmap(getArguments().getString(ARGS_PHOTO_PATH),getActivity()));
        return new AlertDialog.Builder(getActivity()).setView(v).create();
    }
}
