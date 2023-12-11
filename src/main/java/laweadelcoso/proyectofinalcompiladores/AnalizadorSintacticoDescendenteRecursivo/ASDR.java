package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

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
    private void PROGRAM() {

        if(hayErrores)
            return;

        DECLARATION();

    }

    /*Declaraciones*/
    //DECLARATION -> FUN_DECL DECLARATION || VAR_DECL DECLARATION || STATEMENT DECLARATION || EMPTY
    private void DECLARATION() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.FUN){
            FUN_DECL();
            DECLARATION();
        } else if(preanalisis.getTipo() == TipoToken.VAR){
            VAR_DECL();
            DECLARATION();
        } else if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN || preanalisis.getTipo() ==TipoToken.PRINT
                || preanalisis.getTipo() == TipoToken.FOR || preanalisis.getTipo() == TipoToken.IF || preanalisis.getTipo() == TipoToken.RETURN || preanalisis.getTipo() == TipoToken.WHILE || preanalisis.getTipo() == TipoToken.LEFT_BRACE){
            STATEMENT();
            DECLARATION();
        }

    }

    //FUN_DECL -> fun FUNCTION
    private void FUN_DECL() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.FUN){
            match(TipoToken.FUN);
            FUNCTION();
        } else
            hayErrores = true;

    }

    //VAR_DECL -> var id VAR_INIT ;
    private void VAR_DECL() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.VAR){
            match(TipoToken.VAR);
            match(TipoToken.IDENTIFIER);
            VAR_INIT();
            match(TipoToken.SEMICOLON);
        } else
            hayErrores = true;

    }

    //VAR_INIT -> = EXPRESSION
    private void VAR_INIT() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.EQUAL){
            match(TipoToken.EQUAL);
            EXPRESSION();
        }

    }

    /*Sentencias*/
    //STATEMENT -> EXPR_STMT || FOR_STMT || IF_STMT || PRINT_STMT || RETURN_STMT || WHILE_STMT || BLOCK
    private void STATEMENT() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            EXPR_STMT();
        } else if (preanalisis.getTipo() == TipoToken.FOR){
            FOR_STMT();
        } else if(preanalisis.getTipo() == TipoToken.IF){
            IF_STMT();
        } else if (preanalisis.getTipo() == TipoToken.PRINT){
            PRINT_STMT();
        } else if(preanalisis.getTipo() == TipoToken.RETURN){
            RETURN_STMT();
        } else if (preanalisis.getTipo() == TipoToken.WHILE){
            WHILE_STMT();
        } else if (preanalisis.getTipo() == TipoToken.LEFT_BRACE){
            BLOCK();
        } else
            hayErrores = true;

    }

    //EXPR_STMT -> EXPRESSION ;
    private void EXPR_STMT() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            EXPRESSION();
            match(TipoToken.SEMICOLON);
        } else
            hayErrores = true;

    }

    //FOR_STMT -> for ( FOR_STMT_1 FOR_STMT_2 FOR_STMT_3 ) STATEMENT
    private void FOR_STMT() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.FOR){

            match(TipoToken.FOR);
            match(TipoToken.LEFT_PAREN);
            FOR_STMT_1();
            FOR_STMT_2();
            FOR_STMT_3();
            match(TipoToken.RIGHT_PAREN);
            STATEMENT();

        } else
            hayErrores = true;

    }

    //FOR_STMT_1 -> VAR_DECL || EXPR_STMT || ;
    private void FOR_STMT_1() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.VAR){
            VAR_DECL();
        } else if (preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            EXPR_STMT();
        } else if(preanalisis.getTipo() == TipoToken.SEMICOLON){
            match(TipoToken.SEMICOLON);
        } else
            hayErrores = true;

    }

    //FOR_STMT_2 -> EXPRESSION; || ;
    private void FOR_STMT_2() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            EXPRESSION();
            match(TipoToken.SEMICOLON);
        } else if(preanalisis.getTipo() == TipoToken.SEMICOLON){
            match(TipoToken.SEMICOLON);
        } else
            hayErrores = true;

    }

    //FOR_STMT_3 -> EXPRESSION || EMPTY
    private void FOR_STMT_3() {

        if(hayErrores)
            return;
        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN)
            EXPRESSION();


    }

    //IF_STMT -> if (EXPRESSION) STATEMENT ELSE_STATEMENT
    private void IF_STMT() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.IF){
            match(TipoToken.IF);
            match(TipoToken.LEFT_PAREN);
            EXPRESSION();
            match(TipoToken.RIGHT_PAREN);
            STATEMENT();
            ELSE_STMT();
        } else
            hayErrores = true;

    }

    //ELSE_STATEMENT -> else STATEMENT || EMPTY
    private void ELSE_STMT() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.ELSE){
            match(TipoToken.ELSE);
            STATEMENT();
        }

    }

    //PRINT_STMT -> print EXPRESSION ;
    private void PRINT_STMT() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.PRINT){
            match(TipoToken.PRINT);
            EXPRESSION();
            match(TipoToken.SEMICOLON);
        } else
            hayErrores = true;

    }

    //RETURN_STMT -> return RETURN_EXP_OPC ;
    private void RETURN_STMT() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.RETURN){
            match(TipoToken.RETURN);
            RETURN_EXP_OPC();
            match(TipoToken.SEMICOLON);
        } else
            hayErrores = true;

    }

    //RETURN_EXP_OPC -> EXPRESSION || EMPTY
    private void RETURN_EXP_OPC() {

        if(hayErrores)
            return;

        if (preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN)
            EXPRESSION();

    }

    //WHILE_STMT -> while ( EXPRESSION ) STATEMENT
    private void WHILE_STMT() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.WHILE) {
            match(TipoToken.WHILE);
            match(TipoToken.LEFT_PAREN);
            EXPRESSION();
            match(TipoToken.RIGHT_PAREN);
            STATEMENT();
        }
        else
            hayErrores = true;

    }

    //BLOCK -> { DECLARATION }
    private void BLOCK() {

        if(hayErrores)
            return;
        if(preanalisis.getTipo() == TipoToken.LEFT_BRACE){
            match(TipoToken.LEFT_BRACE);
            DECLARATION();
            match(TipoToken.RIGHT_BRACE);
        } else
            hayErrores = true;

    }

    /*Expresiones*/
    //EXPRESSION -> ASSIGNMENT
    private void EXPRESSION() {

        if(hayErrores)
            return;
        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            ASSIGNMENT();
        } else
            hayErrores = true;

    }

    //ASSIGNMENT -> LOGIC_OR ASSIGNMENT_OPC
    private void ASSIGNMENT() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            LOGIC_OR();
            ASSIGNMENT_OPC();
        } else
            hayErrores = true;


    }

    //ASSIGNMENT_OPC -> = EXPRESSION || EMPTY
    private void ASSIGNMENT_OPC() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.EQUAL){
            match(TipoToken.EQUAL);
            EXPRESSION();
        }

    }

    //LOGIC_OR -> LOGIC_AND LOGIC_OR_2
    private void LOGIC_OR() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            LOGIC_AND();
            LOGIC_OR_2();
        } else
            hayErrores = true;

    }

    //LOGIC_OR_2 -> or LOGIC_AND LOGIC_OR_2 || EMPTY
    private void LOGIC_OR_2() {

        if(hayErrores)
            return;
        if(preanalisis.getTipo() == TipoToken.OR){
            match(TipoToken.OR);
            LOGIC_AND();
            LOGIC_OR_2();
        }

    }

    //LOGIC_AND -> EQUALITY LOGIC_AND_2
    private void LOGIC_AND() {

        if(hayErrores)
            return;
        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            EQUALITY();
            LOGIC_AND_2();
        }
        else
            hayErrores = true;

    }

    //LOGIC_AND_2 -> and EQUALITY LOGIC_AND_2 || EMPTY
    private void LOGIC_AND_2() {

        if(hayErrores)
            return;
        if(preanalisis.getTipo() == TipoToken.AND){
            match(TipoToken.AND);
            EQUALITY();
            LOGIC_AND_2();
        }

    }

    //EQUALITY -> COMPARISON EQUALITY_2
    private void EQUALITY() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            COMPARISON();
            EQUALITY_2();
        } else
            hayErrores = true;

    }

    //EQUALITY_2 -> != COMPARISON EQUALITY_2 || == COMPARISON EQUALITY_2 || EMPTY
    private void EQUALITY_2() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.BANG_EQUAL){
            match(TipoToken.BANG_EQUAL);
            COMPARISON();
            EQUALITY_2();
        } else if(preanalisis.getTipo() == TipoToken.EQUAL_EQUAL){
            match(TipoToken.EQUAL_EQUAL);
            COMPARISON();
            EQUALITY_2();
        }

    }

    //COMPARISON -> TERM COMPARISON_2
    private void COMPARISON() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            TERM();
            COMPARISON_2();
        } else
            hayErrores = true;

    }

    //COMPARISON_2 -> > TERM COMPARISON_2 || >= TERM COMPARISON_2 || < TERM COMPARISON_2 || <= TERM COMPARISON_2 || EMPTY
    private void COMPARISON_2() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.GREATER){
            match(TipoToken.GREATER);
            TERM();
            COMPARISON_2();
        } else if(preanalisis.getTipo() == TipoToken.GREATER_EQUAL){
            match(TipoToken.GREATER_EQUAL);
            TERM();
            COMPARISON_2();
        } else if(preanalisis.getTipo() == TipoToken.LESS){
            match(TipoToken.LESS);
            TERM();
            COMPARISON_2();
        } else if(preanalisis.getTipo() == TipoToken.LESS_EQUAL){
            match(TipoToken.LESS_EQUAL);
            TERM();
            COMPARISON_2();
        }

    }

    //TERM -> FACTOR TERM_2
    private void TERM() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            FACTOR();
            TERM_2();
        } else
            hayErrores = true;

    }

    //TERM_2 -> - FACTOR TERM_2 || + FACTOR TERM_2 || EMPTY
    private void TERM_2() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.MINUS){
            match(TipoToken.MINUS);
            FACTOR();
            TERM_2();
        } else if(preanalisis.getTipo() == TipoToken.PLUS){
            match(TipoToken.PLUS);
            FACTOR();
            TERM_2();
        }

    }

    //FACTOR -> UNARY FACTOR_2
    private void FACTOR() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            UNARY();
            FACTOR_2();
        } else
            hayErrores = true;

    }

    //FACTOR_2 -> / UNARY FACTOR_2 || * UNARY FACTOR_2 || EMPTY
    private void FACTOR_2() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.SLASH){
            match(TipoToken.SLASH);
            UNARY();
            FACTOR_2();
        } else if (preanalisis.getTipo() == TipoToken.STAR) {
            match(TipoToken.STAR);
            UNARY();
            FACTOR_2();
        }

    }

    //UNARY -> ! UNARY || - UNARY || CALL
    private void UNARY() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.BANG){
            match(TipoToken.BANG);
            UNARY();
        } else if (preanalisis.getTipo() == TipoToken.MINUS) {
            match(TipoToken.MINUS);
            UNARY();
        } else if(preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            CALL();
        } else
            hayErrores = true;

    }

    //CALL -> PRIMARY CALL_2
    private void CALL() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            PRIMARY();
            CALL_2();
        } else
            hayErrores = true;

    }

    //CALL_2 -> ( ARGUMENTS_OPC ) CALL_2 || EMPTY
    private void CALL_2() {

        if(hayErrores)
            return;
        if(preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            match(TipoToken.LEFT_PAREN);
            ARGUMENTS_OPC();
            match(TipoToken.RIGHT_PAREN);
            CALL_2();
        }

    }

    //PRIMARY -> true || false || null || number || string || id || ( EXPRESSION )
    private void PRIMARY() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.TRUE){
            match(TipoToken.TRUE);
        } else if(preanalisis.getTipo() == TipoToken.FALSE){
            match(TipoToken.FALSE);
        } else if(preanalisis.getTipo() == TipoToken.NULL){
            match(TipoToken.NULL);
        } else if(preanalisis.getTipo() == TipoToken.NUMBER){
            match(TipoToken.NUMBER);
        } else if(preanalisis.getTipo() == TipoToken.STRING){
            match(TipoToken.STRING);
        } else if(preanalisis.getTipo() == TipoToken.IDENTIFIER){
            match(TipoToken.IDENTIFIER);
        } else if(preanalisis.getTipo() == TipoToken.LEFT_PAREN){
            match(TipoToken.LEFT_PAREN);
            EXPRESSION();
            match(TipoToken.RIGHT_PAREN);
        } else
            hayErrores = true;

    }


    /*Otros*/
    //FUNCTION -> id ( PARAMETERS_OPC ) BLOCK
    private void FUNCTION() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.IDENTIFIER) {
            match(TipoToken.IDENTIFIER);
            match(TipoToken.LEFT_PAREN);
            PARAMETERS_OPC();
            match(TipoToken.RIGHT_PAREN);
            BLOCK();
        } else
            hayErrores = true;

    }

    //FUNCTIONS -> FUN_DECL FUNCTIONS || EMPTY
    private void FUNCTIONS() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.FUN){
            FUN_DECL();
            FUNCTIONS();
        }

    }

    //PARAMETERS_OPC -> PARAMETERS || EMPTY
    private void PARAMETERS_OPC() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.IDENTIFIER){
            PARAMETERS();
        }

    }

    //PARAMETERS -> id PARAMETERS_2
    private void PARAMETERS() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.IDENTIFIER){
            match(TipoToken.IDENTIFIER);
            PARAMETERS_2();
        } else
            hayErrores = true;

    }

    //PARAMETERS_2 -> , id PARAMETERS_2 || EMPTY
    private void PARAMETERS_2() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.COMMA){
            match(TipoToken.COMMA);
            match(TipoToken.IDENTIFIER);
            PARAMETERS_2();
        }

    }

    //ARGUMENTS_OPC -> EXPRESSION ARGUMENTS || EMPTY
    private void ARGUMENTS_OPC() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.BANG || preanalisis.getTipo() == TipoToken.MINUS || preanalisis.getTipo() == TipoToken.FALSE || preanalisis.getTipo() == TipoToken.TRUE|| preanalisis.getTipo() == TipoToken.NULL
                || preanalisis.getTipo() == TipoToken.NUMBER || preanalisis.getTipo() == TipoToken.STRING || preanalisis.getTipo() == TipoToken.IDENTIFIER || preanalisis.getTipo() == TipoToken.LEFT_PAREN) {
            EXPRESSION();
            ARGUMENTS();
        }

    }

    //ARGUMENTS -> , EXPRESSION ARGUMENTS || EMPTY
    private void ARGUMENTS() {

        if(hayErrores)
            return;

        if(preanalisis.getTipo() == TipoToken.COMMA){
            match(TipoToken.COMMA);
            EXPRESSION();
            ARGUMENTS();
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