package BD;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

public class MyButton {
	private int row;
	private int seat;
	private boolean vip;
	private boolean bought;
	private JButton b;
	boolean isHere;

	public MyButton(final int row, final int seat, final boolean vip, final boolean bought, int x, int y) {
		isHere = false;
		this.row = row;
		this.seat = seat;
		this.vip = vip;
		this.bought = bought;
		JButton b = new JButton(Integer.toString(seat));
		b.setFont(new Font("Arial", Font.BOLD, 15));
		b.setForeground(Color.WHITE);
		b.setBackground(Color.blue);
		b.setBounds(300 + x, 180 + y, 50, 40);
		// b.setMargin(new Insets(10, 10, 10, 10));
		b.setEnabled(true);
		if (vip) {
			b.setBackground(Color.GREEN);
		}
		if (bought) {
			b.setEnabled(false);
			b.setBackground(Color.DARK_GRAY);
		}
		if (seat > 9)
			b.setHorizontalAlignment(SwingConstants.LEFT);
		b.setVisible(true);
		this.b = b;
	}

	public void listener(JComboBox<String> basket, ArrayList<String> items) {
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String item = "Πÿδ - " + row + ", Μ³ρφε - " + seat + ", Φ³νΰ - " + (vip ? 3 : 2) + '$';
				if (!isHere) {
					items.add(item);
					basket.addItem(item);
					isHere = true;
					// b.setEnabled(false);
					b.setBackground(Color.GRAY);
				} else {
					basket.removeItemAt(items.indexOf(item));
					items.remove(item);
					isHere = false;
					b.setBackground(Color.BLUE);
					if (vip)
						b.setBackground(Color.green);
				}
			}
		});
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getSeat() {
		return seat;
	}


	public void setSeat(int seat) {
		this.seat = seat;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public boolean isBought() {
		return bought;
	}

	public void setBought(boolean bought) {
		this.bought = bought;
	}

	public void addButt(JFrame f) {
		f.add(b);
	}
}
