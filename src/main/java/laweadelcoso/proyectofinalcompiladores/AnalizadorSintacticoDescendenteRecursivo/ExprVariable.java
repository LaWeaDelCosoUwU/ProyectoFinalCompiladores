package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

import laweadelcoso.proyectofinalcompiladores.AnalizadorLexico.Token;

class ExprVariable extends Expression {
    final Token name;

    ExprVariable(Token name) {
        this.name = name;
    }

    public Token getName() {
        return name;
    }

    @Override
    public Object resolver(TablaSimbolos tablasimbolos) {
        System.out.println(name.getLexema() +""+tablasimbolos.obtener(name.getLexema()));
        return tablasimbolos.obtener(name.getLexema());
    }

}