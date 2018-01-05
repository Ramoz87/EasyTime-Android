package com.example.paralect.easytime.model;

import java.util.Comparator;

/**
 * Created by alexei on 04.01.2018.
 */

public class MaterialComparator implements Comparator<Material> {

    public MaterialComparator() { }

    @Override
    public int compare(Material material, Material t1) {
        if (material == null && t1 == null) return 0;

        if (material == null) return -1;

        if (t1 == null) return 1;

        String name1 = material.getName();
        String name2 = t1.getName();

        if (name1 == null && name2 == null) return 0;

        if (name1 == null) return -1;

        if (name2 == null) return 1;

        return name1.compareTo(name2);
    }
}
