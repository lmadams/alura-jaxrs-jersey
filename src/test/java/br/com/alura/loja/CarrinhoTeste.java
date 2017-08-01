package br.com.alura.loja;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import com.thoughtworks.xstream.XStream;
import junit.framework.Assert;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CarrinhoTeste {

    private HttpServer server;
    private Client cliente;
    private WebTarget target;

    @Before
    public void startaServidor() {
        server = Servidor.inicializaServidor();

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(new LoggingFilter());

        this.cliente = ClientBuilder.newClient(clientConfig);
        this.target = cliente.target("http://localhost:8080");
    }

    @After
    public void mataServidor() {
        server.stop();
    }

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
        Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());

    }

    @Test
    public void testaInsertDeCarrinho() {
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");

        Entity<Carrinho> entity = Entity.entity(carrinho, MediaType.APPLICATION_XML);

        Response response = target.path("/carrinhos").request().post(entity);
        Assert.assertEquals(201, response.getStatus());

        String location = response.getHeaderString("Location");
        Carrinho carrinhoRetorno = cliente.target(location).request().get(Carrinho.class);
        Assert.assertEquals("Tablet", carrinhoRetorno.getProdutos().get(0).getNome());
    }
}
