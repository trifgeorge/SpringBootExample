package com.opcua.client.client.functions;

import com.opcua.client.client.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.nodes.UaNode;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.structured.BrowseResult;
import org.eclipse.milo.opcua.stack.core.types.structured.ReferenceDescription;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BrowseNodeExample implements Client {

    private static final Logger log = LogManager.getLogger(BrowseNodeExample.class);

    @Override
    public void run(OpcUaClient client, CompletableFuture<OpcUaClient> future) throws Exception {

        client.connect().get();
//        browseNodeRecursive("", client, Identifiers.RootFolder);
        browseNode("", client, Identifiers.RootFolder);

        future.complete(client);
    }


    private void browseNode(String indent, OpcUaClient client, NodeId browseRoot) throws UaException {
        Deque<NodeId> stack = new ArrayDeque<>();
        stack.push(browseRoot);

        while (!stack.isEmpty()) {
            NodeId nodeId = stack.pop();

            // Browse the current node
            List<ReferenceDescription> references = client.getAddressSpace().browse(nodeId);

            for (ReferenceDescription ref : references) {
                // Print node information
                log.info("{} Node={}", indent, ref.getBrowseName().getName());

                // Add child nodes to the stack for further processing
                stack.push(ref.getNodeId().toNodeId(client.getNamespaceTable()).orElse(null));
            }
        }
    }
    private void browseNodeRecursive(String indent, OpcUaClient client, NodeId browseRoot) throws UaException {

        List<? extends UaNode> nodes = client.getAddressSpace().browseNodes(browseRoot);

        for (UaNode node : nodes) {
            log.info(" {} Node={}", indent, node.getBrowseName().getName());
            browseNodeRecursive(indent + "--", client, node.getNodeId());
        }
    }
}
