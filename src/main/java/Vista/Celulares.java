
package Vista;


public class Celulares extends javax.swing.JFrame {

  
    public Celulares() {
        initComponents();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        TextCantidadCatal = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TextPrecioCatal = new javax.swing.JTextField();
        TextMarcaCatal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        BoxAlmacenCatal = new javax.swing.JComboBox<>();
        BoxRamCatal = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        BoxColorCatal = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        RadioButtSim1Catal = new javax.swing.JRadioButton();
        RadioButtSim2Catal = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        TextNombreCatal = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableCatalogo = new javax.swing.JTable();
        ButtonComprarCatal = new javax.swing.JButton();
        ButtonBuscarCatal = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        ButtonRegresoIni = new javax.swing.JButton();
        ButtonRegresoUsu = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(228, 228, 218));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setText("Cantidad");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));
        jPanel1.add(TextCantidadCatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 190, 30));

        jLabel2.setText("Nombre del Celular");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        jLabel3.setText("Marca");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        jLabel4.setText("Precio");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, -1));

        TextPrecioCatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextPrecioCatalActionPerformed(evt);
            }
        });
        jPanel1.add(TextPrecioCatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 190, 30));
        jPanel1.add(TextMarcaCatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 190, 30));

        jLabel5.setText("Ram");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, -1, -1));

        BoxAlmacenCatal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "32GB", "64GB", "128GB", "256GB" }));
        jPanel1.add(BoxAlmacenCatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, 190, 30));

        BoxRamCatal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1GB", "2GB", "4GB", "8GB", "12GB" }));
        jPanel1.add(BoxRamCatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 190, 30));

        jLabel8.setText("Almacenamiento");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, -1, -1));

        BoxColorCatal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Negro", "Blanco", "Gris", "Dorado" }));
        BoxColorCatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoxColorCatalActionPerformed(evt);
            }
        });
        jPanel1.add(BoxColorCatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 550, 190, 30));

        jLabel9.setText("Color");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, -1, -1));

        buttonGroup1.add(RadioButtSim1Catal);
        RadioButtSim1Catal.setText("Sim 1");
        jPanel1.add(RadioButtSim1Catal, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 620, -1, -1));

        buttonGroup1.add(RadioButtSim2Catal);
        RadioButtSim2Catal.setText("Sim 2");
        jPanel1.add(RadioButtSim2Catal, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 620, -1, -1));

        jLabel10.setText("Sim");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 590, -1, -1));

        TextNombreCatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextNombreCatalActionPerformed(evt);
            }
        });
        jPanel1.add(TextNombreCatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 190, 30));

        TableCatalogo.setBackground(new java.awt.Color(228, 228, 218));
        TableCatalogo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Marca", "Precio", "Ram", "Almacenamiento", "Color", "Sim", "Cantidad"
            }
        ));
        jScrollPane1.setViewportView(TableCatalogo);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 230, 640, 550));

        ButtonComprarCatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/carrito-de-compras.png"))); // NOI18N
        ButtonComprarCatal.setText("Comprar");
        ButtonComprarCatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonComprarCatalActionPerformed(evt);
            }
        });
        jPanel1.add(ButtonComprarCatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 660, 120, 50));

        ButtonBuscarCatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        ButtonBuscarCatal.setText("Buscar");
        ButtonBuscarCatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonBuscarCatalActionPerformed(evt);
            }
        });
        jPanel1.add(ButtonBuscarCatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 730, 110, 50));

        jLabel7.setFont(new java.awt.Font("Vivaldi", 0, 74)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/animal-gato.png"))); // NOI18N
        jLabel7.setText("  Catalogo");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 310, 100));

        ButtonRegresoIni.setBackground(new java.awt.Color(228, 228, 218));
        ButtonRegresoIni.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/flecha-pequena-izquierda (1).png"))); // NOI18N
        ButtonRegresoIni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRegresoIniActionPerformed(evt);
            }
        });
        jPanel1.add(ButtonRegresoIni, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        ButtonRegresoUsu.setBackground(new java.awt.Color(228, 228, 218));
        ButtonRegresoUsu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/hogar.png"))); // NOI18N
        ButtonRegresoUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRegresoUsuActionPerformed(evt);
            }
        });
        jPanel1.add(ButtonRegresoUsu, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/download (3).jpeg"))); // NOI18N
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, 300));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonComprarCatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonComprarCatalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonComprarCatalActionPerformed

    private void ButtonBuscarCatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonBuscarCatalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonBuscarCatalActionPerformed

    private void ButtonRegresoIniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRegresoIniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonRegresoIniActionPerformed

    private void ButtonRegresoUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRegresoUsuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonRegresoUsuActionPerformed

    private void TextPrecioCatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextPrecioCatalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextPrecioCatalActionPerformed

    private void BoxColorCatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoxColorCatalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BoxColorCatalActionPerformed

    private void TextNombreCatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextNombreCatalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextNombreCatalActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Celulares.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Celulares.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Celulares.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Celulares.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Celulares().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JComboBox<String> BoxAlmacenCatal;
    public javax.swing.JComboBox<String> BoxColorCatal;
    public javax.swing.JComboBox<String> BoxRamCatal;
    public javax.swing.JButton ButtonBuscarCatal;
    public javax.swing.JButton ButtonComprarCatal;
    public javax.swing.JButton ButtonRegresoIni;
    public javax.swing.JButton ButtonRegresoUsu;
    public javax.swing.JRadioButton RadioButtSim1Catal;
    public javax.swing.JRadioButton RadioButtSim2Catal;
    public javax.swing.JTable TableCatalogo;
    public javax.swing.JTextField TextCantidadCatal;
    public javax.swing.JTextField TextMarcaCatal;
    public javax.swing.JTextField TextNombreCatal;
    public javax.swing.JTextField TextPrecioCatal;
    public javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
