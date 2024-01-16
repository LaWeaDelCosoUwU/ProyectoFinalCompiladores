package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

public class StmtIf extends Statement {
    final Expression condition;
    final Statement thenBranch;
    final Statement elseBranch;

    StmtIf(Expression condition, Statement thenBranch, Statement elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getThenBranch() {
        return thenBranch;
    }

    public Statement getElseBranch() {
        return elseBranch;
    }

    @Override
    public void execute(TablaSimbolos tablasimbolos) {

        Object conditionResuelta = condition.resolver(tablasimbolos);

        if(conditionResuelta instanceof Boolean){
            if((Boolean) conditionResuelta){
                thenBranch.execute(tablasimbolos);
            }else if(elseBranch != null){
                elseBranch.execute(tablasimbolos);
            }
        }else
            throw new RuntimeException("La condición debe ser booleana");

    }

}
