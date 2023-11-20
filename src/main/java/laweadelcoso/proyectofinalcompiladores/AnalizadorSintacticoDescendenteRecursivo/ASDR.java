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


    @Override
    public boolean parse() {
        return false;
    }

}