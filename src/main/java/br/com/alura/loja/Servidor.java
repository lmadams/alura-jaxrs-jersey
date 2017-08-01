package br.com.alura.loja;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Servidor {

    public static void main(String[] args) throws IOException {
        HttpServer server = inicializaServidor();
        System.out.println("Servidor rodando na porta 8080");
        System.in.read();
        server.stop();
    }

    public static HttpServer inicializaServidor() {
        ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
        URI uri = URI.create("http://localhost:8080/");
        return GrizzlyHttpServerFactory.createHttpServer(uri, config);
    }
}
