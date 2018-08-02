package analisis.scopus;

public class Prueba {
	public static void main(String[] args) {
		String line = "\"Animal and Architecture \"";
		String cvsSplitBy = " and ";
		String[] scopus = line.split(cvsSplitBy);
		
		for (String string : scopus) {
			string = string.replace("\"","");
			System.out.println("|"+string+"|");
			if (string.substring(string.length()-1, string.length()).equals(" ")) {
				string = string.replace(string.substring(string.length()-1, string.length()), "");
				System.out.println("|"+string+"|");
				System.out.println("Tamaño: " + string.length());
			}
		}
		
		String details = "\"Animal Science and Zoology\"";
		details = details.replace("\"","");
		System.out.println(details); 
	}
}
