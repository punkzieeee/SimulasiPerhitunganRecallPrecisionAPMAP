import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.util.*;
import java.awt.ComponentOrientation;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import java.awt.Scrollbar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class GUIHitung extends JFrame {

	private JPanel panelBawah;
	private JTextField inputAngka;
	private JTextArea hasilTeks;
	private JLabel labelTop5;
	private JTextArea hasilSearch;
	private JLabel labelRecall;
	private JTextField hasilRecall1;
	private JTextField hasilRecall2;
	private JLabel labelPrecision;
	private JTextField hasilPrecision1;
	private JLabel labelAP;
	private JTextField hasilAP;
	private JLabel labelMAP;
	private JTextField hasilMAP;
	private XMLReader xml = new XMLReader();
	private SearchFile sf = new SearchFile();
	private JScrollPane scrollTeks;
	private JScrollPane scrollSearch;
	private int angka;
	private int count = 0;
	private JTextField[][] hasilArr = new JTextField[3][5];
	private JTextField hasilRecall3;
	private JTextField hasilRecall4;
	private JTextField hasilRecall5;
	private JTextField hasilPrecision2;
	private JTextField hasilPrecision3;
	private JTextField hasilPrecision4;
	private JTextField hasilPrecision5;
	private JLabel labelQuery;
	private JLabel ke_n;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIHitung frame = new GUIHitung();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIHitung() {
		setMinimumSize(new Dimension(650, 500));
		setSize(new Dimension(650, 500));
		setTitle("Simulasi Perhitungan Recall, Precision, AP, MAP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		panelBawah = new JPanel();
		setContentPane(panelBawah);
		panelBawah.setLayout(null);
		
		JLabel labelAngka = new JLabel("Masukkan angka (1-63):");
		labelAngka.setBounds(10, 7, 149, 15);
		labelAngka.setHorizontalTextPosition(SwingConstants.LEFT);
		labelAngka.setHorizontalAlignment(SwingConstants.LEFT);
		labelAngka.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(labelAngka);
		
		inputAngka = new JTextField();
		inputAngka.setBounds(169, 5, 46, 20);
		getContentPane().add(inputAngka);
		inputAngka.setColumns(3);
		
		JLabel labelTeks = new JLabel("Teks:");
		labelTeks.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelTeks.setBounds(10, 33, 46, 14);
		panelBawah.add(labelTeks);
		
		hasilTeks = new JTextArea();
		hasilTeks.setRows(2);
		hasilTeks.setFont(new Font("Times New Roman", hasilTeks.getFont().getStyle(), hasilTeks.getFont().getSize()));
		hasilTeks.setBackground(Color.WHITE);
		hasilTeks.setEditable(false);
		hasilTeks.setDisabledTextColor(Color.BLACK);
		hasilTeks.setBounds(10, 58, 464, 50);
		hasilTeks.setColumns(10);
		
		scrollTeks = new JScrollPane(hasilTeks);
		scrollTeks.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollTeks.setBounds(10, 53, 614, 55);
		panelBawah.add(scrollTeks);
		
		labelTop5 = new JLabel("Top 5 Hasil Pencarian:");
		labelTop5.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelTop5.setBounds(10, 119, 141, 14);
		panelBawah.add(labelTop5);
		
		hasilSearch = new JTextArea();
		hasilSearch.setRows(5);
		hasilSearch.setBackground(Color.WHITE);
		hasilSearch.setDisabledTextColor(Color.BLACK);
		hasilSearch.setEditable(false);
		hasilSearch.setBounds(10, 144, 464, 104);
		hasilSearch.setColumns(10);
		
		scrollSearch = new JScrollPane(hasilSearch);
		scrollSearch.setBounds(10, 144, 614, 104);
		panelBawah.add(scrollSearch);
		
		labelRecall = new JLabel("Recall");
		labelRecall.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelRecall.setBounds(10, 259, 69, 14);
		panelBawah.add(labelRecall);
		
		hasilRecall1 = new JTextField();
		hasilRecall1.setText("");
		hasilRecall1.setBackground(Color.WHITE);
		hasilRecall1.setDisabledTextColor(Color.BLACK);
		hasilRecall1.setEditable(false);
		hasilRecall1.setBounds(89, 257, 86, 20);
		panelBawah.add(hasilRecall1);
		hasilRecall1.setColumns(10);
		
		hasilRecall2 = new JTextField();
		hasilRecall2.setText("");
		hasilRecall2.setBackground(Color.WHITE);
		hasilRecall2.setDisabledTextColor(Color.BLACK);
		hasilRecall2.setEditable(false);
		hasilRecall2.setBounds(189, 257, 86, 20);
		panelBawah.add(hasilRecall2);
		hasilRecall2.setColumns(10);
		
		hasilRecall3 = new JTextField();
		hasilRecall3.setText("");
		hasilRecall3.setEditable(false);
		hasilRecall3.setDisabledTextColor(Color.BLACK);
		hasilRecall3.setColumns(10);
		hasilRecall3.setBackground(Color.WHITE);
		hasilRecall3.setBounds(289, 257, 86, 20);
		panelBawah.add(hasilRecall3);
		
		hasilRecall4 = new JTextField();
		hasilRecall4.setText("");
		hasilRecall4.setEditable(false);
		hasilRecall4.setDisabledTextColor(Color.BLACK);
		hasilRecall4.setColumns(10);
		hasilRecall4.setBackground(Color.WHITE);
		hasilRecall4.setBounds(389, 257, 86, 20);
		panelBawah.add(hasilRecall4);
		
		hasilRecall5 = new JTextField();
		hasilRecall5.setText("");
		hasilRecall5.setEditable(false);
		hasilRecall5.setDisabledTextColor(Color.BLACK);
		hasilRecall5.setColumns(10);
		hasilRecall5.setBackground(Color.WHITE);
		hasilRecall5.setBounds(489, 257, 86, 20);
		panelBawah.add(hasilRecall5);
		
		labelPrecision = new JLabel("Precision");
		labelPrecision.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelPrecision.setBounds(10, 284, 69, 14);
		panelBawah.add(labelPrecision);
		
		hasilPrecision1 = new JTextField();
		hasilPrecision1.setText("");
		hasilPrecision1.setBackground(Color.WHITE);
		hasilPrecision1.setDisabledTextColor(Color.BLACK);
		hasilPrecision1.setEditable(false);
		hasilPrecision1.setBounds(89, 282, 86, 20);
		panelBawah.add(hasilPrecision1);
		hasilPrecision1.setColumns(10);
		
		hasilPrecision2 = new JTextField();
		hasilPrecision2.setText("");
		hasilPrecision2.setEditable(false);
		hasilPrecision2.setDisabledTextColor(Color.BLACK);
		hasilPrecision2.setColumns(10);
		hasilPrecision2.setBackground(Color.WHITE);
		hasilPrecision2.setBounds(189, 282, 86, 20);
		panelBawah.add(hasilPrecision2);
		
		hasilPrecision3 = new JTextField();
		hasilPrecision3.setText("");
		hasilPrecision3.setEditable(false);
		hasilPrecision3.setDisabledTextColor(Color.BLACK);
		hasilPrecision3.setColumns(10);
		hasilPrecision3.setBackground(Color.WHITE);
		hasilPrecision3.setBounds(289, 282, 86, 20);
		panelBawah.add(hasilPrecision3);
		
		hasilPrecision4 = new JTextField();
		hasilPrecision4.setText("");
		hasilPrecision4.setEditable(false);
		hasilPrecision4.setDisabledTextColor(Color.BLACK);
		hasilPrecision4.setColumns(10);
		hasilPrecision4.setBackground(Color.WHITE);
		hasilPrecision4.setBounds(389, 282, 86, 20);
		panelBawah.add(hasilPrecision4);
		
		hasilPrecision5 = new JTextField();
		hasilPrecision5.setText("");
		hasilPrecision5.setEditable(false);
		hasilPrecision5.setDisabledTextColor(Color.BLACK);
		hasilPrecision5.setColumns(10);
		hasilPrecision5.setBackground(Color.WHITE);
		hasilPrecision5.setBounds(489, 282, 86, 20);
		panelBawah.add(hasilPrecision5);
		
		labelAP = new JLabel("AP");
		labelAP.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelAP.setBounds(10, 309, 69, 14);
		panelBawah.add(labelAP);
		
		hasilAP = new JTextField();
		hasilAP.setBackground(Color.WHITE);
		hasilAP.setDisabledTextColor(Color.BLACK);
		hasilAP.setEditable(false);
		hasilAP.setBounds(89, 307, 86, 20);
		panelBawah.add(hasilAP);
		hasilAP.setColumns(10);
		
		labelMAP = new JLabel("MAP");
		labelMAP.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelMAP.setBounds(10, 334, 69, 14);
		panelBawah.add(labelMAP);
		
		labelQuery = new JLabel("Query ke-");
		labelQuery.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelQuery.setBounds(189, 334, 59, 14);
		panelBawah.add(labelQuery);
		
		ke_n = new JLabel("0");
		ke_n.setFont(new Font("Tahoma", Font.BOLD, 12));
		ke_n.setBounds(249, 334, 15, 14);
		panelBawah.add(ke_n);
		
		hasilMAP = new JTextField();
		hasilMAP.setBackground(Color.WHITE);
		hasilMAP.setDisabledTextColor(Color.BLACK);
		hasilMAP.setEditable(false);
		hasilMAP.setBounds(89, 332, 86, 20);
		panelBawah.add(hasilMAP);
		hasilMAP.setColumns(10);
		
		JButton btnHitung = new JButton("Hitung");
		btnHitung.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (inputAngka.getText().matches("[^0-9]+")) {
					hasilTeks.setText("");
					hasilSearch.setText("");
					hasilRecall1.setText("");
					hasilRecall2.setText("");
					hasilRecall3.setText("");
					hasilRecall4.setText("");
					hasilRecall5.setText("");
					hasilPrecision1.setText("");
					hasilPrecision2.setText("");
					hasilPrecision3.setText("");
					hasilPrecision4.setText("");
					hasilPrecision5.setText("");
					hasilAP.setText("");
					
					JOptionPane.showMessageDialog(null, "Masukkan harus angka!", "Warning!", JOptionPane.WARNING_MESSAGE);
				} else {
					angka = Integer.parseInt(inputAngka.getText());
					hasilArr[0][0] = hasilRecall1;
					hasilArr[0][1] = hasilRecall2;
					hasilArr[0][2] = hasilRecall3;
					hasilArr[0][3] = hasilRecall4;
					hasilArr[0][4] = hasilRecall5;
					
					hasilArr[1][0] = hasilPrecision1;
					hasilArr[1][1] = hasilPrecision2;
					hasilArr[1][2] = hasilPrecision3;
					hasilArr[1][3] = hasilPrecision4;
					hasilArr[1][4] = hasilPrecision5;
					
					hasilArr[2][0] = hasilAP;
					hasilArr[2][1] = hasilMAP;
					
					if (!hasilSearch.getText().isEmpty()) {
						hasilTeks.setText("");
						hasilSearch.setText("");
					}
					
					if (angka < 1 || angka > 63) {
						hasilRecall1.setText("");
						hasilRecall2.setText("");
						hasilRecall3.setText("");
						hasilRecall4.setText("");
						hasilRecall5.setText("");
						hasilPrecision1.setText("");
						hasilPrecision2.setText("");
						hasilPrecision3.setText("");
						hasilPrecision4.setText("");
						hasilPrecision5.setText("");
						hasilAP.setText("");
						
						if (angka == 69 || angka == 420) 
							JOptionPane.showMessageDialog(null, "noice.", "Warning!", JOptionPane.DEFAULT_OPTION);
						else
							JOptionPane.showMessageDialog(null, "Masukkan angka 1-63!", "Warning!", JOptionPane.WARNING_MESSAGE);
					} else {
						xml.read(angka, hasilTeks);
						String[] cetak = {hasilTeks.getText()};
						
						try {
							count++;
							sf.main(cetak, angka, hasilSearch, hasilArr, count);
							ke_n.setText("" + count);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btnHitung.setBounds(10, 370, 89, 23);
		panelBawah.add(btnHitung);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inputAngka.setText("");
				hasilTeks.setText("");
				hasilSearch.setText("");
				hasilRecall1.setText("");
				hasilRecall2.setText("");
				hasilRecall3.setText("");
				hasilRecall4.setText("");
				hasilRecall5.setText("");
				hasilPrecision1.setText("");
				hasilPrecision2.setText("");
				hasilPrecision3.setText("");
				hasilPrecision4.setText("");
				hasilPrecision5.setText("");
				hasilAP.setText("");
				hasilMAP.setText("");
				count = 0;
				ke_n.setText("" + count);
			}
		});
		btnReset.setBounds(109, 370, 89, 23);
		panelBawah.add(btnReset);
	}
}