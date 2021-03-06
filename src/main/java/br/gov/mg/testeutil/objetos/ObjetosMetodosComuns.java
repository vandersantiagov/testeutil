package br.gov.mg.testeutil.objetos;

import org.openqa.selenium.By;

public class ObjetosMetodosComuns {

	public static By linkSairSiareSICAF = By.linkText("Sair");
	public static By abaConultaSiareSICAF = By.linkText("Consulta");
	public static By textoTituloDaAbaConsulta = By.id("lblTituloTela");

	public static By menuAdiministracaoDeServico = By.linkText("Administração de Serviços");
	public static By subMenuHistoricoDeServicoPorProtocolo = By.linkText("Histórico de Serviço por Protocolo");

	public static By campoProtocolo = By.name("protocolo");

	public static By checkOcorrencia = By.name("ocorrencia");
	public static By checkTramitacao = By.name("tramitacao");
	public static By checkTransferencia = By.name("transferencia");
	public static By checkRegistroEntrega = By.name("chkBxRegistroEntrega");
	public static By checkSuspensao = By.name("suspensao");
	public static By checkPriorizacao = By.name("priorizacao");
	public static By checkRedirecionamento = By.name("redirecionamento");

	public static By comboPendencia = By.className("jquery-selectbox-currentItem");
	public static By selecionarOpcaoTodas = By
			.xpath(".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[2]/td[4]/div/div[2]/span[3]");

	public static By comandoPesquisar = By.name("Pesquisar");

	public static By checkProtocoloCaixaDeServico = By.name("ufw_selecao_registro");

	public static By campoNomeResponspavelPeloProtocolo = By
			.xpath(".//*[@id='containerConteudoPrincipal']/div/form/table[3]/tbody/tr[9]/td[2]/p");

	public static By linkControleDeAcesso = By.linkText("Controle de Acesso");
	public static By menuCadastroControleDeAcesso = By.linkText("Cadastros");
	public static By subMenuServidorControleDeAcesso = By.linkText("Servidores");

	public static By textoManutencaoDeServidoresDaSEF = By
			.xpath(".//*[@id='containerConteudoPrincipal']/div/form/table[1]/tbody/tr[1]/td");
	public static By campoNomeResponsavel = By.name("nome");

	public static By comandoPesquisaServidores = By.name("Pesquisar");

	public static By campoCPFResponsavelPeloProtocolo = By.id("funcionario.cpf.identificacaoFormatada");

	public static By campoProtocoloCaixaDeServico = By.id("");

	public static By linkExecutarProtocolo = By.name("lnkExecutar");
	public static By linkESuspenderProtocolo = By.name("lnkSuspender");

	// Link da tela de execução
	public static By linkDeferirExecucao = By.name("lnkDeferir");
	public static By linkIndeferirExecucao = By.name("lnkIndeferir");
	public static By linkDiligenciaExecucao = By.name("lnkDiligencia");
	public static By linkPendenciaExecucao = By.name("lnkPendencia");
	public static By linkOcorrenciaExecucao = By.name("lnkOcorrencia");
	public static By linkVerificarParecerExecucao = By.name("lnkVerificarParecer");

	public static By botaoDetalhesExecucao = By.name("detalhes");

	public static By comandoRetornadoExecucao = By.name("btnRetornar");

	// Tela de confirmação de Estabelicimento
	public static By campoVisivelNomeEstabelicimento = By.id("lbltipoRequerente.desRequerente");
	public static By comandoDesisiteConfirmacaoDeferimento = By.name("btnCancelar");
	public static By comandoConfirmarConfirmacaoDeferimento = By.name("lnkDeferir");

	public static String subPastaDiretorio = new String("Copiar_e_Colar_Arquivo_Texto");
	public static String diretorioPrincipal = new String("Z:\\ArtefatosWebdriver\\");
	
	public static By linkHomeSiare = By.linkText("Home");
	public static By textoValidarTituloHome = By.className("tit");
	
	public static By subMenuServico = By.linkText("Serviço");
	public static By subMenuPriorizacao = By.linkText("Priorização de Serviço");
	
	public static By selecionarProtocoloPesquisado = By.name("ufw_selecao_registro");
	
	public static By linkPriorizar = By.name("lnkPriorizar");
	
	public static By comandoPriorizar = By.name("btnPriorizar");
	
	public static By mensagemDeSucesso = By.id("lblMensagemSucesso");
	
	public static By protocoloCaixaDeServico = By.id("servico.protocolo");
	
	public static By linkSuspenderCaixaDeServico = By.name("lnkSuspender");
	
	public static By linkExecutarCaxiaDeServico = By.className("lnkExecutar");	
	
	public static By campoProtocoloPriorizacao = By.name("txtProtocolo");
	
	public static By campoProtocoloConsulta = By.name("protocolo");
	
	public static By comboMotivoDaSuspensao = By.xpath(".//*[@id='containerConteudoPrincipal']/div/form/table/tbody/tr/td/table[2]/tbody/tr[6]/td[2]/div/input");
	public static By selecionarOpcaoTipoDeSuspensao = By.xpath(".//*[@id='containerConteudoPrincipal']/div/form/table/tbody/tr/td/table[2]/tbody/tr[6]/td[2]/div/div[2]/span[4]");
	public static By comandoConfirmarSuspensaoProtocolo = By.name("Confirmar");
	
	/**
	 * Variáveis do fluxo Resolução de Pendências
	 * @author jacqueline.lucas
	 */
	
    public static By abaHomeSiareSICAF = By.linkText("Home");
    public static By menuAtendimento = By.linkText("Atendimento");
    public static By submenuEntregadeDocumentosResolucaodePendencias = By.linkText("Entrega de Documentos/Resolução de Pendências");
    public static By textoTituloTelaEntregadeDocumentosResolucaodePendencias = By.className("tit");
    public static By comandoPesquisarPendencias = By.name("pesquisar");
    public static By selecaoRegistro =  By.name("ufw_selecao_registro");
    public static By linkResolvePendencia = By.name("lkResolvePendencia");
    public static By selecaoRegistroPendenciasdeDocumentacao = By.name("Selecao_Todos");
    public static By comandoRegistrarEntrega = By.name("Registrar_Entrega");
    public static By campoObservacoes = By.name("txtDesRelato");
    public static By comandoConfirmarEntregaDocumento = By.name("ActConfirmar");
    public static By comandoResolver = By.name("Resolver");
    public static By campoRelato = By.name("DescricaoRelato");
    
	/**
	 * Variáveis do fluxo Presumir DAE
	 * @author Fábio Heller
	 */
    public static By  menuDocumentodeArrecadacaoDAE = By.linkText("Documento de Arrecadação DAE");
    public static By  subMenuManutencaoDAE = By.linkText("Manutenção de DAE");
    public static By  campoNumeroDAE = By.name("nossoNumero");
    public static By  campoPeriodoDeEmissaoInicial = By.name("dtIniEmissoa");
    public static By  campoPeriodoDeEmissaoFinal = By.name("dtFimEmissao");
    public static By  linkPresumirQuitacaoDAE = By.name("linkPresumirQuitacao");
    //public static By  campoValorTotalDoDAE = By.xpath(".//*[@id='containerConteudoPrincipal']/div/form/table[3]/tbody/tr[3]/td[5]");
    public static By  campoValorTotalDoDAE = By.id("total");
    public static By  campoValorDoDAE = By.name("Valor");
    public static By campoBanco = By.className("jquery-selectbox-currentItem");
    public static By campoSelecaoBanco = By.xpath(".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[6]/td[2]/div/div[2]/span[2]");
    public static By campoAgencia = By.name("Agencia");
    public static By campoDataPagamento = By.name("DataPagamento");
    public static By campoNSU = By.name("NSU");

    /**
     * PRIORIZAR PROTOCOLO MÉTODO priorizoarProtocolo
	 * @author Antônio Bernardo
     */
    public static By checkEnviarAnalistaResponsavelSim = By.name("enviarAnalistaResp");
    public static By campoAnalistaResponsavel = By.id("usuario.nome");
    public static By opcaoAnalistaResp = By.xpath("html/body/div[3]/div[2]/div/div[2]/div/form/table[3]/tbody/tr[5]/td[2]/div/div[2]/span");
    public static By campoAnalistaResp = By.xpath("html/body/div[3]/div[2]/div/div[2]/div/form/table[3]/tbody/tr[5]/td[2]/div/div[1]");

    /**
	 * Tela do captcha
	 */

    public static By menssagemCapcha = By.id("messagem");
    public static By excecaoMenssage = By.id("message");
    public static By elementoCapcha = By.xpath("//*[@id='recaptcha-anchor']/div[5]");
    
    /*
     * Contador de Telas
     */
    public static int contadorTelas;

	/**
	 * Tela de Login Intranet do SIARE (SICAF)
	 */	
	public static By validacaoTituloCorretoLogin = By.id("boxFooter");
	public static By cpfField = By.name("login");
	public static By senhaField = By.name("senhaAtual");
	public static By confirmarFieldLogin = By.name("Confirmar");
	
	public static By escreverArquivo = By.id("buscaRapida");
	
	/**
	 * Tela de Login Internet do SIARE (SOL)
	 */	
		
	public static By selecionarTipoDeUsuario = By.name("cmbDominio");
	
	public static By selecionarIE = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[2]");
	public static By selecionarProtocolo = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[3]");
	public static By selecionarCPF = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[4]");
	public static By selecionarCNPJ = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[5]");
	public static By selecionarProdutorRural= By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[6]");
	public static By selecionarDespachanteCPF = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[7]");
	public static By selecionarDespachanteCNPJ = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[8]");
	public static By selecionarRecintoAlfandegadoPessoaJuridica = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[9]");
	public static By selecionarCERM_TFRMPessoaFisica = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[10]");
	public static By selecionarCERM_TFRMPessoaJuridica = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[11]");
	public static By selecionarVAFEspecial = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[12]");
	public static By selecionarContribuinteInterestadual = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[13]");
	
	public static By preencherCampoCPF = By.name("login");
	
	public static By preencherCampoIdentificacao = By.name("dominio");//Exibido somente após selecionar o tipo de usuário
	
	public static By preencherSenhaAtual = By.name("senhaAtual");
	
	public static By comandoConfirmarLoginInternet = By.name("Confirmar");
	
	public static By botaoCaixaMensagem = By.name("btnCaixaMensagem");
    public static By campoPesquisarAssunto = By.name("txtPesquisarAssunto");
    public static By campoPaginacao = By.name("ufw_posicionador_gridMensagens");
    public static By botaoIr = By.name("ufw_link_ir_gridMensagens");
    public static By campoMensage = By.xpath("/html/body/div[3]/div[2]/div/div[3]/div/form/div[2]/div[2]/table[2]/tbody/tr/td");  
    // Pendencia de Documentação
    public static By campoDocumento = By.id("desMotivo");

	
}
