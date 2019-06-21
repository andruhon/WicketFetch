package org.apache.wicket.fetch;

import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.string.Strings;

public class ESModuleContentHeaderItem extends JavaScriptContentHeaderItem {

    /**
     * Creates a new {@code JavaScriptContentHeaderItem}.
     *
     * @param javaScript javascript content to be rendered.
     * @param id         unique id for the javascript element. This can be null, however in that case the
     *                   ajax header contribution can't detect duplicate script fragments.
     */
    public ESModuleContentHeaderItem(CharSequence javaScript, String id) {
        super(javaScript, id, null);
    }

    @Override
    public void render(Response response) {
        String id = getId();
        CharSequence text = getJavaScript();
        response.write("<script type=\"module\" ");
        if (id != null)
        {
            response.write("id=\"" + Strings.escapeMarkup(id) + "\"");
        }
        response.write(">");
        response.write(Strings.replaceAll(text, "</", "<\\/"));
        response.write("</script>\n");
    }
}
