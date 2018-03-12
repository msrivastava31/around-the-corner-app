package edu.uw.medhas.aroundthecorner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import edu.uw.medhas.aroundthecorner.constants.AppConstants;
import edu.uw.medhas.aroundthecorner.listener.impl.CategoryListFragmentListenerImpl;
import edu.uw.medhas.aroundthecorner.listener.impl.MapEntityClickListenerImpl;
import edu.uw.medhas.aroundthecorner.listener.impl.MapLocationListener;
import edu.uw.medhas.aroundthecorner.listener.impl.PlaceSelectionListenerImpl;
import edu.uw.medhas.aroundthecorner.model.PlaceDetail;
import edu.uw.medhas.aroundthecorner.presenter.CategoryListFragmentPresenter;
import edu.uw.medhas.aroundthecorner.presenter.CategoryPresenter;
import edu.uw.medhas.aroundthecorner.presenter.MapIconPresenter;
import edu.uw.medhas.aroundthecorner.presenter.MapPresenter;
import edu.uw.medhas.aroundthecorner.presenter.PlacePresenter;
import edu.uw.medhas.aroundthecorner.presenter.PlaceSearchPresenter;
import edu.uw.medhas.aroundthecorner.presenter.impl.CategoryListFragmentPresenterImpl;
import edu.uw.medhas.aroundthecorner.presenter.impl.CategoryPresenterImpl;
import edu.uw.medhas.aroundthecorner.presenter.impl.MapIconPresenterImpl;
import edu.uw.medhas.aroundthecorner.presenter.impl.MapPresenterImpl;
import edu.uw.medhas.aroundthecorner.presenter.impl.PlacePresenterImpl;
import edu.uw.medhas.aroundthecorner.presenter.impl.PlaceSearchPresenterImpl;
import edu.uw.medhas.aroundthecorner.view.CategoryListFragment;

public class AroundTheCornerActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int GPS_REQUEST_PERMISSION_CODE = 22;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private MapLocationListener mMapLocationListener;

    private GoogleMap mGoogleMap;
    private MapPresenter mMapPresenter;
    private PlacePresenter mPlacePresenter;
    private CategoryPresenter mCategoryPresenter;
    private CategoryListFragmentPresenter mCategoryListFragmentPresenter;
    private MapEntityClickListenerImpl mMapEntityClickListener;

    private boolean isLocationAccessPermitted = false;

    private List<PlaceDetail> mSavedPlaceDetailInstance;
    private PlaceDetail mPoiPlace;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.around_the_corner_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (savedInstanceState != null) {
            Bundle bundle = savedInstanceState.getBundle("poi_bundle");

            mPoiPlace = (PlaceDetail) bundle.getParcelable("poi");

            final List<PlaceDetail> places = bundle.getParcelableArrayList("places");
            if (places != null && !places.isEmpty()) {
                mSavedPlaceDetailInstance = new ArrayList<>(places.size());
                for (Parcelable place : places) {
                    PlaceDetail placeDetail = (PlaceDetail) place;
                    mSavedPlaceDetailInstance.add(placeDetail);
                }
            }
        }

        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mFusedLocationProviderClient != null && mMapLocationListener != null) {
            mFusedLocationProviderClient.removeLocationUpdates(mMapLocationListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestLocationUpdates();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("poi", mPlacePresenter.getPointOfInterestPlaceDetail());

        final List<PlaceDetail> placeDetails = mPlacePresenter.getPlaces();
        final ArrayList<PlaceDetail> places = new ArrayList<>(placeDetails.size());

        for (PlaceDetail place : placeDetails) {
            places.add(place);
        }
        bundle.putParcelableArrayList("places", places);

        outState.putBundle("poi_bundle", bundle);

        super.onSaveInstanceState(outState);
    }

    private void requestLocationUpdates() {
        if (mFusedLocationProviderClient == null || mMapLocationListener == null) {
            return;
        }

        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(AppConstants.LOCATION_UPDATE_INTERVAL);
        //locationRequest.setFastestInterval(AppConstants.LOCATION_UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        try {
            mFusedLocationProviderClient.requestLocationUpdates(locationRequest, mMapLocationListener, Looper.myLooper());
        } catch (SecurityException ex) {
        }
    }

    private void enableLocationAccess() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_REQUEST_PERMISSION_CODE);
        } else {
            isLocationAccessPermitted = true;
            setupLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == GPS_REQUEST_PERMISSION_CODE
                && permissions.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isLocationAccessPermitted = true;
            setupMap();
            setupLocation();
        } else {
            isLocationAccessPermitted = false;
            Toast.makeText(this,"Enabling location access will provide a better user experience",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void setupLocation() {
        mMapLocationListener = new MapLocationListener(mMapPresenter);

        requestLocationUpdates();
        setCurrentLocation();
    }

    private void setupMap() {
        if (mGoogleMap != null) {
            try {
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
            } catch (SecurityException ex) {
                mGoogleMap.setMyLocationEnabled(false);
            }
        }
    }

    private void setupMapListeners() {
        // Setup Bottom Sheet for when user clicks marker
        final View placeDetailsView = findViewById(R.id.place_details);

        mMapEntityClickListener = new MapEntityClickListenerImpl(getSupportFragmentManager(), placeDetailsView,
                        mPlacePresenter, mMapPresenter);
        mMapPresenter.setOnMapClickListener(mMapEntityClickListener);

        mGoogleMap.setOnMarkerClickListener(mMapEntityClickListener);
        mGoogleMap.setOnMapClickListener(mMapEntityClickListener);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        // Setup Map Presesnter
        final MapIconPresenter mapIconPresenter = new MapIconPresenterImpl(this);
        final PlaceSearchPresenter placeSearchPresenter = new PlaceSearchPresenterImpl(getString(R.string.base_url));
        mPlacePresenter = new PlacePresenterImpl();
        mMapPresenter = new MapPresenterImpl(
                mGoogleMap, mapIconPresenter, mPlacePresenter, placeSearchPresenter, getResources().getDisplayMetrics()
        );

        // Setup Place Autocomplete feature
        final PlaceSelectionListener placeSelectionListener = new PlaceSelectionListenerImpl(this, mMapPresenter);
        final PlaceAutocompleteFragment placeAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager()
                .findFragmentById(R.id.place_autocomplete_fragment);
        placeAutocompleteFragment.setOnPlaceSelectedListener(placeSelectionListener);

        // Setup Category Presenter
        mCategoryPresenter = new CategoryPresenterImpl();

        // Setup CategoryList feature
        final CategoryListFragment.Listener categoryListFragmentListener
                = new CategoryListFragmentListenerImpl(mMapPresenter, mCategoryPresenter, mPlacePresenter, this);
        mCategoryListFragmentPresenter = new CategoryListFragmentPresenterImpl(categoryListFragmentListener);

        mMapPresenter.zoomOnStartup();

        setupMapListeners();
        setupMap();

        enableLocationAccess();
    }

    private void setCurrentLocation() {
        if (!isLocationAccessPermitted || mFusedLocationProviderClient == null) {
            return;
        }

        try {
            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        final LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        mMapPresenter.setCurrentLocation(currentLatLng, true);
                    }
                    if (mPoiPlace != null) {
                        mMapPresenter.setPointOfInterest(mPoiPlace);
                        mPoiPlace = null;
                    }

                    if (mSavedPlaceDetailInstance != null && !mSavedPlaceDetailInstance.isEmpty()) {
                        mMapPresenter.setPlaces(mSavedPlaceDetailInstance);
                        mSavedPlaceDetailInstance = null;
                    }
                }
            });
        } catch (SecurityException ex) {
            // Do nothing
        }
    }

    public void showCategories(View view) {
        final CategoryListFragment categoryListFragment
                = CategoryListFragment.newInstance(mCategoryPresenter, mCategoryListFragmentPresenter);
        categoryListFragment.show(getSupportFragmentManager(), categoryListFragment.getTag());
    }

    public void setCurrentLocation(View view) {
        if (isLocationAccessPermitted) {
            mMapEntityClickListener.onMapClick(null);
            setCurrentLocation();
        }
    }
}
