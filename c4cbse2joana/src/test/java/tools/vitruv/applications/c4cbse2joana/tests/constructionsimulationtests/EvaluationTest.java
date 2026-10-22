package joana2confidentiality4cbsemodelpreservation.tests.constructionsimulationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import edu.kit.ipd.sdq.commons.util.java.Pair;
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import org.w3c.dom.Element;

public class EvaluationTest {
	private static Document original = loadOriginalModel();
	private static Document repository = loadOriginalRepository();
	private static Document  eval = loadComparisonModel();
	private static Document  evalsourcecode = loadComparisonSourcecodeModel();
	
	private static List<Object> noMatch = new ArrayList<>();

	@BeforeEach
	public void setup() {
		noMatch.clear();
	}
	
	@AfterEach
	public void cleanup(TestInfo testInfo) {
		if (noMatch.size() > 0) System.out.println("Test %s produced %d elements that were not matched.".formatted(testInfo.getDisplayName(),noMatch.size()));
		assertEquals(noMatch, new ArrayList<>());
		
	}
	
	private static Document loadOriginalModel() {
		return loadResource("resources/model/JoanaRoot.joanaRoot");
	}
	
	private static Document loadOriginalRepository() {
		return loadResource("resources/model/default.repository");
	}
	
	private static Document loadComparisonModel() {
		return loadResource("resources/model/validate2/security.joana");
	}
	
	private static Document loadComparisonSourcecodeModel() {
		return loadResource("resources/model/validate2/structure.sourcecode");
	}
	
	private static Document loadResource(String model) {
		try {  
			File file = new File(model);  
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
			DocumentBuilder db = dbf.newDocumentBuilder();  
			Document doc = db.parse(file); 
			doc.normalizeDocument();
//			doc.getDocumentElement().normalize();
//			System.out.println(doc.get);
			return doc;
		} catch (Exception e) {
		}
		return null;
	}

	private NodeList get(Document doc, String nodeName) {
		return doc.getElementsByTagName(nodeName);
	}
	
	private String get(Node n, String attribute) {
		return n.getAttributes() != null && n.getAttributes().getNamedItem(attribute)!= null ? n.getAttributes().getNamedItem(attribute).getNodeValue() : getChildNodes(n, attribute);
	}
	
	private String getChildNodes(Node n, String attribute) {
		var a = mapTo(n.getChildNodes(), get(attribute)).stream().filter(it -> it != null).findFirst();
		if (a.isPresent()) {return a.get();}
		else return null;
	}

	private Function<Node, String> get(String attribute) {
		return (Node t) -> {
			return get(t, attribute);
		};
	}
	
	

	private <T> List<T> mapTo(NodeList nl, Function<Node, T> func) {
		List<T> result = new ArrayList<>();
		for(int i = 0; i < nl.getLength(); i++) {
			result.add(func.apply(nl.item(i)));
		}
		return result;
	}
	
	private Pair<String, String> extractFromToFromFlowRelation(Node node) {
		return new Pair<String, String>(node.getAttributes().getNamedItem("from").getNodeValue(), node.getAttributes().getNamedItem("to").getNodeValue());
	}
	
	private List<Pair<String, String>> mapToElementsAtExtractedIndex(List<Pair<String, String>> pairs, List<String> names) {
		return pairs.stream().map(mapToElementsAtExtractedIndex(names)).toList();
	}
	
	private List<String> mapToElementAtExtractedIndex(List<String> pairs, List<String> names) {
		return pairs.stream().map(mapToElementAtExtractedIndex(names)).toList();
	}
	
	private Function<Pair<String, String>, Pair<String, String>> mapToElementsAtExtractedIndex(List<String> names) {
		return (Pair<String, String> t) -> {
			return new Pair<String, String>(names.get(getIndex(t.getFirst())), names.get(getIndex(t.getSecond())));
		};
	}
	
	private Function<String, String> mapToElementAtExtractedIndex(List<String> names) {
		return (String t) -> {
			return names.get(getIndex(t));
		};
	}
	
	private int getIndex(String s) {
		return Integer.parseInt(s.split("\\.")[1]);
	}
	
	private List<String> getSecurityLevels(Document doc) {
		return mapToAttributeList(get(doc, "securitylevel"), "name");
	}
	
	private <T> List<T> mapToAttributeList(NodeList nl, String attributeName) {
		List<T> result = new ArrayList<>();
		for (int i = 0; i< nl.getLength(); i++) {
			result.add((T) nl.item(i).getAttributes().getNamedItem(attributeName).getNodeValue());
		}
		return result;
	}
	
	@Test
	public void cmpSecLevel() {
		assertTrue(cmpseclev(get(original, "securitylevel"), get(eval, "securitylevel")));
	}
	
	private boolean cmpseclev(NodeList first, NodeList second) {
		// add empty level with plus one
		boolean result = first.getLength()+ 1 == second.getLength();
		var fir = mapToAttributeList(first, "name");
		var sec = mapToAttributeList(second, "name");
		var firminsec = new ArrayList<>(fir);
		firminsec.removeAll(sec);
		var secminfir = new ArrayList<>(sec);
		secminfir.removeAll(fir);		
		// emtpy level in sec
		return result && firminsec.isEmpty() && secminfir.size()==1;
	}
	
	@Test
	public void cmpFlowRelationNumber() {
		assertEquals(get(original, "flowrelation").getLength(), get(eval, "flowrelation").getLength());
	}
	
	@Test
	public void cmpFlowRelations() {
		var fir = mapToElementsAtExtractedIndex(mapTo(get(original, "flowrelation"), t -> extractFromToFromFlowRelation(t)), getSecurityLevels(original));
		var sec = mapToElementsAtExtractedIndex(mapTo(get(eval, "flowrelation"), t -> extractFromToFromFlowRelation(t)), getSecurityLevels(eval));
		assertEquals(new HashSet<>(fir), new HashSet<>(sec));
	}
	
	@Test 
	public void cmpAnnotationsNumber() {
		var fir = get(original, "annotation");
		var sec = get(eval, "annotation");
		assertEquals(fir.getLength(), sec.getLength());
	}
	
	@Test
	public void cmpAnnotations() {
		var fir = get(original, "annotation");
		var sec = get(eval, "annotation");
		cmp(fir, sec, cmpAnnos());
	}
	
	@Test
	public void cmpAnnotationsSucc() {
		var fir = get(original, "annotation");
		var sec = get(eval, "annotation");
		//assertEquals(fir.getLength(), sec.getLength());
		var result = cmp(fir, sec, cmpAnnosSucc());
		assertTrue(result);
	}

	private String getTag(Node n) {
		return n.getTextContent().trim();
	}

	private BiFunction<Node, Node, Boolean> cmpAnnos() {
		return (Node t, Node u) -> {
			// compare based on securitylevel, tag, annotated method and annotatedparameter
			var result = true;
			result &= mapToElementAtExtractedIndex(List.of(get(t, "securitylevel")), getSecurityLevels(original)).equals(mapToElementAtExtractedIndex(List.of(get(u, "securitylevel")), getSecurityLevels(eval)))||
					mapToElementAtExtractedIndex(List.of(get(t, "securitylevel")), getSecurityLevels(eval)).equals(mapToElementAtExtractedIndex(List.of(get(u, "securitylevel")), getSecurityLevels(original)));
			result &= cmpType(get(t, "xsi:type"), get(u, "xsi:type"));
			
			//TODO:Provide extraction for subnodes because lib is shit. 
			result &= cmpTags(get(t, "tag"), get(u, "tag"));
			result &= cmpMethod(get(t, "annotatedMethod"), get(u, "annotatedMethod"));
			result &= cmpParameter(get(t, "annotatedParameter"), get(u, "annotatedParameter"));
			return result;
		};
	}
	
	private BiFunction<Node, Node, Boolean> cmpAnnosSucc() {
		return (Node t, Node u) -> {
			var result = true;

				var typeT = get(t, "xsi:type");
				var typeU = get(u, "xsi:type");
				
				
				if(cmpType(typeT, typeU)) {
					
					result &= cmpMethod(get(t, "annotatedMethod"), get(u, "annotatedMethod"));
					result &= cmpType(get(t, "xsi:type"), get(u, "xsi:type"));
					result &= cmpParameter(get(t, "annotatedParameter"), get(u, "annotatedParameter"));
					result &= mapToElementAtExtractedIndex(List.of(get(t, "securitylevel")), getSecurityLevels(original)).equals(mapToElementAtExtractedIndex(List.of(get(u, "securitylevel")), getSecurityLevels(eval)))||
							mapToElementAtExtractedIndex(List.of(get(t, "securitylevel")), getSecurityLevels(eval)).equals(mapToElementAtExtractedIndex(List.of(get(u, "securitylevel")), getSecurityLevels(original)));
					

					if(typeT.equals("edu.kit.kastel.scbs.pcm2java4joana.joana:Source")) {
						

						var tagT = searchNode(t.getChildNodes(), "tag").getTextContent();
						var tagU = searchNode(t.getChildNodes(), "tag").getTextContent();
						
						result &= cmpTags(tagT, tagU);
					} 
					
				
				} else {
					return false;
				}
			return result;
		};
	}
	
	
	@Test
	public void cmpFlowSpecificationsNumber() {
		var fir = get(original, "flowspecification");
		var sec = get(eval, "flowspecification");
		assertEquals(fir.getLength(), sec.getLength());
	}
	
	@Test
	public void cmpFlowSpecifications() {
		var fir = get(original, "flowspecification");
		var sec = get(eval, "flowspecification");
		cmp(fir, sec, cmpFlowSpecs());
	}

	
	
	private BiFunction<Node, Node, Boolean> cmpFlowSpecs() {
		return (Node t, Node u) -> {
			var result = true;
			
			var entryPointT = searchNode(t.getChildNodes(), "entrypoint");
			var entryPointU = searchNode(u.getChildNodes(), "entrypoint");
			
			if(entryPointT.getNodeType() == Node.ELEMENT_NODE && entryPointU.getNodeType() == Node.ELEMENT_NODE) {
				var entryPointTElement = (Element) entryPointT;
				var entryPointUElement = (Element) entryPointU;

				var tagT = searchNode(entryPointTElement.getChildNodes(), "tag").getTextContent();
				var tagU = searchNode(entryPointUElement.getChildNodes(), "tag").getTextContent();
				
				result &= cmpTags(tagT, tagU);
				
				var methodT = entryPointTElement.getAttribute("annotatedMethod");
				var methodU = searchNode(entryPointUElement.getChildNodes(), "annotatedMethod").getAttributes().getNamedItem("href").getTextContent();
				
				result &= cmpMethod(methodT, methodU);

				var seclevlels = entryPointTElement.getAttribute("securitylevels").split(" ");
				result &= seclevlels.length==256 || seclevlels.length==255;
				seclevlels = entryPointUElement.getAttribute("securitylevels").split(" ");
				result &= seclevlels.length==256 || seclevlels.length==255;

				// compare annotated method
				result &= methodName(path(entryPointTElement)).equals(methodName(path(entryPointUElement)));
			}
			
			
			return result;
		};
	}
	
	private String methodName(Pair<Pathtype, String> a) {
		if (a.getFirst().equals(Pathtype.HAERING)) {
			int indexClass = Integer.parseInt(a.getSecond().split("@topleveltype.")[1].split("/")[0]);
			int indexMethod = Integer.parseInt("" + a.getSecond().split("@methods.")[1].charAt(0));
			var classname = get(evalsourcecode, "topleveltype").item(indexClass).getAttributes().getNamedItem("name").getNodeValue();
			var methodname = get(evalsourcecode, "topleveltype").item(indexClass).getFirstChild().getNextSibling().getAttributes().getNamedItem("name").getNodeValue();
			return classname + "." + methodname;
		} else {
			File file = new File(new File("").getAbsolutePath().concat("/resources/" + a.getSecond().split("#")[0].substring(2)));
			try {
				var methodIndex = between("@members.", "\"/>", a.getSecond());
				var list = Files.readAllLines(file.toPath());
				var content = new StringJoiner("");
				list.forEach(it -> content.add(it));
				var classname = between("public interface ", " \\{", content.toString());
				var methodline = list.stream().filter(it -> it.matches(".* .*\\(.*\\);")).toList().get(Integer.parseInt(methodIndex));
				return classname + "." + between(" ", "\\(", methodline);
			} catch (FileNotFoundException e) {} catch (IOException e) {}
		}
		return null;
	}
	
	private String between(String pre, String post, String to) {
		return to.split(pre)[1].split(post)[0];
	}
	
	enum Pathtype {
		HAERING, BECKER;
	}
	
	private Pair<Pathtype, String> path(Element e) {
		if (e.getChildNodes().item(3) != null) {
			return new Pair<>(Pathtype.BECKER, e.getChildNodes().item(3).getAttributes().getNamedItem("href").getNodeValue());
		} else if (e.getAttributes().getNamedItem("annotatedMethod")!= null) {
			return new Pair<>(Pathtype.HAERING, e.getAttribute("annotatedMethod"));
		}
		return null;
	}
	
	private String extractTag(Element e) {
		return e.getChildNodes().item(1).getTextContent();
	}

	private boolean cmp(NodeList nl, NodeList n, BiFunction<Node, Node, Boolean> cmp) {
		var result = true;
		for (int i = 0; i< n.getLength();i++) {
			result &= cmp(nl, n.item(i), cmp);
		}
		return result;
	}
	
	private boolean cmp(NodeList nl, Node n, BiFunction<Node, Node, Boolean> cmp) {
		for (int i = 0; i< nl.getLength();i++) {
			if(cmp.apply(n, nl.item(i))) return true;
		}
		noMatch.add(String.format("Node %s could not be found in List%s %s", o(n),System.lineSeparator(), o(nl)));
		return false;
	}
	
	private boolean cmpType(String type1, String type2) {
		return type1.equals(type2);
	}
	private boolean cmpMethod(String method1, String method2) {
		return true;
	}
	private boolean cmpParameter(String par1, String par2) {
		return true;
	}
	private boolean cmpTags(String par1, String par2) {
		return par1==null&&par2==null || par1.contains(par2) || par2.contains(par1);
	}
	
	private String o(Node n) {
		return n.getNodeName() + ":" + n.getTextContent().trim();
	}
	
	//TODO: returns first node of nodeName.
	private Node searchNode(NodeList nodel, String nodeName) {
		for(int i = 0; i < nodel.getLength(); i++) {
			if (nodel.item(i).getNodeName().equals(nodeName)) {
				return nodel.item(i);
			}
		}
		return null;
	}
	
	private String o(NodeList n) {
		StringJoiner j = new StringJoiner(System.lineSeparator());
		for(int i = 0; i< n.getLength(); i++)j.add(o(n.item(i)));
		return j.toString();
	}

}
