
(function()
{
    var insertTextPluginCmd =
        {
            modes: { wysiwyg: 1, source: 1 },
            exec : function( editor )
            {
                editor.fire("customevent", "inserttext");
            }
        };

    var pluginName = 'inserttextplugin';

    // Register a plugin named "inserttext".
    CKEDITOR.plugins.add( pluginName,
        {
            icons: pluginName,

            init : function( editor )
            {
                var command = editor.addCommand( pluginName, insertTextPluginCmd );

                editor.ui.addButton( 'InsertText',
                    {
                        label : 'Insert text Plugin',
                        command : pluginName,
                        toolbar : 'document',
                        icon : "link"
                    });
            }
        });
})();