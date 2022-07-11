import java.util.*;
import javax.swing.JTextField;
public class Relevance {
	int total;
	float totalRel = 0;
	float ratata = 0;
	private ArrayList<String> cocok = new ArrayList<String>();
	
	public void process(int angka, String[] arrStr, JTextField[][] hasilArr, int count) {
		String file = "cacm.rel";
		Scanner scan = new Scanner(Relevance.class.getResourceAsStream(file));
		
		while (scan.hasNext()) {
			int num = Integer.parseInt(scan.next()); scan.next(); // Q0 dilewatin
			String doc = scan.next(); scan.next();
			
			if (num == angka) {
				cocok.add(doc);
				total = cocok.size();
			}
		}
		
		for (int i = 0; i < arrStr.length; i++) {
			float recall = totalRel / total;
			float precision = totalRel / (i+1);
			for (int j = 0; j < cocok.size(); j++) {
				if (cocok.get(j).equals(arrStr[i])) {
					totalRel += 1;
					recall = totalRel / total;
					precision = totalRel / (i+1);
					ratata += precision;
				}
			}
			hasilArr[0][i].setText("" + recall);
			hasilArr[1][i].setText("" + precision);
		}
		
		float rataAP = ratata / totalRel;
		hasilArr[2][0].setText("" + rataAP);
		hasilArr[2][1].setText("" + (rataAP/count));
		
		cocok.clear();
		totalRel = 0;
	}
}