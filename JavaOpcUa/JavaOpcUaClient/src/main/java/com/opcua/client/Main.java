package com.opcua.client;

import com.opcua.client.client.ClientRunner;
import com.opcua.client.client.functions.BrowseExample;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        log.info( " BROWSE EXAMPLE RUNNING ");
        BrowseExample example = new BrowseExample();
        new ClientRunner(example).run();
        log.info( " BROWSE EXAMPLE DONE ");

    }
}
