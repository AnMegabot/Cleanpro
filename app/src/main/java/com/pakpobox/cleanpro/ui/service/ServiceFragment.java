package com.pakpobox.cleanpro.ui.service;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;

/**
 * 服务
 */
public class ServiceFragment extends BaseFragment {

    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.service_content)
    TextView serviceContent;

    public static ServiceFragment newInstance() {
        Bundle args = new Bundle();
        ServiceFragment fragment = new ServiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mTitleTv.setText(getString(R.string.home_service));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });

        String content = "<font color='#333333'>In the year 2010</font>, Cleanpro Laundry Holdings Sdn Bhd adopted the advanced \"Self-service Laundry Management\" by an American company, Dexter Laundry. Since then, Cleanpro Laundry Holdings Sdn Bhd has converted its first conventional laundry in Jalan Imbi into the \"Integrated Laundry Services (2 in 1) Concept\".<br><br>" +
                "<font color='#333333'>In early 2011</font>, Cleanpro Laundry Holdings Sdn Bhd has further transformed into \"24/7 No manpower Self-service Laundry Concept\" by bringing in and opening up to high-tech equipment, committed to the research and development of a self-planning business model. At this time, the first business model of Cleanpro Express is formed.<br><br>" +
                "<font color='#333333'>Since then</font>, Cleanpro Express has fully carried this concept into a franchising business model that later changed the laundry industry in this region into a new era.";
        serviceContent.setText(Html.fromHtml(content));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service;
    }

}
