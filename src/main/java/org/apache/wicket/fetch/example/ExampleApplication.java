package org.apache.wicket.fetch.example;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

public class ExampleApplication extends WebApplication
{
    @Override
    public Class<? extends WebPage> getHomePage()
    {
        return HomePage.class;
    }

    @Override
    public void init()
    {
        super.init();
        // add your configuration here
    }
}