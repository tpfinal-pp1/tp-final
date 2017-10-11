package com.TpFinal;

import java.util.HashSet;
import java.util.Set;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.vaadin.client.ui.ui.UIConnector;
import com.vaadin.server.widgetsetutils.ConnectorBundleLoaderFactory;
import com.vaadin.shared.ui.Connect.LoadStyle;

public class OptimizedConnectorBundleLoaderFactory extends
        ConnectorBundleLoaderFactory {
    private Set<String> eagerConnectors = new HashSet<String>();
    {
        eagerConnectors.add(com.vaadin.client.ui.ui.UIConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.ui.orderedlayout.VerticalLayoutConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.ui.label.LabelConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.extensions.ResponsiveConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.ui.csslayout.CssLayoutConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.ui.button.ButtonConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.ui.orderedlayout.HorizontalLayoutConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.ui.checkbox.CheckBoxConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.ui.textfield.TextFieldConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.ui.passwordfield.PasswordFieldConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.ui.panel.PanelConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.ui.menubar.MenuBarConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.ui.textarea.TextAreaConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.ui.customcomponent.CustomComponentConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.connectors.grid.SingleSelectionModelConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.connectors.grid.GridConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.connectors.data.DataCommunicatorConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.connectors.grid.EditorConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.connectors.grid.TextRendererConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.connectors.grid.ColumnConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.connectors.grid.DetailsManagerConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.ui.tabsheet.TabsheetConnector.class.getName());
        eagerConnectors.add(com.vaadin.client.ui.formlayout.FormLayoutConnector.class.getName());
    }

    @Override
    protected LoadStyle getLoadStyle(JClassType connectorType) {
        if (eagerConnectors.contains(connectorType.getQualifiedBinaryName())) {
            return LoadStyle.EAGER;
        } else {
            // Loads all other connectors immediately after the initial view has
            // been rendered
            return LoadStyle.DEFERRED;
        }
    }
}
