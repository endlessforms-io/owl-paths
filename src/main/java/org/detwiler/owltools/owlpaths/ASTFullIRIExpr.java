/* Generated By:JJTree: Do not edit this line. ASTFullIRIExpr.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.detwiler.owltools.owlpaths;

public
class ASTFullIRIExpr extends org.detwiler.owltools.owlpaths.util.PathNode {
  public ASTFullIRIExpr(int id) {
    super(id);
  }

  public ASTFullIRIExpr(PathExpression p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(PathExpressionVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=2bffacea009fdf93486a431550997a3f (do not edit this line) */
