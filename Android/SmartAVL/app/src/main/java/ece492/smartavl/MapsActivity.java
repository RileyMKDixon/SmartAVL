package ece492.smartavl;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends MainNavigationActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap mMap;
    private Marker marker;

    private TextView latitudeTextView;
    private TextView longitudeTextView;

    protected void onMainSelected() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    protected void onMapSelected() {
        // do nothing
    }

    protected void onLogSelected() {
        Intent intent = new Intent(this, LogActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_maps);
        super.onCreate(savedInstanceState);

        mapView = findViewById(R.id.map);
        mapView.onCreate(null);
        mapView.getMapAsync(this);

        latitudeTextView = findViewById(R.id.lat_value_textView);
        longitudeTextView = findViewById(R.id.lon_value_textView);

        navigation.setSelectedItemId(R.id.navigation_map);
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
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        setInitialLocation(-34, 151);
    }

    public void setInitialLocation(int lat, int lon) {
        LatLng location = new LatLng(lat, lon);
        marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                .fromResource(R.drawable.map_marker_icon)).position(location)
                .title(getString(R.string.map_marker_title)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        latitudeTextView.setText(String.valueOf(lat));
        longitudeTextView.setText(String.valueOf(lon));
        latitudeTextView.invalidate();
        longitudeTextView.invalidate();
    }


    public void updateLocation(int lat, int lon, boolean moveCamera) {
        LatLng location = new LatLng(lat, lon);
        marker.setPosition(location);
        latitudeTextView.setText(String.valueOf(lat));
        longitudeTextView.setText(String.valueOf(lon));
        latitudeTextView.invalidate();
        longitudeTextView.invalidate();
        if (moveCamera) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
