/**
 * CKEditorTextField.java (CKEditor)
 *
 * Copyright 2017 Vaadin Ltd, Sami Viitanen <sami.viitanen@vaadin.org>
 *
 * Based on CKEditor from Yozons, Inc, Copyright (C) 2010-2016 Yozons, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vaadin.alump.ckeditor;

import com.vaadin.annotations.JavaScript;

/**
 * Example version of CKEditorTextField that loads ckeditor js-library from ckeditor.com. Remember that you need to
 * have suitable license to use CKEditor. http://ckeditor.com/pricing
 */
@JavaScript({"http://cdn.ckeditor.com/4.6.2/standard/ckeditor.js", "vaadin-save-plugin.js"})
public class CKEditorTextField extends AbstractCKEditorTextField {

    public CKEditorTextField() {
        super();
    }

    public CKEditorTextField(CKEditorConfig config) {
        super(config);
    }

    public CKEditorTextField(CKEditorConfig config, String initialValue) {
        super(config, initialValue);
    }
}
