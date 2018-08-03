package com.pakpobox.cleanpro.ui.location;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.bean.PageListDataBean;
import com.pakpobox.cleanpro.bean.location.Site;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import java.util.List;

import butterknife.BindView;

/**
 * 我的钱包
 */
public class LocationFragment extends BasePresenterFragment<LocationPresenter, LocationContract.ILocationView> implements LocationContract.ILocationView,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener{
    private static final int RC_LOCATION_PERM = 0x0011;
    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.location_map_container)
    LinearLayout mMapContainer;

    private LinearLayout.LayoutParams mParams;
    private float zoom = 10;//缩放比例
    private double latitude = 3.03319;//纬度
    private double longitude = 101.66505;//经度

    private List<Site> mSiteList = null;

    //谷歌地图
    private MapView mGoogleMapView;//谷歌地图视图
    private GoogleMap googlemap;

    private PopupWindow window = null;
    private Marker lastMarker = null;

    public static LocationFragment newInstance() {
        Bundle args = new Bundle();
        LocationFragment fragment = new LocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mTitleTv.setText(getString(R.string.home_location));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });

        mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createGoogleMapView(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_location;
    }


    @Override
    protected LocationPresenter createPresenter() {
        return new LocationPresenter(getActivity());
    }

    @Override
    public int getPage() {
        return 0;
    }

    @Override
    public void getSuccess(PageListDataBean<Site> data) {
        List<Site> siteList = data.getResultList();
        if (null != siteList) {
            mSiteList = siteList;
            for (Site site : siteList) {
                markGoogleMapSite(site);
            }
        }
    }

    //创建谷歌地图视图
    private void createGoogleMapView(Bundle savedInstanceState) {
        if (!checkGooglePlayServices()) {
            return;
        }

        mGoogleMapView = new MapView(getContext(), new GoogleMapOptions().camera(new CameraPosition(new LatLng(latitude, longitude), zoom, 0, 0)));
        mGoogleMapView.onCreate(savedInstanceState);
        mMapContainer.addView(mGoogleMapView, mParams);
        mGoogleMapView.getMapAsync(this);
    }

    //检查设备google服务是否已安装
    private boolean checkGooglePlayServices() {
        int result = com.google.android.gms.maps.MapsInitializer.initialize(getContext());
        switch (result) {
            case ConnectionResult.SUCCESS:
                return true;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                break;
            case ConnectionResult.SERVICE_INVALID:
                break;
            case ConnectionResult.SERVICE_MISSING:
                break;

        }
        return false;


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googlemap = googleMap;
        if (googlemap != null) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] locationPerms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                requestPermissions(locationPerms, RC_LOCATION_PERM);
                return;
            } else {
                googlemap.setMyLocationEnabled(true);
            }
        }
        mPresenter.getLocations();
        googlemap.setOnMarkerClickListener(this);
        googlemap.setOnMapClickListener(this);
    }

    //谷歌地图mark坐标点
    private void markGoogleMapSite(Site site) {
        try {
            LatLng latLng = new LatLng(site.getLatitude(), site.getLongitude());
            if (null == latLng)
                return;
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(latLng);
            markerOption.snippet(site.getId());

            markerOption.draggable(false);//设置Marker可拖动
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.location_marker)));
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
//            markerOption.setFlat(false);//设置marker平贴地图效果

            googlemap.addMarker(markerOption);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        recoverLastMarker();

        if (null != marker) {
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.location_marker_on)));
            lastMarker = marker;

            if (null != mSiteList && !TextUtils.isEmpty(marker.getSnippet())) {
                Site clickSite = null;
                for (Site site : mSiteList) {
                    if (null != site && site.getId().equals(marker.getSnippet())) {
                        clickSite = site;
                    }
                }
                showInfoPopupView(clickSite);
            }
        }
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        recoverLastMarker();
    }

    //恢复上一个点击的Marker
    private void recoverLastMarker() {
        if (null != lastMarker) {
            lastMarker.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.location_marker)));
            lastMarker = null;
            if (null != window && window.isShowing()) {
                window.dismiss();
                window = null;
            }
        }
    }

    private void showInfoPopupView(Site site) {
        View contentView= LayoutInflater.from(getContext()).inflate(R.layout.pop_map_info, null, false);
        TextView titleTv = contentView.findViewById(R.id.pop_map_info_title_tv);
        TextView locationTv = contentView.findViewById(R.id.pop_map_info_location_tv);
        if (null != site) {
            titleTv.setText(site.getName());
            locationTv.setText(site.getZoneId());
        }
        window = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setOutsideTouchable(false);
        window.setTouchable(false);
        window.setFocusable(false);
        window.setAnimationStyle(R.style.animTranslate);
        window.showAtLocation(mMapContainer, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleMapView != null) {
            try {
                mGoogleMapView.onResume();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleMapView != null) {
            try {
                mGoogleMapView.onPause();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mGoogleMapView != null) {
            try {
                mGoogleMapView.onSaveInstanceState(outState);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recoverLastMarker();

        if (mGoogleMapView != null) {
            try {
                mGoogleMapView.onDestroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_LOCATION_PERM:
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    googlemap.setMyLocationEnabled(true);
                }
                break;
        }

    }
}