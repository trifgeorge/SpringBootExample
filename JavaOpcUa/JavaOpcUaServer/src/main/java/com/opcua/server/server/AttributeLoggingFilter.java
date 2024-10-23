package com.opcua.server.server;

import org.eclipse.milo.opcua.sdk.server.nodes.filters.AttributeFilter;
import org.eclipse.milo.opcua.sdk.server.nodes.filters.AttributeFilterContext.GetAttributeContext;
import org.eclipse.milo.opcua.sdk.server.nodes.filters.AttributeFilterContext.SetAttributeContext;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.function.Predicate;

public class AttributeLoggingFilter implements AttributeFilter {

    private final Logger logger = LogManager.getLogger(AttributeLoggingFilter.class);

    private final Predicate<AttributeId> attributePredicate;

    public AttributeLoggingFilter() {
        this(attributeId -> true);
    }

    public AttributeLoggingFilter(Predicate<AttributeId> attributePredicate) {
        this.attributePredicate = attributePredicate;
    }

    @Override
    public Object getAttribute(GetAttributeContext ctx, AttributeId attributeId) {
        Object value = ctx.getAttribute(attributeId);

        // only log external reads
        if (attributePredicate.test(attributeId) && ctx.getSession().isPresent()) {
            logger.info(
                "get nodeId={} attributeId={} value={}",
                ctx.getNode().getNodeId(), attributeId, value
            );
        }

        return value;
    }

    @Override
    public void setAttribute(SetAttributeContext ctx, AttributeId attributeId, Object value) {
        // only log external writes
        if (attributePredicate.test(attributeId) && ctx.getSession().isPresent()) {
            logger.info(
                "set nodeId={} attributeId={} value={}",
                ctx.getNode().getNodeId(), attributeId, value
            );
        }

        ctx.setAttribute(attributeId, value);
    }

}
