package br.com.alura.loja;

import br.com.alura.loja.modelo.Projeto;
import com.thoughtworks.xstream.XStream;
import junit.framework.Assert;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class ProjetoTeste {

    private HttpServer server;

    @Before
    public void startaServidor() {
        server = Servidor.inicializaServidor();
    }

    @After
    public void mataServidor() {
        server.stop();
    }

    @Test
    public void testaProjeto() {
        Client cliente = ClientBuilder.newClient();
        WebTarget target = cliente.target("http://localhost:8080");
        Projeto projeto = target.path("/projetos/1").request().get(Projeto.class);
        Assert.assertEquals("Minha loja", projeto.getNome());
    }
}
