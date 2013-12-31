package com.infinum.gridviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jkmirko on 27/11/13.
 */
public class CustomItemAdapter extends BaseAdapter {

    private static final Integer[] mThumbIds = {
            com.infinum.gridviewdemo.R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };

    private List<CustomItem> items;

    Context mContext;

    public CustomItemAdapter(Context context) {
        mContext = context;

        initItems();
    }

    private void initItems() {
        items = new ArrayList<CustomItem>(mThumbIds.length);

        for(int i = 0; i < mThumbIds.length; i++) {
            items.add(new CustomItem(i, mThumbIds[i]));
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).id;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = view;

        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            rowView = inflater.inflate(R.layout.gv_adapter_image_layout, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.title = (TextView) rowView.findViewById(R.id.tv_title);
            viewHolder.subtitle = (TextView) rowView.findViewById(R.id.tv_subtitle);
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.iv_image);

            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.image.setImageResource(items.get(i).imgResource);
        holder.title.setText("Item num " + getItemId(i));

        return rowView;
    }

    private static class ViewHolder {
        ImageView image;
        TextView title;
        TextView subtitle;
    }

    public void deleteItems(long[] checkedItemIds) {
        for(long itemId : checkedItemIds) {
            int numOfItems = items.size();
            for(int i = 0; i < numOfItems; i++) {
                if(items.get(i).id == itemId) {
                    items.remove(i);
                    break;
                }
            }
        }
    }

    public static class CustomItem {
        public long id;
        public int imgResource;

        public CustomItem(long id, int imgResource) {
            this.id = id;
            this.imgResource = imgResource;
        }
    }
}
