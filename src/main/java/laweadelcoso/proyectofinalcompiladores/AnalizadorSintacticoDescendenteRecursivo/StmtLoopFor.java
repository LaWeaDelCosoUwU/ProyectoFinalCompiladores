package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

public class StmtLoopFor extends Statement{

    private final Statement init, body;
    private final Expression con;
    private final Expression inc;


    public StmtLoopFor(Statement init, Expression con, Expression inc,  Statement body){

        this.init = init;
        this.con = con;
        this.inc = inc;
        this.body = body;

    }

}
