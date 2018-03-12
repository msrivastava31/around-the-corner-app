package edu.uw.medhas.aroundthecorner.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.text.DecimalFormat;

/**
 * Created by medhas on 1/28/18.
 */

public class PlaceDetail implements Comparable<PlaceDetail>, Parcelable {
    private LatLng mLatLng;
    private Marker mMarker;

    private Integer mPosition;
    private String mName;
    private String mAddress;
    private Double mRating;
    private Double mDistance;

    public PlaceDetail() {}

    protected PlaceDetail(Bundle bundle) {
        mLatLng = (LatLng) bundle.getParcelable("latlng");

        mPosition = (Integer) bundle.getInt("position");
        if (mPosition.equals(-1)) {
            mPosition = null;
        }

        mName = (String) bundle.getString("name");
        mAddress = (String) bundle.getString("address");
        mRating = (Double) bundle.getDouble("rating");
        mDistance = (Double) bundle.getDouble("distance");
    }

    public static final Creator<PlaceDetail> CREATOR = new Creator<PlaceDetail>() {
        @Override
        public PlaceDetail createFromParcel(Parcel in) {
            return new PlaceDetail(in.readBundle());
        }

        @Override
        public PlaceDetail[] newArray(int size) {
            return new PlaceDetail[size];
        }
    };

    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng latLng) {
        mLatLng = latLng;
    }

    public Marker getMarker() {
        return mMarker;
    }

    public void setMarker(Marker marker) {
        mMarker = marker;
    }

    public Integer getPosition() {
        return mPosition;
    }

    public void setPosition(Integer position) {
        mPosition = position;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getRating() {
        if (mRating == null) {
            return "N/A";
        }
        final DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(mRating);
    }

    public void setRating(Double mRating) {
        this.mRating = mRating;
    }

    public String getDistance() {
        final DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(mDistance);
    }

    public void setDistance(Double mDistance) {
        this.mDistance = mDistance;
    }

    @Override
    public int compareTo(@NonNull PlaceDetail placeDetail) {
        return getPosition().compareTo(placeDetail.getPosition());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();

        bundle.putParcelable("latlng", mLatLng);
        bundle.putInt("position", mPosition == null ? -1 : mPosition);
        bundle.putString("name", mName);
        bundle.putString("address", mAddress);
        bundle.putDouble("rating", mRating == null ? 0.00d : mRating);
        bundle.putDouble("distance", mDistance == null ? 0.00d : mDistance);

        parcel.writeBundle(bundle);
    }
}
