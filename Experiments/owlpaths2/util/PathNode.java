package org.detwiler.owltools.owlpaths2.util;

import org.detwiler.owltools.owlpaths2.PathExpression;
import org.detwiler.owltools.owlpaths2.SimpleNode;

public class PathNode extends SimpleNode {
    private String operator = null;
    private int operatorType = 0;
    private String supQual = null;
    private String invQual = null;

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

    @Override
    public String toString() {
        return this.getClass() +" {" +
                "value=" + value +
                '}';
    }
}
