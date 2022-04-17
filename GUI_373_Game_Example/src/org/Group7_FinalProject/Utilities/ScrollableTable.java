package org.Group7_FinalProject.Utilities;

import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/*
 * The ScrollableTable class includes all fields and methods related to a ScrollableTable
 */
public final class ScrollableTable extends JScrollPane {

	//Fields for an AccountTable
	private JTable table;
	private DefaultTableModel model;
	
	//Default no-arg constructor
	public ScrollableTable() {
		this(null, null);
	}
	
	//Constructor that requires two arguments
	public ScrollableTable(Object[] head, Object[][] data) {
		this(head, data, 200, 200);
	}
	
	//Constructor that requires four arguments
	public ScrollableTable(Object[] head, Object[][] data, Integer width, Integer height) {
		
		super();
		
		//Create a new regular table
		this.model = new DefaultTableModel();
		this.table = new JTable(this.model);
		//Load the data into the table
		for (int i = 0; i < head.length; i++) {
			this.model.addColumn(head[i].toString());
		}
		for (int i = 0; i < data.length; i++) {
			this.model.addRow(data[i]);
		}
		//Set the table to fill the entire parent element
		this.table.setPreferredScrollableViewportSize(new Dimension(width, height));
		this.table.setFillsViewportHeight(true);
		//Limit the table to one selection at a time, and start with the first element selected
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.table.setRowSelectionInterval(0, 0);
		//Add the table to the JScrollPane. This will make the table scrollable
		this.getViewport().add(this.table);
		
		//The table color is in stark contrast with the rest of the page, but the header can't be colored it seems..
		//this.table.setGridColor(Color.orange);
		//this.table.setBackground(Color.orange);
		//this.table.setFont(new Font("Arial", Font.BOLD, 18));
		//this.table.setTableHeader(null);
	}
	
	//Method that returns the currently selected row on the table
	public Integer getSelectedRow() {
		return this.table.getSelectedRow();
	}
	
	//Method that sets the currently selected row on the table
	public void setSelectedRow(Integer i) {
		this.table.setRowSelectionInterval(i, i);
	}
	
	//Method that adds a row to the table
	public void addRow(Object[] row) {
		this.model.addRow(row);
	}
	
	//Method that removes a row from the table
	public void deleteRow(Integer row) {
		this.model.removeRow(row);
	}
	
	//Method that refreshes the data displayed in the table
	public void refreshData(Object[] head, Object[][] data) {
		emptyData();
		for (int i = 0; i < head.length; i++) {
			this.model.addColumn(head[i].toString());
		}
		for (int i = 0; i < data.length; i++) {
			this.model.addRow(data[i]);
		}
	}
	
	//Method that completely empties the table
	public void emptyData() {
		model.setRowCount(0);
		model.setColumnCount(0);
	}

	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(JTable table) {
		this.table = table;
	}

	/**
	 * @return the model
	 */
	public DefaultTableModel getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(DefaultTableModel model) {
		this.model = model;
	}

}