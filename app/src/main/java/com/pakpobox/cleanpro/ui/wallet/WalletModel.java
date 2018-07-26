package com.pakpobox.cleanpro.ui.wallet;

import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.bean.Wallet;
import com.pakpobox.cleanpro.common.UrlConstainer;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:36
 */

public class WalletModel extends BaseModel implements WalletContract.IWalletModel {
    @Override
    public void getWallet(NetCallback<Wallet> callback) {
        getRequest(getApiUrl(UrlConstainer.WALLET), getBaseHttpHeader(), callback);
    }
}

