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
        return tablasimbolos.obtener(name.getLexema());
    }

}