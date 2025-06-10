package enumeration;

public enum OpcaoMenu {
    SAIR(0, "0 - Sair", "Opção selecionada: Sair"),
    CADASTRAR_ALUNO(1, "1 - Cadastrar Aluno", "Opção selecionada: Cadastrar Aluno"),
    CADASTRAR_CURSO(2, "2 - Cadastrar Curso", "Opção selecionada: Cadastrar Curso"),
    REALIZAR_MATRICULA(3, "3 - Matricular aluno no curso", "Opção selecionada: Matricular aluno no Curso"),
    CANCELAR_MATRICULA(4, "4 - Cancelar matrícula", "Opção selecionada: Cancelar matrícula"),
    EXCLUIR_ALUNO(5, "5 - Excluir aluno", "Opção selecionada: Excluir aluno"),
    EXCLUIR_CURSO(6, "6 - Excluir curso", "Opção selecionada: Excluir curso"),
    LISTAR_ALUNOS(7, "7 - Listar alunos", "Opção selecionada: Listar alunos"),
    LISTAR_CURSOS(8, "8 - Listar todos os cursos e seus respectivos alunos", "Opção selecionada: Listar cursos"),
    OPCAO_INVALIDA(-1, "", "Opção inválida");

    private final int codigo;
    private final String nome;
    private final String descricao;

    OpcaoMenu(int codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public static OpcaoMenu obterPorCodigo(long codigo) {
        for (OpcaoMenu opcao : OpcaoMenu.values()) {
            if (opcao.getCodigo() == codigo) {
                return opcao;
            }
        }
        return OPCAO_INVALIDA;
    }
}