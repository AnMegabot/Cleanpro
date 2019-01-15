package com.pakpobox.cleanpro.ui.wallet;

import com.pakpobox.cleanpro.bean.Wallet;
import com.pakpobox.cleanpro.ui.mvp.view.IView;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:03
 */

public interface WalletContract {
    interface IWalletPresenter {
        /**
         * 获取钱包
         */
        void getWallet();
    }

    interface IWalletView extends IView {

        /**
         * 获取成功
         * @param data 钱包
         */
        void getSuccess(Wallet data);
    }
}
