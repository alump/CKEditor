/**
 * CKEditorConfig.java (CKEditor)
 *
 * Copyright 2017 Vaadin Ltd, Sami Viitanen <sami.viitanen@vaadin.org>
 *
 * Based on CKEditor from Yozons, Inc, Copyright (C) 2010-2016 Yozons, Inc.
 *
 * Licensed under the LGPL 2.1 license, to match with open source usage of CKEditor JavaScript library.
 */
package org.vaadin.alump.ckeditor.demo;


import com.vaadin.annotations.Push;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.HasValue;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.annotations.Theme;
import org.vaadin.alump.ckeditor.CKEditorConfig;
import org.vaadin.alump.ckeditor.AbstractCKEditorTextField;
import org.vaadin.alump.ckeditor.CKEditorTextField;

import javax.servlet.annotation.WebServlet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * HISTORY:
 * This is a port of VaadinCKEditorApplication 1.8.2 built using Vaadin 6.8.10, and we're giving it version 7.8.2
 * to show it's the same basic code, just ported to Vaadin 7, but using the new VaadinCKEditorUI name.
 * It is essentially a "legacy port" and does not yet take advantage of any of the new capabilities of Vaadin 7.0.5.
 * Finally Vaadin 8 port was done based on Vaadin 7 version.
 * @author Sami Viitanen sami.viitanen@vaadin.com
 */

@Theme("demo")
@Title("CKEditor Demo")
public class VaadinCKEditorUI extends UI {

	//private Label pushUpdateLabel = new Label();
	//private CheckBox enablePushEvents;
	private AtomicInteger pushCounter = new AtomicInteger(0);

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadinCKEditorUI.class, widgetset = "org.vaadin.alump.ckeditor.demo.WidgetSet")
	public static class Servlet extends VaadinServlet {
	}

	private Component createSeparator() {
		Label separator = new Label("&nbsp;");
		separator.addStyleName("separator");
		separator.setContentMode(ContentMode.HTML);
		return separator;
	}

	@Override
	public void init(VaadinRequest request) {

		//Enable to test effects of other UIDL calls to cursor position
		//setPollInterval(5000);
		
		getPage().setTitle("Vaadin CKEditor UI");
		
		VerticalLayout mainView = new VerticalLayout();
		setContent(mainView);

		/*
		enablePushEvents = new CheckBox("Enable push events");
		enablePushEvents.addValueChangeListener(this::enablePushEvents);
		mainView.addComponents(new HorizontalLayout(enablePushEvents, pushUpdateLabel));
		*/


		/* See http://ckeditor.com/latest/samples/plugins/toolbar/toolbar.html for the official info.
		 * This is the full list as we know it in CKEditor 4.x
	[
    { name: 'document', items : [ 'Source','-','NewPage','Preview','Print','-','Templates' ] },
	{ name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
	{ name: 'editing', items : [ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ] },
	{ name: 'forms', items : [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField' ] },
	'/',
	{ name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },
	{ name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','CreateDiv','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ] },
	{ name: 'links', items : [ 'Link','Unlink','Anchor' ] },
	{ name: 'insert', items : [ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak','Iframe' ] },
	'/',
	{ name: 'styles', items : [ 'Styles','Format','Font','FontSize' ] },
	{ name: 'colors', items : [ 'TextColor','BGColor' ] },
	{ name: 'tools', items : [ 'Maximize', 'ShowBlocks','-','About' ] }
	]
		 */
		
		final String editor1InitialValue = 
				"<p>Thanks TinyMCEEditor for getting us started on the CKEditor integration.</p>\n\n<h1>Like TinyMCEEditor said, &quot;Vaadin rocks!&quot;</h1>\n\n<h1>And CKEditor is no slouch either.</h1>\n";

		CKEditorConfig config1 = new CKEditorConfig();
		config1.useCompactTags();
		config1.disableElementsPath();
		config1.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
		config1.disableSpellChecker();
		config1.setHeight("300px");
		config1.addToExtraPlugins("exampleplugin");
		config1.addToExtraPlugins("inserttextplugin");

		//final CKEditorTextField ckEditorTextField1 = new CKEditorTextField(config1);
		final AbstractCKEditorTextField ckEditorTextField1 = new CKEditorPluginExample(config1);
		ckEditorTextField1.setImmediate(true);
		ckEditorTextField1.setHeight("440px"); // account for 300px editor plus toolbars
		mainView.addComponent(ckEditorTextField1);

		ckEditorTextField1.addCustomEventListener(new AbstractCKEditorTextField.CustomEventListener() {
			@Override
			public void onCustomEvent(AbstractCKEditorTextField editor, String param) {
				if(param!=null && param.equals("inserttext")){
					editor.insertText("Text from vaadin");
				}
			}
		});
		
		ckEditorTextField1.setValue(editor1InitialValue);
		ckEditorTextField1.addValueChangeListener(e -> {
				System.out.println("CKEditor v" + ckEditorTextField1.getVersion() + "/" + getVersion()
						+ " - #1 contents: " + e.getValue());
		});
		
		Button resetTextButton1 = new Button("Reset editor #1");
		resetTextButton1.addClickListener(event -> {
			if ( ! ckEditorTextField1.isReadOnly() ) {
				ckEditorTextField1.setValue(editor1InitialValue);
			}
		});
		
		Button toggleReadOnlyButton1 = new Button("Toggle read-only editor #1");
		toggleReadOnlyButton1.addClickListener(event -> {
				ckEditorTextField1.setReadOnly( ! ckEditorTextField1.isReadOnly() );
		});

		Button toggleViewWithoutEditorButton1 = new Button("Toggle view-without-editor #1");
		toggleViewWithoutEditorButton1.addClickListener(event ->  {
				ckEditorTextField1.setViewWithoutEditor( ! ckEditorTextField1.isViewWithoutEditor() );
		});

		Button toggleVisibleButton1 = new Button("Toggle visible editor #1");
		toggleVisibleButton1.addClickListener( event -> {
				ckEditorTextField1.setVisible( ! ckEditorTextField1.isVisible() );
		});
		HorizontalLayout buttonsLayout = new HorizontalLayout(resetTextButton1,toggleReadOnlyButton1,toggleViewWithoutEditorButton1,toggleVisibleButton1);
		buttonsLayout.setSpacing(true);
		mainView.addComponent( buttonsLayout );

		mainView.addComponent(createSeparator());
		
		// Now add in a second editor....
		final String editor2InitialValue = 
			"<p>Here is editor #2.</p>\n\n<p>Hope you find this useful in your Vaadin projects.</p>\n";

		//final CKEditorTextField ckEditorTextField2 = new CKEditorTextField();
		final AbstractCKEditorTextField ckEditorTextField2 = new CKEditorTextField();
		ckEditorTextField2.setWidth("600px");
		mainView.addComponent(ckEditorTextField2);
		
		CKEditorConfig config2 = new CKEditorConfig();
		config2.addCustomToolbarLine("{ items : ['Source','Styles','Bold','VaadinSave','-','Undo','Redo','-','NumberedList','BulletedList'] }");
		config2.enableCtrlSWithVaadinSavePlugin();
		config2.addToRemovePlugins("scayt");
		ckEditorTextField2.setConfig(config2);
		ckEditorTextField2.setValue(editor2InitialValue);
		
		ckEditorTextField2.addValueChangeListener(event -> {
				Notification.show("CKEditor v" + ckEditorTextField2.getVersion() + "/" + getVersion() + " - #2 contents: " + event.getValue());
		});

		ckEditorTextField2.addVaadinSaveListener(editor -> {
				Notification.show("CKEditor v" + ckEditorTextField2.getVersion() + "/" + getVersion() + " - #2 VaadinSave button pressed.");
		});

		Button resetTextButton2 = new Button("Reset editor #2");
		resetTextButton2.addClickListener(event -> {
			if ( ! ckEditorTextField2.isReadOnly() ) {
				ckEditorTextField2.setValue(editor2InitialValue);
			}
		});
		
		Button toggleReadOnlyButton2 = new Button("Toggle read-only editor #2");
		toggleReadOnlyButton2.addClickListener(event -> {
			ckEditorTextField2.setReadOnly( ! ckEditorTextField2.isReadOnly() );
		});

		Button toggleViewWithoutEditorButton2 = new Button("Toggle view-without-editor #2");
		toggleViewWithoutEditorButton2.addClickListener(event -> {
			ckEditorTextField2.setViewWithoutEditor( ! ckEditorTextField2.isViewWithoutEditor() );
		});

		Button toggleVisibleButton2 = new Button("Toggle visible editor #2");
		toggleVisibleButton2.addClickListener(event -> {
				ckEditorTextField2.setVisible( ! ckEditorTextField2.isVisible() );
		});
		
		buttonsLayout = new HorizontalLayout(resetTextButton2,toggleReadOnlyButton2,toggleViewWithoutEditorButton2,toggleVisibleButton2);
		buttonsLayout.setSpacing(true);
		mainView.addComponent( buttonsLayout );

		buttonsLayout = new HorizontalLayout();
		buttonsLayout.setSpacing(true);
		mainView.addComponent( buttonsLayout );
		
		buttonsLayout.addComponent(new Button("Open Modal Subwindow", event -> {

			Window sub = new Window("Subwindow modal");
			VerticalLayout subLayout = new VerticalLayout();
			sub.setContent(subLayout);

			CKEditorConfig config = new CKEditorConfig();
			config.useCompactTags();
			config.disableElementsPath();
			config.disableSpellChecker();
			config.enableVaadinSavePlugin();
			// set BaseFloatZIndex 1000 higher than CKEditor's default of 10000; probably a result of an editor opening
			// in a window that's on top of the main two editors of this demo app
			config.setBaseFloatZIndex(11000);
			config.setHeight("150px");

			final CKEditorTextField ckEditorTextField = new CKEditorTextField(config);
			ckEditorTextField.addValueChangeListener(event2 -> {
					Notification.show("CKEditor v" + ckEditorTextField2.getVersion() + "/" + getVersion() + " - POPUP MODAL contents: " + event2.getValue());
			});
			ckEditorTextField.focus();

			subLayout.addComponent(ckEditorTextField);

			sub.setWidth("80%");
			sub.setModal(true);
			sub.center();

			event.getButton().getUI().addWindow(sub);
        }));

		buttonsLayout.addComponent(new Button("Open Non-Modal Subwindow with 100% Height", event -> {
				Window sub = new Window("Subwindow non-modal 100% height");
				VerticalLayout subLayout = new VerticalLayout();
				sub.setContent(subLayout);
				sub.setWidth("80%");
				sub.setHeight("500px");

				subLayout.setSizeFull();

				CKEditorConfig config = new CKEditorConfig();
				config.useCompactTags();
				config.disableElementsPath();
				config.disableSpellChecker();
				config.enableVaadinSavePlugin();
				// set BaseFloatZIndex 1000 higher than CKEditor's default of 10000; probably a result of an editor opening
				// in a window that's on top of the main two editors of this demo app
				config.setBaseFloatZIndex(11000);
				config.setStartupFocus(true);
				config.setReadOnly(true);

				final CKEditorTextField ckEditorTextField = new CKEditorTextField(config);
				ckEditorTextField.setHeight("100%");
				ckEditorTextField.addValueChangeListener(event2 -> {
						Notification.show("CKEditor v" + ckEditorTextField.getVersion() + "/" + getVersion() + " - POPUP NON-MODAL 100% HEIGHT contents: " + event2.getValue());
				});
				subLayout.addComponent(ckEditorTextField);
				subLayout.setExpandRatio(ckEditorTextField,10);

				final TextField textField = new TextField("TextField");
				textField.addValueChangeListener(event2 -> {
						Notification.show("TextField - POPUP NON-MODAL 100% HEIGHT contents: " + event2.getValue());
				});
				subLayout.addComponent(textField);

				sub.center();

				event.getButton().getUI().addWindow(sub);
        }));
	}

	/*
	private void enablePushEvents(HasValue.ValueChangeEvent<Boolean> event) {
		if(event.getValue()) {
			pushCounter.set(0);
			Runnable runnable = () -> {
				try {
					while(true) {
						if(!VaadinCKEditorUI.this.isAttached() || !enablePushEvents.getValue()) {
							break;
						}
						int value = pushCounter.incrementAndGet();
						VaadinCKEditorUI.this.getUI().access(() -> pushUpdateLabel.setValue("#" + value));
						Thread.sleep(5000);
					}
				} catch(InterruptedException e) {
				}
			};
			Thread thread = new Thread(runnable);
			thread.start();
		}
	}
	*/

	public String getVersion() {
		return "0.1.0";
	}

}
