package com.example.paralect.easytime.main.materials;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.paralect.easytime.model.Customer;
import com.example.paralect.easytime.model.Material;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 29.12.2017.
 */

public class MaterialStickyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private SortedMap<Character, List<Material>> sortedCustomers;
    private int count;

    public MaterialStickyAdapter(SortedMap<Character, List<Material>> sortedCustomers) {
        this.sortedCustomers = sortedCustomers;
        count = calculateCount();
    }

    private int calculateCount() {
        Map<Character, List<Material>> map = sortedCustomers;
        int totalCount = 0;
        for (Character c : map.keySet()) {
            totalCount += map.get(c).size();
        }
        return totalCount;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public long getHeaderId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
