package org.detwiler.owltools.owlpaths.util;

import java.util.HashMap;
import java.util.Map;

public class Qualifiers {
    public enum QualType {
        SUP,
        INV
    }

    Map<QualType,String> qualMap = new HashMap<>();

    public String getQualifier(QualType qualType){
        return qualMap.get(qualType);
    }

    public void setQualifier(QualType qualType, String qualVal){
        qualMap.put(qualType, qualVal);
    }
}
