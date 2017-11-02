package org.vaadin.alump.ckeditor.demo;

import com.vaadin.annotations.JavaScript;
import org.vaadin.alump.ckeditor.AbstractCKEditorTextField;
import org.vaadin.alump.ckeditor.CKEditorConfig;

/**
 * Example of extending with plugins. In JavaScript annotation includes the plugin js file, that is available on
 * same package (in maven project under resources).
 */
@JavaScript({"http://cdn.ckeditor.com/4.6.2/standard/ckeditor.js", "example_plugin.js", "customevent_plugin.js"})
public class CKEditorPluginExample extends AbstractCKEditorTextField {

    public CKEditorPluginExample() {
        super();
    }

    public CKEditorPluginExample(CKEditorConfig config) {
        super(config);
    }

    public CKEditorPluginExample(CKEditorConfig config, String initialValue) {
        super(config, initialValue);
    }
}
