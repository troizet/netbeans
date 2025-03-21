/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.lsp.client.debugger.attach;

import java.awt.Component;
import java.util.Map;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.netbeans.api.debugger.Properties;
import org.netbeans.modules.lsp.client.debugger.DAPDebugger.Type;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

public class DAPAttachPanel extends javax.swing.JPanel {

    @Messages({
        "DN_Attach=Attach",
        "DN_Launch=Launch"
    })
    private static final Map<Type, String> connectionType2DisplayName = Map.of (
            Type.ATTACH, Bundle.DN_Attach(),
            Type.LAUNCH, Bundle.DN_Launch()
    );

    private static final String NODE = "attach";
    private static final String KEY_HOSTNAME = "hostname";
    private static final String KEY_PORT = "port";
    private static final String KEY_CONNECTION_TYPE = "type";
    private static final String KEY_CONFIGURATION = "configuration";
    private static final String KEY_DELAY = "delay";
    private static final String DEFAULT_HOSTNAME = "localhost";
    private static final String DEFAULT_PORT = "";
    private static final String DEFAULT_CONNECTION_TYPE = "ATTACH";
    private static final String DEFAULT_CONFIGURATION = "";
    private static final boolean DEFAULT_DELAY = false;

    /**
     * Creates new form DAPAttachPanel
     */
    public DAPAttachPanel() {
        initComponents();
    }

    public void load(Properties prefs) {
        hostname.setText(prefs.getString(KEY_HOSTNAME, DEFAULT_HOSTNAME));
        port.setText(prefs.getString(KEY_PORT, DEFAULT_PORT));
        try {
            connectionType.setSelectedItem(Type.valueOf(prefs.getString(KEY_CONNECTION_TYPE, DEFAULT_CONNECTION_TYPE)));
        } catch (IllegalArgumentException ex) {
            connectionType.setSelectedItem(Type.ATTACH);
        }
        jsonConfiguration.setText(prefs.getString(KEY_CONFIGURATION, DEFAULT_CONFIGURATION));
        delay.setSelected(prefs.getBoolean(KEY_DELAY, DEFAULT_DELAY));
    }

    public void save(Properties prefs) {
        prefs.setString(KEY_HOSTNAME, getHostName());
        prefs.setString(KEY_PORT, port.getText());
        prefs.setString(KEY_CONNECTION_TYPE, getConnectionType().name());
        prefs.setString(KEY_CONFIGURATION, getJSONConfiguration());
        prefs.setBoolean(KEY_DELAY, getDelay());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jsonConfiguration = new javax.swing.JEditorPane();
        delay = new javax.swing.JCheckBox();
        hostname = new javax.swing.JTextField();
        port = new javax.swing.JTextField();
        connectionType = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(DAPAttachPanel.class, "DAPAttachPanel.jLabel4.text")); // NOI18N

        jLabel1.setLabelFor(hostname);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(DAPAttachPanel.class, "DAPAttachPanel.jLabel1.text")); // NOI18N

        jLabel2.setLabelFor(port);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(DAPAttachPanel.class, "DAPAttachPanel.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(DAPAttachPanel.class, "DAPAttachPanel.jLabel3.text")); // NOI18N

        jLabel5.setLabelFor(jsonConfiguration);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(DAPAttachPanel.class, "DAPAttachPanel.jLabel5.text")); // NOI18N

        jsonConfiguration.setContentType("application/json"); // NOI18N
        jScrollPane1.setViewportView(jsonConfiguration);

        org.openide.awt.Mnemonics.setLocalizedText(delay, org.openide.util.NbBundle.getMessage(DAPAttachPanel.class, "DAPAttachPanel.delay.text")); // NOI18N

        hostname.setText(org.openide.util.NbBundle.getMessage(DAPAttachPanel.class, "DAPAttachPanel.hostname.text")); // NOI18N

        port.setText(org.openide.util.NbBundle.getMessage(DAPAttachPanel.class, "DAPAttachPanel.port.text")); // NOI18N

        connectionType.setModel(getConnectionTypeModel());
        connectionType.setRenderer(getConnectionTypeRenderer());

        jLabel6.setFont(new java.awt.Font("sansserif", 2, 13)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(DAPAttachPanel.class, "DAPAttachPanel.jLabel6.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hostname)
                            .addComponent(port)
                            .addComponent(connectionType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(delay)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(hostname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(connectionType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(delay)
                .addGap(10, 10, 10))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Type> connectionType;
    private javax.swing.JCheckBox delay;
    private javax.swing.JTextField hostname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JEditorPane jsonConfiguration;
    private javax.swing.JTextField port;
    // End of variables declaration//GEN-END:variables

    private ComboBoxModel<Type> getConnectionTypeModel() {
        DefaultComboBoxModel<Type> result = new DefaultComboBoxModel<>();

        result.addElement(Type.ATTACH);
        result.addElement(Type.LAUNCH);
        return result;
    }

    private ListCellRenderer<? super Type> getConnectionTypeRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Type type) {
                    value = connectionType2DisplayName.get(type);
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        };
    }

    public String getHostName() {
        return hostname.getText();
    }

    public int getPort() {
        try {
            return Integer.parseInt(port.getText());
        } catch (NumberFormatException ex) {
            Exceptions.printStackTrace(ex);
            return -1;
        }
    }

    public Type getConnectionType() {
        return (Type) connectionType.getSelectedItem();
    }

    public String getJSONConfiguration() {
        return jsonConfiguration.getText();
    }

    public boolean getDelay() {
        return delay.isSelected();
    }
}
