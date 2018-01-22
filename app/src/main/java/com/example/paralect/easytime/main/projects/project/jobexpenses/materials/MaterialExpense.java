package com.example.paralect.easytime.main.projects.project.jobexpenses.materials;


import android.support.annotation.NonNull;

import com.example.paralect.easytime.model.Material;

class MaterialExpense {
    Material material;
    int count;
    boolean isAdded;

    MaterialExpense(@NonNull Material material) {
        this.material = material;
    }
}
