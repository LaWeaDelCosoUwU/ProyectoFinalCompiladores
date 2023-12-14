package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import laweadelcoso.proyectofinalcompiladores.AnalizadorLexico.TipoToken;
import laweadelcoso.proyectofinalcompiladores.AnalizadorLexico.Token;

public class ASDR implements Parser{

    private int i = 0;
    private Token preanalisis;
    private final List<Token> tokens;
    private boolean hayErrores = false;

    public ASDR(List<Token> tokens){

        this.tokens = tokens;
        preanalisis = tokens.get(i);

    }

    //Gramatica
    @Override
    public boolean parse() {

        PROGRAM();

        if(preanalisis.getTipo() == TipoToken.EOF && !hayErrores){
            System.out.println("No jala");
            return  true;
        }
        else
            System.out.println("Si jala");

        return false;

    }

    //PROGRAM -> DECLARATION
    private List<Statement> PROGRAM() {

        List<Statement> stmts = new ArrayList<>();

        if(hayErrores)
            return null;

        return DECLARATION(stmts);

    }

    /*Declaraciones*/
    //DECLARATION -> FUN_DECL DECLARATION || VAR_DECL DECLARATION || STATEMENT DECLARATION || EMPTY
    private List<Statement> DECLARATION(List<Statement> stmts) {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.FUN){
            Statement statement = FUN_DECL();
            stmts.add(statement);
            return DECLARATION(stmts);
        } else if(preanalisis.getTipo() == TipoToken.VAR){
            Statement statement = VAR_DECL();
            stmts.add(statement);
            return DECLARATION(stmts);
        } else if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN || preanalisis.getTipo() ==TipoToken.PRINT
                || preanalisis.getTipo() == TipoToken.FOR || preanalisis.getTipo() == TipoToken.IF || preanalisis.getTipo() == TipoToken.RETURN || preanalisis.getTipo() == TipoToken.WHILE || preanalisis.getTipo() == TipoToken.LEFT_BRACE){
            Statement statement = STATEMENT();
            stmts.add(statement);
            return DECLARATION(stmts);
        }

        return stmts;

    }

    //FUN_DECL -> fun FUNCTION
    private Statement FUN_DECL() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.FUN){
            match(TipoToken.FUN);
            return FUNCTION();
        } else {
            hayErrores = true;
            return null;
        }

    }

    //VAR_DECL -> var id VAR_INIT ;
    private Statement VAR_DECL() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.VAR){
            match(TipoToken.VAR);
            match(TipoToken.IDENTIFIER);
            Token name = previous();
            Expression init = VAR_INIT();
            match(TipoToken.SEMICOLON);
            return new StmtVar(name,init);
        } else {
            hayErrores = true;
            return null;
        }

    }

    //VAR_INIT -> = EXPRESSION || EMPTY
    private Expression VAR_INIT() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.EQUAL){
            match(TipoToken.EQUAL);
            return EXPRESSION();
        }

        return null;

    }

    /*Sentencias*/
    //STATEMENT -> EXPR_STMT || FOR_STMT || IF_STMT || PRINT_STMT || RETURN_STMT || WHILE_STMT || BLOCK
    private Statement STATEMENT() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            return EXPR_STMT();
        } else if (preanalisis.getTipo() == TipoToken.FOR){
            return FOR_STMT();
        } else if(preanalisis.getTipo() == TipoToken.IF){
            return IF_STMT();
        } else if (preanalisis.getTipo() == TipoToken.PRINT){
            return PRINT_STMT();
        } else if(preanalisis.getTipo() == TipoToken.RETURN){
            return RETURN_STMT();
        } else if (preanalisis.getTipo() == TipoToken.WHILE){
            return WHILE_STMT();
        } else if (preanalisis.getTipo() == TipoToken.LEFT_BRACE){
            return BLOCK();
        } else{
            hayErrores = true;
            return null;
        }

    }

    //EXPR_STMT -> EXPRESSION ;
    private Statement EXPR_STMT() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            Expression expr = EXPRESSION();
            match(TipoToken.SEMICOLON);
            return new StmtExpression(expr);
        } else {
            hayErrores = true;
            return null;
        }

    }

    //FOR_STMT -> for ( FOR_STMT_1 FOR_STMT_2 FOR_STMT_3 ) STATEMENT
    private Statement FOR_STMT() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.FOR){
            match(TipoToken.FOR);
            match(TipoToken.LEFT_PAREN);
            Statement init = FOR_STMT_1();
            Expression con = FOR_STMT_2();
            Expression inc = FOR_STMT_3();
            match(TipoToken.RIGHT_PAREN);
            Statement body = STATEMENT();

            if(inc != null)
                body = new StmtBlock(Arrays.asList(body,new StmtExpression(inc)));

            if(con == null)
                con = new ExprLiteral(true);

            body = new StmtLoop(con,body);

            if(init != null)
                body = new StmtBlock(Arrays.asList(init,body));

            return body;
        } else {
            hayErrores = true;
            return null;
        }

    }

    //FOR_STMT_1 -> VAR_DECL || EXPR_STMT || ;
    private Statement FOR_STMT_1() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.VAR){
            return VAR_DECL();
        } else if (preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            return EXPR_STMT();
        } else if(preanalisis.getTipo() == TipoToken.SEMICOLON){
            match(TipoToken.SEMICOLON);
            return null;
        } else {
            hayErrores = true;
            return null;
        }

    }

    //FOR_STMT_2 -> EXPRESSION; || ;
    private Expression FOR_STMT_2() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            Expression expr = EXPRESSION();
            match(TipoToken.SEMICOLON);
            return expr;
        } else if(preanalisis.getTipo() == TipoToken.SEMICOLON){
            match(TipoToken.SEMICOLON);
            return null;
        } else {
            hayErrores = true;
            return null;
        }

    }

    //FOR_STMT_3 -> EXPRESSION || EMPTY
    private Expression FOR_STMT_3() {

        if(hayErrores)
            return null;
        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN)
            return EXPRESSION();


        return null;
    }

    //IF_STMT -> if (EXPRESSION) STATEMENT ELSE_STATEMENT
    private Statement IF_STMT() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.IF){
            match(TipoToken.IF);
            match(TipoToken.LEFT_PAREN);
            Expression condition = EXPRESSION();
            match(TipoToken.RIGHT_PAREN);
            Statement thenBranch = STATEMENT();
            Statement elseBranch = ELSE_STMT();
            return new StmtIf(condition,thenBranch,elseBranch);
        } else{
            hayErrores = true;
            return null;
        }

    }

    //ELSE_STATEMENT -> else STATEMENT || EMPTY
    private Statement ELSE_STMT() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.ELSE){
            match(TipoToken.ELSE);
            return STATEMENT();
        }
        return null;

    }

    //PRINT_STMT -> print EXPRESSION ;
    private Statement PRINT_STMT() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.PRINT){
            match(TipoToken.PRINT);
            StmtPrint stmtprint = new StmtPrint(EXPRESSION());
            match(TipoToken.SEMICOLON);
            return stmtprint;
        } else {
            hayErrores = true;
            return null;
        }

    }

    //RETURN_STMT -> return RETURN_EXP_OPC ;
    private Statement RETURN_STMT() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.RETURN){
            match(TipoToken.RETURN);
            Expression expr = RETURN_EXP_OPC();
            match(TipoToken.SEMICOLON);
            return new StmtReturn(expr);
        } else {
            hayErrores = true;
            return null;
        }

    }

    //RETURN_EXP_OPC -> EXPRESSION || EMPTY
    private Expression RETURN_EXP_OPC() {

        if(hayErrores)
            return null;

        if (preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN)
            return EXPRESSION();

        return null;
    }

    //WHILE_STMT -> while ( EXPRESSION ) STATEMENT
    private Statement WHILE_STMT() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.WHILE) {
            match(TipoToken.WHILE);
            match(TipoToken.LEFT_PAREN);
            Expression condition = EXPRESSION();
            match(TipoToken.RIGHT_PAREN);
            Statement body = STATEMENT();
            return new StmtLoop(condition,body);
        }
        else {
            hayErrores = true;
            return null;
        }

    }

    //BLOCK -> { DECLARATION }
    private Statement BLOCK() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.LEFT_BRACE){
            match(TipoToken.LEFT_BRACE);
            List<Statement> statements = new ArrayList<>();
            Statement block = new StmtBlock(DECLARATION(statements));
            match(TipoToken.RIGHT_BRACE);
            return block;
        } else {
            hayErrores = true;
            return null;
        }

    }

    /*Expresiones*/
    //EXPRESSION -> ASSIGNMENT
    private Expression EXPRESSION() {

        if(hayErrores)
            return null;
        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            return ASSIGNMENT();
        } else {
            hayErrores = true;
            return null;
        }

    }

    //ASSIGNMENT -> LOGIC_OR ASSIGNMENT_OPC
    private Expression ASSIGNMENT() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            return ASSIGNMENT_OPC(LOGIC_OR());
        } else {
            hayErrores = true;
            return null;
        }
    }

    //ASSIGNMENT_OPC -> = EXPRESSION || EMPTY
    private Expression ASSIGNMENT_OPC(Expression expr) {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.EQUAL){
            match(TipoToken.EQUAL);
            Token name = previous();
            Expression val = EXPRESSION();
            return new ExprAssign(name,val);
        }

        return expr;

    }

    //LOGIC_OR -> LOGIC_AND LOGIC_OR_2
    private Expression LOGIC_OR() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            return LOGIC_OR_2(LOGIC_AND());
        } else {
            hayErrores = true;
            return null;
        }

    }

    //LOGIC_OR_2 -> or LOGIC_AND LOGIC_OR_2 || EMPTY
    private Expression LOGIC_OR_2(Expression expr) {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.OR){
            match(TipoToken.OR);
            Token operator = previous();
            Expression expr2 = LOGIC_AND();
            ExprLogical exprLogical = new ExprLogical(expr,operator,expr2);
            LOGIC_OR_2(exprLogical);
        }

        return expr;

    }

    //LOGIC_AND -> EQUALITY LOGIC_AND_2
    private Expression LOGIC_AND() {

        if(hayErrores)
            return null;
        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            return LOGIC_AND_2(EQUALITY());
        }
        else {
            hayErrores = true;
            return null;
        }

    }

    //LOGIC_AND_2 -> and EQUALITY LOGIC_AND_2 || EMPTY
    private Expression LOGIC_AND_2(Expression expr) {

        if(hayErrores)
            return null;
        if(preanalisis.getTipo() == TipoToken.AND){
            match(TipoToken.AND);
            Token operator = previous();
            Expression expr2 = EQUALITY();
            ExprLogical exprLogical = new ExprLogical(expr,operator,expr2);
            LOGIC_AND_2(exprLogical);
        }

        return expr;

    }

    //EQUALITY -> COMPARISON EQUALITY_2
    private Expression EQUALITY() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            return EQUALITY_2(COMPARISON());
        } else {
            hayErrores = true;
            return null;
        }

    }

    //EQUALITY_2 -> != COMPARISON EQUALITY_2 || == COMPARISON EQUALITY_2 || EMPTY
    private Expression EQUALITY_2(Expression expr) {

        Token operator;
        Expression expr2;
        ExprBinary exprBinary;

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.BANG_EQUAL){
            match(TipoToken.BANG_EQUAL);
            operator = previous();
            expr2 = COMPARISON();
            exprBinary = new ExprBinary(expr,operator,expr2);
            return EQUALITY_2(exprBinary);
        } else if(preanalisis.getTipo() == TipoToken.EQUAL_EQUAL){
            match(TipoToken.EQUAL_EQUAL);
            operator = previous();
            expr2 = COMPARISON();
            exprBinary = new ExprBinary(expr,operator,expr2);
            return EQUALITY_2(exprBinary);
        }

        return expr;

    }

    //COMPARISON -> TERM COMPARISON_2
    private Expression COMPARISON() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            return COMPARISON_2(TERM());
        } else {
            hayErrores = true;
            return null;
        }

    }

    //COMPARISON_2 -> > TERM COMPARISON_2 || >= TERM COMPARISON_2 || < TERM COMPARISON_2 || <= TERM COMPARISON_2 || EMPTY
    private Expression COMPARISON_2(Expression expr) {

        Token operator;
        Expression expr2;
        ExprBinary exprBinary;

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.GREATER){
            match(TipoToken.GREATER);
            operator = previous();
            expr2 = TERM();
            exprBinary = new ExprBinary(expr,operator,expr2);
            COMPARISON_2(exprBinary);
        } else if(preanalisis.getTipo() == TipoToken.GREATER_EQUAL){
            match(TipoToken.GREATER_EQUAL);
            operator = previous();
            expr2 = TERM();
            exprBinary = new ExprBinary(expr,operator,expr2);
            COMPARISON_2(exprBinary);
        } else if(preanalisis.getTipo() == TipoToken.LESS){
            match(TipoToken.LESS);
            operator = previous();
            expr2 = TERM();
            exprBinary = new ExprBinary(expr,operator,expr2);
            COMPARISON_2(exprBinary);
        } else if(preanalisis.getTipo() == TipoToken.LESS_EQUAL){
            match(TipoToken.LESS_EQUAL);
            operator = previous();
            expr2 = TERM();
            exprBinary = new ExprBinary(expr,operator,expr2);
            COMPARISON_2(exprBinary);
        }

        return expr;

    }

    //TERM -> FACTOR TERM_2
    private Expression TERM() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            return TERM_2(FACTOR());
        } else {
            hayErrores = true;
            return null;
        }

    }

    //TERM_2 -> - FACTOR TERM_2 || + FACTOR TERM_2 || EMPTY
    private Expression TERM_2(Expression expr) {

        Token operator;
        Expression expr2;
        ExprBinary exprBinary;

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.MINUS){
            match(TipoToken.MINUS);
            operator = previous();
            expr2 = FACTOR();
            exprBinary = new ExprBinary(expr,operator,expr2);
            TERM_2(exprBinary);
        } else if(preanalisis.getTipo() == TipoToken.PLUS){
            match(TipoToken.PLUS);
            operator = previous();
            expr2 = FACTOR();
            exprBinary = new ExprBinary(expr,operator,expr2);
            TERM_2(exprBinary);
        }

        return expr;

    }

    //FACTOR -> UNARY FACTOR_2
    private Expression FACTOR() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            return FACTOR_2(UNARY());
        } else {
            hayErrores = true;
            return null;
        }

    }

    //FACTOR_2 -> / UNARY FACTOR_2 || * UNARY FACTOR_2 || EMPTY
    private Expression FACTOR_2(Expression expr) {

        Token operator;
        Expression expr2;
        ExprBinary exprBinary;

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.SLASH){
            match(TipoToken.SLASH);
            operator = previous();
            expr2 = UNARY();
            exprBinary = new ExprBinary(expr,operator,expr2);
            FACTOR_2(exprBinary);
        } else if (preanalisis.getTipo() == TipoToken.STAR) {
            match(TipoToken.STAR);
            operator = previous();
            expr2 = UNARY();
            exprBinary = new ExprBinary(expr,operator,expr2);
            FACTOR_2(exprBinary);
        }

        return expr;

    }

    //UNARY -> ! UNARY || - UNARY || CALL
    private Expression UNARY() {

        Token operator;

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.BANG){
            match(TipoToken.BANG);
            operator = previous();
            return new ExprUnary(operator,UNARY());
        } else if (preanalisis.getTipo() == TipoToken.MINUS) {
            match(TipoToken.MINUS);
            operator = previous();
            return new ExprUnary(operator,UNARY());
        } else if(preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            return CALL();
        } else {
            hayErrores = true;
            return null;
        }

    }

    //CALL -> PRIMARY CALL_2
    private Expression CALL() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            return CALL_2(PRIMARY());
        } else {
            hayErrores = true;
            return null;
        }

    }

    //CALL_2 -> ( ARGUMENTS_OPC ) CALL_2 || EMPTY
    private Expression CALL_2(Expression expr) {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            match(TipoToken.LEFT_PAREN);
            List<Expression> args = ARGUMENTS_OPC();
            match(TipoToken.RIGHT_PAREN);
            CALL_2(new ExprCallFunction(expr, args));
        }

        return expr;

    }

    //PRIMARY -> true || false || null || number || string || id || ( EXPRESSION )
    private Expression PRIMARY() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.TRUE){
            match(TipoToken.TRUE);
            return new ExprLiteral(true);
        } else if(preanalisis.getTipo() == TipoToken.FALSE){
            match(TipoToken.FALSE);
            return new ExprLiteral(false);
        } else if(preanalisis.getTipo() == TipoToken.NULL){
            match(TipoToken.NULL);
            return new ExprLiteral(null);
        } else if(preanalisis.getTipo() == TipoToken.NUMBER){
            match(TipoToken.NUMBER);
            return new ExprLiteral(previous().getLiteral());
        } else if(preanalisis.getTipo() == TipoToken.STRING){
            match(TipoToken.STRING);
            return new ExprLiteral(previous().getLiteral());
        } else if(preanalisis.getTipo() == TipoToken.IDENTIFIER){
            match(TipoToken.IDENTIFIER);
            return new ExprVariable(previous());
        } else if(preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            match(TipoToken.LEFT_PAREN);
            Expression expr = EXPRESSION();
            match(TipoToken.RIGHT_PAREN);
            return new ExprGrouping(expr);
        } else {
            hayErrores = true;
            return null;
        }

    }


    /*Otros*/
    //FUNCTION -> id ( PARAMETERS_OPC ) BLOCK
    private Statement FUNCTION() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.IDENTIFIER) {
            match(TipoToken.IDENTIFIER);
            Token name = previous();
            match(TipoToken.LEFT_PAREN);
            List<Token> params = PARAMETERS_OPC();
            match(TipoToken.RIGHT_PAREN);
            StmtBlock body = (StmtBlock) BLOCK();
            return new StmtFunction(name,params,body);
        } else {
            hayErrores = true;
            return null;
        }

    }

    //FUNCTIONS -> FUN_DECL FUNCTIONS || EMPTY
    /*Ni idea de para qué sea esta, no la llama nadie nunca, así que por el momento se queda así, toda bonita, toda inutil*/
    private void FUNCTIONS() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.FUN){
            FUN_DECL();
            FUNCTIONS();
        }

    }

    //PARAMETERS_OPC -> PARAMETERS || EMPTY
    private List<Token> PARAMETERS_OPC() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.IDENTIFIER){
            return PARAMETERS();
        }

        return null;
    }

    //PARAMETERS -> id PARAMETERS_2
    private List<Token> PARAMETERS() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.IDENTIFIER){
            match(TipoToken.IDENTIFIER);
            List<Token> lstparam = new ArrayList<>();
            lstparam.add(previous());
            PARAMETERS_2(lstparam);
            return lstparam;
        } else {
            hayErrores = true;
            return null;
        }

    }

    //PARAMETERS_2 -> , id PARAMETERS_2 || EMPTY
    private void PARAMETERS_2(List<Token> lstparam) {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.COMMA){
            match(TipoToken.COMMA);
            match(TipoToken.IDENTIFIER);
            lstparam.add(previous());
            PARAMETERS_2(lstparam);
        }

    }

    //ARGUMENTS_OPC -> EXPRESSION ARGUMENTS || EMPTY
    private List<Expression> ARGUMENTS_OPC() {

        if(hayErrores)
            return null;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN) {
            List<Expression> lstargs = new ArrayList<>();
            Expression expr = EXPRESSION();
            lstargs.add(expr);
            ARGUMENTS(lstargs);
            return lstargs;
        }

        return null;

    }

    //ARGUMENTS -> , EXPRESSION ARGUMENTS || EMPTY
    private void ARGUMENTS(List<Expression> lstArgs) {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.COMMA){
            match(TipoToken.COMMA);
            Expression expr = EXPRESSION();
            lstArgs.add(expr);
            ARGUMENTS(lstArgs);
        }

    }

    private void match(TipoToken tt){
        if(preanalisis.getTipo() == tt){
            i++;
            preanalisis = tokens.get(i);
        }else
            hayErrores = true;
    }

    private Token previous(){
        return this.tokens.get(i-1);
    }

}