package com.pakpobox.cleanpro.ui.wallet.trlist;

import com.pakpobox.cleanpro.bean.TradingRecort;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * User:Sean.Wei
 * Date:2018/6/27
 * Time:12:08
 */

public class TRListPresenter extends BasePresenter<TRListContract.ITRListView> implements TRListContract.ITRListPresenter {
    private TRListContract.ITRListView rcDetailsView;

    TRListPresenter() {
    }

    @Override
    public void getRechargeDetailList() {
        rcDetailsView = getView();
        rcDetailsView.showLoading("");
        List<TradingRecort> datas = new ArrayList<>();
        for (int i=0; i<10; i++) {
            String type = "Credit card";
            String amount = "+30";
            switch (i % 3) {
                case 0:
                    type = "Credit card";
                    amount = "+30";
                    break;
                case 1:
                    type = "Laundry";
                    amount = "-5";
                    break;
                case 2:
                    type = "Dryer";
                    amount = "-5";
                    break;
                case 3:
                    type = "Scan";
                    amount = "+30";
                    break;
            }
            datas.add(new TradingRecort(UUID.randomUUID().toString(), type, "2018-05-22 12:02", amount));
        }
        if (rcDetailsView.getPage() == 0) {
            rcDetailsView.clearListData();
        }
        if (rcDetailsView.getPage() > 10) {
            rcDetailsView.showNoMore();
        } else {
            rcDetailsView.autoLoadMore();
        }
        rcDetailsView.setData(datas);

        if (rcDetailsView.getData().size() == 0)
            rcDetailsView.showEmpty();
        else
            rcDetailsView.showContent();

        rcDetailsView.hideLoading();
    }

}
