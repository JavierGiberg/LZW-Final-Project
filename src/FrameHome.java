/**
 * Final Project LZW: Javier Giberg.
 */
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FrameHome extends javax.swing.JFrame {

	public String filenIn = "";
	private String fileOut = "";

	
	public FrameHome() {
		initComponents();
	}


	private void initComponents() {

		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenu2 = new javax.swing.JMenu();
		CompressBtn = new javax.swing.JButton();
		DecompressBtn = new javax.swing.JButton();
		Output = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();

		jMenu1.setText("File");
		jMenuBar1.add(jMenu1);

		jMenu2.setText("Edit");
		jMenuBar1.add(jMenu2);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		CompressBtn.setFont(new java.awt.Font("Segoe Print", 1, 14)); 
		CompressBtn.setText("Compresse file");
		CompressBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				CompressBtnActionPerformed(evt);
			}
		});

		DecompressBtn.setFont(new java.awt.Font("Segoe Print", 1, 14)); 
		DecompressBtn.setText("Decompress file");
		DecompressBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				DecompressBtnActionPerformed(evt);
			}
		});

		Output.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N

		jLabel1.setIcon(new javax.swing.ImageIcon("lzw.jpg")); // NOI18N

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
						.addComponent(Output, javax.swing.GroupLayout.Alignment.LEADING,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE))
				.addGap(0, 0, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup().addGap(86, 86, 86)
						.addComponent(CompressBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(DecompressBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(84, 84, 84)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(18, 18, 18)
				.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 255,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(
						Output, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGap(54, 54, 54)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(DecompressBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(CompressBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
								javax.swing.GroupLayout.PREFERRED_SIZE))
				.addContainerGap(60, Short.MAX_VALUE)));

		pack();
	}

	private void DecompressBtnActionPerformed(java.awt.event.ActionEvent evt) {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		File fileIn = chooser.getSelectedFile();
		filenIn = fileIn.getAbsolutePath();

		// save
		JFileChooser fs = new JFileChooser(new File("c:\\"));
		fs.setDialogTitle("Save file");
		fs.setFileFilter(new FileNameExtensionFilter(".txt", "LZWG"));
		int result = fs.showSaveDialog(null);
		File fileOut = fs.getSelectedFile();
		if (result == JFileChooser.APPROVE_OPTION) {
			System.out.println(fileOut);
		}
		try {
			String srcIn = filenIn;
			String srcOut = fileOut + "(LZWG Result)";

			LZW A = new LZW();

			File size = new File(srcOut);

			String toOut = A.Decompress(srcIn, srcOut) + " Size file is : " + size.length() + "Kb";
			Output.setText(toOut);

		} catch (IOException ex) {
			Logger.getLogger(FrameHome.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void CompressBtnActionPerformed(java.awt.event.ActionEvent evt) {
	
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		File fileIn = chooser.getSelectedFile();
		filenIn = fileIn.getAbsolutePath();

		JFileChooser fs = new JFileChooser(new File("c:\\"));
		fs.setDialogTitle("Save file");
		fs.setFileFilter(new FileNameExtensionFilter(".bin", "LZWG"));
		int result = fs.showSaveDialog(null);
		File fileOut = fs.getSelectedFile();
	
		try {
			String srcIn = filenIn;
			String srcOut = fileOut + "(LZWG Result).bin";
			LZW A = new LZW();
			File size = new File(srcOut);

			String toOut = A.Compress(srcIn, srcOut) + " Size file is : " + size.length() + "Kb";
			Output.setText(toOut);
		} catch (IOException ex) {
			Logger.getLogger(FrameHome.class.getName()).log(Level.SEVERE, null, ex);
		}

	}


	public static void main(String args[]) {
	
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(FrameHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(FrameHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(FrameHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(FrameHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new FrameHome().setVisible(true);
			}
		});
	}


	private javax.swing.JButton CompressBtn;
	private javax.swing.JButton DecompressBtn;
	private javax.swing.JLabel Output;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenuBar jMenuBar1;

}
