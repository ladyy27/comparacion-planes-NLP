package ec.edu.utpl.translation;

import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.common.collect.ImmutableList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexanders0
 */
public class Traduction {

	public static void main(String[] args) throws SQLException {
		// DB connection
		MySqlConnection dbObject = new MySqlConnection();
		Connection conn = dbObject.AbrirConexion();
		// ArrayList<Sentence> Sentences = dbObject.getSentenceFromDB(conn);
		//
		// for (Sentence s : Sentences) {
		// System.out.println(s.getId() + " " + s.getWord());
		// dbObject.updateDataToDB(conn, translate(s.getWord()),
		// String.valueOf(s.getId()));
		// }

		ArrayList<Triple> Topics = dbObject.getTopics(conn);
		ArrayList<Triple> translatedTopics = new ArrayList<Triple>();

		for (Triple topic : Topics) {
			System.out.println(topic.getS() + " - " + topic.getP() + " - " + topic.getO());
			topic.setP("texto_traducido");
			topic.setO(translate(topic.getO()));
			translatedTopics.add(topic);
		}

		for (Triple translatedTopic : translatedTopics) {
			System.out.println(translatedTopic.getS() + " - " + translatedTopic.getP() + " - " + translatedTopic.getO());
		}
		
		dbObject.storeTranslation(conn, translatedTopics);

		dbObject.CerrarConexion(conn);
	}

	public static String translate(String text) {
		// Instantiates a client
		Translate translate = TranslateOptions.getDefaultInstance().getService();

		// Language detection
		List<Detection> detections = translate.detect(ImmutableList.of(text));
		String sourceLanguage = detections.get(0).getLanguage();

		if (!(sourceLanguage.equals("en") || sourceLanguage.equals("und"))) {
			System.out.println("\nSource Text: " + text);
			System.out.println("Source Language: " + sourceLanguage);
			// Translates some text into English
			Translation translation = translate.translate(text,
					Translate.TranslateOption.sourceLanguage(sourceLanguage),
					Translate.TranslateOption.targetLanguage("en"));
			System.out.println("translation = " + translation.getTranslatedText());
			return translation.getTranslatedText().replace("&#39;", "'").replace("&quot;", "\"").replace("&amp;", "&");
		} else {
			System.out.println("text = " + text);
			return text;
		}
	}

}
