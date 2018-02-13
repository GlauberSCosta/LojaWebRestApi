package br.com.alura.loja.resource;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.ProjetoDao;
import br.com.alura.loja.modelo.Projeto;

@Path("projetos")
public class ProjetoResource {
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id) {
		 Projeto projeto = new ProjetoDao().busca(id);
		return projeto.toXML();
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adciona(String conteudo) {
		Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
		new ProjetoDao().adiciona(projeto);
		
		URI uri = null;
		try {
			uri = new URI("/projetos/" + projeto.getId());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseBuilder builder ;
		builder = Response.created(uri);
		return  builder.build() ;
	}
	@Path("{id}")
	@DELETE
	public Response deletar(@PathParam("id") long idProjeto) {
		new ProjetoDao().remove(idProjeto);
		
		return Response.ok().build();
	}
}
