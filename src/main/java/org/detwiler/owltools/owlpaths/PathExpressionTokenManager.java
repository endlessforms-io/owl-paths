/* PathExpressionTokenManager.java */
/* Generated By:JJTree&JavaCC: Do not edit this line. PathExpressionTokenManager.java */
package org.detwiler.owltools.owlpaths;
import java.io.*;
import org.detwiler.owltools.owlpaths.util.PathNode;
import org.detwiler.owltools.owlpaths.util.PathExpressionElementVisitor;
import org.detwiler.owltools.owlpaths.util.Qualifiers;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.search.EntitySearcher;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** Token Manager. */
@SuppressWarnings ("unused")
public class PathExpressionTokenManager implements PathExpressionConstants {

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0){
   switch (pos)
   {
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0){
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0(){
   switch(curChar)
   {
      case 9:
         return jjStartNfaWithStates_0(0, 2, 4);
      case 10:
         return jjStartNfaWithStates_0(0, 3, 4);
      case 13:
         return jjStartNfaWithStates_0(0, 4, 4);
      case 32:
         return jjStartNfaWithStates_0(0, 1, 4);
      case 40:
         return jjStopAtPos(0, 5);
      case 41:
         return jjStopAtPos(0, 6);
      case 42:
         return jjStopAtPos(0, 12);
      case 43:
         return jjStopAtPos(0, 13);
      case 47:
         return jjStopAtPos(0, 10);
      case 58:
         return jjStopAtPos(0, 9);
      case 60:
         return jjStartNfaWithStates_0(0, 7, 1);
      case 62:
         return jjStopAtPos(0, 8);
      case 63:
         return jjStopAtPos(0, 14);
      case 124:
         return jjStopAtPos(0, 11);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static final long[] jjbitVec0 = {
   0xfffffffffffffffeL, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static final long[] jjbitVec2 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 21;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 4:
                  if ((0x2bff70ffffffffffL & l) != 0L)
                     { jjCheckNAddTwoStates(3, 4); }
                  else if (curChar == 58)
                     { jjCheckNAdd(5); }
                  break;
               case 0:
                  if ((0x2bff70ffffffffffL & l) != 0L)
                     { jjCheckNAddTwoStates(3, 4); }
                  else if (curChar == 60)
                     { jjCheckNAdd(1); }
                  break;
               case 1:
                  if ((0xafffffda00000000L & l) != 0L)
                     { jjCheckNAddTwoStates(1, 2); }
                  break;
               case 2:
                  if (curChar == 62)
                     kind = 20;
                  break;
               case 3:
                  if ((0x2bff70ffffffffffL & l) != 0L)
                     { jjCheckNAddTwoStates(3, 4); }
                  break;
               case 5:
                  if ((0x2bff70ffffffffffL & l) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  { jjCheckNAdd(5); }
                  break;
               case 7:
                  if (curChar == 61)
                     { jjCheckNAdd(8); }
                  break;
               case 8:
                  if ((0xafffffda00000000L & l) != 0L)
                     { jjCheckNAddTwoStates(8, 9); }
                  break;
               case 13:
                  if (curChar == 61)
                     { jjCheckNAdd(14); }
                  break;
               case 14:
                  if ((0x2bff70ffffffffffL & l) != 0L)
                     { jjCheckNAddTwoStates(14, 15); }
                  break;
               case 15:
                  if (curChar == 58)
                     { jjCheckNAdd(16); }
                  break;
               case 16:
                  if ((0x2bff70ffffffffffL & l) != 0L)
                     { jjCheckNAddTwoStates(16, 17); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 4:
               case 3:
                  if ((0xefffffffd7ffffffL & l) != 0L)
                     { jjCheckNAddTwoStates(3, 4); }
                  break;
               case 0:
                  if ((0xefffffffd7ffffffL & l) != 0L)
                     { jjCheckNAddTwoStates(3, 4); }
                  else if (curChar == 91)
                     { jjAddStates(0, 1); }
                  break;
               case 1:
                  if ((0x47fffffe87ffffffL & l) != 0L)
                     { jjAddStates(2, 3); }
                  break;
               case 5:
                  if ((0xefffffffd7ffffffL & l) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 6:
                  if (curChar == 91)
                     { jjAddStates(0, 1); }
                  break;
               case 8:
                  if ((0x47fffffe87ffffffL & l) != 0L)
                     { jjAddStates(4, 5); }
                  break;
               case 9:
                  if (curChar == 93 && kind > 15)
                     kind = 15;
                  break;
               case 10:
                  if (curChar == 80)
                     jjstateSet[jjnewStateCnt++] = 7;
                  break;
               case 11:
                  if (curChar == 85)
                     jjstateSet[jjnewStateCnt++] = 10;
                  break;
               case 12:
                  if (curChar == 83)
                     jjstateSet[jjnewStateCnt++] = 11;
                  break;
               case 14:
                  if ((0xefffffffd7ffffffL & l) != 0L)
                     { jjAddStates(6, 7); }
                  break;
               case 16:
                  if ((0xefffffffd7ffffffL & l) != 0L)
                     { jjAddStates(8, 9); }
                  break;
               case 17:
                  if (curChar == 93 && kind > 16)
                     kind = 16;
                  break;
               case 18:
                  if (curChar == 86)
                     jjstateSet[jjnewStateCnt++] = 13;
                  break;
               case 19:
                  if (curChar == 78)
                     jjstateSet[jjnewStateCnt++] = 18;
                  break;
               case 20:
                  if (curChar == 73)
                     jjstateSet[jjnewStateCnt++] = 19;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int hiByte = (curChar >> 8);
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 077);
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 4:
               case 3:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2))
                     { jjCheckNAddTwoStates(3, 4); }
                  break;
               case 0:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2))
                     { jjCheckNAddTwoStates(3, 4); }
                  break;
               case 5:
                  if (!jjCanMove_0(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 14:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2))
                     { jjAddStates(6, 7); }
                  break;
               case 16:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2))
                     { jjAddStates(8, 9); }
                  break;
               default : if (i1 == 0 || l1 == 0 || i2 == 0 ||  l2 == 0) break; else break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 21 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, "\50", "\51", "\74", "\76", "\72", "\57", "\174", 
"\52", "\53", "\77", null, null, null, null, null, null, null, };
protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}
static final int[] jjnextStates = {
   12, 20, 1, 2, 8, 9, 14, 15, 16, 17, 
};
private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2)
{
   switch(hiByte)
   {
      case 0:
         return ((jjbitVec2[i2] & l2) != 0L);
      default :
         if ((jjbitVec0[i1] & l1) != 0L)
            return true;
         return false;
   }
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(Exception e)
   {
      jjmatchedKind = 0;
      jjmatchedPos = -1;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

void SkipLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
void MoreLexicalActions()
{
   jjimageLen += (lengthOfMatch = jjmatchedPos + 1);
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
void TokenLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

    /** Constructor. */
    public PathExpressionTokenManager(SimpleCharStream stream){

      if (SimpleCharStream.staticFlag)
            throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");

    input_stream = stream;
  }

  /** Constructor. */
  public PathExpressionTokenManager (SimpleCharStream stream, int lexState){
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Reinitialise parser. */
  
  public void ReInit(SimpleCharStream stream)
  {


    jjmatchedPos =
    jjnewStateCnt =
    0;
    curLexState = defaultLexState;
    input_stream = stream;
    ReInitRounds();
  }

  private void ReInitRounds()
  {
    int i;
    jjround = 0x80000001;
    for (i = 21; i-- > 0;)
      jjrounds[i] = 0x80000000;
  }

  /** Reinitialise parser. */
  public void ReInit(SimpleCharStream stream, int lexState)
  
  {
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Switch to specified lex state. */
  public void SwitchTo(int lexState)
  {
    if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
    else
      curLexState = lexState;
  }


/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0x31ffe1L, 
};
static final long[] jjtoSkip = {
   0x1eL, 
};
static final long[] jjtoSpecial = {
   0x0L, 
};
static final long[] jjtoMore = {
   0x0L, 
};
    protected SimpleCharStream  input_stream;

    private final int[] jjrounds = new int[21];
    private final int[] jjstateSet = new int[2 * 21];
    private final StringBuilder jjimage = new StringBuilder();
    private StringBuilder image = jjimage;
    private int jjimageLen;
    private int lengthOfMatch;
    protected int curChar;
}
