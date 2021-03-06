package mygui;

import curriculum.Teacher;
import curriculum.Course;
import curriculum.Storage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ProfInfo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txt_name;
	private JTextField txt_tel;
	private JTextField txt_email;
	private JTextField txt_office;
	private JTextField txt_crs;
	
	private Teacher teacher; 

	/**
	 * Create the dialog.
	 */
	public ProfInfo(Teacher current_teacher, Course course) {
		if (current_teacher!=null) teacher=current_teacher;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		contentPanel.setVisible(true);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		{
			JLabel lb_name = new JLabel("姓名");
			lb_name.setBounds(23, 10, 54, 15);
			contentPanel.add(lb_name);
		}
		{
			JLabel lb_tel = new JLabel("Tel");
			lb_tel.setBounds(23, 45, 54, 15);
			contentPanel.add(lb_tel);
		}
		{
			JLabel lb_email = new JLabel("Email");
			lb_email.setBounds(23, 78, 54, 15);
			contentPanel.add(lb_email);
		}
		{
			JLabel lb_office = new JLabel("办公室");
			lb_office.setBounds(23, 122, 54, 15);
			contentPanel.add(lb_office);
		}
		{
			txt_name = new JTextField();
			txt_name.setBounds(63, 7, 87, 21);
			if (current_teacher!=null) txt_name.setText(current_teacher.getName());
			contentPanel.add(txt_name);
			txt_name.setColumns(10);
		}
		{
			txt_tel = new JTextField();
			txt_tel.setBounds(63, 42, 178, 21);
			if (current_teacher!=null) txt_tel.setText(current_teacher.getTel());
			contentPanel.add(txt_tel);
			txt_tel.setColumns(10);
		}
		{
			txt_email = new JTextField();
			txt_email.setBounds(63, 75, 178, 21);
			if (current_teacher!=null) txt_email.setText(current_teacher.getMail());
			contentPanel.add(txt_email);
			txt_email.setColumns(10);
		}
		{
			txt_office = new JTextField();
			txt_office.setBounds(63, 119, 321, 21);
			if (current_teacher!=null) txt_office.setText(current_teacher.getOffice());
			contentPanel.add(txt_office);
			txt_office.setColumns(10);
		}
		{
			JLabel lb_crs = new JLabel("课程");
			lb_crs.setBounds(187, 10, 54, 15);
			contentPanel.add(lb_crs);
		}
		{
			txt_crs = new JTextField();
			txt_crs.setEditable(false);
			txt_crs.setText(course.getName());
			txt_crs.setBounds(224, 7, 66, 21);
			contentPanel.add(txt_crs);
			txt_crs.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						
						//////////revise//////////////
						if (current_teacher==null) { //add new teacher
							String databaseFile = "data.sqlite";
					        Storage db = new Storage("jdbc:sqlite:" + databaseFile, !new File(databaseFile).exists());
							teacher = db.addNewTeacher(UUID.randomUUID().toString(), txt_name.getText(), 
									txt_email.getText(), txt_tel.getText(), txt_office.getText());
							course.addTeacher(teacher);
						}
						else { //edit teacher
							teacher.setName(txt_name.getText());
							teacher.setMail(txt_email.getText());
							teacher.setTel(txt_tel.getText());
							teacher.setOffice(txt_office.getText());
						}
						
						ProfInfo.this.dispose();
					} 
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton); 
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						
						//////////cancel//////////////
						ProfInfo.this.dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
}
