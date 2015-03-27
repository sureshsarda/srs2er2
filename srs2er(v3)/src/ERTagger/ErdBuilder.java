package ERTagger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nlp.objects.Attribute;
import nlp.objects.Entity;
import nlp.objects.Model;
import nlp.objects.RelationEntity;
import nlp.objects.Relationship;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ErdBuilder {

	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private File outputFile;
	private Document doc;

	public ErdBuilder(File file) throws ParserConfigurationException {

		dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
		outputFile = file;
		doc = db.newDocument();

	}

	/*Basics*/
	public void parse(Model model) throws TransformerException {

		Element diagram = doc.createElement("diagram");

		diagram.setAttribute("container", "");
		diagram.setAttribute("lastSavePath", "");
		diagram.setAttribute("name", "Unnammed");
		diagram.setAttribute("sqlPre", "");

		Element elements = doc.createElement("elements");

		parseEntities(model.getEntities(), elements);
		parseRelationships(model.getRelationships(), elements);
		parseRelationEntities(model, elements);

		diagram.appendChild(elements);
		doc.appendChild(diagram);

		finalizeXml();
	}

	/*Entities*/
	private void parseEntities(List<Entity> entities, Element appendTo) {
		for (Entity entity : entities) {
			parseEntity(entity, appendTo);
		}
	}

	private void parseEntity(Entity e, Element appendTo) {
		Element entity = doc.createElement("entity");
		appendTo.appendChild(entity);

		entity.setAttribute("gen_position", "");
		entity.setAttribute("id", String.valueOf(e.getId()));
		entity.setAttribute("name", e.getLemmName());
		entity.setAttribute("pname", e.getLemmName());
		entity.setAttribute("sname", e.getLemmName());
		entity.setAttribute("sql_sequenz_name", "");

		addPositionElement(entity);

		parseAttributes(e.getAttributes(), entity);
	}

	private void parseAttributes(List<Attribute> attributes, Element appendTo) {
		for (Attribute attribute : attributes) {
			parseAttribute(attribute, appendTo);
		}
	}


	private void parseAttribute(Attribute attrb, Element appendTo) {
		Element attribute = doc.createElement("attribute");
		appendTo.appendChild(attribute);

		attribute.setAttribute("name", attrb.getName());
		attribute.setAttribute("unique", "false");
		attribute.setAttribute("type", "VARCHAR");
		attribute.setAttribute("primarykey", "false");
		attribute.setAttribute("notnull", "false");
		attribute.setAttribute("length", "0");
		attribute.setAttribute("genType", "None");
		attribute.setAttribute("foreignkey", "false");
		attribute.setAttribute("fkeytbl", "");

		addPositionElement(attribute);
	}

	/*Relations*/
	private void parseRelationships(List<Relationship> relations,
			Element appendTo) {

		for (Relationship relationship : relations) {
			parseRelationship(relationship, appendTo);
		}
	}
	private void parseRelationship(Relationship r, Element appendTo) {
		Element relationship = doc.createElement("relation");
		appendTo.appendChild(relationship);

		relationship.setAttribute("gen_position", "");
		relationship.setAttribute("id", String.valueOf(r.getId()));
		relationship.setAttribute("name", r.getLemmName());
		relationship.setAttribute("pname", r.getLemmName());
		relationship.setAttribute("sname", r.getLemmName());
		relationship.setAttribute("sql_sequenz_name", "");
		relationship.setAttribute("makeTable", "true");

		addPositionElement(relationship);
	}

	/*Connections*/
	private void parseRelationEntities(Model model, Element appendTo) {
		Element connectionsElement = doc.createElement("connections");
		appendTo.appendChild(connectionsElement);	
		
		List<String> entityNameList = new ArrayList<String>(model.getEntities()
				.size());
		for (Entity entity : model.getEntities()) {
			entityNameList.add(entity.getLemmName());
		}

		for (Relationship relationship : model.getRelationships()) {
			for (RelationEntity relationEntity : relationship.getConnects()) {
				parseRelationEntity(relationEntity , relationship.getId(),
						entityNameList.indexOf(relationEntity .getLemmName()) +  1, connectionsElement);
			}
		}
	}
	private void parseRelationEntity(RelationEntity re, Integer from,
			Integer to, Element appendTo) {
		Element connection = doc.createElement("connection");
		appendTo.appendChild(connection);

		connection.setAttribute("from", from.toString());
		connection.setAttribute("to", to.toString());
		connection.setAttribute("multiplicity", "0");
		connection.setAttribute("cascades", "");

	}

	/*Position Element*/
	private void addPositionElement(Element appendTo) {
		Element positionElement = doc.createElement("position");
		appendTo.appendChild(positionElement);

		positionElement.setAttribute("y", "0");
		positionElement.setAttribute("x", "0");
		positionElement.setAttribute("w", "-1");
		positionElement.setAttribute("h", "-1");

	}
	
	/*Finalizing*/
	private void finalizeXml() throws TransformerException {
		/* Saving the XML */
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		transformerFactory.setAttribute("indent-number", new Integer(2));
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(outputFile);
		transformer.transform(source, result);
	}
}