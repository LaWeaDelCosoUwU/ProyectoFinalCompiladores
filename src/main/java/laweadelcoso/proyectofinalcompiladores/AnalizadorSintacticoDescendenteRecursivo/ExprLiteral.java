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
        if(value instanceof Number){
            return ((Number)value).doubleValue();
        }else if (value instanceof Boolean){
            return (Boolean) value;
        }else if(value == null){
            return null;
        }else{
            throw new RuntimeException();
        }
    }

}
