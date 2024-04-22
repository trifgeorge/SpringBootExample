package com.opcua.client;

import com.opcua.client.client.ClientRunner;
import com.opcua.client.client.functions.BrowseAsyncExample;
import com.opcua.client.client.functions.BrowseExample;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

//        log.info( " BROWSE EXAMPLE RUNNING ");
//        BrowseExample browseExample = new BrowseExample();
//        new ClientRunner(browseExample).run();
//        log.info( " BROWSE EXAMPLE DONE ");

        log.info( " BROWSE ASYNC EXAMPLE RUNNING ");
        BrowseAsyncExample browseAsyncExample = new BrowseAsyncExample();
        new ClientRunner(browseAsyncExample).run();
        log.info( " BROWSE ASYNC EXAMPLE DONE ");

    }
}
