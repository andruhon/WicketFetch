package org.apache.wicket.fetch;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.string.Strings;

public abstract class OnChangeFetchAjaxBehavior extends AjaxFormComponentUpdatingBehavior {

    // TODO all this should really be resolved from the classname
    public static final JavaScriptResourceReference RESOURCE_REFERENCE = new JavaScriptResourceReference(OnChangeFetchAjaxBehavior.class, "js/OnChangeFetchAjaxBehavior.js");

    public OnChangeFetchAjaxBehavior() {
        super("change");
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        response.render(new ESModuleContentHeaderItem(
                "import OnChangeFetchAjaxBehavior from \"/"+ RequestCycle.get().mapUrlFor(RESOURCE_REFERENCE, null).getPath()+"\";\n" +
                        "let behavior = new OnChangeFetchAjaxBehavior(\""+component.getMarkupId()+"\",\""+getCallbackUrl()+"\");\n"
                        // +"behavior.registerCallback('OnChangeFetchAjaxBehavior.doMagic', OnChangeFetchAjaxBehavior.doMagic);"
                ,component.getMarkupId()+"-script")
        );
        RequestCycle requestCycle = component.getRequestCycle();
        Url baseUrl = requestCycle.getUrlRenderer().getBaseUrl();
        CharSequence ajaxBaseUrl = Strings.escapeMarkup(baseUrl.toString());
        response.render(JavaScriptHeaderItem.forScript("WicketFetchBaseUrl=\"" + ajaxBaseUrl
                + "\";", "wicket-fetch-base-url"));
    }

}
