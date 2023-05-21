package org.detwiler.owltools.owlpaths.util;

import org.detwiler.owltools.owlpaths.SimpleNode;
import org.detwiler.owltools.owlpaths.PathExpression;

public class PathNode extends SimpleNode {
    private String operator = null;
    private int operatorType = 0;

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
