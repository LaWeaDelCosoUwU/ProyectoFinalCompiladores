package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

import java.util.List;

import laweadelcoso.proyectofinalcompiladores.AnalizadorLexico.Token;

public class ASDR implements Parser{

    private int i = 0;
    private Token preanalisis;
    private final List<Token> tokens;

    public ASDR(List<Token> tokens){

        this.tokens = tokens;
        preanalisis = tokens.get(i);

    }

    //Gramatica
    @Override
    public boolean parse() {

        //PROGRAM -> DECLARATION
        PROGRAM();


        return false;

    }

    private void PROGRAM() {
        DECLARATION();
    }


    /*Declaraciones*/
    //DECLARATION -> FUN_DECL DECLARATION || VAR_DECL DECLARATION || STATEMENT DECLARATION || EMPTY
    private void DECLARATION() {
    }

    //FUN_DECL -> fun FUNCTION
    private void FUN_DECL() {
    }

    //VAR_DECL -> var id VAR_INIT ;
    private void VAR_DECL() {
    }

    //VAR_INIT -> = EXPRESSION
    private void VAR_INIT() {
    }

    /*Sentencias*/
    //STATEMENT -> EXPR_STMT || FOR_STMT || IF_STMT || PRINT_STMT || RETURN_STMT || WHILE_STMT || BLOCK
    private void STATEMENT() {
    }

    //EXPR_STMT -> EXPRESSION ;
    private void EXPR_STMT() {
    }

    //FOR_STMT -> for ( FOR_STMT_1 FOR_STMT_2 FOR_STMT_3 ) STATEMENT
    private void FOR_STMT() {
    }

    //FOR_STMT_1 -> VAR_DECL || EXPR_STMT || ;
    private void FOR_STMT_1() {
    }

    //FOR_STMT_2 -> EXPRESSION; || ;
    private void FOR_STMT_2() {
    }

    //FOR_STMT_3 -> EXPRESSION || EMPTY
    private void FOR_STMT_3() {
    }

    //IF_STMT -> if (EXPRESSION) STATEMENT ELSE_STATEMENT
    private void IF_STMT() {
    }

    //ELSE_STATEMENT -> else STATEMENT || EMPTY
    private void ELSE_STMT() {
    }

    //PRINT_STMT -> print EXPRESSION ;
    private void PRINT_STMT() {
    }

    //RETURN_STMT -> return RETURN_EXP_OPC ;
    private void RETURN_STMT() {
    }

    //RETURN_EXP_OPC -> EXPRESSION || EMPTY
    private void RETURN_EXP_OPC() {
    }

    //WHILE_STMT -> while ( EXPRESSION ) STATEMENT
    private void WHILE_STMT() {
    }

    //BLOCK -> { DECLARATION }
    private void BLOCK() {
    }

    /*Expresiones*/
    //EXPRESSION -> ASSIGNMENT
    private void EXPRESSION() {
    }

    //ASSIGNMENT -> LOGIC_OR ASSIGNMENT_OPC
    private void ASSIGNMENT() {
    }

    //ASSIGNMENT_OPC -> = EXPRESSION || EMPTY
    private void ASSIGNMENT_OPC() {
    }

    //LOGIC_OR -> LOGIC_AND LOGIC_OR_2
    private void LOGIC_OR() {
    }

    //LOGIC_OR_2 -> or LOGIC_AND LOGIC_OR_2 || EMPTY
    private void LOGIC_OR_2() {
    }

    //LOGIC_AND -> EQUALITY LOGIC_AND_2
    private void LOGIC_AND() {
    }

    //LOGIC_AND_2 -> and EQUALITY LOGIC_AND_2 || EMPTY
    private void LOGIC_AND_2() {
    }

    //EQUALITY -> COMPARISON EQUALITY_2
    private void EQUALITY() {
    }

    //EQUALITY_2 -> != COMPARISON EQUALITY_2 || == COMPARISON EQUALITY_2 || == COMPARISON EQUALITY_2 || EMPTY
    private void EQUALITY_2() {
    }

    //COMPARISON -> TERM COMPARISON_2
    private void COMPARISON() {
    }

    //COMPARISON_2 -> > TERM COMPARISON_2 || >= TERM COMPARISON_2 || < TERM COMPARISON_2 || <= TERM COMPARISON_2 || EMPTY
    private void COMPARISON_2() {
    }

    //TERM -> FACTOR TERM_2
    private void TERM() {
    }

    //TERM_2 -> - FACTOR TERM_2 || + FACTOR TERM_2 || EMPTY
    private void TERM_2() {
    }

    //FACTOR -> UNARY FACTOR_2
    private void FACTOR() {
    }

    //FACTOR_2 -> / UNARY FACTOR_2 || * UNARY FACTOR_2 || EMPTY
    private void FACTOR_2() {
    }

    //UNARY -> ! UNARY || - UNARY || CALL
    private void UNARY() {
    }

    //CALL -> PRIMARY CALL_2
    private void CALL() {
    }

    //CALL_2 -> ( ARGUMENTS_OPC ) CALL_2 || EMPTY
    private void CALL_2() {
    }

    //PRIMARY -> true || false || null || number || string || id || ( EXPRESSION )
    private void PRIMARY() {
    }


    /*Otros*/
    //FUNCTION -> id ( PARAMETERS_OPC ) BLOCK
    private void FUNCTION() {
    }

    //FUNCTIONS -> FUN_DECL FUNCTIONS || EMPTY
    private void FUNCTIONS() {
    }

    //PARAMETERS_OPC -> PARAMETERS || EMPTY
    private void PARAMETERS_OPC() {
    }

    //PARAMETERS -> id PARAMETERS_2
    private void PARAMETERS() {
    }

    //PARAMETERS_2 -> , id PARAMETERS_2 || EMPTY
    private void PARAMETERS_2() {
    }

    //ARGUMENTS_OPC -> EXPRESSION ARGUMENTS || EMPTY
    private void ARGUMENTS_OPC() {
    }

    //ARGUMENTS -> , EXPRESSION ARGUMENTS || EMPTY
    private void ARGUMENTS() {
    }


}