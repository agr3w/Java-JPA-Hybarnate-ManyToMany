package enumeration;

public enum OpcaoMenu {
    SAIR(0, "Saindo do sistema..."),
    CADASTRAR(1, "Opção selecionada: Cadastrar Aluno"),
    CADASTRAR_CURSO(2, "Opção selecionada: Cadastrar Curso"),
    MATRICULAR(3, "Opção selecionada: Matricular Aluno"),
    CANCELAR_MATRICULA(4, "Opção selecionada: Cancelar Matrícula"),
    EXCLUIR_ALUNO(5, "Opção selecionada: Excluir Aluno"),
    EXCLUIR_CURSO(6, "Opção selecionada: Excluir Curso"),
    LISTAR_ALUNOS(7, "Opção selecionada: Listar Alunos"),
    LISTAR_CURSOS(8, "Opção selecionada: Listar Cursos e Alunos"),
    INVALIDA(-1, "Opção inválida");

    private final int codigo;
    private final String descricao;

    OpcaoMenu(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static OpcaoMenu obterPorCodigo(int codigo) {
        for (OpcaoMenu opcao : OpcaoMenu.values()) {
            if (opcao.getCodigo() == codigo) {
                return opcao;
            }
        }
        return INVALIDA;
    }
}


/*
public enum OpcaoMenu {
	SAIR(0, "Opção selecionada: Cadastrar"),
	CADASTRAR(1, "Opção selecionada: Cadastrar"),
	EDITAR(2, "Opção selecionada: Editar"),
	REMOVER(3, "Opção selecionada: Remover"),
	PESQUISAR_TODOS(4, "Opção selecionada: Pesquisar todos"),
	PESQUISAR_PELO_CPF(5, "Opção selecionada: Pesquisar pelo CPF"),
	PESQUISAR_PELAS_INICIAIS(6, "Opção selecionada: Pesquisar pelas iniciais"),
	INVALIDA(-1, "Opção inválida");
	
	private final int codigo;
	private final String descricao;

	// Construtor do enum
	OpcaoMenu(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

    // Método para obter o código da opção
    public int getCodigo() {
        return codigo;
    }

    // Método para obter a descrição da opção
    public String getDescricao() {
        return descricao;
    }

    // Método estático para buscar a opção pelo código
    public static OpcaoMenu obterPorCodigo(int codigo) {
        for (OpcaoMenu opcao : OpcaoMenu.values()) {
            if (opcao.getCodigo() == codigo) {
                return opcao;
            }
        }
        return INVALIDA;
    }  
	}
}

*/
