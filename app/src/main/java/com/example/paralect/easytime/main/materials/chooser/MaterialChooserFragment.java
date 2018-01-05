package com.example.paralect.easytime.main.materials.chooser;

import com.example.paralect.easytime.Sorter;
import com.example.paralect.easytime.app.EasyTimeManager;
import com.example.paralect.easytime.base.AbsStickyFragment;
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
}
