package org.rpc_provider;

import org.rpc_protocol.Server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.rpc_protocol", "org.rpc_provider"})
public class Main {
    @Autowired
    public MyService myService;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Main.class, args);
        new Server("127.0.0.1", 8088).start();
    }
}
