package org.apache.wicket.fetch;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import java.time.Instant;

public class OnChangeFetchBehavior extends AbstractFetchBehavior {

    // TODO all this should really be resolved from the classname
    public static final JavaScriptResourceReference RESOURCE_REFERENCE = new JavaScriptResourceReference(OnChangeFetchBehavior.class, "js/OnChangeFetchBehavior.js");

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        response.render(new ESModuleContentHeaderItem(
                "import OnChangeFetchBehavior from \"/"+ RequestCycle.get().mapUrlFor(RESOURCE_REFERENCE, null).getPath()+"\";\n" +
                " new OnChangeFetchBehavior(\""+component.getMarkupId()+"\",\""+getCallbackUrl()+"\");"
                ,component.getMarkupId()+"-script"));
    }

    @Override
    public void onRequest() {
        // TODO look at AjaxRequestHandler, AjaxEnclosureListener and WebApplication
        RequestCycle requestCycle = RequestCycle.get();
        String encoding = getComponent().getApplication().getRequestCycleSettings().getResponseRequestEncoding();
        WebResponse response = (WebResponse) requestCycle.getResponse();
        response.disableCaching();
        response.setContentType("application/json; charset=" + encoding);
        response.write("{\"data\":\"value\",\"time\":"+ Instant.now().getEpochSecond()+"}");
        response.flush();
    }

}
