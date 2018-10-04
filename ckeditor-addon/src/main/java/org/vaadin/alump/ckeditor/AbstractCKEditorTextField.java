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
package org.vaadin.alump.ckeditor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.vaadin.event.ConnectorEventListener;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.shared.Registration;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.LegacyComponent;
import com.vaadin.util.ReflectTools;
import org.vaadin.alump.ckeditor.client.VCKEditorTextField;

/**
 * Server side component for the VCKEditorTextField widget. Is abstract as does not contain definition from where
 * the JavaScript libraries are loaded.
 * @see org.vaadin.alump.ckeditor.CKEditorTextField
 */
public abstract class AbstractCKEditorTextField extends AbstractField<String>
	implements FieldEvents.BlurNotifier, FieldEvents.FocusNotifier, Component.Focusable, LegacyComponent  {

	private CKEditorConfig config;
	private String version = "unknown";
	private String insertText = null;
	private String insertHtml = null;
	private boolean protectedBody = false;
	private boolean viewWithoutEditor = false;
	private boolean focusRequested = false;
	private boolean immediate = false;
	protected LinkedList<VaadinSaveListener> vaadinSaveListenerList;
	protected LinkedList<CustomEventListener> customEventListenerList;

	private boolean textIsDirty;
	protected String value;

	protected AbstractCKEditorTextField() {
		super.setValue("");
		setWidth(100, Unit.PERCENTAGE);
		setHeight(300, Unit.PIXELS);
	}

    protected AbstractCKEditorTextField(CKEditorConfig config) {
		this();
		setConfig(config);
	}

    protected AbstractCKEditorTextField(CKEditorConfig config, String initialValue) {
		this();
		setValue(initialValue);
		setConfig(config);
	}
	
	public void setConfig(CKEditorConfig config) {
		this.config = config;
		if ( config.isReadOnly() )
			setReadOnly(true);
	}
	
	public String getVersion() {
		return version;
	}


	@Override
	public String getValue() {
		return value;
	}

	@Override
	protected void doSetValue(String s) {
        value = s == null ? "" : s;
		textIsDirty = true;
	}
 
 	@Override
	public void beforeClientResponse(boolean initial) {
		if(initial) {
			textIsDirty = true;
		}
		super.beforeClientResponse(initial);
	}
	
	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		//super.paintContent(target);
		
		if(textIsDirty) {
			Object currValueObject = getValue();
			String currValue = currValueObject == null ? "" : currValueObject.toString();
			target.addVariable(this, VCKEditorTextField.VAR_TEXT, currValue);
			textIsDirty = false;
		}

		target.addAttribute(VCKEditorTextField.ATTR_IMMEDIATE, isImmediate());
		target.addAttribute(VCKEditorTextField.ATTR_READONLY, isReadOnly());
		target.addAttribute(VCKEditorTextField.ATTR_VIEW_WITHOUT_EDITOR, isViewWithoutEditor());

		if (config != null) {
			target.addAttribute(VCKEditorTextField.ATTR_INPAGECONFIG, config.getInPageConfig());
			
			if ( config.hasWriterRules() ) {
				int i = 0;
				Set<String> tagNameSet = config.getWriterRulesTagNames();
				for( String tagName : tagNameSet ) {
					target.addAttribute(VCKEditorTextField.ATTR_WRITERRULES_TAGNAME+i, tagName);
					target.addAttribute(VCKEditorTextField.ATTR_WRITERRULES_JSRULE+i, config.getWriterRuleByTagName(tagName));
					++i;
				}
			}
			
			if ( config.hasWriterIndentationChars() ) {
				target.addAttribute(VCKEditorTextField.ATTR_WRITER_INDENTATIONCHARS, config.getWriterIndentationChars());
			}
			
			if ( config.hasKeystrokeMappings() ) {
				int i = 0;
				Set<Integer> keystrokeSet = config.getKeystrokes();
				for( Integer keystroke : keystrokeSet ) {
					target.addAttribute(VCKEditorTextField.ATTR_KEYSTROKES_KEYSTROKE+i, keystroke);
					target.addAttribute(VCKEditorTextField.ATTR_KEYSTROKES_COMMAND+i, config.getKeystrokeCommandByKeystroke(keystroke));
					++i;
				}
			}
			
			if ( config.hasProtectedSource() ) {
				int i = 0;
				for( String protectedSourceRegex : config.getProtectedSource() ) {
					target.addAttribute(VCKEditorTextField.ATTR_PROTECTED_SOURCE+i, protectedSourceRegex);
					++i;
				}
			}
		}
		
		target.addAttribute(VCKEditorTextField.ATTR_PROTECTED_BODY, protectedBody);
		
		if (insertHtml != null) {
			target.addAttribute(VCKEditorTextField.ATTR_INSERT_HTML, insertHtml);
			insertHtml = null;
		}
		if (insertText != null) {
			target.addAttribute(VCKEditorTextField.ATTR_INSERT_TEXT, insertText);
			insertText = null;
		}	
		
		if ( focusRequested ) {
			target.addAttribute(VCKEditorTextField.ATTR_FOCUS, true);
			focusRequested = false;
		}
		
	}
	
    @Override
    public void changeVariables(Object source, Map<String, Object> variables) {
        //super.changeVariables(source, variables);

        // Sets the CKEditor version
        if (variables.containsKey(VCKEditorTextField.VAR_VERSION)) {
        	version = (String)variables.get(VCKEditorTextField.VAR_VERSION);
        }
        
        // Sets the text
        if (variables.containsKey(VCKEditorTextField.VAR_TEXT) && ! isReadOnly()) {
            // Only do the setting if the string representation of the value has been updated
        	Object newVarTextObject = variables.get(VCKEditorTextField.VAR_TEXT);
            String newValue = newVarTextObject == null ? "" : newVarTextObject.toString();

            Object currValueObject = getValue();
            final String oldValue = currValueObject == null ? "" : currValueObject.toString();
            if ( ! newValue.equals(oldValue) ) {
        		setValue(newValue, true);
            }
        }
        
        if (variables.containsKey(FocusEvent.EVENT_ID)) {
            fireEvent(new FocusEvent(this));
        }
        if (variables.containsKey(BlurEvent.EVENT_ID)) {
            fireEvent(new BlurEvent(this));
        }
        
        if (variables.containsKey(SelectionChangeEvent.EVENT_ID)) {
        	Object selectedHtmlObject = variables.get(SelectionChangeEvent.EVENT_ID);
            if ( selectedHtmlObject != null ) {
            	String selectedHtml = selectedHtmlObject.toString();
            	fireEvent(new SelectionChangeEvent(this,selectedHtml));
            }
        }

        // See if the vaadinsave button was pressed
        if (variables.containsKey(VCKEditorTextField.VAR_VAADIN_SAVE_BUTTON_PRESSED) && ! isReadOnly()) {
        	notifyVaadinSaveListeners();
        }

		// See if customevent was fired
		if (variables.containsKey(VCKEditorTextField.VAR_ON_CUSTOM_EVENT) && ! isReadOnly()) {
			notifyCustomEventListeners((String)variables.get(VCKEditorTextField.VAR_ON_CUSTOM_EVENT));
		}
    }
	
	@Override
	public Registration addBlurListener(BlurListener listener) {
		return addListener(BlurEvent.EVENT_ID, BlurEvent.class, listener, BlurListener.blurMethod);
	}

	public void removeBlurListener(BlurListener listener) {
		removeListener(BlurEvent.EVENT_ID, BlurEvent.class, listener);
	}

	@Override
	public Registration addFocusListener(FocusListener listener) {
		return addListener(FocusEvent.EVENT_ID, FocusEvent.class, listener, FocusListener.focusMethod);
	}

	public void removeFocusListener(FocusListener listener) {
		removeListener(FocusEvent.EVENT_ID, FocusEvent.class, listener);
	}
	
	/**
	 * @param listener
	 */
	public void addSelectionChangeListener(SelectionChangeListener listener) {
		addListener(SelectionChangeEvent.EVENT_ID, SelectionChangeEvent.class, listener, SelectionChangeListener.selectionChangeMethod);
	}
	public void removeSelectionChangeListener(SelectionChangeListener listener) {
		removeListener(SelectionChangeEvent.EVENT_ID, SelectionChangeEvent.class, listener);
	}
	
	@Override
    public void setHeight(String height) {
		super.setHeight(height);
	}

	@Override
	public void detach() {
		super.detach();
	}
	
	// Part of Focusable
	@Override
    public void focus() {
		super.focus();
		focusRequested = true;
		markAsDirty();
    }
	
	public boolean isViewWithoutEditor() {
		return viewWithoutEditor;
	}
	public void setViewWithoutEditor(boolean v) {
		viewWithoutEditor = v;
		markAsDirty();
	}
	
	public void insertHtml(String html) {
		if (insertHtml == null) 
			insertHtml = html;
		else 
			insertHtml += html;
		markAsDirty();
	}
	
	public void insertText(String text) {
		if (insertText == null) 
			insertText = text;
		else 
			insertText += text;
		markAsDirty();
	}

	public void setProtectedBody(boolean protectedBody) {
		this.protectedBody = protectedBody;
		markAsDirty();
	}

	public boolean isProtectedBody() {
		return protectedBody;
	}
	
	
	public synchronized void addVaadinSaveListener(VaadinSaveListener listener) {
		if ( vaadinSaveListenerList == null )
			vaadinSaveListenerList = new LinkedList<VaadinSaveListener>();
		vaadinSaveListenerList.add(listener);
	}
	public synchronized void removeVaadinSaveListener(VaadinSaveListener listener) {
		if ( vaadinSaveListenerList != null )
			vaadinSaveListenerList.remove(listener);
	}
	synchronized void notifyVaadinSaveListeners() {
		if ( vaadinSaveListenerList != null ) {
			for( VaadinSaveListener listener : vaadinSaveListenerList )
				listener.vaadinSave(this);
		}
	}

	public synchronized void addCustomEventListener(CustomEventListener listener) {
		if ( customEventListenerList == null ) {
			customEventListenerList = new LinkedList<CustomEventListener>();
		}
		customEventListenerList.add(listener);
	}
	public synchronized void removeCustomEventListener(CustomEventListener listener) {
		if ( customEventListenerList != null ) {
			customEventListenerList.remove(listener);
		}
	}
	synchronized void notifyCustomEventListeners(String param) {
		if ( customEventListenerList != null ) {
			for( CustomEventListener listener : customEventListenerList )
				listener.onCustomEvent(this, param);
		}
	}
	
	public interface VaadinSaveListener extends Serializable {
		/**
	     * Notifies this listener that the vaadinsave button in the editor was pressed.
	     * 
	     * @param editor the CKEditorTextField that was saved
	     */
		void vaadinSave(AbstractCKEditorTextField editor);
	}

	public interface CustomEventListener extends Serializable {
		/**
		 * Notifies this listener that a custom event has fired
		 *
		 * @param editor the CKEditorTextField that fired the event
		 */
		void onCustomEvent(AbstractCKEditorTextField editor, String param);
	}
	
	
    @SuppressWarnings("serial")
    public static class SelectionChangeEvent extends Component.Event {
        public static final String EVENT_ID = VCKEditorTextField.EVENT_SELECTION_CHANGE;

        private String selectedHtml;
        
        public SelectionChangeEvent(Component source, String selectedHtml) {
            super(source);
            this.selectedHtml = selectedHtml;
        }
        
        public String getSelectedHtml() {
        	return selectedHtml;
        }
        
        public boolean hasSelectedHtml() {
        	return ! "".equals(selectedHtml);
        }
    }

    public interface SelectionChangeListener extends ConnectorEventListener {
        public static final Method selectionChangeMethod = ReflectTools.findMethod(
                SelectionChangeListener.class, "selectionChange", SelectionChangeEvent.class);
        
        public void selectionChange(SelectionChangeEvent event);
    }

	/**
	 * Set CKEditor immediate to get change events faster
	 * @param immediate
	 */
	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
		markAsDirty();
	}

	/**
	 * Is CKEditor in immediate mode
	 * @return
	 */
	public boolean isImmediate() {
		return this.immediate;
	}

}
