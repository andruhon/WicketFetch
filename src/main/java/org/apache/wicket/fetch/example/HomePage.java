package org.apache.wicket.fetch.example;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.fetch.MyExperimentalBehaviour;
import org.apache.wicket.fetch.OnChangeFetchBehavior;
import org.apache.wicket.fetch.ZzzBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class HomePage extends WebPage {

    IModel<String> field1Model = Model.of();
    IModel<String> field2Model = Model.of();
    IModel<String> field3Model = Model.of();

    public HomePage() {
        add(new Label("helloMessage", "Hello WicketWorld!"));
        TextField<String> field1 = new TextField<>("field1", field1Model);
        field1.add(new OnChangeFetchBehavior());
        TextField<String> field2 = new TextField<>("field2", field2Model);
        field2.add(new OnChangeAjaxBehavior() {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {

            }
        });
        TextField<String> field3 = new TextField<>("field3", field3Model);
//        field3.add(new MyExperimentalBehaviour());
        add(field1);
        add(field2);
        add(field3);
    }

}
