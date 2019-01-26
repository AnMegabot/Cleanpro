package com.pakpobox.cleanpro.ui.home;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.base.BaseListFragment;
import com.pakpobox.cleanpro.base.list.BaseListAdapter;
import com.pakpobox.cleanpro.bean.Promos;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.bean.Wallet;
import com.pakpobox.cleanpro.bean.location.Site;
import com.pakpobox.cleanpro.ui.account.LoginActivity;
import com.pakpobox.cleanpro.ui.main.MainFragment;
import com.pakpobox.cleanpro.ui.my.CreditFragment;
import com.pakpobox.cleanpro.ui.price.PriceFragment;
import com.pakpobox.cleanpro.ui.wallet.WalletFragment;
import com.pakpobox.cleanpro.utils.SystemUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;

import butterknife.BindView;

/**
 * 首页
 */
public class HomeFragment extends BaseListFragment<HomePresenter, HomeContract.IHomeView, Promos> implements HomeContract.IHomeView, View.OnClickListener {
    public static final int LOGIN_REQUEST_CODE = 0x0011;

    @BindView(R.id.home_containerLayout)
    FrameLayout mContainerLayout;

    Banner homeBanner;
    TextView mBalanceTv;
    TextView mCouponsTv;
    TextView mPointsTv;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected boolean isRegisterEvent() {
        return true;
    }

    @Override
    protected void initViews(View view) {
//        StatusBarUtil.setHeight(getContext(), mToolbar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initListView(mContainerLayout);
        mPresenter.getUserInfo();
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(getActivity());
    }

    @Override
    protected void loadDatas() {
        mPresenter.getPromosList();
    }

    @Override
    protected boolean isCanLoadMore() {
        return true;
    }

    @Override
    protected BaseListAdapter getListAdapter() {
        return new PromosListAdapter(getContext(), new PromosListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Promos order) {

            }
        });
    }

    @Override
    public void getUserInfoSuccess(UserBean userBean) {
        if (null != userBean) {
            mCouponsTv.setText(String.format(getString(R.string.my_coupons_info), userBean.getCouponCount()));
            mPointsTv.setText(userBean.getCredit() + "");
            Wallet wallet = userBean.getWallet();
            if (null != wallet) {
                mBalanceTv.setText(SystemUtils.formatFloat2Str(wallet.getBalance()));
            }
        }

    }

    @Override
    protected View initHeaderView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home_header, null, false);
        homeBanner = view.findViewById(R.id.home_banner);
        mBalanceTv = view.findViewById(R.id.home_balance_tv);
        mCouponsTv = view.findViewById(R.id.home_coupons_tv);
        mPointsTv = view.findViewById(R.id.home_points_tv);

        view.findViewById(R.id.home_balance_btn).setOnClickListener(this);
        view.findViewById(R.id.home_coupons_btn).setOnClickListener(this);
        view.findViewById(R.id.home_points_btn).setOnClickListener(this);
        view.findViewById(R.id.home_view_all_btn).setOnClickListener(this);
        initBanner();
        return view;
    }

    private void initBanner() {
        if (null == homeBanner)
            return;
        //设置banner样式
        homeBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        homeBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        Integer[] sh_images = {R.mipmap.banner, R.mipmap.banner_1};
        homeBanner.setImages(Arrays.asList(sh_images));
        //设置banner动画效果
//        homeBanner.setBannerAnimation(Transformer.ForegroundToBackground);
        //设置标题集合（当banner样式有显示title时）
//        homeBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        homeBanner.isAutoPlay(true);
        //设置轮播时间
        homeBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        homeBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置点击监听
        homeBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        //banner设置方法全部调用完毕时最后调用
        homeBanner.start();

    }

    @Override
    public boolean isStatusLayoutEnable() {
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        if (null != homeBanner)
            homeBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        if (null != homeBanner)
            homeBanner.stopAutoPlay();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case LOGIN_REQUEST_CODE:
                clickClickingView();
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_balance_btn:
                if (!AppSetting.isLogin()) {
                    getActivity().startActivityForResult(new Intent(getContext(), LoginActivity.class), LOGIN_REQUEST_CODE);
                    setClickingView(view);
                } else {
                    if (getParentFragment() instanceof MainFragment) {
                        ((MainFragment) getParentFragment()).start(WalletFragment.newInstance());
                    }
                }
                break;
            case R.id.home_coupons_btn:
                if (!AppSetting.isLogin()) {
                    getActivity().startActivityForResult(new Intent(getContext(), LoginActivity.class), LOGIN_REQUEST_CODE);
                    setClickingView(view);
                } else {
                    if (getParentFragment() instanceof MainFragment) {
                        ((MainFragment) getParentFragment()).start(WalletFragment.newInstance());
                    }
                }
                break;
            case R.id.home_points_btn:
                if (!AppSetting.isLogin()) {
                    getActivity().startActivityForResult(new Intent(getContext(), LoginActivity.class), LOGIN_REQUEST_CODE);
                    setClickingView(view);
                } else {
                    if (getParentFragment() instanceof MainFragment) {
                        ((MainFragment) getParentFragment()).start(CreditFragment.newInstance());
                    }
                }
                break;
            case R.id.home_view_all_btn:
                if (getParentFragment() instanceof MainFragment) {
                    ((MainFragment) getParentFragment()).start(PriceFragment.newInstance());
                }
                break;
        }
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */

            //Glide 加载图片简单用法
//            Glide.with(context).load(path).into(imageView);
            Glide.with(context).load(context.getResources().getDrawable((int) path)).into(imageView);

            //Picasso 加载图片简单用法
//            Picasso.with(context).load(path).into(imageView);

            //用fresco加载图片简单用法，记得要写下面的createImageView方法
//            Uri uri = Uri.parse((String) path);
//            imageView.setImageURI(uri);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
//            SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
//            return simpleDraweeView;
            return super.createImageView(context);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(UserBean event) {
        if (AppSetting.isLogin()) {
            clickClickingView();
        }
    }
}
