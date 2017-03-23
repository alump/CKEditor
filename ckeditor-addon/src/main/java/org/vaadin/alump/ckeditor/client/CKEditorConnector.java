/**
 * AbstractCKEditorTextField.java (CKEditor)
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
package org.vaadin.alump.ckeditor.client;

import com.vaadin.client.ui.LegacyConnector;
import com.vaadin.client.ui.SimpleManagedLayout;
import com.vaadin.shared.ui.Connect;
import org.vaadin.alump.ckeditor.AbstractCKEditorTextField;

@Connect(AbstractCKEditorTextField.class)
public class CKEditorConnector extends LegacyConnector  implements SimpleManagedLayout {
	private static final long serialVersionUID = -3096333767592095605L;

	@Override
    public VCKEditorTextField getWidget() {
        return (VCKEditorTextField) super.getWidget();
    }

    @Override
    public void layout() {
        // TODO Auto-generated method stub
        
    } 

}
