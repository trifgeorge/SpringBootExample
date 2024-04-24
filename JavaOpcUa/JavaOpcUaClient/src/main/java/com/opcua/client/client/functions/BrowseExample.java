package com.opcua.client.client.functions;

import com.opcua.client.client.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.enumerated.BrowseDirection;
import org.eclipse.milo.opcua.stack.core.types.enumerated.BrowseResultMask;
import org.eclipse.milo.opcua.stack.core.types.enumerated.NodeClass;
import org.eclipse.milo.opcua.stack.core.types.structured.BrowseDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.BrowseResult;
import org.eclipse.milo.opcua.stack.core.types.structured.ReferenceDescription;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class BrowseExample implements Client {

    private static final Logger log = LogManager.getLogger(BrowseExample.class);

    @Override
    public void run(OpcUaClient client, CompletableFuture<OpcUaClient> future) throws Exception {
        client.connect().get();
        browseNode("",client, Identifiers.RootFolder);

        future.complete(client);
    }

    private void browseNode(String indent, OpcUaClient client, NodeId browseRoot){
        BrowseDescription browse = new BrowseDescription(browseRoot, BrowseDirection.Forward, Identifiers.References, true, Unsigned.uint(NodeClass.Object.getValue()|NodeClass.Variable.getValue()),Unsigned.uint(BrowseResultMask.All.getValue()));

        try{
            BrowseResult browseResult = client.browse(browse).get();
            List<ReferenceDescription> references = Arrays.asList(browseResult.getReferences());
            for(ReferenceDescription rd: references){
                log.info("{} Node={} Identifier={}", indent, rd.getBrowseName().getName(), rd.getNodeId().getIdentifier());

                rd.getNodeId().toNodeId(client.getNamespaceTable())
                        .ifPresent(nodeId -> browseNode(indent + "--", client, nodeId));
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
