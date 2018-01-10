package com.example.paralect.easytime.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.paralect.easytime.R;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 29.12.2017.
 */

public abstract class AlphabetStickyAdapter<E> extends BaseAdapter implements StickyListHeadersAdapter {

    private SortedMap<Character, List<E>> mSortedData = new TreeMap<>();
    private int count;

    public void setData(SortedMap<Character, List<E>> sortedData) {
        if (sortedData == null || sortedData.isEmpty())
            mSortedData.clear();
        else
            mSortedData = sortedData;
        count = calculateCount();
        notifyDataSetChanged();
    }

    private int calculateCount() {
        Map<Character, List<E>> map = mSortedData;
        int totalCount = 0;
        for (Character c : map.keySet()) {
            totalCount += map.get(c).size();
        }
        return totalCount;
    }

    private char getSection(int position) {
        int count = 0;
        Map<Character, List<E>> map = mSortedData;
        for (Character c : map.keySet()) {
            List<E> curr = map.get(c);
            if (position < count + curr.size())
                return c;

            count += curr.size();
        }
        return '0';
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        char section = getSection(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_header, parent, false);
        }
        ((TextView) convertView).setText(String.valueOf(section));

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return Character.getNumericValue(getSection(position));
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public E getItem(int i) {
        int count = 0;
        Map<Character, List<E>> map = mSortedData;
        for (Character c : map.keySet()) {
            List<E> curr = map.get(c);
            if (i < count + curr.size())
                return curr.get(i - count);

            count += curr.size();
        }
        return null; // should not happen
    }
}
