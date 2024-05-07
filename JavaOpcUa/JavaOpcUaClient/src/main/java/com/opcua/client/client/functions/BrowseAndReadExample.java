package com.opcua.client.client.functions;

import com.opcua.client.client.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.nodes.UaVariableNode;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.structured.ReferenceDescription;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BrowseAndReadExample implements Client {

    private static final Logger log = LogManager.getLogger(BrowseAndReadExample.class);
    @Override
    public void run(OpcUaClient client, CompletableFuture<OpcUaClient> future) throws Exception {
        client.connect().get();
        // ns=0 nodes defined by the OPC UA standard itself
        // ns=1 ?
        // ns=2 -> nodes defined by companion specifications or vendor-specific extensions.
        // also works like this
         NodeId dynamicDouble = NodeId.parse("ns=2;s=HelloWorld/Dynamic/Double");
//        NodeId dynamicDouble = browseNode(client, Identifiers.ObjectsFolder);

        UaVariableNode node = client.getAddressSpace().getVariableNode(dynamicDouble);
        DataValue value = node.readValue();
        log.info("DynamicDouble= {} ",value.getValue().getValue());
        future.complete(client);
    }

    private NodeId browseNode(OpcUaClient client, NodeId browseRoot) throws UaException {
        Deque<NodeId> stack = new ArrayDeque<>();
        stack.push(browseRoot);
        while (!stack.isEmpty()) {
            NodeId nodeId = stack.pop();

            // Browse the current node
            List<ReferenceDescription> references = client.getAddressSpace().browse(nodeId);

            for (ReferenceDescription ref : references) {
                // Print node information
                log.info("Node={} Identifier={}", ref.getBrowseName().getName(), ref.getNodeId().getIdentifier());
                if(ref.getNodeId().getIdentifier().toString().equals("HelloWorld/Dynamic/Double")){
                    return ref.getNodeId().toNodeId(client.getNamespaceTable()).orElse(NodeId.NULL_NUMERIC);
                }
                // Add child nodes to the stack for further processing
                stack.push(ref.getNodeId().toNodeId(client.getNamespaceTable()).orElse(null));
            }
        }
        return NodeId.NULL_NUMERIC;
    }
}
