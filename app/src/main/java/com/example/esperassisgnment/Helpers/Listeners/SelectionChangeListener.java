package com.example.esperassisgnment.Helpers.Listeners;

import com.example.esperassisgnment.Models.Entities.Selection;
import java.util.Set;

/**
 * Helper interface used to notify something has changed in selectionSet
 */
public interface SelectionChangeListener {
    void selectionChanged(Set<Selection> selections);
}
