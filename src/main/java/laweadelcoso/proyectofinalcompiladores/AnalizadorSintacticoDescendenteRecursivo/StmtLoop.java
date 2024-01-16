package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

public class StmtLoop extends Statement {
    final Expression condition;
    final Statement body;

    StmtLoop(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getBody() {
        return body;
    }

    @Override
    public void execute(TablaSimbolos tablasimbolos) {

        if(condition.resolver(tablasimbolos) instanceof Boolean){
            while((Boolean) condition.resolver(tablasimbolos)){
                body.execute(tablasimbolos);
                condition.resolver(tablasimbolos);
            }
        }else
            throw new RuntimeException("La condición debe ser booleana");

    }

}
