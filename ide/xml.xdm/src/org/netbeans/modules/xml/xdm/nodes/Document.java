/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.modules.xml.xdm.nodes;

import java.lang.ref.SoftReference;
import java.util.Map;
import javax.xml.XMLConstants;
import org.netbeans.modules.xml.xdm.visitor.FindNamespaceVisitor;
import org.netbeans.modules.xml.xdm.visitor.XMLNodeVisitor;
import org.w3c.dom.DOMException;
import org.w3c.dom.NodeList;

/**
 *
 * @author Ajit
 */
public class Document extends NodeImpl implements Node, org.w3c.dom.Document {
    
    /** Creates a new instance of Document */
    Document() {
        super();
        resetNamespaceCache();
    }
    
    public Document clone(boolean cloneContent, boolean cloneAttributes, boolean cloneChildren) {
        Document ret = (Document) super.clone(cloneContent, cloneAttributes, cloneChildren);
        ret.resetNamespaceCache();
        return ret;
    }
    
    void resetNamespaceCache() {
        fnv = null;
    }
    
    public short getNodeType() {
        return Node.DOCUMENT_NODE;
    }

    public String getNodeName() {
        return "#document"; //NOI18N
    }

	public void accept(XMLNodeVisitor visitor) {
		visitor.visit(this);
	}

    /**
     *  Attempts to adopt a node from another document to this document. If 
     * supported, it changes the <code>ownerDocument</code> of the source 
     * node, its children, as well as the attached attribute nodes if there 
     * are any. If the source node has a parent it is first removed from the 
     * child list of its parent. This effectively allows moving a subtree 
     * from one document to another (unlike <code>importNode()</code> which 
     * create a copy of the source node instead of moving it). When it 
     * fails, applications should use <code>Document.importNode()</code> 
     * instead. Note that if the adopted node is already part of this 
     * document (i.e. the source and target document are the same), this 
     * method still has the effect of removing the source node from the 
     * child list of its parent, if any. The following list describes the 
     * specifics for each type of node. 
     * <dl>
     * <dt>ATTRIBUTE_NODE</dt>
     * <dd>The 
     * <code>ownerElement</code> attribute is set to <code>null</code> and 
     * the <code>specified</code> flag is set to <code>true</code> on the 
     * adopted <code>Attr</code>. The descendants of the source 
     * <code>Attr</code> are recursively adopted.</dd>
     * <dt>DOCUMENT_FRAGMENT_NODE</dt>
     * <dd>The 
     * descendants of the source node are recursively adopted.</dd>
     * <dt>DOCUMENT_NODE</dt>
     * <dd>
     * <code>Document</code> nodes cannot be adopted.</dd>
     * <dt>DOCUMENT_TYPE_NODE</dt>
     * <dd>
     * <code>DocumentType</code> nodes cannot be adopted.</dd>
     * <dt>ELEMENT_NODE</dt>
     * <dd><em>Specified</em> attribute nodes of the source element are adopted. Default attributes 
     * are discarded, though if the document being adopted into defines 
     * default attributes for this element name, those are assigned. The 
     * descendants of the source element are recursively adopted.</dd>
     * <dt>ENTITY_NODE</dt>
     * <dd>
     * <code>Entity</code> nodes cannot be adopted.</dd>
     * <dt>ENTITY_REFERENCE_NODE</dt>
     * <dd>Only 
     * the <code>EntityReference</code> node itself is adopted, the 
     * descendants are discarded, since the source and destination documents 
     * might have defined the entity differently. If the document being 
     * imported into provides a definition for this entity name, its value 
     * is assigned.</dd>
     * <dt>NOTATION_NODE</dt>
     * <dd><code>Notation</code> nodes cannot be 
     * adopted.</dd>
     * <dt>PROCESSING_INSTRUCTION_NODE, TEXT_NODE, CDATA_SECTION_NODE, 
     * COMMENT_NODE</dt>
     * <dd>These nodes can all be adopted. No specifics.</dd>
     * </dl> 
     * <p ><b>Note:</b>  Since it does not create new nodes unlike the 
     * <code>Document.importNode()</code> method, this method does not raise 
     * an <code>INVALID_CHARACTER_ERR</code> exception, and applications 
     * should use the <code>Document.normalizeDocument()</code> method to 
     * check if an imported name is not an XML name according to the XML 
     * version in use. 
     * @param source The node to move into this document.
     * @return The adopted node, or <code>null</code> if this operation 
     *   fails, such as when the source node comes from a different 
     *   implementation.
     * @exception DOMException
     *   NOT_SUPPORTED_ERR: Raised if the source node is of type 
     *   <code>DOCUMENT</code>, <code>DOCUMENT_TYPE</code>.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised when the source node is 
     *   readonly.
     * @since DOM Level 3
     */
    public org.w3c.dom.Node adoptNode(org.w3c.dom.Node source) {
        //TODO Implement later
        return null;
    }

    /**
     * Imports a node from another document to this document, without altering 
     * or removing the source node from the original document; this method 
     * creates a new copy of the source node. The returned node has no 
     * parent; (<code>parentNode</code> is <code>null</code>).
     * <br>For all nodes, importing a node creates a node object owned by the 
     * importing document, with attribute values identical to the source 
     * node's <code>nodeName</code> and <code>nodeType</code>, plus the 
     * attributes related to namespaces (<code>prefix</code>, 
     * <code>localName</code>, and <code>namespaceURI</code>). As in the 
     * <code>cloneNode</code> operation, the source node is not altered. 
     * User data associated to the imported node is not carried over. 
     * However, if any <code>UserDataHandlers</code> has been specified 
     * along with the associated data these handlers will be called with the 
     * appropriate parameters before this method returns.
     * <br>Additional information is copied as appropriate to the 
     * <code>nodeType</code>, attempting to mirror the behavior expected if 
     * a fragment of XML or HTML source was copied from one document to 
     * another, recognizing that the two documents may have different DTDs 
     * in the XML case. The following list describes the specifics for each 
     * type of node. 
     * <dl>
     * <dt>ATTRIBUTE_NODE</dt>
     * <dd>The <code>ownerElement</code> attribute 
     * is set to <code>null</code> and the <code>specified</code> flag is 
     * set to <code>true</code> on the generated <code>Attr</code>. The 
     * descendants of the source <code>Attr</code> are recursively imported 
     * and the resulting nodes reassembled to form the corresponding subtree.
     * Note that the <code>deep</code> parameter has no effect on 
     * <code>Attr</code> nodes; they always carry their children with them 
     * when imported.</dd>
     * <dt>DOCUMENT_FRAGMENT_NODE</dt>
     * <dd>If the <code>deep</code> option 
     * was set to <code>true</code>, the descendants of the source 
     * <code>DocumentFragment</code> are recursively imported and the 
     * resulting nodes reassembled under the imported 
     * <code>DocumentFragment</code> to form the corresponding subtree. 
     * Otherwise, this simply generates an empty 
     * <code>DocumentFragment</code>.</dd>
     * <dt>DOCUMENT_NODE</dt>
     * <dd><code>Document</code> 
     * nodes cannot be imported.</dd>
     * <dt>DOCUMENT_TYPE_NODE</dt>
     * <dd><code>DocumentType</code> 
     * nodes cannot be imported.</dd>
     * <dt>ELEMENT_NODE</dt>
     * <dd><em>Specified</em> attribute nodes of the source element are imported, and the generated 
     * <code>Attr</code> nodes are attached to the generated 
     * <code>Element</code>. Default attributes are <em>not</em> copied, though if the document being imported into defines default 
     * attributes for this element name, those are assigned. If the 
     * <code>importNode</code> <code>deep</code> parameter was set to 
     * <code>true</code>, the descendants of the source element are 
     * recursively imported and the resulting nodes reassembled to form the 
     * corresponding subtree.</dd>
     * <dt>ENTITY_NODE</dt>
     * <dd><code>Entity</code> nodes can be 
     * imported, however in the current release of the DOM the 
     * <code>DocumentType</code> is readonly. Ability to add these imported 
     * nodes to a <code>DocumentType</code> will be considered for addition 
     * to a future release of the DOM.On import, the <code>publicId</code>, 
     * <code>systemId</code>, and <code>notationName</code> attributes are 
     * copied. If a <code>deep</code> import is requested, the descendants 
     * of the the source <code>Entity</code> are recursively imported and 
     * the resulting nodes reassembled to form the corresponding subtree.</dd>
     * <dt>
     * ENTITY_REFERENCE_NODE</dt>
     * <dd>Only the <code>EntityReference</code> itself is 
     * copied, even if a <code>deep</code> import is requested, since the 
     * source and destination documents might have defined the entity 
     * differently. If the document being imported into provides a 
     * definition for this entity name, its value is assigned.</dd>
     * <dt>NOTATION_NODE</dt>
     * <dd>
     * <code>Notation</code> nodes can be imported, however in the current 
     * release of the DOM the <code>DocumentType</code> is readonly. Ability 
     * to add these imported nodes to a <code>DocumentType</code> will be 
     * considered for addition to a future release of the DOM.On import, the 
     * <code>publicId</code> and <code>systemId</code> attributes are copied.
     * Note that the <code>deep</code> parameter has no effect on this type 
     * of nodes since they cannot have any children.</dd>
     * <dt>
     * PROCESSING_INSTRUCTION_NODE</dt>
     * <dd>The imported node copies its 
     * <code>target</code> and <code>data</code> values from those of the 
     * source node.Note that the <code>deep</code> parameter has no effect 
     * on this type of nodes since they cannot have any children.</dd>
     * <dt>TEXT_NODE, 
     * CDATA_SECTION_NODE, COMMENT_NODE</dt>
     * <dd>These three types of nodes inheriting 
     * from <code>CharacterData</code> copy their <code>data</code> and 
     * <code>length</code> attributes from those of the source node.Note 
     * that the <code>deep</code> parameter has no effect on these types of 
     * nodes since they cannot have any children.</dd>
     * </dl> 
     * @param importedNode The node to import.
     * @param deep If <code>true</code>, recursively import the subtree under 
     *   the specified node; if <code>false</code>, import only the node 
     *   itself, as explained above. This has no effect on nodes that cannot 
     *   have any children, and on <code>Attr</code>, and 
     *   <code>EntityReference</code> nodes.
     * @return The imported node that belongs to this <code>Document</code>.
     * @exception DOMException
     *   NOT_SUPPORTED_ERR: Raised if the type of node being imported is not 
     *   supported.
     *   <br>INVALID_CHARACTER_ERR: Raised if one of the imported names is not 
     *   an XML name according to the XML version in use specified in the 
     *   <code>Document.xmlVersion</code> attribute. This may happen when 
     *   importing an XML 1.1 [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>] element 
     *   into an XML 1.0 document, for instance.
     * @since DOM Level 2
     */
    public org.w3c.dom.Node importNode(org.w3c.dom.Node importedNode, boolean deep) {
        //TODO Implement later
        return null;
    }

    /**
     * Rename an existing node of type <code>ELEMENT_NODE</code> or 
     * <code>ATTRIBUTE_NODE</code>.
     * <br>When possible this simply changes the name of the given node, 
     * otherwise this creates a new node with the specified name and 
     * replaces the existing node with the new node as described below.
     * <br>If simply changing the name of the given node is not possible, the 
     * following operations are performed: a new node is created, any 
     * registered event listener is registered on the new node, any user 
     * data attached to the old node is removed from that node, the old node 
     * is removed from its parent if it has one, the children are moved to 
     * the new node, if the renamed node is an <code>Element</code> its 
     * attributes are moved to the new node, the new node is inserted at the 
     * position the old node used to have in its parent's child nodes list 
     * if it has one, the user data that was attached to the old node is 
     * attached to the new node.
     * <br>When the node being renamed is an <code>Element</code> only the 
     * specified attributes are moved, default attributes originated from 
     * the DTD are updated according to the new element name. In addition, 
     * the implementation may update default attributes from other schemas. 
     * Applications should use <code>Document.normalizeDocument()</code> to 
     * guarantee these attributes are up-to-date.
     * <br>When the node being renamed is an <code>Attr</code> that is 
     * attached to an <code>Element</code>, the node is first removed from 
     * the <code>Element</code> attributes map. Then, once renamed, either 
     * by modifying the existing node or creating a new one as described 
     * above, it is put back.
     * <br>In addition,
     * <ul>
     * <li> a user data event <code>NODE_RENAMED</code> is fired, 
     * </li>
     * <li> 
     * when the implementation supports the feature "MutationNameEvents", 
     * each mutation operation involved in this method fires the appropriate 
     * event, and in the end the event {
     * <code>http://www.w3.org/2001/xml-events</code>, 
     * <code>DOMElementNameChanged</code>} or {
     * <code>http://www.w3.org/2001/xml-events</code>, 
     * <code>DOMAttributeNameChanged</code>} is fired. 
     * </li>
     * </ul>
     * @param n The node to rename.
     * @param namespaceURI The new namespace URI.
     * @param qualifiedName The new qualified name.
     * @return The renamed node. This is either the specified node or the new 
     *   node that was created to replace the specified node.
     * @exception DOMException
     *   NOT_SUPPORTED_ERR: Raised when the type of the specified node is 
     *   neither <code>ELEMENT_NODE</code> nor <code>ATTRIBUTE_NODE</code>, 
     *   or if the implementation does not support the renaming of the 
     *   document element.
     *   <br>INVALID_CHARACTER_ERR: Raised if the new qualified name is not an 
     *   XML name according to the XML version in use specified in the 
     *   <code>Document.xmlVersion</code> attribute.
     *   <br>WRONG_DOCUMENT_ERR: Raised when the specified node was created 
     *   from a different document than this document.
     *   <br>NAMESPACE_ERR: Raised if the <code>qualifiedName</code> is a 
     *   malformed qualified name, if the <code>qualifiedName</code> has a 
     *   prefix and the <code>namespaceURI</code> is <code>null</code>, or 
     *   if the <code>qualifiedName</code> has a prefix that is "xml" and 
     *   the <code>namespaceURI</code> is different from "<a href='http://www.w3.org/XML/1998/namespace'>
     *   http://www.w3.org/XML/1998/namespace</a>" [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     *   . Also raised, when the node being renamed is an attribute, if the 
     *   <code>qualifiedName</code>, or its prefix, is XMLNS and the 
     *   <code>namespaceURI</code> is different from "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>".
     * @since DOM Level 3
     */
    public org.w3c.dom.Node renameNode(org.w3c.dom.Node n, String namespaceURI, String qualifiedName) {
        //TODO Implement later
        return null;
    }

    /**
     * Creates a <code>Text</code> node given the specified string.
     * @param data The data for the node.
     * @return The new <code>Text</code> object.
     */
    public org.w3c.dom.Text createTextNode(String data) {
        return new Text(data);
    }

    /**
     * Creates an <code>EntityReference</code> object. In addition, if the 
     * referenced entity is known, the child list of the 
     * <code>EntityReference</code> node is made the same as that of the 
     * corresponding <code>Entity</code> node.
     * <p ><b>Note:</b> If any descendant of the <code>Entity</code> node has 
     * an unbound namespace prefix, the corresponding descendant of the 
     * created <code>EntityReference</code> node is also unbound; (its 
     * <code>namespaceURI</code> is <code>null</code>). The DOM Level 2 and 
     * 3 do not support any mechanism to resolve namespace prefixes in this 
     * case.
     * @param name The name of the entity to reference.Unlike 
     *   <code>Document.createElementNS</code> or 
     *   <code>Document.createAttributeNS</code>, no namespace well-formed 
     *   checking is done on the entity name. Applications should invoke 
     *   <code>Document.normalizeDocument()</code> with the parameter "
     *   namespaces" set to <code>true</code> in order to ensure that the 
     *   entity name is namespace well-formed. 
     * @return The new <code>EntityReference</code> object.
     * @exception DOMException
     *   INVALID_CHARACTER_ERR: Raised if the specified name is not an XML 
     *   name according to the XML version in use specified in the 
     *   <code>Document.xmlVersion</code> attribute.
     *   <br>NOT_SUPPORTED_ERR: Raised if this document is an HTML document.
     */
    public org.w3c.dom.EntityReference createEntityReference(String name) {
        //TODO Implement later
        return null;
    }

    /**
     * Creates an element of the type specified. Note that the instance 
     * returned implements the <code>Element</code> interface, so attributes 
     * can be specified directly on the returned object.
     * <br>In addition, if there are known attributes with default values, 
     * <code>Attr</code> nodes representing them are automatically created 
     * and attached to the element.
     * <br>To create an element with a qualified name and namespace URI, use 
     * the <code>createElementNS</code> method.
     * @param tagName The name of the element type to instantiate. For XML, 
     *   this is case-sensitive, otherwise it depends on the 
     *   case-sensitivity of the markup language in use. In that case, the 
     *   name is mapped to the canonical form of that markup by the DOM 
     *   implementation.
     * @return A new <code>Element</code> object with the 
     *   <code>nodeName</code> attribute set to <code>tagName</code>, and 
     *   <code>localName</code>, <code>prefix</code>, and 
     *   <code>namespaceURI</code> set to <code>null</code>.
     * @exception DOMException
     *   INVALID_CHARACTER_ERR: Raised if the specified name is not an XML 
     *   name according to the XML version in use specified in the 
     *   <code>Document.xmlVersion</code> attribute.
     */
    public org.w3c.dom.Element createElement(String tagName) {
        return new Element(tagName);
    }

    /**
     * Creates a <code>Comment</code> node given the specified string.
     * @param data The data for the node.
     * @return The new <code>Comment</code> object.
     */
    public org.w3c.dom.Comment createComment(String data) {
       return new Comment(data);
    }

    /**
     * Creates a <code>CDATASection</code> node whose value is the specified 
     * string.
     * @param data The data for the <code>CDATASection</code> contents.
     * @return The new <code>CDATASection</code> object.
     * @exception DOMException
     *   NOT_SUPPORTED_ERR: Raised if this document is an HTML document.
     */
    public org.w3c.dom.CDATASection createCDATASection(String data) {
        return new CData(data);
    }

    /**
     * Creates an <code>Attr</code> of the given name. Note that the 
     * <code>Attr</code> instance can then be set on an <code>Element</code> 
     * using the <code>setAttributeNode</code> method. 
     * <br>To create an attribute with a qualified name and namespace URI, use 
     * the <code>createAttributeNS</code> method.
     * @param name The name of the attribute.
     * @return A new <code>Attr</code> object with the <code>nodeName</code> 
     *   attribute set to <code>name</code>, and <code>localName</code>, 
     *   <code>prefix</code>, and <code>namespaceURI</code> set to 
     *   <code>null</code>. The value of the attribute is the empty string.
     * @exception DOMException
     *   INVALID_CHARACTER_ERR: Raised if the specified name is not an XML 
     *   name according to the XML version in use specified in the 
     *   <code>Document.xmlVersion</code> attribute.
     */
    public org.w3c.dom.Attr createAttribute(String name) {
        return new Attribute(name);
    }

    /**
     * Creates a <code>ProcessingInstruction</code> node given the specified 
     * name and data strings.
     * @param target The target part of the processing instruction.Unlike 
     *   <code>Document.createElementNS</code> or 
     *   <code>Document.createAttributeNS</code>, no namespace well-formed 
     *   checking is done on the target name. Applications should invoke 
     *   <code>Document.normalizeDocument()</code> with the parameter "
     *   namespaces" set to <code>true</code> in order to ensure that the 
     *   target name is namespace well-formed. 
     * @param data The data for the node.
     * @return The new <code>ProcessingInstruction</code> object.
     * @exception DOMException
     *   INVALID_CHARACTER_ERR: Raised if the specified target is not an XML 
     *   name according to the XML version in use specified in the 
     *   <code>Document.xmlVersion</code> attribute.
     *   <br>NOT_SUPPORTED_ERR: Raised if this document is an HTML document.
     */
    public org.w3c.dom.ProcessingInstruction createProcessingInstruction(String target, String data) {
        //TODO Implement later
        return null;
    }

    /**
     * Creates an element of the given qualified name and namespace URI.
     * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     * , applications must use the value <code>null</code> as the 
     * namespaceURI parameter for methods if they wish to have no namespace.
     * @param namespaceURI The namespace URI of the element to create.
     * @param qualifiedName The qualified name of the element type to 
     *   instantiate.
     * @return A new <code>Element</code> object with the following 
     *   attributes:
     * <table>
     * <caption>Element object attributes</caption>
     * <tr>
     * <th>Attribute</th>
     * <th>Value</th>
     * </tr>
     * <tr>
     * <td rowspan='1' colspan='1'><code>Node.nodeName</code></td>
     * <td rowspan='1' colspan='1'>
     *   <code>qualifiedName</code></td>
     * </tr>
     * <tr>
     * <td rowspan='1' colspan='1'><code>Node.namespaceURI</code></td>
     * <td rowspan='1' colspan='1'>
     *   <code>namespaceURI</code></td>
     * </tr>
     * <tr>
     * <td rowspan='1' colspan='1'><code>Node.prefix</code></td>
     * <td rowspan='1' colspan='1'>prefix, extracted 
     *   from <code>qualifiedName</code>, or <code>null</code> if there is 
     *   no prefix</td>
     * </tr>
     * <tr>
     * <td rowspan='1' colspan='1'><code>Node.localName</code></td>
     * <td rowspan='1' colspan='1'>local name, extracted from 
     *   <code>qualifiedName</code></td>
     * </tr>
     * <tr>
     * <td rowspan='1' colspan='1'><code>Element.tagName</code></td>
     * <td rowspan='1' colspan='1'>
     *   <code>qualifiedName</code></td>
     * </tr>
     * </table>
     * @exception DOMException
     *   INVALID_CHARACTER_ERR: Raised if the specified 
     *   <code>qualifiedName</code> is not an XML name according to the XML 
     *   version in use specified in the <code>Document.xmlVersion</code> 
     *   attribute.
     *   <br>NAMESPACE_ERR: Raised if the <code>qualifiedName</code> is a 
     *   malformed qualified name, if the <code>qualifiedName</code> has a 
     *   prefix and the <code>namespaceURI</code> is <code>null</code>, or 
     *   if the <code>qualifiedName</code> has a prefix that is "xml" and 
     *   the <code>namespaceURI</code> is different from "<a href='http://www.w3.org/XML/1998/namespace'>
     *   http://www.w3.org/XML/1998/namespace</a>" [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     *   , or if the <code>qualifiedName</code> or its prefix is XMLNS and 
     *   the <code>namespaceURI</code> is different from "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>", or if the <code>namespaceURI</code> is "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>" and neither the <code>qualifiedName</code> nor its prefix is XMLNS.
     *   <br>NOT_SUPPORTED_ERR: Always thrown if the current document does not 
     *   support the <code>"XML"</code> feature, since namespaces were 
     *   defined by XML.
     * @since DOM Level 2
     */
    public org.w3c.dom.Element createElementNS(String namespaceURI, String qualifiedName) {
        Element ret = new Element(qualifiedName);
        String prefix = ret.getPrefix();
        if (prefix != null) {
            if (namespaceURI == null) {
                throw new DOMException(DOMException.NAMESPACE_ERR, null);
            }
            ret.appendAttribute(new Attribute("xmlns:"+prefix, namespaceURI)); //NOI18N
        } else {
            ret = new Element(ret.getLocalName());
            if (namespaceURI != null && ! namespaceURI.equals(XMLConstants.NULL_NS_URI)) {
                ret.appendAttribute(new Attribute(XMLNS, namespaceURI)); 
            }
        }
        return ret;
    }

    /**
     * Creates an empty <code>DocumentFragment</code> object.
     * @return A new <code>DocumentFragment</code>.
     */
    public org.w3c.dom.DocumentFragment createDocumentFragment() {
        //TODO Implement later
        return null;
    }

    /**
     * Creates an attribute of the given qualified name and namespace URI.
     * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     * , applications must use the value <code>null</code> as the 
     * <code>namespaceURI</code> parameter for methods if they wish to have 
     * no namespace.
     * @param namespaceURI The namespace URI of the attribute to create.
     * @param qualifiedName The qualified name of the attribute to 
     *   instantiate.
     * @return A new <code>Attr</code> object with the following attributes:
     * <table>
     * <caption>Attr object attributes</caption>
     * <tr>
     * <th>
     *   Attribute</th>
     * <th>Value</th>
     * </tr>
     * <tr>
     * <td rowspan='1' colspan='1'><code>Node.nodeName</code></td>
     * <td rowspan='1' colspan='1'>qualifiedName</td>
     * </tr>
     * <tr>
     * <td rowspan='1' colspan='1'>
     *   <code>Node.namespaceURI</code></td>
     * <td rowspan='1' colspan='1'><code>namespaceURI</code></td>
     * </tr>
     * <tr>
     * <td rowspan='1' colspan='1'>
     *   <code>Node.prefix</code></td>
     * <td rowspan='1' colspan='1'>prefix, extracted from 
     *   <code>qualifiedName</code>, or <code>null</code> if there is no 
     *   prefix</td>
     * </tr>
     * <tr>
     * <td rowspan='1' colspan='1'><code>Node.localName</code></td>
     * <td rowspan='1' colspan='1'>local name, extracted from 
     *   <code>qualifiedName</code></td>
     * </tr>
     * <tr>
     * <td rowspan='1' colspan='1'><code>Attr.name</code></td>
     * <td rowspan='1' colspan='1'>
     *   <code>qualifiedName</code></td>
     * </tr>
     * <tr>
     * <td rowspan='1' colspan='1'><code>Node.nodeValue</code></td>
     * <td rowspan='1' colspan='1'>the empty 
     *   string</td>
     * </tr>
     * </table>
     * @exception DOMException
     *   INVALID_CHARACTER_ERR: Raised if the specified 
     *   <code>qualifiedName</code> is not an XML name according to the XML 
     *   version in use specified in the <code>Document.xmlVersion</code> 
     *   attribute.
     *   <br>NAMESPACE_ERR: Raised if the <code>qualifiedName</code> is a 
     *   malformed qualified name, if the <code>qualifiedName</code> has a 
     *   prefix and the <code>namespaceURI</code> is <code>null</code>, if 
     *   the <code>qualifiedName</code> has a prefix that is "xml" and the 
     *   <code>namespaceURI</code> is different from "<a href='http://www.w3.org/XML/1998/namespace'>
     *   http://www.w3.org/XML/1998/namespace</a>", if the <code>qualifiedName</code> or its prefix is XMLNS and the 
     *   <code>namespaceURI</code> is different from "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>", or if the <code>namespaceURI</code> is "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>" and neither the <code>qualifiedName</code> nor its prefix is XMLNS.
     *   <br>NOT_SUPPORTED_ERR: Always thrown if the current document does not 
     *   support the <code>"XML"</code> feature, since namespaces were 
     *   defined by XML.
     * @since DOM Level 2
     */
    public org.w3c.dom.Attr createAttributeNS(String namespaceURI, String qualifiedName) {
        //TODO validate namespaceURI
        return new Attribute(qualifiedName);
    }

    /**
     * Returns the <code>Element</code> that has an ID attribute with the 
     * given value. If no such element exists, this returns <code>null</code>
     * . If more than one element has an ID attribute with that value, what 
     * is returned is undefined. 
     * <br> The DOM implementation is expected to use the attribute 
     * <code>Attr.isId</code> to determine if an attribute is of type ID. 
     * <p ><b>Note:</b> Attributes with the name "ID" or "id" are not of type 
     * ID unless so defined.
     * @param elementId The unique <code>id</code> value for an element.
     * @return The matching element or <code>null</code> if there is none.
     * @since DOM Level 2
     */
    public org.w3c.dom.Element getElementById(String elementId) {
        //TODO Implement later
        return null;
    }

    /**
     * Returns a <code>NodeList</code> of all the <code>Elements</code> in 
     * document order with a given tag name and are contained in the 
     * document.
     * @param tagname  The name of the tag to match on. The special value "*" 
     *   matches all tags. For XML, the <code>tagname</code> parameter is 
     *   case-sensitive, otherwise it depends on the case-sensitivity of the 
     *   markup language in use. 
     * @return A new <code>NodeList</code> object containing all the matched 
     *   <code>Elements</code>.
     */
    public org.w3c.dom.NodeList getElementsByTagName(String tagname) {
        //TODO Implement later
        return null;
    }

    /**
     * Returns a <code>NodeList</code> of all the <code>Elements</code> with a 
     * given local name and namespace URI in document order.
     * @param namespaceURI The namespace URI of the elements to match on. The 
     *   special value <code>"*"</code> matches all namespaces.
     * @param localName The local name of the elements to match on. The 
     *   special value "*" matches all local names.
     * @return A new <code>NodeList</code> object containing all the matched 
     *   <code>Elements</code>.
     * @since DOM Level 2
     */
    public org.w3c.dom.NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
        //TODO Implement later
        return null;
    }

    /**
     *  This method acts as if the document was going through a save and load 
     * cycle, putting the document in a "normal" form. As a consequence, 
     * this method updates the replacement tree of 
     * <code>EntityReference</code> nodes and normalizes <code>Text</code> 
     * nodes, as defined in the method <code>Node.normalize()</code>. 
     * <br> Otherwise, the actual result depends on the features being set on 
     * the <code>Document.domConfig</code> object and governing what 
     * operations actually take place. Noticeably this method could also 
     * make the document namespace well-formed according to the algorithm 
     * described in , check the character normalization, remove the 
     * <code>CDATASection</code> nodes, etc. See 
     * <code>DOMConfiguration</code> for details. 
     * <pre>// Keep in the document 
     * the information defined // in the XML Information Set (Java example) 
     * DOMConfiguration docConfig = myDocument.getDomConfig(); 
     * docConfig.setParameter("infoset", Boolean.TRUE); 
     * myDocument.normalizeDocument();</pre>
     * 
     * <br>Mutation events, when supported, are generated to reflect the 
     * changes occurring on the document.
     * <br> If errors occur during the invocation of this method, such as an 
     * attempt to update a read-only node or a <code>Node.nodeName</code> 
     * contains an invalid character according to the XML version in use, 
     * errors or warnings (<code>DOMError.SEVERITY_ERROR</code> or 
     * <code>DOMError.SEVERITY_WARNING</code>) will be reported using the 
     * <code>DOMErrorHandler</code> object associated with the "error-handler
     * " parameter. Note this method might also report fatal errors (
     * <code>DOMError.SEVERITY_FATAL_ERROR</code>) if an implementation 
     * cannot recover from an error. 
     * @since DOM Level 3
     */
    public void normalizeDocument() {
         //TODO Implement later
   }

    /**
     * This is a convenience attribute that allows direct access to the child 
     * node that is the document element of the document.
     */
    public org.w3c.dom.Element getDocumentElement() {
        if(hasChildNodes()) {
            NodeList childNodes = getChildNodes();
            for (int i=0;i<childNodes.getLength(); i++) {
                Node child = (Node)childNodes.item(i);
                if (child instanceof org.w3c.dom.Element)
                    return (org.w3c.dom.Element)child;
            }
        }
        return null;
    }

    /**
     * The Document Type Declaration (see <code>DocumentType</code>) 
     * associated with this document. For XML documents without a document 
     * type declaration this returns <code>null</code>. For HTML documents, 
     * a <code>DocumentType</code> object may be returned, independently of 
     * the presence or absence of document type declaration in the HTML 
     * document.
     * <br>This provides direct access to the <code>DocumentType</code> node, 
     * child node of this <code>Document</code>. This node can be set at 
     * document creation time and later changed through the use of child 
     * nodes manipulation methods, such as <code>Node.insertBefore</code>, 
     * or <code>Node.replaceChild</code>. Note, however, that while some 
     * implementations may instantiate different types of 
     * <code>Document</code> objects supporting additional features than the 
     * "Core", such as "HTML" [<a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>DOM Level 2 HTML</a>]
     * , based on the <code>DocumentType</code> specified at creation time, 
     * changing it afterwards is very unlikely to result in a change of the 
     * features supported.
     * @version DOM Level 3
     */
    public org.w3c.dom.DocumentType getDoctype() {
        //TODO Implement later
        return null;
    }

    /**
     *  The location of the document or <code>null</code> if undefined or if 
     * the <code>Document</code> was created using 
     * <code>DOMImplementation.createDocument</code>. No lexical checking is 
     * performed when setting this attribute; this could result in a 
     * <code>null</code> value returned when using <code>Node.baseURI</code>
     * . 
     * <br> Beware that when the <code>Document</code> supports the feature 
     * "HTML" [<a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>DOM Level 2 HTML</a>]
     * , the href attribute of the HTML BASE element takes precedence over 
     * this attribute when computing <code>Node.baseURI</code>. 
     * @since DOM Level 3
     */
    public void setDocumentURI(String documentURI) {
    }

    /**
     *  The location of the document or <code>null</code> if undefined or if 
     * the <code>Document</code> was created using 
     * <code>DOMImplementation.createDocument</code>. No lexical checking is 
     * performed when setting this attribute; this could result in a 
     * <code>null</code> value returned when using <code>Node.baseURI</code>
     * . 
     * <br> Beware that when the <code>Document</code> supports the feature 
     * "HTML" [<a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>DOM Level 2 HTML</a>]
     * , the href attribute of the HTML BASE element takes precedence over 
     * this attribute when computing <code>Node.baseURI</code>. 
     * @since DOM Level 3
     */
    public String getDocumentURI() {
        //TODO Implement later
        return null;
    }

    /**
     *  The configuration used when <code>Document.normalizeDocument()</code> 
     * is invoked. 
     * @since DOM Level 3
     */
    public org.w3c.dom.DOMConfiguration getDomConfig() {
        //TODO Implement later
        return null;
    }

    /**
     * The <code>DOMImplementation</code> object that handles this document. A 
     * DOM application may use objects from multiple implementations.
     */
    public org.w3c.dom.DOMImplementation getImplementation() {
        //TODO Implement later
        return null;
    }

    /**
     * An attribute specifying the encoding used for this document at the time 
     * of the parsing. This is <code>null</code> when it is not known, such 
     * as when the <code>Document</code> was created in memory.
     * @since DOM Level 3
     */
    public String getInputEncoding() {
        //TODO Implement later
        return null;
    }

    /**
     * An attribute specifying whether error checking is enforced or not. When 
     * set to <code>false</code>, the implementation is free to not test 
     * every possible error case normally defined on DOM operations, and not 
     * raise any <code>DOMException</code> on DOM operations or report 
     * errors while using <code>Document.normalizeDocument()</code>. In case 
     * of error, the behavior is undefined. This attribute is 
     * <code>true</code> by default.
     * @since DOM Level 3
     */
    public void setStrictErrorChecking(boolean strictErrorChecking) {
    }

    /**
     * An attribute specifying whether error checking is enforced or not. When 
     * set to <code>false</code>, the implementation is free to not test 
     * every possible error case normally defined on DOM operations, and not 
     * raise any <code>DOMException</code> on DOM operations or report 
     * errors while using <code>Document.normalizeDocument()</code>. In case 
     * of error, the behavior is undefined. This attribute is 
     * <code>true</code> by default.
     * @since DOM Level 3
     */
    public boolean getStrictErrorChecking() {
        //TODO Implement later
        return false;
    }

    /**
     * An attribute specifying, as part of the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#NT-XMLDecl'>XML declaration</a>, the encoding of this document. This is <code>null</code> when 
     * unspecified or when it is not known, such as when the 
     * <code>Document</code> was created in memory.
     * @since DOM Level 3
     */
    public String getXmlEncoding() {
        for (Token token : super.getTokens()) {
            if(token.getType() == TokenType.TOKEN_PI_VAL) {
                String versionImage = token.getValue();
                int versionIndex = versionImage.indexOf("encoding");
                if(versionIndex == -1) return null;
                versionIndex = versionImage.indexOf('=',versionIndex);
                if(versionIndex == -1) return null;
                versionImage = versionImage.substring(versionIndex+1).trim();
                if(versionImage.startsWith("\"")) {
                    int versionEndIndex = versionImage.indexOf('"',1);
                    if(versionEndIndex == -1) return null;
                    return versionImage.substring(1,versionEndIndex);
                } else if(versionImage.startsWith("'")) {
                    int versionEndIndex = versionImage.indexOf('\'',1);
                    if(versionEndIndex == -1) return null;
                    return versionImage.substring(1,versionEndIndex);
                }
            }
        }
        return null;
    }

    /**
     * An attribute specifying, as part of the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#NT-XMLDecl'>XML declaration</a>, whether this document is standalone. This is <code>false</code> when 
     * unspecified.
     * <p ><b>Note:</b>  No verification is done on the value when setting 
     * this attribute. Applications should use 
     * <code>Document.normalizeDocument()</code> with the "validate" 
     * parameter to verify if the value matches the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#sec-rmd'>validity 
     * constraint for standalone document declaration</a> as defined in [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]. 
     * @exception DOMException
     *    NOT_SUPPORTED_ERR: Raised if this document does not support the 
     *   "XML" feature. 
     * @since DOM Level 3
     */
    public void setXmlStandalone(boolean xmlStandalone) {
        //TODO Implement later
    }

    /**
     * An attribute specifying, as part of the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#NT-XMLDecl'>XML declaration</a>, whether this document is standalone. This is <code>false</code> when 
     * unspecified.
     * <p ><b>Note:</b>  No verification is done on the value when setting 
     * this attribute. Applications should use 
     * <code>Document.normalizeDocument()</code> with the "validate" 
     * parameter to verify if the value matches the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#sec-rmd'>validity 
     * constraint for standalone document declaration</a> as defined in [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]. 
     * @since DOM Level 3
     */
    public boolean getXmlStandalone() {
        for (Token token : super.getTokens()) {
            if(token.getType() == TokenType.TOKEN_PI_VAL) {
                String versionImage = token.getValue();
                int versionIndex = versionImage.indexOf("standalone");
                if(versionIndex == -1) return false;
                versionIndex = versionImage.indexOf('=',versionIndex);
                if(versionIndex == -1) return false;
                versionImage = versionImage.substring(versionIndex+1).trim();
                if(versionImage.startsWith("\"")) {
                    int versionEndIndex = versionImage.indexOf('"',1);
                    if(versionEndIndex == -1) return false;
                    return versionImage.substring(1,versionEndIndex).equals("yes");
                } else if(versionImage.startsWith("'")) {
                    int versionEndIndex = versionImage.indexOf('\'',1);
                    if(versionEndIndex == -1) return false;
                    return versionImage.substring(1,versionEndIndex).equals("yes");
                }
            }
        }
        return false;
    }

    /**
     *  An attribute specifying, as part of the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#NT-XMLDecl'>XML declaration</a>, the version number of this document. If there is no declaration and if 
     * this document supports the "XML" feature, the value is 
     * <code>"1.0"</code>. If this document does not support the "XML" 
     * feature, the value is always <code>null</code>. Changing this 
     * attribute will affect methods that check for invalid characters in 
     * XML names. Application should invoke 
     * <code>Document.normalizeDocument()</code> in order to check for 
     * invalid characters in the <code>Node</code>s that are already part of 
     * this <code>Document</code>. 
     * <br> DOM applications may use the 
     * <code>DOMImplementation.hasFeature(feature, version)</code> method 
     * with parameter values "XMLVersion" and "1.0" (respectively) to 
     * determine if an implementation supports [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]. DOM 
     * applications may use the same method with parameter values 
     * "XMLVersion" and "1.1" (respectively) to determine if an 
     * implementation supports [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>]. In both 
     * cases, in order to support XML, an implementation must also support 
     * the "XML" feature defined in this specification. <code>Document</code>
     *  objects supporting a version of the "XMLVersion" feature must not 
     * raise a <code>NOT_SUPPORTED_ERR</code> exception for the same version 
     * number when using <code>Document.xmlVersion</code>. 
     * @exception DOMException
     *    NOT_SUPPORTED_ERR: Raised if the version is set to a value that is 
     *   not supported by this <code>Document</code> or if this document 
     *   does not support the "XML" feature. 
     * @since DOM Level 3
     */
    public void setXmlVersion(String xmlVersion) {
        //TODO Implement later
    }

    /**
     *  An attribute specifying, as part of the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#NT-XMLDecl'>XML declaration</a>, the version number of this document. If there is no declaration and if 
     * this document supports the "XML" feature, the value is 
     * <code>"1.0"</code>. If this document does not support the "XML" 
     * feature, the value is always <code>null</code>. Changing this 
     * attribute will affect methods that check for invalid characters in 
     * XML names. Application should invoke 
     * <code>Document.normalizeDocument()</code> in order to check for 
     * invalid characters in the <code>Node</code>s that are already part of 
     * this <code>Document</code>. 
     * <br> DOM applications may use the 
     * <code>DOMImplementation.hasFeature(feature, version)</code> method 
     * with parameter values "XMLVersion" and "1.0" (respectively) to 
     * determine if an implementation supports [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]. DOM 
     * applications may use the same method with parameter values 
     * "XMLVersion" and "1.1" (respectively) to determine if an 
     * implementation supports [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>]. In both 
     * cases, in order to support XML, an implementation must also support 
     * the "XML" feature defined in this specification. <code>Document</code>
     *  objects supporting a version of the "XMLVersion" feature must not 
     * raise a <code>NOT_SUPPORTED_ERR</code> exception for the same version 
     * number when using <code>Document.xmlVersion</code>. 
     * @since DOM Level 3
     */
    public String getXmlVersion() {
        for (Token token : super.getTokens()) {
            if(token.getType() == TokenType.TOKEN_PI_VAL) {
                String versionImage = token.getValue();
                int versionIndex = versionImage.indexOf("version");
                if(versionIndex == -1) return null;
                versionIndex = versionImage.indexOf('=',versionIndex);
                if(versionIndex == -1) return null;
                versionImage = versionImage.substring(versionIndex+1).trim();
                if(versionImage.startsWith("\"")) {
                    int versionEndIndex = versionImage.indexOf('"',1);
                    if(versionEndIndex == -1) return null;
                    return versionImage.substring(1,versionEndIndex);
                } else if(versionImage.startsWith("'")) {
                    int versionEndIndex = versionImage.indexOf('\'',1);
                    if(versionEndIndex == -1) return null;
                    return versionImage.substring(1,versionEndIndex);
                }
            }
        }
        return null;
    }

    /**
     * This api returns the namespaceuri of specified node.
     * @param node The node which namespace to find.
     * @return The namespaceuri of given node.
     */
    public String getNamespaceURI(Node node) {
        return getNamespaceCache().findNamespace(node);
    }	

    private FindNamespaceVisitor getNamespaceCache() {
        FindNamespaceVisitor v;
        
        if (fnv == null || (v = fnv.get()) == null) {
            v = new FindNamespaceVisitor(this);
            // preinitialize, further calls will not mutate the visitor.
            v.getNamespaceMap();
            fnv = new SoftReference<FindNamespaceVisitor>(v);
        }
        return v;
    }
    
    Map<Integer,String> getNamespaceMap() {
        return getNamespaceCache().getNamespaceMap();
    }
    
    /**
     * Namespace finder visitor
     */
    private volatile SoftReference<FindNamespaceVisitor> fnv;
}
