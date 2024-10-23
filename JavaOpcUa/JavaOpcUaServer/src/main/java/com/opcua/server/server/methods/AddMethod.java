package com.opcua.server.server.methods;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.milo.opcua.sdk.core.ValueRanks;
import org.eclipse.milo.opcua.sdk.server.api.methods.AbstractMethodInvocationHandler;
import org.eclipse.milo.opcua.sdk.server.nodes.UaMethodNode;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.structured.Argument;

public class AddMethod extends AbstractMethodInvocationHandler {

    private final Logger logger = LogManager.getLogger(AddMethod.class);

    public static final Argument first = new Argument("first element", Identifiers.Int32, ValueRanks.Scalar, null, new LocalizedText(" The first element to add "));
    public static final Argument second = new Argument("second element", Identifiers.Int32, ValueRanks.Scalar, null, new LocalizedText(" The second element to add "));
    public static final Argument result = new Argument("result", Identifiers.Int32, ValueRanks.Scalar, null, new LocalizedText(" The result of 'first' plus 'second'"));
    /**
     * @param node the {@link UaMethodNode} this handler will be installed on.
     */
    public AddMethod(UaMethodNode node) {
        super(node);
    }

    @Override
    public Argument[] getInputArguments() {
        return new Argument[]{first,second};
    }

    @Override
    public Argument[] getOutputArguments() {
        return new Argument[]{result};
    }

    @Override
    protected Variant[] invoke(InvocationContext invocationContext, Variant[] inputValues) {
        int first = (int) inputValues[0].getValue();
        int second = (int) inputValues[1].getValue();
        int result = first + second;
        logger.info(" Received parameters {} and {} for node {}", inputValues[0], inputValues[1], invocationContext.getObjectId().getIdentifier());
        return new Variant[]{new Variant(result)};
    }
}
