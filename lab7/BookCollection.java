import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

// you need to import some xml libraries

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

// import any standard library if needed

/**
 * A book collection holds 0 or more books in a collection.
 */
public class BookCollection {
	private List<Book> books;

	/**
	 * Creates a new collection with no books by default.
	 */
	public BookCollection() {
		this.books = new ArrayList<Book>();
	}

	/**
	 * Creates a new book collection with the specified list of books pre-defined.
	 *
	 * @param books A books list.
	 */
	public BookCollection(List<Book> books) {
		this.books = books;
	}

	/**
	 * Returns the current list of books stored by this collection.
	 *
	 * @return A (mutable) list of books.
	 */
	public List<Book> getList() {
		return books;
	}

	/**
	 * Sets the list of books in this collection to the specified value.
	 */
	public void setList(List<Book> books) {
		this.books = books;
	}

	/**
	 * A simple human-readable toString implementation. Not particularly useful to
	 * save to disk.
	 *
	 * @return A human-readable string for printing
	 */
	@Override
	public String toString() {
		return this.books.stream().map(book -> " - " + book.display() + "\n").collect(Collectors.joining());
	}

	/**
	 * Saves this collection to the specified "bespoke" file.
	 *
	 * @param file The path to a file.
	 */
	public void saveToBespokeFile(File file) {
		// TODO: Implement this function yourself. The specific hierarchy is up to you,
		// but it must be in a bespoke format and should match the
		// load function.

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));

			for (Book b : books) {
				String line = b.title + "/" + b.authorName + "/" + b.yearReleased + "/" + b.bookGenre.display();
				bw.write(line);
				bw.newLine();
			}

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

//		try
//		{
//			String test = "";
//			BufferedWriter bw = new BufferedWriter(new FileWriter(file) );
//			for (Book book:books){
//				String data = book.title +";"
//						+book.authorName+";"
//						+book.yearReleased+";"
//						+book.bookGenre.display();
//
//				bw.write(data);
//				bw.newLine();
//			}
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * Saves this collection to the specified JSON file.
	 *
	 * @param file The path to a file.
	 */
	public void saveToJSONFile(File file) {
		// TODO: Implement this function yourself. The specific hierarchy is up to you,
		// but it must be in a JSON format and should match the load function.


		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		try (FileWriter writer = new FileWriter(file)) {
			gson.toJson(this.books, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}


		/*FileWriter FW = null;
		try
		{
			JsonArray JA = new JsonArray();
			for (Book books :getList()){
				JsonObject jsonObject = new JsonObject();
				//jsonObject.add("authorName");
				//jsonObject.put();
				JA.add(jsonObject);

			}
		}
		catch (Exception e){
			e.printStackTrace();
			//System.out.println(e.toString());
		}*/
		
		
	}

	/**
	 * Saves this collection to the specified XML file.
	 *
	 * @param file The path to a file.
	 */
	public void saveToXMLFile(File file) {
		// TODO: Implement this function yourself. The specific hierarchy is up to you,
		// but it must be in an XML format and should match the
		// load function.

		File f = file;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// make the xml tree
			// use factory to get the instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			// Root element <customers>
			Element rootElem = doc.createElement("customers");
			doc.appendChild(rootElem);

			for (Book b : books ) {
				Element customerElem = doc.createElement("customer");
				rootElem.appendChild(customerElem);

				// child element <name> under <customer>
				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(b.bookGenre.name ));
				customerElem.appendChild(name);

				Element id = doc.createElement("year");
				id.appendChild(doc.createTextNode(Integer.toString(b.yearReleased)));
				customerElem.appendChild(id);

				Element phone = doc.createElement("Author name");
				phone.appendChild(doc.createTextNode(b.authorName));
				customerElem.appendChild(phone);

				Element address = doc.createElement("title");
				address.appendChild(doc.createTextNode(b.title));
				customerElem.appendChild(address);
			}

			// save the xml file
			// Transformer is for process XML from a variety of sources and write the transformation
			// output to a variety of sinks
			Transformer transformer = TransformerFactory.newInstance().newTransformer();

			// set xml encoding to utf-8
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			// pretty print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(f);
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	/**
	 * Load a pre-existing book collection from a "bespoke" file.
	 *
	 * @param file The file to load from. This is guaranteed to exist.
	 * @return An initialised book collection.
	 */
	public static BookCollection loadFromBespokeFile(File file) {
		// TODO: Implement this function yourself.
		BookCollection result = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			List<Book> thisBook = new ArrayList<Book>();

			while ((line = br.readLine()) != null) {
				String[] tokens = line.split("/");
				int year = Integer.parseInt(tokens[2]);
				// convert String to BookGenre
				BookGenre genre = null;
				if (tokens[3].compareTo("Action (Fiction)")==0)
					genre = BookGenre.FICTION_ACTION;
				else if (tokens[3].compareTo("Comedy (Fiction)")==0)
					genre = BookGenre.FICTION_COMEDY;
				else if (tokens[3].compareTo("Fantasy (Fiction)")==0)
					genre = BookGenre.FICTION_FANTASY;
				else if (tokens[3].compareTo("Non-Fiction")==0)
					genre = BookGenre.NON_FICTION;
				Book book = new Book(tokens[0], tokens[1], year, genre);
				thisBook.add(book);
			}
			result = new BookCollection(thisBook);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	/*	try{
			BufferedReader br = new BufferedReader(new FileReader(file.getName()));
			String list = "";
			String element = br.readLine();
			while(element != null){
				list = list+element;
				element = br.readLine();
			}
			String[] e = element.split(";");
			Book book = new Book(e[0],e[1],Integer.parseInt(e[2]),BookGenre.valueOf(e[3]));

		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;*/
	}

	/**
	 * Load a pre-existing book collection from a JSON file.
	 *
	 * @param file The file to load from. This is guaranteed to exist.
	 * @return An initialised book collection.
	 */
	public static BookCollection loadFromJSONFile(File file) {
		// TODO: Implement this function yourself.
		return null;
		
		
	}

	/**
	 * Load a pre-existing book collection from an XML file.
	 *
	 * @param file The file to load from. This is guaranteed to exist.
	 * @return An initialised book collection.
	 */
	public static BookCollection loadFromXMLFile(File file) {
		// TODO: Implement this function yourself.
		
		
		return null;
		
	}
}
