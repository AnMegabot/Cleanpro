package com.pakpobox.cleanpro.ui.price;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.list.BaseListAdapter;
import com.pakpobox.cleanpro.base.list.ListDataHolder;
import com.pakpobox.cleanpro.bean.price.ItemProp;
import com.pakpobox.cleanpro.bean.price.Price;
import com.pakpobox.cleanpro.bean.price.Sku;
import com.pakpobox.cleanpro.utils.SystemUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User:Sean.Wei
 * Date:2018/6/27
 * Time:12:14
 */

public class PriceAdapter extends BaseListAdapter<Price> {

    private OnItemClickListener listener;
    private Context mContext;

    public PriceAdapter(Context context, OnItemClickListener listener) {
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_price_list;
    }

    @Override
    public void bindDatas(ListDataHolder holder, final  Price bean, int itemType, int position) {
        LinearLayout titleLayout = holder.getView(R.id.item_price_title_llt);
        ImageView typeIm = holder.getView(R.id.item_price_type_icon);
        TextView titleTv = holder.getView(R.id.item_price_title_tv);
        LinearLayout contentLayout = holder.getView(R.id.item_price_content_layout);
        contentLayout.removeAllViews();
        if (null == bean)
            return;
        titleTv.setText(bean.getName_en());
        typeIm.setImageResource("Laundry".equals(bean.getName_en()) ? R.mipmap.icon_laundry_2 : R.mipmap.icon_dryer_2);
        titleLayout.setBackgroundColor("Laundry".equals(bean.getName_en()) ?  mContext.getResources().getColor(R.color.price_item_laundry_title_bg): mContext.getResources().getColor(R.color.price_item_dryer_title_bg));

        List<ItemProp> itemProps = bean.getItem_props();
        if (null == itemProps)
            return;

        int columns = 3;

        ItemProp priceItem = new ItemProp();
        priceItem.setName("Price(RM)");
        itemProps.add(priceItem);

        //titil
        TableLayout titleTbl = createTable(itemProps.size());
        titleTbl.setBackgroundColor(mContext.getResources().getColor(R.color.price_item_bg_dark));
        contentLayout.addView(titleTbl);

        TableRow tltleTr = createTableRow();
        titleTbl.addView(tltleTr);

        for (int i = 0; i<columns; i++) {
            ItemProp itemProp = i<itemProps.size() ? itemProps.get(i) : null;
            if (null != itemProp) {
                TextView title = createTableTv();
                tltleTr.addView(title);
                title.setText(itemProp.getName());
            }
        }

        //velue
        List<Sku> skuList = bean.getSku_list();
        if (null == skuList)
            return;

        Map<String, List<Sku>> skuMap = new LinkedHashMap<>();
        for (Sku sku : skuList) {
            List<Sku> skus = skuMap.get(sku.getProp_values().get(0).getValue());
            if (null == skus)
                skus = new ArrayList<>();
            skus.add(sku);
            skuMap.put(sku.getProp_values().get(0).getValue(), skus);
        }
        int i = 0;
        for (String str : skuMap.keySet()) {
            i++;
            TableLayout valueTbl = createTable(itemProps.size());
            if (i%2 == 0)
                valueTbl.setBackgroundColor(mContext.getResources().getColor(R.color.price_item_bg_dark));
            contentLayout.addView(valueTbl);

            TableRow valueTr = createTableRow();
            valueTbl.addView(valueTr);

            List<Sku> skus = skuMap.get(str);
            if (itemProps.size() == 2){
                TextView durationTv = createTableTv();
                durationTv.setText(str);
                valueTr.addView(durationTv);

                TextView drPriceTv = createTableTv();
                drPriceTv.setText( SystemUtils.formatFloat2Str(skus.get(0).getPrice()));
                valueTr.addView(drPriceTv);

            }else{
                TextView capTv = createTableTv();
                capTv.setPaddingRelative(0, 0, 0, 0);
                capTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                TableRow.LayoutParams tltleTvLps = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT);
                tltleTvLps.weight = 1;
                capTv.setLayoutParams(tltleTvLps);
                capTv.setText(str);
                valueTr.addView(capTv);

                TableLayout priceTbl = createInerTable();
                valueTr.addView(priceTbl);
                for (Sku sku : skus) {
                    TableRow priceTr = createInerTableRow();
                    priceTbl.addView(priceTr);

                    TextView tembTv = createTableTv();
                    tembTv.setText(sku.getProp_values().get(1).getValue());
                    priceTr.addView(tembTv);

                    TextView priceTv = createTableTv();
                    priceTv.setText(SystemUtils.formatFloat2Str(sku.getPrice()));
                    priceTr.addView(priceTv);
                }
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(bean);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(Price order);
    }

    private TableLayout createTable(int columns) {
        TableLayout titleTbl = new TableLayout(mContext);
        if (columns == 2) {
            titleTbl.setColumnCollapsed(2, true);
        }
        titleTbl.setOrientation(LinearLayout.VERTICAL);
        titleTbl.setDividerDrawable(mContext.getResources().getDrawable(R.drawable.shape_line_tblayout));
        titleTbl.setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING|LinearLayout.SHOW_DIVIDER_MIDDLE|LinearLayout.SHOW_DIVIDER_END);
        LinearLayout.LayoutParams tltleLps = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tltleLps.setMargins(0, 20, 0, 0);
        titleTbl.setLayoutParams(tltleLps);

        return titleTbl;
    }

    private TableLayout createInerTable() {
        TableLayout titleTbl = new TableLayout(mContext);
        titleTbl.setOrientation(LinearLayout.VERTICAL);
        titleTbl.setDividerDrawable(mContext.getResources().getDrawable(R.drawable.shape_line_tblayout));
        titleTbl.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        TableRow.LayoutParams tltleLps = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tltleLps.weight = 2;
        tltleLps.span = 2;
        titleTbl.setLayoutParams(tltleLps);

        return titleTbl;
    }

    private TableRow createTableRow() {
        TableRow tltleTr = new TableRow(mContext);
        tltleTr.setOrientation(LinearLayout.HORIZONTAL);
        tltleTr.setDividerDrawable(mContext.getResources().getDrawable(R.drawable.shape_line_tblayout));
        tltleTr.setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING|LinearLayout.SHOW_DIVIDER_MIDDLE|LinearLayout.SHOW_DIVIDER_END);
        TableLayout.LayoutParams tltleRowLps = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tltleTr.setLayoutParams(tltleRowLps);

        return tltleTr;
    }

    private TableRow createInerTableRow() {
        TableRow tltleTr = new TableRow(mContext);
        tltleTr.setOrientation(LinearLayout.HORIZONTAL);
        tltleTr.setDividerDrawable(mContext.getResources().getDrawable(R.drawable.shape_line_tblayout));
        tltleTr.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        TableLayout.LayoutParams tltleRowLps = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tltleTr.setLayoutParams(tltleRowLps);

        return tltleTr;
    }

    private TextView createTableTv() {
        TextView tableTv = new TextView(mContext);
        tableTv.setGravity(Gravity.CENTER);
        tableTv.setTextColor(mContext.getResources().getColor(R.color.textColorPrimaryDark));
        tableTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        tableTv.setPaddingRelative(0, (int)mContext.getResources().getDimension(R.dimen.price_table_row_padding), 0, (int)mContext.getResources().getDimension(R.dimen.price_table_row_padding));
        TableRow.LayoutParams tltleTvLps = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
        tltleTvLps.weight = 1;
        tableTv.setLayoutParams(tltleTvLps);
        return tableTv;
    }
}
