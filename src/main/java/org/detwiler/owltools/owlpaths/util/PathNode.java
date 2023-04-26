package org.detwiler.owltools.owlpaths.util;

import org.detwiler.owltools.owlpaths.SimpleNode;
import org.detwiler.owltools.owlpaths.PathExpression;

import java.util.HashMap;
import java.util.Map;

public class PathNode extends SimpleNode {
    private String operator = null;
    private int operatorType = 0;
    //private String supQual = null;
    //private String invQual = null;
    private Qualifiers qualifiers = new Qualifiers();

    public PathNode(int i) {
        super(i);
    }

    public PathNode(PathExpression p, int i) {
        super(p, i);
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(int opType) {
        this.operatorType = opType;
    }

    /*
    public String getSupQual() {
        return supQual;
    }

    public void setSupQual(String supQual) {
        this.supQual = supQual;
    }

    public String getInvQual() {
        return invQual;
    }

    public void setInvQual(String invQual) {
        this.invQual = invQual;
    }

     */

    public Qualifiers getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(Qualifiers qualifiers) {
        this.qualifiers = qualifiers;
    }

    @Override
    public String toString() {
        return this.getClass() + " {" +
                "Operator=" + operator +
                (qualifiers.getQualifier(Qualifiers.QualType.SUP) == null ? "" : " with superclass qualifier " + qualifiers.getQualifier(Qualifiers.QualType.SUP)) +
                '}';
    }
}
