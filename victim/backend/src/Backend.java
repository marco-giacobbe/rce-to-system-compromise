import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class Backend {
    private static final Logger logger = LogManager.getLogger(Backend.class);

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new DefaultHandler());
        server.createContext("/submit", new SubmitHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Victim HTTP server in ascolto su porta 8080...");
    }

	public static String sanitizeLog(String input) {
		if (input == null) return "";
		//return input.replaceAll("(?i)jndi", "[REMOVED]");
		return input;
	}


	// Handler for index
	static class DefaultHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
		    String path = exchange.getRequestURI().getPath();
		    logger.info("Request path: " + Backend.sanitizeLog(path));

		    if (path.equals("/")) {
		        // Return index.html
		        byte[] html = Files.readAllBytes(Paths.get("./resources/index.html"));
		        exchange.sendResponseHeaders(200, html.length);
		        OutputStream os = exchange.getResponseBody();
		        os.write(html);
		        os.close();
		    } else {
		        // Return 404
		        String response = "404 Not Found";
		        exchange.sendResponseHeaders(404, response.length());
		        OutputStream os = exchange.getResponseBody();
		        os.write(response.getBytes());
		        os.close();
		    }
		}
	}


    // Handler for the form
    static class SubmitHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();

                String[] params = URLDecoder.decode(formData, StandardCharsets.UTF_8.name()).split("&");
				int item = -1;
				String email = null;

				for (String param : params) {
					String[] keyValue = param.split("=", 2);
					if (keyValue.length == 2) {
						if (keyValue[0].equals("item")) {
							item = Integer.parseInt(keyValue[1]);
						} else if (keyValue[0].equals("email")) {
							email = keyValue[1];
						}
					}
				}

                logger.info(email + " has purchased item: " + item);
                String response = "Thank you for purchasing!";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }
}

