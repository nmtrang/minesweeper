package GUI;
import javax.swing.*;

import minesweeper.Minesweeper;

public class Input extends javax.swing.JFrame {
    private final Minesweeper iMinesweeper;
    private int size = 0;
    private int bom = 0;
    /**
     * Creates new form Input
     */
    public Input(Minesweeper minesweeper) {
        this.iMinesweeper = minesweeper;
        initComponents();
    }


    public void setSize(int size) {
        this.size = size;
    }

    public void setBom(int bom) {
        this.bom = bom;
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Button_8x8 = new javax.swing.JButton();
        Button_16x16 = new javax.swing.JButton();
        Button_Custom = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        setIconImages(null);
        setPreferredSize(new java.awt.Dimension(600, 600));
        getContentPane().setLayout(null);

        Button_16x16.setIcon(new javax.swing.ImageIcon("src/resources/img/16x16.jpg")); // NOI18N
        Button_16x16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_16x16ActionPerformed(evt);
            }
        });
        getContentPane().add(Button_16x16);
        Button_16x16.setBounds(30, 321, 220, 70);

        Button_8x8.setIcon(new javax.swing.ImageIcon("src/resources/img/8x8.jpg")); // NOI18N
        Button_8x8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_8x8ActionPerformed(evt);
            }
        });
        getContentPane().add(Button_8x8);
        Button_8x8.setBounds(350, 321, 220, 70);

        Button_Custom.setIcon(new javax.swing.ImageIcon("src/resources/img/Custom.jpg")); // NOI18N
        Button_Custom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_CustomActionPerformed(evt);
            }
        });
        getContentPane().add(Button_Custom);
        Button_Custom.setBounds(190, 450, 220, 70);

        jLabel1.setIcon(new javax.swing.ImageIcon("src/resources/img/Background/Final_BG.jpg")); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 600, 600);

        pack();
        setLocationRelativeTo(null);

    }// </editor-fold>

    private void Button_8x8ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        size = 8;
        bom = 10;
        set();
    }

    private void Button_16x16ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        size = 16;
        bom = 15;
        set();
    }

    private void Button_CustomActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        CustomMap custommap = new CustomMap(iMinesweeper);
        custommap.setVisible(true);
        dispose();
    }
    public void main(Input input) {
        this.setVisible(true);
    }
    public void set(){
        iMinesweeper.proceed(size,bom);
        dispose();
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton Button_8x8;
    private javax.swing.JButton Button_16x16;
    private javax.swing.JButton Button_Custom;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration                   
}
