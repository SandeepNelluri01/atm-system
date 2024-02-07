package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener{
    
    JButton deposit,withdrawl,ministatement,pinchange,fastcash,balanceenquiry,exit;
    String pinnumber;
    
        FastCash(String pinnumber){
            this.pinnumber = pinnumber;
            setLayout(null);
            
            ImageIcon i1 = new  ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
            Image i2 = i1.getImage().getScaledInstance(900, 700,Image.SCALE_DEFAULT );
            ImageIcon i3 = new ImageIcon(i2);
            JLabel image = new JLabel(i3);
            image.setBounds(0,0,900,700);
            add(image);
            
            JLabel text = new JLabel("SELECT WITHDRAWL AMOUNT");
            text.setBounds(180,220,700,30);
            text.setForeground(Color.WHITE);
            text.setFont(new Font("System",Font.BOLD,14));
            image.add(text);
            
            deposit = new JButton("RS 100");
            deposit.setBounds(160,324,140,22);
            deposit.addActionListener(this);
            image.add(deposit);
            
            withdrawl = new JButton("RS 500");
            withdrawl.setBounds(160,351,140,22);
            withdrawl.addActionListener(this);
            image.add(withdrawl);
            
            fastcash = new JButton("RS 1000");
            fastcash.setBounds(370,325,140,22);
            fastcash.addActionListener(this);
            image.add(fastcash);
            
            ministatement = new JButton("RS 2000");
            ministatement.setBounds(160,379,140,22);
            ministatement.addActionListener(this);
            image.add(ministatement);
            
            pinchange = new JButton("RS 5000");
            pinchange.setBounds(370,351,140,22);
            pinchange.addActionListener(this);
            image.add(pinchange);
            
            balanceenquiry = new JButton("RS 10000");
            balanceenquiry.setBounds(370,378,140,22);
            balanceenquiry.addActionListener(this);
            image.add(balanceenquiry);
            
            exit = new JButton("BACK");
            exit.setBounds(370,405,140,22);
            exit.addActionListener(this);
            image.add(exit);
            
            setSize(900,900);
            setLocation(300,0);
            setVisible(true);
            
        }
        
        public void actionPerformed(ActionEvent ae){
         if(ae.getSource() == exit){
             setVisible(false);
             new Transactions(pinnumber).setVisible(true);
         }else {
             
         String amount = ((JButton)ae.getSource()).getText().substring(3);
         
         Conn c = new Conn();
             try {
                 ResultSet rs = c.s.executeQuery("select * from bank where pin = '"+pinnumber+"'");
                 int balance =0;
                 while(rs.next()){
                     if(rs.getString("type").equals("Deposit")){
                         balance += Integer.parseInt(rs.getString("amount"));
                     }else{
                         balance -= Integer.parseInt(rs.getString("amount"));
                     }
                 }
                 
                 if(ae.getSource()!= exit && balance < Integer.parseInt(amount)){
                     JOptionPane.showMessageDialog(null, "Insufficient Balance");
                     return;
                 }
                 
                 Date date = new Date();
                 String query = "insert into bank values('"+pinnumber+"','"+date+"','Withdrawl','"+amount+"')";
                 c.s.executeUpdate(query);
                 JOptionPane.showMessageDialog(null, "Rs "+ amount + " Debited Successfully");
                 
                 setVisible(false);
                 new Transactions(pinnumber).setVisible(true);
                 
             } catch (Exception e) {
                 System.out.println(e);
             }
             
         }
      }
        
    public static void main(String[] args){
        new FastCash("");
    }
}
