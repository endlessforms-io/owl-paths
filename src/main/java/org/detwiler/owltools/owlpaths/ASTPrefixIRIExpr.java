/* Generated By:JJTree: Do not edit this line. ASTPrefixIRIExpr.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.detwiler.owltools.owlpaths;

public
class ASTPrefixIRIExpr extends org.detwiler.owltools.owlpaths.util.PathNode {
  public ASTPrefixIRIExpr(int id) {
    super(id);
  }

  public ASTPrefixIRIExpr(PathExpression p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(PathExpressionVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=c98556db2f53cb2d6590254f85f5eb86 (do not edit this line) */
