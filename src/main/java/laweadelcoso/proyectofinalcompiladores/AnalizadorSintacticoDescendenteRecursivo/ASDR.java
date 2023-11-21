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

        /*Declaraciones*/
        //DECLARATION -> FUN_DECL DECLARATION || VAR_DECL DECLARATION || STATEMENT DECLARATION || EMPTY

        //FUN_DECL -> fun FUNCTION

        //VAR_DECL -> var id VAR_INIT ;

        //VAR_INIT -> = EXPRESSION


        /*Sentencias*/
        //STATEMENT -> EXPR_STMT || FOR_STMT || IF_STMT || PRINT_STMT || RETURN_STMT || WHILE_STMT || BLOCK

        //EXPR_STMT -> EXPRESSION ;

        //FOR_STMT -> for ( FOR_STMT_1 FOR_STMT_2 FOR_STMT_3 ) STATEMENT

        //FOR_STMT_1 -> VAR_DECL || EXPR_STMT || ;

        //FOR_STMT_2 -> EXPRESSION; || ;

        //FOR_STMT_3 -> EXPRESSION || EMPTY

        //IF_STMT -> if (EXPRESSION) STATEMENT ELSE_STATEMENT

        //ELSE_STATEMENT -> else STATEMENT || EMPTY

        //PRINT_STMT -> print EXPRESSION ;

        //RETURN_STMT -> return RETURN_EXP_OPC ;

        //RETURN_EXP_OPC -> EXPRESSION || EMPTY

        //WHILE_STMT -> while ( EXPRESSION ) STATEMENT

        //BLOCK -> { DECLARATION }

        /*Expresiones*/
        //EXPRESSION -> ASSIGNMENT

        //ASSIGNMENT -> LOGIC_OR ASSIGNMENT_OPC

        //ASSIGNMENT_OPC -> = EXPRESSION || EMPTY

        //LOGIC_OR -> LOGIC_AND LOGIC_OR_2

        //LOGIC_OR_2 -> or LOGIC_AND LOGIC_OR_2 || EMPTY

        //LOGIC_AND -> EQUALITY LOGIC_AND_2

        //LOGIC_AND_2 -> and EQUALITY LOGIC_AND_2 || EMPTY

        //EQUALITY -> COMPARISON EQUALITY_2

        //EQUALITY_2 -> != COMPARISON EQUALITY_2 || == COMPARISON EQUALITY_2 || == COMPARISON EQUALITY_2 || EMPTY

        //COMPARISON -> TERM COMPARISON_2

        //COMPARISON_2 -> > TERM COMPARISON_2 || >= TERM COMPARISON_2 || < TERM COMPARISON_2 || <= TERM COMPARISON_2 || EMPTY

        //TERM -> FACTOR TERM_2

        //TERM_2 -> - FACTOR TERM_2 || + FACTOR TERM_2 || EMPTY

        //FACTOR -> UNARY FACTOR_2

        //FACTOR_2 -> / UNARY FACTOR_2 || * UNARY FACTOR_2 || EMPTY

        //UNARY -> ! UNARY || - UNARY || CALL

        //CALL -> PRIMARY CALL_2

        //CALL_2 -> ( ARGUMENTS_OPC ) CALL_2 || EMPTY

        //PRIMARY -> true || false || null || number || string || id || ( EXPRESSION )


        /*Otros*/
        //FUNCTION -> id ( PARAMETERS_OPC ) BLOCK

        //FUNCTIONS -> FUN_DECL FUNCTIONS || EMPTY

        //PARAMETERS_OPC -> PARAMETERS || EMPTY

        //PARAMETERS -> id PARAMETERS_2

        //PARAMETERS_2 -> , id PARAMETERS_2 || EMPTY

        //ARGUMENTS_OPC -> EXPRESSION ARGUMENTS || EMPTY

        //ARGUMENTS -> , EXPRESSION ARGUMENTS || EMPTY

        return false;

    }

}