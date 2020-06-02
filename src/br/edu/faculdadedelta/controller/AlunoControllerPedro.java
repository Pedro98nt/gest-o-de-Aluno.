package br.edu.faculdadedelta.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.edu.faculdadedelta.dao.AlunoDaoPedro;
import br.edu.faculdadedelta.modelo.AlunoPedro;
import br.edu.faculdadedelta.modelo.GrauInstrucaoPedro;

@ManagedBean
@SessionScoped
public class AlunoControllerPedro {

	private AlunoPedro aluno = new AlunoPedro();
	private AlunoDaoPedro dao = new AlunoDaoPedro();
	private GrauInstrucaoPedro grauSelecionado = new GrauInstrucaoPedro();
	
	public AlunoPedro getAluno() {
		return aluno;
	}
	public void setAluno(AlunoPedro aluno) {
		this.aluno = aluno;
	}
	public GrauInstrucaoPedro getGrauSelecionado() {
		return grauSelecionado;
	}
	public void setGrauSelecionado(GrauInstrucaoPedro grauSelecionado) {
		this.grauSelecionado = grauSelecionado;
	}
	
	public void limparCampos() {
		aluno = new AlunoPedro();
	}
	
	private void exibirMensagem(String mensagem) {
		FacesMessage msg = new FacesMessage(mensagem);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String salvar() {
		try {
			if(aluno.getId() == null) {
				aluno.setGrauInstitucional(grauSelecionado);
				dao.incluir(aluno);
				limparCampos();
				exibirMensagem("Inclusao realizada com sucesso.");
			} else {
				aluno.setGrauInstitucional(grauSelecionado);
				dao.alterar(aluno);
				limparCampos();
				exibirMensagem("Alteracao realizada com sucesso.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			exibirMensagem("Erro ao realizar a operação, tente novamente mais tarde! " + e.getMessage());
		}
		return "CadastroAluno.xhtml";
	}

	public String editar() {
		aluno.setGrauInstitucional(grauSelecionado);
		return "CadastroAluno.xhtml";
	}
	
	public String excluir() {
		try {
			aluno.setGrauInstitucional(grauSelecionado);
				dao.excluir(aluno);
				limparCampos();
				exibirMensagem("Exclusao realizada com sucesso.");
			} catch (Exception e) {
				e.printStackTrace();
				exibirMensagem("Erro ao realizar a operação, tente novamente mais tarde! " + e.getMessage());
			}
			return "ListaAluno.xhtml";
	}
	
	public List<AlunoPedro> getListar(){
		List<AlunoPedro> listaRetorno = new ArrayList<>();
		
		try {
		listaRetorno = dao.listar();
		} catch (Exception e) {
			e.printStackTrace();
			exibirMensagem("Erro ao realizar a operação, tente novamente mais tarde! " + e.getMessage());
		}
		
		return listaRetorno;
	}
}
