/*
 * Admin Toolbox GUI class.
 */
package admintool;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;

/**
 *
 * @author Nicholas Eyring
 */
public class Tool extends javax.swing.JFrame {

    private String language;
    private DoubleMap englishMap;
    private DoubleMap transMap;
    private Integer counter;
    private int maxCounter;
    private HashMap<Integer,String> keys;
    private String[] suggestions;
    private String code;
    
    /**
     * Creates new form Tool
     */
    public Tool() {
        initComponents();
        setTitle("Admin Tool");
        setLocationRelativeTo(null); // display in center screen
        setResizable(false);    
        
        jTextField13.setEditable(false);
        jTextField14.setEditable(false);
        jTextField15.setEditable(false);
        
        
        // "Done" button
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        
        // "Get Suggestions" button
        jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                language = jComboBox1.getSelectedItem().toString();
                
                englishMap = DLbot.getTranslations("English");
                transMap = DLbot.getTranslations(language);
                
                maxCounter = transMap.KeytoValue.size()-1;
                counter = maxCounter; // initialize counter
                
                // "Items Remaining" text field
                jTextField13.setText(counter.toString());
                
                keys = new HashMap<Integer,String>(); // create the keys HashMap
                
                int i = counter;
        
                // initialize keys HashMap
                for (String key : englishMap.KeytoValue.keySet()) {
                    keys.put(i, key);
                    i = i - 1;
                }
                
                // "Original English" text field
                jTextField14.setText(englishMap.KeytoValue.get(keys.get(counter)));
        
                // "Current Translation" text field
                jTextField15.setText(transMap.KeytoValue.get(keys.get(counter)));
                
                suggestions = DLbot.removeSuggestion(language, keys.get(counter), "");
                
                code = DLbot.getWikiCode(); // update wiki code
                
                jList2.setModel(new javax.swing.AbstractListModel() {
                    String[] strings = suggestions;
                    public int getSize() { return strings.length; }
                    public Object getElementAt(int i) { return strings[i]; }
                });
            }
        });
        
        
        // "Validate" button
        jButton1.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               String validatedSuggestion = jList2.getSelectedValue().toString();
               DLbot.validateTranslation(language, keys.get(counter), validatedSuggestion);
               suggestions = DLbot.removeSuggestion(language, keys.get(counter), validatedSuggestion); // remove the validated suggestion
               
               code = DLbot.getWikiCode(); // update wiki code
               
               // Make the change in memory
               String oldValue = transMap.KeytoValue.get(keys.get(counter));
               transMap.KeytoValue.remove(keys.get(counter));
               transMap.KeytoValue.put(keys.get(counter), validatedSuggestion);
               transMap.ValuetoKey.remove(oldValue);
               transMap.ValuetoKey.put(validatedSuggestion, keys.get(counter));
        
                // "Current Translation" text field
                jTextField15.setText(transMap.KeytoValue.get(keys.get(counter)));
                
                jList2.setModel(new javax.swing.AbstractListModel() {
                    String[] strings = suggestions;
                    public int getSize() { return strings.length; }
                    public Object getElementAt(int i) { return strings[i]; }
                });
           } 
        });
        
        // "Next Item" button
        jButton12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (counter > 0) {
                    counter = counter - 1; // decrement counter
                    // "Items Remaining" text field
                    jTextField13.setText(counter.toString());
                    // "Original English" text field
                    jTextField14.setText(englishMap.KeytoValue.get(keys.get(counter)));
                    // "Current Translation" text field
                    jTextField15.setText(transMap.KeytoValue.get(keys.get(counter)));
                    
                    suggestions = getSuggestions(code); // get suggestions for next key
                    
                    jList2.setModel(new javax.swing.AbstractListModel() {
                        String[] strings = suggestions;
                        public int getSize() { return strings.length; }
                        public Object getElementAt(int i) { return strings[i]; }
                    });
                }                
            }
        });
        
        // "Previous Item" button
        jButton13.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if (counter < maxCounter) {
                   counter = counter + 1; // increment counter
                   // "Items Remaining" text field
                   jTextField13.setText(counter.toString());
                   // "Original English" text field
                   jTextField14.setText(englishMap.KeytoValue.get(keys.get(counter)));
                   // "Current Translation" text field
                   jTextField15.setText(transMap.KeytoValue.get(keys.get(counter)));
                   
                   suggestions = getSuggestions(code); // get suggestions for previous key
                
                   jList2.setModel(new javax.swing.AbstractListModel() {
                       String[] strings = suggestions;
                       public int getSize() { return strings.length; }
                       public Object getElementAt(int i) { return strings[i]; }
                   });
               }              
           } 
        });
        
        // "Remove" button
        jButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               String toRemove = jList2.getSelectedValue().toString();
               suggestions = DLbot.removeSuggestion(language, keys.get(counter), toRemove); // remove selected suggestion
               
               code = DLbot.getWikiCode(); // update wiki code
               
               jList2.setModel(new javax.swing.AbstractListModel() {
                    String[] strings = suggestions;
                    public int getSize() { return strings.length; }
                    public Object getElementAt(int i) { return strings[i]; }
                });
            }
        });
        
        
    }
    
    /*
     * Returns a String array of current suggestions for the current key (mapped through counter)
     * given the wiki Code of the suggestions page.
     */
    private String[] getSuggestions(String code) {
        String[] split = code.split(keys.get(counter) + " =");
        String[] lines = split[1].split("\n");
        String[] suggestions = new String[100]; // maximum 100 suggestions
        // iterate through lines
        for (int j=1; j<lines.length; j++) {
            if(!lines[j].contains("end-") && j<=100) {
                   suggestions[j-1] = lines[j];
            } else {
               break;
            }
        }
        return suggestions;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Please choose a language:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Deutsch", "Français", "Italiano" }));

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Items Remaining:");

        jLabel14.setText("Original English:");

        jLabel15.setText("Current Translation:");

        jButton12.setText("Next Item");

        jButton13.setText("Previous Item");

        jLabel5.setText("Please validate a suggestion:");

        jButton1.setText("Validate");

        jScrollPane1.setViewportView(jList2);

        jButton4.setText("Remove");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 61, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jButton13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton12)
                                .addGap(76, 76, 76))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addContainerGap())))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(jSeparator11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator9)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField14)
                                    .addComponent(jTextField15)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel5))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton12, jButton13});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton4});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton4))
                .addGap(11, 11, 11)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(jButton13))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Comprehensive", jPanel5);

        jButton2.setText("Done");

        jButton3.setText("Get Suggestions");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Tool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tool().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    // End of variables declaration//GEN-END:variables
}
