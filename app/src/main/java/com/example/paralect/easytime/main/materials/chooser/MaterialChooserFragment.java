package com.example.paralect.easytime.main.materials.chooser;

import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.paralect.easytime.utils.Sorter;
import com.example.paralect.easytime.manager.EasyTimeManager;
import com.example.paralect.easytime.main.AbsStickyFragment;
import com.example.paralect.easytime.model.Material;
import com.example.paralect.easytime.model.MaterialComparator;

import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by alexei on 04.01.2018.
 */

public class MaterialChooserFragment extends AbsStickyFragment {

    private Comparator<Material> comparator;
    private final Sorter<Material> sorter = new Sorter<Material>() {
        @Override
        public char getCharacterForItem(Material item) {
            return item.getName().charAt(0);
        }
    };

    public static MaterialChooserFragment newInstance() {
        return new MaterialChooserFragment();
    }

    private List<Material> getMaterials() {
        return EasyTimeManager.getMaterials(this.getContext());
    }

    private SortedMap<Character, List<Material>> getSortedMaterials(List<Material> materials) {
        initComparator();
        return sorter.getSortedItems(materials, comparator);
    }

    private void initComparator() {
        if (comparator == null) {
            comparator = new MaterialComparator();
        }
    }

    @Override
    public StickyListHeadersAdapter buildAdapter() {
        List<Material> materials = getMaterials();
        SortedMap<Character, List<Material>> sortedMaterials = getSortedMaterials(materials);
        return new MaterialAlphabetAdapter(sortedMaterials);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreateActionBar(ActionBar actionBar) {
        actionBar.setTitle("Materials");
    }

    @Override
    public boolean needsOptionsMenu() {
        return true;
    }
}
