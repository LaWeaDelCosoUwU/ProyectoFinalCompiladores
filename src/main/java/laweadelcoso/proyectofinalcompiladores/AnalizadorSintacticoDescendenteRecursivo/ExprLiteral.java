package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

class ExprLiteral extends Expression {
    final Object value;

    ExprLiteral(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public Object resolver(TablaSimbolos tablasimbolos) {
        return value;
    }

}
