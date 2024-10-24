package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import task.Task;

public class ToDoListGUI extends JFrame {
	
    private DefaultListModel<Task> taskListModel;
    private JList<Task> taskList;
    private JTextField taskInputField;

    public ToDoListGUI() {
    	
        setTitle("To-Do List");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Task list model
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Scroll pane for the task list
        JScrollPane taskScrollPane = new JScrollPane(taskList);

        // Task input field
        taskInputField = new JTextField(20);

        // Buttons
        JButton addButton = new JButton("Add Task");
        JButton editButton = new JButton("Edit Task");
        JButton deleteButton = new JButton("Delete Task");
        JButton markCompletedButton = new JButton("Mark as Completed");
        JButton markNotCompletedButton = new JButton("Mark as not Completed");

        // Add button listener
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String taskName = taskInputField.getText().trim();
                if (!taskName.isEmpty()) {
                    Task task = new Task(taskName);
                    taskListModel.addElement(task);
                    taskInputField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Task name cannot be empty.");
                }
            }
        });

        // Edit button listener
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String newTaskName = JOptionPane.showInputDialog("Edit Task:", taskListModel.get(selectedIndex).getName());
                    if (newTaskName != null && !newTaskName.trim().isEmpty()) {
                        taskListModel.get(selectedIndex).setName(newTaskName);
                        taskList.repaint();
                    } else {
                        JOptionPane.showMessageDialog(null, "Task name cannot be empty.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a task to edit.");
                }
            }
        });

        // Delete button listener
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    taskListModel.remove(selectedIndex);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a task to delete.");
                }
            }
        });

        // Mark completed button listener
        markCompletedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Task task = taskListModel.get(selectedIndex);
                    task.setCompleted(true);
                    taskList.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a task to mark as completed.");
                }
            }
        });
        
		// Mark not completed button listener
		markNotCompletedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = taskList.getSelectedIndex();
				if (selectedIndex != -1) {
					Task task = taskListModel.get(selectedIndex);
	                task.setCompleted(false);
	                taskList.repaint();
	            } else {
	            	JOptionPane.showMessageDialog(null, "Please select a task to mark as NOT completed.");
	            }
			}
		});

        // Layout setup
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Task:"));
        inputPanel.add(taskInputField);
        inputPanel.add(addButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2));
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(markCompletedButton);
        buttonPanel.add(markNotCompletedButton);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(inputPanel, BorderLayout.NORTH);
        container.add(taskScrollPane, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ToDoListGUI().setVisible(true);
        });
    }
}
