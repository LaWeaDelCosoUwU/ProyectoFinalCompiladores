package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

import laweadelcoso.proyectofinalcompiladores.AnalizadorLexico.Token;

public class ExprSet extends Expression{
    final Expression object;
    final Token name;
    final Expression value;

    ExprSet(Expression object, Token name, Expression value) {
        this.object = object;
        this.name = name;
        this.value = value;
    }
}
