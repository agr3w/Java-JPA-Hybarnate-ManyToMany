package enumeration;

public enum OpcaoMenu {
    CADASTRAR(1, "Cadastrar aluno"),
    EDITAR(2, "Cadastrar curso"),
    REMOVER(5, "Excluir aluno"),
    LISTAR_ALUNOS(7, "Listar alunos"),
    PESQUISAR_PELO_CPF(3, "Matricular aluno"),
    PESQUISAR_PELAS_INICIAIS(4, "Cancelar matrícula"),
    LISTAR_CURSOS(8, "Listar cursos"),
    EXCLUIR_CURSO(6, "Excluir curso"),
    SAIR(0, "Sair"),
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
