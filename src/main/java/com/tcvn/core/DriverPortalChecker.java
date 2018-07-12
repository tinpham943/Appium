package com.tcvn.core;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.testng.TestNG;
import org.testng.collections.Lists;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;

/**
 * UI designer for running Driver Portal test cases. 
 * @author DatLe
 */
public class DriverPortalChecker extends CoreTestTemplate {

	Object site = null;
	Object testCases = null;
	private static String ipServer = null;
	private static String ipArea = null;
	private static String driverNo = "";

	private JFrame frmAllRunsChecker;
	private JTextField textFieldDriverName;
	private JTextField textFieldJobCode;
	private JTextField textFieldServer;
	private JTextField textFieldArea;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxDriverNo;

	// List of driver regarding sites selected
	String[] driverListEastAllen = {"", "1", "2", "3", "4", "5", "6", "7", "8"};
	String[] driverListClayton = {"", "1"};

	// Get value of comboBoxDriverNo
	public static String getUIDriverNo() {
		return driverNo;
	}
	// Get value of textFieldIPServer
	public static String getUIIPServer() {
		return ipServer;
	}// Get value of textFieldIPServer
	public static String getUIIPArea() {
		return ipArea;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DriverPortalChecker window = new DriverPortalChecker();
					window.frmAllRunsChecker.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DriverPortalChecker() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frmAllRunsChecker = new JFrame();
		frmAllRunsChecker.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, 
						"Are you sure you want to exit? \n"
						+ "Please send your test cases and feedback to: \n"
						+ "- Public:\\Dat \n"
						+ "- Or email: ledat.job@gmail.com",
						"Exit Confirmation", JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
					frmAllRunsChecker.dispose();
				}
			}
		});
		frmAllRunsChecker.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 18));
		frmAllRunsChecker.setTitle("All Runs Checker");
		frmAllRunsChecker.setBounds(100, 100, 450, 404);
		frmAllRunsChecker.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmAllRunsChecker.getContentPane().setLayout(null);

		JButton btnStart = new JButton("START");
		btnStart.setBounds(153, 276, 109, 41);
		btnStart.setBackground(new Color(60, 179, 113));
		frmAllRunsChecker.getContentPane().add(btnStart);

		String[] siteList = {"","Clayton","East Allen","Henry","Hopewell","Sai Gon"};
		JComboBox comboBoxSite = new JComboBox(siteList);
		comboBoxSite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				site = comboBoxSite.getSelectedItem();
				if(site.equals("East Allen")) {
					comboBoxDriverNo.setModel(new DefaultComboBoxModel(driverListEastAllen));
					textFieldServer.setText("http://113.20.115.84:8999/");
					textFieldArea.setText("apieastallen/");

					if(driverNo.equals("1")) {
						textFieldDriverName.setText("Justin Vaughn");
						textFieldJobCode.setText("Driver");
					} else if(driverNo.equals("2")) {
						textFieldDriverName.setText("Janet McEvoy");
						textFieldJobCode.setText("Driver");
					} else if(driverNo.equals("3")) {
						textFieldDriverName.setText("Amie Murphy");
						textFieldJobCode.setText("Driver");
					} else if(driverNo.equals("4")) {
						textFieldDriverName.setText("Amy Peters");
						textFieldJobCode.setText("Driver");
					} else if(driverNo.equals("5")) {
						textFieldDriverName.setText("Dee Vasquez");
						textFieldJobCode.setText("Driver");
					} else if(driverNo.equals("6")) {
						textFieldDriverName.setText("Larry Gerber");
						textFieldJobCode.setText("Driver");
					} else if(driverNo.equals("7")) {
						textFieldDriverName.setText("Dan Beckman");
						textFieldJobCode.setText("Driver");
					} else if(driverNo.equals("8")) {
						textFieldDriverName.setText("Richard Stroh");
						textFieldJobCode.setText("Driver");
					}

				} else if(site.equals("Clayton")) {
					comboBoxDriverNo.setModel(new DefaultComboBoxModel(driverListClayton));
					textFieldServer.setText("http://113.20.115.84:9898/");
					textFieldArea.setText("apiclayton/");

					if(driverNo.equals("1")) {
						textFieldDriverName.setText("Sylvia Bennett");
						textFieldJobCode.setText("Terminated");
					}
				} else if(site.equals("Henry")) {
					textFieldServer.setText("http://113.20.115.84:8558/");
					textFieldArea.setText("apilive/");

				} else if(site.equals("Hopewell")) {
					textFieldServer.setText("http://113.20.115.84:8778/");
					textFieldArea.setText("apihopewellva1/");

				} else if(site.equals("Sai Gon")) {
					textFieldServer.setText("http://113.20.115.84:6886/");
					textFieldArea.setText("apisaigon/");
				}
			}
		});
		comboBoxSite.setBounds(70, 34, 109, 22);
		frmAllRunsChecker.getContentPane().add(comboBoxSite);

		JLabel lblContact = new JLabel("Contact me via Skype: ledat.phomaique");
		lblContact.setForeground(Color.BLUE);
		lblContact.setBounds(12, 330, 408, 16);
		frmAllRunsChecker.getContentPane().add(lblContact);

		JLabel lblSite = new JLabel("Sites");
		lblSite.setForeground(Color.BLACK);
		lblSite.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSite.setBounds(23, 35, 46, 16);
		frmAllRunsChecker.getContentPane().add(lblSite);

		JLabel lblDriverNo = new JLabel("Driver No.");
		lblDriverNo.setForeground(Color.BLACK);
		lblDriverNo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDriverNo.setBounds(203, 35, 109, 16);
		frmAllRunsChecker.getContentPane().add(lblDriverNo);

		comboBoxDriverNo = new JComboBox();
		comboBoxDriverNo.setEditable(true);
		comboBoxDriverNo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				site = comboBoxSite.getSelectedItem();
				driverNo = comboBoxDriverNo.getSelectedItem().toString();
				if(site.equals("East Allen")) {

					if(driverNo.equals("")) {
						JOptionPane.showMessageDialog(
								null, "Please select a driver to run the tests!"
								, "No Driver Selected", JOptionPane.INFORMATION_MESSAGE);
					} else if(driverNo.equals("1")) {
						textFieldDriverName.setText("Justin Vaughn");
						textFieldJobCode.setText("Driver");
					} else if(driverNo.equals("2")) {
						textFieldDriverName.setText("Janet McEvoy");
						textFieldJobCode.setText("Driver");
					} else if(driverNo.equals("3")) {
						textFieldDriverName.setText("Amie Murphy");
						textFieldJobCode.setText("Driver");
					} else if(driverNo.equals("4")) {
						textFieldDriverName.setText("Amy Peters");
						textFieldJobCode.setText("Driver");
					} else if(driverNo.equals("5")) {
						textFieldDriverName.setText("Dee Vasquez");
						textFieldJobCode.setText("Driver");
					} else if(driverNo.equals("6")) {
						textFieldDriverName.setText("Larry Gerber");
						textFieldJobCode.setText("Driver");
					} else if(driverNo.equals("7")) {
						textFieldDriverName.setText("Dan Beckman");
						textFieldJobCode.setText("Driver");
					} else if(driverNo.equals("8")) {
						textFieldDriverName.setText("Richard Stroh");
						textFieldJobCode.setText("Driver");
					}
				} else if(site.equals("Clayton")) {

					if(driverNo.equals("")) {
						JOptionPane.showMessageDialog(
								null, "Please select a driver to run the tests!"
								, "No Driver Selected", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else if(site.equals("")) {
					JOptionPane.showMessageDialog(
							null, "Please select a site to run the tests!"
							, "No Site Selected", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		comboBoxDriverNo.setBounds(295, 34, 88, 24);
		frmAllRunsChecker.getContentPane().add(comboBoxDriverNo);

		JLabel lblDriverName = new JLabel("Driver Name");
		lblDriverName.setBounds(80, 69, 88, 16);
		frmAllRunsChecker.getContentPane().add(lblDriverName);

		textFieldDriverName = new JTextField();
		textFieldDriverName.setEditable(false);
		textFieldDriverName.setBounds(43, 86, 162, 22);
		frmAllRunsChecker.getContentPane().add(textFieldDriverName);
		textFieldDriverName.setColumns(10);

		JLabel lblJobCode = new JLabel("Job Code");
		lblJobCode.setBounds(270, 69, 70, 16);
		frmAllRunsChecker.getContentPane().add(lblJobCode);

		textFieldJobCode = new JTextField();
		textFieldJobCode.setEditable(false);
		textFieldJobCode.setBounds(233, 86, 133, 22);
		frmAllRunsChecker.getContentPane().add(textFieldJobCode);
		textFieldJobCode.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		panel.setBounds(23, 64, 377, 57);
		frmAllRunsChecker.getContentPane().add(panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(23, 134, 377, 88);
		frmAllRunsChecker.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblApi = new JLabel("Server");
		lblApi.setBounds(12, 12, 76, 16);
		panel_1.add(lblApi);

		textFieldServer = new JTextField();
		textFieldServer.setEditable(false);
		textFieldServer.setBounds(69, 9, 296, 22);
		panel_1.add(textFieldServer);
		textFieldServer.setColumns(10);

		JLabel lblArea = new JLabel("Area");
		lblArea.setBounds(12, 44, 45, 16);
		panel_1.add(lblArea);

		textFieldArea = new JTextField();
		textFieldArea.setEditable(false);
		textFieldArea.setColumns(10);
		textFieldArea.setBounds(69, 41, 296, 22);
		panel_1.add(textFieldArea);

		JLabel lblTestCases = new JLabel("Test Cases");
		lblTestCases.setForeground(Color.BLACK);
		lblTestCases.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTestCases.setBounds(33, 235, 96, 16);
		frmAllRunsChecker.getContentPane().add(lblTestCases);

		JComboBox comboBoxTestCases = new JComboBox();
		Object[] testCasesList = new Object[] {"", "All Runs Crashing Test", "Assigned Route Test", "Login Test", "eDTA Test"};
		AutoCompleteSupport.install(comboBoxTestCases, GlazedLists.eventListOf(testCasesList));
		comboBoxTestCases.setEditable(true);
		comboBoxTestCases.setBounds(125, 234, 258, 22);
		frmAllRunsChecker.getContentPane().add(comboBoxTestCases);

		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					TestNG testng = new TestNG();
					List<String> suites = Lists.newArrayList();
					site = comboBoxSite.getSelectedItem();
					driverNo = comboBoxDriverNo.getSelectedItem().toString();
					testCases = comboBoxTestCases.getSelectedItem();
					ipServer = textFieldServer.getText();
					ipArea = textFieldArea.getText();
					if((!site.equals("")) && driverNo.length() > 0
							&& (testCases.equals("All Runs Crashing Test"))) {
						suites.add(System.getProperty("user.dir")+"/AllRunTest_InputAPI.xml");
						testng.setTestSuites(suites);
						testng.run();

					} else if((!site.equals("")) && driverNo.length() > 0
							&& (testCases.equals("Assigned Route Test"))) {
						suites.add(System.getProperty("user.dir")+"/AssignedRouteTest.xml");
						testng.setTestSuites(suites);
						testng.run();
					}
					else if((!site.equals("")) && driverNo.length() > 0
							&& (testCases.equals("eDTA Test"))) {
						suites.add(System.getProperty("user.dir")+"/EDTATest.xml");
						testng.setTestSuites(suites);
						testng.run();
					}
					else if(!site.equals("") && (testCases.equals("Login Test"))) {
						suites.add(System.getProperty("user.dir")+"/LoginTest.xml");
						testng.setTestSuites(suites);
						testng.run();
					}
					else if(driverNo.length() == 0) {
						JOptionPane.showMessageDialog(
								null, "Please select a driver to run the tests!"
								, "No Driver Selected", JOptionPane.INFORMATION_MESSAGE);
					} else if(site.equals("")) {
						JOptionPane.showMessageDialog(
								null, "Please select a site to run the tests!"
								, "No Site Selected", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(
								null, "Not enough data provided to run the tests!"
								, "No Data to Run The Test Case", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
}
