package org.apache.wicket.fetch;

import org.apache.wicket.Component;
import org.apache.wicket.IRequestListener;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.request.mapper.parameter.INamedParameters;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.lang.Args;

import java.util.List;

public abstract class AbstractFetchBehavior extends Behavior implements IRequestListener {

    public static final JavaScriptResourceReference RESOURCE_REFERENCE = new JavaScriptResourceReference(AbstractFetchBehavior.class, "js/AbstractFetchBehavior.js");

    /** the component that this handler is bound to. */
    private Component component;

    @Override
    public boolean rendersPage() {
        return false;
    }

    public CharSequence getCallbackUrl()
    {
        // TODO this one is a copypaste from ajax behavior
        Component component = getComponent();
        if (component == null)
        {
            throw new IllegalArgumentException(
                    "Behavior must be bound to a component to create the URL");
        }

        PageParameters parameters = new PageParameters();
        PageParameters pageParameters = component.getPage().getPageParameters();
        List<INamedParameters.NamedPair> allNamedInPath = pageParameters.getAllNamedByType(INamedParameters.Type.PATH);
        for (INamedParameters.NamedPair namedPair : allNamedInPath) {
            parameters.add(namedPair.getKey(), namedPair.getValue(), INamedParameters.Type.PATH);
        }
        return getComponent().urlForListener(this, parameters);
    }

    /**
     * Gets the component that this handler is bound to.
     *
     * @return the component that this handler is bound to
     */
    protected final Component getComponent()
    {
        return component;
    }

    /**
     * Bind this handler to the given component.
     *
     * @param hostComponent
     *            the component to bind to
     */
    @Override
    public final void bind(final Component hostComponent)
    {
        // TODO this one is a copypaste from ajax behavior
        Args.notNull(hostComponent, "hostComponent");

        if (component != null)
        {
            throw new IllegalStateException("this kind of handler cannot be attached to " +
                    "multiple components; it is already attached to component " + component +
                    ", but component " + hostComponent + " wants to be attached too");
        }

        component = hostComponent;

        // call the callback
        onBind();
    }

    protected void onBind() {
    }

    @Override
    public final void unbind(Component component)
    {
        // TODO this one is a copypaste from ajax behavior
        onUnbind();

        this.component = null;

        super.unbind(component);
    }

    /**
     * Called when the behavior is removed from its component. The bound host component is
     * still available through {@linkplain #getComponent()}. The relation to it will be removed
     * right after the finish of the execution of this method.
     */
    protected void onUnbind()
    {
    }

}
