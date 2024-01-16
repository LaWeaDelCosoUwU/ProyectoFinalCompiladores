package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

import java.util.List;

public class StmtBlock extends Statement{
    private final List<Statement> statements;

    StmtBlock(List<Statement> statements) {
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public void execute(TablaSimbolos tablasimbolos) {

    }

}
