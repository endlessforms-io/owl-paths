/* Generated By:JavaCC: Do not edit this line. PathExpressionDefaultVisitor.java Version 7.0.10 */
package org.detwiler.owltools.owlpaths;

public class PathExpressionDefaultVisitor implements PathExpressionVisitor{
  public Object defaultVisit(SimpleNode node, Object data){
    node.childrenAccept(this, data);
    return data;
  }
  public Object visit(SimpleNode node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTStart node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTBinaryOpExpr node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTPropertyExpr node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTFullIRIExpr node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTPrefixIRIExpr node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTUnaryOpExpr node, Object data){
    return defaultVisit(node, data);
  }
}
/* JavaCC - OriginalChecksum=0017c89c0c8265cd740ab1426bcbfe89 (do not edit this line) */
