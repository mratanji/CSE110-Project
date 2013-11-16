package com.group8.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTable.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
 
public class boardDisplay extends JPanel
{
   
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    private String[] columnNames = {"First Name", "Room #", "Terminal #", "Comments"};
    private DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    private JTable table = new JTable(model) {
    	public boolean isCellEditable(int row, int column) 
    	{
    			return false; // This causes all cells to be not editable
    	}
    };

		   
	public boardDisplay()
	{
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane);
		
		add(panel);
	}
 
	public void addRow(String firstName, String roomNumber, String terminalNumber, String comments) {
		Object [] row = new Object [5];
	    row[0] = firstName;
	    row[1] = roomNumber;
	    row[2] = terminalNumber;
	    row[3] = comments;
	    model.addRow(row);
	}
 
	public void deleteRow() {
		int[] rows = table.getSelectedRows();
		for(int i=0; i < rows.length; i++) {
			model.removeRow(rows[i]-i);
		}
	}
	
    public void editData(String[] values) {
    	for (int i = 0 ; i < values.length ; i++) {
    		table.getModel().setValueAt(values[i], table.getRowCount()-1,i);
        }
    } 
}