package br.com.casadocodigo.loja.beans;

import java.io.IOException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import org.hibernate.jpa.criteria.expression.function.SubstringFunction;

import br.com.casadocodigo.loja.daos.AutorDao;
import br.com.casadocodigo.loja.daos.LivroDao;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Autor;
import br.com.casadocodigo.loja.models.Livro;

@Named
@RequestScoped
public class AdminLivrosBean {
	
	private Livro livro = new Livro();
	
	@Inject
	private LivroDao dao;
	@Inject
	private AutorDao autorDao;
	@Inject
	private FacesContext context; 
	
	
	private Part capaLivro;
	
	@Transactional
	public String salvar() throws IOException {
		FileSaver fileSaver = new FileSaver();
		String nomeArquivo = "capaLivro_"+livro.getTitulo().substring(0,10);
		String capaPath = fileSaver.write(capaLivro, "livros", nomeArquivo);
		livro.setCapaPath(capaPath);
		dao.salvar(livro);
		
//		capaLivro.write(File.separator +"home"+File.separator+"noteblack"+File.separator+File.separator +"livros"+File.separator+capaLivro.getSubmittedFileName());
//		capaLivro.write(File.separator +"casadocodigo"+File.separator+"livros"+File.separator+capaLivro.getSubmittedFileName());
//		capaLivro.write(File.separator+capaLivro.getSubmittedFileName());
		
		
		
		context.getExternalContext()
			.getFlash().setKeepMessages(true);
		context
			.addMessage(null, new FacesMessage("Livro cadastrado com sucesso!"));
		
		return "/livros/lista?faces-redirect=true";
	}

	public List<Autor> getAutores() {
		return autorDao.listar();
	}
	
	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Part getCapaLivro() {
		return capaLivro;
	}

	public void setCapaLivro(Part capaLivro) {
		this.capaLivro = capaLivro;
	}

}
