/**
 * CKEditor.java (CKEditor)
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

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Wrapper around CKEDITOR.editor js object
 */
public class CKEditor extends JavaScriptObject {

	protected CKEditor() {
	}

	public final native boolean checkDirty()
	/*-{
		return this.checkDirty();
	}-*/;

	public final native void resetDirty()
	/*-{
		this.resetDirty();
	}-*/;

	public final native String getData()
	/*-{
		return this.getData();
	}-*/;

	public final native void setData(String htmlData)
	/*-{
		return this.setData(htmlData);
	}-*/;
	
	public final native boolean isReadOnly()
	/*-{
		return this.readOnly;
	}-*/;

	public final native void setReadOnly(boolean isReadOnly)
	/*-{
		this.setReadOnly(isReadOnly);
	}-*/;
	
	public final native void setWriterRules(String tagName, String jsRule)
	/*-{
	 	var rule = @org.vaadin.alump.ckeditor.client.CKEditorService::convertJavaScriptStringToObject(Ljava/lang/String;)(jsRule);
		this.dataProcessor.writer.setRules(tagName, rule);
	}-*/;
	
	public final native void setKeystroke(int keystroke, String command)
	/*-{
		this.setKeystroke(keystroke, command);
	}-*/;
	
	public final native void pushProtectedSource(String regexString)
	/*-{
	    var regex = @org.vaadin.alump.ckeditor.client.CKEditorService::convertJavaScriptStringToObject(Ljava/lang/String;)(regexString);
		this.config.protectedSource.push( regex );
	}-*/;
	
	public final native void setWriterIndentationChars(String indentationChars)
	/*-{
		this.dataProcessor.writer.indentationChars = indentationChars;
	}-*/;
	
	public final native void instanceReady(CKEditorService.CKEditorListener listener)
	/*-{
	 	// The 'listener' passed to us is used as 'listenerData' for the callback.
	 	this.on( 'blur', function( ev ) {
 			ev.listenerData.@org.vaadin.alump.ckeditor.client.CKEditorService.CKEditorListener::onBlur()();
    	}, null, listener);
    	
	 	this.on( 'focus', function( ev ) {
 			ev.listenerData.@org.vaadin.alump.ckeditor.client.CKEditorService.CKEditorListener::onFocus()();
    	}, null, listener);
    	
     	this.on( 'vaadinsave', function( ev ) {
	 		ev.listenerData.@org.vaadin.alump.ckeditor.client.CKEditorService.CKEditorListener::onSave()();
    	}, null, listener);
    	
    	// hook into the change events for ckEditor
		this.on('change', function(ev) { 
			ev.listenerData.@org.vaadin.alump.ckeditor.client.CKEditorService.CKEditorListener::onChange()();
		}, null, listener);

		this.on('selectionChange', function(ev) { 
			ev.listenerData.@org.vaadin.alump.ckeditor.client.CKEditorService.CKEditorListener::onSelectionChange()();
		}, null, listener);
		this.on('contentDom', function(ev) {
			this.document.on('keyup', function(ev2) {
				ev2.listenerData.@org.vaadin.alump.ckeditor.client.CKEditorService.CKEditorListener::onSelectionChange()();
			}, null, ev.listenerData);
			this.document.on('mouseup', function(ev2) {
				ev2.listenerData.@org.vaadin.alump.ckeditor.client.CKEditorService.CKEditorListener::onSelectionChange()();
			}, null, ev.listenerData);
		}, null, listener);
		
		this.on('mode', function(ev) { 
			ev.listenerData.@org.vaadin.alump.ckeditor.client.CKEditorService.CKEditorListener::onModeChange(Ljava/lang/String;)(ev.editor.mode);
		}, null, listener);
		this.on('dataReady', function(ev) { 
			ev.listenerData.@org.vaadin.alump.ckeditor.client.CKEditorService.CKEditorListener::onDataReady()();
		}, null, listener);

	}-*/;
	
	public final native void execCommand(String cmd)
	/*-{
		this.execCommand(cmd);
	}-*/;
	
	public final native void updateElement()
	/*-{
		this.updateElement();
	}-*/;
	
	public final native void destroy(boolean noUpdate)
	/*-{
		this.destroy(noUpdate);
	}-*/;
	
	public final native void destroy()
	/*-{
		this.destroy();
	}-*/;
	
	public final native void resize(int width, int height)
	/*-{
	 	this.resize(width, height);
	}-*/;
	
	public final native String getId()
	/*-{
	 	return this.id;
	}-*/;
	
	public final native void focus()
	/*-{
	 	this.focus();
	}-*/;
	
	public final native int getTabIndex()
	/*-{
	 	return this.tabIndex;
	}-*/;
	
	public final native void setTabIndex(int tabIndex)
	/*-{
	 	this.tabIndex = tabIndex;
	}-*/;
	
	public final native void insertHtml(String data)
	/*-{
	 	this.insertHtml(data);
	}-*/;
	
	public final native void insertText(String data)
	/*-{
	 	this.insertText(data);
	}-*/;
	
	public final native void protectBody(boolean protectBody)
	/*-{
	 	if (this.document) {
	 		if (this.document.getBody()) {
	 			this.document.getBody().$.contentEditable = !protectBody;
	 		}
	 	}
	}-*/;

	public final native String getSelectedHtml()
	/*-{
	    return this.getSelectedHtml(true);
	}-*/;

}
