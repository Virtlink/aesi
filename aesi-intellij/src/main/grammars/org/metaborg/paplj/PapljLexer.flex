package org.metaborg.paplj;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.metaborg.paplj.psi.PapljTypes.*;
import static org.metaborg.paplj.psi.PapljTokenElementTypes.*;

%%

%{
  public _PapljLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _PapljLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

ID=[:letter:][a-zA-Z_0-9]*
INT=[0-9]+

%%
<YYINITIAL> {
  {WHITE_SPACE}      { return WHITE_SPACE; }

  "#"                { return SHA; }
  "!"                { return EXCL; }
  ";"                { return SEMICOLON; }
  "."                { return DOT; }
  "*"                { return ASTERISK; }
  "+"                { return PLUS; }
  "-"                { return MINUS; }
  "/"                { return SLASH; }
  "("                { return BRACE_L; }
  ")"                { return BRACE_R; }
  "{"                { return CBRACE_L; }
  "}"                { return CBRACE_R; }
  "["                { return BRACK_L; }
  "]"                { return BRACK_R; }
  "<"                { return ABRACK_L; }
  ">"                { return ABRACK_R; }
  ","                { return COMMA; }
  "="                { return EQ; }
  "||"               { return OROR; }
  "&&"               { return ANDAND; }
  "=="               { return EQEQ; }
  "!="               { return EXCLEQ; }
  "public"           { return K_PUBLIC; }
  "import"           { return K_IMPORT; }
  "program"          { return K_PROGRAM; }
  "class"            { return K_CLASS; }
  "extends"          { return K_EXTENDS; }
  "run"              { return K_RUN; }
  "true"             { return K_TRUE; }
  "false"            { return K_FALSE; }
  "new"              { return K_NEW; }
  "null"             { return K_NULL; }
  "this"             { return K_THIS; }
  "if"               { return K_IF; }
  "else"             { return K_ELSE; }
  "let"              { return K_LET; }
  "in"               { return K_IN; }
  "as"               { return K_AS; }

  {ID}               { return ID; }
  {INT}              { return INT; }

}

[^] { return BAD_CHARACTER; }
