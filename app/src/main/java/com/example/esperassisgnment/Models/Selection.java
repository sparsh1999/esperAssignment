package com.example.esperassisgnment.Models;

import androidx.annotation.Nullable;

public class Selection {
    public int featureId;
    public int optionId;

    public Selection(int featureId, int optionId){
        this.featureId = featureId;
        this.optionId = optionId;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(this.featureId+"0"+this.optionId);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Selection){
            Selection selection = (Selection)obj;
            if (selection.optionId==this.optionId && selection.featureId==this.featureId)
                return true;
        }
        return false ;
    }
}
