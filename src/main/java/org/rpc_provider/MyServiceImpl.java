package org.rpc_provider;

import org.springframework.stereotype.Service;

@Service
public class MyServiceImpl implements MyService {

    @Override
    public String sayHello(String name) {
        return "hello";
    }
}
