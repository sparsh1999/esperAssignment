package com.example.esperassisgnment.Models;

import com.example.esperassisgnment.Models.Entities.Feature;
import com.example.esperassisgnment.Models.Entities.Options;
import com.example.esperassisgnment.Models.Entities.Selection;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
    public Feature feature;
    public List<Options> options;
    public Selection selection;
}
