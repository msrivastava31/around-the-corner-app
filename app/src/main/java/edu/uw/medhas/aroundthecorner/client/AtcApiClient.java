package edu.uw.medhas.aroundthecorner.client;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;

import edu.uw.medhas.aroundthecorner.dto.PlaceList;
import edu.uw.medhas.aroundthecorner.presenter.PlaceSearchPresenter;

/**
 * Created by medhas on 2/27/18.
 */

public class AtcApiClient extends AsyncTask<String, Void, PlaceList> {
    private final String mBaseUrl;
    private final PlaceSearchPresenter mPlaceSearchPresenter;
    private final ObjectMapper mObjectMapper;

    public AtcApiClient(String baseUrl, PlaceSearchPresenter placeSearchPresenter) {
        super();
        mBaseUrl = baseUrl;
        mPlaceSearchPresenter = placeSearchPresenter;
        mObjectMapper = new ObjectMapper();
    }

    @Override
    protected PlaceList doInBackground(String... args) {
        final String urlStr = mBaseUrl + "?latlng=" + args[0] + "&category=" + args[1] + "&max_results=" + args[2];

        URL url;
        HttpURLConnection httpURLConnection = null;
        PlaceList placeList = null;
        try {
            url = new URL(urlStr);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(20000);
            httpURLConnection.setReadTimeout(20000);
            httpURLConnection.setRequestProperty("accept", "application/json");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            placeList = mObjectMapper.readValue(httpURLConnection.getInputStream(), PlaceList.class);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return placeList;
    }

    @Override
    protected void onPostExecute(PlaceList placeList) {
        super.onPostExecute(placeList);
        mPlaceSearchPresenter.placeSearchCallback(placeList);
    }
}
