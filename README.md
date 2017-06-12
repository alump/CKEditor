# CKEditor Add-on for Vaadin

[![Build Status](https://epic.siika.fi/jenkins/job/CKEditor%20(Vaadin)/badge/icon)](https://epic.siika.fi/jenkins/job/CKEditor%20(Vaadin)/)

CKEditor is a UI component add-on for Vaadin 8.

This project is fork of [vaadin-ckeditor](https://github.com/OpenESignForms/vaadin-ckeditor/tree/master/VaadinCKEditor) project from Yozons, Inc. (David Wall)

## Licensing

__NOTICE__ that add-on licensing does not include CKEditor JavaScript library itself. It is required to use this 
add-on, please check their licensing at http://ckeditor.com/pricing . If you do not buy license you are using it under
GPL / LGPL / MPL that will affect software you are using.

CKEditor add-on has Apache 2.0 license, so it can be used in closed source projects as long as you have
suitable license from CKEditor.js.

CKEditor add-on's demo project has LGPL license, as it uses Open Source version of CKEditor.

## Online demo

Try the add-on demo at http://app.siika.fi/CKEditorDemo

## Release notes

### Version 0.1.1 (2017-06-12)
- Add missing setImmediate to get value change events faster
- Plugin example to demo project

### Version 0.1.0 (2017-03-23)
- Initial release. Vaadin 8 port of Vaadin-CKEditor add-on from Yozons, Inc

## Download release

Official releases of this add-on are available at Vaadin Directory. For Maven instructions, download and reviews, go to http://vaadin.com/addon/ckeditor

## Building and running demo

git clone <url of the MyComponent repository>
mvn clean install
cd demo
mvn jetty:run

To see the demo, navigate to http://localhost:8080/

## Development with Eclipse IDE

For further development of this add-on, the following tool-chain is recommended:
- Eclipse IDE
- m2e wtp plug-in (install it from Eclipse Marketplace)
- Vaadin Eclipse plug-in (install it from Eclipse Marketplace)
- JRebel Eclipse plug-in (install it from Eclipse Marketplace)
- Chrome browser

### Importing project

Choose File > Import... > Existing Maven Projects

Note that Eclipse may give "Plugin execution not covered by lifecycle configuration" errors for pom.xml. Use "Permanently mark goal resources in pom.xml as ignored in Eclipse build" quick-fix to mark these errors as permanently ignored in your project. Do not worry, the project still works fine. 

### Debugging server-side

If you have not already compiled the widgetset, do it now by running vaadin:install Maven target for ckeditor-root project.

If you have a JRebel license, it makes on the fly code changes faster. Just add JRebel nature to your ckeditor-demo project by clicking project with right mouse button and choosing JRebel > Add JRebel Nature

To debug project and make code modifications on the fly in the server-side, right-click the ckeditor-demo project and choose Debug As > Debug on Server. Navigate to http://localhost:8080/ckeditor-demo/ to see the application.

### Debugging client-side

Debugging client side code in the ckeditor-demo project:
  - run "mvn vaadin:run-codeserver" on a separate console while the application is running
  - activate Super Dev Mode in the debug window of the application or by adding ?superdevmode to the URL
  - You can access Java-sources and set breakpoints inside Chrome if you enable source maps from inspector settings.


## Roadmap
- Proper clean up of code, removing dependency to legacy classes

## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome, but there are no guarantees that they are accepted as such. Process for contributing is the following:
- Fork this project
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- Refer to the fixed issue in commit
- Send a pull request for the original project
- Comment on the original issue that you have implemented a fix for it

## License & Author

Add-on is distributed under Apache License 2.0. For license terms, see LICENSE.txt.

MyComponent is written by <...>

# Developer Guide

## Getting started

Notice that this add-on can be used without copying CKEditor javascript library to project. As it will by default load JavaScript library from ckeditor.com.

TODO, see documentation of old add-on.
