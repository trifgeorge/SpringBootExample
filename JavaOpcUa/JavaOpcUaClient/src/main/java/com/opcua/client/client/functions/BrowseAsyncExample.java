package com.opcua.client.client.functions;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.opcua.client.client.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.nodes.UaNode;
import org.eclipse.milo.opcua.stack.core.Identifiers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class BrowseAsyncExample implements Client {

    private static final Logger log = LogManager.getLogger(BrowseAsyncExample.class);

    @Override
    public void run(OpcUaClient client, CompletableFuture<OpcUaClient> future) throws Exception {
        client.connect().get();

        UaNode rootNode = client.getAddressSpace().getNode(Identifiers.RootFolder);
        Tree<UaNode> tree = new Tree<>(rootNode);

        browseRecursive(client, tree).get();

        traverse(tree, 0, (depth, n)->log.info((indent(depth) + n.getBrowseName().getName())));
    }



    private <T> void traverse(Tree<T> tree, int depth, BiConsumer<Integer, T> consumer) {

        consumer.accept(depth,tree.node);
        tree.children.forEach(child->traverse(child,depth + 1, consumer));
    }

    private CompletableFuture<Void> browseRecursive(OpcUaClient client, Tree<UaNode> tree) {

        return client.getAddressSpace().browseNodesAsync(tree.node).thenCompose(uaNodes -> {
            uaNodes.forEach(tree::addChild);
            Stream<CompletableFuture<Void>> futures = tree.children.stream().map(child -> browseRecursive(client, child));
            return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
        });
    }

    private static String indent(int depth) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            s.append("  ");
        }
        return s.toString();
    }

    private static class Tree<T> {

        List<Tree<T>> children = Lists.newCopyOnWriteArrayList();
        T node;

        Tree(T node) {
            this.node = node;
        }

        void addChild(T child) {
            children.add(new Tree<>(child));
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("children", children)
                    .add("node", node)
                    .toString();
        }
    }
}
