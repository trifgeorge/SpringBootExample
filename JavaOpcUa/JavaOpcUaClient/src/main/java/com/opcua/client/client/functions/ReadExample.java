package com.opcua.client.client.functions;

import com.google.common.collect.ImmutableList;
import com.opcua.client.client.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.nodes.UaVariableNode;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.ServerState;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ReadExample implements Client {

    private static final Logger log = LogManager.getLogger(ReadExample.class);

    @Override
    public void run(OpcUaClient client, CompletableFuture<OpcUaClient> future) throws Exception {

        client.connect().get();

        // synchronous read request via VariableNode
        UaVariableNode node = client.getAddressSpace().getVariableNode(Identifiers.Server_ServerStatus_StartTime);
        DataValue value = node.readValue();
        log.info(" StartTime={} ",value.getValue().getValue());

        // asynchronous read request
        CompletableFuture<List<DataValue>> result = readServerStateAndTime(client);
        result.thenAccept(values->{
            DataValue v0 = values.get(0);
            DataValue v1 = values.get(1);

            log.info(" State={} ", ServerState.from((Integer) v0.getValue().getValue()));
            log.info(" CurrentTime={} ", v1.getValue().getValue());

            future.complete(client);
        });
    }

    private CompletableFuture<List<DataValue>> readServerStateAndTime(OpcUaClient client) {
        List<NodeId> nodeIds = ImmutableList.of(Identifiers.Server_ServerStatus_State, Identifiers.Server_ServerStatus_CurrentTime);
        return client.readValues(0.0, TimestampsToReturn.Both, nodeIds);
    }
}
